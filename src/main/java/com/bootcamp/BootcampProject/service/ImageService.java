package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.image.Image;
import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.User;
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

    public User getLoggedInUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        String username= appUserDetails.getUsername();
        User user = userRepository.findByEmail(username);
        return user;
    }

    public String saveImage(Image image, UUID id) {

    }

    public Image downloadUserImage(UUID imageId){

    }
}
