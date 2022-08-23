package com.fridgerescuer.springboot.exception.data.repository;

public class NoSuchCommentException extends RuntimeException{
    public NoSuchCommentException(Throwable cause) {
        super(cause);
    }

    public NoSuchCommentException() {
    }

    public NoSuchCommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
