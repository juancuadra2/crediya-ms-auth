package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.ErrorMessage;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
                    .error(ErrorMessage.VALIDATION_FAILED)
                    .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .messages(errorMessages)
                    .build();

            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(errorResponse);
        }

        return Mono.empty();
    }
}
