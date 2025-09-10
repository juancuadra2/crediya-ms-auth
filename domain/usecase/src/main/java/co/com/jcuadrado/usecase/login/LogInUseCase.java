package co.com.jcuadrado.usecase.login;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.handler.LoginPayloadValidator;
import co.com.jcuadrado.model.auth.Login;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.auth.gateways.PasswordEncoderGateway;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import co.com.jcuadrado.usecase.role.RoleUseCase;
import reactor.core.publisher.Mono;

public record LogInUseCase(
        UserRepository userRepository,
        PasswordEncoderGateway passwordEncoderGateway,
        RoleUseCase roleUseCase
) {

    public Mono<User> logIn(Login login) {
        return validateLoginPayload(login)
                .flatMap(this::authenticateUser)
                .flatMap(this::attachRoleToUser);
    }

    private Mono<Login> validateLoginPayload(Login login) {
        return LoginPayloadValidator.validate(login);
    }

    private Mono<User> authenticateUser(Login login) {
        return getUserByEmail(login.getEmail())
                .flatMap(user -> validatePassword(login.getPassword(), user));
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