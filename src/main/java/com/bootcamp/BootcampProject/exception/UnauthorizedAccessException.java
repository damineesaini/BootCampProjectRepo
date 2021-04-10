package com.bootcamp.BootcampProject.exception;

public class UnauthorizedAccessException extends Throwable{
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
