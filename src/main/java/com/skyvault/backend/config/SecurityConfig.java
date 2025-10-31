package com.skyvault.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private Environment environment;

    @Value("${skyvault.security.actuator.public-endpoints:health,info}")
    private String[] publicActuatorEndpoints;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilitar CSRF para APIs REST
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar CORS
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                // Sin sesiones - API stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Autorización de endpoints
                .authorizeHttpRequests(auth -> {
                    // APIs públicas principales - SkyVault es una plataforma de consulta pública
                    auth.requestMatchers("/api/v1/aircraft/**").permitAll()
                            .requestMatchers("/api/v1/manufacturers/**").permitAll()
                            .requestMatchers("/api/v1/families/**").permitAll()
                            .requestMatchers("/api/v1/catalog/**").permitAll()
                            .requestMatchers("/api/v1/search/**").permitAll()
                            .requestMatchers("/api/v1/statistics/public/**").permitAll();

                    // Health y documentación siempre públicos
                    auth.requestMatchers("/actuator/health").permitAll()
                            .requestMatchers("/actuator/info").permitAll();

                    // Swagger público en desarrollo, restringido en producción
                    if (isDevelopmentProfile()) {
                        auth.requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/swagger-ui.html").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-resources/**").permitAll()
                                .requestMatchers("/webjars/**").permitAll();
                    } else {
                        auth.requestMatchers("/swagger-ui/**").denyAll()
                                .requestMatchers("/v3/api-docs/**").denyAll();
                    }

                    // Actuator sensibles restringidos en producción
                    if (isDevelopmentProfile()) {
                        auth.requestMatchers("/actuator/**").permitAll();
                    } else {
                        // En producción solo endpoints específicos públicos
                        auth.requestMatchers("/actuator/metrics").denyAll()
                                .requestMatchers("/actuator/env").denyAll()
                                .requestMatchers("/actuator/configprops").denyAll()
                                .requestMatchers("/actuator/**").denyAll();
                    }

                    // Futuras APIs admin/user (para cuando las implementes)
                    auth.requestMatchers("/api/v1/admin/**").denyAll() // Bloqueadas por ahora
                            .requestMatchers("/api/v1/user/**").denyAll()   // Bloqueadas por ahora

                            // Todo lo demás requiere autenticación (preparado para futuro)
                            .anyRequest().authenticated();
                })

                // Headers de seguridad mejorados
                .headers(headers -> headers
                        .contentTypeOptions(contentType -> {})

                        // HSTS más estricto en producción
                        .httpStrictTransportSecurity(hstsConfig -> hstsConfig
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                                .preload(isProductionProfile())
                        )

                        .frameOptions(frameOptions -> frameOptions.deny())
                        .addHeaderWriter(new XXssProtectionHeaderWriter())
                        .addHeaderWriter(new ReferrerPolicyHeaderWriter(
                                ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))

                        // CSP ajustado para SkyVault
                        .contentSecurityPolicy(csp -> csp.policyDirectives(
                                "default-src 'self'; " +
                                        "script-src 'self' " + (isDevelopmentProfile() ? "'unsafe-inline' 'unsafe-eval' " : "") + "; " +
                                        "style-src 'self' 'unsafe-inline'; " +
                                        "img-src 'self' data: https: blob:; " +
                                        "font-src 'self' data: https:; " +
                                        "connect-src 'self' https:; " +
                                        "media-src 'self' data:; " +
                                        "object-src 'none'; " +
                                        "base-uri 'self'"
                        ))
                )

                .build();
    }

    /**
     * Verificar si estamos en perfil de desarrollo
     */
    private boolean isDevelopmentProfile() {
        return environment.acceptsProfiles("dev") ||
                environment.acceptsProfiles("local") ||
                !environment.acceptsProfiles("prod");
    }

    /**
     * Verificar si estamos en perfil de producción
     */
    private boolean isProductionProfile() {
        return environment.acceptsProfiles("prod");
    }
}
