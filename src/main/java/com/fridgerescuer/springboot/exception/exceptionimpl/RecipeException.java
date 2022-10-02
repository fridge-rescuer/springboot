package com.fridgerescuer.springboot.exception.exceptionimpl;

import com.fridgerescuer.springboot.exception.errorcodeimpl.RecipeError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecipeException extends RuntimeException {
    private final RecipeError errorCode;
}
