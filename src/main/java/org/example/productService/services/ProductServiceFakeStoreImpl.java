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
 ParameterizedTypeReference [Important]:
    In Spring Framework, ParameterizedTypeReference<T> is a utility class used when you need to work with generic types in certain operations, such as making HTTP requests with RestTemplate or WebClient. It helps resolve issues where Java's type erasure causes problems with retaining generic type information at runtime.

    Java's type erasure removes generic type information at runtime, which makes it difficult for Spring to determine the exact type of a generic collection. ParameterizedTypeReference<T> is a way to explicitly provide this type information so that Spring can deserialize responses into the appropriate types.

    In simple terms, it is used when you need to pass or capture generic types, such as a List<MyClass> or Map<String, Object>, which would otherwise lose type information due to Java's type erasure.

    Usage in RestTemplate Example:
        Let's say you want to make an API call to get a list of MyClass objects. Normally, due to type erasure, you would not be able to directly get List<MyClass> from RestTemplate without casting or using a helper class. ParameterizedTypeReference solves this.

    Example without ParameterizedTypeReference (loses generic info):
        RestTemplate restTemplate = new RestTemplate();
        List<MyClass> result = restTemplate.getForObject("http://example.com/api/data", List.class);
        // This would not work as expected because it loses the type information.

    Example using ParameterizedTypeReference:
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<MyClass>> response = restTemplate.exchange(
                "http://example.com/api/data",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MyClass>>() {}
        );
        List<MyClass> result = response.getBody();
*/

package org.example.productService.services;

import org.example.productService.dtos.fakestore.FakeStoreCreateProductRequestDto;
import org.example.productService.dtos.fakestore.FakeStoreGetProductResponseDto;
import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
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
         // [Important] List<FakeStoreCreateProductResponseDto> will not work due to Type Erasure in Java (Generics are removed and replaced with Object during compilation) but Array/ParameterizedTypeReference will work here
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

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        ResponseEntity<FakeStoreGetProductResponseDto> fakeStoreProductResponse = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreGetProductResponseDto.class
        );

//        if (fakeStoreProductResponse.getStatusCode() != HttpStatusCode.valueOf(200)) {}
//        fakeStoreProductResponse.getHeaders().

        FakeStoreGetProductResponseDto fakeStoreProduct = fakeStoreProductResponse.getBody();

        if (fakeStoreProduct == null) {
            throw new ProductNotFoundException("Product with id: " + productId + " doesn't exist. Retry some other product.");
        }

        return fakeStoreProduct.toProduct();
    }

    // TODO
    @Override
    public void deleteProduct(Long id) {}

    // For PUT requests, you should use the put() method of RestTemplate. If you need to get a response object from a PUT request, you can use the exchange() method with HttpMethod.PUT.
    public Product replaceProduct(Long id, Product product) {
        FakeStoreCreateProductRequestDto fakeStoreProductDto = FakeStoreCreateProductRequestDto.fromProduct(product);

        // Implementation 1
        ResponseEntity<FakeStoreGetProductResponseDto> fakeStoreProductDtoResponse = restTemplate.exchange(
                "https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(fakeStoreProductDto),
                FakeStoreGetProductResponseDto.class,
                id  // This will replace {id} in the URL
        );

        // Implementation 2
//        FakeStoreGetProductResponseDto fakeStoreProductDtoResponse =
//                requestForEntity("https://fakestoreapi.com/products/{id}",HttpMethod.PUT, fakeStoreProductDto, FakeStoreGetProductResponseDto.class,id).getBody();


        return fakeStoreProductDtoResponse.getBody().toProduct();
    }

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    // Custom method to make any HTTP requests
    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
