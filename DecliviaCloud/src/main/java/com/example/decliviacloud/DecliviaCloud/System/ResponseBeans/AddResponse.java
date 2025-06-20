package com.example.decliviacloud.DecliviaCloud.System.ResponseBeans;

/**
 * Objeto genérico que se devuelve como respuesta cuando se produce la creación de un recurso.
 * Incluye el id del registro que se ha creado
 * @param id: Id del registro que se ha creado
 */
public record AddResponse(int id) {
}
