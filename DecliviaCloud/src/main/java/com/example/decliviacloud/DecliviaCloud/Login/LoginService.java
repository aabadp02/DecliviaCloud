package com.example.decliviacloud.DecliviaCloud.Login;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserRecord;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserService;
import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaException;
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
     * @throws DecliviaException Si las credenciales no son correctas
     */
    public LoginResponse Login(LoginRequest loginRecord) throws DecliviaException {

        // Buscamos al usuario según sus credenciales
        UserRecord user = userService.FindUserByCredentials(loginRecord.userName(), loginRecord.password());

        // Si no se encuentra, quiere decir que el login ha sido incorrecto, por lo que lanzamos una excepción
        if(user == null){
            throw new DecliviaException("Usuario o contraseña incorrectos");
        }

        // En caso de que exista, devolvemos el usuario
        return new LoginResponse(user.userName(), "prueba-token");
    }

    /**
     * Método para registrar un nuevo usuario en la aplicación
     * @param signInRequest: objeto con los datos necesarios para crear el nuevo usuario
     * @return El id del usuario usuario que acabamos de crear
     * @throws DecliviaException: Excepción que saltará en caso de que los datos introducidos sean de un usuario ya existente
     */
    public int SignIn(SignInRequest signInRequest) throws DecliviaException{

        // Creamos un objeto de tipo usuario con los datos que nos han llegado en el sign in
        UserRecord user = new UserRecord(null, signInRequest.userName(), signInRequest.email(), signInRequest.password(), false);

        // Llamamos al servicio de usurios para persistir el nuevo usuario
        return userService.CreateUser(user);
    }
}
