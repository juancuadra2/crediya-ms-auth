package co.com.jcuadrado.r2dbc.entity;

import co.com.jcuadrado.r2dbc.constant.RoleConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = RoleConstants.ROLE_TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RoleEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
}
