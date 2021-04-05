package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CategoryDto;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import com.bootcamp.BootcampProject.service.CategoryMetadataFieldService;
import com.bootcamp.BootcampProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryMetadataFieldService categoryMetadataFieldService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/allcategory")
    public List<Category> listAllCategory(){
        return categoryService.findAll();
    }

    @GetMapping("/allsubcategory")
    public List<Category> listAllSubCategory(){
        return categoryService.findAllSubCategory();
    }

    @GetMapping("/allmetadatafield")
    public List<CategoryMetadataField> findAllMetadataField(){
        return categoryMetadataFieldService.findAllMetadataField();
    }

    @PostMapping("/addCategory")
    public String addNewCategory(@RequestBody CategoryDto categoryDto, HttpServletResponse response){
        return categoryService.addNewCategory(categoryDto);
    }

    @GetMapping("/viewCategory/{id}")
    public Category viewSingleCategory(@PathVariable String id, HttpServletResponse response) throws Exception {
        UUID uuid = UUID.fromString(id);
        return categoryService.viewCategory(uuid);
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestBody CategoryDto categoryDto, @RequestParam("name") String category, HttpServletResponse response){
        return categoryService.updateCategory(categoryDto,category);
    }
}
