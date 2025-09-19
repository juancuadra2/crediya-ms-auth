package co.com.jcuadrado.api.exception;

import co.com.jcuadrado.api.exception.handler.ExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final List<ExceptionHandler<? extends Throwable>> exceptionHandlers;

    public GlobalExceptionHandler(List<ExceptionHandler<? extends Throwable>> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers.stream()
                .sorted(Comparator.comparingInt(ExceptionHandler::getOrder))
                .toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Mono<Void> handle(@NonNull ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return exceptionHandlers.stream()
                .filter(handler -> handler.canHandle(ex))
                .findFirst()
                .map(handler -> ((ExceptionHandler<Throwable>) handler).handle(exchange, ex))
                .orElse(Mono.error(new RuntimeException("No handler found for exception: " + ex.getClass())));
    }
}
