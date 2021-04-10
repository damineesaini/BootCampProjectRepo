package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.image.Image;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.repository.ImageRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ImageRepository imageRepository;

    public User getLoggedInUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        String username= appUserDetails.getUsername();
        User user = userRepository.findByEmail(username);
        return user;
    }

    public String saveProductVariationImage(Image image, UUID id) throws Exception {
        if (productVariationRepository.findById(id).isPresent()){
            ProductVariation productVariation = productVariationRepository.findById(id).get();
            imageRepository.save(image);
            productVariation.setProductImage(image);
            productVariationRepository.save(productVariation);
            return "Image successfully uploaded";
        }
        else {
            throw new Exception("product variation does not exists");
        }
    }

    public String saveImage(Image image, UUID id) throws Exception {
        if (userRepository.findById(id).isPresent()){
            User user = userRepository.findById(id).get();
            imageRepository.save(image);
            user.setProfileImage(image);
            userRepository.save(user);
            return "Image successfully uploaded";
        }
        else {
            throw new Exception("product variation does not exists");
        }
    }

}
