package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CategoryMetadataFieldValueDto {
    @NotNull(message = "fieldValues should not be null!!")
    private List<String> fieldValues;

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
