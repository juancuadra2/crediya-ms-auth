package co.com.jcuadrado.api.constant.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes relacionadas con JWT (JSON Web Tokens)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtConstants {
    
    // Claims de JWT
    public static final String ROLES_CLAIM = "roles";
    public static final String AUTHORITY_KEY = "authority";
    public static final String SUBJECT_CLAIM = "sub";
    public static final String ISSUED_AT_CLAIM = "iat";
    public static final String EXPIRATION_CLAIM = "exp";
}
