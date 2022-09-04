package com.fridgerescuer.springboot.exception.exceptionimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoSuchIngredientException extends RuntimeException {

    public NoSuchIngredientException(Throwable cause) {
        super(cause);
    }

    public NoSuchIngredientException(String message, Throwable cause) {
        super(message, cause);
    }
}
