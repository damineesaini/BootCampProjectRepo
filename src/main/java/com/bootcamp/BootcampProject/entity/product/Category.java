package com.bootcamp.BootcampProject.entity.product;

import javax.persistence.*;
import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_category_id")
    private Category parentCategoryId;

    @OneToMany(mappedBy="parentCategoryId",cascade = CascadeType.ALL)
    private Set<Category> childCategory = new HashSet<Category>();

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

    public Set<Category> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(Set<Category> childCategory) {
        this.childCategory = childCategory;
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


