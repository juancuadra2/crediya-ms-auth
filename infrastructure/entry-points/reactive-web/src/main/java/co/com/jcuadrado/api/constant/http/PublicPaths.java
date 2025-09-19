package co.com.jcuadrado.api.constant.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PublicPaths {

    // Swagger
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String V3_API_DOCS = "/v3/api-docs";
    public static final String SWAGGER_RESOURCES = "/swagger-resources";
    public static final String WEBJARS = "/webjars";

    // Management
    public static final String ACTUATOR = "/actuator";
    public static final String H2_CONSOLE = "/h2";

    // Common patterns
    public static final String PATH_WILDCARD = "/**";

    // Wildcard variations
    public static final String SWAGGER_UI_WILDCARD = SWAGGER_UI + PATH_WILDCARD;
    public static final String V3_API_DOCS_WILDCARD = V3_API_DOCS + PATH_WILDCARD;
    public static final String SWAGGER_RESOURCES_WILDCARD = SWAGGER_RESOURCES + PATH_WILDCARD;
    public static final String WEBJARS_WILDCARD = WEBJARS + PATH_WILDCARD;
    public static final String ACTUATOR_WILDCARD = ACTUATOR + PATH_WILDCARD;
    public static final String H2_WILDCARD = H2_CONSOLE + PATH_WILDCARD;

    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";

}
