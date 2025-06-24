package com.example.decliviacloud.DecliviaCloud.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    // Extraemos el secret para generar tokens del archivo properties
    @Value("${jwt.secret}")
    private String secret;

    // Extraemos el tiempo de expiración del archivo properties
    @Value("${jwt.expiration}")
    private long expirationMs;

    // Definimos el logger para poder escribir logs en el servicio
    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Método para sacar el claim del token que corresponde al nombre de usuario.
     * @param token: Token jwt del que sacaremos el nombre del usuario
     * @return: Nombre del usuario contenido en el token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Método para generar el token jwt que contendrá cierta información, como el nombre del usuario
     * para el cuál se ha creado el token o el flag para saber si se trata de un usuario administrador o no.
     * TODO: Incluir los roles en los claims cuando los haya.
     * @param username: Nombre del usuario para el que se general token
     * @param isAdmin: flag que introduciremos como claim en el token
     * @return
     */
    public String generateToken(String username, boolean isAdmin) {
        return Jwts.builder()
                .setSubject(username)
                .claim("isAdmin", isAdmin)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Método para comprobar si el token es correcto
     * @param token: Token jwt
     * @return: true si el token es válido. False si no.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        }
        // ¡OJO! Estamos capturando la excecpión porque lo único que queremos es saber si el token es válido.
        // No repetir esto en el resto de la aplicaicón
        //
        catch (JwtException e) {
            // Logueamos la excepción por si necesitásemos saber el motivo exacto del token inválido
            logger.warn(e.getMessage());
            return false;
        }
    }
}
