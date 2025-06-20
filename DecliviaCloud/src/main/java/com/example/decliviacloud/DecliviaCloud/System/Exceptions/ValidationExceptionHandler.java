package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import com.example.decliviacloud.DecliviaCloud.System.ApiResponses.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {
    /**
     * Handler para capturar excepciones realacionadas con los argumentos
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> HandleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        // Sacamos los mensajes de error individuales de las validaciones
        List<String> validationErrors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        // Creamos una lista de errores que solamente contiene el mensaje de la excepción
        List<DecliviaError> errorList = new ArrayList<>();

        // Convertimos la lista de errores a DecliviaErrors
        for(String error : validationErrors) {
            errorList.add(new DecliviaError(error));
        }

        // Devolvemos un ApiError que contiene la lista de errores
        return new ResponseEntity<>(new ApiError(errorList), HttpStatus.BAD_REQUEST);
    }
}
