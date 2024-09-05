// This is the main class of the application. It is the entry point of the Spring Boot application.
// The @SpringBootApplication annotation is used to mark the class as a Spring Boot application. It is a convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan annotations.
// SpringApplication.run is a static method that starts the Spring Boot application. It takes the main class and command-line arguments as arguments.
// Code Flow: The main method starts the Spring Boot application, which in turn starts the embedded Tomcat server and initializes the application context. The application context is responsible for managing the beans and dependencies in the application.
// The application context scans the packages for components (controllers, services, repositories) annotated with @Component, @Service, @Repository, etc., and creates instances of these beans.
// The application context also reads the application.properties or application.yml file for configuration properties and initializes the beans accordingly.
// The application context starts the embedded Tomcat server and listens for incoming HTTP requests. When a request is received, it routes it to the appropriate controller method based on the request mapping annotations.
// The controller method processes the request, interacts with the service layer to perform business logic, and returns the response to the client.
// The response is sent back to the client, and the application context manages the lifecycle of the beans until the application is shut down.
// Dispatch Servlet --> Advices --> Controller --> Service --> Repository --> ORM(JPA, Hibernate, JDBC) --> Database

package org.example.productService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
