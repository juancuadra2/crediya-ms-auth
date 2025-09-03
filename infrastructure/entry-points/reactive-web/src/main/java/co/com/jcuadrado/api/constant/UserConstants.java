package co.com.jcuadrado.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserConstants {
    public static final String USERS_API_PATH = "/api/users";
    public static final String DOCUMENT_NUMBER_PATH_VARIABLE = "documentNumber";
    public static final String DOCUMENT_NUMBER_ENDPOINT = "/{documentNumber}";
}
