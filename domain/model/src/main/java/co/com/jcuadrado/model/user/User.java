package co.com.jcuadrado.model.user;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String documentNumber;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String role;
    private Double baseSalary;
}