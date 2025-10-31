package com.skyvault.backend.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Map;

@Configuration
public class ActuatorConfig {

    @Bean
    public InfoContributor customInfoContributor() {
        return builder -> builder
                .withDetail("app", Map.of(
                        "name", "SkyVault Backend API",
                        "description", "Aircraft comparison platform backend",
                        "version", getClass().getPackage().getImplementationVersion() != null ?
                                getClass().getPackage().getImplementationVersion() : "development",
                        "startTime", Instant.now().toString(),
                        "features", Map.of(
                                "manufacturers", "Aircraft manufacturers catalog",
                                "families", "Aircraft families with filtering",
                                "aircraft", "Aircraft models with advanced search",
                                "comparison", "Side-by-side aircraft comparison",
                                "specifications", "Detailed technical specifications",
                                "images", "Aircraft image galleries",
                                "search", "Intelligent search with suggestions"
                        )
                ))
                .withDetail("api", Map.of(
                        "version", "v1",
                        "baseUrl", "/api/v1",
                        "documentation", "/swagger-ui.html",
                        "openapi", "/v3/api-docs"
                ))
                .withDetail("database", Map.of(
                        "type", "PostgreSQL",
                        "flyway", "Enabled",
                        "jpa", "Hibernate"
                ))
                .withDetail("caching", Map.of(
                        "enabled", true,
                        "provider", "ConcurrentMap", // o Redis en producción
                        "caches", "manufacturers, families, aircraft-detail, specifications, images, search-suggestions"
                ));
    }
}
