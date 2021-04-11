package com.bootcamp.BootcampProject.entity.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "category_metadata_field_value")
@JsonFilter("metadataValueFilter")
public class CategoryMetadataFieldValues {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_metadata_field_id")
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


