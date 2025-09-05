package co.com.jcuadrado.api.constant.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes relacionadas con autenticación y tokens
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {
    
    // Prefijos y formatos
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    
    // Atributos de contexto
    public static final String TOKEN_ATTRIBUTE = "token";
    public static final String USER_ATTRIBUTE = "user";
}
