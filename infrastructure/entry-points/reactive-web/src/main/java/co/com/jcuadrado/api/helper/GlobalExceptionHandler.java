package co.com.jcuadrado.api.helper;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.jcuadrado.api.constant.ExceptionError;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.exceptions.BusinessException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @NonNull
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ErrorResponseDTO errorResponse;
        HttpStatus httpStatus;

        switch (ex) {
            case BusinessException businessException -> {
                httpStatus = mapErrorCodeToHttpStatus(businessException.code);

                errorResponse = ErrorResponseDTO.builder()
                        .messages(Set.of(businessException.getMessage()))
                        .error(businessException.code.name())
                        .status(httpStatus.name())
                        .build();

                log.error(ExceptionError.DOMAIN_EXCEPTION_LOG, businessException.getMessage());
            }
            case WebExchangeBindException bindEx -> {
                // Errores de validación
                httpStatus = HttpStatus.BAD_REQUEST;

                Set<String> validationMessages = bindEx.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.toSet());

                errorResponse = ErrorResponseDTO.builder()
                        .messages(validationMessages)
                        .error(ExceptionError.VALIDATION_EXCEPTION_LOG)
                        .status(httpStatus.name())
                        .build();

                log.error(ExceptionError.VALIDATION_EXCEPTION_LOG, validationMessages);
            }
            default -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                errorResponse = ErrorResponseDTO.builder()
                        .messages(Set.of(ExceptionError.INTERNAL_SERVER_ERROR_MESSAGE))
                        .error(ExceptionError.INTERNAL_SERVER_ERROR_TITLE)
                        .status(httpStatus.name())
                        .build();

                log.error(ExceptionError.INTERNAL_SERVER_ERROR_LOG, ex);
            }
        }

        response.setStatusCode(httpStatus);

        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error(ExceptionError.SERIALIZATION_EXCEPTION_MESSAGE, e);
            return response.setComplete();
        }
    }

    private HttpStatus mapErrorCodeToHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
