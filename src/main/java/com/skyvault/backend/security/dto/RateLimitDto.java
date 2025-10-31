package com.skyvault.backend.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO para respuestas de rate limiting en SkyVault
 * Informa al cliente sobre límites de requests y estado actual
 */
@Schema(
        name = "RateLimit",
        description = "Information about rate limiting status and restrictions"
)
public class RateLimitDto {

    @Schema(description = "Current status of rate limiting", example = "ACTIVE")
    @NotNull
    private RateLimitStatus status;

    @Schema(description = "Client IP address", example = "192.168.1.100")
    private String clientIp;

    @Schema(description = "Requests remaining in current window", example = "45")
    @Positive
    private Integer requestsRemaining;

    @Schema(description = "Maximum requests allowed per window", example = "100")
    @Positive
    private Integer requestsLimit;

    @Schema(description = "Time window in seconds", example = "60")
    @Positive
    private Integer windowSeconds;

    @Schema(description = "When the current window resets", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime resetTime;

    @Schema(description = "Retry after seconds (when blocked)", example = "30")
    private Integer retryAfterSeconds;

    @Schema(description = "Human-readable message", example = "Rate limit reached. Please try again in 30 seconds.")
    private String message;

    @Schema(description = "Additional context information")
    private String context;

    @Schema(description = "Timestamp when this response was generated", example = "2024-01-15T10:25:30")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    // Constructores
    public RateLimitDto() {
        this.timestamp = LocalDateTime.now();
    }

    public RateLimitDto(RateLimitStatus status, String clientIp, Integer requestsRemaining,
                        Integer requestsLimit, Integer windowSeconds) {
        this();
        this.status = status;
        this.clientIp = clientIp;
        this.requestsRemaining = requestsRemaining;
        this.requestsLimit = requestsLimit;
        this.windowSeconds = windowSeconds;
    }

    // Factory methods
    public static RateLimitDto allowed(String clientIp, int remaining, int limit, int windowSeconds, LocalDateTime resetTime) {
        RateLimitDto dto = new RateLimitDto(RateLimitStatus.ACTIVE, clientIp, remaining, limit, windowSeconds);
        dto.setResetTime(resetTime);
        dto.setMessage(String.format("Rate limit active. %d requests remaining.", remaining));
        return dto;
    }

    public static RateLimitDto blocked(String clientIp, int limit, int windowSeconds, int retryAfterSeconds) {
        RateLimitDto dto = new RateLimitDto(RateLimitStatus.EXCEEDED, clientIp, 0, limit, windowSeconds);
        dto.setRetryAfterSeconds(retryAfterSeconds);
        dto.setResetTime(LocalDateTime.now().plusSeconds(retryAfterSeconds));
        dto.setMessage(String.format("Rate limit exceeded. Retry after %d seconds.", retryAfterSeconds));
        return dto;
    }

    public static RateLimitDto warning(String clientIp, int remaining, int limit, int windowSeconds, LocalDateTime resetTime) {
        RateLimitDto dto = new RateLimitDto(RateLimitStatus.WARNING, clientIp, remaining, limit, windowSeconds);
        dto.setResetTime(resetTime);
        dto.setMessage(String.format("Rate limit warning. Only %d requests remaining.", remaining));
        return dto;
    }

    public static RateLimitDto disabled(String clientIp) {
        RateLimitDto dto = new RateLimitDto(RateLimitStatus.DISABLED, clientIp, null, null, null);
        dto.setMessage("Rate limiting is disabled.");
        return dto;
    }

    // Getters y Setters
    public RateLimitStatus getStatus() {
        return status;
    }

    public void setStatus(RateLimitStatus status) {
        this.status = status;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getRequestsRemaining() {
        return requestsRemaining;
    }

    public void setRequestsRemaining(Integer requestsRemaining) {
        this.requestsRemaining = requestsRemaining;
    }

    public Integer getRequestsLimit() {
        return requestsLimit;
    }

    public void setRequestsLimit(Integer requestsLimit) {
        this.requestsLimit = requestsLimit;
    }

    public Integer getWindowSeconds() {
        return windowSeconds;
    }

    public void setWindowSeconds(Integer windowSeconds) {
        this.windowSeconds = windowSeconds;
    }

    public LocalDateTime getResetTime() {
        return resetTime;
    }

    public void setResetTime(LocalDateTime resetTime) {
        this.resetTime = resetTime;
    }

    public Integer getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    public void setRetryAfterSeconds(Integer retryAfterSeconds) {
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Enum para estados de rate limiting
    @Schema(description = "Rate limiting status")
    public enum RateLimitStatus {
        @Schema(description = "Rate limiting is active and allowing requests")
        ACTIVE,

        @Schema(description = "Rate limiting is warning (close to limit)")
        WARNING,

        @Schema(description = "Rate limit has been exceeded")
        EXCEEDED,

        @Schema(description = "Rate limiting is disabled")
        DISABLED
    }

    // Métodos de utilidad
    public boolean isBlocked() {
        return status == RateLimitStatus.EXCEEDED;
    }

    public boolean isWarning() {
        return status == RateLimitStatus.WARNING;
    }

    public boolean isActive() {
        return status == RateLimitStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateLimitDto that = (RateLimitDto) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(clientIp, that.clientIp) &&
                Objects.equals(requestsRemaining, that.requestsRemaining) &&
                Objects.equals(requestsLimit, that.requestsLimit) &&
                Objects.equals(windowSeconds, that.windowSeconds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, clientIp, requestsRemaining, requestsLimit, windowSeconds);
    }

    @Override
    public String toString() {
        return String.format("RateLimitDto{status=%s, clientIp='%s', remaining=%d/%d, window=%ds}",
                status, clientIp, requestsRemaining, requestsLimit, windowSeconds);
    }
}
