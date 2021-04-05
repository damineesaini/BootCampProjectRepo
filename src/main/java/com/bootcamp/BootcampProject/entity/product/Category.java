package com.bootcamp.BootcampProject.entity.product;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_category_id")
    private Category parentCategoryId;

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

    public Category getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Category parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

}


/*
 * create table category(
 *   id int,
 *   name varchar(20),
 *   parent_category_id int,
 *   foreign key (parent_category_id)
 *   references category(id)
 * );*/


