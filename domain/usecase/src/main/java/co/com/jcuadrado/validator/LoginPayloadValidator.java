package co.com.jcuadrado.validator;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.auth.LoginRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.jcuadrado.util.ObjectsUtil.isNullOrEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginPayloadValidator {

    public static Mono<LoginRequest> validate(LoginRequest loginRequest) {
        if (loginRequest == null) {
            return Mono.error(new BusinessException(ErrorMessage.INVALID_CREDENTIALS, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(loginRequest.getPassword())) {
            return Mono.error(new BusinessException(ErrorMessage.PASSWORD_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (loginRequest.getPassword().length() < 8) {
            return Mono.error(new BusinessException(ErrorMessage.PASSWORD_MIN_LENGTH, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(loginRequest.getEmail())) {
            return Mono.error(new BusinessException(ErrorMessage.EMAIL_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        return Mono.just(loginRequest);
    }

}
