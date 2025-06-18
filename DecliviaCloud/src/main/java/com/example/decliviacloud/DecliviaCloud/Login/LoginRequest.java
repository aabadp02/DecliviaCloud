package com.example.decliviacloud.DecliviaCloud.Login;

import jakarta.validation.constraints.NotBlank;

/**
 * Record con los datos necesarios para loguearse en la aplicación
 * @param userName
 * @param password
 */
public record LoginRequest(@NotBlank String userName, @NotBlank String password) { }
