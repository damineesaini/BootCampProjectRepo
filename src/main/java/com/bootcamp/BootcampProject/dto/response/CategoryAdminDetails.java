package com.bootcamp.BootcampProject.dto.response;

import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;

import java.util.List;

public class CategoryAdminDetails {
    private List<Category> categoryList;
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesList() {
        return categoryMetadataFieldValuesList;
    }

    public void setCategoryMetadataFieldValuesList(List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList) {
        this.categoryMetadataFieldValuesList = categoryMetadataFieldValuesList;
    }
}
