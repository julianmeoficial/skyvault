package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;
import java.util.Map;

@Schema(description = "Configuración de cache")
public class CacheConfigurationDto {

    @Schema(description = "Nombre del cache", example = "aircraftCache")
    private String name;

    @Schema(description = "Número máximo de entradas", example = "1000")
    private Long maxSize;

    @Schema(description = "Tiempo de vida en segundos", example = "3600")
    private Long ttlSeconds;

    @Schema(description = "Tiempo de inactividad en segundos", example = "1800")
    private Long idleTimeSeconds;

    @Schema(description = "Cache está habilitado", example = "true")
    private Boolean enabled;

    @Schema(description = "Tipo de cache", example = "CAFFEINE")
    private String cacheType;

    @Schema(description = "Configuración de evicción", example = "LRU")
    private String evictionPolicy;

    @Schema(description = "Número de entradas actuales", example = "450")
    private Long currentSize;

    @Schema(description = "Configuración adicional")
    private Map<String, Object> additionalConfig;

    @Schema(description = "Cache escribir-a-través habilitado", example = "false")
    private Boolean writeThrough;

    @Schema(description = "Cache escribir-detrás habilitado", example = "false")
    private Boolean writeBehind;

    @Schema(description = "Estadísticas habilitadas", example = "true")
    private Boolean statisticsEnabled;

    @Schema(description = "Tiempo de refresh en segundos", example = "300")
    private Long refreshAfterWriteSeconds;

    // Constructors
    public CacheConfigurationDto() {}

    public CacheConfigurationDto(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public Long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(Long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public Long getIdleTimeSeconds() {
        return idleTimeSeconds;
    }

    public void setIdleTimeSeconds(Long idleTimeSeconds) {
        this.idleTimeSeconds = idleTimeSeconds;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public String getEvictionPolicy() {
        return evictionPolicy;
    }

    public void setEvictionPolicy(String evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    public Long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Long currentSize) {
        this.currentSize = currentSize;
    }

    public Map<String, Object> getAdditionalConfig() {
        return additionalConfig;
    }

    public void setAdditionalConfig(Map<String, Object> additionalConfig) {
        this.additionalConfig = additionalConfig;
    }

    public Boolean getWriteThrough() {
        return writeThrough;
    }

    public void setWriteThrough(Boolean writeThrough) {
        this.writeThrough = writeThrough;
    }

    public Boolean getWriteBehind() {
        return writeBehind;
    }

    public void setWriteBehind(Boolean writeBehind) {
        this.writeBehind = writeBehind;
    }

    public Boolean getStatisticsEnabled() {
        return statisticsEnabled;
    }

    public void setStatisticsEnabled(Boolean statisticsEnabled) {
        this.statisticsEnabled = statisticsEnabled;
    }

    public Long getRefreshAfterWriteSeconds() {
        return refreshAfterWriteSeconds;
    }

    public void setRefreshAfterWriteSeconds(Long refreshAfterWriteSeconds) {
        this.refreshAfterWriteSeconds = refreshAfterWriteSeconds;
    }
}
