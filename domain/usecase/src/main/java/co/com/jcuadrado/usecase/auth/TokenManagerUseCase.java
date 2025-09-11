package co.com.jcuadrado.usecase.auth;

import co.com.jcuadrado.constants.ErrorCode;
import co.com.jcuadrado.constants.ErrorMessage;
import co.com.jcuadrado.exceptions.BusinessException;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record TokenManagerUseCase(AuthTokenGateway authTokenGateway) {

    public Mono<Boolean> validateToken(String token) {
        validateInput(token);
        return authTokenGateway.validateToken(token);
    }

    public Mono<String> getSubject(String token) {
        validateInput(token);
        return authTokenGateway.getSubject(token);
    }

    public Flux<String> getRoles(String token) {
        validateInput(token);
        return authTokenGateway.getRoles(token);
    }

    private void validateInput(String token){
        if (token == null) throw new BusinessException(ErrorMessage.INVALID_TOKEN, ErrorCode.BAD_REQUEST);
    }

}
