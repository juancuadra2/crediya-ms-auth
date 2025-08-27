package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.GeneralDomainException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static co.com.jcuadrado.util.ObjectsUtil.isNullOrEmpty;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {
        if (isNullOrEmpty(user.getDocumentNumber())){
            return Mono.error(new GeneralDomainException(ErrorMessage.DOCUMENT_NUMBER_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getName())) {
            return Mono.error(new GeneralDomainException(ErrorMessage.NAME_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getLastName())) {
            return Mono.error(new GeneralDomainException(ErrorMessage.LAST_NAME_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getEmail())) {
            return Mono.error(new GeneralDomainException(ErrorMessage.EMAIL_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (Objects.isNull(user.getBaseSalary())){
            return Mono.error(new GeneralDomainException(ErrorMessage.BASE_SALARY_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (user.getBaseSalary() <= 0 || user.getBaseSalary() > 15_000_000) {
            return Mono.error(new GeneralDomainException(ErrorMessage.BASE_SALARY_RANGE, ErrorCode.BAD_REQUEST));
        }

        return userRepository.getUserByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new GeneralDomainException(ErrorMessage.EMAIL_ALREADY_EXISTS, ErrorCode.CONFLICT)))
                .switchIfEmpty(userRepository.saveUser(user));
    }

    public Flux<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
