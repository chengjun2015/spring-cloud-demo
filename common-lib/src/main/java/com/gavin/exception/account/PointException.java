package com.gavin.exception.account;

public class PointException extends RuntimeException {

    public PointException(String message) {
        super(message);
    }

    public PointException(String message, Throwable cause) {
        super(message, cause);
    }
}
