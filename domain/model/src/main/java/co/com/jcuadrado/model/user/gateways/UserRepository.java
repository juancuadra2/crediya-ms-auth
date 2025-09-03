package co.com.jcuadrado.model.user.gateways;

import co.com.jcuadrado.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> saveUser(User user);
    Flux<User> getAllUsers();
    Mono<User> getUserByEmailOrDocumentNumber(String email, String documentNumber);
    Mono<User> getUserByDocumentNumber(String documentNumber);
}
