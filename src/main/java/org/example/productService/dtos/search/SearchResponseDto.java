package org.example.productService.dtos.search;

import lombok.Getter;
import lombok.Setter;
import org.example.productService.dtos.product.GetProductDto;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class SearchResponseDto {
//    private List<GetProductDto> productsPage;
//    private int pageNumber;
//    private int pageSize;

    private Page<GetProductDto> productsPage; // Page object will take care of the pagination attributes and methods for us
}
