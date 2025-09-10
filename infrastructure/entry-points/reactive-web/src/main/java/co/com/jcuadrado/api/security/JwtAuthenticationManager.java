package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.exception.AuthException;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthTokenGateway authTokenGateway;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        return validateToken(token)
                .flatMap(this::buildAuthentication);
    }

    private Mono<String> validateToken(String token) {
        return authTokenGateway.validateToken(token)
                .filter(Boolean::booleanValue)
                .map(valid -> token)
                .switchIfEmpty(Mono.error(new AuthException(AuthException.ErrorType.UNAUTHORIZED, AuthConstants.INVALID_TOKEN_ERROR)));
    }

    private Mono<Authentication> buildAuthentication(String token) {
        return Mono.zip(
                authTokenGateway.getSubject(token),
                authTokenGateway.getRoles(token).map(SimpleGrantedAuthority::new).collectList()
        ).map(tuple -> {
            String subject = tuple.getT1();
            List<SimpleGrantedAuthority> authorities = tuple.getT2();
            return new UsernamePasswordAuthenticationToken(subject, token, authorities);
        });
    }
}