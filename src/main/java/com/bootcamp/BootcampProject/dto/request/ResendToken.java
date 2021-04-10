package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.Email;

public class ResendToken {
    @Email(message = "Email should be valid")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
