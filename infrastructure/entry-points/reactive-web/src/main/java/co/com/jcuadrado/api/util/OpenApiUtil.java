package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.doc.ApiJsonExamples;
import co.com.jcuadrado.api.constant.doc.ApiResponseConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiSchemaConstants;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OpenApiUtil {

    public static void addSchemaToComponents(Components components, Class<?> dtoClass) {
        var schemas = ModelConverters.getInstance().read(dtoClass);
        if (components.getSchemas() == null) {
            components.schemas(schemas);
        } else {
            components.getSchemas().putAll(schemas);
        }
    }

    public static ApiResponse createInternalServerErrorResponse() {
        return createErrorResponse(
                ApiResponseConstants.INTERNAL_SERVER_ERROR_DESCRIPTION,
                ApiResponseConstants.INTERNAL_ERROR_NAME,
                ApiResponseConstants.INTERNAL_ERROR_SUMMARY,
                ApiJsonExamples.INTERNAL_SERVER_ERROR_RESPONSE_EXAMPLE);
    }

    public static ApiResponse createErrorResponse(String description, String exampleName, String exampleSummary,
                                            Object exampleValue) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType(OpenApiSchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(
                                        OpenApiConstants.COMPONENT_SCHEMA_ERROR_RESPONSE_DTO))
                                .addExamples(exampleName, createExample(exampleSummary,
                                        exampleValue))));
    }

    public static Example createExample(String summary, Object value) {
        return new Example()
                .summary(summary)
                .value(value);
    }

}
