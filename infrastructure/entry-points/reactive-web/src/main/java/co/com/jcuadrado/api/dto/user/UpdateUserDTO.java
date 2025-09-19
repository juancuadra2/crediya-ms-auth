package co.com.jcuadrado.api.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import co.com.jcuadrado.api.constant.validation.ValidationMessages;
import co.com.jcuadrado.api.constant.doc.OpenApiSchemaConstants;
import co.com.jcuadrado.api.constant.validation.RegexPatterns;
import co.com.jcuadrado.api.constant.doc.UserDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder(toBuilder = true)
@Schema(description = OpenApiSchemaConstants.UPDATE_USER_DTO_DESCRIPTION)
public record UpdateUserDTO(
        @Schema(
                description = UserDtoConstants.DOCUMENT_NUMBER_DESCRIPTION,
                example = UserDtoConstants.DOCUMENT_NUMBER_EXAMPLE
        )
        @NotNull(message = ValidationMessages.DOCUMENT_NUMBER_REQUIRED)
        String documentNumber,

        @Schema(
                description = UserDtoConstants.NAME_DESCRIPTION,
                example = UserDtoConstants.NAME_EXAMPLE,
                minLength = 2, maxLength = 50
        )
        @NotBlank(message = ValidationMessages.NAME_REQUIRED)
        @Size(
                min = UserDtoConstants.NAME_MIN_LENGTH,
                max = UserDtoConstants.NAME_MAX_LENGTH,
                message = ValidationMessages.NAME_SIZE
        )
        String name,

        @Schema(
                description = UserDtoConstants.LAST_NAME_DESCRIPTION,
                example = UserDtoConstants.LAST_NAME_EXAMPLE,
                minLength = 2, maxLength = 50
        )
        @Size(min = UserDtoConstants.LAST_NAME_MIN_LENGTH, max = UserDtoConstants.LAST_NAME_MAX_LENGTH, message = ValidationMessages.LAST_NAME_SIZE)
        String lastName,

        @Schema(
                description = UserDtoConstants.EMAIL_DESCRIPTION,
                example = UserDtoConstants.EMAIL_EXAMPLE
        )
        @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
        @Email(message = ValidationMessages.EMAIL_INVALID_FORMAT)
        String email,

        @Schema(
                description = UserDtoConstants.PHONE_DESCRIPTION,
                example = UserDtoConstants.PHONE_EXAMPLE,
                pattern = UserDtoConstants.PHONE_PATTERN
        )
        @Pattern(regexp = RegexPatterns.PHONE, message = ValidationMessages.PHONE_INVALID_PATTERN)
        String phone,

        @Schema(
                description = UserDtoConstants.ADDRESS_DESCRIPTION,
                example = UserDtoConstants.ADDRESS_EXAMPLE,
                maxLength = 255
        )
        @Size(max = UserDtoConstants.ADDRESS_MAX_LENGTH, message = ValidationMessages.ADDRESS_MAX_LENGTH)
        String address,

        @Schema(
                description = UserDtoConstants.BIRTH_DATE_DESCRIPTION,
                example = UserDtoConstants.BIRTH_DATE_EXAMPLE
        )
        LocalDate birthDate,

        @Schema(
                description = UserDtoConstants.ROLE_DESCRIPTION,
                example = UserDtoConstants.ROLE_EXAMPLE,
                allowableValues = {UserDtoConstants.ROLE_ADMIN, UserDtoConstants.ROLE_USER}
        )
        @NotBlank(message = ValidationMessages.ROLE_REQUIRED)
        String role,

        @Schema(
                description = UserDtoConstants.BASE_SALARY_DESCRIPTION,
                example = UserDtoConstants.BASE_SALARY_EXAMPLE,
                minimum = UserDtoConstants.BASE_SALARY_MINIMUM_EXAMPLE
        )
        @DecimalMin(value = UserDtoConstants.BASE_SALARY_MINIMUM, inclusive = false, message = ValidationMessages.BASE_SALARY_MINIMUM)
        BigDecimal baseSalary
) {
}
