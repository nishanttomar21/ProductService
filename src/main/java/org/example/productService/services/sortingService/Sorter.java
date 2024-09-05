package org.example.productService.services.sortingService;

import org.example.productService.models.Product;
import java.util.List;

public interface Sorter {
    List<Product> applySorting(List<Product> products);
}
