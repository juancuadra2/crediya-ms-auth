package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ApiResponseConstants {
    public static final String USER_CREATED_SUCCESS_DESCRIPTION = "Usuario creado exitosamente";
    public static final String USERS_LIST_SUCCESS_DESCRIPTION = "Lista de usuarios obtenida exitosamente";

    public static final String VALIDATION_ERROR_DESCRIPTION = "Datos de entrada inválidos";
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Error interno del servidor";
    
    public static final String USER_EXAMPLE_NAME = "Usuario ejemplo";
    public static final String USER_EXAMPLE_SUMMARY = "Ejemplo de creación de usuario";
    public static final String USER_CREATED_NAME = "Usuario creado";
    public static final String USER_CREATED_SUMMARY = "Respuesta exitosa de creación";
    public static final String USERS_LIST_NAME = "Lista de usuarios";
    public static final String USERS_LIST_SUMMARY = "Respuesta exitosa con lista de usuarios";
    public static final String VALIDATION_ERROR_NAME = "Error de validación";
    public static final String VALIDATION_ERROR_SUMMARY = "Error por datos inválidos";
    public static final String INTERNAL_ERROR_NAME = "Error interno";
    public static final String INTERNAL_ERROR_SUMMARY = "Error del servidor";
}
