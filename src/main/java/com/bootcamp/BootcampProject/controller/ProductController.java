package com.bootcamp.BootcampProject.controller;


import com.bootcamp.BootcampProject.dto.request.ProductRequestParams;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.ProductService;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    SellerService sellerService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductRequestParams productRequestParams, HttpServletRequest request) throws Exception, UserNotFoundException {
        Seller seller = sellerService.getLoggedInSeller();
        return productService.addNewProduct(productRequestParams,seller);
    }
}
