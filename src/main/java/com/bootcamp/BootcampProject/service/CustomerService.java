//package com.bootcamp.BootcampProject.service;
//
//import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
//import com.bootcamp.BootcampProject.entity.user.Customer;
//import com.bootcamp.BootcampProject.entity.user.User;
//import com.bootcamp.BootcampProject.repository.CustomerRepository;
//import com.bootcamp.BootcampProject.repository.UserRepository;
//import com.fasterxml.jackson.databind.ser.FilterProvider;
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomerService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public Customer getLoggedInCustomer(){
//        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
//
//        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
//        String username= appUserDetails.getUsername();
//        User user = userRepository.findByEmail(username);
//
//        return  customerRepository.findBy(user);
//    }
//
//    public MappingJacksonValue getProfile(){
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","isActive","contactNo");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("userDynamicFilter",filter);
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getLoggedInCustomer());
//        mappingJacksonValue.setFilters(filters);
//        return mappingJacksonValue;
//    }
//
//    public MappingJacksonValue getAddress(){
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("addresses");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("userDynamicFilter",filter);
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getLoggedInCustomer());
//        mappingJacksonValue.setFilters(filters);
//        return mappingJacksonValue;
//    }
//
//
//}
