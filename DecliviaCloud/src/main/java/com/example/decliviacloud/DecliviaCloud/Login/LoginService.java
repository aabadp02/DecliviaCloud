package com.example.decliviacloud.DecliviaCloud.Login;

import com.example.decliviacloud.DecliviaCloud.System.Exceptions.ApiError;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserRecord;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserService userService;

    /**
     * Método para loguearse en la aplicación.
     * Hace una llama al servicio de usuarios para comprobar si existe un usuario con las credenciales
     * En caso de que no exista (nulo) lanzamos una excepción para devolver en el controller un error a través
     * de la API
     * @param loginRecord: Dto con las credenciales del usuario que se está logueando
     * @return El usuario logueado
     * @throws Exception Si las credenciales no son correctas
     */
    public LoginResponse Login(LoginRequest loginRecord) throws ApiError {

        // Buscamos al usuario según sus credenciales
        UserRecord user = userService.FindUserByCredentials(loginRecord.userName(), loginRecord.password());

        // Si no se encuentra, quiere decir que el login ha sido incorrecto, por lo que lanzamos una excepción
        if(user == null){
            throw new ApiError("Usuario o contraseña incorrectos");
        }

        // En caso de que exista, devolvemos el usuario
        return new LoginResponse(user.userName(), "prueba-token");
    }
}
