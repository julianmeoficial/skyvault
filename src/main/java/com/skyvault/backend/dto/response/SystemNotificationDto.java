package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Notificación del sistema")
public class SystemNotificationDto {

    @Schema(description = "ID único de la notificación", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Tipo de notificación", example = "ERROR", allowableValues = {"INFO", "WARNING", "ERROR", "CRITICAL"})
    private String type;

    @Schema(description = "Severidad", example = "HIGH", allowableValues = {"LOW", "MEDIUM", "HIGH", "CRITICAL"})
    private String severity;

    @Schema(description = "Título de la notificación", example = "Cache Performance Alert")
    private String title;

    @Schema(description = "Mensaje descriptivo")
    private String message;

    @Schema(description = "Componente que generó la notificación", example = "AircraftService")
    private String component;

    @Schema(description = "Categoría", example = "PERFORMANCE", allowableValues = {"SYSTEM", "PERFORMANCE", "SECURITY", "DATA", "CACHE", "DATABASE"})
    private String category;

    @Schema(description = "Operación relacionada", example = "findAircraft")
    private String operation;

    @Schema(description = "Código de error", example = "CACHE_001")
    private String errorCode;

    @Schema(description = "Detalles adicionales")
    private Map<String, Object> details;

    @Schema(description = "Stack trace del error")
    private String stackTrace;

    @Schema(description = "Usuario relacionado (si aplica)", example = "system")
    private String userId;

    @Schema(description = "Sesión relacionada (si aplica)")
    private String sessionId;

    @Schema(description = "IP de origen")
    private String sourceIp;

    @Schema(description = "User Agent")
    private String userAgent;

    @Schema(description = "Timestamp de la notificación")
    private LocalDateTime timestamp;

    @Schema(description = "Notificación ha sido leída", example = "false")
    private Boolean read;

    @Schema(description = "Timestamp cuando fue leída")
    private LocalDateTime readAt;

    @Schema(description = "Notificación ha sido procesada", example = "false")
    private Boolean processed;

    @Schema(description = "Timestamp cuando fue procesada")
    private LocalDateTime processedAt;

    @Schema(description = "Acción recomendada")
    private String recommendedAction;

    @Schema(description = "URL para más información")
    private String moreInfoUrl;

    @Schema(description = "Notificación puede ser descartada", example = "true")
    private Boolean dismissible;

    @Schema(description = "Tiempo de vida en minutos", example = "1440")
    private Integer ttlMinutes;

    // Constructors
    public SystemNotificationDto() {
        this.timestamp = LocalDateTime.now();
        this.read = false;
        this.processed = false;
        this.dismissible = true;
    }

    public SystemNotificationDto(String type, String title, String message) {
        this();
        this.type = type;
        this.title = title;
        this.message = message;
    }

    // Getters and Setters completos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public void setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
    }

    public Boolean getDismissible() {
        return dismissible;
    }

    public void setDismissible(Boolean dismissible) {
        this.dismissible = dismissible;
    }

    public Integer getTtlMinutes() {
        return ttlMinutes;
    }

    public void setTtlMinutes(Integer ttlMinutes) {
        this.ttlMinutes = ttlMinutes;
    }
}
