/*
 Annotations - Used to provide metadata to the Java code. Annotations are used to provide extra information about the code to the compiler or runtime. They do not affect the execution of the code itself.
 RestController - Act like a controller and receive requests from the Dispatcher Servlet. It's a specialized version of @Controller that is used to create RESTful web services
 Qualifier - The @Qualifier annotation in Spring is used to provide more control over the autowiring process when there are multiple beans of the same type available for injection. It allows you to narrow down the selection of candidate beans based on a specified qualifier value.
 Autowired - The @Autowired annotation in Spring is a key feature used for dependency injection, allowing Spring to automatically resolve and inject collaborating beans into your application components. It's used in setter/constructor/field injections.
 Value - The @Value annotation in Spring is used to assign default values to variables and method arguments. It is used at the field or method/constructor parameter level.
 PostMapping - Map the HTTP POST requests to the method
 GetMapping - Map the HTTP GET requests to the method
 DeleteMapping - Map the HTTP DELETE requests to the method
 RequestMapping - Map the HTTP requests to the custom method
 PathVariable - Used to extract the values from the URI
 RequestParam/QueryParams - Used to extract the values from the query string
 RequestBody - Used to extract the values from the request body (JSON/XML)
 ApplicationContext - The ApplicationContext is the central interface within a Spring application for providing configuration information to the application. It is responsible for instantiating, configuring, and assembling the beans.
 Reflection - Reflection is a feature in Java that allows you to inspect and manipulate classes, methods, fields, and other components of a Java program at runtime. It provides a way to access and modify the properties and behavior of objects dynamically.
 TomcatServer - Tomcat is an open-source web server and servlet container developed by the Apache Software Foundation. It is used to serve Java-based web applications.
 JsonIgnore - The @JsonIgnore annotation in Jackson is used to ignore a property or field during serialization and deserialization. It tells the Jackson library to exclude the annotated property from the JSON output.
 JsonSerialize - The @JsonSerialize annotation in Jackson is used to specify a custom serializer for a property or field during serialization. It allows you to define how the property should be serialized to JSON.
 JsonDeserialize - The @JsonDeserialize annotation in Jackson is used to specify a custom deserializer for a property or field during deserialization. It allows you to define how the property should be deserialized from JSON.
 Jackson - Jackson is a popular JSON processing library for Java. It provides a set of high-performance tools for parsing, generating, and manipulating JSON data. Jackson is widely used in Java applications for handling JSON data.
 Serialization - The process of converting an object into a format that can be stored or transmitted. Common formats include JSON, XML, and binary data.
 Deserialization - The reverse process of serialization. It involves converting the serialized data back into an object that the program can work with.

 Multiple controllers can call/use 1 service, hence use models/exact values inside (not dtos) and dtos outside the application.
 1 controller can use multiple services, but it's not recommended due to violation of SRP principle

 The Spring Container is the core of the Spring Framework, responsible for managing the lifecycle of beans. It instantiates, configures, and assembles the beans based on the configuration metadata provided (usually in XML or Java annotations).
 There are two main types of containers in Spring:
    1. BeanFactory: The simplest container that provides basic support for dependency injection and manages bean creation and configuration. It uses lazy initialization, meaning beans are created only when requested.
    2. ApplicationContext: An extension of the BeanFactory that adds more enterprise-specific features. It eagerly initializes beans and provides additional functionalities such as event propagation, internationalization, and support for various application contexts (like web applications).

 [Important] Class should be noun, method should be verb
 @UpdateMapping is not a standard annotation in Spring Boot or Spring Framework. Updates to resources are typically handled using the @PutMapping or @PatchMapping annotations.
 Exception Handling is done in the Controller layer and not Service layer using @ControllerAdvice and @ExceptionHandler annotations. It allows you to handle exceptions globally or for specific controllers. Service throws the exception and Controller catches it. @ExceptionHandler methods can be placed within a controller class for specific handling or in a @ControllerAdvice class for global handling.
*/

package org.example.productService.controllers;

import org.example.productService.dtos.product.*;
import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;
import org.example.productService.services.ProductService;
import org.example.productService.services.ProductServiceDBImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;    // Dependency Inversion
    private ProductServiceDBImpl productServiceDBImpl;

    // Solution 1 - Constructor Injection (Dependency Injection)
    /* public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }*/

    // Solution 2 - [Better Implementation] Constructor Injection using application.properties configuration
    public ProductController(@Value("${productService}") String productServiceBeanName, ApplicationContext context) {
        this.productService = (ProductService) context.getBean(productServiceBeanName);
    }

    // Setter/Method Injection
    @Autowired
    public void setProductServiceDBImpl(ProductServiceDBImpl productServiceDBImpl) {
        this.productServiceDBImpl = productServiceDBImpl;
    }

    // HTTP Requests
    @PostMapping("")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        // DTO --> Model (Data conversion)
        Product productRequest = createProductRequestDto.toProduct();

        // Service called (using Model data)
        Product productResponse = productService.createProduct(productRequest);

        // Return/Response back Model --> DTO (Data conversion)
        CreateProductDto responseDto = CreateProductDto.fromProduct(productResponse);
        CreateProductResponseDto response = new CreateProductResponseDto();
        response.setProduct(responseDto);

        return response;
    }

    @GetMapping("")
    public GetAllProductsResponseDto getAllProducts() {
        List<GetProductDto> responseDto = new ArrayList<>();
        GetAllProductsResponseDto response = new GetAllProductsResponseDto();

        // Service called
        List<Product> products = productService.getAllProducts();

        // Model --> DTO (Data conversion)
        for (Product product : products) {
            responseDto.add(GetProductDto.fromProduct(product));
        }

        response.setProducts(responseDto);

        // Return/Response back DTO
        return response;
    }

    @PatchMapping("/{id}")
    public PatchProductResponseDto updateProduct(@PathVariable("id") Long productId, @RequestBody CreateProductDto productDto) throws ProductNotFoundException {
        // DTO --> Model (Data conversion)
        Product productRequest = productDto.toProduct();

        // Service called (using Model data)
        Product product = productService.partialUpdateProduct(productId, productRequest);

        // Model --> DTO (Data conversion)
        PatchProductResponseDto response = new PatchProductResponseDto();
        response.setProduct(GetProductDto.fromProduct(product));

        return response;
    }

    @GetMapping("/{id}")
    public String getSingleProduct(@PathVariable("id") Long id) {
        return "Product retrieved: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        return "Product deleted: " + id;
    }

    @PutMapping("")
    public void replaceProduct() {}

    @RequestMapping(name = "NISHANT", value = "/")      // Custom HTTP Request
    public String nishantMethod() {
        return "Nishant Method";
    }
}
