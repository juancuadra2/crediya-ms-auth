package co.com.jcuadrado.model.auth;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoginRequest {
    private String email;
    private String password;
}
