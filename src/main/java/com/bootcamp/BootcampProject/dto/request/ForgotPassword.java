package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.Pattern;

public class ForgotPassword {
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String password;
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
