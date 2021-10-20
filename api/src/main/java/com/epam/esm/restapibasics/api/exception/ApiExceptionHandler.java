package com.epam.esm.restapibasics.api.exception;

import com.epam.esm.restapibasics.model.dao.exception.EntityAlreadyExistsException;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.service.exception.DaoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(String.format("requested entity not found id = %d", e.getEntityId()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        ErrorDto errorDto = new ErrorDto("entity already created", HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {DaoResultException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(DaoResultException e) {
        ErrorDto errorDto = new ErrorDto("bad request", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
