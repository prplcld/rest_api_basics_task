package com.epam.esm.restapibasics.model.dao.exception;

public class NoTagFoundException extends Exception {

    public NoTagFoundException() {
    }

    public NoTagFoundException(String message) {
        super(message);
    }

    public NoTagFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTagFoundException(Throwable cause) {
        super(cause);
    }
}
