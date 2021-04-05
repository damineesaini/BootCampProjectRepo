package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.entity.product.Category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CategoryDto {
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String name;
    private Category parentCategoryId;

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
