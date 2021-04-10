package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.ForgotPassword;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.PasswordDoesNotMatchException;
import com.bootcamp.BootcampProject.exception.TokenExpiredException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class ForgotPasswordController {
    @Autowired
    ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgot-password")
    public String resetPassword(@RequestParam("email") String email) throws UserNotFoundException {
        return forgotPasswordService.resetPassword(email);
    }

    @PutMapping("/reset-password")
    public String updatePassword(@RequestParam("token") String resetToken, @Valid @RequestBody ForgotPassword forgotPassword) throws Exception, PasswordDoesNotMatchException, DoesNotExistException, TokenExpiredException {
        return forgotPasswordService.updatePassword(resetToken,forgotPassword);
    }
}
