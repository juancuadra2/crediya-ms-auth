package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.user.CreateUserDTO;
import co.com.jcuadrado.api.dto.user.UpdateUserDTO;
import co.com.jcuadrado.api.dto.user.UserDTO;
import co.com.jcuadrado.model.user.User;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
    UserDTO toDTO(User user);
    User toModel(CreateUserDTO userDTO);
    User toModel(UpdateUserDTO userDTO);

    default Mono<UserDTO> toDTOMono(Mono<User> userMono) {
        return userMono.map(this::toDTO);
    }

    default Flux<UserDTO> toDTOFlux(Flux<User> userFlux) {
        return userFlux.map(this::toDTO);
    }
}
