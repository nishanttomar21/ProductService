// According to FakeStore API, this request body is for creating data according to FakeStore needs

package org.example.fakeStore.dtos.fakestore;

import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class FakeStoreCreateProductRequestDto {
    private String title;
    private double price;
    private String image;
    private String description;
    private String category;

    public static FakeStoreCreateProductRequestDto fromProduct(Product product) {
        FakeStoreCreateProductRequestDto dto = new FakeStoreCreateProductRequestDto();

        dto.title = product.getTitle();
        dto.price = product.getPrice();
        dto.image = product.getImageUrl();
        dto.description = product.getDescription();
        dto.category = product.getCategory().getName();

        return dto;
    }
}