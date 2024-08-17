/*
 Base Model - A common practice among developers to create a base class or interface for their domain models (entities) to share common properties and behaviors.
 Id - The @Id annotation is used to specify the primary key of an entity. In this case, the id field is the primary key of the Product entity.

 Why Create a Base Model?
    Code Reusability: Avoids redundant code by defining shared attributes like creation/modification timestamps, versioning, or other common fields.
    Consistent Structure: Enforces a uniform structure across your domain models.
    Additional Functionality: Can encapsulate common logic or methods that multiple entities might need.

    MappedSuperclass - So that classes that implements this class (Child Classes) will be able to use its attributes and methods.

    [Important] Don't create a table fot Base Model class in the database. It's just a common class to be extended by other classes.
*/

package org.example.fakeStore.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    private Long id;
    private Date createdAt;
    private Date lastModifiedAt;
    private boolean isDeleted;
}
