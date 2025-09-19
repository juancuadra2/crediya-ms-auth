package co.com.jcuadrado.model.role.gateways;

import co.com.jcuadrado.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> getRoleById(String id);
}