package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.auth.AuthResponseDTO;
import co.com.jcuadrado.model.auth.AuthResponse;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface AuthResponseDTOMapper {

    AuthResponseDTO toDTO(AuthResponse authResponse);

    default Mono<AuthResponseDTO> toDTOMono(Mono<AuthResponse> authResponseMono) {
        return authResponseMono.map(this::toDTO);
    }
}
