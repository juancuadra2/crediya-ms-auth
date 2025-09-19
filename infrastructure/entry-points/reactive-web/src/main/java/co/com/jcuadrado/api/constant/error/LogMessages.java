package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Mensajes de log para excepciones
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessages {
    public static final String VALIDATION_EXCEPTION_LOG = "VALIDATION_EXCEPTION: ";
    public static final String NOT_FOUND_EXCEPTION_LOG = "NOT_FOUND_EXCEPTION: ";
    public static final String INTERNAL_SERVER_ERROR_LOG = "INTERNAL_SERVER_ERROR: ";
    public static final String BAD_REQUEST_EXCEPTION_LOG = "BAD_REQUEST_EXCEPTION: {}";
    public static final String BUSINESS_EXCEPTION_LOG = "BUSINESS_EXCEPTION: {}";

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Se ha producido un error interno.";
    public static final String SERIALIZATION_EXCEPTION_MESSAGE = "Se ha producido un error al serializar la respuesta.";
}
