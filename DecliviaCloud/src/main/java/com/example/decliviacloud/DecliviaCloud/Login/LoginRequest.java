package com.example.decliviacloud.DecliviaCloud.Login;

/**
 * Record con los datos necesarios para loguearse en la aplicación
 * @param userName
 * @param password
 */
public record LoginRequest(String userName, String password) { }
