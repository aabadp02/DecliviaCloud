package com.example.decliviacloud.DecliviaCloud.Login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String userName,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message="El email debe ser válido")
        String email,

        @NotBlank(message="La contraseña no puede estar vacía")
        String password
){}
