package com.bootcamp.BootcampProject.exception;

public class PasswordDoesNotMatchException extends Throwable{
    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
