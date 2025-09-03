package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.constant.SecurityHeaderConfigConstants;
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
        headers.set(SecurityHeaderConfigConstants.CSP_NAME, SecurityHeaderConfigConstants.CSP_VALUE);
        headers.set(SecurityHeaderConfigConstants.STS_NAME, SecurityHeaderConfigConstants.STS_VALUE);
        headers.set(SecurityHeaderConfigConstants.X_CONTENT_TYPE_OPTIONS_NAME, SecurityHeaderConfigConstants.X_CONTENT_TYPE_OPTIONS_VALUE);
        headers.set(SecurityHeaderConfigConstants.SERVER_NAME, SecurityHeaderConfigConstants.SERVER_VALUE);
        headers.set(SecurityHeaderConfigConstants.CACHE_CONTROL_NAME, SecurityHeaderConfigConstants.CACHE_CONTROL_VALUE);
        headers.set(SecurityHeaderConfigConstants.PRAGMA_NAME, SecurityHeaderConfigConstants.PRAGMA_VALUE);
        headers.set(SecurityHeaderConfigConstants.REFERRER_POLICY_NAME, SecurityHeaderConfigConstants.REFERRER_POLICY_VALUE);
        return chain.filter(exchange);
    }
}
