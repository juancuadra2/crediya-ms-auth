package co.com.jcuadrado.usecase.role;

import co.com.jcuadrado.model.role.Role;
import co.com.jcuadrado.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RoleUseCase {

    private final RoleRepository roleRepository;

    public Mono<Role> saveRole(Role role) {
        return roleRepository.saveRole(role);
    }

    public Flux<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    public Mono<Role> getRoleById(String id) {
        return roleRepository.getRoleById(id);
    }

    public Mono<Role> updateRole(Role role) {
        return roleRepository.updateRole(role);
    }

    public Mono<Void> deleteRole(String id) {
        return roleRepository.deleteRole(id);
    }
}
