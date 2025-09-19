package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.ExceptionConstants;
import co.com.jcuadrado.api.constant.error.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomResponseStatusExceptionHandler implements ExceptionHandler<ResponseStatusException> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof ResponseStatusException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, ResponseStatusException throwable) {
        HttpStatus httpStatus = HttpStatus.valueOf(throwable.getStatusCode().value());
        String message = Objects.toString(throwable.getMessage(), "");
        message = message.replaceAll(ExceptionConstants.QUOTED_MESSAGE_PATTERN.pattern(), "$1");
        message = HttpStatus.UNSUPPORTED_MEDIA_TYPE.equals(httpStatus) ? ExceptionConstants.MEDIA_TYPE_NOT_SUPPORTED_MESSAGE : message;

        Set<String> messages = Set.of(message);
        String traceString = String.format(ExceptionConstants.TRACE_FORMAT_HTTP_STATUS,
                throwable.getMessage(),
                throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
                throwable.getStatusCode().value()
        );

        log.error(LogMessages.HTTP_STATUS_EXCEPTION_LOG, traceString);
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, httpStatus);
    }

    @Override
    public int getOrder() {
        return ExceptionConstants.RESPONSE_STATUS_EXCEPTION_HANDLER_ORDER;
    }
}
