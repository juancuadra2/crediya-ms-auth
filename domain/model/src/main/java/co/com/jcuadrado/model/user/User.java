package co.com.jcuadrado.model.user;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String documentNumber;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String role;
    private BigDecimal baseSalary;
}