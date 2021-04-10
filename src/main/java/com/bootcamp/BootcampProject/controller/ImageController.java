package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.entity.image.Image;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import com.bootcamp.BootcampProject.service.ImageService;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    SellerService sellerService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

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

    @PostMapping("/profile/uploadProductVariationImage")
    public String uploadProductVariationImage(@RequestParam("file") MultipartFile file, @Param("productVariationId") String productVariationId) throws Exception, DoesNotExistException {
        if (file.isEmpty()) {
            throw new IOException("Upload Image");
        }
        if (productVariationRepository.findById(UUID.fromString(productVariationId)).isPresent()) {
            ProductVariation productVariation = productVariationRepository.findById(UUID.fromString(productVariationId)).get();
            UUID prodId = productVariation.getProductId().getId();
            Seller seller = sellerService.getLoggedInSeller();
            String message=null;
            try {
                String extension = null;
                byte[] bytes = file.getBytes();
                extension = file.getOriginalFilename().split("\\.")[1];
                if (extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")) {
                    File newPath = new File(FOLDER_PATH + "/products" + "/variations");
                    if (!newPath.exists()) {
                        if (newPath.mkdirs()) {
                            System.out.println("New directory created");
                        } else {
                            System.out.println("Failed to create new folder");
                        }
                    }
                    UUID id = UUID.randomUUID();
                    Path path = Paths.get(newPath.toString() + "/" + id + "." + extension);
                    Files.write(path, bytes);
                    Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                    image.setFilename(id.toString() + "." + extension);
                    image.setPath(path.toString());
                    image.setCreateDate(new Date());
                    image.setUserId(seller.getUserId());
                    productVariation.setProductImage(image);
                    productVariationRepository.save(productVariation);
                    message = imageService.saveProductVariationImage(image, id);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }
        else {
            throw new DoesNotExistException("product variation with given id does not exist.");
        }

    }


}
