package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.NoArgsConstructor;

import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidationUtil {

    public static <T> Mono<Void> validateOrThrow(Validator validator, T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            Set<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            return Mono.error(new ValidationException(errorMessages));
        }
        return Mono.empty();
    }
}
