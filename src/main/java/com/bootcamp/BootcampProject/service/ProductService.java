package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.ProductRequestParams;
import com.bootcamp.BootcampProject.dto.request.ProductUpdate;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.*;
import com.bootcamp.BootcampProject.repository.CategoryRepository;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.RoleRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailSendService emailSendService;

    /**************************** Seller's API **********************************/


    public MappingJacksonValue getAllProductForSeller(Seller seller) throws InactiveException, DoesNotExistException {
            if(productRepository.findAllNonDeletedActive().isEmpty()){
                throw new InactiveException("No active products found");
            }
            else if(productRepository.findBySellerUserId(seller).isEmpty()){
                throw new DoesNotExistException("no products found");
            }
            else {
                SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","description","brand","isActive","isDelete","isCancellable","isReturnable","categoryId");
                SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name","hasChild","isActive","parentCategoryId");
                FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("categoryFilter",filterCategory);

                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(productRepository.findAllNonDeletedActive());
                mappingJacksonValue.setFilters(filters);
                return mappingJacksonValue;
            }
    }

    public String saveProd(ProductRequestParams productRequestParams,Category category,Seller seller,User user){
        Product newProduct = new Product();
        newProduct.setName(productRequestParams.getName());
        newProduct.setBrand(productRequestParams.getBrand());
        newProduct.setDescription(productRequestParams.getDescription());
        newProduct.setCancellable(productRequestParams.isCancellable());
        System.out.println(productRequestParams.isCancellable());
        newProduct.setCategoryId(category);
        newProduct.setReturnable(productRequestParams.isReturnable());
        System.out.println(productRequestParams.isReturnable());
        newProduct.setActive(false);
        newProduct.setSellerUserId(seller);
        productRepository.save(newProduct);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("damineesaini7@gmail.com");
        message.setFrom("damineesaini1111@gmail.com");
        message.setSubject("Product Inactive");
        message.setText("A new product has been added but is waiting to be activated by the admin. The details of the product are: \n" +" category : "+ newProduct.getId()+ "\n category : "+ newProduct.getCategoryId().getName() +"\n brand : "+ newProduct.getBrand() +"\n name : "+ newProduct.getName() +"\n description : "+ newProduct.getDescription());
        emailSendService.sendEmail(message);
        return "Product Added Successfully";
    }

    public String addNewProduct(ProductRequestParams productRequestParams, Seller seller) throws Exception, UserNotFoundException {
        UUID id =seller.getUserId().getId();
        Category category;
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            if (productRepository.findByName(productRequestParams.getName()).isPresent()) {
                Product existProduct = productRepository.findByName(productRequestParams.getName()).get();
                if (existProduct.getCategoryId().getName().equals(productRequestParams.getCategory())) {
                    if (user.equals(existProduct.getSellerUserId().getUserId()) && existProduct.getBrand().equals(productRequestParams.getBrand())) {
                        return "Product already exist";
                    }
                    else {
                       return saveProd(productRequestParams,existProduct.getCategoryId(),seller,user);
                    }
                }
                else {
                    if (categoryRepository.findByName(productRequestParams.getCategory()) != null) {
                        category = categoryRepository.findByName(productRequestParams.getCategory());
                        if (category.getParentCategoryId() != null) {
                            return saveProd(productRequestParams,category,seller,user);
                        }
                        else {
                            throw new Exception("Category is a parent category");
                        }
                    }
                    else {
                            throw new Exception("Category is invalid");
                    }
                }
            }
            else {
                if (categoryRepository.findByName(productRequestParams.getCategory()) != null) {
                    category = categoryRepository.findByName(productRequestParams.getCategory());
                    return saveProd(productRequestParams,category,seller,user);
                } else {
                    throw new Exception("Category does not exist");
                }
            }
        }
        else {
            throw new UserNotFoundException("invalid User id");
        }
    }

    public MappingJacksonValue viewProductById(UUID productId, Seller seller) throws DoesNotExistException, UnauthorizedAccessException, ProductNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            if (seller.equals(product.getSellerUserId())){
                if (!product.isDelete()){
                    SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isActive","isDelete","isCancellable","isReturnable","categoryId");
                    SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name","hasChild","isActive","parentCategoryId");
                    FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("categoryFilter",filterCategory);
                    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(product);
                    mappingJacksonValue.setFilters(filters);
                    return mappingJacksonValue;
                }
                else {
                    throw new DoesNotExistException("Trying to access a deleted product");
                }
            }
            else {
                throw new UnauthorizedAccessException("Unable to access.You are not seller of this product.");
            }
        }
        else {
            throw new ProductNotFoundException("Product does not exist");
        }
    }
    @Transactional
    @Modifying
    public String updateProductById(ProductUpdate productUpdate, UUID productId, Seller seller) throws UnauthorizedAccessException, ProductNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            if (seller.equals(product.getSellerUserId())){
                    product.setName(productUpdate.getName());
                    product.setDescription(productUpdate.getDescription());
                    product.setReturnable(productUpdate.isReturnable());
                    product.setCancellable(productUpdate.isCancellable());
                    productRepository.save(product);
                    return "product updated successfully";
            }
            else {
                throw new UnauthorizedAccessException("Unable to access.You are not seller of this product.");
            }
        }
        else {
            throw new ProductNotFoundException("Product does not exist");
        }
    }
    @Transactional
    @Modifying
    public String deleteProductById(UUID productId, Seller seller) throws UnauthorizedAccessException, ProductNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            if (seller.equals(product.getSellerUserId())){
                product.setDelete(true);
                productRepository.save(product);
                return "product deleted successfully";
            }
            else {
                throw new UnauthorizedAccessException("Unable to access.You are not seller of this product.");
            }
        }
        else {
            throw new ProductNotFoundException("Product does not exist");
        }
    }

    /**************************** Customer's API **********************************/

    public MappingJacksonValue viewProduct(UUID productId) throws ProductNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name","hasChild","isActive","parentCategoryId");
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","quantityAvailable","price","productMetadata","productImage");
            SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isActive","isDelete","isCancellable","isReturnable","categoryId","productVariationId");
            FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("productVariationFilter",filter).addFilter("categoryFilter",filterCategory);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(product);
            mappingJacksonValue.setFilters(filters);
            return mappingJacksonValue;
        }
        else {
            throw new ProductNotFoundException("Product does not exist");
        }
    }

    public MappingJacksonValue getAllProductByCategory(UUID categoryId) throws NotChildCategoryException, CategoryNotFoundException {
        List<Product> validProducts = new ArrayList<>();
        if (categoryRepository.findById(categoryId).isPresent()){
            Category category = categoryRepository.findById(categoryId).get();
            if (!category.isHasChild()){
                List<Product> products = productRepository.findAllByCategoryId(categoryId);
                for (Product product: products) {
                    if (product.isActive() && !product.isDelete()){
                        if (product.getProductVariationId()!=null){
                            validProducts.add(product);
                        }
                    }
                }
                SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name","hasChild","isActive","parentCategoryId");
                SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","quantityAvailable","price","productMetadata","productImage");
                SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isActive","isDelete","isCancellable","isReturnable","categoryId","productVariationId");
                FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("productVariationFilter",filter).addFilter("categoryFilter",filterCategory);
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(validProducts);
                mappingJacksonValue.setFilters(filters);
                return mappingJacksonValue;
            }
            else {
                throw new NotChildCategoryException("category has child. please specify a particular child category");
            }
        }
        else {
            throw new CategoryNotFoundException("category does not exist.Invalid category id passed");
        }
    }

    public MappingJacksonValue viewSimilarProduct(UUID productId) throws  ProductNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            String brand = product.getBrand();
            List<Product> products = productRepository.findAllByBrand(brand);
            SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name","hasChild","isActive","parentCategoryId");
            SimpleBeanPropertyFilter filter2 =SimpleBeanPropertyFilter.filterOutAllExcept("filename","path");
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","quantityAvailable","price","productMetadata","productImage");
            SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isActive","isDelete","isCancellable","isReturnable","categoryId","productVariationId");
            FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("imageFilter",filter2).addFilter("productVariationFilter",filter).addFilter("categoryFilter",filterCategory);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(products);
            mappingJacksonValue.setFilters(filters);
            return mappingJacksonValue;
