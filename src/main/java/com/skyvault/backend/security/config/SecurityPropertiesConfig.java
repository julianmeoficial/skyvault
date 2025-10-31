package com.skyvault.backend.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties de configuración de seguridad para SkyVault
 */
@Configuration
@ConfigurationProperties(prefix = "skyvault.security")
public class SecurityPropertiesConfig {

    private final RateLimitingConfig rateLimiting = new RateLimitingConfig();
    private final ContentSecurityPolicyConfig contentSecurityPolicy = new ContentSecurityPolicyConfig();
    private String[] actuatorPublicEndpoints = {"health", "info"};

    public RateLimitingConfig getRateLimiting() {
        return rateLimiting;
    }

    public ContentSecurityPolicyConfig getContentSecurityPolicy() {
        return contentSecurityPolicy;
    }

    public String[] getActuatorPublicEndpoints() {
        return actuatorPublicEndpoints;
    }

    public void setActuatorPublicEndpoints(String[] actuatorPublicEndpoints) {
        this.actuatorPublicEndpoints = actuatorPublicEndpoints;
    }

    /**
     * Configuración de Content Security Policy
     */
    public static class ContentSecurityPolicyConfig {
        private boolean reportOnly = false;

        public boolean isReportOnly() {
            return reportOnly;
        }

        public void setReportOnly(boolean reportOnly) {
            this.reportOnly = reportOnly;
        }
    }
}
