package co.com.jcuadrado.api.constant.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String TOKEN_ATTRIBUTE = "token";

    public static final String NO_TOKEN_PROVIDED_ERROR = "No token provided";
    public static final String INVALID_TOKEN_FORMAT_ERROR = "Invalid token format";
    public static final String INVALID_TOKEN_ERROR = "Invalid token";
    public static final String USER_NOT_AUTHENTICATED_ERROR = "User not authenticated";
    public static final String ACCESS_DENIED_ERROR = "Access denied";
}
