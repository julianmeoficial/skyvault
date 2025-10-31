package com.skyvault.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${skyvault.api.version:1.0.0}")
    private String apiVersion;

    @Value("${skyvault.api.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${spring.application.name:SkyVault Backend}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server().url(baseUrl).description("SkyVault API Server"),
                        new Server().url("https://api.skyvault.com").description("Production Server")
                ))
                .tags(List.of(
                        new Tag().name("ManufacturersPage").description("Aircraft manufacturers operations"),
                        new Tag().name("Families").description("Aircraft families operations"),
                        new Tag().name("Aircraft").description("Aircraft models operations"),
                        new Tag().name("Compare").description("Aircraft comparison operations"),
                        new Tag().name("Search").description("Search and suggestions operations"),
                        new Tag().name("Health").description("Health check operations")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("SkyVault API")
                .description(
                        "**SkyVault** is a comprehensive aircraft comparison platform that provides detailed information about 36+ commercial aircraft models from Airbus and Boeing.\n\n" +

                                "## Features\n" +
                                "- **Aircraft Catalog**: Browse and filter aircraft by manufacturer, family, type, and specifications\n" +
                                "- **Advanced Filtering**: Filter by passenger capacity, range, production state, and more\n" +
                                "- **Detailed Specifications**: Access comprehensive technical data for each aircraft\n" +
                                "- **Side-by-Side Comparison**: Compare multiple aircraft models simultaneously\n" +
                                "- **Search & Suggestions**: Intelligent search with autocompletions\n" +
                                "- **Rich Media**: High-quality images and visual galleries\n\n" +

                                "## API Design Principles\n" +
                                "- **RESTful Architecture**: Following REST conventions with proper HTTP status codes\n" +
                                "- **Problem Details**: Error responses follow RFC 7807 standard\n" +
                                "- **Pagination**: Efficient pagination with configurable page sizes\n" +
                                "- **Caching**: ETags and Last-Modified headers for optimal performance\n" +
                                "- **CORS Support**: Cross-origin requests enabled for web applications\n\n" +

                                "## Response Formats\n" +
                                "All responses are in JSON format with consistent structure:\n" +
                                "- **Success**: Data with pagination metadata where applicable\n" +
                                "- **Errors**: Problem Details (RFC 7807) format with correlation IDs\n" +
                                "- **Empty Results**: Empty arrays/objects, never null responses\n\n" +

                                "## Authentication\n" +
                                "Current version provides public read-only access. Authentication will be added for premium features.\n\n" +

                                "## Rate Limiting\n" +
                                "API requests are rate-limited to ensure fair usage. Limits vary by endpoint complexity."
                )
                .version(apiVersion)
                .contact(new Contact()
                        .name("SkyVault Development Team")
                        .email("dev@skyvault.com")
                        .url("https://skyvault.com/contact")
                )
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                );
    }
}
