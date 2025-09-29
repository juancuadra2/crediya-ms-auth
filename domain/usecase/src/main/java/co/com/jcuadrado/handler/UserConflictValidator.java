package co.com.jcuadrado.handler;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.user.User;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserConflictValidator {

    public static <T extends User> Mono<T> validate(T validUser, T existingUser) {
        if (existingUser.getEmail().equalsIgnoreCase(validUser.getEmail())) {
            return Mono.error(new BusinessException(ErrorMessage.EMAIL_ALREADY_EXISTS, ErrorCode.CONFLICT));
        }
        if (existingUser.getDocumentNumber().equalsIgnoreCase(validUser.getDocumentNumber())) {
            return Mono.error(new BusinessException(ErrorMessage.DOCUMENT_NUMBER_ALREADY_EXISTS, ErrorCode.CONFLICT));
        }
        return Mono.just(validUser);
    }

}
