package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DecliviaException.class})
    public ResponseEntity<DecliviaError> HandleApiError(DecliviaException apiError) {

        return new ResponseEntity<DecliviaError>(new DecliviaError(apiError.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
