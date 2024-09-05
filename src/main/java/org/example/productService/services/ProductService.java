// Service - Business Logic
// Service should be independent of the controller and dtos
// Always use models or exact value inside the service

package org.example.productService.services;

import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product partialUpdateProduct(Long productId, Product product) throws ProductNotFoundException;
}
