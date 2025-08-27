package co.com.jcuadrado.api.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessStatus {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    NO_CONTENT(HttpStatus.NO_CONTENT);

    private final HttpStatus httpStatus;

    SuccessStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}