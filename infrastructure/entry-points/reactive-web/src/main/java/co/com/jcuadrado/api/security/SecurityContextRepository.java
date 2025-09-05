package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.constant.error.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getAttribute(AuthConstants.TOKEN_ATTRIBUTE);
        if (token == null) {
            return Mono.empty();
        }
        
        return jwtAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
                .map(authentication -> (SecurityContext) new SecurityContextImpl(authentication))
                .onErrorResume(error -> {
                    log.error(LogMessages.AUTHENTICATION_FAILED_LOG, error.getMessage());
                    return Mono.empty();
                });
    }
}
