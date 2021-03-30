package com.bootcamp.BootcampProject.exception;

public class TokenExpiredException extends Throwable{
    public TokenExpiredException(String message) {
        super(message);
    }
}
