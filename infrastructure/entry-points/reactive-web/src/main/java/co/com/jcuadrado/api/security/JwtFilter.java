package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.api.AuthEndpoints;
import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.constant.error.AuthErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final JwtProvider jwtProvider;

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        
        if (path.startsWith(AuthEndpoints.AUTH_API_PATH)) {
            return chain.filter(exchange);
        }
        
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        
        if (auth == null) {
            return Mono.error(new RuntimeException(AuthErrorMessages.NO_TOKEN_PROVIDED_ERROR));
        }
        
        if (!auth.startsWith(AuthConstants.BEARER_PREFIX)) {
            return Mono.error(new RuntimeException(AuthErrorMessages.INVALID_TOKEN_FORMAT_ERROR));
        }
        
        String token = auth.replace(AuthConstants.BEARER_PREFIX, "");
        
        if (!jwtProvider.validate(token)) {
            return Mono.error(new RuntimeException(AuthErrorMessages.INVALID_TOKEN_ERROR));
        }
        
        exchange.getAttributes().put(AuthConstants.TOKEN_ATTRIBUTE, token);
        return chain.filter(exchange);
    }
}
