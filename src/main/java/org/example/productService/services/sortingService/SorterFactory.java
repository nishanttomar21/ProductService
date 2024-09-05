// Implemented sorting as a factory method pattern because it's a common pattern for creating objects based on a key.

package org.example.productService.services.sortingService;

import org.example.productService.dtos.search.SortingCriteria;

public class SorterFactory {

    public static Sorter getSorterByCriteria(SortingCriteria sortingCriteria) {
        return switch (sortingCriteria) {
            case RELEVANCE -> null;
            case POPULARITY -> null;
            case PRICE_HIGH_TO_LOW -> new PriceHighToLowSorter();
            case PRICE_LOW_TO_HIGH -> new PriceLowToHighSorter();
            case RATING_HIGH_TO_LOW -> null;
            case RATING_LOW_TO_HIGH -> null;
            case null -> null;
        };
    }
}
