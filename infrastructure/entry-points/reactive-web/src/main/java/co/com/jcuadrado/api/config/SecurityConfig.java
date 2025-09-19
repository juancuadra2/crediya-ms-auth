package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.security.CustomSecurityContextRepository;
import co.com.jcuadrado.api.constant.api.AuthEndpoints;
import co.com.jcuadrado.api.constant.http.PublicPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomSecurityContextRepository securityContextRepository;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(AuthEndpoints.AUTH_API_PATH + PublicPaths.PATH_WILDCARD).permitAll()
                        .pathMatchers(PublicPaths.H2_WILDCARD).permitAll()
                        .pathMatchers(PublicPaths.ACTUATOR_WILDCARD).permitAll()
                        .pathMatchers(PublicPaths.SWAGGER_UI_WILDCARD).permitAll()
                        .pathMatchers(PublicPaths.SWAGGER_UI_HTML).permitAll()
                        .pathMatchers(PublicPaths.V3_API_DOCS_WILDCARD).permitAll()
                        .pathMatchers(PublicPaths.SWAGGER_RESOURCES_WILDCARD).permitAll()
                        .pathMatchers(PublicPaths.WEBJARS_WILDCARD).permitAll()
                        .anyExchange().authenticated())
                .securityContextRepository(securityContextRepository)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
