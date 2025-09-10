package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.auth.AuthConstants;

import co.com.jcuadrado.api.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getAttribute(AuthConstants.TOKEN_ATTRIBUTE);
        if (token == null) {
            token = extractTokenFromRequest(exchange);
        }
        if (token == null) {
            return Mono.error(new AuthException(AuthException.ErrorType.UNAUTHORIZED, AuthConstants.NO_TOKEN_PROVIDED_ERROR));
        }
        return jwtAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
                .map(SecurityContextImpl::new)
                .cast(SecurityContext.class);
    }

    private String extractTokenFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith(AuthConstants.BEARER_PREFIX)) {
            return authHeader.replace(AuthConstants.BEARER_PREFIX, "");
        }
        return null;
    }
}
