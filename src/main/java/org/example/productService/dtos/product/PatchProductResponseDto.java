package org.example.productService.dtos.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchProductResponseDto {
    private GetProductDto product;
    private String errorMessage;
}
