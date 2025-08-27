package co.com.jcuadrado.r2dbc.exception;

import co.com.jcuadrado.r2dbc.constant.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorCode code;

    public GeneralException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public GeneralException(String message, ErrorCode code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}