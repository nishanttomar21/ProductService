// JPA Query Methods are defined in the repository interface. They are defined by appending the method name with the query keyword. The query keyword is followed by the entity name. Example: findByName(String name) - This method will find the entity by its name.
// UPSERT is a database operation that combines the words "update" and "insert" to update an existing row in a table if a specified value exists, or insert a new row if it doesn't.
// Optional<Object> is a container object that may or may not contain a non-null value. If a value is present, isPresent() will return true and get() will return the value.

package org.example.fakeStore.repositories;

import org.example.fakeStore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Override
    Category save(Category category);
}
