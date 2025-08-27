package co.com.jcuadrado.api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Dto de usuario.")
public record UserDTO(
        UUID id,
        String documentNumber,
        String name,
        String lastName,
        String email,
        String phone,
        String address,
        LocalDate birthDate,
        Double baseSalary,
        String role
) {
}
