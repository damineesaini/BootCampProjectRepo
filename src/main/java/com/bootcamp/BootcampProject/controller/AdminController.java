package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @GetMapping("/home")
    public String adminHome(){
        return "Welcome Admin";
    }

    @GetMapping("/allCustomer")
    public List<Customer> retrieveAllCustomers(){
        return adminService.findAllCustomer();
    }

    @GetMapping("/allSeller")
    public List<Seller> retrieveAllSeller(){
        return adminService.findAllSeller();
    }

    @PutMapping("/activate-customer/{id}")
    public String customerActivation(@PathVariable UUID userId) throws UserNotFoundException {
        return adminService.activateUser(userId);
    }

    @PutMapping("/deactivate-customer/{id}")
    public String customerDeactivation(@PathVariable UUID userId) throws UserNotFoundException {
        return adminService.deactivateUser(userId);
    }

    @PutMapping("/activate-seller/{id}")
    public String sellerActivation(@PathVariable UUID userId) throws UserNotFoundException {
        return adminService.activateUser(userId);
    }

    @PutMapping("/deactivate-seller/{id}")
    public String sellerDeactivation(@PathVariable UUID userId) throws UserNotFoundException {
        return adminService.deactivateUser(userId);
    }

}
