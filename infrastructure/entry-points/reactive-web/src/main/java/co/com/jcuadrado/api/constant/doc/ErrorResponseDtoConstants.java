package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ErrorResponseDtoConstants {

    public static final String ERROR_RESPONSE_DESCRIPTION = "DTO para respuestas de error del sistema";
    public static final String MESSAGES_DESCRIPTION = "Lista de mensajes de error";
    public static final String ERROR_DESCRIPTION = "Tipo de error";
    public static final String STATUS_DESCRIPTION = "Código de estado HTTP";
    
    public static final String MESSAGES_EXAMPLE = "[\"El email debe tener un formato válido\", \"El teléfono es requerido\"]";
    public static final String ERROR_EXAMPLE = "Validation Error";
    public static final String STATUS_EXAMPLE = "BAD_REQUEST";
}
