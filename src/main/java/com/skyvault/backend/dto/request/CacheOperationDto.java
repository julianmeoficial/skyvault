package com.skyvault.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Map;

@Schema(description = "DTO para operaciones de cache")
public class CacheOperationDto {

    @NotBlank(message = "Operation is required")
    @Pattern(regexp = "^(CLEAR|CLEAR_ALL|EVICT|EVICT_KEY|REFRESH|WARM_UP|GET_STATS|GET_HEALTH)$",
            message = "Invalid operation")
    @Schema(description = "Operación a realizar", example = "CLEAR",
            allowableValues = {"CLEAR", "CLEAR_ALL", "EVICT", "EVICT_KEY", "REFRESH", "WARM_UP", "GET_STATS", "GET_HEALTH"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String operation;

    @Size(max = 50, message = "Cache name cannot exceed 50 characters")
    @Schema(description = "Nombre del cache específico", example = "aircraftCache")
    private String cacheName;

    @Schema(description = "Clave específica del cache")
    private String cacheKey;

    @Schema(description = "Lista de nombres de cache para operaciones múltiples")
    private List<String> cacheNames;

    @Schema(description = "Lista de claves para operaciones múltiples")
    private List<String> cacheKeys;

    @Schema(description = "Forzar operación aunque cause impacto en performance", example = "false")
    private Boolean force = false;

    @Schema(description = "Ejecutar operación de forma asíncrona", example = "true")
    private Boolean async = true;

    @Schema(description = "Parámetros adicionales para la operación")
    private Map<String, Object> parameters;

    @Size(max = 200, message = "Reason cannot exceed 200 characters")
    @Schema(description = "Razón para la operación (para auditoría)")
    private String reason;

    @Schema(description = "Usuario que ejecuta la operación")
    private String executedBy;

    @Schema(description = "Notificar resultado por email", example = "false")
    private Boolean notifyResult = false;

    @Schema(description = "Timeout en segundos para la operación", example = "30")
    private Integer timeoutSeconds = 30;

    // Constructors
    public CacheOperationDto() {}

    public CacheOperationDto(String operation) {
        this.operation = operation;
    }

    public CacheOperationDto(String operation, String cacheName) {
        this.operation = operation;
        this.cacheName = cacheName;
    }

    // Getters and Setters
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public List<String> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(List<String> cacheNames) {
        this.cacheNames = cacheNames;
    }

    public List<String> getCacheKeys() {
        return cacheKeys;
    }

    public void setCacheKeys(List<String> cacheKeys) {
        this.cacheKeys = cacheKeys;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public Boolean getNotifyResult() {
        return notifyResult;
    }

    public void setNotifyResult(Boolean notifyResult) {
        this.notifyResult = notifyResult;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }
}
