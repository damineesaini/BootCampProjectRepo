package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CustomerRegister;
import com.bootcamp.BootcampProject.dto.request.ResendToken;
import com.bootcamp.BootcampProject.dto.request.SellerRegister;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.AlreadyExistException;
import com.bootcamp.BootcampProject.exception.TokenExpiredException;
import com.bootcamp.BootcampProject.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping("/customer-register")
    public Object registerCustomer(@Valid @RequestBody CustomerRegister customer) throws AlreadyExistException {
        if(customer.getPassword().equals(customer.getConfirmPassword())){
            Customer newCustomer = registrationService.createNewCustomer(customer);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newCustomer.getUserId()).toUri();
            return ResponseEntity.created(location).build();
        }
        else {
            return "password does not match";
        }
    }

    @PutMapping("/confirm-account")
    public String confirmCustomerAccount(@RequestParam("token") String confirmationToken) throws TokenExpiredException {
        return registrationService.confirmCustomerAccount(confirmationToken);
    }

    @PostMapping("/resend-activation-link")
    public String resendActivationLink(@Valid @RequestBody ResendToken email){
        return registrationService.resendActivationToken(email);
    }

    @PostMapping("/seller-register")
    public Object registerSeller(@Valid @RequestBody SellerRegister sellerRegister) throws AlreadyExistException {
        if(sellerRegister.getPassword().equals(sellerRegister.getConfirmPassword())){
            Seller newSeller = registrationService.createNewSeller(sellerRegister);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newSeller.getUserId()).toUri();
            return ResponseEntity.created(location).build();}
        else {
            return "password does not match";
        }
    }
}