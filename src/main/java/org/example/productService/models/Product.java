// Models - Used to represent the data of the application. In this case, we are creating a Product model to represent the product data. The model is used to interact with the database.
// Entity - An entity represents a table in a relational database, and each entity instance corresponds to a row in that table. Entities are used to interact with the database using an ORM (Object-Relational Mapping) framework like Hibernate.
// @Id - This annotation is used to specify the primary key of an entity. In this case, the id field is the primary key of the Product entity.
// Getter and Setter - Lombok annotations used to automatically generate getter and setter methods for the fields of the entity.
// @NoArgsConstructor - When you apply this annotation to a class, Lombok automatically generates a no-argument constructor for that class during compilation.
// @AllArgsConstructor - When you apply this annotation to a class, Lombok automatically generates a constructor that includes all fields of the class as parameters during compilation.
// @JoinColumn - It is used to specify the foreign key column that will be used to join two entities in a relationship. It is typically applied in associations such as @ManyToOne, @OneToOne, and @OneToMany.

package org.example.productService.models;

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
    private Double price;
    private String imageUrl;

    @ManyToOne
    private Category category;    // Tell spring the cardinality of the relation between Product and Category for creation of table in the database
}
