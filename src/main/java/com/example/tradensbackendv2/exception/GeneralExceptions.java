package com.example.tradensbackendv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptions {
    @ExceptionHandler(RegisteredUserException.class)
    public ResponseEntity<?> handle(RegisteredUserException registeredUserException){
        return new ResponseEntity<>(registeredUserException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}