package com.epam.esm.restapibasics.api.exception;

public class ErrorDto {

    private final String message;
    private final Integer errorCode;

    public ErrorDto(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
