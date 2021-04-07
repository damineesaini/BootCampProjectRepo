package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CustomerUpdate;
import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Address;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
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
    public Set<Address> customerAddress() throws UserNotFoundException {
        return (Set<Address>) customerService.getAddress();
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

    @DeleteMapping("/delete-address/{addressId}")
    public String deleteAddress(@PathVariable String addressId,HttpServletResponse response){
        UUID addressid = UUID.fromString(addressId);
        Customer customer = customerService.getLoggedInCustomer();
        UUID id =customer.getUserId().getId();
        String message = customerService.deleteAddress(addressid,id);
        return message;
    }

    @PutMapping("/update-address/{addressId}")
    public String updateAddress(@Valid @RequestBody NewAddress newAddress, @PathVariable String addressId, HttpServletResponse response){
        UUID addressid = UUID.fromString(addressId);
        Customer customer = customerService.getLoggedInCustomer();
        UUID id =customer.getUserId().getId();
        String message = customerService.updateAddress(newAddress,addressid,id);
        return message;
    }

    @PutMapping("/update-password")
    public String updateAddress(@Valid @RequestBody UpdatePasswordDto updatePasswordDto){
        Customer customer = customerService.getLoggedInCustomer();
        UUID id =customer.getUserId().getId();
        String message = customerService.updatePassword(updatePasswordDto,id);
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