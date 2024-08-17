// Always create these 2 Generic DTOs - createModalDto and getModalDto like createProductDto, getProductDto for each model as a good coding practice and keeping using that in other classes.

package org.example.fakeStore.dtos.product;

import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class GetProductDto {
    private Long id;    // New field
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;

    public static GetProductDto fromProduct(Product product) {
        GetProductDto getProductDto = new GetProductDto();

        getProductDto.setId(product.getId());
        getProductDto.setTitle(product.getTitle());
        getProductDto.setDescription(product.getDescription());
        getProductDto.setPrice(product.getPrice());
        getProductDto.setImageUrl(product.getImageUrl());
        getProductDto.setCategory(product.getCategoryName());

        return getProductDto;
    }
}
