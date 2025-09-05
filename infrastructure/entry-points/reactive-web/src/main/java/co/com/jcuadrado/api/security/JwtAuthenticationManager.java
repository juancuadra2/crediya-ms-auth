package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.auth.JwtConstants;
import co.com.jcuadrado.api.constant.error.AuthErrorMessages;
import co.com.jcuadrado.api.constant.error.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        return Mono.just(authentication)
                .filter(auth -> jwtProvider.validate(token))
                .switchIfEmpty(Mono.error(new RuntimeException(AuthErrorMessages.INVALID_TOKEN_ERROR)))
                .map(auth -> jwtProvider.getClaims(token))
                .map(claims -> {
                    Authentication authResult = new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            Stream.of(claims.get(JwtConstants.ROLES_CLAIM))
                                    .filter(role -> role instanceof List)
                                    .map(role -> {
                                        @SuppressWarnings("unchecked")
                                        List<Map<String, String>> roleList = (List<Map<String, String>>) role;
                                        return roleList;
                                    })
                                    .flatMap(role -> role.stream()
                                            .map(r -> r.get(JwtConstants.AUTHORITY_KEY))
                                            .map(SimpleGrantedAuthority::new))
                                    .toList());
                    return authResult;
                })
                .doOnError(error -> log.error(LogMessages.JWT_AUTHENTICATION_FAILED_LOG, error.getMessage()));
    }
}
