package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Schema(description = "Estado de salud de los caches")
public class CacheHealthDto {

    @Schema(description = "Estado general", example = "HEALTHY")
    private String overallStatus;

    @Schema(description = "Número total de caches", example = "5")
    private Integer totalCaches;

    @Schema(description = "Caches saludables", example = "4")
    private Integer healthyCaches;

    @Schema(description = "Caches con problemas", example = "1")
    private Integer unhealthyCaches;

    @Schema(description = "Hit ratio promedio", example = "0.85")
    private Double averageHitRatio;

    @Schema(description = "Memoria total usada en MB", example = "256.5")
    private Double totalMemoryUsedMB;

    @Schema(description = "Memoria máxima disponible en MB", example = "1024.0")
    private Double maxMemoryMB;

    @Schema(description = "Porcentaje de memoria usada", example = "25.1")
    private Double memoryUsagePercent;

    @Schema(description = "Detalles de cada cache")
    private Map<String, CacheStatusDto> cacheDetails;

    @Schema(description = "Errores recientes")
    private List<CacheErrorDto> recentErrors;

    @Schema(description = "Alertas activas")
    private List<String> activeAlerts;

    @Schema(description = "Última verificación")
    private LocalDateTime lastCheckTime;

    @Schema(description = "Tiempo de respuesta promedio en ms", example = "12.5")
    private Double averageResponseTimeMs;

    @Schema(description = "Operaciones por segundo", example = "150.2")
    private Double operationsPerSecond;

    // Constructors
    public CacheHealthDto() {
        this.lastCheckTime = LocalDateTime.now();
    }

    // Getters and Setters
    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public Integer getTotalCaches() {
        return totalCaches;
    }

    public void setTotalCaches(Integer totalCaches) {
        this.totalCaches = totalCaches;
    }

    public Integer getHealthyCaches() {
        return healthyCaches;
    }

    public void setHealthyCaches(Integer healthyCaches) {
        this.healthyCaches = healthyCaches;
    }

    public Integer getUnhealthyCaches() {
        return unhealthyCaches;
    }

    public void setUnhealthyCaches(Integer unhealthyCaches) {
        this.unhealthyCaches = unhealthyCaches;
    }

    public Double getAverageHitRatio() {
        return averageHitRatio;
    }

    public void setAverageHitRatio(Double averageHitRatio) {
        this.averageHitRatio = averageHitRatio;
    }

    public Double getTotalMemoryUsedMB() {
        return totalMemoryUsedMB;
    }

    public void setTotalMemoryUsedMB(Double totalMemoryUsedMB) {
        this.totalMemoryUsedMB = totalMemoryUsedMB;
    }

    public Double getMaxMemoryMB() {
        return maxMemoryMB;
    }

    public void setMaxMemoryMB(Double maxMemoryMB) {
        this.maxMemoryMB = maxMemoryMB;
    }

    public Double getMemoryUsagePercent() {
        return memoryUsagePercent;
    }

    public void setMemoryUsagePercent(Double memoryUsagePercent) {
        this.memoryUsagePercent = memoryUsagePercent;
    }

    public Map<String, CacheStatusDto> getCacheDetails() {
        return cacheDetails;
    }

    public void setCacheDetails(Map<String, CacheStatusDto> cacheDetails) {
        this.cacheDetails = cacheDetails;
    }

    public List<CacheErrorDto> getRecentErrors() {
        return recentErrors;
    }

    public void setRecentErrors(List<CacheErrorDto> recentErrors) {
        this.recentErrors = recentErrors;
    }

    public List<String> getActiveAlerts() {
        return activeAlerts;
    }

    public void setActiveAlerts(List<String> activeAlerts) {
        this.activeAlerts = activeAlerts;
    }

    public LocalDateTime getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(LocalDateTime lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public Double getAverageResponseTimeMs() {
        return averageResponseTimeMs;
    }

    public void setAverageResponseTimeMs(Double averageResponseTimeMs) {
        this.averageResponseTimeMs = averageResponseTimeMs;
    }

    public Double getOperationsPerSecond() {
        return operationsPerSecond;
    }

    public void setOperationsPerSecond(Double operationsPerSecond) {
        this.operationsPerSecond = operationsPerSecond;
    }

    // Clases internas de soporte
    public static class CacheStatusDto {
        private String name;
        private String status;
        private Double hitRatio;
        private Long size;
        private String lastError;

        // Constructors y getters/setters
        public CacheStatusDto() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Double getHitRatio() { return hitRatio; }
        public void setHitRatio(Double hitRatio) { this.hitRatio = hitRatio; }

        public Long getSize() { return size; }
        public void setSize(Long size) { this.size = size; }

        public String getLastError() { return lastError; }
        public void setLastError(String lastError) { this.lastError = lastError; }
    }

    public static class CacheErrorDto {
        private String cacheName;
        private String error;
        private LocalDateTime timestamp;
        private String severity;

        // Constructors y getters/setters
        public CacheErrorDto() {}

        public String getCacheName() { return cacheName; }
        public void setCacheName(String cacheName) { this.cacheName = cacheName; }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
    }
}
