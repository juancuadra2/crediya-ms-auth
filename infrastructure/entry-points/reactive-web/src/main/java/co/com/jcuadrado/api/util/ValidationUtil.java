package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static <T> Mono<ServerResponse> validateAndReturnError(Validator validator, T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            Set<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(HttpStatus.BAD_REQUEST.name())
                    .status(ObjectUtils.nullSafeToString(HttpStatus.BAD_REQUEST.value()))
                    .messages(errorMessages)
                    .build();

            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(errorResponse);
        }

        return Mono.empty();
    }
}
