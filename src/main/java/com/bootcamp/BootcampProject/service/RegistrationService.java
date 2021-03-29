package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.user.*;
import com.bootcamp.BootcampProject.dto.request.CustomerRegister;
//import com.bootcamp.BootcampProject.exception.UserAlreadyExistException;
import com.bootcamp.BootcampProject.exception.UserAlreadyExistException;
import com.bootcamp.BootcampProject.repository.CustomerRepository;
import com.bootcamp.BootcampProject.repository.RoleRepository;
import com.bootcamp.BootcampProject.repository.SellerRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    //
//    @Autowired
//    private SellerRepository sellerRepository;
//
////    @Autowired
////    private PasswordEncoder bCryptPasswordEncoder;
//
//
    public Customer createNewCustomer(CustomerRegister customerRegister) throws UserAlreadyExistException {
        User userExists = userRepository.findByEmail(customerRegister.getEmail());
        if (userExists != null) throw new UserAlreadyExistException("User is already registered with the given email");
        Customer newCustomer = new Customer();
        User newUser = new User();
        newUser.setEmail(customerRegister.getEmail());
        newUser.setFirstName(customerRegister.getFirstName());
        newUser.setMiddleName(customerRegister.getMiddleName());
        newUser.setLastName(customerRegister.getLastName());
        newUser.setPassword(customerRegister.getPassword());
        Address address = new Address();
        address.setAddressLine(customerRegister.getAddressLine());
        address.setCity(customerRegister.getCity());
        address.setCountry(customerRegister.getCountry());
        address.setState(customerRegister.getState());
        address.setZipcode(customerRegister.getZipcode());
        address.setLabel(customerRegister.getLabel());
        newUser.addAddresses(address);
        newUser.setActive(false);
        newUser.setDeleted(false);
        Role role = new Role();
        role.setAuthority("ROLE_CUSTOMER");
        newUser.setRoles(new ArrayList<>(Arrays.asList(role)));
        newCustomer.setContactNo(customerRegister.getContact());
        customerRepository.save(newCustomer);
        return newCustomer;
    }
//
    public List<Customer> findAllCustomer() {
        return (List<Customer>)customerRepository.findAll();
    }
//
////    public Seller createNewSeller(Seller seller){
////        Seller newSeller = new Seller();
////        newSeller.setEmail(seller.getEmail());
////        newSeller.setFirstName(seller.getFirstName());
////        newSeller.setMiddleName(seller.getMiddleName());
////        newSeller.setLastName(seller.getLastName());
////        newSeller.setPassword(bCryptPasswordEncoder.encode(seller.getPassword()));
////        newSeller.setActive(false);
////        newSeller.setDeleted(false);
////        Role role = roleRepository.findByRole("SELLER");
////        newSeller.setRoles(new ArrayList<>(Arrays.asList(role)));
////        newSeller.setId(seller.getId());
////        newSeller.setCompanyContactNo(seller.getCompanyContactNo());
////        newSeller.setCompanyName(seller.getCompanyName());
////        newSeller.setGst(seller.getGst());
////        sellerRepository.save(newSeller);
////        return newSeller;
////    }
}
