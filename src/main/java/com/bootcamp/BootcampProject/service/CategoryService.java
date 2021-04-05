package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CategoryDto;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return (List<Category>) categoryRepository.findAll();
    }

    public List<Category> findAllSubCategory(){
        return categoryRepository.findAllChildCategory();
    }

    public String addNewCategory(CategoryDto categoryDto){
        if(categoryRepository.findByName(categoryDto.getName()) != null){
            return "Category already exists";
        }
        else {
            Category category = new Category();
            category.setName(categoryDto.getName());
            category.setParentCategoryId(categoryDto.getParentCategoryId());
            categoryRepository.save(category);
            return  "category saved successfully";
        }
    }

    public Category viewCategory(UUID id) throws Exception {
        if (categoryRepository.findById(id).isPresent()){
            return categoryRepository.findById(id).get();
        }
        else {
            throw new Exception("invalid id");
        }
    }

    public String updateCategory(CategoryDto categoryDto,String category){
        if(categoryRepository.findByName(category) !=null)
        {
            Category updateCategory = categoryRepository.findByName(category);
            updateCategory.setParentCategoryId(categoryDto.getParentCategoryId());
            updateCategory.setName(categoryDto.getName());
            categoryRepository.save(updateCategory);
            return "updated successfully";
        }
        else{
            return "invalid category";
        }
    }
}
