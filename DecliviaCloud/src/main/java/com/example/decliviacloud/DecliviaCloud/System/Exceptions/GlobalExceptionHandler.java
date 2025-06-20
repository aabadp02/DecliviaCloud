package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserService;
import com.example.decliviacloud.DecliviaCloud.System.ApiResponses.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para capturar y manejar las exepciones que saltan en la aplicación a nivel global.
 * Da igual si la excepción se produce en un controlador o en un servicio, ese handler va a tratar de capturarlas
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Manejador para capturar las excepciones de tipo DecliviaException y convertirlas en ResponseEntities de tal forma
     * que sean respuestas estándard, con un formato concreto, etc.
     * @param decliviaException: Excepción que ha saltado en la aplicaicón y que queremos devolver a través de la API
     * @return: Un ResponseEntity de tipo DecliviaError que tiene un formato concreto para devolver los errores de la aplicación
     */
    @ExceptionHandler({DecliviaException.class})
    public ResponseEntity<ApiError> HandelApiException(DecliviaException decliviaException) {

        // Creamos una lista de errores que solamente contiene el mensaje de la excepción
        List<DecliviaError> errorList = new ArrayList<>(List.of(new DecliviaError(decliviaException.getMessage())));

        // Devolvemos un ApiError que contiene la lista de errores
        return new ResponseEntity<>(new ApiError(errorList), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador para capturar las excepciones no tratadas de la aplicación. Por ejemplo, cuando salta un null pointer
     * @param runtimeException: Excepción que ha saltado en la aplicaicón y que queremos devolver a través de la API
     * @return: Un ResponseEntity de tipo DecliviaError que tiene un formato concreto para devolver los errores de la aplicación
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> HandleRunTimeException(RuntimeException runtimeException) {

        //loggeamos la excepción
        logger.error("Se ha producido una excepción no tratada: {}", runtimeException.getMessage());

        // Creamos una lista de errores que solamente contiene el mensaje de la excepción
        List<DecliviaError> errorList = new ArrayList<>(List.of(new DecliviaError("Ha ocurrido un error inesperado", runtimeException.getMessage())));

        // Devolvemos un ApiError que contiene la lista de errores
        return new ResponseEntity<ApiError>(new ApiError(errorList), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
