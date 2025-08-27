package co.com.jcuadrado.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoleRouterRest {
    @Bean
    public RouterFunction<ServerResponse> roleRouterFunction(RoleHandler handler) {
        return route(POST("/api/role"), handler::listenSaveRole)
                .andRoute(GET("/api/role"), handler::listenGetAllRoles);
    }
}
