package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.Pattern;

public class UpdatePasswordDto {
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String oldPassword;
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String newPassword;
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
