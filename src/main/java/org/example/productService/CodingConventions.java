/**

A. TODO [Coding convention in springboot for creating 2 classes createDto and getDto for each model  and later using in other classes]:

    In a Spring Boot project, it's common to use Data Transfer Objects (DTOs) to transfer data between different layers of an application, such as the controller, service, and repository layers. DTOs are particularly useful for encapsulating the data that you want to expose or receive from an API while keeping your internal models safe from external exposure.

    When creating CreateDto and GetDto classes for each model, it's essential to follow a consistent coding convention to maintain readability, clarity, and maintainability. Here’s a convention you can follow:

    1. Naming Conventions
        Model Class: Use PascalCase for model names. Example: User, Product.
        DTO Classes:
        CreateDto: Name it in the format <ModelName>CreateDto. Example: UserCreateDto, ProductCreateDto.
        GetDto: Name it in the format <ModelName>GetDto. Example: UserGetDto, ProductGetDto.

    2. Package Structure
        Organize your DTOs under a dto package inside the model package or alongside your model classes.

        Copy code
        com.example.projectname.model
        │
        ├── User.java
        ├── dto
        │   ├── UserCreateDto.java
        │   └── UserGetDto.java

    3. Class Structure
        CreateDto Class: Include only the fields that are required for creating a new instance of the model. This might exclude fields like id, createdAt, or any auto-generated fields.
        GetDto Class: Include all fields that you want to expose in the response to the client. This might include additional fields like id, createdAt, or any derived fields.

        public class UserCreateDto {
            @NotBlank(message = "Username is required") // Validation annotations can be added
            private String username;

            @NotBlank(message = "Email is required")
            private String email;
        }

        public class UserGetDto {
            private Long id;
            private String username;
            private String email;
            private String createdAt;
        }

    4. Mapping Between Model and DTOs
        Use a mapping service or library like MapStruct, ModelMapper, or write custom methods to convert between the model and DTOs.
        Create a static method in each DTO class to convert the model to the DTO and vice versa. This method should handle the conversion logic between the model and DTO fields.

        public class UserMapper {

            public static User toEntity(UserCreateDto dto) {
                User user = new User();
                user.setUsername(dto.getUsername());
                user.setEmail(dto.getEmail());
                return user;
            }

            public static UserGetDto toDto(User user) {
                UserGetDto dto = new UserGetDto();
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setCreatedAt(user.getCreatedAt().toString());
                return dto;
            }
        }

    6. Controller Usage
        In your controllers, you can use these DTOs to handle incoming requests and to send responses.

        @RestController
        @RequestMapping("/api/users")
        public class UserController {

            private final UserService userService;

            public UserController(UserService userService) {
                this.userService = userService;
            }

            @PostMapping
            public ResponseEntity<UserGetDto> createUser(@RequestBody UserCreateDto userCreateDto) {
                UserGetDto createdUser = userService.createUser(userCreateDto);
                return ResponseEntity.ok(createdUser);
            }

            @GetMapping("/{id}")
            public ResponseEntity<UserGetDto> getUserById(@PathVariable Long id) {
                UserGetDto user = userService.getUserById(id);
                return ResponseEntity.ok(user);
            }
        }

B. TODO [Why use ResponseEntity in response sent to client]:

    The ResponseEntity class in Spring is a powerful way to represent the entire HTTP response, including the status code, headers, and body. It's often used in controllers to return responses from RESTful APIs. Using ResponseEntity in Spring Boot is optional but highly recommended, especially when you want to have fine-grained control over your HTTP responses

    ResponseEntity is recommended when you need precise control over your HTTP responses.
    It allows for setting status codes, headers, and different types of response bodies, which is useful in many scenarios, particularly in RESTful web services.
    For simple use cases, where a method always returns 200 OK, you can skip ResponseEntity and let Spring handle the wrapping for you.

    @GetMapping("/users")
    public List<UserGetDto> getAllUsers() {
        return userService.getAllUsers(); // Implicitly wrapped in ResponseEntity.ok() (Spring will wrap it in a ResponseEntity automatically.)
    }

C. TODO [Should we convert the dto data to model in controller layer or service layer]:

    In a Spring Boot application, it's generally considered best practice to convert DTOs (Data Transfer Objects) to model entities in the service layer rather than in the controller layer. Here's why:

    Separation of Concerns:
        Controller Layer: The controller is responsible for handling HTTP requests, interacting with the service layer, and returning appropriate HTTP responses. It should be focused on dealing with the web aspect of your application, such as request handling and response formatting.
        Service Layer: The service layer contains the business logic of your application. It's responsible for processing the data, which often involves converting DTOs to entities and vice versa. This keeps your business logic decoupled from the web layer, making the application easier to maintain and test.

    Example Workflow:
        Controller Receives DTO: The controller receives a CreateDto from the client.
        Controller Passes DTO to Service: The controller passes this DTO to the service layer without converting it.
        Service Converts DTO to Model: The service converts the DTO to a model entity, processes it, and performs the necessary business logic.
        Service Returns Result: The service might return a GetDto or another DTO that the controller will use to construct the HTTP response.

 D. TODO [Beans in Spring and their scopes]:

    The @Bean annotation in Java Spring framework typically creates a single instance of a class by default, but there are some nuances to understand:

    Singleton scope: By default, @Bean creates a singleton instance, meaning only one instance of the bean is created and shared across the application context.
    Prototype scope: You can change this behavior by explicitly specifying a different scope:

         @Bean
         @Scope("prototype")
         public MyClass myBean() {
            return new MyClass();
         }
     This will create a new instance every time the bean is requested.

     Other scopes: Spring also supports other scopes like request, session, and application for web applications.
     Configuration class: The class containing the @Bean method should be annotated with @Configuration to ensure proper behavior.
     Method invocation: If a @Bean method is called directly from another @Bean method in the same configuration class, it doesn't create a new instance but returns the existing singleton instance.

 E. TODO [@Transactional]:

     "@Transactional" refers to a declarative way to manage transactions within your application. It is used to ensure that a series of operations within a method are executed within the context of a database transaction, where either all of the operations complete successfully, or none of them are applied (rollback). This is particularly important for maintaining data integrity in case of failures.

     Basic Concept:
         1. Atomicity: If any operation within a transaction fails, all other changes are rolled back, ensuring data consistency.
         2. Isolation: Controls how transactional changes made by one method are visible to others.
         3. Propagation: Defines how transactions behave when they are called within other transactions.
         4. Rollback: Defines conditions under which a transaction should be rolled back.

 F. TODO [Tomcat vs Servlet]:

     Tomcat:
         - Server: Tomcat is a web server and servlet container.
         - Role: It provides the runtime environment for servlets to execute.
         - Implementation: Tomcat implements the Servlet API specification.
         - Scope: It manages the lifecycle of servlets, including loading, initializing, and destroying them.
         - Additional features: Tomcat also supports JSP and WebSocket protocols.

     Servlet:
         - Component: A servlet is a Java class that extends server functionality.
         - Role: It handles client requests and generates responses.
         - API: Servlets are defined by the Servlet API specification.
         - Scope: A servlet focuses on processing specific types of requests.
         - Deployment: Servlets are deployed within a container like Tomcat.

     Relationship:
         - Tomcat hosts and runs servlets.
         - Servlets need a container like Tomcat to function.
         - You can have multiple servlets running in a single Tomcat instance.

     In essence, Tomcat provides the environment and infrastructure, while servlets are the actual Java components that process web requests. It's a "container and contained" relationship.

 G. TODO [MVC (Model-View-Controller) design pattern]:

    1. Model:
        - Represents the data and business logic of the application.
        - In Spring Boot, the model typically consists of Java objects (POJOs) that hold the application’s data, often retrieved from a database.
        - These objects are passed between the Controller and the View.

    2. View:
        - Represents the user interface (UI) of the application.
        - It is responsible for displaying the data to the user, which is provided by the Controller.
        - In Spring Boot, views can be implemented using various technologies, such as Thymeleaf, JSP, or Freemarker. They generate the dynamic web pages that are presented to the user.

    3. Controller:
        - Acts as an intermediary between the Model and the View.
        - Handles incoming HTTP requests, processes them (often by calling service methods to interact with the Model), and returns a view (typically a web page or JSON response).
        - In Spring Boot, controllers are typically annotated with @Controller or @RestController.

    How MVC works in Spring Boot:
        - Client Request: A user makes a request (e.g., to a specific URL).
        - Controller: Spring Boot routes this request to a controller method, which processes the request and interacts with the Model (e.g., by fetching data from a database).
        - Model: The data (model) is prepared, and the Controller adds the data to the view model.
        - View: The Controller returns a view (like an HTML page), which uses the Model to display data to the user.
 **/