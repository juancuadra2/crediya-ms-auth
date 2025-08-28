package co.com.jcuadrado.api.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import co.com.jcuadrado.api.constant.doc.OpenApiSchemaConstants;
import co.com.jcuadrado.api.constant.doc.UserDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
@Schema(description = OpenApiSchemaConstants.USER_DTO_DESCRIPTION)
public record UserDTO(
        @Schema(description = UserDtoConstants.USER_ID_DESCRIPTION, example = UserDtoConstants.USER_ID_EXAMPLE)
        UUID id,
        
        @Schema(description = UserDtoConstants.DOCUMENT_NUMBER_DESCRIPTION, example = UserDtoConstants.DOCUMENT_NUMBER_EXAMPLE)
        String documentNumber,
        
        @Schema(description = UserDtoConstants.NAME_DESCRIPTION, example = UserDtoConstants.NAME_EXAMPLE)
        String name,
        
        @Schema(description = UserDtoConstants.LAST_NAME_DESCRIPTION, example = UserDtoConstants.LAST_NAME_EXAMPLE)
        String lastName,
        
        @Schema(description = UserDtoConstants.EMAIL_DESCRIPTION, example = UserDtoConstants.EMAIL_EXAMPLE)
        String email,
        
        @Schema(description = UserDtoConstants.PHONE_DESCRIPTION, example = UserDtoConstants.PHONE_EXAMPLE)
        String phone,
        
        @Schema(description = UserDtoConstants.ADDRESS_DESCRIPTION, example = UserDtoConstants.ADDRESS_EXAMPLE)
        String address,
        
        @Schema(description = UserDtoConstants.BIRTH_DATE_DESCRIPTION, example = UserDtoConstants.BIRTH_DATE_EXAMPLE)
        LocalDate birthDate,
        
        @Schema(description = UserDtoConstants.BASE_SALARY_DESCRIPTION, example = UserDtoConstants.BASE_SALARY_EXAMPLE)
        BigDecimal baseSalary,
        
        @Schema(description = UserDtoConstants.ROLE_DESCRIPTION, example = UserDtoConstants.ROLE_EXAMPLE)
        String role
) {
}
