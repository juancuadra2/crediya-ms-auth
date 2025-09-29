package co.com.jcuadrado.r2dbc.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BAD_REQUEST,
    NOT_FOUND,
    CONFLICT,
    INTERNAL_ERROR
}