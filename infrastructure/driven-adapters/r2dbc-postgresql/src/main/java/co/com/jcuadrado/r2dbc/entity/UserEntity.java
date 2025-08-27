package co.com.jcuadrado.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {
    @Id
    private UUID id;
    private String documentNumber;
    private String name;
    @Column("lastname")
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private Double baseSalary;
    @Column("role_id")
    private UUID role;
}
