package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.AuthEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouterRest {

    @Bean
    RouterFunction<ServerResponse> authRouterFunction(AuthHandler authHandler) {
        return route(POST(AuthEndpoints.AUTH_API_PATH + AuthEndpoints.LOGIN_ENDPOINT), authHandler::login)
                .andRoute(GET(AuthEndpoints.AUTH_API_PATH + AuthEndpoints.VALIDATE_TOKEN_ENDPOINT), authHandler::validateToken);
    }
}
