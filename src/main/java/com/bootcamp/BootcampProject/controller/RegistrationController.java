package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.SellerRegister;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.TokenExpiredException;
import com.bootcamp.BootcampProject.exception.UserAlreadyExistException;
import com.bootcamp.BootcampProject.dto.request.CustomerRegister;
import com.bootcamp.BootcampProject.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @GetMapping
    public List<Seller> allCustomer(){
        return registrationService.findAllCustomer();
    }
    @PostMapping("/customer-register")
    public ResponseEntity<Object> registerCustomer(@RequestBody CustomerRegister customer) throws UserAlreadyExistException {
        Customer newCustomer = registrationService.createNewCustomer(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newCustomer.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/confirm-account")
    public String confirmCustomerAccount(@RequestParam("token") String confirmationToken) throws TokenExpiredException {
        return registrationService.confirmCustomerAccount(confirmationToken);
    }

    @PostMapping("/resend-activation-link")
    public String resendActivationLink(@RequestBody String email){
        return registrationService.resendActivationToken(email);
    }

    @PostMapping("/seller-register")
    public ResponseEntity<Object> registerSeller(@RequestBody SellerRegister sellerRegister) throws UserAlreadyExistException {
            Seller newSeller = registrationService.createNewSeller(sellerRegister);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newSeller.getUserId()).toUri();
            return ResponseEntity.created(location).build();
    }
}
