package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import co.com.jcuadrado.api.exception.AuthException;
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
public class AuthExceptionHandler implements ExceptionHandler<AuthException> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof AuthException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AuthException throwable) {
        HttpStatus status = throwable.getType().equals(AuthException.ErrorType.UNAUTHORIZED)
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.FORBIDDEN;
        Set<String> messages = Set.of(throwable.getMessage());

        log.error(LogMessages.JWT_AUTHENTICATION_FAILED_LOG, throwable.getMessage());
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, status);
    }
}
