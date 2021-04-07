package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.ProductRequestParams;
import com.bootcamp.BootcampProject.dto.request.ProductUpdate;
import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.repository.CategoryRepository;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
    EmailSendService emailSendService;

    /**************************** Seller's API *********************************/

    public List<Product> getAllProduct(){
        return (List<Product>) productRepository.findAll();
    }

    public String saveProd(ProductRequestParams productRequestParams,Category category,Seller seller,User user){
        Product newProduct = new Product();
        newProduct.setName(productRequestParams.getName());
        newProduct.setBrand(productRequestParams.getBrand());
        newProduct.setDescription(productRequestParams.getDescription());
        newProduct.setCancellable(productRequestParams.isCancellable());
        newProduct.setCategoryId(category);
        newProduct.setReturnable(productRequestParams.isReturnable());
        newProduct.setActive(false);
        newProduct.setSellerUserId(seller);
        productRepository.save(newProduct);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("damineesaini1111@gmail.com");
        message.setSubject("Product Inactive");
        message.setText("Your product has been added but is waiting to be activated by the admin. the details of the product are:" + newProduct);
        emailSendService.sendEmail(message);
        return "Product Added Successfully";
    }

    public String createProduct(ProductRequestParams productRequestParams, Seller seller) throws Exception {
        User user = userRepository.findById(seller.getUserId().getId()).get();
        boolean isNameExist = productRepository.findByName(productRequestParams.getName()).isPresent();
        if (isNameExist){
            Product product = productRepository.findByName(productRequestParams.getName()).get();
            if (product.getCategoryId().getName().equals(productRequestParams.getCategory()) && product.getBrand().equals(productRequestParams.getBrand())) {
                return "Product already exists";
            }
            else{
                return saveProd(productRequestParams,product.getCategoryId(),seller,user);
            }
        }
        else {
            if (categoryRepository.findByName(productRequestParams.getCategory()) != null) {
                Category category = categoryRepository.findByName(productRequestParams.getCategory());
                if (!category.isHasChild()) {
                    return saveProd(productRequestParams, category, seller, user);
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

    public String addNewProduct(ProductRequestParams productRequestParams, Seller seller) throws Exception, UserNotFoundException {
        UUID id =seller.getUserId().getId();
        Category category;
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            if (productRepository.findByName(productRequestParams.getName()).isPresent()) {
                Product existProduct = productRepository.findByName(productRequestParams.getName()).get();
                if (existProduct.getCategoryId().getName().equals(productRequestParams.getCategory())) {
                    if (user.equals(existProduct.getSellerUserId()) && existProduct.getBrand().equals(productRequestParams.getBrand())) {
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

    public Product viewProductById(UUID productId, Seller seller) throws Exception {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            if (seller.equals(product.getSellerUserId())){
                return product;
            }
            else {
                throw new Exception("Unable to access.You are not seller of this product.");
            }
        }
        else {
            throw new Exception("Product does not exist");
        }
    }
    @Transactional
    @Modifying
    public String updateProductById(ProductUpdate productUpdate, UUID productId, Seller seller) throws Exception {
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
                throw new Exception("Unable to access.You are not seller of this product.");
            }
        }
        else {
            throw new Exception("Product does not exist");
        }
    }
    @Transactional
    @Modifying
    public String deleteProductById(UUID productId, Seller seller) throws Exception {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            if (seller.equals(product.getSellerUserId())){
                product.setDelete(true);
                productRepository.save(product);
                return "product deleted successfully";
            }
            else {
                throw new Exception("Unable to access.You are not seller of this product.");
            }
        }
        else {
            throw new Exception("Product does not exist");
        }
    }

    /**************************** Customer's API *********************************/

    public Product viewProduct(UUID productId) throws Exception {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            return product;
        }
        else {
            throw new Exception("Product does not exist");
        }
    }

    public List<Product> viewSimilarProduct(UUID productId) throws Exception {
        if(productRepository.findById(productId).isPresent()){
            Product product=productRepository.findById(productId).get();
            String brand = product.getBrand();
            List<Product> products = productRepository.findAllByBrand(brand);
            return products;
        }
        else {
            throw new Exception("Product does not exist");
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
                message.setSubject("Account Activated");
                message.setText("Your account is successfully activated");
                emailSendService.sendEmail(message);
                return "User Activated";
            }
            else {
                return "Account is already activated";
            }
        }
        else {
            throw new UserNotFoundException("Incorrect user id");
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

    public List<Product> getAllProductByCategory(UUID categoryId) throws Exception {
        List<Product> validProducts = new ArrayList<>();
      if (categoryRepository.findById(categoryId).isPresent()){
          Category category = categoryRepository.findById(categoryId).get();
          if (!category.isHasChild()){
              List<Product> products = productRepository.findAllByCategoryId(category);
              for (Product product: products) {
                  if (product.isActive() && !product.isDelete()){
                      if (product.getProductVariationId()!=null){
                          validProducts.add(product);
                      }
                  }
              }
              return validProducts;
          }
          else {
              throw new Exception("category has child. please specify a particular child category");
          }
      }
      else {
          throw new Exception("category does not exist.Invalid category id passed");
      }
    }
}
