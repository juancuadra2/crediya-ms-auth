package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class ValidationExceptionHandler implements ExceptionHandler<WebExchangeBindException> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof WebExchangeBindException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, WebExchangeBindException throwable) {
        Set<String> messages = throwable.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toSet());

        log.error(LogMessages.VALIDATION_EXCEPTION_LOG, messages);
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, HttpStatus.BAD_REQUEST);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
