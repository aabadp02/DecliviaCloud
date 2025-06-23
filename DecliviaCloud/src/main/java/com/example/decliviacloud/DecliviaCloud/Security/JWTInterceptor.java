package com.example.decliviacloud.DecliviaCloud.Security;

import com.example.decliviacloud.DecliviaCloud.Cruds.Sessions.SessionService;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserRecord;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserService;
import com.example.decliviacloud.DecliviaCloud.System.ApiResponses.ApiError;
import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaError;
import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaException;
import com.example.decliviacloud.DecliviaCloud.System.Exceptions.GlobalExceptionHandler;
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

    /**
     * Método para deolver un ApiError desde el interceptor.
     * Necesitamos esto porque en este punto del programa no interviene el GlobalExceptionHandler,
     * por lo que si tratamos de hacer saltar una excepción, no la capturará y no se devolverá como un ApiErro estándard
     * @param errorMessage: mensaje de error que queremos añadir a la excepción
     * @param response: objeto response para poder devolver el error
     */
    private void ReturnApiError(String errorMessage, HttpServletResponse response) {
        // Creamos una lista de errores que solamente contiene el mensaje de la excepción
        List<DecliviaError> errorList = new ArrayList<>(List.of(new DecliviaError(errorMessage)));
        ApiError error = new ApiError(errorList);

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
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
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     * @throws DecliviaException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, DecliviaException {

        List<String> excludePaths = List.of("/api/login", "/api/signin");

        if (excludePaths.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
        }
        else {

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                if (jwtUtil.validateToken(token)) {

                    // Obtenemos el usuario de la base de datos
                    String username = jwtUtil.getUsernameFromToken(token);
                    UserRecord user = userService.FindUserByUserName(username);

                    if(user == null) {
                        logger.error("El usuario {}no existe", username);
                        ReturnApiError("Ha ocurrido un error inesperado", response);
                        return;
                    }

                    // Validamos si el token existe para el usaurio
                    if(sessionService.ValidateTokenByUser(user, token)) {
                        filterChain.doFilter(request, response);
                    }
                    else {
                        ReturnApiError("La sesión no es válida", response);
                        return;
                    }
                }
                else {
                    ReturnApiError("El token no es válido", response);
                    return;
                }
            }
            else {
                ReturnApiError("No se ha provisto un token", response);
                return;
            }
        }
    }
}
