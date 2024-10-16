/*
 Stubs - In Spring Boot, stubs are often used in testing to simulate the behavior of real components or services. They allow you to write unit and integration tests without depending on external systems (like databases, third-party services, or microservices)

 Here’s a breakdown of how to use stubs in different contexts when testing in Spring Boot:
    1. Using Mockito to Create Stubs: Mockito is one of the most common libraries for creating stubs and mocks in Spring Boot testing. You can use @Mock or @MockBean to create stubs of your components or services.
    2. Using WireMock for HTTP Stubs: For testing Spring Boot applications that rely on external HTTP services, WireMock is a great tool. It allows you to stub HTTP endpoints, simulate various responses (like 200, 404, 500), and verify that the requests were made as expected.
    3. Using TestRestTemplate with Stubs: For testing controllers or making HTTP calls to your application’s endpoints during integration testing, you can use TestRestTemplate. You can still stub the responses from downstream services using tools like Mockito or WireMock as shown above

 Summary:
     - Mockito is commonly used to create stubs for internal services, repositories, or components.
     - WireMock can be used for stubbing external HTTP services, providing more flexibility when dealing with RESTful APIs.
     - TestRestTemplate can help test controllers or API endpoints with real or mocked dependencies.
 */

package org.example.productService.controllers;

import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;
import org.example.productService.services.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary    // Now, ProductServiceFakeStoreImpl and ProductServiceDBImpl will not be called and rather StorageProductServiceStub will be called
public class StorageProductServiceStub implements ProductService {

    Map<String, Product> productMap;

    public StorageProductServiceStub() {
        productMap = new HashMap<>();
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product partialUpdateProduct(Long productId, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
