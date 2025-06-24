package com.example.decliviacloud.DecliviaCloud.Security;

import com.example.decliviacloud.DecliviaCloud.Cruds.Sessions.SessionService;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserRecord;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserService;
import com.example.decliviacloud.DecliviaCloud.System.ApiResponses.ApiError;
import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaError;
import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTInterceptor extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    SessionService sessionService;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final String BEARER_PREFIX = "Bearer ";

    /**
     * Método para deolver un ApiError desde el interceptor.
     * Necesitamos esto porque en este punto del programa no interviene el GlobalExceptionHandler,
     * por lo que si tratamos de hacer saltar una excepción, no la capturará y no se devolverá como un ApiErro estándard
     * @param exception: excepción que querremos convertir a un ApiError
     * @param response: objeto response para poder devolver el error
     */
    private void ReturnApiError(Exception exception, HttpServletResponse response) {
        // Creamos una lista de errores que solamente contiene el mensaje de la excepción
        List<DecliviaError> errorList = new ArrayList<>(List.of(new DecliviaError(exception.getMessage())));
        ApiError error = new ApiError(errorList);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        try {
            String body = objectMapper.writeValueAsString(error);
            response.getWriter().write(body);
        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Método para extraer el token de la request enviada por el usuario
     * @param request : request http enviada a través de la API
     * @return
     * @throws DecliviaException
     */
    private String extractTokenFromRequest(HttpServletRequest request) throws DecliviaException {

        // Extraemos la cabezar "authorization" del mensaje http, ya que ahí se encuentra el token
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        // El token viene dado por "Bearer token", así que lo extraemos descartando dicho prefijo
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {

            String token = authHeader.substring(7);

            return token;
        }
        else {
            throw new DecliviaException("No se ha provisto ningún token");
        }
    }

    /**
     * Método para comprobar si el token existe en nuestra base de datos y que corresponde al usuario contenido dentro del token
     * @param token
     * @return
     */
    private boolean verifyTokenExists(String token) throws DecliviaException {

        // Extraemos el usuario que viene en el token
        String username = jwtUtil.getUsernameFromToken(token);

        // Sacamos el usuario de la base de datos
        UserRecord user = userService.FindUserByUserName(username);

        // Verificamos que el usuario exista en nuestra base de datos
        if(user == null) {
            logger.error("El usuario '{}' no existe", username);

            throw new DecliviaException("Ha ocurrido un error inesperado");
        }

        return sessionService.ValidateTokenByUser(user, token);
    }

    /**
     * Función para validar que el token que nos llega es correcto, tanto en su forma, como
     * si existe dentro de la base de datos y pertenece realmente al usuario indicado.
     * @param token
     * @throws DecliviaException
     */
    private void validateToken(String token) throws DecliviaException
    {
        // Validamos que el token sea correcto (que no se haya intentado modificar desde fuera y que sea un token válido dada nuestra clave de generación, etc)
        if(!jwtUtil.validateToken(token)) {
            throw new DecliviaException("El token no es válido");
        }

        // Verificamos que el token exista en nuestra base de datos y corresponda al usuario contenido dentro dle mimso
        if(!verifyTokenExists(token)) {
            throw new DecliviaException("La sesión no es válida");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     * @throws DecliviaException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // lista de URLs para las que NO querremos validar el token cuando se hagan peticiones.
        // Por ejemplo: no querremos validr el token durante el login porque, obviamente, en ese punto el usuario aún no tiene token
        List<String> excludePaths = List.of("/api/login", "/api/signin");

        // Si se trata de una de las urls indicadas para no validar el token, simplemente dejamos pasar la request para que llegue al controlador correspondiente
        if (excludePaths.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
        }
        else {

            // Extraemos el token de la request
            String token = extractTokenFromRequest(request);

            try {

                // Validamos el token
                validateToken(token);
            }
            // ¡OJO! Capturaramos la excepción aquí ya que en este punto de la aplicación el GlobalExceptionHanlder no interviene.
            // Por lo tanto, nos encargamos manualmente de capturar la excepción y devolver un APIError
            catch (Exception e) {

                ReturnApiError(e, response);

                return;
            }

            // Si ha salido todo bien, dejamos pasar la request al controlador correspondiente
            filterChain.doFilter(request, response);
        }
    }
}
