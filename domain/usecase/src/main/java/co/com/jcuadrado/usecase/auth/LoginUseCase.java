package co.com.jcuadrado.usecase.auth;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.handler.LoginPayloadValidator;
import co.com.jcuadrado.model.auth.AuthResponse;
import co.com.jcuadrado.model.auth.LoginRequest;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.auth.gateways.PasswordEncoderGateway;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import co.com.jcuadrado.usecase.role.RoleUseCase;
import reactor.core.publisher.Mono;

public record LoginUseCase(
        UserRepository userRepository,
        PasswordEncoderGateway passwordEncoderGateway,
        RoleUseCase roleUseCase,
        AuthTokenGateway authTokenGateway
) {

    public Mono<AuthResponse> login(LoginRequest loginRequest) {
        return validateLoginPayload(loginRequest)
                .flatMap(this::authenticateUser)
                .flatMap(this::attachRoleToUser)
                .flatMap(user -> authTokenGateway.generateToken(user)
                        .map(token -> AuthResponse.builder()
                                .token(token)
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build()));
    }

    private Mono<LoginRequest> validateLoginPayload(LoginRequest loginRequest) {
        return LoginPayloadValidator.validate(loginRequest);
    }

    private Mono<User> authenticateUser(LoginRequest loginRequest) {
        return getUserByEmail(loginRequest.getEmail())
                .flatMap(user -> validatePassword(loginRequest.getPassword(), user));
    }

    private Mono<User> validatePassword(String providedPassword, User user) {
        return passwordEncoderGateway.matches(providedPassword, user.getPassword())
                .flatMap(isValidPassword -> handlePasswordValidation(isValidPassword, user));
    }

    private Mono<User> handlePasswordValidation(Boolean isValidPassword, User user) {
        if (Boolean.TRUE.equals(isValidPassword)) {
            return Mono.just(user);
        }
        return Mono.error(createInvalidCredentialsException());
    }

    private Mono<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .switchIfEmpty(Mono.error(createInvalidCredentialsException()));
    }

    private Mono<User> attachRoleToUser(User user) {
        return roleUseCase.getRoleById(user.getRole())
                .map(role -> updateUserWithRoleName(user, role.getName()));
    }

    private User updateUserWithRoleName(User user, String roleName) {
        user.setRole(roleName);
        return user;
    }

    private BusinessException createInvalidCredentialsException() {
        return new BusinessException(ErrorMessage.INVALID_CREDENTIALS, ErrorCode.UNAUTHORIZED);
    }
}