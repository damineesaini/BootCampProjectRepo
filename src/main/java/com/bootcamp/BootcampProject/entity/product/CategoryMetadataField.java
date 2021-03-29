package com.bootcamp.BootcampProject.entity.product;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "category_metadata_field")
public class CategoryMetadataField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/*
* create table category_metadata_field(
*   id int,
*   name varchar(20)
* );*/