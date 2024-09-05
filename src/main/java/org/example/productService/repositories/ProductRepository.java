// Repository is an interface that extends JpaRepository<Model, Primary key datatype> to perform CRUD operations on the Model. It is used to interact with the database
// @Repository annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects
// Service --> Repository --> ORM(JPA, JDBC) --> Database
// JpaRepository<Model, Primary key datatype> is an interface that extends the PagingAndSortingRepository<Model, Primary key datatype> which in turn extends the CrudRepository<Model, Primary key datatype>
// ORM (Object-Relational Mapping) is a programming technique that maps the object-oriented domain model (Java objects) to a relational database (Tables)
// Optional<Object> is a container object that may or may not contain a non-null value. If a value is present, isPresent() will return true and get() will return the value.
// 3 ways to Query the database:
//      1. JPA Query Methods - Define the method in the repository interface by appending the method name with the query keyword followed by the entity name. Example: findByName(String name) - This method will find the entity by its name.
//      2. @Query Annotation - Define the query using JPQL (Java Persistence Query Language) in the repository interface. Example: @Query("SELECT c FROM Category c WHERE c.name = :name") - This method will find the entity by its name. You are writing the query in object-oriented way using java objects.
//      3. Native Query - Define the query using native SQL in the repository interface. Example: @Query(value = "SELECT * FROM categories WHERE name = :name", nativeQuery = true) - This method will find the entity by its name. SQL query directly runs on the database.
// When your system/codebase start to become large/complex, then companies generally start to transition from ORM to directly writing Native queries. ORM is good for small projects because it is easy to use and understand. It is also good for rapid development. But for large projects, ORM can be slow and inefficient. Native queries are faster and more efficient for large projects.

package org.example.productService.repositories;

import org.example.productService.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Create and Update operations is done using save()
    // If the product you try to save has an ID:
    // JPA will see if a product with that ID exists:
    //      If no -> Insert
    //      If yes -> Update

    // 1. JPA Query Methods
    @Override
    Product save(Product product);

    @Override
    void delete(Product product);

    @Override
    List<Product> findAll();

    @Override
    Optional<Product> findById(Long id);

    Optional<List<Product>> findAllByTitleContaining(String title); // Find all products by title containing a given string

    Page<Product> findAllByTitleContainingAndCategory_Id(String title, Long categoryId, Pageable pageable); // Find all products by title containing a given string and category id and also applies pagination to the result set based on the Pageable parameter.
    //Page<Product> findAllByTitleContainingAndCategory_IdOrderBy(String title, Long categoryId, Sort sort); // Find all products by title containing a given string and category id and also applies sorting to the result

    List<Product> findAllByCategory_Subcategory_NameEquals(String subcategoryName);     // JPA Query Methods(attribute of attribute)  - find all products by subcategory name

    // 2. JPQL - Java Persistence Query Language
    // select * from products p where p.price = ?
    @Query("SELECT p FROM Product p WHERE p.price > :productPrice")   // productPrice is a variable (named parameter) that will be replaced by the value passed in the method
    List<Product> JPQLFunction1(@Param("productPrice") Double productPrice);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.category.subcategory.name = :subcategoryName")  // subcategoryName is a variable (named parameter) that will be replaced by the value passed in the method
    List<Product> JPQLFunction2(@Param("subcategoryName") String subcategoryName);

    // 3. Native Query - SQL (Warning: Don't use SQL queries unless you have to as it can lead to SQL injection attacks)
    @Query(value = CustomQueries.GET_PRODUCTS_WITH_SUBCATEGORY_NAME, nativeQuery = true)  // nativeQuery = true tells Spring that this is a native SQL query
    List<Product> nativeQueryFunction();
}