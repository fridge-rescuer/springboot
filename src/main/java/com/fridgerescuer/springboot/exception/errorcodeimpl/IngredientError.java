package com.fridgerescuer.springboot.exception.errorcodeimpl;

import com.fridgerescuer.springboot.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum IngredientError implements ErrorCode {
    NOT_EXIST(HttpStatus.FORBIDDEN, "존재하지 않는 재료입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
