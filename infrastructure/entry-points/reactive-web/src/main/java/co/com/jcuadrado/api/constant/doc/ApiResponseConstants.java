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

    // Auth responses
    public static final String LOGIN_SUCCESS_DESCRIPTION = "Autenticación exitosa";
    public static final String TOKEN_VALIDATION_SUCCESS_DESCRIPTION = "Token validado exitosamente";
    public static final String UNAUTHORIZED_ERROR_DESCRIPTION = "Credenciales inválidas o token expirado";

    public static final String LOGIN_EXAMPLE_NAME = "Credenciales ejemplo";
    public static final String LOGIN_EXAMPLE_SUMMARY = "Ejemplo de credenciales de login";
    public static final String LOGIN_SUCCESS_NAME = "Login exitoso";
    public static final String LOGIN_SUCCESS_SUMMARY = "Respuesta exitosa de autenticación";
    public static final String TOKEN_VALIDATION_NAME = "Token validado";
    public static final String TOKEN_VALIDATION_SUMMARY = "Respuesta exitosa de validación de token";
    public static final String UNAUTHORIZED_ERROR_NAME = "No autorizado";
    public static final String UNAUTHORIZED_ERROR_SUMMARY = "Error de autorización";

    // JWT Security constants
    public static final String JWT_SECURITY_SCHEME_DESCRIPTION = "JWT Authorization header using the Bearer scheme. Example: \"Authorization: Bearer {token}\"";
    public static final String JWT_SECURITY_SCHEME_NAME = "bearerAuth";
    public static final String JWT_SECURITY_SCHEME_TYPE = "bearer";
    public static final String JWT_SECURITY_BEARER_FORMAT = "JWT";
}
