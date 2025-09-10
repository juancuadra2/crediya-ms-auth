package co.com.jcuadrado.usecase.user;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SaveUserUseCaseTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private SaveUserUseCase useCase;

    @Test
    void successfulSaveUser() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(BigDecimal.valueOf(10000000L))
                .build();
        when(repository.getUserByEmailOrDocumentNumber(user.getEmail(), user.getDocumentNumber()))
                .thenReturn(Mono.empty());
        when(repository.saveUser(user)).thenReturn(Mono.just(user));

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        Mockito.verify(repository).saveUser(user);
    }

    @Test
    void failedSaveUserDocumentNumberIsNullOrEmpty() {
        User user = User.builder()
                .documentNumber(null)
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(BigDecimal.valueOf(10000000.00))
                .build();

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.DOCUMENT_NUMBER_REQUIRED, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserNameIsNullOrEmpty() {
        User user = User.builder()
                .documentNumber("123456789")
                .name(null)
                .lastName("Doe")
                .email("john.doe@example.com")
                .baseSalary(BigDecimal.valueOf(1000000.0))
                .build();

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.NAME_REQUIRED, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserLastNameIsNullOrEmpty() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("")
                .email("juan.cuadrado@mail.com")
                .baseSalary(BigDecimal.valueOf(10000000.00))
                .build();

        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.LAST_NAME_REQUIRED, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserEmailIsNullOrEmpty() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("")
                .baseSalary(BigDecimal.valueOf(10000000.00))
                .build();

        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.EMAIL_REQUIRED, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserBaseSalaryIsNullOrEmpty() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(null)
                .build();

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.BASE_SALARY_REQUIRED, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserBaseSalaryIsLessThanZero() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(BigDecimal.valueOf(-0.00))
                .build();
        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.BASE_SALARY_RANGE, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserBaseSalaryIsGreaterThanFifteenMillion() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(BigDecimal.valueOf(16000000.00))
                .build();

        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.BASE_SALARY_RANGE, ex.getMessage());
                    assertEquals(ErrorCode.BAD_REQUEST, ex.getCode());
                })
                .verify();
    }

    @Test
    void failedSaveUserEmailAlreadyExists() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(BigDecimal.valueOf(10000000.00))
                .build();

        when(repository.getUserByEmailOrDocumentNumber(user.getEmail(), user.getDocumentNumber()))
                .thenReturn(Mono.just(user));
        when(repository.saveUser(user)).thenReturn(Mono.just(user));

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.EMAIL_ALREADY_EXISTS, ex.getMessage());
                    assertEquals(ErrorCode.CONFLICT, ex.getCode());
                })
                .verify();
    }

}
