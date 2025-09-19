package co.com.jcuadrado.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

@Getter
public class AuthException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum ErrorType {
        UNAUTHORIZED,
        FORBIDDEN;
    }

    private final ErrorType type;

    public AuthException(ErrorType type, String message) {
        super(httpStatus, message);
        this.type = type;
    }

    private static final HttpStatusCode httpStatus = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());

}
