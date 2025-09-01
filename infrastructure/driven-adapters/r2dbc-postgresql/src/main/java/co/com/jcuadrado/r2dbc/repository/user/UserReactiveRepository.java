package co.com.jcuadrado.r2dbc.repository.user;

import co.com.jcuadrado.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, UUID>, ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<UserEntity> findFirstByEmailOrDocumentNumber(String email, String documentNumber);
    Mono<UserEntity> findByEmail(String email);
}
