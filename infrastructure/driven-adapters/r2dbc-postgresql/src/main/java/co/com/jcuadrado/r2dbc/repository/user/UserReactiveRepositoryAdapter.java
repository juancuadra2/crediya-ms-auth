package co.com.jcuadrado.r2dbc.repository.user;

import java.util.UUID;

import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import co.com.jcuadrado.r2dbc.constant.ErrorCode;
import co.com.jcuadrado.r2dbc.constant.UserConstants;
import co.com.jcuadrado.r2dbc.constant.RepositoryConstants;
import co.com.jcuadrado.r2dbc.entity.UserEntity;
import co.com.jcuadrado.r2dbc.exception.GeneralException;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Log4j2
public class UserReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<User, UserEntity, UUID, UserReactiveRepository> implements UserRepository {

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
                .doOnSuccess(u -> log.info(RepositoryConstants.LOG_USER_SAVED_SUCCESS, u.getEmail()))
                .doOnError(e -> log.error(RepositoryConstants.LOG_ERROR_SAVING_USER, e.getMessage()))
                .onErrorMap(ex -> new GeneralException(UserConstants.ERROR_SAVING_USER + ex.getMessage(), ErrorCode.INTERNAL_ERROR));
    }

    @Override
    public Flux<User> getAllUsers() {
        return super.findAll()
                .doOnError(e -> log.error(RepositoryConstants.LOG_ERROR_RETRIEVING_ALL_USERS, e.getMessage()));
    }

    @Override
    public Mono<User> getUserByEmailOrDocumentNumber(String email, String documentNumber) {
        return super.repository.findFirstByEmailOrDocumentNumber(email, documentNumber).map(this::toEntity)
                .doOnSuccess(user -> log.info(RepositoryConstants.LOG_USER_RETRIEVED_BY_EMAIL_OR_DOCUMENT_SUCCESS, documentNumber, email))
                .doOnError(e -> log.error(RepositoryConstants.LOG_ERROR_RETRIEVING_USER_BY_EMAIL_OR_DOCUMENT, e.getMessage()));
    }

    @Override
    public Mono<User> getUserByDocumentNumber(String documentNumber) {
        return super.repository.findByDocumentNumber(documentNumber).map(this::toEntity)
                .doOnSuccess(user -> log.info(RepositoryConstants.LOG_USER_RETRIEVED_BY_DOCUMENT_SUCCESS, documentNumber))
                .doOnError(e -> log.error(RepositoryConstants.LOG_ERROR_RETRIEVING_USER_BY_DOCUMENT, e.getMessage()));
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return super.repository.findByEmail(email).map(this::toEntity)
                .doOnSuccess(user -> log.info(RepositoryConstants.LOG_USER_RETRIEVED_BY_EMAIL_SUCCESS, email))
                .doOnError(e -> log.error(RepositoryConstants.LOG_ERROR_RETRIEVING_USER_BY_EMAIL, e.getMessage()));
    }
}
