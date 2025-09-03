package co.com.jcuadrado.exceptions;

import co.com.jcuadrado.constants.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public final ErrorCode code;

    public BusinessException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}