package com.bootcamp.BootcampProject.controller;


import com.bootcamp.BootcampProject.dto.request.ProductRequestParams;
import com.bootcamp.BootcampProject.dto.request.ProductUpdate;
import com.bootcamp.BootcampProject.dto.request.ProductVariationDto;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.ProductService;
import com.bootcamp.BootcampProject.service.ProductVariationService;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductVariationService productVariationService;

    @Autowired
    SellerService sellerService;

    /********************************** Seller API ************************************/
    @PostMapping("seller/addProduct")
    public String addProduct(@RequestBody ProductRequestParams productRequestParams, HttpServletRequest request) throws Exception, UserNotFoundException {
        Seller seller = sellerService.getLoggedInSeller();
        return productService.addNewProduct(productRequestParams,seller);
    }

    @PostMapping("seller/add-variation")
    public String addVariation(@RequestBody ProductVariationDto productVariationDto, @Param("productId") String productId) throws Exception {
        UUID prodId = UUID.fromString(productId);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.addNewProductVariation(productVariationDto,prodId,seller);
    }

    @GetMapping("seller/view-product/{id}")
    public Product viewProduct(@PathVariable String id,HttpServletRequest httpServletRequest) throws Exception {
    UUID productId = UUID.fromString(id);
    Seller seller = sellerService.getLoggedInSeller();
    return productService.viewProductById(productId,seller);
    }

    @GetMapping("seller/view-variation/{id}")
    public ProductVariation viewVariation(@PathVariable String id,HttpServletRequest httpServletRequest) throws Exception {
        UUID variationId = UUID.fromString(id);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.viewProductVariationById(variationId,seller);
    }

    @GetMapping("seller/view-allproduct")
    public List<Product> viewAllProduct(){
        return productService.getAllProduct();
    }

    @GetMapping("seller/view-variation/{productid}")
    public List<ProductVariation> viewAllVariation(@PathVariable String productid, HttpServletRequest httpServletRequest) throws Exception {
        UUID productId = UUID.fromString(productid);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.viewAllVariationOfProduct(productId,seller);
    }

    @DeleteMapping("seller/delete-product/{id}")
    public String deleteProduct(@PathVariable String id,HttpServletRequest httpServletRequest) throws Exception {
        UUID productId = UUID.fromString(id);
        Seller seller = sellerService.getLoggedInSeller();
        return productService.deleteProductById(productId,seller);
    }

    @PutMapping("seller/update-product/{id}")
    public String updateProduct(@RequestBody ProductUpdate productUpdate, @PathVariable String id, HttpServletRequest httpServletRequest) throws Exception {
       UUID productId = UUID.fromString(id);
       Seller seller = sellerService.getLoggedInSeller();
       return productService.updateProductById(productUpdate,productId,seller);
    }

    @PutMapping("seller/update-variation/{id}")
    public String updateVariation(@RequestBody ProductVariationDto productVariationDto,@PathVariable String id,HttpServletRequest httpServletRequest) throws Exception {
        UUID variationId = UUID.fromString(id);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.updateProductVariation(productVariationDto,variationId,seller);
    }

    /********************************** Customer API ************************************/

    @GetMapping("/customer/view-product/{id}")
    public Product viewProduct(@PathVariable String id) throws Exception {
        UUID productId = UUID.fromString(id);
        return productService.viewProduct(productId);
    }

    @GetMapping("/customer/viewall-product/{categoryid}")
    public List<Product> viewAllProduct(@PathVariable String categoryid) throws Exception {
        UUID categoryId = UUID.fromString(categoryid);
        return productService.getAllProductByCategory(categoryId);
    }

    @GetMapping("/customer/similarProducts/{productid}")
    public List<Product> viewSimilarProducts(@PathVariable String productid) throws Exception {
        UUID productId = UUID.fromString(productid);
        return productService.viewSimilarProduct(productId);
    }

    /********************************** Admin API ************************************/

    @GetMapping("/admin/view-product/{id}")
    public Product viewSingleProduct(@PathVariable String id) throws Exception {
        UUID productId = UUID.fromString(id);
        return productService.viewProduct(productId);
    }

    @GetMapping("/admin/view-allProduct")
    public  List<Product> viewAllProducts(){
        return productService.getAllProduct();
    }

    @PutMapping("/admin/deactivate-product/{id}")
    public String deactivateProduct(@PathVariable String id) throws UserNotFoundException {
        return productService.deactivateProduct(UUID.fromString(id));
    }

    @PutMapping("/admin/activate-product/{id}")
    public String activateProduct(@PathVariable String id) throws UserNotFoundException {
        return productService.activateProduct(UUID.fromString(id));
    }
}
