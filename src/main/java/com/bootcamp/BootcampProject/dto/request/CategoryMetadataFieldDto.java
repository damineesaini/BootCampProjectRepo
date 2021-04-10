package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CategoryMetadataFieldDto {
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
