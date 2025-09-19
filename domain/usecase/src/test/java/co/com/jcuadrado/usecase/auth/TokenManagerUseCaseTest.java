package co.com.jcuadrado.usecase.auth;

import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenManagerUseCaseTest {

    @Mock
    private AuthTokenGateway authTokenGateway;

    @InjectMocks
    private TokenManagerUseCase tokenManagerUseCase;

    @Test
    void successfulValidateToken() {
        String token = "valid-token";

        when(authTokenGateway.validateToken(token)).thenReturn(Mono.just(true));

        Mono<Boolean> result = tokenManagerUseCase.validateToken(token);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(authTokenGateway).validateToken(token);
    }

    @Test
    void failedValidateToken() {
        String token = "invalid-token";

        when(authTokenGateway.validateToken(token)).thenReturn(Mono.just(false));

        Mono<Boolean> result = tokenManagerUseCase.validateToken(token);

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();

        verify(authTokenGateway).validateToken(token);
    }

    @Test
    void failedValidateTokenWithException() {
        Mono<Boolean> result = tokenManagerUseCase.validateToken(null);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException exception = (BusinessException) error;
                    assertNotNull(exception);
                    assertEquals(ErrorMessage.INVALID_TOKEN, exception.getMessage());
                })
                .verify();
    }

    @Test
    void successfulCreateToken() {
        String token = "valid-token";
        String subject = "subject@mail.com";

        when(authTokenGateway.getSubject(token)).thenReturn(Mono.just(subject));

        Mono<String> result = tokenManagerUseCase.getSubject(token);

        StepVerifier.create(result)
                .expectNext(subject)
                .verifyComplete();

        verify(authTokenGateway).getSubject(token);
    }

    @Test
    void successfulGetRoles() {
        String token = "valid-token";
        String role = "ADMIN";

        when(authTokenGateway.getRoles(token)).thenReturn(Flux.just(role));

        Flux<String> result = tokenManagerUseCase.getRoles(token);

        StepVerifier.create(result)
                .expectNext(role)
                .verifyComplete();

        verify(authTokenGateway).getRoles(token);
    }
}
