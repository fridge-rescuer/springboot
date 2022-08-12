package com.fridgerescuer.springboot.exception.data.repository;

public class NoSuchRecipeException extends RuntimeException{
    public NoSuchRecipeException() {
    }

    public NoSuchRecipeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRecipeException(Throwable cause) {
        super(cause);
    }
}
