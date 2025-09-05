package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.util.OpenApiUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.jcuadrado.api.constant.doc.ApiJsonExamples;
import co.com.jcuadrado.api.constant.doc.ApiResponseConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiOperationConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiSchemaConstants;
import co.com.jcuadrado.api.constant.api.UserEndpoints;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
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
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(createApiInfo())
                    .addTagsItem(createUsersTag())
                    .components(createComponents())
                    .path(UserEndpoints.USERS_API_PATH, createUsersPaths());
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
                    .addApiResponse(OpenApiSchemaConstants.STATUS_500, OpenApiUtil.createInternalServerErrorResponse());
        }

        private Operation createGetAllUsersOperation() {
            return new Operation()
                    .summary(OpenApiOperationConstants.GET_ALL_USERS_SUMMARY)
                    .description(OpenApiOperationConstants.GET_ALL_USERS_DESCRIPTION)
                    .operationId(OpenApiOperationConstants.GET_ALL_USERS_OPERATION_ID)
                    .addTagsItem(OpenApiOperationConstants.USUARIOS_TAG_NAME)
                    .responses(createGetAllUsersResponses());
        }

        private ApiResponses createGetAllUsersResponses() {
            return new ApiResponses()
                    .addApiResponse(OpenApiSchemaConstants.STATUS_200, createArrayResponse())
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
}
