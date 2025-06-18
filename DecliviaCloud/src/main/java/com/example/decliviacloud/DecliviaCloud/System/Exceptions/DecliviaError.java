package com.example.decliviacloud.DecliviaCloud.System.Exceptions;

import java.util.List;

/**
 * Clase para definir una respuesta de error de la aplicación.
 * ¡OJO! No confundir con DecliviaException.
 * Esta clase será lo que se devuelva dentro del ResponseEntity cuando devolvemos un error
 * controlado de la aplicación (por ejemplo: un usuario que ya existe durante la creación.
 *
 * TODO: Incluir un mensaje de admin/téncico con la traza completa de la excepción cuando el usuario es admin.
 *
 * @param ErrorMessage: Mensaje de error
 * @param ApiVersion: Versión de la API
 */
public record DecliviaError(
        String ErrorMessage,
        String ApiVersion) {

    public DecliviaError(String ErrorMessage) { this(ErrorMessage, "0.0.1"); };
}
