package co.com.jcuadrado.api.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class AuthException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum ErrorType {
        UNAUTHORIZED,
        FORBIDDEN,
    }

    private final ErrorType type;

    public AuthException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }
}
