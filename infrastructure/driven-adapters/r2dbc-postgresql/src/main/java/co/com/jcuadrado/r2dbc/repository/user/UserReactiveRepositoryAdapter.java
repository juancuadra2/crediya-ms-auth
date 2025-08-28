package co.com.jcuadrado.r2dbc.repository.user;

import java.util.UUID;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import co.com.jcuadrado.r2dbc.constant.ErrorCode;
import co.com.jcuadrado.r2dbc.constant.UserConstants;
import co.com.jcuadrado.r2dbc.entity.UserEntity;
import co.com.jcuadrado.r2dbc.exception.GeneralException;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        UUID,
        UserReactiveRepository
        > implements UserRepository {

    protected UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Transactional
    @Override
    public Mono<User> saveUser(User user) {
        UserEntity entity = toData(user);
        entity.setRole(UUID.fromString(user.getRole()));
        return repository.save(entity)
                .map(this::toEntity)
                .onErrorMap(ex -> new GeneralException(UserConstants.ERROR_SAVING_USER + ex.getMessage() , ErrorCode.INTERNAL_ERROR));
    }

    @Override
    public Flux<User> getAllUsers() {
        return super.findAll();
    }

    @Override
    public Mono<User> getUserByEmailOrDocumentNumber(String email, String documentNumber) {
        return super.repository.findByEmailOrDocumentNumber(email, documentNumber).map(this::toEntity);
    }
}
