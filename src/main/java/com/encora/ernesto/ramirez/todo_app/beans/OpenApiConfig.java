package com.encora.ernesto.ramirez.todo_app.beans;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation.
 * Sets up metadata for the API using values from application properties.
 */
@Configuration
public class OpenApiConfig {

    @Value("${app.api.title}")
    private String title;

    @Value("${app.api.version}")
    private String version;

    @Value("${app.api.description}")
    private String description;

    /**
     * Bean to configure OpenAPI metadata.
     * 
     * @return OpenAPI instance with API information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description));
    }
}
