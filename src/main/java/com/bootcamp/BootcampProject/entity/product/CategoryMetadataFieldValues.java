package com.bootcamp.BootcampProject.entity.product;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "category_metadata_field_value")
public class CategoryMetadataFieldValues {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToOne(targetEntity = CategoryMetadataField.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataFieldId;
    @OneToOne(targetEntity = Category.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @Column(name = "field_values")
    private String values;

    public CategoryMetadataField getCategoryMetadataFieldId() {
        return categoryMetadataFieldId;
    }

    public void setCategoryMetadataFieldId(CategoryMetadataField categoryMetadataFieldId) {
        this.categoryMetadataFieldId = categoryMetadataFieldId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}

/*
 * create table category_metadata_field_values(
 *   category_metadata_field_id int,
 *   category_id int,
 *   values varchar(50)
 * foreign key (category_metadata_field_id)
    references category_metadata_field(id),
    * foreign key (category_id)
    references category(id)
 * );
 * */
