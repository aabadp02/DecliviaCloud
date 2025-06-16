package com.example.decliviacloud.DecliviaCloud.Login;

import com.example.decliviacloud.DecliviaCloud.System.Exceptions.ApiError;
import com.example.decliviacloud.DecliviaCloud.System.Responses.ApiResponse;
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
    public ResponseEntity<ApiResponse<LoginResponse>> Login(@RequestBody LoginRequest loginRequest) throws ApiError
    {
        // Nos logueamos
        LoginResponse loginResponse = loginService.Login(loginRequest);

        // Devolvemos la respuesta del login (el nombre de usuario y el token) junto con un código 200
        return new ResponseEntity<ApiResponse<LoginResponse>>(new ApiResponse<>(loginResponse, "Login correcto"), HttpStatus.OK);
    }
}
