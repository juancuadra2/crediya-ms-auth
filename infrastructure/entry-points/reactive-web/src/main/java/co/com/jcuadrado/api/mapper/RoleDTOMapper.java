package co.com.jcuadrado.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.jcuadrado.api.dto.role.CreateRoleDTO;
import co.com.jcuadrado.api.dto.role.RoleDTO;
import co.com.jcuadrado.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface RoleDTOMapper {
    RoleDTO toDTO(Role role);

    @Mapping(target = "id", ignore = true)
    Role toDomain(CreateRoleDTO createRoleDTO);

    default Flux<RoleDTO> toDTOFlux(Flux<Role> roleFlux) {
        return roleFlux.map(this::toDTO);
    }

    default Mono<RoleDTO> toDTOMono(Mono<Role> roleMono) {
        return roleMono.map(this::toDTO);
    }
}
