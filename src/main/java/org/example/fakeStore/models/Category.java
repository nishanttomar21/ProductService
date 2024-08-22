// Entity - An entity represents a table in a relational database, and each entity instance corresponds to a row in that table. Entities are used to interact with the database using an ORM (Object-Relational Mapping) framework like Hibernate.

package org.example.fakeStore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String name;
    private String description;

    @OneToMany
    private List<Product> featuredProducts;

    @OneToMany(mappedBy = "category")   // category attribute of Product is representing this relation; [Important] If same relation is represented from both the classes, Spring may end up representing the relation twice in the database. To avoid this, we need to tell Spring that they are the same relation. This can be done by using the mappedBy attribute in the @OneToMany annotation to tell that it has already been marked by someone else.
    private List<Product> allProducts;

    @OneToOne
    private Subcategory subcategory;
}
