package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class OpenApiConstants {

    public static final String API_TITLE = "Authentication API";
    public static final String API_VERSION = "1.0";
    public static final String API_DESCRIPTION = "API para gestión de usuarios en el sistema";

    public static final String COMPONENT_SCHEMA_USER_DTO = "#/components/schemas/UserDTO";
    public static final String COMPONENT_SCHEMA_CREATE_USER_DTO = "#/components/schemas/CreateUserDTO";
    public static final String COMPONENT_SCHEMA_ERROR_RESPONSE_DTO = "#/components/schemas/ErrorResponseDTO";

}
