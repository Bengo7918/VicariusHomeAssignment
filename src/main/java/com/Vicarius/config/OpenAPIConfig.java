package com.Vicarius.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customizeOpenApi() {
        Info info = new Info().title("Vicarius Home Assignment").description("project for vicarius");
        return new OpenAPI().info(info);
    }

    @Bean
    public OpenApiCustomiser customizeResponses() {
        return openApi -> openApi.getPaths().forEach(
                (s, pathItem) -> pathItem.readOperations().forEach(
                        operation ->
                                operation.getResponses().addApiResponse(
                                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                                        new ApiResponse().description("Server Encountered an Error")
                                )));
    }
}
