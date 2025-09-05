package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Mensajes de error para autenticación
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthErrorMessages {
    
    // Errores de autenticación
    public static final String AUTHENTICATION_FAILED = "Credenciales inválidas";
    public static final String NO_TOKEN_PROVIDED_ERROR = "No token provided";
    public static final String INVALID_TOKEN_FORMAT_ERROR = "Invalid token format";
    public static final String INVALID_TOKEN_ERROR = "Invalid token";
    
    // Errores de JWT
    public static final String TOKEN_EXPIRED_ERROR = "Token expired";
    public static final String TOKEN_UNSUPPORTED_ERROR = "Token unsupported";
    public static final String TOKEN_MALFORMED_ERROR = "Token malformed";
    public static final String BAD_SIGNATURE_ERROR = "Bad signature";
    public static final String ILLEGAL_ARGS_ERROR = "Illegal arguments";
}
