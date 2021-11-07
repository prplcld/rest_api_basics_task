package com.epam.esm.restapibasics.api.exception;

import com.epam.esm.restapibasics.service.exception.EmptyOrderException;
import com.epam.esm.restapibasics.service.exception.EntityAlreadyExistsException;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String NOT_FOUND_ERROR = "entity_not_found";
    private static final String ENTITY_ALREADY_CREATED_ERROR = "entity_already_created";
    private static final String EMPTY_ORDER_ERROR = "empty_order";

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        int errorCode = 404;
        String message = getMessage(NOT_FOUND_ERROR);
        switch (e.getCauseEntity().getSimpleName()) {
            case "Tag" -> {
                errorCode = 40401;
            }
            case "GiftCertificate" -> {
                errorCode = 40402;
            }
            case "Order" -> {
                errorCode = 40403;
            }
            case "User" -> {
                errorCode = 40404;
            }
        }
        ErrorDto errorDto = new ErrorDto(String.format(message, e.getEntityId()), errorCode);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmptyOrderException.class)
    public ResponseEntity<Object> handleEmptyOrderException(EmptyOrderException e) {
        String message = getMessage(EMPTY_ORDER_ERROR);
        ErrorDto errorDto = new ErrorDto(message, 409);
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        String message = getMessage(ENTITY_ALREADY_CREATED_ERROR);
        ErrorDto errorDto = new ErrorDto(message, 409);
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), 500);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(String errorName) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(errorName, null, locale);
    }


}
