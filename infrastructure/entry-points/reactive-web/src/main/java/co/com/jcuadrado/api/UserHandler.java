package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constants.SuccessStatus;
import co.com.jcuadrado.api.dto.user.CreateUserDTO;
import co.com.jcuadrado.api.dto.user.UserDTO;
import co.com.jcuadrado.api.mapper.UserDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
import co.com.jcuadrado.usecase.user.UserUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final UserDTOMapper userDTOMapper;
    private final Validator validator;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
                .flatMap(createUserDTO -> ValidationUtil.validateAndReturnError(validator, createUserDTO)
                        .switchIfEmpty(
                                userUseCase.saveUser(userDTOMapper.toModel(createUserDTO))
                                        .transform(userDTOMapper::toDTOMono)
                                        .flatMap(userDTO -> ResponseUtil.buildSuccessResponse(userDTO, SuccessStatus.CREATED))
                        )
                );
    }

    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userDTOMapper.toDTOFlux(userUseCase.getAllUsers()), UserDTO.class);
    }
}
