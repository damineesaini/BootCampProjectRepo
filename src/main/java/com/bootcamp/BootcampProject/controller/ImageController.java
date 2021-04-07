package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.entity.image.Image;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.SellerRepository;
import com.bootcamp.BootcampProject.service.ImageService;
import com.bootcamp.BootcampProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ImageController {
    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    private static String FOLDER_PATH="/home/ttn/Desktop/BootcampProjectRepo-master/images";

    @PostMapping("/profile/uploadIMage")
    public ResponseEntity<Object> uploadProfileImage(@RequestParam("file") MultipartFile file) throws Exception {
        User user = imageService.getLoggedInUser();
        UUID id =user.getId();
        if (file.isEmpty()){
            throw new Exception("Upload image please!");
        }
        try {
            String extension = null;
            byte[] bytes = file.getBytes();
            extension=file.getOriginalFilename().split("\\.")[1];
            if (extension.equals("jpeg")||extension.equals("jpg")||extension.equals("png")){
                Path path = Paths.get(FOLDER_PATH+"/users"+id+"."+extension);
                Files.write(path,bytes);

                Image image =new Image(file.getOriginalFilename(),file.getContentType(),file.getBytes());
                image.setFilename(id.toString()+"."+extension);
                image.setPath(path.toString());
                image.setCreateDate(new Date());
                image.setUserId(user);

                String message = imageService.saveImage(image,id);
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            }
            else {
                throw new Exception("Invalid file format. Kindly use jpg,jpeg and png");
            }
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/product/uploadImage/{productId}")
    public ResponseEntity<Object> uploadProductImage(@RequestParam("file") MultipartFile file, @PathVariable("productId") String productId) throws Exception {
        User user = imageService.getLoggedInUser();
        UUID id =user.getId();
        Seller seller = sellerRepository.findByUserId(user);
        UUID productid = UUID.fromString(productId);
        Optional<Product> optionalProduct = productRepository.findById(productid);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            UUID sellerId = product.getSellerUserId().getUserId().getId();
            if (sellerId.equals(id)) {
                try {
                    String extension = null;
                    byte[] bytes = file.getBytes();
                    extension = file.getOriginalFilename().split("\\.")[1];
                    if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) {
                        Path path = Paths.get(FOLDER_PATH + "/products" + id + "." + extension);
                        Files.write(path, bytes);

                        Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                        image.setFilename(id.toString() + "." + extension);
                        image.setPath(path.toString());
                        image.setCreateDate(new Date());
                        image.setUserId(user);

                        String message = imageService.saveImage(image, productid);
                        return new ResponseEntity<>(message, HttpStatus.CREATED);
                    } else {
                        throw new Exception("Invalid file format. Kindly use jpg,jpeg and png");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            else {
                throw new Exception("you do not have perission");
            }
        }
        else {
            throw new Exception("product is not available");
        }
    }

    @GetMapping("/download/profileImage")
    public ResponseEntity<Resource> downloadProfileImage(){
        User user = imageService.getLoggedInUser();
        UUID imageId = user.getProfileImage().getId();
        Image image =imageService.downloadUserImage(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFilename())
                .body(new ByteArrayResource(image.getData()));
    }

//    @GetMapping("/download/productImage/{productId}")
//    public ResponseEntity<Resource> downloadProductImage(@PathVariable("productId") String productId) throws Exception {
//        UUID productid = UUID.fromString(productId);
//        Optional<Product> optionalProduct = productRepository.findById(productid);
//        if (optionalProduct.isPresent()) {
//            Product product = optionalProduct.get();
//            if (product.isActive()&&!product.isDelete()){
//                UUID imageId=product.getProductImage().getId();
//                if (imageId==null){
//                    throw new Exception("resource not found for the requested product");
//                }
//                UUID sellerId= product.getSellerUserId().getUserId().getId();
//                User user = imageService.getLoggedInUser();
//                String role = user.getRoles().stream().filter(role1 -> role1.getAuthority().equals("ROLE_SELLER")).toString();
//                if (role.equals("ROLE_SELLER")){
//                    if(sellerId.equals(user.getId())){
//                        Image image = imageService.downloadUserImage(imageId);
//                    }
//                }
//            }
//
//        }
//    }

}
