package co.com.jcuadrado.handler;

import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.auth.LoginRequest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


class LoginPayloadValidatorTest {

    @Test
    void successValidate() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@mail.com")
                .password("12345678")
                .build();

        Mono<LoginRequest> result = LoginPayloadValidator.validate(loginRequest);

        StepVerifier.create(result)
                .expectNext(loginRequest)
                .verifyComplete();
    }

    @Test
    void failValidate_NullRequest() {
        Mono<LoginRequest> result = LoginPayloadValidator.validate(null);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.INVALID_CREDENTIALS, ex.getMessage());
                })
                .verify();
    }

    @Test
    void failValidate_EmailIsNullOrEmpty() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("")
                .password("12345678")
                .build();

        Mono<LoginRequest> result = LoginPayloadValidator.validate(loginRequest);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.EMAIL_REQUIRED, ex.getMessage());
                })
                .verify();
    }

    @Test
    void failValidate_PasswordIsNullOrEmpty() {
        LoginRequest loginRequest = LoginRequest.builder()
                .password("")
                .build();

        Mono<LoginRequest> result = LoginPayloadValidator.validate(loginRequest);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.PASSWORD_REQUIRED, ex.getMessage());
                })
                .verify();
    }

    @Test
    void failValidate_PasswordMinLength() {
        LoginRequest loginRequest = LoginRequest.builder()
                .password("123")
                .build();

        Mono<LoginRequest> result = LoginPayloadValidator.validate(loginRequest);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException ex = assertInstanceOf(BusinessException.class, error);
                    assertEquals(ErrorMessage.PASSWORD_MIN_LENGTH, ex.getMessage());
                })
                .verify();
    }
}
