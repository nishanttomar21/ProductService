// Implemented search by filtering as a factory method pattern because it's a common pattern for creating objects based on a key.

package org.example.productService.services.filteringService;

public class FilterFactory {

    public static Filter getFilterFromKey(String key) {
        return switch (key) {
            case "brand" -> new BrandFilter();
            case "os" -> new OsFilter();
            case "ram" -> new RamFilter();
            default -> throw new IllegalArgumentException("Invalid filter key: " + key);
        };
    }
}
