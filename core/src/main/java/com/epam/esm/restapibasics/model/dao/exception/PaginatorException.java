package com.epam.esm.restapibasics.model.dao.exception;

public class PaginatorException extends RuntimeException {
    public PaginatorException() {
    }

    public PaginatorException(String message) {
        super(message);
    }

    public PaginatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaginatorException(Throwable cause) {
        super(cause);
    }

    public PaginatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
