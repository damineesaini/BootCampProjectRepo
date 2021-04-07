package com.bootcamp.BootcampProject.dto.request;

import java.util.List;

public class CategoryMetadataFieldValueDto {
    private List<String> fieldValues;

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
