package org.example.productService.services.filteringService;

import org.example.productService.models.Product;

import java.util.List;

public class BrandFilter implements Filter {

    @Override
    public List<Product> applyFilter(List<Product> products, List<String> filteredValues) {
        // Implement the filtering logic for Brand here
        // For example, filter products by a specific brand

        return List.of();
    }
}
