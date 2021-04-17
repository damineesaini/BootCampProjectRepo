package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.SellerUpdate;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.UnauthorizedAccessException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/profile")
    public MappingJacksonValue sellerProfile(){
        MappingJacksonValue seller =sellerService.getProfile();
        return seller;
    }

    @PutMapping("/update-profile")
    public String updateProfile(@Valid @RequestBody SellerUpdate sellerUpdate, HttpServletResponse response) throws UserNotFoundException, UnauthorizedAccessException {
        Seller seller = sellerService.getLoggedInSeller();
        UUID id =seller.getUserId().getId();
        String message = sellerService.updateSellerProfile(sellerUpdate,id);
        return message;
    }

    @PutMapping("/update-address/{addressId}")
    public String updateAddress(@Valid @RequestBody NewAddress newAddress,@PathVariable String addressId, HttpServletResponse response) throws UserNotFoundException, UnauthorizedAccessException {
        UUID addressid = UUID.fromString(addressId);
        Seller seller = sellerService.getLoggedInSeller();
        UUID id =seller.getUserId().getId();
        String message = sellerService.updateAddress(newAddress,addressid,id);
        return message;
    }

    @PutMapping("/update-password")
    public String updateAddress(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws UserNotFoundException, UnauthorizedAccessException {
        Seller seller = sellerService.getLoggedInSeller();
        UUID id =seller.getUserId().getId();
        String message = sellerService.updatePassword(updatePasswordDto,id);
        return message;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws UserNotFoundException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                String tokenValue = authHeader.replace("bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
            } catch (Exception e) {
                throw new UserNotFoundException("user not found");
            }
        }
        return "Logged out successfully";
    }

}