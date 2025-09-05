package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.UserEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class UserRouterRest {

    @Bean
    RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
        return RouterFunctions.route(POST(UserEndpoints.USERS_API_PATH), handler::listenSaveUser)
                .andRoute(GET(UserEndpoints.USERS_API_PATH), handler::listenGetAllUsers)
                .andRoute(GET(UserEndpoints.USERS_API_PATH + UserEndpoints.DOCUMENT_NUMBER_ENDPOINT), handler::listenGetUserByDocumentNumber);
    }
}
