package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.role.CreateRoleDTO;
import co.com.jcuadrado.api.dto.role.RoleDTO;
import co.com.jcuadrado.api.dto.role.UpdateRoleDTO;
import co.com.jcuadrado.model.role.Role;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface RoleDTOMapper {
    // Conversión de Domain Model a DTO para respuestas
    RoleDTO toDTO(Role role);

    // Conversión de DTO a Domain Model (para crear)
    Role toDomain(CreateRoleDTO createRoleDTO);

    // Conversión de UpdateDTO a Domain Model
    Role toDomain(UpdateRoleDTO updateRoleDTO);

    // Métodos reactivos para colecciones
    default Flux<RoleDTO> toDTOFlux(Flux<Role> roleFlux) {
        return roleFlux.map(this::toDTO);
    }

    default Mono<RoleDTO> toDTOMono(Mono<Role> roleMono) {
        return roleMono.map(this::toDTO);
    }
}
