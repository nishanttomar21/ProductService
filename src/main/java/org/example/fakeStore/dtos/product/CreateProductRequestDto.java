// DTOS - (Data Transfer Object) Used to send data between the client/APIs and the server without potentially exposing sensitive information. They are used to define the structure of the data that will be sent in the request body.
// Basically when you are sending/getting something from outside, you should use DTOs to avoid exposing sensitive information.
/*
    In a typical Spring Boot application:
        The controller layer receives requests and maps them to DTOs.
        The service layer maps the DTOs to models, performs business logic, and interacts with the repository layer.
        The repository layer interacts with the database using models.
        The response is mapped back to DTOs before being sent to the client.

    Using DTOs helps in:
        Hiding the internal structure of domain models
        Providing a specific view of data for each client
        Reducing the amount of data transferred between layers
        Avoiding over-fetching or under-fetching of data
 */

package org.example.fakeStore.dtos.product;


import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class CreateProductRequestDto {
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;

    // Convert the DTO to a Product model
    public Product toProduct() {
        Product product = new Product();

        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.imageUrl);
        product.setCategoryName(this.category);

        return product;
    }
}
