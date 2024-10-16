/*
    MVC Testing - Testing the Model-View-Controller (MVC) components of a web application. It allows you to verify the behavior of your controllers, request mappings, and the overall flow of HTTP requests through your application.

    Here's a brief overview of MVC testing in Spring Boot:
        Purpose:
            - Test controller methods
            - Verify request mappings
            - Check response status, headers, and content
            - Validate model attributes

        Key components:
            - MockMvc: A testing framework provided by Spring that simulates HTTP requests without starting a full web server

        Common annotations:
            - @WebMvcTest: For testing a specific controller
            - @AutoConfigureMockMvc: To autoconfigure MockMvc in a full application context test

        Basic steps:
            - Set up the test environment
            - Perform a mock HTTP request
            - Assert the response
 */
// ObjectMapper - It is used to convert Java objects to JSON and vice versa.
// @WebMvcTest - For testing a specific controller
// @AutoConfigureMockMvc: To autoconfigure MockMvc in a full application context test
// @MockBean: To create a mock object for a dependency
// MockMvc: A testing framework provided by Spring that simulates HTTP requests without starting a full web server
// MockMvcRequestBuilders: A class containing static methods for building mock HTTP requests
// MockMvcResultMatchers: A class containing static methods for matching the expected result of a mock HTTP request
// Mockito: A mocking framework for Java
// andExpect(): This is a method in the MockMvc framework that allows you to set expectations for the response.
// content(): This method focuses on the content (body) of the HTTP response.
// json(): This method specifies that you're expecting JSON content and will compare the response content as JSON.
// objectMapper.writeValueAsString(expectedResponse):
//      objectMapper is an instance of Jackson's ObjectMapper.
//      writeValueAsString() converts the Java object (expectedResponse) into a JSON string.
//      This conversion is necessary because the json() method expects a string representation of the JSON.


package org.example.productService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productService.dtos.product.CreateProductRequestDto;
import org.example.productService.dtos.product.GetAllProductsResponseDto;
import org.example.productService.dtos.product.GetProductDto;
import org.example.productService.models.Product;
import org.example.productService.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    // @Autowired
    @MockBean(name = "fakeStoreProductService")
    private ProductService productService;

   @MockBean
   private RestTemplate restTemplate;  // To remove the constructor error while mocking ProductController class

    @Autowired
    private ObjectMapper objectMapper;
    //object <-> json <-> string


    @Test
    public void TestGetAllProducts_RunsSuccessfully() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Iphone 16");
        product.setPrice(1000D);
        product.setDescription(null);
        product.setImageUrl(null);
        product.setCategory(null);
        product.setDeleted(false);

        List<Product> productListRequest = new ArrayList<>();
        productListRequest.add(product);

        when(productService.getAllProducts()).thenReturn(productListRequest);

        mockMvc.perform(get("/products/"))
                .andExpect(status().isOk())   // status: response status should be OK
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))        // .andDo(print()) for debugging
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))   // content type: application/json, default is text/plain
                .andExpect(jsonPath("$.products").isArray())    // // $: Represents the JSON object returned by the API
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].title").value("Iphone 16"))
                .andExpect(jsonPath("$.products[0].price").value(1000.0))
                .andExpect(jsonPath("$.products[0].description").isEmpty())
                .andExpect(jsonPath("$.products[0].imageUrl").isEmpty())
                .andExpect(jsonPath("$.products[0].category").isEmpty());

        // Combine way to check all the above fields in one line
        List<GetProductDto> productListResponse = new ArrayList<>();
        productListResponse.add(GetProductDto.fromProduct(product));
        GetAllProductsResponseDto expectedResponse = new GetAllProductsResponseDto();
        expectedResponse.setProducts(productListResponse);

        mockMvc.perform(get("/products/"))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));  // content: body of the response should match the request body
    }


    @Test
    public void TestCreateProduct_RunsSuccessfully() throws Exception {
        CreateProductRequestDto productDto = new CreateProductRequestDto();
        productDto.setTitle("MacBook Pro");
        productDto.setPrice(1000D);

        Product product = new Product();
        product.setTitle("MacBook Pro");
        product.setPrice(1000D);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products/").
                        content(objectMapper.writeValueAsString(productDto)).
                        contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.length()").value(6))
                .andExpect(jsonPath("$.product.title").value("MacBook Pro"));
    }
}


//{
//    "id": 2,
//    "title": "MacBook Pro"
//}
//
//$.title
//$.id
//$.length()

// mockMvc.perform(post("/products/")
//        .content(objectMapper.writeValueAsString(productDto))
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("Authorization", "Bearer token")
//        .param("someParam", "someValue"))