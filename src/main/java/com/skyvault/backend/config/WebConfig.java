package com.skyvault.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Configuración de paginación por defecto
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

        // Configurar valores por defecto de paginación
        resolver.setFallbackPageable(PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.by("id")));
        resolver.setMaxPageSize(MAX_PAGE_SIZE);
        resolver.setOneIndexedParameters(false); // Páginas basadas en 0

        // Nombres de parámetros personalizados
        resolver.setPageParameterName("page");
        resolver.setSizeParameterName("size");
        // ELIMINADO: setSortParameterName (no existe en Spring Boot 3.2)

        argumentResolvers.add(resolver);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .simpleDateFormat("yyyy-MM-dd")
                .build();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Formatters personalizados para conversión de parámetros

        // Formatter para Sort.Direction
        registry.addConverter(String.class, Sort.Direction.class, source -> {
            try {
                return Sort.Direction.fromString(source);
            } catch (IllegalArgumentException e) {
                return Sort.Direction.ASC; // Default fallback
            }
        });

        // Formatter para Boolean (para parámetros como isActive)
        registry.addConverter(String.class, Boolean.class, source -> {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }
            return switch (source.toLowerCase().trim()) {
                case "true", "1", "yes", "on" -> true;
                case "false", "0", "no", "off" -> false;
                default -> null;
            };
        });
    }
}
