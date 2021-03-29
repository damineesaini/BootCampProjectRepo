package com.bootcamp.BootcampProject.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String invalid_username_entered) { super(invalid_username_entered);
    }
}
