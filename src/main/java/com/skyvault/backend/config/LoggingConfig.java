package com.skyvault.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Configuration
public class LoggingConfig {

    @Bean
    public OncePerRequestFilter correlationIdFilter() {
        return new OncePerRequestFilter() {

            private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
            private static final String CORRELATION_ID_KEY = "correlationId";

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {

                // Obtener correlation ID del header o generar uno nuevo
                String correlationId = request.getHeader(CORRELATION_ID_HEADER);
                if (correlationId == null || correlationId.trim().isEmpty()) {
                    correlationId = UUID.randomUUID().toString();
                }

                // Agregar al MDC para logging
                MDC.put(CORRELATION_ID_KEY, correlationId);

                // Agregar al response header
                response.setHeader(CORRELATION_ID_HEADER, correlationId);

                try {
                    filterChain.doFilter(request, response);
                } finally {
                    // Limpiar MDC
                    MDC.remove(CORRELATION_ID_KEY);
                }
            }
        };
    }
}
