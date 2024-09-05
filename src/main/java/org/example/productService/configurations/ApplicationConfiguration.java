// Bean - A class that is managed by Spring IoC container. These beans are created with the configuration metadata that is supplied to the spring container/Application context.
// Configuration - The @Configuration annotation in Spring Boot is used to indicate that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
// RestTemplate - A class that is part of the Spring Framework. It is a synchronous HTTP client that simplifies communication with HTTP servers and enforces RESTful principles. It handles HTTP connections and makes requests to RESTful web services.

package org.example.productService.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
