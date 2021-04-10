package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CategoryDto;
import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldDto;
import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldValueDto;
import com.bootcamp.BootcampProject.dto.response.CategoryDetailResponse;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import com.bootcamp.BootcampProject.exception.AlreadyExistException;
import com.bootcamp.BootcampProject.exception.CategoryNotFoundException;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.service.CategoryMetadataFieldService;
import com.bootcamp.BootcampProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    /********************************** Admin API ************************************/

    @PostMapping("/admin/add-metadata-field")
    public String addMetadata(@RequestBody CategoryMetadataFieldDto categoryMetadataFieldDto) throws AlreadyExistException {
        return categoryMetadataFieldService.addMetadataField(categoryMetadataFieldDto);
    }

    @GetMapping("/admin/viewAllMetadata")
    public List<CategoryMetadataField> listAllMetadata(){
        return categoryMetadataFieldService.findAllMetadataField();
    }

    @PostMapping("/admin/addCategory")
    public String addNewCategory(@RequestBody CategoryDto categoryDto, HttpServletResponse response) throws DoesNotExistException, AlreadyExistException {
        return categoryService.addNewCategory(categoryDto);
    }

    @GetMapping("/admin/viewCategory/{id}")
    public Category viewSingleCategory(@PathVariable String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        return categoryService.viewCategory(uuid);
    }

    @GetMapping("/admin/allcategory")
    public List<Category> listAllCategory(){
        return categoryService.findAll();
    }

    @PatchMapping("/admin/updateCategory/{categoryId}")
    public String updateCategory(@PathVariable String categoryId, @RequestParam("name") String category, HttpServletResponse response) throws DoesNotExistException, AlreadyExistException {
        return categoryService.updateCategory(UUID.fromString(categoryId),category);
    }

    @PostMapping("/admin/add-metadata-values")
    public String addNewMetadataValues2(@RequestBody CategoryMetadataFieldValueDto categoryMetadataFieldValueDto, @Param("id") String id, @Param("mtaId") String mtaId) throws Exception, DoesNotExistException, AlreadyExistException {
        return categoryMetadataFieldService.addMetadataFieldValue2(categoryMetadataFieldValueDto,UUID.fromString(id),UUID.fromString(mtaId));
    }

    @PutMapping("/admin/update-metadata-values")
    public String updateNewMetadataValues(@RequestBody CategoryMetadataFieldValueDto categoryMetadataFieldValueDto, @Param("id") String id,@Param("mtaId") String mtaId) throws Exception {
        return categoryMetadataFieldService.updateMetadataFieldValue(categoryMetadataFieldValueDto,UUID.fromString(id),UUID.fromString(mtaId));
    }

    @GetMapping("/admin/view-all-category")
    public List<CategoryMetadataField> listAllCategoryDetails(){
        return categoryMetadataFieldService.findAllMetadataField();
    }

    /********************************** Seller API ************************************/

    @GetMapping("/seller/view-allcategory")
    public List<Category> listAllCategories(){
        return categoryService.findAllCategory();
    }

    /********************************** Customer API ************************************/

        @GetMapping("/customer/view-all-categories")
    public List<Category> listAllSubCategory(@Param("id") String id) throws Exception {
        if (id.equals(""))
            return categoryService.findAllCategory();
        else{
        UUID categoryId = UUID.fromString(id);
        return categoryService.findAllSubCategory(categoryId);}
    }

    @GetMapping("/customer/viewCategoryDetails/{id}")
    public CategoryDetailResponse viewSingleCategoryDetails(@PathVariable String id) throws CategoryNotFoundException, DoesNotExistException {
        UUID uuid = UUID.fromString(id);
        CategoryDetailResponse categoryDetailResponse = new CategoryDetailResponse();
        categoryDetailResponse.setMetadata(categoryMetadataFieldService.findMetadataFieldForCategory(uuid));
        categoryDetailResponse.setBrand(categoryService.findBrandListForCategory(uuid));
        categoryDetailResponse.setPrice(categoryService.findPrice(uuid));
        return categoryDetailResponse;
    }

}
