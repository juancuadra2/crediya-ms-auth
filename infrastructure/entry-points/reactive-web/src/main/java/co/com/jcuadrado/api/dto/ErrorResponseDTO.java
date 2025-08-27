package co.com.jcuadrado.api.dto;

import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
public record ErrorResponseDTO(
        Set<String> messages,
        String error,
        String status
) {
}
