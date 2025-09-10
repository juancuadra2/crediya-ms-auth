package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private FindUserUseCase useCase;

    @Test
    void successfulGetAllUsers() {
        Flux<User> users = Flux.just(
                User.builder().email("test1@example.com").documentNumber("123456789").build(),
                User.builder().email("test2@example.com").documentNumber("987654321").build());

        when(repository.getAllUsers()).thenReturn(users);

        Flux<User> result = useCase.getAllUsers();

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void failedGetAllUsers() {
        when(repository.getAllUsers()).thenReturn(Flux.error(new RuntimeException("Database error")));

        Flux<User> result = useCase.getAllUsers();

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> assertEquals("Error obteniendo los usuarios", error.getMessage()))
                .verify();
    }

    @Test
    void successfulGetUserByDocumentNumber() {
        String documentNumber = "123456789";
        User user = User.builder()
                .documentNumber(documentNumber)
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .build();

        when(repository.getUserByDocumentNumber(documentNumber)).thenReturn(Mono.just(user));

        Mono<User> result = useCase.getUserByDocumentNumber(documentNumber);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
        Mockito.verify(repository).getUserByDocumentNumber(documentNumber);
    }

    @Test
    void failedGetUserByDocumentNumberNotFound() {
        String documentNumber = "123456789";
        when(repository.getUserByDocumentNumber(documentNumber)).thenReturn(Mono.empty());
        Mono<User> result = useCase.getUserByDocumentNumber(documentNumber);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.USER_NOT_FOUND, ex.getMessage());
                    assertEquals(ErrorCode.NOT_FOUND, ex.getCode());
                })
                .verify();
        Mockito.verify(repository).getUserByDocumentNumber(documentNumber);
        Mockito.verifyNoMoreInteractions(repository);
    }

}
