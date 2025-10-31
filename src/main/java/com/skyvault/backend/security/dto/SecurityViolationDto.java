package com.skyvault.backend.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO para reportar violaciones de seguridad en SkyVault
 * Registra y comunica intentos de acceso no autorizados o comportamiento sospechoso
 */
@Schema(
        name = "SecurityViolation",
        description = "Information about security violations and suspicious activities"
)
public class SecurityViolationDto {

    @Schema(description = "Unique violation identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private String violationId;

    @Schema(description = "Type of security violation", example = "SUSPICIOUS_REQUEST")
    @NotNull
    private SecurityViolationType type;

    @Schema(description = "Severity level of the violation", example = "MEDIUM")
    @NotNull
    private SecurityViolationSeverity severity;

    @Schema(description = "Client IP address", example = "192.168.1.100")
    private String clientIp;

    @Schema(description = "User agent string", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    @Size(max = 500)
    private String userAgent;

    @Schema(description = "HTTP method used", example = "POST")
    private String httpMethod;

    @Schema(description = "Request URI", example = "/api/v1/aircraft/search")
    private String requestUri;

    @Schema(description = "Request headers (sanitized)")
    private List<String> requestHeaders;

    @Schema(description = "Description of the violation",
            example = "Multiple failed attempts to access admin endpoints from same IP")
    @NotNull
    @Size(max = 1000)
    private String description;

    @Schema(description = "Detailed reason for flagging as violation")
    @Size(max = 2000)
    private String reason;

    @Schema(description = "Actions taken in response")
    private List<String> actionsTaken;

    @Schema(description = "Additional context or metadata")
    private String context;

    @Schema(description = "When the violation occurred", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Whether this violation was automatically handled")
    private Boolean autoHandled;

    @Schema(description = "Session ID if available")
    private String sessionId;

    @Schema(description = "Geographic location info (if available)")
    private String location;

    @Schema(description = "Number of similar violations from this source")
    private Integer repeatCount;

    // Constructores
    public SecurityViolationDto() {
        this.violationId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.autoHandled = false;
        this.repeatCount = 1;
    }

    public SecurityViolationDto(SecurityViolationType type, SecurityViolationSeverity severity,
                                String clientIp, String description) {
        this();
        this.type = type;
        this.severity = severity;
        this.clientIp = clientIp;
        this.description = description;
    }

    // Factory methods para violaciones comunes
    public static SecurityViolationDto suspiciousRequest(String clientIp, String userAgent,
                                                         String requestUri, String reason) {
        SecurityViolationDto dto = new SecurityViolationDto(
                SecurityViolationType.SUSPICIOUS_REQUEST,
                SecurityViolationSeverity.MEDIUM,
                clientIp,
                "Suspicious request pattern detected"
        );
        dto.setUserAgent(userAgent);
        dto.setRequestUri(requestUri);
        dto.setReason(reason);
        return dto;
    }

    public static SecurityViolationDto rateLimitExceeded(String clientIp, String requestUri, int attempts) {
        SecurityViolationDto dto = new SecurityViolationDto(
                SecurityViolationType.RATE_LIMIT_EXCEEDED,
                SecurityViolationSeverity.LOW,
                clientIp,
                "Rate limit exceeded - potential abuse detected"
        );
        dto.setRequestUri(requestUri);
        dto.setRepeatCount(attempts);
        dto.setReason(String.format("Client exceeded rate limit with %d attempts", attempts));
        return dto;
    }

    public static SecurityViolationDto unauthorizedAccess(String clientIp, String requestUri, String userAgent) {
        SecurityViolationDto dto = new SecurityViolationDto(
                SecurityViolationType.UNAUTHORIZED_ACCESS,
                SecurityViolationSeverity.HIGH,
                clientIp,
                "Attempt to access protected resource without authorization"
        );
        dto.setRequestUri(requestUri);
        dto.setUserAgent(userAgent);
        dto.setReason("Access denied to protected endpoint");
        return dto;
    }

    public static SecurityViolationDto malformedRequest(String clientIp, String requestUri, String reason) {
        SecurityViolationDto dto = new SecurityViolationDto(
                SecurityViolationType.MALFORMED_REQUEST,
                SecurityViolationSeverity.MEDIUM,
                clientIp,
                "Malformed or potentially malicious request"
        );
        dto.setRequestUri(requestUri);
        dto.setReason(reason);
        return dto;
    }

    public static SecurityViolationDto blockedContent(String clientIp, String requestUri, String contentType) {
        SecurityViolationDto dto = new SecurityViolationDto(
                SecurityViolationType.BLOCKED_CONTENT,
                SecurityViolationSeverity.MEDIUM,
                clientIp,
                "Blocked potentially dangerous content"
        );
        dto.setRequestUri(requestUri);
        dto.setContext(contentType);
        return dto;
    }

    // Getters y Setters
    public String getViolationId() {
        return violationId;
    }

    public void setViolationId(String violationId) {
        this.violationId = violationId;
    }

    public SecurityViolationType getType() {
        return type;
    }

    public void setType(SecurityViolationType type) {
        this.type = type;
    }

    public SecurityViolationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(SecurityViolationSeverity severity) {
        this.severity = severity;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public List<String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(List<String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getActionsTaken() {
        return actionsTaken;
    }

    public void setActionsTaken(List<String> actionsTaken) {
        this.actionsTaken = actionsTaken;
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

    public Boolean getAutoHandled() {
        return autoHandled;
    }

    public void setAutoHandled(Boolean autoHandled) {
        this.autoHandled = autoHandled;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    // Enums
    @Schema(description = "Type of security violation")
    public enum SecurityViolationType {
        @Schema(description = "Suspicious request pattern")
        SUSPICIOUS_REQUEST,

        @Schema(description = "Rate limit exceeded")
        RATE_LIMIT_EXCEEDED,

        @Schema(description = "Unauthorized access attempt")
        UNAUTHORIZED_ACCESS,

        @Schema(description = "Malformed or invalid request")
        MALFORMED_REQUEST,

        @Schema(description = "Blocked malicious content")
        BLOCKED_CONTENT,

        @Schema(description = "Potential bot or automated attack")
        BOT_ACTIVITY,

        @Schema(description = "IP-based security violation")
        IP_VIOLATION,

        @Schema(description = "Other security violation")
        OTHER
    }

    @Schema(description = "Severity level of security violation")
    public enum SecurityViolationSeverity {
        @Schema(description = "Low severity - monitoring only")
        LOW,

        @Schema(description = "Medium severity - requires attention")
        MEDIUM,

        @Schema(description = "High severity - immediate action required")
        HIGH,

        @Schema(description = "Critical severity - system threat")
        CRITICAL
    }

    // Métodos de utilidad
    public boolean isHighSeverity() {
        return severity == SecurityViolationSeverity.HIGH || severity == SecurityViolationSeverity.CRITICAL;
    }

    public boolean requiresImmediateAction() {
        return severity == SecurityViolationSeverity.CRITICAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityViolationDto that = (SecurityViolationDto) o;
        return Objects.equals(violationId, that.violationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(violationId);
    }

    @Override
    public String toString() {
        return String.format("SecurityViolationDto{id='%s', type=%s, severity=%s, clientIp='%s', timestamp=%s}",
                violationId, type, severity, clientIp, timestamp);
    }
}
