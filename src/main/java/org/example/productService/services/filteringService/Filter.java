package org.example.productService.services.filteringService;

import org.example.productService.models.Product;

import java.util.List;

public interface Filter {

    List<Product> applyFilter(List<Product> products, List<String> filteredValues);
}
