package org.example.productService.services.filteringService;

import org.example.productService.models.Product;

import java.util.List;

public class RamFilter implements Filter {

    @Override
    public List<Product> applyFilter(List<Product> products, List<String> filteredValues) {
        // Implement the filtering logic for RAM here
        // For example, you can filter products based on RAM size

        return List.of();
    }
}
