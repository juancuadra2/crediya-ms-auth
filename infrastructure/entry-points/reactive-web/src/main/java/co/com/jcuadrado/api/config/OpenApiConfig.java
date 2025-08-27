package co.com.jcuadrado.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Authentication API")
                        .version("1.0")
                        .description("API para gestión de usuarios en el sistema"));
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        String[] paths = { "/user/**" };
        return GroupedOpenApi.builder().group("users")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("User API").version("1.0")))
                .pathsToMatch(paths)
                .build();
    }
}
