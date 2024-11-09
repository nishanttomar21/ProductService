// Bean - A class that is managed by Spring IoC container. These beans are created with the configuration metadata that is supplied to the spring container/Application context.
// Configuration - The @Configuration annotation in Spring Boot is used to indicate that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
// LoadBalanced - The @LoadBalanced annotation in Spring Cloud is used to enable load balancing for RESTful web services. It automatically selects an instance of a service to make requests to, ensuring that the requests are distributed across multiple instances of the service.
// RestTemplate - (Helps call 3rd Party APIs, complex implementation) A class that is part of the Spring Framework. It is a synchronous HTTP client that simplifies communication with HTTP servers and enforces RESTful principles. It handles HTTP connections and makes requests to RESTful web services.
// OpenFeign - (Helps call 3rd Party APIs, easy implementation) A declarative REST client that makes writing web service clients easier in Spring Boot applications. It's easier to implement than RestTemplate and provides a more readable and maintainable code.
// Object of RestTemplate is created using new RestTemplate() whereas Object of OpenFeign is created using @FeignClient annotation. Object of RestTemplate is not autowired whereas Object of OpenFeign is autowired
// SpringBoot does not know about the RestTemplate as it's not present as a spring bean and hence is not able to autowire it. To fix this, we need to create a bean of RestTemplate in the configuration class.
// WebClient is a reactive web client that supports both blocking and non-blocking I/O. It provides a more modern and efficient way to interact with web services using asynchronous programming.
// I/O operations (Input/Output operations) are processes that involve transferring data to or from a computer system. These operations typically include:
//    1. Reading from or writing to files on disk
//    2. Sending or receiving data over a network (In the context of web services and the WebClient mentioned in the code comment, I/O operations primarily refer to network communications, such as making HTTP requests to APIs and receiving responses.)
//    3. Interacting with external devices (e.g., printers, sensors)
//    4. Reading user input or displaying output on a screen
// ParameterizedTypeReference [Important]:
//    In Spring Framework, ParameterizedTypeReference<T> is a utility class used when you need to work with generic types in certain operations, such as making HTTP requests with RestTemplate or WebClient. It helps resolve issues where Java's type erasure causes problems with retaining generic type information at runtime.
//
//    Java's type erasure removes generic type information at runtime, which makes it difficult for Spring to determine the exact type of a generic collection. ParameterizedTypeReference<T> is a way to explicitly provide this type information so that Spring can deserialize responses into the appropriate types.
//
//    In simple terms, it is used when you need to pass or capture generic types, such as a List<MyClass> or Map<String, Object>, which would otherwise lose type information due to Java's type erasure.
//
//    Usage in RestTemplate Example:
//        Let's say you want to make an API call to get a list of MyClass objects. Normally, due to type erasure, you would not be able to directly get List<MyClass> from RestTemplate without casting or using a helper class. ParameterizedTypeReference solves this.
//
//    Example without ParameterizedTypeReference (loses generic info):
//        RestTemplate restTemplate = new RestTemplate();
//        List<MyClass> result = restTemplate.getForObject("http://example.com/api/data", List.class);
//        // This would not work as expected because it loses the type information.
//
//    Example using ParameterizedTypeReference:
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<MyClass>> response = restTemplate.exchange(
//                "http://example.com/api/data",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<MyClass>>() {}
//        );
//        List<MyClass> result = response.getBody();
// Command to run Redis server --> redis-server

package org.example.productService.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Bean
    //@LoadBalanced // Comment this line if you want to disable load balancing for the RestTemplate and FakeStore API calls will only work if you comment this
    public RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        // return new RestTemplate();
        return new RestTemplate(factory);
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
