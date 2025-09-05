package co.com.jcuadrado.usecase.login;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.PasswordEncoderGateway;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public record LogInUseCase(UserRepository userRepository, PasswordEncoderGateway passwordEncoderGateway) {

    public Mono<User> logIn(String email, String password) {
        return userRepository.getUserByEmail(email)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.USER_NOT_FOUND, ErrorCode.NOT_FOUND)))
                .filter(user -> passwordEncoderGateway.matches(password, user.getPassword()))
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.INVALID_CREDENTIALS, ErrorCode.UNAUTHORIZED)));
    }

}
