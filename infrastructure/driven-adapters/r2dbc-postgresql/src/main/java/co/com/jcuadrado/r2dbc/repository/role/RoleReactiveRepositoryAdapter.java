package co.com.jcuadrado.r2dbc.repository.role;

import co.com.jcuadrado.model.role.Role;
import co.com.jcuadrado.model.role.gateways.RoleRepository;
import co.com.jcuadrado.r2dbc.entity.RoleEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RoleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        String,
        RoleReactiveRepository
        > implements RoleRepository {

    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Mono<Role> saveRole(Role role) {
        return super.save(role);
    }

    @Override
    public Flux<Role> getAllRoles() {
        return super.findAll();
    }

    @Override
    public Mono<Role> getRoleById(String id) {
        return super.findById(id);
    }

    @Override
    public Mono<Role> updateRole(Role role) {
        return super.save(role);
    }

    @Override
    public Mono<Void> deleteRole(String id) {
        return repository.deleteById(id);
    }
}
