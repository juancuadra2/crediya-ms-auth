package co.com.jcuadrado.api;

import co.com.jcuadrado.api.dto.CreateRoleDTO;
import co.com.jcuadrado.api.dto.RoleDTO;
import co.com.jcuadrado.api.mapper.RoleDTOMapper;
import co.com.jcuadrado.usecase.role.RoleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RoleUseCase roleUseCase;
    private final RoleDTOMapper roleDTOMapper;

    public Mono<ServerResponse> listenSaveRole(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateRoleDTO.class)
                .map(roleDTOMapper::toDomain)
                .flatMap(roleUseCase::saveRole)
                .transform(roleDTOMapper::toDTOMono)
                .flatMap(roleDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(roleDTO));
    }

    public Mono<ServerResponse> listenGetAllRoles(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(roleDTOMapper.toDTOFlux(roleUseCase.getAllRoles()), RoleDTO.class);
    }
}
