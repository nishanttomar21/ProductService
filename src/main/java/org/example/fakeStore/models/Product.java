// Models - Used to represent the data of the application. In this case, we are creating a Product model to represent the product data. The model is used to interact with the database.

package org.example.fakeStore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;    // New field
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String categoryName;    // Changed from category to categoryName
}
