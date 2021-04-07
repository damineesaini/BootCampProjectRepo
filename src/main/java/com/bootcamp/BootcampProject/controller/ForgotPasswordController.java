package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.ForgotPassword;
import com.bootcamp.BootcampProject.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ForgotPasswordController {
    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public String resetPassword(@RequestBody String email){
        return forgotPasswordService.resetPassword(email);
    }

    @PutMapping("/reset-password")
    public String updatePassword(@RequestParam("token") String resetToken, @Valid @RequestBody ForgotPassword forgotPassword) throws Exception {
        return forgotPasswordService.updatePassword(resetToken,forgotPassword);
    }
}
