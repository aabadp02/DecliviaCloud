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
 * @param errorMessage: Mensaje de error
 * @param errorTrace: Traza del error (traza de la excepción). Este mensaje solo debería verlo un técnico. SOlo se debería rellenar si el usaurio es de tipo admin
 */
public record DecliviaError(String errorMessage, String errorTrace) {

    /**
     * Constructor para indicar solamente el mensaje de error.
     * Este mensaje es el que verá el usuario final
     * @param errorMessage
     */
    public DecliviaError(String errorMessage) { this(errorMessage, null);}

}
