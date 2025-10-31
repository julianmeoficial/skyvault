package com.skyvault.backend.service;

import com.skyvault.backend.dto.response.CacheConfigurationDto;
import com.skyvault.backend.dto.response.CacheHealthDto;
import com.skyvault.backend.dto.response.CacheStatisticsDto;

import java.util.Set;
import java.util.Map;

/**
 * Servicio para gestión centralizada de cache.
 */
public interface CacheService {

    /**
     * Obtener nombres de todos los caches
     */
    Set<String> getCacheNames();

    /**
     * Obtener estadísticas de un cache específico
     */
    CacheStatisticsDto getCacheStatistics(String cacheName);

    /**
     * Obtener estadísticas de todos los caches
     */
    Map<String, CacheStatisticsDto> getAllCacheStatistics();

    /**
     * Limpiar un cache específico
     */
    void evictCache(String cacheName);

    /**
     * Limpiar todos los caches
     */
    void evictAllCaches();

    /**
     * Limpiar entrada específica de un cache
     */
    void evictCacheEntry(String cacheName, Object key);

    /**
     * Verificar si una entrada existe en cache
     */
    boolean isCached(String cacheName, Object key);

    /**
     * Obtener configuración de cache
     */
    CacheConfigurationDto getCacheConfiguration(String cacheName);

    /**
     * Obtener estado de salud de los caches
     */
    CacheHealthDto getCacheHealth();

    /**
     * Precarga un cache específico con datos comunes
     */
    void warmUpCache(String cacheName);

    /**
     * Precarga todos los caches con datos comunes
     */
    void warmUpAllCaches();

    /**
     * Obtiene información detallada de un cache específico
     */
    Map<String, Object> getCacheInfo(String cacheName);
}
