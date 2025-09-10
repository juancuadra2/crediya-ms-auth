package co.com.jcuadrado.jwtsecurity.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessages {
    public static final String TOKEN_EXPIRED_LOG = "Token expired: {}";
    public static final String TOKEN_UNSUPPORTED_LOG = "Token unsupported: {}";
    public static final String TOKEN_MALFORMED_LOG = "Token malformed: {}";
    public static final String ILLEGAL_ARGS_LOG = "Illegal args: {}";
}
