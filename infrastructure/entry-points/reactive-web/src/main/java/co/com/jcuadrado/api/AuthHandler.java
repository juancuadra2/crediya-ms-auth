package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.SuccessStatus;
import co.com.jcuadrado.api.constant.validation.ValidationMessages;
import co.com.jcuadrado.api.dto.auth.AuthResponseDTO;
import co.com.jcuadrado.api.dto.auth.LoginRequestDTO;
import co.com.jcuadrado.api.mapper.LoginRequestDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.usecase.login.LogInUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final LogInUseCase logInUseCase;
    private final Validator validator;
    private final LoginRequestDTOMapper mapper;
    private final AuthTokenGateway authTokenGateway;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequestDTO.class)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_REQUEST_BODY)))
                .flatMap(dto -> ValidationUtil.validateOrThrow(validator, dto)
                        .then(logInUseCase.logIn(mapper.toModel(dto))
                                .flatMap(this::generateTokenResponse))
                );
    }

    private Mono<ServerResponse> generateTokenResponse(User user) {
        return authTokenGateway.generateToken(user)
                .map(token -> createAuthResponse(token, user))
                .flatMap(authResponse -> ResponseUtil.buildSuccessResponse(authResponse, SuccessStatus.OK));
    }

    private AuthResponseDTO createAuthResponse(String token, User user) {
        return new AuthResponseDTO(token, user.getEmail(), user.getRole());
    }
}