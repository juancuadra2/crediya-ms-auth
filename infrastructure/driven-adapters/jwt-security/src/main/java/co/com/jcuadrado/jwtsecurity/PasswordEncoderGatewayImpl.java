package co.com.jcuadrado.jwtsecurity;

import co.com.jcuadrado.model.auth.gateways.PasswordEncoderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PasswordEncoderGatewayImpl implements PasswordEncoderGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<String> encode(String password) {
        return Mono.fromCallable(() -> passwordEncoder.encode(password));
    }

    @Override
    public Mono<Boolean> matches(String rawPassword, String encodedPassword) {
        return Mono.fromCallable(() -> passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
