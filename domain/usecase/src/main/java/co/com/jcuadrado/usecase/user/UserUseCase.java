package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.handler.UserExistenceValidator;
import co.com.jcuadrado.handler.UserPayloadValidator;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record UserUseCase(
        UserRepository userRepository
) {

    public Mono<User> saveUser(User user) {
        return UserPayloadValidator.validate(user)
        .flatMap(validUser -> userRepository.getUserByEmailOrDocumentNumber(validUser.getEmail(), validUser.getDocumentNumber())
        .flatMap(existingUser -> UserExistenceValidator.validate(validUser, existingUser))
        .switchIfEmpty(userRepository.saveUser(validUser)));
    }

    public Mono<User> getUserByEmail(String email) {
        return this.userRepository.getUserByEmail(email)
            .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.USER_NOT_FOUND, ErrorCode.NOT_FOUND)));
    }

    public Flux<User> getAllUsers() {
        return userRepository.getAllUsers()
                .onErrorMap(ex -> new BusinessException(ErrorMessage.ERROR_GETTING_USERS, ErrorCode.INTERNAL_ERROR));
    }
}
