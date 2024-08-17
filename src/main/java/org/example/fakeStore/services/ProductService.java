// Service - Business Logic
// Service should be independent of the controller and dtos
// Always use models or exact value inside the service

package org.example.fakeStore.services;

import org.example.fakeStore.models.Product;

import java.util.List;

public interface ProductService {

    public Product createProduct(Product product);
    public List<Product> getAllProducts();
    public Product partialUpdateProduct(Long productId, Product product);
}
