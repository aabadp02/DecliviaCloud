package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clase para capturar y manejar las exepciones que saltan en la aplicación a nivel global.
 * Da igual si la excepción se produce en un controlador o en un servicio, ese handler va a tratar de capturarlas
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejador para capturar las excepciones de tipo DecliviaException y convertirlas en ResponseEntities de tal forma
     * que sean respuestas estándard, con un formato concreto, etc.
     * @param decliviaException: Excepción que ha saltado en la aplicaicón y que queremos devolver a través de la API
     * @return: Un ResponseEntity de tipo DecliviaError que tiene un formato concreto para devolver los errores de la aplicación
     */
    @ExceptionHandler({DecliviaException.class})
    public ResponseEntity<DecliviaError> HandelApiException(DecliviaException decliviaException) {
        return new ResponseEntity<DecliviaError>(new DecliviaError(decliviaException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador para capturar las excepciones no tratadas de la aplicación. Por ejemplo, cuando salta un null pointer
     * @param runtimeException: Excepción que ha saltado en la aplicaicón y que queremos devolver a través de la API
     * @return: Un ResponseEntity de tipo DecliviaError que tiene un formato concreto para devolver los errores de la aplicación
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<DecliviaError> HandleRunTimeException(RuntimeException runtimeException) {
        return new ResponseEntity<DecliviaError>(new DecliviaError("EXCEPCIÓN NO TRATADA: " + runtimeException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
