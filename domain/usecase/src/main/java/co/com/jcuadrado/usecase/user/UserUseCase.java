package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.handler.UserExistenceValidator;
import co.com.jcuadrado.handler.UserPayloadValidator;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.PasswordEncoderGateway;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record UserUseCase(
        UserRepository userRepository,
        PasswordEncoderGateway passwordEncoderGateway
) {

    public Mono<User> saveUser(User user) {
        return UserPayloadValidator.validate(user)
                .flatMap(validUser -> {
                    String encryptedPassword = passwordEncoderGateway.encode(validUser.getPassword());
                    User userWithEncryptedPassword = validUser.toBuilder()
                            .password(encryptedPassword)
                            .build();

                    return userRepository.getUserByEmailOrDocumentNumber(userWithEncryptedPassword.getEmail(), userWithEncryptedPassword.getDocumentNumber())
                            .flatMap(existingUser -> UserExistenceValidator.validate(userWithEncryptedPassword, existingUser))
                            .switchIfEmpty(userRepository.saveUser(userWithEncryptedPassword));
                });
    }

    public Mono<User> getUserByDocumentNumber(String documentNumber) {
        return this.userRepository.getUserByDocumentNumber(documentNumber)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.USER_NOT_FOUND, ErrorCode.NOT_FOUND)));
    }

    public Flux<User> getAllUsers() {
        return userRepository.getAllUsers()
                .onErrorMap(ex -> new BusinessException(ErrorMessage.ERROR_GETTING_USERS, ErrorCode.INTERNAL_ERROR));
    }
}
