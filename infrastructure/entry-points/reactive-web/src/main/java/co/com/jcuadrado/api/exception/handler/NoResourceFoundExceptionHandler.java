package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class NoResourceFoundExceptionHandler implements ExceptionHandler<NoResourceFoundException> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof NoResourceFoundException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, NoResourceFoundException throwable) {
        Set<String> messages = Set.of(ObjectUtils.nullSafeToString(throwable.getReason()));
        log.error(LogMessages.NOT_FOUND_EXCEPTION_LOG, throwable);
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, HttpStatus.valueOf(throwable.getStatusCode().value()));
    }
}
