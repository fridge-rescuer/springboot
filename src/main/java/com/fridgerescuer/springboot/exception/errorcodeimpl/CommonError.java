package com.fridgerescuer.springboot.exception.errorcodeimpl;

import com.fridgerescuer.springboot.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonError implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터입니다");

    private final HttpStatus httpStatus;
    private final String message;}
