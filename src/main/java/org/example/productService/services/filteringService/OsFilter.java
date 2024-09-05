package org.example.productService.services.filteringService;

import org.example.productService.models.Product;

import java.util.List;

public class OsFilter implements Filter {

    @Override
    public List<Product> applyFilter(List<Product> products, List<String> filteredValues) {
        // Implement the filtering logic for OS here
        // For example, filter products based on the Operating System names

        return List.of();
    }
}
