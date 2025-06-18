package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
    /**
     * Handler para capturar excepciones realacionadas con los argumentos
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<DecliviaError> HandleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ResponseEntity<DecliviaError>(new DecliviaError(methodArgumentNotValidException.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
