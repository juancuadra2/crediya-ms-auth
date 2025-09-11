package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.SuccessStatus;
import co.com.jcuadrado.api.constant.validation.ValidationMessages;
import co.com.jcuadrado.api.dto.auth.LoginRequestDTO;
import co.com.jcuadrado.api.mapper.AuthResponseDTOMapper;
import co.com.jcuadrado.api.mapper.LoginRequestDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
import co.com.jcuadrado.usecase.auth.LoginUseCase;
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

    private final LoginUseCase loginUseCase;
    private final Validator validator;
    private final LoginRequestDTOMapper loginRequestDTOMapper;
    private final AuthResponseDTOMapper authResponseDTOMapper;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequestDTO.class)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_REQUEST_BODY)))
                .flatMap(dto -> ValidationUtil.validateOrThrow(validator, dto)
                        .then(Mono.defer(() -> loginUseCase.login(loginRequestDTOMapper.toModel(dto))
                                        .transform(authResponseDTOMapper::toDTOMono))
                                .flatMap(authResponseDTO -> ResponseUtil.buildSuccessResponse(authResponseDTO, SuccessStatus.OK)))
                );
    }

}