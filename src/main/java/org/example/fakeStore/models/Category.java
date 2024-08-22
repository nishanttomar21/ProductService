// Entity - An entity represents a table in a relational database, and each entity instance corresponds to a row in that table. Entities are used to interact with the database using an ORM (Object-Relational Mapping) framework like Hibernate.
// Cascade:
//      It is recommended not to use cascade in your entities/tables. Instead, use the service layer to manage the cascading operations as this will be much easy to debug the code, more verbose. This will give you more control over the cascading operations and prevent unwanted cascading operations.
//      Cascade should be recursively applied to all the entities in the relation. If you have a relation between two entities, and you want to apply cascade on one entity, you should apply cascade on the other entity as well.
//      CascadeType.PERSIST - It ensures that when a parent entity is persisted (saved), all related child entities marked with CascadeType.PERSIST are also saved automatically.
//      CascadeType.REMOVE - It ensures that when a parent entity is removed (deleted), all related child entities marked with CascadeType.REMOVE are also removed automatically.
//      CascadeType.MERGE - It ensures that when a parent entity is merged (updated), all related child entities marked with CascadeType.MERGE are also merged automatically.
//      CascadeType.DETACH - It ensures that when a parent entity is detached (disconnected from the persistence context), all related child entities marked with CascadeType.DETACH are also detached automatically.
//      CascadeType.REFRESH - It ensures that when a parent entity is refreshed (reloaded/updated from the database), all related child entities marked with CascadeType.REFRESH are also refreshed automatically.
//      CascadeType.ALL - It ensures that all the above cascade operations are applied to the parent entity.
//      CascadeType.NONE - It ensures that no cascade operations are applied to the parent entity. Default Cascade Type is NONE (cascade = {}).

package org.example.fakeStore.models;

import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "category" )   // category attribute of Product is representing this relation; [Important] If same relation is represented from both the classes, Spring may end up representing the relation twice in the database. To avoid this, we need to tell Spring that they are the same relation. This can be done by using the mappedBy attribute in the @OneToMany annotation to tell that it has already been marked by someone else.
    // @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})  // Example of cascade
    private List<Product> allProducts;

    @OneToOne(cascade = {})
    private Subcategory subcategory;
}
