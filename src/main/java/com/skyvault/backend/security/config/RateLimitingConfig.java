package com.skyvault.backend.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de rate limiting para APIs públicas de SkyVault
 * Previene abuso sin requerir autenticación
 */
@Configuration
@ConfigurationProperties(prefix = "skyvault.security.rate-limiting")
public class RateLimitingConfig {

    private boolean enabled = true;
    private int requestsPerMinute = 100;
    private int requestsPerHour = 1000;
    private int burstCapacity = 50;

    // Getters y Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRequestsPerMinute() {
        return requestsPerMinute;
    }

    public void setRequestsPerMinute(int requestsPerMinute) {
        this.requestsPerMinute = requestsPerMinute;
    }

    public int getRequestsPerHour() {
        return requestsPerHour;
    }

    public void setRequestsPerHour(int requestsPerHour) {
        this.requestsPerHour = requestsPerHour;
    }

    public int getBurstCapacity() {
        return burstCapacity;
    }

    public void setBurstCapacity(int burstCapacity) {
        this.burstCapacity = burstCapacity;
    }
}
