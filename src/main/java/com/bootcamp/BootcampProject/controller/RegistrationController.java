package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
//import com.bootcamp.BootcampProject.exception.UserAlreadyExistException;
import com.bootcamp.BootcampProject.dto.request.CustomerRegister;
import com.bootcamp.BootcampProject.exception.UserAlreadyExistException;
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
    public List<Customer> getCustomer() {
        return registrationService.findAllCustomer();
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> registerCustomer(@RequestBody CustomerRegister customer) throws UserAlreadyExistException {
        Customer newCustomer = registrationService.createNewCustomer(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newCustomer.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
//    @PostMapping("/seller")
//    public ResponseEntity<Object> registerSeller(@RequestBody Seller seller) throws UserAlreadyExistException {
//        User userExists = registrationService.findUserByEmail(seller.getEmail());
//        if (userExists != null) throw new UserAlreadyExistException("User is already registered with the given email");
//        else
//        {Seller newSeller = registrationService.createNewSeller(seller);
//            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newSeller.getId()).toUri();
//            return ResponseEntity.created(location).build();}
//    }
//}
