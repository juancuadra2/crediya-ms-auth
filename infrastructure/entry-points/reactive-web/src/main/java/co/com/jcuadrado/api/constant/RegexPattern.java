package co.com.jcuadrado.api.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexPattern {
    public static final String PHONE = "^\\+?[1-9]\\d{1,14}$";
}