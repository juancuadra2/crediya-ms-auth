package co.com.jcuadrado.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AuthConstants {
    // Rutas y endpoints
    public static final String AUTH_API_PATH = "/auth";
    public static final String LOGIN_ENDPOINT = "/login";
    
    // Prefijos de autenticación (movidos desde SecurityConstants)
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    
    // Atributos (movidos desde SecurityConstants)
    public static final String TOKEN_ATTRIBUTE = "token";
    
    // Mensajes de error HTTP (movidos desde SecurityConstants)
    public static final String NO_TOKEN_PROVIDED_ERROR = "No token provided";
    public static final String INVALID_TOKEN_FORMAT_ERROR = "Invalid token format";
    
    // Mensajes de log (movidos desde SecurityConstants)
    public static final String AUTHENTICATION_FAILED_LOG = "Authentication failed: {}";
}
