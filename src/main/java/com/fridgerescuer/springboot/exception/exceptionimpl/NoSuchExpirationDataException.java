package com.fridgerescuer.springboot.exception.exceptionimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoSuchExpirationDataException extends RuntimeException {

    public NoSuchExpirationDataException(Throwable cause) {
        super(cause);
    }

    public NoSuchExpirationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
