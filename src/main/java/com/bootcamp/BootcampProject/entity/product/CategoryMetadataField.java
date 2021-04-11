package com.bootcamp.BootcampProject.entity.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category_metadata_field")
@JsonFilter("categoryMetadataFilter")
public class CategoryMetadataField {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_field_id")
    @JsonProperty
    private Set<CategoryMetadataFieldValues> values;

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

    public Set<CategoryMetadataFieldValues> getValues() {
        return values;
    }

    public void setValues(Set<CategoryMetadataFieldValues> values) {
        this.values = values;
    }

    public void addValues(CategoryMetadataFieldValues categoryMetadataFieldValues) {
        if (categoryMetadataFieldValues !=null) {
            if (values==null) {
                values = new HashSet<>();
            }
            categoryMetadataFieldValues.setCategoryMetadataFieldId(this);
            values.add(categoryMetadataFieldValues);
        }
    }
}
