package com.example.decliviacloud.DecliviaCloud.Login;

import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaException;
import com.example.decliviacloud.DecliviaCloud.System.ResponseBeans.AddResponse;
import com.example.decliviacloud.DecliviaCloud.System.ApiResponses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * Endpoint para poder loguearse en la aplicación.
     * De momento devuelve true si las credenciales son correctas y false si no lo son.
     * TODO: Implementar JWT y devolver un token
     * @param loginRequest: Record con los datos de las credenciales del usuario que se va a loguear
     * @return true si son correctas las credenciales y false si no.
     */
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<LoginResponse>> Login(@RequestBody @Valid LoginRequest loginRequest) throws DecliviaException
    {
        // Nos logueamos
        LoginResponse loginResponse = loginService.Login(loginRequest);

        // Devolvemos la respuesta del login (el nombre de usuario y el token) junto con un código 200
        return new ResponseEntity<ApiResponse<LoginResponse>>(new ApiResponse<>(loginResponse, "Login correcto"), HttpStatus.OK);
    }

    /**
     * Endpoint para poder registrar un usuario en la aplicación
     * De momento solo devuelve un 200 ok si ha salido bien o una excepción en caso de que no
     * TODO: Estudiar si devolver el id del usaurio que se acaba de crear
     * @param signInRequest: Record con los datos del usuario que se quiere registrar dentro de la aplicación
     * @return: El id del usuario que se acaba de crear si sale bien
     * @throws DecliviaException: Excepción que saltará si los datos introducidos ya se están utilizando en algún usuario existente
     */
    @RequestMapping(value = "api/signin", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<AddResponse>> SignIn(@RequestBody @Valid SignInRequest signInRequest) throws DecliviaException
    {
        // Creamos el usuario y almacenamos el id del nuevo usuario creado
        int userId = loginService.SignIn(signInRequest);

        // Incluimos el id en el objeto de respuesta de creación para devolver el id del usuario que se acaba de registrar
        AddResponse addResponse = new AddResponse(userId);

        // Si todo ha salido bien devolvemos un 200 OK con el id del usuario que acabamos de crear
        return new ResponseEntity<ApiResponse<AddResponse>>(new ApiResponse<AddResponse>(addResponse, "El usuario se ha registrado correctamente"), HttpStatus.OK);
    }
}
