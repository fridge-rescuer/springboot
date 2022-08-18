package com.fridgerescuer.springboot.exception.data.repository;

public class NoSuchIngredientException extends RuntimeException{
    public NoSuchIngredientException(Throwable cause) {
        super(cause);
    }

    public NoSuchIngredientException() {
    }

    public NoSuchIngredientException(String message, Throwable cause) {
        super(message, cause);
    }
}
