package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldDto;
import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldValueDto;
import com.bootcamp.BootcampProject.dto.response.MetadataFieldResponse;
import com.bootcamp.BootcampProject.dto.response.MetadataValueResponse;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;
import com.bootcamp.BootcampProject.exception.AlreadyExistException;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldRepository;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldValuesRepository;
import com.bootcamp.BootcampProject.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryMetadataFieldService {
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public String addMetadataField(CategoryMetadataFieldDto categoryMetadataFieldDto) throws AlreadyExistException {
        CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findByName(categoryMetadataFieldDto.getName());
        if (categoryMetadataField !=null){
            throw new AlreadyExistException("Metadata field already exist");
        }
        else {
            CategoryMetadataField categoryMetadataField1 = new CategoryMetadataField();
            categoryMetadataField1.setName(categoryMetadataFieldDto.getName());
            categoryMetadataFieldRepository.save(categoryMetadataField1);
            return "Category Metadata added successfully with id: "+ categoryMetadataField1.getId();
        }
    }


    public String addMetadataFieldValue2(CategoryMetadataFieldValueDto categoryMetadataFieldValueDto, UUID id, UUID mtaId) throws DoesNotExistException {
        if(categoryRepository.findById(id).isEmpty()){
            throw new DoesNotExistException("category does not exist");
            }
        else if (categoryMetadataFieldRepository.findById(mtaId).isEmpty()){
            throw new DoesNotExistException("Metadata field not exist");
            }
        else {
            Category category = categoryRepository.findById(id).get();
            CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findById(mtaId).get();
            if (categoryMetadataFieldValuesRepository.findItByCategoryIdAndCategoryMetadataFieldId(category.getId(),categoryMetadataField.getId())==null){
                CategoryMetadataFieldValues categoryMetadataFieldValues = new CategoryMetadataFieldValues();
                System.out.println(categoryMetadataFieldValueDto.getFieldValues());
                String values = String.join(",", categoryMetadataFieldValueDto.getFieldValues());
                System.out.println(values);
                categoryMetadataFieldValues.setCategoryId(category);
                categoryMetadataFieldValues.setValues(values);
                categoryMetadataFieldValues.setCategoryMetadataFieldId(categoryMetadataField);
                categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
                return "Metadata field values added successfully";
            }
            else {
                    List<CategoryMetadataFieldValues> categoryMetadataFieldValues = categoryMetadataFieldValuesRepository.findByCategoryIdAndCategoryMetadataFieldId(category.getId(),categoryMetadataField.getId());
                    String values = String.join(",", categoryMetadataFieldValueDto.getFieldValues());
                    Optional<CategoryMetadataFieldValues> existMetadataValues = categoryMetadataFieldValues.stream().filter(s->s.getValues().equals(values)).findFirst();
                    if (existMetadataValues.isPresent())
                    {
                        return "Values already exist";
                    }
                    else {
                        CategoryMetadataFieldValues updatedCategoryMetadataFieldValues = new CategoryMetadataFieldValues();
                        updatedCategoryMetadataFieldValues.setCategoryId(category);
                        updatedCategoryMetadataFieldValues.setValues(values);
                        categoryMetadataField.addValues(updatedCategoryMetadataFieldValues);
                        updatedCategoryMetadataFieldValues.setCategoryMetadataFieldId(categoryMetadataField);
                        categoryMetadataFieldValuesRepository.save(updatedCategoryMetadataFieldValues);
                        categoryMetadataFieldRepository.save(categoryMetadataField);
                        return "Metadata field values added successfully";
                    }
                }
            }

    }

    @Transactional
    @Modifying
    public String updateMetadataFieldValue(CategoryMetadataFieldValueDto categoryMetadataFieldValueDto,UUID id, UUID mtaId) throws Exception {
        if(categoryRepository.findById(id).isEmpty()){
            throw new Exception("category does not exist");
        }
        else if (categoryMetadataFieldRepository.findById(mtaId).isEmpty()){
            throw new Exception("Metadata field not exist");
        }
        else {
            Category category = categoryRepository.findById(id).get();
            CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findById(mtaId).get();
            if(categoryMetadataFieldValuesRepository.findByCategoryIdAndCategoryMetadataFieldId(category.getId(),categoryMetadataField.getId())!=null){
                List<CategoryMetadataFieldValues> categoryMetadataFieldValues = categoryMetadataFieldValuesRepository.findByCategoryIdAndCategoryMetadataFieldId(category.getId(),categoryMetadataField.getId());
                String values = String.join(",", categoryMetadataFieldValueDto.getFieldValues());
               Optional<CategoryMetadataFieldValues> existMetadataValues = categoryMetadataFieldValues.stream().filter(s->s.getValues().equals(values)).findFirst();
                if (existMetadataValues.isPresent())
                {
                    return "Values already exist";
                }
                else {
                    CategoryMetadataFieldValues updatedCategoryMetadataFieldValues = new CategoryMetadataFieldValues();
                    updatedCategoryMetadataFieldValues.setCategoryId(category);
                    updatedCategoryMetadataFieldValues.setValues(values);
                    categoryMetadataField.addValues(updatedCategoryMetadataFieldValues);
                    updatedCategoryMetadataFieldValues.setCategoryMetadataFieldId(categoryMetadataField);
                    categoryMetadataFieldValuesRepository.save(updatedCategoryMetadataFieldValues);
                    categoryMetadataFieldRepository.save(categoryMetadataField);
                    return "Metadata field values updated successfully";
                }
            }
            else {
                return "Provide category does not have provided category metadata field associated with it. You should add a new category metadata field.";
            }
        }
    }

        public MappingJacksonValue findAllMetadataField(){
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name");
            FilterProvider filters = new SimpleFilterProvider().addFilter("categoryMetadataFilter",filter);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryMetadataFieldRepository.findAll());
            mappingJacksonValue.setFilters(filters);
            return mappingJacksonValue;
        }

    public List<CategoryMetadataFieldValues> findAllMetadataFieldValues(UUID categoryId){
        return categoryMetadataFieldValuesRepository.findByCategoryId(categoryId);
    }

    public List<CategoryMetadataFieldValues> getAllMetadataFieldValues(){
        return (List<CategoryMetadataFieldValues>) categoryMetadataFieldValuesRepository.findAll();
    }

    public List<MetadataValueResponse> findMetadataFieldForCategory(UUID categoryId) throws DoesNotExistException {
        if (categoryMetadataFieldValuesRepository.findByCategoryId(categoryId).isEmpty()){
            throw new DoesNotExistException("metadata for given categoryId doe not exist");
        }
        else {
            List<MetadataValueResponse> metadataValueResponseList =new ArrayList<>();
            MetadataValueResponse metadataValueResponse;
            MetadataFieldResponse metadataFieldResponse;
            List<CategoryMetadataFieldValues> list = categoryMetadataFieldValuesRepository.findByCategoryId(categoryId);
            for (CategoryMetadataFieldValues values:list) {
                UUID metadataFieldId=values.getCategoryMetadataFieldId().getId();
                metadataFieldResponse = new MetadataFieldResponse();
                metadataFieldResponse.setField(categoryMetadataFieldRepository.findById(metadataFieldId).get().getName());
                metadataValueResponse = new MetadataValueResponse();
                metadataValueResponse.setMetadataFieldResponse(metadataFieldResponse);
                metadataValueResponse.setValues(values.getValues());
                metadataValueResponseList.add(metadataValueResponse);
            }
            return metadataValueResponseList;
        }
    }


}

