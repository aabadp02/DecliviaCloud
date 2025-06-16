package com.example.decliviacloud.DecliviaCloud.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase para definir errores que devolverá la API.
 * ¡OJO! No confundir con la clase ApiException.
 * Esta clase servirá para devolver errores controlados al usuario
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiError extends RuntimeException {

    public ApiError(String exceptionMessage) {
        super(exceptionMessage);
    }
}
