package com.epam.esm.restapibasics.service.exception;

public class UnableToCreateOrderException extends RuntimeException {
    public UnableToCreateOrderException() {
    }

    public UnableToCreateOrderException(String message) {
        super(message);
    }

    public UnableToCreateOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToCreateOrderException(Throwable cause) {
        super(cause);
    }
}
