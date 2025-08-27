package co.com.jcuadrado.api.dto.user;

import co.com.jcuadrado.api.constants.ErrorMessage;
import co.com.jcuadrado.api.constants.RegexPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Dto para creación de usuario.")
public record CreateUserDTO(
        @NotNull(message = ErrorMessage.DOCUMENT_NUMBER_REQUIRED)
        String documentNumber,

        @NotBlank(message = ErrorMessage.NAME_REQUIRED)
        @Size(min = 2, max = 50, message = ErrorMessage.NAME_SIZE)
        String name,

        @Size(min = 2, max = 50, message = ErrorMessage.LAST_NAME_SIZE)
        String lastName,

        @NotBlank(message = ErrorMessage.EMAIL_REQUIRED)
        @Email(message = ErrorMessage.EMAIL_INVALID_FORMAT)
        String email,

        @Pattern(regexp = RegexPattern.PHONE, message = ErrorMessage.PHONE_INVALID_PATTERN)
        String phone,

        @Size(max = 255, message = ErrorMessage.ADDRESS_MAX_LENGTH)
        String address,

        LocalDate birthDate,

        @NotBlank(message = ErrorMessage.ROLE_REQUIRED)
        String role,

        @DecimalMin(value = "0.0", inclusive = false, message = ErrorMessage.BASE_SALARY_MINIMUM)
        Double baseSalary
) {
}
