package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CategoryDto;
import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldDto;
import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldValueDto;
import com.bootcamp.BootcampProject.dto.response.CategoryAdminDetails;
import com.bootcamp.BootcampProject.dto.response.CategoryDetailResponse;
import com.bootcamp.BootcampProject.exception.AlreadyExistException;
import com.bootcamp.BootcampProject.exception.CategoryNotFoundException;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.service.CategoryMetadataFieldService;
import com.bootcamp.BootcampProject.service.CategoryService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    public String addMetadata(@Valid @RequestBody CategoryMetadataFieldDto categoryMetadataFieldDto) throws AlreadyExistException {
        return categoryMetadataFieldService.addMetadataField(categoryMetadataFieldDto);
    }

    @GetMapping("/admin/viewAllMetadata")
    public MappingJacksonValue listAllMetadata(){
        return categoryMetadataFieldService.findAllMetadataField();
    }

    @PostMapping("/admin/addCategory")
    public String addNewCategory(@Valid @RequestBody CategoryDto categoryDto, HttpServletResponse response) throws DoesNotExistException, AlreadyExistException {
        return categoryService.addNewCategory(categoryDto);
    }

    @GetMapping("/admin/viewCategory/{id}")
    public MappingJacksonValue viewSingleCategory(@PathVariable String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        CategoryAdminDetails categoryAdminDetails = new CategoryAdminDetails();
        categoryAdminDetails.setCategoryList(categoryService.viewCategory(uuid));
        categoryAdminDetails.setCategoryMetadataFieldValuesList(categoryMetadataFieldService.findAllMetadataFieldValues(uuid));
        SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","hasChild","isActive","parentCategoryId");
        SimpleBeanPropertyFilter filterValues = SimpleBeanPropertyFilter.filterOutAllExcept("categoryMetadataFieldId","values");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("categoryMetadataFilter",filter).addFilter("metadataValueFilter",filterValues).addFilter("categoryFilter",filterCategory);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryAdminDetails);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/admin/allcategory")
    public MappingJacksonValue listAllCategory(){
        CategoryAdminDetails categoryAdminDetails = new CategoryAdminDetails();
        categoryAdminDetails.setCategoryList(categoryService.findAll());
        categoryAdminDetails.setCategoryMetadataFieldValuesList(categoryMetadataFieldService.getAllMetadataFieldValues());
        SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","hasChild","isActive","parentCategoryId");
        SimpleBeanPropertyFilter filterValues = SimpleBeanPropertyFilter.filterOutAllExcept("categoryMetadataFieldId","values");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("categoryMetadataFilter",filter).addFilter("metadataValueFilter",filterValues).addFilter("categoryFilter",filterCategory);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryAdminDetails);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @PatchMapping("/admin/updateCategory/{categoryId}")
    public String updateCategory(@PathVariable String categoryId, @RequestParam("name") String category, HttpServletResponse response) throws DoesNotExistException, AlreadyExistException {
        return categoryService.updateCategory(UUID.fromString(categoryId),category);
    }

    @PostMapping("/admin/add-metadata-values")
    public String addNewMetadataValues2(@Valid @RequestBody CategoryMetadataFieldValueDto categoryMetadataFieldValueDto, @Param("id") String id, @Param("mtaId") String mtaId) throws Exception, DoesNotExistException, AlreadyExistException {
        return categoryMetadataFieldService.addMetadataFieldValue2(categoryMetadataFieldValueDto,UUID.fromString(id),UUID.fromString(mtaId));
    }

    @PutMapping("/admin/update-metadata-values")
    public String updateNewMetadataValues(@Valid @RequestBody CategoryMetadataFieldValueDto categoryMetadataFieldValueDto, @Param("id") String id,@Param("mtaId") String mtaId) throws Exception {
        return categoryMetadataFieldService.updateMetadataFieldValue(categoryMetadataFieldValueDto,UUID.fromString(id),UUID.fromString(mtaId));
    }

    /********************************** Seller API ************************************/

    @GetMapping("/seller/view-allcategory")
    public MappingJacksonValue listAllCategories(){

        SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","parentCategoryId");
        SimpleBeanPropertyFilter filterValues = SimpleBeanPropertyFilter.filterOutAllExcept("categoryMetadataFieldId","values","categoryId");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("categoryFilter",filterCategory).addFilter("categoryMetadataFilter",filter).addFilter("metadataValueFilter",filterValues);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryMetadataFieldService.getAllMetadataFieldValues());
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    /********************************** Customer API *************************************/

        @GetMapping("/customer/view-all-categories")
    public Object listAllSubCategory(@Param("id") String id) throws Exception {
            SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","parentCategoryId");
            FilterProvider filters = new SimpleFilterProvider().addFilter("categoryFilter",filterCategory);
            if (id.equals("")){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryService.findAllCategory());
                mappingJacksonValue.setFilters(filters);
                return mappingJacksonValue;
            }
        else{
                UUID categoryId = UUID.fromString(id);
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryService.findAllSubCategory(categoryId));
                mappingJacksonValue.setFilters(filters);
                return mappingJacksonValue;
        }
    }

    @GetMapping("/customer/viewCategoryDetails/{id}")
    public MappingJacksonValue viewSingleCategoryDetails(@PathVariable String id) throws CategoryNotFoundException, DoesNotExistException {
        UUID uuid = UUID.fromString(id);
        CategoryDetailResponse categoryDetailResponse = new CategoryDetailResponse();
        categoryDetailResponse.setMetadata(categoryMetadataFieldService.findMetadataFieldForCategory(uuid));
        categoryDetailResponse.setBrand(categoryService.findBrandListForCategory(uuid));
        categoryDetailResponse.setPrice(categoryService.findPrice(uuid));
        SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isCancellable","isReturnable");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("quantityAvailable","price","productId","productMetadata","productImage");
        FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("productVariationFilter",filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryDetailResponse);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

}
