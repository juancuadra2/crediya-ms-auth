package co.com.jcuadrado.api.constant.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes para endpoints de autenticación
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthEndpoints {
    
    public static final String AUTH_API_PATH = "/auth";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REFRESH_ENDPOINT = "/refresh";
    public static final String LOGOUT_ENDPOINT = "/logout";
}
