package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

/**
 * Clase para definir excepciones personalizadas de nuestra aplicación.
 * ¡OJO! No confundir con la clase DecliviaError.
 * Esta clase nos sirve para lanzar excepciones conocidas y controladas en nuestra aplicación.
 */
public class DecliviaException extends RuntimeException {
    public DecliviaException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
