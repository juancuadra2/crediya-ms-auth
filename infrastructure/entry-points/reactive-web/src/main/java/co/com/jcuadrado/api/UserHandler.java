package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.UserEndpoints;
import co.com.jcuadrado.api.constant.auth.AuthRoles;
import co.com.jcuadrado.api.constant.validation.ValidationMessages;
import co.com.jcuadrado.api.util.AuthorizationUtil;
import co.com.jcuadrado.usecase.user.FindUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.jcuadrado.api.constant.SuccessStatus;
import co.com.jcuadrado.api.dto.user.CreateUserDTO;
import co.com.jcuadrado.api.dto.user.UserDTO;
import co.com.jcuadrado.api.mapper.UserDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
import co.com.jcuadrado.usecase.user.SaveUserUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final SaveUserUseCase saveUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final UserDTOMapper userDTOMapper;
    private final Validator validator;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return AuthorizationUtil.requireAnyRole(new String[]{AuthRoles.ADMIN.name(), AuthRoles.ADVISER.name()},
                serverRequest.bodyToMono(CreateUserDTO.class)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_REQUEST_BODY)))
                        .flatMap(createUserDTO -> ValidationUtil.validateOrThrow(validator, createUserDTO)
                                .then(Mono.defer(() -> saveUserUseCase.saveUser(userDTOMapper.toModel(createUserDTO))
                                        .transform(userDTOMapper::toDTOMono))))
                        .flatMap(userDTO -> ResponseUtil.buildSuccessResponse(userDTO, SuccessStatus.CREATED)));
    }

    /**
     * @param serverRequest This param is not used but is required by the framework.
     */
    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {
        return AuthorizationUtil.requireAnyRole(new String[]{AuthRoles.ADMIN.name(), AuthRoles.ADVISER.name()},
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(userDTOMapper.toDTOFlux(findUserUseCase.getAllUsers()), UserDTO.class));
    }

    public Mono<ServerResponse> listenGetUserByDocumentNumber(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDTOMapper.toDTOMono(findUserUseCase
                                .getUserByDocumentNumber(
                                        serverRequest.pathVariable(UserEndpoints.DOCUMENT_NUMBER_PATH_VARIABLE))),
                        UserDTO.class);
    }
}
