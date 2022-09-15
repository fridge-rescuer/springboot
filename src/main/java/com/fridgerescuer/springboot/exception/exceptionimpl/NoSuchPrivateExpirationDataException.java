package com.fridgerescuer.springboot.exception.exceptionimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoSuchPrivateExpirationDataException extends RuntimeException {

    public NoSuchPrivateExpirationDataException(Throwable cause) {
        super(cause);
    }

    public NoSuchPrivateExpirationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
