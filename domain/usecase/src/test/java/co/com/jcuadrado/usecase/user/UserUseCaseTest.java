package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.GeneralDomainException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class UserUseCaseTest {

    private UserRepository repository;
    private UserUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(UserRepository.class);
        useCase = new UserUseCase(repository);
    }

    @Test
    void successfulSaveUser() {
        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .lastName("Cuadrado")
                .email("juan.cuadrado@mail.com")
                .baseSalary(10000000.00)
                .build();
        when(repository.getUserByEmail(user.getEmail())).thenReturn(Mono.empty());
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
                .baseSalary(10000000.00)
                .build();

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
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
                .baseSalary(1000000.0)
                .build();

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
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
                .baseSalary(10000000.00)
                .build();

        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
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
                .baseSalary(10000000.00)
                .build();

        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
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
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
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
                .baseSalary(-0.00)
                .build();
        Mono<User> result = useCase.saveUser(user);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
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
                .baseSalary(10000000.00)
                .build();

        when(repository.getUserByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(repository.saveUser(user)).thenReturn(Mono.just(user));

        Mono<User> result = useCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(GeneralDomainException.class, error);
                    GeneralDomainException ex = (GeneralDomainException) error;
                    assertEquals(ErrorMessage.EMAIL_ALREADY_EXISTS, ex.getMessage());
                    assertEquals(ErrorCode.CONFLICT, ex.getCode());
                })
                .verify();
    }



}
