// Important: We can use CreateProductResponseDto class for this purpose. But it is not a good practice to use the same class for different purposes. So, we have created a new class GetProductResponseDto for this purpose.

package org.example.fakeStore.dtos.product;

import lombok.Getter;
import lombok.Setter;
import org.example.fakeStore.models.Product;

@Getter
@Setter
public class GetProductResponseDto {
    private GetProductDto product;
}
