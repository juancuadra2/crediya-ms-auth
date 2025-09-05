package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.SuccessStatus;
import co.com.jcuadrado.api.dto.AuthResponse;
import co.com.jcuadrado.api.dto.LoginRequest;
import co.com.jcuadrado.api.security.CustomUserDetails;
import co.com.jcuadrado.api.security.JwtProvider;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
import co.com.jcuadrado.usecase.login.LogInUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final LogInUseCase logInUseCase;
    private final JwtProvider jwtProvider;
    private final Validator validator;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest -> ValidationUtil.validateAndReturnError(validator, loginRequest)
                        .switchIfEmpty(
                                logInUseCase.logIn(loginRequest.getEmail(), loginRequest.getPassword())
                                        .map(user -> {
                                            CustomUserDetails userDetails = new CustomUserDetails(user);
                                            String token = jwtProvider.generateToken(userDetails);
                                            return new AuthResponse(token, user.getEmail(), user.getRole());
                                        })
                                        .flatMap(authResponse -> ResponseUtil.buildSuccessResponse(authResponse,
                                                SuccessStatus.OK))));
    }
}
