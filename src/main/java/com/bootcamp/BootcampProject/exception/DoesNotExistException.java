package com.bootcamp.BootcampProject.exception;

public class DoesNotExistException extends  Throwable{
    public DoesNotExistException(String message) {
        super(message);
    }
}
