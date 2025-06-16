package com.example.decliviacloud.DecliviaCloud.System.Responses;

/**
 * Clase para definir las respuests exitosas de la aplicación.
 * Será lo que se devuelva desde los controllers dentro de los ReponseEntity
 * @param Response: Objeto con los datos que se quiera devolver
 * @param ApiMessage: Mensaje de la respuesta
 * @param ApiVersion: Versión de la API
 * @param <T>: Tipo del objeto que se devolverá (Response)
 */
public record ApiResponse<T>(
        T Response,
        String ApiMessage,
        String ApiVersion
){
    public ApiResponse(T Response, String ApiMessage){
        this(Response, ApiMessage,"v 0.0.1");
    }
}
