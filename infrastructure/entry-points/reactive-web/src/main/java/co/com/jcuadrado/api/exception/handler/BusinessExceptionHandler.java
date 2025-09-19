package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import co.com.jcuadrado.api.constant.error.ExceptionConstants;
import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.exceptions.BusinessException;
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
public class BusinessExceptionHandler implements ExceptionHandler<BusinessException> {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public boolean canHandle(Throwable throwable) {
        return throwable instanceof BusinessException;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, BusinessException throwable) {
        HttpStatus status = mapErrorCodeToHttpStatus(throwable.code);
        Set<String> messages = Set.of(throwable.getMessage());
        String traceString = String.format(ExceptionConstants.TRACE_FORMAT_BUSINESS,
                throwable.getMessage(),
                throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
                throwable.code
        );
        log.error(LogMessages.BUSINESS_EXCEPTION_LOG, traceString);
        return errorResponseWriter.writeErrorResponse(exchange.getResponse(), messages, status);
    }

    private HttpStatus mapErrorCodeToHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case ErrorCode.BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case ErrorCode.NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ErrorCode.CONFLICT -> HttpStatus.CONFLICT;
            case ErrorCode.UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    @Override
    public int getOrder() {
        return ExceptionConstants.BUSINESS_EXCEPTION_HANDLER_ORDER;
    }
}
