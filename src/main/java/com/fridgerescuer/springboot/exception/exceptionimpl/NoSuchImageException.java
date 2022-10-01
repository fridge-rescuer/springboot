package com.fridgerescuer.springboot.exception.exceptionimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoSuchImageException extends RuntimeException {

    public NoSuchImageException(Throwable cause) {
        super(cause);
    }

    public NoSuchImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
