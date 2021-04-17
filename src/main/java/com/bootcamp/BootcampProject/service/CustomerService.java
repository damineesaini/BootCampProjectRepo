package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CustomerUpdate;
import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Address;
import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.UnauthorizedAccessException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.repository.AddressRepository;
import com.bootcamp.BootcampProject.repository.CustomerRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailSendService emailSendService;

    public Customer getLoggedInCustomer() throws UserNotFoundException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        String username= appUserDetails.getUsername();
        if (userRepository.findByEmail(username)!=null){
            User user = userRepository.findByEmail(username);
            return  customerRepository.findByUserId(user);
        }
        else {
            throw new UserNotFoundException("invalid email. User not found.");
        }

    }

    public MappingJacksonValue getProfile() throws UserNotFoundException {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","email","isActive","profileImage");
        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id","userId","contact");
        SimpleBeanPropertyFilter filter2 =SimpleBeanPropertyFilter.filterOutAllExcept("filename","path");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter",filter).addFilter("customerFilter",filter1).addFilter("imageFilter",filter2);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getLoggedInCustomer());
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    public MappingJacksonValue getAddress() throws UserNotFoundException {
        UUID id = getLoggedInCustomer().getUserId().getId();
        List<Address> addressList=addressRepository.findAllByUserId(id);
        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id","addressLine","city","state","country","zipcode","label");
        FilterProvider filters = new SimpleFilterProvider().addFilter("addressFilter",filter1);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(addressList);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @Transactional
    @Modifying
    public String updateCustomerProfile(CustomerUpdate customerUpdate, UUID id) throws UserNotFoundException, UnauthorizedAccessException {
        if(userRepository.findById(id).isPresent()) {
            User user1 = userRepository.findById(id).get();
            Customer customer = customerRepository.findByUserId(user1);
            if (customer.equals(getLoggedInCustomer())) {
                if (customerUpdate.getFirstName() != null) {
                    user1.setFirstName(customerUpdate.getFirstName());
                }
                if (customerUpdate.getMiddleName() != null) {
                    user1.setMiddleName(customerUpdate.getMiddleName());
                }
                if (customerUpdate.getLastName() != null) {
                    user1.setLastName(customerUpdate.getLastName());
                }
                if (customerUpdate.getContactNo() != null) {
                    customer.setContactNo(customerUpdate.getContactNo());
                }
                customer.setUserId(user1);
                customerRepository.save(customer);
                return "Profile updated Successfully";
            }
            else {
                throw new UnauthorizedAccessException("Trying to access another user account");
            }
        }
        else {
            throw new UserNotFoundException("invalid user id");
        }
    }

    @Transactional
    @Modifying
    public String addAddress(NewAddress newAddress,UUID id) throws UserNotFoundException, UnauthorizedAccessException {
        if(userRepository.findById(id).isPresent()) {
            User user1 = userRepository.findById(id).get();
            Customer customer = customerRepository.findByUserId(user1);
            if (customer.equals(getLoggedInCustomer())) {
                Address address = new Address();
                address.setAddressLine(newAddress.getAddressLine());
                address.setCity(newAddress.getCity());
                address.setState(newAddress.getState());
                address.setCountry(newAddress.getCountry());
                address.setZipcode(newAddress.getZipcode());
                address.setLabel(newAddress.getLabel());
                address.setUserId(user1);
                addressRepository.save(address);

                return "address added";
            }
            else {
                throw new UnauthorizedAccessException("trying to access another users data");
            }
        }
        else {
            throw new UserNotFoundException("invalid user id. user is not found.");
        }
    }

    @Transactional
    @Modifying
    public String deleteAddress(UUID addressId, UUID id) throws DoesNotExistException, UnauthorizedAccessException, UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            User user1 = userRepository.findById(id).get();
            Customer customer = customerRepository.findByUserId(user1);
            if (customer.equals(getLoggedInCustomer())){
                if (addressRepository.findById(addressId).isPresent()) {
                    Address address = addressRepository.findById(addressId).get();
                    address.setDelete(true);
                    addressRepository.save(address);
                    return "Address Deleted";
                } else {
                    throw new DoesNotExistException("Address id does not exist");
                }
            } else {
                    throw new UnauthorizedAccessException("trying delete another users address");
            }
        } else {
                throw new DoesNotExistException("User does not exist");
        }
    }

    @Transactional
    @Modifying
    public String updateAddress(NewAddress newAddress,UUID addressId,UUID id) throws DoesNotExistException, UserNotFoundException, UnauthorizedAccessException {
        if (userRepository.findById(id).isPresent()) {
            User user1 = userRepository.findById(id).get();
            Customer customer = customerRepository.findByUserId(user1);
            if (customer.equals(getLoggedInCustomer())){
        if (addressRepository.findById(addressId).isPresent()){
            Address updatedAddress= addressRepository.findById(addressId).get();

            if (updatedAddress.getUserId().getId().equals(id)){
                if (newAddress.getAddressLine()!=null){
                    updatedAddress.setAddressLine(newAddress.getAddressLine());
                }
                if (newAddress.getCity()!=null){
                    updatedAddress.setCity(newAddress.getCity());
                }
                if (newAddress.getState()!=null){
                    updatedAddress.setState(newAddress.getState());
                }
                if (newAddress.getCountry()!=null){
                    updatedAddress.setCountry(newAddress.getCountry());
                }
                if (newAddress.getZipcode()!=0){
                    updatedAddress.setZipcode(newAddress.getZipcode());
                }
                if (newAddress.getLabel()!=null){
                    updatedAddress.setLabel(newAddress.getLabel());
                }
            }
            return "address updated successfully";
        }
        else {
            throw new DoesNotExistException("Address id does not exist");
        }
            } else {
                throw new UnauthorizedAccessException("trying delete another users address");
            }
        } else {
            throw new DoesNotExistException("User does not exist");
        }
    }

    public String updatePassword(UpdatePasswordDto updatePasswordDto , UUID id) throws UserNotFoundException, UnauthorizedAccessException {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            Customer customer = customerRepository.findByUserId(user);
            if (customer.equals(getLoggedInCustomer())) {
                String oldPassword = updatePasswordDto.getOldPassword();

                if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
                    String newPassword = updatePasswordDto.getNewPassword();
                    String confirmPassword = updatePasswordDto.getConfirmPassword();

                    if (newPassword.equals(confirmPassword)) {
                        String updatePassword = bCryptPasswordEncoder.encode(newPassword);
                        user.setPassword(updatePassword);
                        userRepository.save(user);

                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(user.getEmail());
                        message.setFrom("damineesaini1111@gmail.com");
                        message.setSubject("Password Updated!!");
                        message.setText("Your account password has been update.");
                        emailSendService.sendEmail(message);
                    }
                }
                return "password updated successfully!!";
            } else {
                throw new UnauthorizedAccessException("trying to update someone else passowrd");
            }
        }
            else {
            throw new UserNotFoundException("user not found. invalid user id");
        }
    }
}