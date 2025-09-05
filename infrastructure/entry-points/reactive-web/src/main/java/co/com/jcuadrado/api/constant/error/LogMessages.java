package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Mensajes de log para excepciones
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessages {
    
    // Logs de excepciones
    public static final String DOMAIN_EXCEPTION_LOG = "DOMAIN_EXCEPTION: {}";
    public static final String VALIDATION_EXCEPTION_LOG = "VALIDATION_EXCEPTION: {}";
    public static final String INTERNAL_SERVER_ERROR_LOG = "INTERNAL_SERVER_ERROR: {}";
    public static final String AUTHENTICATION_FAILED_LOG = "Authentication failed: {}";
    public static final String JWT_AUTHENTICATION_FAILED_LOG = "JWT authentication failed: {}";
    
    // Logs específicos de JWT
    public static final String TOKEN_EXPIRED_LOG = "Token expired: {}";
    public static final String TOKEN_UNSUPPORTED_LOG = "Token unsupported: {}";
    public static final String TOKEN_MALFORMED_LOG = "Token malformed: {}";
    public static final String BAD_SIGNATURE_LOG = "Bad signature: {}";
    public static final String ILLEGAL_ARGS_LOG = "Illegal args: {}";
}
