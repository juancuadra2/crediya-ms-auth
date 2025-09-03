package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class OpenApiSchemaConstants {

    public static final String USER_DTO_DESCRIPTION = "DTO que representa un usuario del sistema";
    public static final String CREATE_USER_DTO_DESCRIPTION = "DTO para la creación de un nuevo usuario en el sistema";
    public static final String UPDATE_USER_DTO_DESCRIPTION = "DTO para actualización de usuario";

    public static final String APPLICATION_JSON = "application/json";
    
    public static final String STATUS_200 = "200";
    public static final String STATUS_201 = "201";
    public static final String STATUS_400 = "400";
    public static final String STATUS_500 = "500";
}
