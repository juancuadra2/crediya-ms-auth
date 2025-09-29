package co.com.jcuadrado.model.auth.gateways;

import reactor.core.publisher.Mono;

public interface PasswordEncoderGateway {
    Mono<String> encode(String password);
    Mono<Boolean> matches(String rawPassword, String encodedPassword);
}
