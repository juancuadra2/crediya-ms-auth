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
}

