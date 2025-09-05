package co.com.jcuadrado.api.security.constants;

import co.com.jcuadrado.api.constant.AuthConstants;

/**
 * Constantes relacionadas con seguridad HTTP
 * @deprecated Usar AuthConstants para constantes de autenticación
 */
@Deprecated(since = "1.0", forRemoval = true)
public final class SecurityConstants {

    private SecurityConstants() {
        // Clase utilitaria - constructor privado
    }

    // @deprecated Usar AuthConstants.AUTH_API_PATH
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String AUTH_PATH_PREFIX = AuthConstants.AUTH_API_PATH;
    
    // @deprecated Usar AuthConstants.BEARER_PREFIX
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String BEARER_PREFIX = AuthConstants.BEARER_PREFIX;
    
    // @deprecated Usar AuthConstants.ROLE_PREFIX
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String ROLE_PREFIX = AuthConstants.ROLE_PREFIX;

    // @deprecated Usar AuthConstants.TOKEN_ATTRIBUTE
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String TOKEN_ATTRIBUTE = AuthConstants.TOKEN_ATTRIBUTE;

    // @deprecated Usar AuthConstants.NO_TOKEN_PROVIDED_ERROR
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String NO_TOKEN_PROVIDED_ERROR = AuthConstants.NO_TOKEN_PROVIDED_ERROR;
    
    // @deprecated Usar AuthConstants.INVALID_TOKEN_FORMAT_ERROR
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String INVALID_TOKEN_FORMAT_ERROR = AuthConstants.INVALID_TOKEN_FORMAT_ERROR;

    // @deprecated Usar AuthConstants.AUTHENTICATION_FAILED_LOG
    @Deprecated(since = "1.0", forRemoval = true)
    public static final String AUTHENTICATION_FAILED_MESSAGE = AuthConstants.AUTHENTICATION_FAILED_LOG;
}
