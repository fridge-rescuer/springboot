package com.fridgerescuer.springboot.exception.data.repository;

public class NoSuchMemberException extends RuntimeException{
    public NoSuchMemberException() {
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }
}
