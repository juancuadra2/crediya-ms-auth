package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.exception.AuthException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationUtil {
    private static Mono<Authentication> getAuthentication() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication);
    }

    private static Mono<Set<String>> getCurrentUserRoles() {
        return getAuthentication()
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
                );
    }

    public static Mono<ServerResponse> requireAnyRole(String[] allowedRoles, Mono<ServerResponse> response) {
        return getCurrentUserRoles()
                .switchIfEmpty(Mono.error(new AuthException(AuthException.ErrorType.FORBIDDEN, AuthConstants.USER_NOT_AUTHENTICATED_ERROR)))
                .flatMap(userRoles -> {
                    boolean hasRequiredRole = Arrays.stream(allowedRoles)
                            .anyMatch(userRoles::contains);
                    if (hasRequiredRole) {
                        return response;
                    } else {
                        return Mono.error(new AuthException(AuthException.ErrorType.FORBIDDEN, AuthConstants.ACCESS_DENIED_ERROR));
                    }
                });
    }
}
