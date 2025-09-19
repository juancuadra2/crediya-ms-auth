package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.validator.UserConflictValidator;
import co.com.jcuadrado.validator.UserPayloadValidator;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.auth.gateways.PasswordEncoderGateway;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public record SaveUserUseCase(
        UserRepository userRepository,
        PasswordEncoderGateway passwordEncoderGateway
) {

    public Mono<User> saveUser(User user) {
        return UserPayloadValidator.validate(user)
                .flatMap(validUser -> encryptUserPassword(validUser, passwordEncoderGateway))
                .flatMap(encUser -> isValidToSave(encUser, userRepository))
                .flatMap(userRepository::saveUser);
    }

    public static Mono<User> encryptUserPassword(User user, PasswordEncoderGateway passwordEncoder) {
        return passwordEncoder.encode(user.getPassword())
                .doOnNext(user::setPassword)
                .thenReturn(user);
    }

    private static Mono<User> isValidToSave(User user, UserRepository userRepository) {
        return userRepository.getUserByEmailOrDocumentNumber(user.getEmail(), user.getDocumentNumber())
                .flatMap(existingUser -> UserConflictValidator.validate(user, existingUser))
                .switchIfEmpty(Mono.just(user));
    }
}
