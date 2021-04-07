package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.service.ImageService;
import com.bootcamp.BootcampProject.service.ProductService;
import com.bootcamp.BootcampProject.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    UserDaoService userDaoService;

    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    private static String FOLDER_PATH="/home/ttn/Desktop/BootcampProjectRepo-master/images";

//    @PostMapping("/profile/uploadIMage")
//    public ResponseEntity<Object>uploadProfileImage(@RequestParam("file") MultipartFile file){
//        User user =
//    }

}
