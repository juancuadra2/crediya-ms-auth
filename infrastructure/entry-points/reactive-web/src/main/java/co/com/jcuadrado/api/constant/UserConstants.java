package co.com.jcuadrado.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserConstants {
    public static final String USERS_API_PATH = "/api/users";
    public static final String EMAIL_PATH_VARIABLE = "email";
    public static final String EMAIL_ENDPOINT = "/{email}";
}
