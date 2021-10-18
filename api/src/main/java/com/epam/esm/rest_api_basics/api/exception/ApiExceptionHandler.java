package com.epam.esm.rest_api_basics.api.exception;

import com.epam.esm.rest_api_basics.model.dao.exception.EntityAlreadyExistsException;
import com.epam.esm.rest_api_basics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.rest_api_basics.model.dao.exception.NoIdException;
import com.epam.esm.rest_api_basics.service.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler { //TODO

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity(String.format("requested entity not found id = %d", e.getEntityId()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiExceptionEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity("entity already created", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiExceptionEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoIdException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(NoIdException e) {
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity("wrong entity id", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiExceptionEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleEntityAlreadyExistsException(BadRequestException e) {
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity("bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiExceptionEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiExceptionEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
