package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Estadísticas de cache")
public class CacheStatisticsDto {

    @Schema(description = "Nombre del cache")
    private String name;

    @Schema(description = "Número de hits")
    private Long hitCount;

    @Schema(description = "Número de misses")
    private Long missCount;

    @Schema(description = "Ratio de hits")
    private Double hitRatio;

    @Schema(description = "Número de evictions")
    private Long evictionCount;

    // CAMPOS FALTANTES:
    @Schema(description = "Número de cargas")
    private Long loadCount;

    @Schema(description = "Ratio de misses")
    private Double missRatio;

    @Schema(description = "Tiempo promedio de carga")
    private Double averageLoadTime;

    @Schema(description = "Último acceso")
    private LocalDateTime lastAccessTime;

    @Schema(description = "Última actualización")
    private LocalDateTime lastUpdateTime;

    @Schema(description = "Tamaño estimado")
    private Long estimatedSize;

    @Schema(description = "Tamaño máximo")
    private Long maxSize;

    // Constructor
    public CacheStatisticsDto() {}

    // GETTERS Y SETTERS COMPLETOS:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public Long getMissCount() {
        return missCount;
    }

    public void setMissCount(Long missCount) {
        this.missCount = missCount;
    }

    public Double getHitRatio() {
        return hitRatio;
    }

    public void setHitRatio(Double hitRatio) {
        this.hitRatio = hitRatio;
    }

    public Long getEvictionCount() {
        return evictionCount;
    }

    public void setEvictionCount(Long evictionCount) {
        this.evictionCount = evictionCount;
    }

    // GETTERS Y SETTERS FALTANTES:
    public Long getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(Long loadCount) {
        this.loadCount = loadCount;
    }

    public Double getMissRatio() {
        return missRatio;
    }

    public void setMissRatio(Double missRatio) {
        this.missRatio = missRatio;
    }

    public Double getAverageLoadTime() {
        return averageLoadTime;
    }

    public void setAverageLoadTime(Double averageLoadTime) {
        this.averageLoadTime = averageLoadTime;
    }

    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(LocalDateTime lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Long estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }
}
