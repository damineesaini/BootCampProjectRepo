package com.bootcamp.BootcampProject.exception;

public class ProductNotFoundException extends Throwable{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
