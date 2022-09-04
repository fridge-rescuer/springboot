package com.fridgerescuer.springboot.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;
/**
 * DTO의 Validation 평가 후 에러가 났을 시 저장하기 위한 공간
 */
@Builder
@Getter
@RequiredArgsConstructor
public class ValidationError {

    private final String field;
    private final String message;

    public static ValidationError of(final FieldError fieldError) {
        return ValidationError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build();
    }
}