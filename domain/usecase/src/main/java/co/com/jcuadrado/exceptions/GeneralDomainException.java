package co.com.jcuadrado.exceptions;

import co.com.jcuadrado.constants.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralDomainException extends RuntimeException {

    private final ErrorCode code;

    public GeneralDomainException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}