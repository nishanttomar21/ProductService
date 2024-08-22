// List.of() is a static factory method that creates an immutable List instance with the specified elements. It was introduced in Java 9. Before Java 9, you could use Arrays.asList() to create a List instance with the specified elements. However, the list returned by Arrays.asList() is mutable, so you need to wrap it with new ArrayList<>(Arrays.asList()) to make it immutable.

package org.example.fakeStore.services;

import org.example.fakeStore.exception.ProductNotFoundException;
import org.example.fakeStore.models.Category;
import org.example.fakeStore.models.Product;
import org.example.fakeStore.repositories.CategoryRepository;
import org.example.fakeStore.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Logic to save the product in the database
@Service("dBProductService")
public class ProductServiceDBImpl implements ProductService {
    private final ProductRepository productRepository;  // Dependency Inversion
    private final CategoryRepository categoryRepository; // Dependency Inversion

    // Constructor Injection
    public ProductServiceDBImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) {
        // Get the category of the product
        Category categoryToBeSaved = getCategoryInProduct(product);

        // Set the category of the product
        product.setCategory(categoryToBeSaved);

        // Save the product in the database
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product partialUpdateProduct(Long productId, Product product) throws ProductNotFoundException {
        // Get the product to be updated
        Optional<Product> productRequested = productRepository.findById(productId);

        // Check if the product exists
        if (productRequested.isEmpty())
            throw new ProductNotFoundException(String.format("Product with id %d not found", productId));

        // Update the product details
        Product productToBeUpdated = productRequested.get();

        if (product.getTitle() != null)
            productToBeUpdated.setTitle(product.getTitle());
        if (product.getDescription() != null)
            productToBeUpdated.setDescription(product.getDescription());
        if (product.getPrice() != null)
            productToBeUpdated.setPrice(product.getPrice());
        if (product.getImageUrl() != null)
            productToBeUpdated.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            Category categoryToBeSaved = getCategoryInProduct(product);
            productToBeUpdated.setCategory(categoryToBeSaved);
        }

        // Save the updated product in the database
        return productRepository.save(productToBeUpdated);
    }

    // Helper method to get the category of the product
    private Category getCategoryInProduct(Product product) {
        String categoryName = product.getCategory().getName();

        Optional<Category> category = categoryRepository.findByName(categoryName);
        Category categoryToBeSaved;

        /**
         * category.orElseGet(product::getCategory) checks if category contains a value:
         * This method is a part of the Optional class.
         * If category is present (not empty), it assigns that value to categoryToBeSaved.
         * If category is empty, it calls product.getCategory() and assigns the returned value to categoryToBeSaved.

         categoryToBeSaved = category.orElseGet(product::getCategory); // Above if-else block can be replaced with this line for function coding style
         */
        if (category.isEmpty())   // Better inline implementation of if-else block for functional coding style in comments above
            categoryToBeSaved = categoryRepository.save(product.getCategory()); // Save the category in the database
        else
            categoryToBeSaved = category.get();

        return categoryToBeSaved;
    }
}
