package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import co.com.jcuadrado.api.constant.error.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Log4j2
public class DefaultExceptionHandler implements ExceptionHandler<Throwable> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return true;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        Set<String> messages = Set.of(LogMessages.INTERNAL_SERVER_ERROR_MESSAGE);
        String traceString = String.format(ExceptionConstants.TRACE_FORMAT_SIMPLE,
                throwable.getMessage(),
                throwable.getCause() != null ? throwable.getCause().toString() : "N/A"
        );
        log.error(LogMessages.INTERNAL_SERVER_ERROR_LOG, traceString);
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public int getOrder() {
        return ExceptionConstants.DEFAULT_EXCEPTION_HANDLER_ORDER;
    }
}
