package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CategoryMetadataFieldValueDto;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldRepository;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldValuesRepository;
import com.bootcamp.BootcampProject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CategoryMetadataFieldService {
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public String addMetadataField(String name) throws Exception {
        CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findByName(name);
        if (categoryMetadataField !=null){
            throw new Exception("Metadata field already exist");
        }
        else {
            CategoryMetadataField categoryMetadataField1 = new CategoryMetadataField();
            categoryMetadataField1.setName(name);
            categoryMetadataFieldRepository.save(categoryMetadataField1);
            return "Category Metadata added successfully";
        }
    }

    private String toCommaSeperatedString(List<String> value) {
        String values = "";
        if (value.isEmpty()) return values;
        else {
            for (String valu : value) {
                values = values + valu + ",";
            }
            values = values.substring(0, values.length() - 1);
            return values;
        }
    }

    private Set<String> toSetOfValues(String value) {
        Set<String> values = new HashSet<>();
        String[] splitValue = value.split(",");
        for (String split:splitValue){
            values.add(split);
        }
        return values;
    }



    public String addMetadataFieldValue(CategoryMetadataFieldValueDto categoryMetadataFieldValueDto, UUID id, UUID mtaId) throws Exception {
        if(!categoryRepository.findById(id).isPresent()){
            throw new Exception("category does not exist");
        }
        else if (!categoryMetadataFieldRepository.findById(mtaId).isPresent()){
            throw new Exception("Metadata field not exist");
        }
        else {
            Category category = categoryRepository.findById(id).get();
            CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findById(mtaId).get();
            CategoryMetadataFieldValues categoryMetadataFieldValues = new CategoryMetadataFieldValues();
            {
                    String values = toCommaSeperatedString(categoryMetadataFieldValueDto.getFieldValues());
                    categoryMetadataFieldValues.setCategoryId(category);
                    categoryMetadataFieldValues.setValues(values);
                    categoryMetadataFieldValues.setCategoryMetadataFieldId(categoryMetadataField);

                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
                    }
                return "Metadata field values added successfully";
            }
        }

        public List<CategoryMetadataField> findAllMetadataField(){
            return (List<CategoryMetadataField>) categoryMetadataFieldRepository.findAll();
        }

    public List<CategoryMetadataFieldValues> findAllMetadataFieldValues(){
        return (List<CategoryMetadataFieldValues>) categoryMetadataFieldValuesRepository.findAll();
    }
}

