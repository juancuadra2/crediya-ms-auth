package co.com.jcuadrado.api.constant.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes para endpoints de usuarios
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserEndpoints {
    
    public static final String USERS_API_PATH = "/api/users";
    public static final String DOCUMENT_NUMBER_PATH_VARIABLE = "documentNumber";
    public static final String DOCUMENT_NUMBER_ENDPOINT = "/{documentNumber}";
}
