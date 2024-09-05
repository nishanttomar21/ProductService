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
// FetchType - (when will an attribute be fetched) It is used to specify the fetching strategy to retrieve the related entities. Used when you have a relation between two entities. There are two types of FetchType:
//      FetchType.LAZY - It fetches the related entities lazily, i.e., only when they are requested. When you try to get object of class, the value of that attribute will be fetched when accessed. It is the default fetch type for @OneToMany and @ManyToMany relationships. By default Collections(List, Set, Map) are lazy fetchType, collections leads to calling joins in SQL. It means that when you load an entity, the collection is not loaded. It is loaded only when you try to access it. Mostly for Collections you use LAZY and only use EAGER when the no. of items are less and those items are almost needed together along with the object of the parent class.
//      FetchType.EAGER - It fetches the related entities eagerly, i.e., at the time of fetching the parent entity. When you try to get object of class, the value of all the attributes will be fetched. It is the default fetch type for @OneToOne and @ManyToOne relationships.
//      FetchType.LAZY is more efficient than FetchType.EAGER as it loads the related entities only when they are needed. However, FetchType.LAZY can lead to LazyInitializationException if the related entities are accessed outside the transaction boundary.
//      FetchType.EAGER can lead to performance issues as it fetches all the related entities even if they are not needed. It is recommended to use FetchType.LAZY for @OneToMany and @ManyToMany relationships to avoid performance issues.
// FetchMode - (how will an attribute be fetched) It is used to specify the fetching mode to retrieve the related entities. Used when you have a relation between two entities. There are three types of FetchMode:
//      FetchMode.SELECT - It fetches the related entities using a separate SELECT query. It is the default fetch mode for @OneToMany and @ManyToMany relationships. It means that when you load an entity, the collection is not loaded. It is loaded only when you try to access it.
//      FetchMode.SUBSELECT - It fetches the related entities using a separate SELECT query with a subquery statement. It is used to fetch the related entities in a single query. It is more efficient than FetchMode.SELECT as it reduces the number of queries executed. It is recommended to use FetchMode.SUBSELECT for @OneToMany and @ManyToMany relationships to improve performance.
//      FetchMode.JOIN - It fetches the related entities using a JOIN query. It is the default fetch mode for @OneToOne and @ManyToOne relationships. It means that when you load an entity, the collection is loaded along with it. It is loaded even if you do not access it.
// FetchMode.SELECT is more efficient than FetchMode.JOIN as it loads the related entities using a separate SELECT query only when they are needed. However, FetchMode.SELECT can lead to N+1 query issues if the related entities are accessed in a loop. FetchMode.JOIN fetches all the related entities using a JOIN query even if they are not needed. It is recommended to use FetchMode.SELECT for @OneToMany and @ManyToMany relationships to avoid N+1 query issues.
// FetchType.LAZY and FetchMode.SELECT are used together to fetch the related entities lazily using a separate SELECT query only when they are needed. FetchType.EAGER and FetchMode.JOIN are used together to fetch the related entities eagerly using a JOIN query even if they are not needed.

package org.example.productService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    @Column(nullable = false, unique = true, name = "category_name")  // Column annotation is used to specify the column details of the entity. In this case, we are specifying that the name column is not nullable (validation, cannot be empty) and unique. We are also specifying the name of the column in the database as category_name.
    private String name;

    @Basic(fetch = FetchType.LAZY)
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Product> featuredProducts;

    @OneToMany(mappedBy = "category")   // Collections by default FetchType = LAZY
    @Fetch(FetchMode.JOIN)
    // category attribute of Product is representing this relation; [Important] If same relation is represented from both the classes, Spring may end up representing the relation twice in the database. To avoid this, we need to tell Spring that they are the same relation. This can be done by using the mappedBy attribute in the @OneToMany annotation to tell that it has already been marked by someone else.
    // @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})  // Example of cascade
    private List<Product> allProducts;

    @OneToOne(cascade = {})
    private Subcategory subcategory;

    private int countOfProducts;
}
