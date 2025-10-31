package com.skyvault.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${skyvault.cors.allowed-origins:http://localhost:3000,http://localhost:5173}")
    private List<String> allowedOrigins;

    @Value("${skyvault.cors.allowed-methods:GET,POST,PUT,DELETE,PATCH,OPTIONS}")
    private List<String> allowedMethods;

    @Value("${skyvault.cors.allowed-headers:*}")
    private List<String> allowedHeaders;

    @Value("${skyvault.cors.allow-credentials:true}")
    private Boolean allowCredentials;

    @Value("${skyvault.cors.max-age:3600}")
    private Long maxAge;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Orígenes permitidos - configurables por profile
        configuration.setAllowedOriginPatterns(allowedOrigins);

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(allowedMethods);

        // Headers permitidos
        if (allowedHeaders.contains("*")) {
            configuration.addAllowedHeader("*");
        } else {
            configuration.setAllowedHeaders(allowedHeaders);
        }

        // Headers expuestos al cliente
        configuration.setExposedHeaders(Arrays.asList(
                "X-Total-Count",
                "X-Page-Number",
                "X-Page-Size",
                "X-Total-Pages",
                "X-Correlation-Id",
                "Cache-Control",
                "ETag",
                "Last-Modified"
        ));

        // Permitir cookies y headers de autenticación
        configuration.setAllowCredentials(allowCredentials);

        // Cache preflight por 1 hora
        configuration.setMaxAge(maxAge);

        // Aplicar a todos los endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/actuator/**", configuration);
        source.registerCorsConfiguration("/swagger-ui/**", configuration);
        source.registerCorsConfiguration("/v3/api-docs/**", configuration);

        return source;
    }
}
