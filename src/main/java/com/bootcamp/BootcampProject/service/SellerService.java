package com.bootcamp.BootcampProject.service;


import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.SellerUpdate;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Address;
import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.repository.AddressRepository;
import com.bootcamp.BootcampProject.repository.SellerRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailSendService emailSendService;

    public Seller getLoggedInSeller(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        String username= appUserDetails.getUsername();
        User user = userRepository.findByEmail(username);
        return  sellerRepository.findBy(user);
    }

    public MappingJacksonValue getProfile(){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","isActive","contactNo");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userDynamicFilter",filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getLoggedInSeller());
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    public MappingJacksonValue getAddress(){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("addresses");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userDynamicFilter",filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getLoggedInSeller());
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @Transactional
    @Modifying
    public String updateSellerProfile(SellerUpdate sellerUpdate, UUID id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User user1 = user.get();
            Seller seller = sellerRepository.findBy(user1);
            if (sellerUpdate.getFirstName()!=null){
                user1.setFirstName(sellerUpdate.getFirstName());
            }
            if(sellerUpdate.getMiddleName()!=null){
                user1.setMiddleName(sellerUpdate.getMiddleName());
            }
            if(sellerUpdate.getLastName()!=null){
                user1.setLastName(sellerUpdate.getLastName());
            }
            if(sellerUpdate.getEmail()!=null){
                user1.setEmail(sellerUpdate.getEmail());
            }
            if(sellerUpdate.getCompanyContactNo()!= 0l)
                seller.setCompanyContactNo(sellerUpdate.getCompanyContactNo());

            seller.setUserId(user1);
            sellerRepository.save(seller);
        }
        return  "Profile updated Successfully";
    }

    @Transactional
    @Modifying
    public String updateAddress(NewAddress newAddress, UUID addressId, UUID id){
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()){
            Address updatedAddress= address.get();

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
                if (newAddress.getZipcode()!=0l){
                    updatedAddress.setZipcode(newAddress.getZipcode());
                }
                if (newAddress.getLabel()!=null){
                    updatedAddress.setLabel(newAddress.getLabel());
                }
            }
        }
        return "address updated successfully";
    }

    public String updatePassword(UpdatePasswordDto updatePasswordDto , String username){
        User user = userRepository.findByEmail(username);

        String oldPassword = updatePasswordDto.getOldPassword();

        if (bCryptPasswordEncoder.matches(oldPassword,user.getPassword())){
            String newPassword = updatePasswordDto.getNewPassword();
            String confirmPassword = updatePasswordDto.getConfirmPassword();

            if (newPassword.equals(confirmPassword)){
                String updatePassword=bCryptPasswordEncoder.encode(newPassword);
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
    }
}
