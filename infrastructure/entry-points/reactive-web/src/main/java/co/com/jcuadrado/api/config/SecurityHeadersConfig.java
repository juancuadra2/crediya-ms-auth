package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.constant.http.SecurityHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class SecurityHeadersConfig implements WebFilter {

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.set(SecurityHeaders.CSP_NAME, SecurityHeaders.CSP_VALUE);
        headers.set(SecurityHeaders.STS_NAME, SecurityHeaders.STS_VALUE);
        headers.set(SecurityHeaders.X_CONTENT_TYPE_OPTIONS_NAME, SecurityHeaders.X_CONTENT_TYPE_OPTIONS_VALUE);
        headers.set(SecurityHeaders.SERVER_NAME, SecurityHeaders.SERVER_VALUE);
        headers.set(SecurityHeaders.CACHE_CONTROL_NAME, SecurityHeaders.CACHE_CONTROL_VALUE);
        headers.set(SecurityHeaders.PRAGMA_NAME, SecurityHeaders.PRAGMA_VALUE);
        headers.set(SecurityHeaders.REFERRER_POLICY_NAME, SecurityHeaders.REFERRER_POLICY_VALUE);
        return chain.filter(exchange);
    }
}
