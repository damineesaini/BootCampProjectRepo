package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;

import java.util.List;

public class CategoryMetadataFieldValueDto {
    private List<CategoryMetadataFieldDto> fieldValues;

    public List<CategoryMetadataFieldDto> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<CategoryMetadataFieldDto> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
