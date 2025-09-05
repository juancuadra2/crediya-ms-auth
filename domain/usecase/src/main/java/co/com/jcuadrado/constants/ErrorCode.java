package co.com.jcuadrado.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BAD_REQUEST,
    NOT_FOUND,
    CONFLICT,
    UNAUTHORIZED,
    INTERNAL_ERROR
}