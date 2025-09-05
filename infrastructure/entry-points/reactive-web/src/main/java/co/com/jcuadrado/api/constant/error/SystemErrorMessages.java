package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Mensajes de error del sistema
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SystemErrorMessages {
    
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Se ha producido un error interno.";
    public static final String INTERNAL_SERVER_ERROR_TITLE = "Error interno";
    public static final String SERIALIZATION_EXCEPTION_MESSAGE = "Se ha producido un error al serializar la respuesta.";
    public static final String DESERIALIZATION_EXCEPTION_MESSAGE = "Se ha producido un error al deserializar la petición.";
}
