package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CategoryDto {
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String name;
    private String parentCategoryName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCategoryId() {
        return parentCategoryName;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryName = parentCategoryId;
    }
}
