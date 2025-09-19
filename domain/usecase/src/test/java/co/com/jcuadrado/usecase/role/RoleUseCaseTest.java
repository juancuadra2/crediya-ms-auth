package co.com.jcuadrado.usecase.role;

import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.role.Role;
import co.com.jcuadrado.model.role.gateways.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleUseCase useCase;

    @Test
    void successfulGetRoleById() {
        Role role = Role.builder()
                .id("1")
                .name("ADMIN")
                .build();

        when(repository.getRoleById("1")).thenReturn(Mono.just(role));

        Mono<Role> result = useCase.getRoleById(role.getId());

        StepVerifier.create(result)
                .expectNext(role)
                .verifyComplete();

        verify(repository).getRoleById("1");
    }

    @Test
    void failedGetRoleById() {
        when(repository.getRoleById("1")).thenReturn(Mono.empty());

        Mono<Role> result = useCase.getRoleById("1");

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException exception = (BusinessException) error;
                    assertNotNull(exception);
                    assertEquals(ErrorMessage.ROLE_DOES_NOT_EXIST, exception.getMessage());
                })
                .verify();

        verify(repository).getRoleById("1");
    }

}
