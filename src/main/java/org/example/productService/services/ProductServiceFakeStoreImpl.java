/*
 Service - (Beam) Keeps the object of this class saved in Spring container/Application context and to be used by spring when Dependency Injecting
 Beans - Beans are the objects that form the backbone of the application and that are managed by the Spring IoC container. These beans are created with the configuration metadata that you supply to the container, for example, in the form of XML <bean/> definitions.
 Primary - This annotation is used to give a higher preference to a bean when there are multiple beans of the same type.
 RestTemplate - (Helps call 3rd Party APIs, complex implementation) A class that is part of the Spring Framework. It is a synchronous HTTP client that simplifies communication with HTTP servers and enforces RESTful principles. It handles HTTP connections and makes requests to RESTful web services.
 OpenFeign - (Helps call 3rd Party APIs, easy implementation) A declarative REST client that makes writing web service clients easier in Spring Boot applications. It's easier to implement than RestTemplate and provides a more readable and maintainable code.
 Object of RestTemplate is created using new RestTemplate() whereas Object of OpenFeign is created using @FeignClient annotation. Object of RestTemplate is not autowired whereas Object of OpenFeign is autowired
 SpringBoot does not know about the RestTemplate as it's not present as a spring bean and hence is not able to autowire it. To fix this, we need to create a bean of RestTemplate in the configuration class.
 WebClient is a reactive web client that supports both blocking and non-blocking I/O. It provides a more modern and efficient way to interact with web services using asynchronous programming.
 I/O operations (Input/Output operations) are processes that involve transferring data to or from a computer system. These operations typically include:
    1. Reading from or writing to files on disk
    2. Sending or receiving data over a network (In the context of web services and the WebClient mentioned in the code comment, I/O operations primarily refer to network communications, such as making HTTP requests to APIs and receiving responses.)
    3. Interacting with external devices (e.g., printers, sensors)
    4. Reading user input or displaying output on a screen
*/

package org.example.productService.services;

import org.example.productService.dtos.fakestore.FakeStoreCreateProductRequestDto;
import org.example.productService.dtos.fakestore.FakeStoreGetProductResponseDto;
import org.example.productService.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Stream;


@Service("fakeStoreProductService")
// @Primary
public class ProductServiceFakeStoreImpl implements ProductService {
    private final WebClient webClient;
    private final RestTemplate restTemplate;

    // Error (IMPORTANT): Could not autowire. No beans of 'RestTemplate' or 'WebClient' types found, hence manually create a bean of RestTemplate in the configuration class
    public ProductServiceFakeStoreImpl(WebClient.Builder webClientBuilder, RestTemplate restTemplate) {
        this.webClient = webClientBuilder.baseUrl("https://fakestoreapi.com").build();
        this.restTemplate = restTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        // [Request] Model --> DTO (Data conversion)
        FakeStoreCreateProductRequestDto request = FakeStoreCreateProductRequestDto.fromProduct(product);

        // 3rd Party API call (using DTO)
        FakeStoreGetProductResponseDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                request,
                FakeStoreGetProductResponseDto.class // Response DTO type to be converted to
        );

        // [Response] DTO --> Model (Data conversion)
        Product createProductResponse = response.toProduct();

        // Return Model
        return createProductResponse;
    }

    @Override
    public List<Product> getAllProducts() {
         // [Important] List<FakeStoreCreateProductResponseDto> will not work due to Type Erasure in Java (Generics are removed and replaced with Object during compilation) but Array works here
        // 3rd Party API call (using DTO)
        FakeStoreGetProductResponseDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                 FakeStoreGetProductResponseDto[].class // Response DTO type to be converted to (Used array here due to type closure)
                /** Error: Cannot convert from List to Class<T> */
                // List.class
        );

        // [Response] DTO --> Model (Data conversion)
        List<Product> products = Stream.of(response)
                                        .map(FakeStoreGetProductResponseDto::toProduct)
                                        .toList();

        // Return Model
        return products;
    }

    @Override
    public Product partialUpdateProduct(Long productId, Product product) {
        /** Error: PATCH method not supported by RestTemplate */
//        FakeStoreGetProductResponseDto request = restTemplate.exchange(
//                "https://fakestoreapi.com/products/" + productId,
//                HttpMethod.PATCH,
//                FakeStoreCreateProductRequestDto.fromProduct(product),
//                FakeStoreGetProductResponseDto.class
//        );

        // Model --> DTO (Data conversion)
        FakeStoreCreateProductRequestDto request = FakeStoreCreateProductRequestDto.fromProduct(product);

        // 3rd Party API call (using DTO)
        // Used WebClient instead of RestTemplate as PATCH method is not supported by RestTemplate
        FakeStoreGetProductResponseDto response = webClient.patch()
                .uri("/products/{id}", productId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FakeStoreGetProductResponseDto.class)
                .block(); // Blocking for simplicity (Sync call)

        return response.toProduct();
    }
}
