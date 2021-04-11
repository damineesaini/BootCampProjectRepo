package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.NewAddress;
import com.bootcamp.BootcampProject.dto.request.SellerUpdate;
import com.bootcamp.BootcampProject.dto.request.UpdatePasswordDto;
import com.bootcamp.BootcampProject.entity.user.Address;
import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
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
        return  sellerRepository.findByUserId(user);
    }

    public MappingJacksonValue getProfile(){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","email","addresses","profileImage");
        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("addressLine","city","state","country","zipcode");
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("id","userId","companyName","companyContactNo","gst");
        SimpleBeanPropertyFilter filter3 =SimpleBeanPropertyFilter.filterOutAllExcept("filename","path");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter",filter).addFilter("addressFilter",filter1).addFilter("sellerFilter",filter2).addFilter("imageFilter",filter3);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(getLoggedInSeller());
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @Transactional
    @Modifying
    public String updateSellerProfile(SellerUpdate sellerUpdate, UUID id) throws UserNotFoundException {
        if(userRepository.findById(id).isPresent()){
            User user1 = userRepository.findById(id).get();
            Seller seller = sellerRepository.findByUserId(user1);
            if (sellerUpdate.getFirstName()!=null){
                user1.setFirstName(sellerUpdate.getFirstName());
            }
            if(sellerUpdate.getMiddleName()!=null){
                user1.setMiddleName(sellerUpdate.getMiddleName());
            }
            if(sellerUpdate.getLastName()!=null){
                user1.setLastName(sellerUpdate.getLastName());
            }
            if(sellerUpdate.getCompanyContactNo()!= null)
                seller.setCompanyContactNo(sellerUpdate.getCompanyContactNo());

            seller.setUserId(user1);
            sellerRepository.save(seller);
            return  "Profile updated Successfully";
        }
else {
    throw new UserNotFoundException("user does not found. invalid user id");
        }
    }

    @Transactional
    @Modifying
    public String updateAddress(NewAddress newAddress, UUID addressId, UUID id){
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

    @Transactional
    @Modifying
    public String updatePassword(UpdatePasswordDto updatePasswordDto , UUID id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();

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
        }
        return "user not found";
    }
}