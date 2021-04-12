package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/home")
    public String adminHome(){
        return "Welcome Admin";
    }

    @GetMapping("/allCustomer")
    public MappingJacksonValue retrieveAllCustomers(){
        return adminService.findAllCustomer();
    }

    @GetMapping("/allSeller")
    public MappingJacksonValue retrieveAllSeller(){
        return adminService.findAllSeller();
    }

    @PutMapping(value ="/activate-customer/{userId}")
    public String customerActivation(@PathVariable String userId) throws UserNotFoundException {
        UUID userid = UUID.fromString(userId);
        return adminService.activateUser(userid);
    }

    @PutMapping("/deactivate-customer/{userId}")
    public String customerDeactivation(@PathVariable String userId) throws UserNotFoundException {
        UUID userid = UUID.fromString(userId);
        return adminService.deactivateUser(userid);
    }

    @PutMapping("/activate-seller/{userId}")
    public String sellerActivation(@PathVariable String userId) throws UserNotFoundException {
        UUID userid = UUID.fromString(userId);
        return adminService.activateUser(userid);
    }

    @PutMapping("/deactivate-seller/{userId}")
    public String sellerDeactivation(@PathVariable String userId) throws UserNotFoundException {
        UUID userid = UUID.fromString(userId);
        return adminService.deactivateUser(userid);
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws UserNotFoundException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                String tokenValue = authHeader.replace("bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
            } catch (Exception e) {
                throw new UserNotFoundException("usernot found");
            }
        }
        return "Logged out successfully";
    }

}