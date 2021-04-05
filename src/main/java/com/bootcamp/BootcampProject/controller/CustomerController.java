package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CustomerUpdate;
import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/profile")
    public MappingJacksonValue customerProfile(){
     return customerService.getProfile();
    }

    @GetMapping("/addresses")
    public MappingJacksonValue customerAddress(){
        return  customerService.getAddress();
    }

    @PutMapping("/update-profile")
    public String updateProfile(@RequestBody CustomerUpdate customerUpdate, HttpServletResponse response){
        Customer customer = customerService.getLoggedInCustomer();
        UUID id =customer.getUserId().getId();
        String message = customerService.updateCustomerProfile(customerUpdate,id);
        return message;
    }

    @PostMapping("/add-address")
    public String addAddress(@RequestBody NewAddress newAddress, HttpServletResponse response){
        Customer customer = customerService.getLoggedInCustomer();
        UUID id = customer.getUserId().getId();
        String message = customerService.addAddress(newAddress,id);
        return message;
    }

    @DeleteMapping("/delete-address")
    public String deleteAddress(@PathVariable String addressId,HttpServletResponse response){
        UUID addressid = UUID.fromString(addressId);
        Customer customer = customerService.getLoggedInCustomer();
        UUID id =customer.getUserId().getId();
        String message = customerService.deleteAddress(addressid,id);
        return message;
    }

    @PutMapping("/update-address")
    public String updateAddress(@RequestBody NewAddress newAddress, @PathVariable String addressId, HttpServletResponse response){
        UUID addressid = UUID.fromString(addressId);
        Customer customer = customerService.getLoggedInCustomer();
        UUID id =customer.getUserId().getId();
        String message = customerService.updateAddress(newAddress,addressid,id);
        return message;
    }

    @PutMapping("/update-password")
    public String updateAddress(@RequestBody UpdatePasswordDto updatePasswordDto){
        Customer customer = customerService.getLoggedInCustomer();
        String email =customer.getUserId().getEmail();
        String message = customerService.updatePassword(updatePasswordDto,email);
        return message;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return "Logged out successfully";
    }



}