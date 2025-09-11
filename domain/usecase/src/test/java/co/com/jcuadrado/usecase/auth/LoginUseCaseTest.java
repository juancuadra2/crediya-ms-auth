package co.com.jcuadrado.usecase.auth;

import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.auth.AuthResponse;
import co.com.jcuadrado.model.auth.LoginRequest;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.auth.gateways.PasswordEncoderGateway;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import co.com.jcuadrado.usecase.role.RoleUseCase;
import co.com.jcuadrado.model.role.Role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test for LoginUseCase")
@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderGateway passwordEncoderGateway;

    @Mock
    private RoleUseCase roleUseCase;

    @Mock
    private AuthTokenGateway authTokenGateway;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    @DisplayName("Should login successfully with valid credentials")
    void shouldLoginSuccessfullyWithValidCredentials() {
        // Given
        String email = "user@test.com";
        String password = "myPassword123";
        String hashedPassword = "hashedMyPassword123";
        String roleId = "USER";
        String roleName = "Regular User";
        String token = "jwt-token-abc123";

        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .email(email)
                .password(hashedPassword)
                .role(roleId)
                .build();

        Role role = Role.builder()
                .id(roleId)
                .name(roleName)
                .build();

        when(userRepository.getUserByEmail(email)).thenReturn(Mono.just(user));
        when(passwordEncoderGateway.matches(password, hashedPassword)).thenReturn(Mono.just(true));
        when(roleUseCase.getRoleById(roleId)).thenReturn(Mono.just(role));
        when(authTokenGateway.generateToken(any(User.class))).thenReturn(Mono.just(token));

        Mono<AuthResponse> result = loginUseCase.login(loginRequest);

        StepVerifier.create(result)
                .assertNext(authResponse -> {
                    assertNotNull(authResponse);
                    assertEquals(token, authResponse.getToken());
                    assertEquals(email, authResponse.getEmail());
                    assertEquals(roleName, authResponse.getRole());
                })
                .verifyComplete();

        verify(userRepository, times(1)).getUserByEmail(email);
        verify(passwordEncoderGateway, times(1)).matches(password, hashedPassword);
        verify(roleUseCase, times(1)).getRoleById(roleId);
        verify(authTokenGateway, times(1)).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should fail login with invalid password")
    void shouldFailLoginWithInvalidPassword() {
        // Given
        String email = "user@test.com";
        String password = "myPassword123";
        String hashedPassword = "hashedMyPassword123";
        String roleId = "USER";

        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        User user = User.builder()
                .documentNumber("123456789")
                .name("Juan")
                .email(email)
                .password(hashedPassword)
                .role(roleId)
                .build();

        when(userRepository.getUserByEmail(email)).thenReturn(Mono.just(user));
        when(passwordEncoderGateway.matches(password, hashedPassword)).thenReturn(Mono.just(false));

        Mono<AuthResponse> result = loginUseCase.login(loginRequest);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException exception = (BusinessException) error;
                    assertEquals(ErrorMessage.INVALID_CREDENTIALS, exception.getMessage());
                })
                .verify();

        verify(userRepository, times(1)).getUserByEmail(email);
        verify(passwordEncoderGateway, times(1)).matches(password, hashedPassword);
    }

}