package com.epam.esm.restapibasics.api.exception;

import org.springframework.http.HttpStatus;

public class ErrorDto {

    private final String message;
    private final HttpStatus httpStatus;

    public ErrorDto(String message, HttpStatus httpStatus) {
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
