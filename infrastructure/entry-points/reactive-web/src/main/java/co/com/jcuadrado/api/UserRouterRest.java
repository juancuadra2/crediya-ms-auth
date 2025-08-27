package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.UserConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
        return route(POST(UserConstants.USERS_API_PATH), handler::listenSaveUser)
                .andRoute(GET(UserConstants.USERS_API_PATH), handler::listenGetAllUsers);
    }

}
