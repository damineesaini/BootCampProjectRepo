package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.SellerRegister;
import com.bootcamp.BootcampProject.entity.token.ConfirmationToken;
import com.bootcamp.BootcampProject.entity.user.*;
import com.bootcamp.BootcampProject.dto.request.CustomerRegister;
import com.bootcamp.BootcampProject.exception.TokenExpiredException;
import com.bootcamp.BootcampProject.exception.UserAlreadyExistException;
import com.bootcamp.BootcampProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Seller> findAllCustomer() {
        List<Seller> sellers = (List<Seller>) sellerRepository.findAll();
        return sellers;
    }

    public Customer createNewCustomer(CustomerRegister customerRegister) throws UserAlreadyExistException {
        User userExists = userRepository.findByEmail(customerRegister.getEmail());
        if (userExists != null) throw new UserAlreadyExistException("User is already registered with the given email");
        else {
            Customer newCustomer = new Customer();
            User newUser = new User();
            newUser.setEmail(customerRegister.getEmail());
            newUser.setFirstName(customerRegister.getFirstName());
            newUser.setMiddleName(customerRegister.getMiddleName());
            newUser.setLastName(customerRegister.getLastName());
            newUser.setPassword(bCryptPasswordEncoder.encode(customerRegister.getPassword()));
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
            ConfirmationToken confirmationToken= new ConfirmationToken(newUser);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(customerRegister.getEmail());
            message.setFrom("damineesaini11@gmail.com");
            message.setSubject("Complete Registration");
            message.setText("To confirm your account, please lick here:"+"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
            emailSendService.sendEmail(message);
            return newCustomer;
        }

    }

    public Seller createNewSeller(SellerRegister sellerRegister) throws UserAlreadyExistException {

              User userExists = userRepository.findByEmail(sellerRegister.getEmail());
        if (userExists != null) throw new UserAlreadyExistException("User is already registered with the given email");
        else {
            Seller newSeller = new Seller();
            User newUser = new User();
            newUser.setEmail(sellerRegister.getEmail());
            newUser.setFirstName(sellerRegister.getFirstName());
            newUser.setMiddleName(sellerRegister.getMiddleName());
            newUser.setLastName(sellerRegister.getLastName());
            newUser.setPassword(bCryptPasswordEncoder.encode(sellerRegister.getPassword()));
            Address address = new Address();
            address.setAddressLine(sellerRegister.getAddressLine());
            address.setCity(sellerRegister.getCity());
            address.setCountry(sellerRegister.getCountry());
            address.setState(sellerRegister.getState());
            address.setZipcode(sellerRegister.getZipcode());
            address.setLabel(sellerRegister.getLabel());
            newUser.addAddresses(address);
            newUser.setActive(false);
            newUser.setDeleted(false);
            Role role = new Role();
            role.setAuthority("ROLE_SELLER");
            newUser.setRoles(new ArrayList<>(Arrays.asList(role)));
            newSeller.setCompanyContactNo(sellerRegister.getContact());
            newSeller.setCompanyName(sellerRegister.getCompanyName());
            newSeller.setGst(sellerRegister.getGst());
            sellerRepository.save(newSeller);
            ConfirmationToken confirmationToken= new ConfirmationToken(newUser);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(sellerRegister.getEmail());
            message.setFrom("damineesaini11@gmail.com");
            message.setSubject("Complete Registration");
            message.setText("To confirm your account, please lick here:"+"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
            emailSendService.sendEmail(message);
            return newSeller;
        }
    }

    @Transactional
    public String confirmCustomerAccount(String confirmationToken) throws TokenExpiredException {
        ConfirmationToken confirmationToken1 = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(confirmationToken1!=null) {
            Date presentDate = new Date();
            if (confirmationToken1.getExpiryDate().getTime() - presentDate.getTime() <= 0) {
                User userExists = confirmationToken1.getUser();
                confirmationTokenRepository.deleteConfirmationToken(confirmationToken);
                ConfirmationToken confirmationToken2 = new ConfirmationToken(userExists);
                confirmationTokenRepository.save(confirmationToken2);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(userExists.getEmail());
                message.setFrom("damineesaini11@gmail.com");
                message.setSubject("Complete Registration");
                message.setText("To confirm your account, please lick here:"+"http://localhost:8080/confirm-account?token="+confirmationToken1.getConfirmationToken());
                emailSendService.sendEmail(message);
                throw new TokenExpiredException("Token has been expired!! New activation link send on your email");
            } else {
                User user = userRepository.findByEmail(confirmationToken1.getUser().getEmail());
                user.setActive(true);
                confirmationTokenRepository.deleteConfirmationToken(confirmationToken);
                return "ThankYou , Your account has been verified";
            }
        }
        else {
            return "Error! Please try again";
        }
    }

    @Transactional
    public String resendActivationToken(String email){
        User userExists = userRepository.findByEmail(email);
        if (userExists != null){
            if(!userExists.isActive()){
                ConfirmationToken confirmationToken = null;
                confirmationTokenRepository.findByUser(userExists);

                if (confirmationToken!=null){
                    String token = confirmationToken.getConfirmationToken();
                    Date presentDate= new Date();
                    if(confirmationToken.getExpiryDate().getTime() - presentDate.getTime() <=0){
                        confirmationTokenRepository.deleteConfirmationToken(token);

                        ConfirmationToken confirmationToken1 = new ConfirmationToken(userExists);
                        confirmationTokenRepository.save(confirmationToken1);

                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(userExists.getEmail());
                        message.setFrom("damineesaini11@gmail.com");
                        message.setSubject("Complete Registration");
                        message.setText("To confirm your account, please lick here:"+"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
                        emailSendService.sendEmail(message);

                        return "New activation link is successfully sent on your registered email";
                    }
                    else {
                        return "Your current activation link is not expired yet,please use the already existing link to activate";
                    }
                }
                else {
                    ConfirmationToken newConfirmationToken =new ConfirmationToken(userExists);
                    confirmationTokenRepository.save(newConfirmationToken);

                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(userExists.getEmail());
                    message.setFrom("damineesaini11@gmail.com");
                    message.setSubject("Complete Registration");
                    message.setText("To confirm your account, please lick here:"+"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
                    emailSendService.sendEmail(message);

                    return "New activation link is successfully sent on your registered email";
                }
            }
            else {
                return "Account is already active. No need to generate an activation token";
            }
        }
        else {
            throw new UsernameNotFoundException("Invalid email entered");
        }
    }
}
