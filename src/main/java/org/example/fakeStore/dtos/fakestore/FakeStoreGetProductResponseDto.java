package org.example.fakeStore.dtos.fakestore;

import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class FakeStoreGetProductResponseDto {
    private Long id;    // New field
    private String title;
    private double price;
    private String image;
    private String description;
    private String category;

    // Convert the DTO to a Product model
    public Product toProduct() {
        Product product = new Product();

        product.setId(this.id);
        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.image);
        product.setCategoryName(this.category);

        return product;
    }
}