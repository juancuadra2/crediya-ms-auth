package co.com.jcuadrado.model.role.gateways;

import co.com.jcuadrado.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> saveRole(Role role);
    Flux<Role> getAllRoles();
    Mono<Role> getRoleById(String id);
    Mono<Role>  updateRole(Role role);
    Mono<Void> deleteRole(String id);
}