package com.bootcamp.BootcampProject.utility;

import org.springframework.stereotype.Component;

@Component
public class Welcome {
    private String message;

    public Welcome() {
    }

    public Welcome(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

