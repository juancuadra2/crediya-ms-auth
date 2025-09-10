package co.com.jcuadrado.api.exception.handler;

import co.com.jcuadrado.api.constant.error.LogMessages;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Log4j2
@RequiredArgsConstructor
public class ErrorResponseWriter {

    private final ObjectMapper objectMapper;

    public Mono<Void> writeErrorResponse(ServerHttpResponse response, Set<String> messages, HttpStatus status) {
        response.setStatusCode(status);
        ErrorResponseDTO errorResponse = buildErrorResponse(messages, status);
        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            DataBuffer buffer = response.bufferFactory()
                    .wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error(LogMessages.SERIALIZATION_EXCEPTION_MESSAGE, e);
            return response.setComplete();
        }
    }

    private ErrorResponseDTO buildErrorResponse(Set<String> messages, HttpStatus status) {
        return ErrorResponseDTO.builder()
                .messages(messages)
                .error(status.name())
                .status(String.valueOf(status.value()))
                .build();
    }
}