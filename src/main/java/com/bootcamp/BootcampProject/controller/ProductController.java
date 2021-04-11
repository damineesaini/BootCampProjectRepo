package com.bootcamp.BootcampProject.controller;


import com.bootcamp.BootcampProject.dto.request.ProductRequestParams;
import com.bootcamp.BootcampProject.dto.request.ProductUpdate;
import com.bootcamp.BootcampProject.dto.request.ProductVariationDto;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.*;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import com.bootcamp.BootcampProject.service.ProductService;
import com.bootcamp.BootcampProject.service.ProductVariationService;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductVariationService productVariationService;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    SellerService sellerService;


    /********************************** Seller API ************************************/
    @PostMapping("/seller/addProduct")
    public String addProduct(@Valid @RequestBody ProductRequestParams productRequestParams, HttpServletRequest request) throws Exception, UserNotFoundException {
        Seller seller = sellerService.getLoggedInSeller();
        return productService.addNewProduct(productRequestParams,seller);
    }

    @PostMapping(value = "/seller/add-variation")
    public String addVariation(@Valid @RequestBody ProductVariationDto productVariationDto,@Param("productId") String productId) throws Exception, DoesNotExistException, InactiveException, ProductNotFoundException, AlreadyExistException {
        UUID prodId = UUID.fromString(productId);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.addNewProductVariation(productVariationDto,prodId,seller);
    }

//    @PostMapping(value = "/seller/add-variations")
//    public String addVariations(@RequestPart("productVariationDto") ProductVariationDto productVariationDto, @RequestPart("file") MultipartFile file, @Param("productId") String productId) throws Exception, DoesNotExistException, InactiveException, ProductNotFoundException, AlreadyExistException {
//        UUID prodId = UUID.fromString(productId);
//        Seller seller = sellerService.getLoggedInSeller();
//        return productVariationService.addNewProductVariations(productVariationDto,prodId,seller,file);
//    }

    @GetMapping("/seller/view-product/{id}")
    public MappingJacksonValue viewProductBySeller(@PathVariable String id) throws DoesNotExistException, UnauthorizedAccessException, ProductNotFoundException {
    UUID productId = UUID.fromString(id);
    Seller seller = sellerService.getLoggedInSeller();
    return productService.viewProductById(productId,seller);
    }

    @GetMapping("/seller/view-variation/{id}")
    public MappingJacksonValue viewVariation(@PathVariable String id, HttpServletRequest httpServletRequest) throws Exception, DoesNotExistException, InactiveException, ProductNotFoundException {
        UUID variationId = UUID.fromString(id);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.viewProductVariationById(variationId,seller);
    }

    @GetMapping("seller/view-allproduct")
    public MappingJacksonValue viewAllProduct() throws InactiveException, DoesNotExistException {
        Seller seller = sellerService.getLoggedInSeller();
        return productService.getAllProductForSeller(seller);
    }

    @GetMapping("/seller/view-allvariation/{productid}")
    public MappingJacksonValue viewAllVariation(@PathVariable String productid, HttpServletRequest httpServletRequest) throws Exception, InactiveException, ProductNotFoundException {
        UUID productId = UUID.fromString(productid);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.viewAllVariationOfProduct(productId,seller);
    }

    @DeleteMapping("/seller/delete-product/{id}")
    public String deleteProduct(@PathVariable String id,HttpServletRequest httpServletRequest) throws UnauthorizedAccessException, ProductNotFoundException {
        UUID productId = UUID.fromString(id);
        Seller seller = sellerService.getLoggedInSeller();
        return productService.deleteProductById(productId,seller);
    }

    @PatchMapping("/seller/update-product/{id}")
    public String updateProduct(@Valid @RequestBody ProductUpdate productUpdate, @PathVariable String id, HttpServletRequest httpServletRequest) throws UnauthorizedAccessException, ProductNotFoundException {
       UUID productId = UUID.fromString(id);
       Seller seller = sellerService.getLoggedInSeller();
       return productService.updateProductById(productUpdate,productId,seller);
    }

    @PatchMapping("/seller/update-variation/{id}")
    public String updateVariation(@Valid @RequestBody ProductVariationDto productVariationDto,@PathVariable String id,HttpServletRequest httpServletRequest) throws Exception, DoesNotExistException, InactiveException, ProductNotFoundException {
        UUID variationId = UUID.fromString(id);
        Seller seller = sellerService.getLoggedInSeller();
        return productVariationService.updateProductVariation(productVariationDto,variationId,seller);
    }

    /********************************** Customer API *************************************/

    @GetMapping("/customer/view-product/{id}")
    public MappingJacksonValue viewProduct(@PathVariable String id) throws ProductNotFoundException {
        UUID productId = UUID.fromString(id);
        return productService.viewProduct(productId);
    }

    @GetMapping("/customer/viewall-product/{categoryid}")
    public MappingJacksonValue viewAllProduct(@PathVariable String categoryid) throws CategoryNotFoundException, NotChildCategoryException{
        UUID categoryId = UUID.fromString(categoryid);
        return productService.getAllProductByCategory(categoryId);
    }

    @GetMapping("/customer/similarProducts/{productid}")
    public MappingJacksonValue viewSimilarProducts(@PathVariable String productid) throws ProductNotFoundException {
        UUID productId = UUID.fromString(productid);
        return productService.viewSimilarProduct(productId);
    }

    /********************************** Admin API *************************************/

    @GetMapping("/admin/view-product/{id}")
    public MappingJacksonValue viewSingleProduct(@PathVariable String id) throws ProductNotFoundException {
        UUID productId = UUID.fromString(id);
        return productService.viewProduct(productId);
    }

    @GetMapping("/admin/view-allProduct")
    public MappingJacksonValue viewAllProducts() throws InactiveException {
        return productService.getAllProduct();
    }

    @PatchMapping("/admin/deactivate-product/{id}")
    public String deactivateProduct(@PathVariable String id) throws UserNotFoundException {
        return productService.deactivateProduct(UUID.fromString(id));
    }

    @PatchMapping("/admin/activate-product/{id}")
    public String activateProduct(@PathVariable String id) throws UserNotFoundException {
        return productService.activateProduct(UUID.fromString(id));
    }
}
