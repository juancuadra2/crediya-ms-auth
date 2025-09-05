package co.com.jcuadrado.api.dto;

import co.com.jcuadrado.api.constant.validation.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email(message = ValidationMessages.EMAIL_INVALID_FORMAT)
    private String email;
    
    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    private String password;
}
