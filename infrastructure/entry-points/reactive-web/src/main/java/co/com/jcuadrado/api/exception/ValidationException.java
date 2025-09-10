package co.com.jcuadrado.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Getter
public class ValidationException extends ResponseStatusException {

    private final Set<String> messages;

    public ValidationException(Set<String> messages) {
        super(HttpStatus.BAD_REQUEST);
        this.messages = messages;
    }
}
