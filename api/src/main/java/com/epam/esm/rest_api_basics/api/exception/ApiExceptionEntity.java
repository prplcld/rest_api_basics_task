package com.epam.esm.rest_api_basics.api.exception;

import org.springframework.http.HttpStatus;

public class ApiExceptionEntity {

    private final String message;
    private final HttpStatus httpStatus;

    public ApiExceptionEntity(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
