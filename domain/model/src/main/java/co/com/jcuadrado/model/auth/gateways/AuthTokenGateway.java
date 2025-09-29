package co.com.jcuadrado.model.auth.gateways;

import co.com.jcuadrado.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthTokenGateway {
    Mono<String> generateToken(User user);
    Mono<Boolean> validateToken(String token);
    Mono<String> getSubject(String token);
    Flux<String> getRoles(String token);
}
