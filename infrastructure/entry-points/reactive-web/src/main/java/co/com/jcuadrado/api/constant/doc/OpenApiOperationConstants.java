package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class OpenApiOperationConstants {

    public static final String CREATE_USER_SUMMARY = "Crear usuario";
    public static final String CREATE_USER_DESCRIPTION = "Crea un nuevo usuario en el sistema";
    public static final String CREATE_USER_OPERATION_ID = "saveUser";
    
    public static final String GET_ALL_USERS_SUMMARY = "Obtener todos los usuarios";
    public static final String GET_ALL_USERS_DESCRIPTION = "Retorna la lista de todos los usuarios registrados en el sistema";
    public static final String GET_ALL_USERS_OPERATION_ID = "getAllUsers";

    public static final String CREATE_USER_REQUEST_DESCRIPTION = "Datos del usuario a crear";

    public static final String USUARIOS_TAG_NAME = "Usuarios";
    public static final String USUARIOS_TAG_DESCRIPTION = "API para gestión de usuarios del sistema";
}
