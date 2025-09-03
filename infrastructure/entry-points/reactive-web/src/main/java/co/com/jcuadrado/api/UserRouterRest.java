package co.com.jcuadrado.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.jcuadrado.api.constant.UserConstants;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class UserRouterRest {

    @Bean
    RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
        return RouterFunctions.route(POST(UserConstants.USERS_API_PATH), handler::listenSaveUser)
                .andRoute(GET(UserConstants.USERS_API_PATH), handler::listenGetAllUsers)
                .andRoute(GET(UserConstants.USERS_API_PATH + UserConstants.DOCUMENT_NUMBER_ENDPOINT), handler::listenGetUserByDocumentNumber);
    }
}
