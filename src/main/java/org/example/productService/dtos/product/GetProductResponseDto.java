// Important: We can use CreateProductResponseDto class for this purpose. But it is not a good practice to use the same class for different purposes. So, we have created a new class GetProductResponseDto for this purpose.

package org.example.productService.dtos.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductResponseDto {
    private GetProductDto product;
}
