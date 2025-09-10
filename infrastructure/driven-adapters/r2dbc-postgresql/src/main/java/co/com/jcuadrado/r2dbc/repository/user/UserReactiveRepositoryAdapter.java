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
public class UserReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<User, UserEntity, UUID, UserReactiveRepository> implements UserRepository {

    protected UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Transactional
    @Override
    public Mono<User> saveUser(User user) {
        UserEntity entity = toData(user);
        
        // Convertir nombre de rol a UUID
        UUID roleUuid = convertRoleNameToUuid(user.getRole());
        entity.setRole(roleUuid);
        
        return repository.save(entity)
                .map(this::toEntity)
                .onErrorMap(ex -> new GeneralException(UserConstants.ERROR_SAVING_USER + ex.getMessage(),
                        ErrorCode.INTERNAL_ERROR));
    }
    
    private UUID convertRoleNameToUuid(String roleName) {
        // Mapeo temporal de nombres de roles a UUIDs
        // En una implementación completa, esto debería consultar la base de datos
        switch (roleName.toUpperCase()) {
            case "ADMIN":
                return UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
            case "ADVISER":
                return UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
            case "USER":
                return UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
            default:
                // Si no es un rol conocido, intentar convertirlo directamente 
                // (por si viene un UUID válido)
                try {
                    return UUID.fromString(roleName);
                } catch (IllegalArgumentException e) {
                    throw new GeneralException("Rol no válido: " + roleName, ErrorCode.BAD_REQUEST);
                }
        }
    }

    @Override
    public Flux<User> getAllUsers() {
        return super.findAll();
    }

    @Override
    public Mono<User> getUserByEmailOrDocumentNumber(String email, String documentNumber) {
        return super.repository.findFirstByEmailOrDocumentNumber(email, documentNumber).map(this::toEntity);
    }

    @Override
    public Mono<User> getUserByDocumentNumber(String documentNumber) {
        return super.repository.findByDocumentNumber(documentNumber).map(this::toEntity);
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return super.repository.findByEmail(email).map(this::toEntity);
    }
}
