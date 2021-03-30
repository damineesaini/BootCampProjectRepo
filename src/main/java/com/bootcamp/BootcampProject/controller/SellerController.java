package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.SellerUpdate;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @GetMapping("/profile")
    public Seller sellerProfile(){
        Seller seller =sellerService.getLoggedInSeller();
        return seller;
    }

    @PutMapping("/update-profile")
    public String updateProfile(@RequestBody SellerUpdate sellerUpdate, HttpServletResponse response){
        Seller seller = sellerService.getLoggedInSeller();
        UUID id =seller.getUserId().getId();
        String message = sellerService.updateSellerProfile(sellerUpdate,id);
        return message;
    }

    @PutMapping("/update-address")
    public String updateAddress(@RequestBody NewAddress newAddress,@PathVariable UUID addressId, HttpServletResponse response){
        Seller seller = sellerService.getLoggedInSeller();
        UUID id =seller.getUserId().getId();
        String message = sellerService.updateAddress(newAddress,addressId,id);
        return message;
    }

    @PutMapping("/update-password")
    public String updateAddress(@RequestBody UpdatePasswordDto updatePasswordDto){
        Seller seller = sellerService.getLoggedInSeller();
        String email =seller.getUserId().getEmail();
        String message = sellerService.updatePassword(updatePasswordDto,email);
        return message;
    }


}
