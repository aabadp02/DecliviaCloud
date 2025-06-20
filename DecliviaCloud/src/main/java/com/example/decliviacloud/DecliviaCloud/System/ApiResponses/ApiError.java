package com.example.decliviacloud.DecliviaCloud.System.ApiResponses;

import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaError;

import java.util.List;

/**
 * Respuesta de la API como resultado de un proceso que ha dado lugar a un error.
 * Por ejepmlo, se devolverá un ApiError cuando salte una excepción (controlada o no)
 * @param errorList: lista de los errores que han sucedido durante el proceso
 * @param apiVersion: versión de la api en la que estamos
 */
public record ApiError(List<DecliviaError> errorList, String apiVersion) {

    public ApiError(List<DecliviaError> errorList) { this(errorList, "0.0.1");}
}
