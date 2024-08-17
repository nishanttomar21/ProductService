package org.example.fakeStore.dtos.product;

import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class CreateProductResponseDto {
    private CreateProductDto product;
}
