package org.example.fakeStore.services;

import org.example.fakeStore.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dBProductService")
public class ProductServiceDBImpl implements ProductService {

    @Override
    public Product createProduct(Product product) {
        // Logic to save the product in the database
        return new Product();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product partialUpdateProduct(Long productId, Product product) {
        return null;
    }
}