//            return products;
        }
        else {
            throw new ProductNotFoundException("Product does not exist");
        }
    }


    /**************************** Admin's API *********************************/

    @Transactional
    @Modifying
    public String activateProduct(UUID productId) throws UserNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product =productRepository.findById(productId).get();
            if (!product.isActive()){
                product.setActive(true);
                productRepository.save(product);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(product.getSellerUserId().getUserId().getEmail());
                message.setFrom("damineesaini11@gmail.com");
                message.setSubject("Product Activated");
                message.setText("Your product is successfully activated");
                emailSendService.sendEmail(message);
                return "Product Activated";
            }
            else {
                return "Product is already activated";
            }
        }
        else {
            throw new UserNotFoundException("Incorrect product id");
        }
    }

    @Transactional
    @Modifying
    public String deactivateProduct(UUID productId) throws UserNotFoundException {
        if(productRepository.findById(productId).isPresent()){
            Product product = productRepository.findById(productId).get();
            if (product.isActive()){
                product.setActive(false);
                productRepository.save(product);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(product.getSellerUserId().getUserId().getEmail());
                message.setFrom("damineesaini11@gmail.com");
                message.setSubject("Product Deactivated");
                message.setText("Your product is deactivated by admin");
                emailSendService.sendEmail(message);
                return "Product Deactivated";
            }
            else {
                return "Product is already deactivated";
            }
        }
        else {
            throw new UserNotFoundException("Incorrect product id");
        }
    }



    public MappingJacksonValue getAllProduct() throws InactiveException {
        if(productRepository.findAllNonDeletedActive().isEmpty()){
            throw new InactiveException("No active products found");
        }
        else {
            SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name","hasChild","isActive","parentCategoryId");
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","quantityAvailable","price","productMetadata","productImage");
            SimpleBeanPropertyFilter filter2 =SimpleBeanPropertyFilter.filterOutAllExcept("filename","path");
            SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isActive","isDelete","isCancellable","isReturnable","categoryId","productVariationId");
            FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter",filter4).addFilter("imageFilter",filter2).addFilter("productVariationFilter",filter).addFilter("categoryFilter",filterCategory);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(productRepository.findAllNonDeletedActive());
            mappingJacksonValue.setFilters(filters);
            return mappingJacksonValue;
        }
    }
}

