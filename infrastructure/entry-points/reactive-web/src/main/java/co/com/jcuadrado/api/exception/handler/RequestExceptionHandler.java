package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import co.com.jcuadrado.api.exception.RequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestExceptionHandler implements ExceptionHandler<RequestException>{

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof RequestException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, RequestException throwable) {
        Set<String> messages = Set.of(throwable.getMessage());
        log.error(LogMessages.JWT_AUTHENTICATION_FAILED_LOG, throwable.getMessage());
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, throwable.getHttpStatus());
    }
}
