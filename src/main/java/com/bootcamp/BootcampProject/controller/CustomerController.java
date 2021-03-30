//package com.bootcamp.BootcampProject.controller;
//
//import com.bootcamp.BootcampProject.entity.user.Address;
//import com.bootcamp.BootcampProject.entity.user.Customer;
//import com.bootcamp.BootcampProject.repository.CustomerRepository;
//import com.bootcamp.BootcampProject.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping("/profile")
//    public MappingJacksonValue customerProfile(){
//     return customerService.getProfile();
//    }
//
//    @GetMapping("/addresses")
//    public MappingJacksonValue customerAddress(){
//        return  customerService.getAddress();
//    }
//
//}
