package com.example.decliviacloud.DecliviaCloud.System.Responses;

public record ApiResponse<T>(
        T Response,
        String ApiMessage,
        String ApiVersion
){
    public ApiResponse(T Response, String ApiMessage){
        this(Response, ApiMessage,"v 0.0.1");
    }
}
