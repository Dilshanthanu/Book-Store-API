package com.example.BackEnd.ExceptionHandler;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
