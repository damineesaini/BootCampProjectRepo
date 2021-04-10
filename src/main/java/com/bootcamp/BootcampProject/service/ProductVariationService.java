package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.ProductVariationDto;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.AlreadyExistException;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.InactiveException;
import com.bootcamp.BootcampProject.exception.ProductNotFoundException;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldRepository;
import com.bootcamp.BootcampProject.repository.CategoryMetadataFieldValuesRepository;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    private static String FOLDER_PATH="/home/ttn/Desktop/BootcampProjectRepo-master/images";

    public String addNewProductVariation(ProductVariationDto productVariationDto, UUID prodId, Seller seller) throws Exception, DoesNotExistException, InactiveException, ProductNotFoundException, AlreadyExistException {
        if (productRepository.findById(prodId).isPresent()){
            Product product = productRepository.findById(prodId).get();
            if (seller.equals(product.getSellerUserId())){
                if(product.isActive() && !product.isDelete()){
                    ProductVariation productVariation = new ProductVariation();
                    productVariation.setProductId(product);
                    productVariation.setPrice(productVariationDto.getPrice());
                    productVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable());
                    UUID categoryId = product.getCategoryId().getId();
                    System.out.println(categoryId);
                    CategoryMetadataField fieldId;
                    System.out.println(productVariationDto.getMetadataFieldValuesMap());
                    Map<String, String> metadata = productVariationDto.getMetadataFieldValuesMap();
                    System.out.println(metadata);
                    List<String> keys = new ArrayList<>(metadata.keySet());
                    for (String field:keys) {
                        fieldId = categoryMetadataFieldRepository.findIdByName(field);
                        if (fieldId != null) {
                           List<CategoryMetadataFieldValues> dbValues = categoryMetadataFieldValuesRepository.findByCategoryIdAndCategoryMetadataFieldId(categoryId, fieldId.getId());
System.out.println(dbValues);
                            System.out.println(metadata.get(field));
                            Optional<CategoryMetadataFieldValues> existMetadataValues = dbValues.stream().filter(s->s.getValues().equals(metadata.get(field))).findFirst();
                            if (existMetadataValues.isPresent()) {
                                throw new DoesNotExistException("the value entered for field " + field + " does not exist");
                            }
                        } else {
                            throw new DoesNotExistException("the field" + field + "entered does not exist");
                        }
                    }

                    productVariation.setProductMetadata(productVariationDto.getMetadataFieldValuesMap());
                    productVariation.setActive(true);
                    productVariationRepository.save(productVariation);
                    return "variation saved.";
                }
                else {
                    throw new InactiveException("Product is either not active or deleted.");
                }
            }
            else{
                throw new InactiveException("accessing unauthorized product");
            }
        }
        else {
            throw new ProductNotFoundException("Incorrect Product Id");
        }
    }

    public ProductVariation viewProductVariationById(UUID variationId,Seller seller) throws Exception, InactiveException, DoesNotExistException, ProductNotFoundException {
        if (productVariationRepository.findById(variationId).isPresent()){
            ProductVariation productVariation = productVariationRepository.findById(variationId).get();
            Product product = productVariation.getProductId();
            if (productRepository.findById(product.getId()).isPresent()){
                if (product.getSellerUserId().equals(seller)) {
                    if (!product.isDelete()){
                        return productVariation;
                    }
                    else{
                        throw new InactiveException("Trying to access deleted product");
                    }
                }
                else {
                    throw new  InactiveException("accessing unauthorized product");
                }
            }
            else{
                throw new DoesNotExistException("Product does not exist ");
            }
        }
        else {
            throw new ProductNotFoundException("invalid id");
        }
    }

    public List<ProductVariation> viewAllVariationOfProduct(UUID productId, Seller seller) throws Exception, InactiveException, ProductNotFoundException {
        if (productRepository.findById(productId).isPresent()){
            Product product = productRepository.findById(productId).get();
            if (product.getSellerUserId().equals(seller)) {
                if (!product.isDelete()){
                    return productVariationRepository.findAllByProductId(product);
                }
                else{
                    throw new InactiveException("Trying to access deleted product");
                }
            }
            else {
                throw new InactiveException("accessing unauthorized product");
            }
        }
        else {
            throw new ProductNotFoundException("invalid product id");
        }
    }

    public String updateProductVariation(ProductVariationDto productVariationDto, UUID variationId, Seller seller) throws Exception, InactiveException, DoesNotExistException, ProductNotFoundException {
        if (productVariationRepository.findById(variationId).isPresent()){
            ProductVariation productVariation = productVariationRepository.findById(variationId).get();
            Product product =productVariation.getProductId();
            if (productRepository.findById(product.getId()).isPresent()){
                if (product.getSellerUserId().equals(seller)) {
                    if (!product.isDelete()){
                        productVariation.setPrice(productVariationDto.getPrice());
                        productVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable());
                        UUID categoryId = product.getCategoryId().getId();
                        System.out.println(categoryId);
                        CategoryMetadataField fieldId;
                        Map<String, String> metadata = productVariationDto.getMetadataFieldValuesMap();
                        List<String> keys = new ArrayList<>(metadata.keySet());
                        for (String field:keys) {
                            fieldId = categoryMetadataFieldRepository.findIdByName(field);
                            if (fieldId != null) {
                                List<CategoryMetadataFieldValues> dbValues = categoryMetadataFieldValuesRepository.findByCategoryIdAndCategoryMetadataFieldId(categoryId, fieldId.getId());
                                System.out.println(dbValues);
                                System.out.println(metadata.get(field));
                                Optional<CategoryMetadataFieldValues> existMetadataValues = dbValues.stream().filter(s->s.getValues().equals(metadata.get(field))).findFirst();
                                if (existMetadataValues.isPresent()) {
                                    throw new DoesNotExistException("the value entered for field " + field + " does not exist");
                                }
                            } else {
                                throw new DoesNotExistException("the field" + field + "entered does not exist");
                            }
                        }
                        productVariation.setProductMetadata(productVariationDto.getMetadataFieldValuesMap());
                        productVariationRepository.save(productVariation);
                        return "Product variation updated successfully";
                    }
                    else{
                        throw new InactiveException("Trying to access deleted product");
                    }
                }
                else {
                    throw new InactiveException("accessing unauthorized product");
                }
            }
            else{
                throw new DoesNotExistException("Product does not exist ");
            }
        }
        else {
            throw new ProductNotFoundException("invalid product id");
        }

    }
}
