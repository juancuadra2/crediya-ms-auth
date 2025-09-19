package co.com.jcuadrado.handler;

import java.math.BigDecimal;
import java.util.Objects;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.constants.ValidationConstants;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.user.User;
import static co.com.jcuadrado.util.ObjectsUtil.isNullOrEmpty;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserPayloadValidator {

    public static Mono<User> validate(User user) {
        if (isNullOrEmpty(user.getDocumentNumber())){
            return Mono.error(new BusinessException(ErrorMessage.DOCUMENT_NUMBER_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getName())) {
            return Mono.error(new BusinessException(ErrorMessage.NAME_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getLastName())) {
            return Mono.error(new BusinessException(ErrorMessage.LAST_NAME_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getEmail())) {
            return Mono.error(new BusinessException(ErrorMessage.EMAIL_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (isNullOrEmpty(user.getPassword())) {
            return Mono.error(new BusinessException(ErrorMessage.PASSWORD_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (user.getPassword().length() < 8) {
            return Mono.error(new BusinessException(ErrorMessage.PASSWORD_MIN_LENGTH, ErrorCode.BAD_REQUEST));
        }
        if (Objects.isNull(user.getBaseSalary())){
            return Mono.error(new BusinessException(ErrorMessage.BASE_SALARY_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (user.getBaseSalary().compareTo(BigDecimal.ZERO) <= ValidationConstants.MIN_SALARY_RANGE ||
            user.getBaseSalary().compareTo(new BigDecimal(ValidationConstants.MAX_SALARY_RANGE)) > ValidationConstants.MIN_SALARY_RANGE) {
            return Mono.error(new BusinessException(ErrorMessage.BASE_SALARY_RANGE, ErrorCode.BAD_REQUEST));
        }
        return Mono.just(user);
    }

}
