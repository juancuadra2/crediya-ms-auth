package co.com.jcuadrado.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.Test;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.user.User;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserExistenceValidatorTest {

    @Test
    void testEmailExists() {
        User validUser = User.builder().email("test@example.com").build();
        User existingUser = User.builder().email("test@example.com").build();

        Mono<User> result = UserExistenceValidator.validate(validUser, existingUser);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.EMAIL_ALREADY_EXISTS, ex.getMessage());
                    assertEquals(ErrorCode.CONFLICT, ex.getCode());
                })
                .verify();
    }

    @Test
    void testEmailDoesNotExist() {
        User validUser = User.builder()
                .email("new@example.com")
                .documentNumber("0000000000")
                .build();
        User existingUser = User.builder()
                .email("test@example.com")
                .documentNumber("0000000001")
                .build();

        Mono<User> result = UserExistenceValidator.validate(validUser, existingUser);

        StepVerifier.create(result)
                .expectNext(validUser)
                .verifyComplete();
    }

    @Test
    void testDocumentNumberExists() {
        User validUser = User.builder()
                .documentNumber("123456789")
                .email("test@example.com")
                .build();
        User existingUser = User.builder()
                .documentNumber("123456789")
                .email("test1@example.com")
                .build();

        Mono<User> result = UserExistenceValidator.validate(validUser, existingUser);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.DOCUMENT_NUMBER_ALREADY_EXISTS, ex.getMessage());
                    assertEquals(ErrorCode.CONFLICT, ex.getCode());
                })
                .verify();
    }

    @Test
    void testDocumentNumberDoesNotExist() {
        User validUser = User.builder()
                .documentNumber("987654321")
                .email("new@example.com")
                .build();
        User existingUser = User.builder()
                .documentNumber("123456789")
                .email("test@example.com")
                .build();

        Mono<User> result = UserExistenceValidator.validate(validUser, existingUser);

        StepVerifier.create(result)
                .expectNext(validUser)
                .verifyComplete();
    }

    @Test
    void testUserDoesNotExist() {
        User validUser = User.builder().email("new@example.com").documentNumber("987654321").build();
        User existingUser = User.builder().email("test@example.com").documentNumber("123456789").build();

        Mono<User> result = UserExistenceValidator.validate(validUser, existingUser);

        StepVerifier.create(result)
                .expectNext(validUser)
                .verifyComplete();
    }
}