package co.com.jcuadrado.constants;

import lombok.NoArgsConstructor;

/**
 * Constantes relacionadas con JWT (JSON Web Tokens)
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class JwtConstants {

    // Claims
    public static final String ROLES_CLAIM = "roles";
    public static final String AUTHORITY_KEY = "authority";

    // Mensajes de error para logs
    public static final String TOKEN_EXPIRED_ERROR = "Token expired: {}";
    public static final String TOKEN_UNSUPPORTED_ERROR = "Token unsupported: {}";
    public static final String TOKEN_MALFORMED_ERROR = "Token malformed: {}";
    public static final String BAD_SIGNATURE_ERROR = "Bad signature: {}";
    public static final String ILLEGAL_ARGS_ERROR = "Illegal args: {}";

    // Mensajes de error para excepciones
    public static final String INVALID_TOKEN_ERROR = "Invalid token";
    public static final String JWT_AUTHENTICATION_FAILED_ERROR = "JWT authentication failed: {}";
}
