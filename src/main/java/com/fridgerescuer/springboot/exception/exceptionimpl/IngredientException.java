package com.fridgerescuer.springboot.exception.exceptionimpl;

import com.fridgerescuer.springboot.exception.errorcodeimpl.IngredientError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IngredientException extends RuntimeException {
    private final IngredientError errorCode;
}
