package com.gavin.exception;

public class UsernameExistedException extends RuntimeException {

    public UsernameExistedException(String message) {
        super(message);
    }

    public UsernameExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
