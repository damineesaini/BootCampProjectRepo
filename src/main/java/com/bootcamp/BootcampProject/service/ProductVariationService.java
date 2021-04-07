package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.ProductVariationDto;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    public String convertWithStream(Map<String,String> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }

    public String addNewProductVariation(ProductVariationDto productVariationDto, UUID prodId, Seller seller) throws Exception {
        if (productRepository.findById(prodId).isPresent()){
            Product product = productRepository.findById(prodId).get();
            if (seller.equals(product.getSellerUserId())){
                if(product.isActive() && !product.isDelete()){
                    ProductVariation productVariation = new ProductVariation();
                    productVariation.setProductId(product);
                    productVariation.setPrice(productVariationDto.getPrice());
                    productVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable());
                    productVariation.setProductMetadata(convertWithStream(productVariationDto.getMetadataFieldValuesMap()));
                    productVariation.setActive(true);
                    productVariationRepository.save(productVariation);
                    return "Product variation saved successfully";
                }
                else {
                    throw new Exception("Product is either not active or deleted.");
                }
            }
            else{
                throw new Exception("accessing unauthorized product");
            }
        }
        else {
            throw new Exception("Incorrect Product Id");
        }
    }

    public ProductVariation viewProductVariationById(UUID variationId,Seller seller) throws Exception {
        if (productVariationRepository.findById(variationId).isPresent()){
            ProductVariation productVariation = productVariationRepository.findById(variationId).get();
            Product product = productVariation.getProductId();
            if (productRepository.findById(product.getId()).isPresent()){
                if (product.getSellerUserId().equals(seller)) {
                    if (!product.isDelete()){
                        return productVariation;
                    }
                    else{
                        throw new Exception("Trying to access deleted product");
                    }
                }
                else {
                    throw new Exception("accessing unauthorized product");
                }
            }
            else{
                throw new Exception("Product does not exist ");
            }
        }
        else {
            throw new Exception("invalid id");
        }
    }

    public List<ProductVariation> viewAllVariationOfProduct(UUID productId, Seller seller) throws Exception {
        if (productRepository.findById(productId).isPresent()){
            Product product = productRepository.findById(productId).get();
            if (product.getSellerUserId().equals(seller)) {
                if (!product.isDelete()){
                    return productVariationRepository.findAllByProductId(product);
                }
                else{
                    throw new Exception("Trying to access deleted product");
                }
            }
            else {
                throw new Exception("accessing unauthorized product");
            }
        }
        else {
            throw new Exception("invalid product id");
        }
    }

    public String updateProductVariation(ProductVariationDto productVariationDto, UUID variationId, Seller seller) throws Exception {
        if (productVariationRepository.findById(variationId).isPresent()){
            ProductVariation productVariation = productVariationRepository.findById(variationId).get();
            Product product =productVariation.getProductId();
            if (productRepository.findById(product.getId()).isPresent()){
                if (product.getSellerUserId().equals(seller)) {
                    if (!product.isDelete()){
                        productVariation.setPrice(productVariationDto.getPrice());
                        productVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable());
                        productVariation.setProductMetadata(convertWithStream(productVariationDto.getMetadataFieldValuesMap()));
                        productVariationRepository.save(productVariation);
                        return "Product variation updated successfully";
                    }
                    else{
                        throw new Exception("Trying to access deleted product");
                    }
                }
                else {
                    throw new Exception("accessing unauthorized product");
                }
            }
            else{
                throw new Exception("Product does not exist ");
            }
        }
        else {
            throw new Exception("invalid id");
        }

    }
}
