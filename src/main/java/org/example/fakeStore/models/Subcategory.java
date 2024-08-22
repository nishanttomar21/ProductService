package org.example.fakeStore.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Subcategory extends BaseModel {

    private String name;
}
