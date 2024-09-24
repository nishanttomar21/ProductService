// Projections - It refers to a technique used with Spring Data JPA to efficiently retrieve partial data from database entities. Instead of fetching entire entities, projections allow you to select only specific fields, reducing the amount of data transferred and potentially improving performance.

package org.example.productService.repositories.projections;

public interface ProductWithIdAndTitle {
    Long getId();

    String getTitle();

//    String getPrice();
}
