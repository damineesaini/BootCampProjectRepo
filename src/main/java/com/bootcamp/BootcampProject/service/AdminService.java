package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.repository.CustomerRepository;
import com.bootcamp.BootcampProject.repository.SellerRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailSendService emailSendService;

    @Transactional
    public String activateUser(UUID userId) throws UserNotFoundException {
        Optional<User> user;
        try {
            user = userRepository.findById(userId);
        }catch (NullPointerException e){
            throw new UsernameNotFoundException("Incorrect user Id");
        }

        if(!user.isPresent()){
            User user1 =user.get();
            if (!user1.isActive()){
                user1.setActive(true);
                userRepository.save(user1);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user1.getEmail());
                message.setFrom("damineesaini11@gmail.com");
                message.setSubject("Account Activated");
                message.setText("Your account is successfully activated");
                emailSendService.sendEmail(message);
                return "User Activated";
            }
            else {
                return "Account is already activated";
            }
        }
        else {
            throw new UserNotFoundException("Incorrect user id");
        }
    }

    @Transactional
    public String deactivateUser(UUID userId) throws UserNotFoundException {
        Optional<User> user;
        try {
            user = userRepository.findById(userId);
        }catch (NullPointerException e){
            throw new UsernameNotFoundException("Incorrect user Id");
        }

        if(!user.isPresent()){
            User user1 =user.get();
            if (user1.isActive()){
                user1.setActive(false);
                userRepository.save(user1);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user1.getEmail());
                message.setFrom("damineesaini11@gmail.com");
                message.setSubject("Account Deactivated");
                message.setText("Your account is deactivated by admin");
                emailSendService.sendEmail(message);
                return "User Deactivated";
            }
            else {
                return "Account is already deactivated";
            }
        }
        else {
            throw new UserNotFoundException("Incorrect user id");
        }
    }

    public List<Customer> findAllCustomer() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return customers;
    }

    public List<Seller> findAllSeller() {
        List<Seller> sellers = (List<Seller>) sellerRepository.findAll();
        return sellers;
    }
}
