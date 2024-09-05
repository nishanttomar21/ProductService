package org.example.productService.services;

import org.example.productService.dtos.search.FilterDto;
import org.example.productService.dtos.search.SortingCriteria;
import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;
import org.example.productService.repositories.ProductRepository;
import org.example.productService.services.filteringService.FilterFactory;
import org.example.productService.services.sortingService.SorterFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {
    private final ProductRepository productRepository;
    
    public SearchService(ProductRepository productService) {
        this.productRepository = productService;
    }

    public Page<Product> search(String query, List<FilterDto> filters, SortingCriteria sortingCriteria, int pageNumber, int pageSize) throws ProductNotFoundException {
        Optional<List<Product>> productsRequested = productRepository.findAllByTitleContaining(query);

        // Check if the product exists
        if (productsRequested.isEmpty())
            throw new ProductNotFoundException(String.format("Product with tile '%s' not found", query));

        List<Product> products = productsRequested.get();

        // Apply filters if provided
        for (FilterDto filter : filters) {
            products = FilterFactory.getFilterFromKey(filter.getKey()).applyFilter(products, filter.getValues());
        }

        // Apply sorting if provided
        if (sortingCriteria != null) {
            products = SorterFactory.getSorterByCriteria(sortingCriteria).applySorting(products);
        }

        // Paginate the results
        // 1. Creates a Pageable object directly, adjusting the page number to be zero-based.
        // 2. Uses PageImpl constructor with the full list of products, letting Spring handle the pagination internally.
        // 3. Eliminates the need for manual sublist creation, reducing potential for errors and improving efficiency.
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Product> paginatedProducts = new PageImpl<>(products, pageable, products.size());

        return paginatedProducts;
    }

    public Page<Product> simpleSearch(String query, Long categoryId, int pageNumber, int pageSize, String sortingAttribute) {
        // SELECT * from products
        // WHERE title like "%query%"
        // AND categoryID = {categoryId}
        // LIMIT {pageSize} OFFSET pageNumber * pageSize
        // ORDER BY sortingAttribute;
        Page<Product> products = productRepository.findAllByTitleContainingAndCategory_Id(query, categoryId,
                        PageRequest.of(pageNumber, pageSize, Sort.by(sortingAttribute).descending()));  /** [Something New]: of(int pageNumber, int pageSize, Sort sort), PageRequest return Pageable object, JPA has support for Pageable interface */

        return products;
    }
}
