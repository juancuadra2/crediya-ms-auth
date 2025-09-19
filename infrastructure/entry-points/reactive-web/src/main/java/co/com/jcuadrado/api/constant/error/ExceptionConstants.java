package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Constantes relacionadas con excepciones y manejo de errores
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionConstants {

    public static final Pattern QUOTED_MESSAGE_PATTERN = Pattern.compile(".*\"([^\"]*)\".*");
    public static final String MEDIA_TYPE_NOT_SUPPORTED_MESSAGE = "Media type not supported";
    public static final int RESPONSE_STATUS_EXCEPTION_HANDLER_ORDER = 2;

    // Nuevas constantes para evitar textos hardcodeados en los handlers
    public static final String TRACE_FORMAT_HTTP_STATUS = "Message: %s, Cause: %s, StatusCode: %s";
    public static final String TRACE_FORMAT_BUSINESS = "Message: %s, Cause: %s, ErrorCode: %s";
    public static final String TRACE_FORMAT_SIMPLE = "Message: %s, Cause: %s";

    // Órdenes de ejecución para los handlers
    public static final int VALIDATION_EXCEPTION_HANDLER_ORDER = 1;
    public static final int BUSINESS_EXCEPTION_HANDLER_ORDER = 3;
    public static final int DEFAULT_EXCEPTION_HANDLER_ORDER = Integer.MAX_VALUE;
}
