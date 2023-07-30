package com.sbms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity employeeNotfoundExceptionHandler(EmployeeNotFoundException enfe) {
        String message = enfe.getMessage();
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}
