package co.com.jcuadrado.usecase.role;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.role.Role;
import co.com.jcuadrado.model.role.gateways.RoleRepository;
import reactor.core.publisher.Mono;

public record RoleUseCase(RoleRepository roleRepository) {

    public Mono<Role> getRoleById(String id) {
        return roleRepository.getRoleById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.ROLE_DOES_NOT_EXIST, ErrorCode.NOT_FOUND)));
    }

}
