// Always create these 2 Generic DTOs - createModalDto and getModalDto like createProductDto, getProductDto for each model as a good coding practice and keeping using that in other classes.

package org.example.fakeStore.dtos.product;

import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Category;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class CreateProductDto {
    private Long id;    // New field
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String categoryName;

    // Convert the Product model to a DTO
    public static CreateProductDto fromProduct(Product product) {
        CreateProductDto createProductDto = new CreateProductDto();

        createProductDto.setId(product.getId());
        createProductDto.setTitle(product.getTitle());
        createProductDto.setDescription(product.getDescription());
        createProductDto.setPrice(product.getPrice());
        createProductDto.setImageUrl(product.getImageUrl());
        //createProductDto.setCategoryName(product.getCategory().getName());

        return createProductDto;
    }

    public Product toProduct() {
        Product product = new Product();

        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.imageUrl);

        Category category = new Category();
        category.setName(this.categoryName);
        product.setCategory(category);

        return product;
    }
}
