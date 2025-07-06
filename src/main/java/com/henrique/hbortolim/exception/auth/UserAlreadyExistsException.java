package com.henrique.hbortolim.exception.auth;

public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String field, String value) {
        super(String.format("User with %s '%s' already exists", field, value));
    }
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
} 