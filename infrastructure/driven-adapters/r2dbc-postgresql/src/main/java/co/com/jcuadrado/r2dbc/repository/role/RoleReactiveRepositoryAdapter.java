package co.com.jcuadrado.r2dbc.repository.role;

import co.com.jcuadrado.model.role.Role;
import co.com.jcuadrado.model.role.gateways.RoleRepository;
import co.com.jcuadrado.r2dbc.constant.RepositoryConstants;
import co.com.jcuadrado.r2dbc.entity.RoleEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Log4j2
public class RoleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        UUID,
        RoleReactiveRepository
        > implements RoleRepository {

    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Mono<Role> getRoleById(String id) {
        UUID uuid = UUID.fromString(id);
        return super.findById(uuid)
                .doOnError(e -> log.error(RepositoryConstants.LOG_ERROR_RETRIEVING_ROLE_BY_ID, e.getMessage()));
    }
}
