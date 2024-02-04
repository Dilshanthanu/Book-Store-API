package com.example.BackEnd.ExceptionHandler;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}

