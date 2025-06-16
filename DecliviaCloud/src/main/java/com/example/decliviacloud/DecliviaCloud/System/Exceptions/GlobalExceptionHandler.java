package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApiError.class})
    public ResponseEntity<Object> HandleApiError(ApiError apiError) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError.getMessage());
    }
}
