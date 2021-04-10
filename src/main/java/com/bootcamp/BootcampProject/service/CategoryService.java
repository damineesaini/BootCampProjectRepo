package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CategoryDto;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.exception.AlreadyExistException;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldValuesRepository;
import com.bootcamp.BootcampProject.repository.CategoryRepository;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    public List<Category> findAll(){
        return (List<Category>) categoryRepository.findAll();
    }

    public String addNewCategory(CategoryDto categoryDto) throws DoesNotExistException, AlreadyExistException {
        if(categoryRepository.findByName(categoryDto.getName()) != null){
            return "Category already exists";
        }
        else if(categoryDto.getParentCategoryId()!=null) {
            if (categoryRepository.findByName(categoryDto.getParentCategoryId()) == null) {
                throw new DoesNotExistException("parent category mentioned does not exist");
            } else {
                Category parentCategory = categoryRepository.findByName(categoryDto.getParentCategoryId());
                if (productRepository.findAllByCategoryId(parentCategory.getId()).isEmpty()) {
                    Category category = new Category();
                    category.setName(categoryDto.getName());
                    category.setParentCategoryId(parentCategory);
                    parentCategory.setHasChild(true);
                    categoryRepository.save(parentCategory);
                    categoryRepository.save(category);
                    return "category saved successfully";
                }
                else {
                    List<Product> parentAssociatedProduct = productRepository.findAllByCategoryId(parentCategory.getId());
                           Optional<Product> product = parentAssociatedProduct.stream().filter(s->!s.isDelete()).findFirst();
                        if (product.isPresent()){
                            throw new AlreadyExistException("parent category is already associated with a product. Please choose a valid parent category id");
                        }
                        else {
                            Category category = new Category();
                            category.setName(categoryDto.getName());
                            category.setParentCategoryId(parentCategory);
                            parentCategory.setHasChild(true);
                            categoryRepository.save(parentCategory);
                            categoryRepository.save(category);
                            return  "category saved successfully";
                        }
                }
                }
            }

        else {
            Category category = new Category();
            category.setName(categoryDto.getName());
            category.setParentCategoryId(null);
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

    public String updateCategory(UUID categoryId, String category) throws DoesNotExistException, AlreadyExistException {
        if(categoryRepository.findById(categoryId).isEmpty()){
            throw new DoesNotExistException("Category id does not exist");
        }
        if(categoryRepository.findByName(category) !=null)
        {
            throw new AlreadyExistException("Category with provided name already exist.");
        }
        else {
            Category updateCategory = categoryRepository.findById(categoryId).get();
            updateCategory.setName(category);
            categoryRepository.save(updateCategory);
            return "updated successfully";
        }
    }

    public List<Category> findAllSubCategory(UUID categoryId) throws Exception {
        if (categoryRepository.findById(categoryId).isPresent()){
            Category parentCategory = categoryRepository.findById(categoryId).get();
            return categoryRepository.findAllByParentId(parentCategory.getId());
        }
        else {
            throw new Exception("invalid category id");
        }
    }

    public List<Category> findAllCategory() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Set<Object> findBrandListForCategory(UUID categoryId) throws DoesNotExistException {
        if (categoryRepository.findById(categoryId).isEmpty()){
            throw new DoesNotExistException("metadata for given categoryId doe not exist");
        }
        else if(categoryRepository.findById(categoryId).get().isHasChild()){
            List<Category> categories = categoryRepository.findAllByParentId(categoryId);
            Set<Object> brands = new HashSet<>();
            for (Category s : categories) {
                Object allBrandsByCategoryId = productRepository.findAllBrandsByCategoryId(s.getId());
                brands.add(allBrandsByCategoryId);
            }
            return brands;
        }
        else {
            return productRepository.findAllBrandsByCategoryId(categoryId);
        }
    }

    public Set<Object> findPrice(UUID categoryId) throws DoesNotExistException {
        if (categoryRepository.findById(categoryId).isEmpty()){
            throw new DoesNotExistException("metadata for given categoryId doe not exist");
        }
        else if(categoryRepository.findById(categoryId).get().isHasChild()){
            List<Category> categories = categoryRepository.findAllByParentId(categoryId);

            Set<Object> brands = new HashSet<>();
            for (Category s : categories) {
                List<Product> products =productRepository.findAllByCategoryId(categoryId);
            for (Product sp : products) {
                double maxPrice = productVariationRepository.findMaximumPrice(sp.getId());
                double minPrice = productVariationRepository.findMinimumPrice(sp.getId());
                Object maxAndMinPrice = productVariationRepository.findMaxAndMinPrice(minPrice,maxPrice);
                brands.add(maxAndMinPrice);
            }

        }return brands;
            }
        else {
            List<Product> products =productRepository.findAllByCategoryId(categoryId);
             Set<Object> brands = new HashSet<>();
            for (Product s : products) {
                double maxPrice = productVariationRepository.findMaximumPrice(s.getId());
                double minPrice = productVariationRepository.findMinimumPrice(s.getId());
                Object maxAndMinPrice = productVariationRepository.findMaxAndMinPrice(minPrice,maxPrice);
                brands.add(maxAndMinPrice);
            }
            return brands;
        }
    }


}
