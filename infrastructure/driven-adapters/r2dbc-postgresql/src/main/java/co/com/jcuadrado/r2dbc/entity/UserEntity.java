package co.com.jcuadrado.r2dbc.entity;

import co.com.jcuadrado.r2dbc.constant.UserConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = UserConstants.USER_TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {
    @Id
    private UUID id;
    private String documentNumber;
    private String name;
    @Column(UserConstants.LAST_NAME_COLUMN_NAME)
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private BigDecimal baseSalary;
    @Column(UserConstants.ROLE_ID_COLUMN_NAME)
    private UUID role;
}
