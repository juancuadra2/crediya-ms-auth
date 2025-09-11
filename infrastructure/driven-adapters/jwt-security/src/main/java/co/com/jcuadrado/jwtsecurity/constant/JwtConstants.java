package co.com.jcuadrado.jwtsecurity.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConstants {
    public static final String ROLES_CLAIM = "roles";
    public static final String AUTHORITIES_CLAIM = "authority";
}