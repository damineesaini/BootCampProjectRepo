package com.bootcamp.BootcampProject.exception;

public class AccountLockException extends Throwable{
    public AccountLockException(String message) {
        super(message);
    }
}
