package com.epam.esm.restapibasics.api.exception;

import com.epam.esm.restapibasics.service.exception.EntityAlreadyExistsException;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.service.exception.DaoResultException;
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
    private static final String DAO_ERROR = "dao_error";

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        String message = getMessage(NOT_FOUND_ERROR);
        ErrorDto errorDto = new ErrorDto(String.format(message, e.getEntityId()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        String message = getMessage(ENTITY_ALREADY_CREATED_ERROR);
        ErrorDto errorDto = new ErrorDto(message, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {DaoResultException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(DaoResultException e) {
        String message = getMessage(DAO_ERROR);
        ErrorDto errorDto = new ErrorDto(message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(String errorName) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(errorName, null, locale);
    }
}
