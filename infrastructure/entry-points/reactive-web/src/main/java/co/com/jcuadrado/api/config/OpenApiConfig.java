package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.util.OpenApiUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.jcuadrado.api.constant.doc.ApiJsonExamples;
import co.com.jcuadrado.api.constant.doc.ApiResponseConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiOperationConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiSchemaConstants;
import co.com.jcuadrado.api.constant.api.AuthEndpoints;
import co.com.jcuadrado.api.constant.api.UserEndpoints;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import co.com.jcuadrado.api.dto.auth.AuthResponseDTO;
import co.com.jcuadrado.api.dto.auth.LoginRequestDTO;
import co.com.jcuadrado.api.dto.user.CreateUserDTO;
import co.com.jcuadrado.api.dto.user.UpdateUserDTO;
import co.com.jcuadrado.api.dto.user.UserDTO;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(createApiInfo())
                    .addTagsItem(createUsersTag())
                    .addTagsItem(createAuthTag())
                    .components(createComponents())
                    .path(UserEndpoints.USERS_API_PATH, createUsersPaths())
                    .path(AuthEndpoints.AUTH_API_PATH + AuthEndpoints.LOGIN_ENDPOINT, createLoginPath())
                    .path(AuthEndpoints.AUTH_API_PATH + AuthEndpoints.VALIDATE_TOKEN_ENDPOINT, createValidateTokenPath());
        }

        private Info createApiInfo() {
            return new Info()
                    .title(OpenApiConstants.API_TITLE)
                    .version(OpenApiConstants.API_VERSION)
                    .description(OpenApiConstants.API_DESCRIPTION);
        }

        private Tag createUsersTag() {
            return new Tag()
                    .name(OpenApiOperationConstants.USUARIOS_TAG_NAME)
                    .description(OpenApiOperationConstants.USUARIOS_TAG_DESCRIPTION);
        }

        private Components createComponents() {
                Components components = new Components();

                OpenApiUtil.addSchemaToComponents(components, UserDTO.class);
                OpenApiUtil.addSchemaToComponents(components, CreateUserDTO.class);
                OpenApiUtil.addSchemaToComponents(components, UpdateUserDTO.class);
                OpenApiUtil.addSchemaToComponents(components, ErrorResponseDTO.class);
                OpenApiUtil.addSchemaToComponents(components, LoginRequestDTO.class);
                OpenApiUtil.addSchemaToComponents(components, AuthResponseDTO.class);

                // Agregar configuración de seguridad JWT
                components.addSecuritySchemes(ApiResponseConstants.JWT_SECURITY_SCHEME_NAME, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(ApiResponseConstants.JWT_SECURITY_SCHEME_TYPE)
                        .bearerFormat(ApiResponseConstants.JWT_SECURITY_BEARER_FORMAT)
                        .description(ApiResponseConstants.JWT_SECURITY_SCHEME_DESCRIPTION));

                return components;
        }


        private PathItem createUsersPaths() {
            return new PathItem()
                    .post(createSaveUserOperation())
                    .get(createGetAllUsersOperation());
        }

        private Operation createSaveUserOperation() {
            return new Operation()
                    .summary(OpenApiOperationConstants.CREATE_USER_SUMMARY)
                    .description(OpenApiOperationConstants.CREATE_USER_DESCRIPTION)
                    .operationId(OpenApiOperationConstants.CREATE_USER_OPERATION_ID)
                    .addTagsItem(OpenApiOperationConstants.USUARIOS_TAG_NAME)
                    .addSecurityItem(new SecurityRequirement().addList(ApiResponseConstants.JWT_SECURITY_SCHEME_NAME))
                    .requestBody(createSaveUserRequestBody())
                    .responses(createSaveUserResponses());
        }

        private RequestBody createSaveUserRequestBody() {
            return new RequestBody()
                    .description(OpenApiOperationConstants.CREATE_USER_REQUEST_DESCRIPTION)
                    .required(true)
                    .content(new Content().addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                            .schema(new Schema<CreateUserDTO>().$ref(OpenApiConstants.COMPONENT_SCHEMA_CREATE_USER_DTO))
                            .addExamples(ApiResponseConstants.USER_EXAMPLE_NAME, OpenApiUtil.createExample(ApiResponseConstants.USER_EXAMPLE_SUMMARY, ApiJsonExamples.CREATE_USER_REQUEST_EXAMPLE))));
        }

        private ApiResponses createSaveUserResponses() {
            return new ApiResponses()
                    .addApiResponse(OpenApiSchemaConstants.STATUS_201, createSuccessResponse())
                    .addApiResponse(OpenApiSchemaConstants.STATUS_400, OpenApiUtil.createErrorResponse(
                            ApiResponseConstants.VALIDATION_ERROR_DESCRIPTION,
                            ApiResponseConstants.VALIDATION_ERROR_NAME,
                            ApiResponseConstants.VALIDATION_ERROR_SUMMARY,
                            ApiJsonExamples.VALIDATION_ERROR_RESPONSE_EXAMPLE))
                    .addApiResponse(OpenApiSchemaConstants.STATUS_401, OpenApiUtil.createErrorResponse(
                            ApiResponseConstants.UNAUTHORIZED_ERROR_DESCRIPTION,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_NAME,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_SUMMARY,
                            ApiJsonExamples.UNAUTHORIZED_ERROR_RESPONSE_EXAMPLE))
                    .addApiResponse(OpenApiSchemaConstants.STATUS_500, OpenApiUtil.createInternalServerErrorResponse());
        }

        private Operation createGetAllUsersOperation() {
            return new Operation()
                    .summary(OpenApiOperationConstants.GET_ALL_USERS_SUMMARY)
                    .description(OpenApiOperationConstants.GET_ALL_USERS_DESCRIPTION)
                    .operationId(OpenApiOperationConstants.GET_ALL_USERS_OPERATION_ID)
                    .addTagsItem(OpenApiOperationConstants.USUARIOS_TAG_NAME)
                    .addSecurityItem(new SecurityRequirement().addList(ApiResponseConstants.JWT_SECURITY_SCHEME_NAME))
                    .responses(createGetAllUsersResponses());
        }

        private ApiResponses createGetAllUsersResponses() {
            return new ApiResponses()
                    .addApiResponse(OpenApiSchemaConstants.STATUS_200, createArrayResponse())
                    .addApiResponse(OpenApiSchemaConstants.STATUS_401, OpenApiUtil.createErrorResponse(
                            ApiResponseConstants.UNAUTHORIZED_ERROR_DESCRIPTION,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_NAME,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_SUMMARY,
                            ApiJsonExamples.UNAUTHORIZED_ERROR_RESPONSE_EXAMPLE))
                    .addApiResponse(OpenApiSchemaConstants.STATUS_500, OpenApiUtil.createInternalServerErrorResponse());
        }


        private ApiResponse createSuccessResponse() {
            return new ApiResponse()
                    .description(ApiResponseConstants.USER_CREATED_SUCCESS_DESCRIPTION)
                    .content(new Content().addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                            .schema(new Schema<>().$ref(OpenApiConstants.COMPONENT_SCHEMA_USER_DTO))
                            .addExamples(ApiResponseConstants.USER_CREATED_NAME, OpenApiUtil.createExample(ApiResponseConstants.USER_CREATED_SUMMARY, ApiJsonExamples.USER_CREATED_RESPONSE_EXAMPLE))));
        }

        private ApiResponse createArrayResponse() {
            return new ApiResponse()
                    .description(ApiResponseConstants.USERS_LIST_SUCCESS_DESCRIPTION)
                    .content(new Content()
                            .addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                                    .schema(new ArraySchema().items(new Schema<>().$ref(OpenApiConstants.COMPONENT_SCHEMA_USER_DTO)))
                                    .addExamples(ApiResponseConstants.USERS_LIST_NAME, OpenApiUtil
                                            .createExample(ApiResponseConstants.USERS_LIST_SUMMARY, ApiJsonExamples.USERS_LIST_RESPONSE_EXAMPLE))));
        }

        // Auth methods
        private Tag createAuthTag() {
            return new Tag()
                    .name(OpenApiOperationConstants.AUTH_TAG_NAME)
                    .description(OpenApiOperationConstants.AUTH_TAG_DESCRIPTION);
        }

        private PathItem createLoginPath() {
            return new PathItem()
                    .post(createLoginOperation());
        }

        private PathItem createValidateTokenPath() {
            return new PathItem()
                    .get(createValidateTokenOperation());
        }

        private Operation createLoginOperation() {
            return new Operation()
                    .summary(OpenApiOperationConstants.LOGIN_SUMMARY)
                    .description(OpenApiOperationConstants.LOGIN_DESCRIPTION)
                    .operationId(OpenApiOperationConstants.LOGIN_OPERATION_ID)
                    .addTagsItem(OpenApiOperationConstants.AUTH_TAG_NAME)
                    .requestBody(createLoginRequestBody())
                    .responses(createLoginResponses());
        }

        private Operation createValidateTokenOperation() {
            return new Operation()
                    .summary(OpenApiOperationConstants.VALIDATE_TOKEN_SUMMARY)
                    .description(OpenApiOperationConstants.VALIDATE_TOKEN_DESCRIPTION)
                    .operationId(OpenApiOperationConstants.VALIDATE_TOKEN_OPERATION_ID)
                    .addTagsItem(OpenApiOperationConstants.AUTH_TAG_NAME)
                    .responses(createValidateTokenResponses());
        }

        private RequestBody createLoginRequestBody() {
            return new RequestBody()
                    .description(OpenApiOperationConstants.LOGIN_REQUEST_DESCRIPTION)
                    .required(true)
                    .content(new Content().addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                            .schema(new Schema<LoginRequestDTO>().$ref(OpenApiConstants.COMPONENT_SCHEMA_LOGIN_REQUEST_DTO))
                            .addExamples(ApiResponseConstants.LOGIN_EXAMPLE_NAME, OpenApiUtil.createExample(ApiResponseConstants.LOGIN_EXAMPLE_SUMMARY, ApiJsonExamples.LOGIN_REQUEST_EXAMPLE))));
        }

        private ApiResponses createLoginResponses() {
            return new ApiResponses()
                    .addApiResponse(OpenApiSchemaConstants.STATUS_200, createLoginSuccessResponse())
                    .addApiResponse(OpenApiSchemaConstants.STATUS_400, OpenApiUtil.createErrorResponse(
                            ApiResponseConstants.VALIDATION_ERROR_DESCRIPTION,
                            ApiResponseConstants.VALIDATION_ERROR_NAME,
                            ApiResponseConstants.VALIDATION_ERROR_SUMMARY,
                            ApiJsonExamples.VALIDATION_ERROR_RESPONSE_EXAMPLE))
                    .addApiResponse(OpenApiSchemaConstants.STATUS_401, OpenApiUtil.createErrorResponse(
                            ApiResponseConstants.UNAUTHORIZED_ERROR_DESCRIPTION,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_NAME,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_SUMMARY,
                            ApiJsonExamples.UNAUTHORIZED_ERROR_RESPONSE_EXAMPLE))
                    .addApiResponse(OpenApiSchemaConstants.STATUS_500, OpenApiUtil.createInternalServerErrorResponse());
        }

        private ApiResponses createValidateTokenResponses() {
            return new ApiResponses()
                    .addApiResponse(OpenApiSchemaConstants.STATUS_200, createTokenValidationSuccessResponse())
                    .addApiResponse(OpenApiSchemaConstants.STATUS_401, OpenApiUtil.createErrorResponse(
                            ApiResponseConstants.UNAUTHORIZED_ERROR_DESCRIPTION,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_NAME,
                            ApiResponseConstants.UNAUTHORIZED_ERROR_SUMMARY,
                            ApiJsonExamples.UNAUTHORIZED_ERROR_RESPONSE_EXAMPLE))
                    .addApiResponse(OpenApiSchemaConstants.STATUS_500, OpenApiUtil.createInternalServerErrorResponse());
        }

        private ApiResponse createLoginSuccessResponse() {
            return new ApiResponse()
                    .description(ApiResponseConstants.LOGIN_SUCCESS_DESCRIPTION)
                    .content(new Content().addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                            .schema(new Schema<>().$ref(OpenApiConstants.COMPONENT_SCHEMA_AUTH_RESPONSE_DTO))
                            .addExamples(ApiResponseConstants.LOGIN_SUCCESS_NAME, OpenApiUtil.createExample(ApiResponseConstants.LOGIN_SUCCESS_SUMMARY, ApiJsonExamples.LOGIN_RESPONSE_EXAMPLE))));
        }

        private ApiResponse createTokenValidationSuccessResponse() {
            return new ApiResponse()
                    .description(ApiResponseConstants.TOKEN_VALIDATION_SUCCESS_DESCRIPTION)
                    .content(new Content().addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                            .schema(new Schema<>().$ref(OpenApiConstants.COMPONENT_SCHEMA_AUTH_RESPONSE_DTO))
                            .addExamples(ApiResponseConstants.TOKEN_VALIDATION_NAME, OpenApiUtil.createExample(ApiResponseConstants.TOKEN_VALIDATION_SUMMARY, ApiJsonExamples.VALIDATE_TOKEN_RESPONSE_EXAMPLE))));
        }
}
