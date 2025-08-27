package co.com.jcuadrado.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionError {
    public static final String DOMAIN_EXCEPTION_LOG = "DOMAIN_EXCEPTION: {}";
    public static final String VALIDATION_EXCEPTION_LOG = "VALIDATION_EXCEPTION: {}";
    public static final String INTERNAL_SERVER_ERROR_LOG = "INTERNAL_SERVER_ERROR : ";

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Se ha producido un error interno.";
    public static final String INTERNAL_SERVER_ERROR_TITLE = "Error interno";

    public static final String SERIALIZATION_EXCEPTION_MESSAGE = "Se ha producido un error al serializar la respuesta.";
}
