package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Clase para definir excepciones personalizadas de nuestra aplicación.
 * ¡OJO! No confundir con la clase DecliviaError.
 * Esta clase nos sirve para lanzar excepciones conocidas y controladas en nuestra aplicación.
 */
public class DecliviaException extends RuntimeException {

    // Código HTTP de la respuesta de la excepción.
    // Si no se indica, por defecto será 500 (INTERNAL SERVER ERROR)
    private final HttpStatusCode httpStatusCode;

    /**
     * Constructor básico de DecliviaException en la que solo se indica el mensaje de error.
     * El código http será 500
     * @param exceptionMessage: Mensaje de error de la excepción
     */
    public DecliviaException(String exceptionMessage) {

        super(exceptionMessage);

        // Damos como valor por defecto al código http -> 500
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Constructor con el que indicar a la execepción el código HTTP que se devolverá
     * @param exceptionMessage: Mensaje de error de la excepción
     * @param httpStatusCode: Código http que se devolverá a través de la API
     */
    public DecliviaException(String exceptionMessage, HttpStatusCode httpStatusCode ) {

        super(exceptionMessage);

        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Getter del código http
     * @return El código http que se devolverá a través de la API
     */
    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
