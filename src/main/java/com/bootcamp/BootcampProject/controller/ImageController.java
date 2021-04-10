package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.entity.image.Image;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import com.bootcamp.BootcampProject.service.CustomerService;
import com.bootcamp.BootcampProject.service.ImageService;
import com.bootcamp.BootcampProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    SellerService sellerService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductVariationRepository productVariationRepository;

    private static String FOLDER_PATH="/home/ttn/Desktop/BootCampProjectRepo-master";


    @PostMapping("/customer/uploadIMage")
    public ResponseEntity<Object> uploadCustomerProfileImage(@RequestBody MultipartFile file) throws Exception {
        Customer customer = customerService.getLoggedInCustomer();
        if (file.isEmpty()){
            throw new Exception("Upload image please!");
        }
        try {
            Path fileStorageLocation = Paths.get(FOLDER_PATH).toAbsolutePath().normalize();
            System.out.println(fileStorageLocation);
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            System.out.println(originalFileName);
            String fileName = "";
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            System.out.println(extension);

            if (extension.equals(".jpeg")||extension.equals(".jpg")||extension.equals(".png")){
                fileName= customer.getUserId().getId() + extension;
                System.out.println(fileName);
                System.out.println(fileStorageLocation.resolve("images/users/"));
                Path targetLocation = fileStorageLocation.resolve("images/users/"+fileName);
                System.out.println(targetLocation);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                Image image =new Image(file.getOriginalFilename(),file.getContentType(),file.getBytes());
                image.setFilename(customer.getUserId().getId().toString()+"."+extension);
                image.setPath(targetLocation.toString());
                image.setCreateDate(new Date());
                image.setUserId(customer.getUserId());

                String message = imageService.saveImage(image,customer.getUserId().getId());
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

    @PostMapping("/seller/uploadIMage")
    public ResponseEntity<Object> uploadSellerProfileImage(@RequestBody MultipartFile file) throws Exception {
        Seller seller = sellerService.getLoggedInSeller();
        if (file.isEmpty()){
            throw new Exception("Upload image please!");
        }
        try {
            Path fileStorageLocation = Paths.get(FOLDER_PATH).toAbsolutePath().normalize();
            System.out.println(fileStorageLocation);
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            System.out.println(originalFileName);
            String fileName = "";
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            System.out.println(extension);

            if (extension.equals(".jpeg")||extension.equals(".jpg")||extension.equals(".png")){
            fileName= seller.getUserId().getId() + extension;
            System.out.println(fileName);
            System.out.println(fileStorageLocation.resolve("/users/"));
            Path targetLocation = fileStorageLocation.resolve(fileName);
            System.out.println(targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Image image =new Image(file.getOriginalFilename(),file.getContentType(),file.getBytes());
            image.setFilename(seller.getUserId().getId().toString()+"."+extension);
            image.setPath(targetLocation.toString());
            image.setCreateDate(new Date());
                image.setUserId(seller.getUserId());

            String message = imageService.saveImage(image,seller.getUserId().getId());
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
