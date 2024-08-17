// Models - Used to represent the data of the application. In this case, we are creating a Product model to represent the product data. The model is used to interact with the database.
// Entity - An entity represents a table in a relational database, and each entity instance corresponds to a row in that table. Entities are used to interact with the database using an ORM (Object-Relational Mapping) framework like Hibernate.
// Id - The @Id annotation is used to specify the primary key of an entity. In this case, the id field is the primary key of the Product entity.
// Getter and Setter - Lombok annotations used to automatically generate getter and setter methods for the fields of the entity.

package org.example.fakeStore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private String description;
    private double price;
    private String imageUrl;

    @ManyToOne
    private Category category;    // Tell spring the cardinality of the relation between Product and Category for creation of table in the database
}
