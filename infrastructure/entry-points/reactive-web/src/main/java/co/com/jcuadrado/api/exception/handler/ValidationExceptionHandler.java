package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import co.com.jcuadrado.api.constant.error.ExceptionConstants;
import co.com.jcuadrado.api.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class ValidationExceptionHandler implements ExceptionHandler<ValidationException> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof ValidationException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, ValidationException throwable) {
        String traceString = String.format(ExceptionConstants.TRACE_FORMAT_SIMPLE,
                throwable.getMessage(),
                throwable.getCause() != null ? throwable.getCause().toString() : "N/A"
        );
        log.error(LogMessages.VALIDATION_EXCEPTION_LOG, traceString);
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), throwable.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public int getOrder() {
        return ExceptionConstants.VALIDATION_EXCEPTION_HANDLER_ORDER;
    }
}
