package co.com.jcuadrado.r2dbc.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserConstants {
    public static final String USER_TABLE_NAME = "users";
    public static final String LAST_NAME_COLUMN_NAME = "lastname";
    public static final String ROLE_ID_COLUMN_NAME = "role_id";

    public static final String ERROR_SAVING_USER = "Error al guardar el usuario: ";
}
