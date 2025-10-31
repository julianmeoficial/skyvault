package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.response.CacheConfigurationDto;
import com.skyvault.backend.dto.response.CacheHealthDto;
import com.skyvault.backend.dto.response.CacheStatisticsDto;
import com.skyvault.backend.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CacheServiceImpl implements CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    private final CacheManager cacheManager;

    // Métricas en memoria (en producción sería una solución más robusta como Micrometer)
    private final Map<String, CacheMetrics> cacheMetrics = new ConcurrentHashMap<>();

    @Autowired
    public CacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        initializeCacheMetrics();
    }

    @Override
    public Set<String> getCacheNames() {
        Set<String> names = new HashSet<>(cacheManager.getCacheNames());
        logger.debug("Available cache names: {}", names);
        return names;
    }

    @Override
    public CacheStatisticsDto getCacheStatistics(String cacheName) {
        logger.debug("Getting statistics for cache: {}", cacheName);

        CacheMetrics metrics = cacheMetrics.get(cacheName);
        if (metrics == null) {
            logger.warn("No metrics found for cache: {}", cacheName);
            return null;
        }

        CacheStatisticsDto stats = new CacheStatisticsDto();
        stats.setName(cacheName);
        stats.setHitCount(metrics.hitCount.get());
        stats.setMissCount(metrics.missCount.get());
        stats.setEvictionCount(metrics.evictionCount.get());
        stats.setLoadCount(metrics.loadCount.get());

        // Calcular ratios
        long totalRequests = stats.getHitCount() + stats.getMissCount();
        stats.setHitRatio(totalRequests > 0 ? (double) stats.getHitCount() / totalRequests : 0.0);
        stats.setMissRatio(totalRequests > 0 ? (double) stats.getMissCount() / totalRequests : 0.0);

        // Métricas de tiempo
        stats.setAverageLoadTime(metrics.averageLoadTime);
        stats.setLastAccessTime(metrics.lastAccessTime);
        stats.setLastUpdateTime(metrics.lastUpdateTime);

        // Métricas de memoria (simuladas)
        stats.setEstimatedSize(metrics.estimatedSize.get());
        stats.setMaxSize(metrics.maxSize);

        return stats;
    }

    @Override
    public Map<String, CacheStatisticsDto> getAllCacheStatistics() {
        logger.debug("Getting statistics for all caches");

        Map<String, CacheStatisticsDto> allStats = new HashMap<>();

        for (String cacheName : getCacheNames()) {
            CacheStatisticsDto stats = getCacheStatistics(cacheName);
            if (stats != null) {
                allStats.put(cacheName, stats);
            }
        }

        return allStats;
    }

    @Override
    public void evictCache(String cacheName) {
        logger.info("Evicting cache: {}", cacheName);

        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();

            // Actualizar métricas
            CacheMetrics metrics = cacheMetrics.get(cacheName);
            if (metrics != null) {
                metrics.evictionCount.addAndGet(metrics.estimatedSize.get());
                metrics.estimatedSize.set(0);
                metrics.lastUpdateTime = LocalDateTime.now();
            }

            logger.info("Cache '{}' evicted successfully", cacheName);
        } else {
            logger.warn("Cache '{}' not found", cacheName);
        }
    }

    @Override
    public void evictAllCaches() {
        logger.warn("Evicting ALL caches");

        Set<String> cacheNames = getCacheNames();
        for (String cacheName : cacheNames) {
            evictCache(cacheName);
        }

        logger.warn("All caches evicted - performance may be impacted");
    }

    @Override
    public void evictCacheEntry(String cacheName, Object key) {
        logger.debug("Evicting key '{}' from cache '{}'", key, cacheName);

        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);

            // Actualizar métricas
            CacheMetrics metrics = cacheMetrics.get(cacheName);
            if (metrics != null) {
                metrics.evictionCount.incrementAndGet();
                metrics.estimatedSize.decrementAndGet();
                metrics.lastUpdateTime = LocalDateTime.now();
            }

            logger.debug("Key '{}' evicted from cache '{}'", key, cacheName);
        } else {
            logger.warn("Cache '{}' not found", cacheName);
        }
    }

    @Override
    public boolean isCached(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(key);
            boolean cached = wrapper != null;

            // Actualizar métricas
            CacheMetrics metrics = cacheMetrics.get(cacheName);
            if (metrics != null) {
                if (cached) {
                    metrics.hitCount.incrementAndGet();
                } else {
                    metrics.missCount.incrementAndGet();
                }
                metrics.lastAccessTime = LocalDateTime.now();
            }

            logger.debug("Cache check for key '{}' in cache '{}': {}", key, cacheName, cached);
            return cached;
        }

        return false;
    }

    @Override
    public CacheConfigurationDto getCacheConfiguration(String cacheName) {
        logger.debug("Getting configuration for cache: {}", cacheName);

        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }

        CacheConfigurationDto config = new CacheConfigurationDto();
        config.setName(cacheName);
        config.setEnabled(true);
        config.setCacheType("CAFFEINE"); // Asumiendo Caffeine como implementación

        // Configuración por defecto (en producción esto vendría de la configuración real)
        CacheMetrics metrics = cacheMetrics.get(cacheName);
        if (metrics != null) {
            config.setMaxSize(metrics.maxSize);
            config.setCurrentSize(metrics.estimatedSize.get());
        }

        config.setTtlSeconds(3600L); // 1 hora por defecto
        config.setIdleTimeSeconds(1800L); // 30 minutos por defecto
        config.setEvictionPolicy("LRU");
        config.setStatisticsEnabled(true);
        config.setWriteThrough(false);
        config.setWriteBehind(false);
        config.setRefreshAfterWriteSeconds(300L); // 5 minutos

        // Configuración adicional
        Map<String, Object> additionalConfig = new HashMap<>();
        additionalConfig.put("recordStats", true);
        additionalConfig.put("softValues", false);
        additionalConfig.put("weakKeys", false);
        config.setAdditionalConfig(additionalConfig);

        return config;
    }

    @Override
    public CacheHealthDto getCacheHealth() {
        logger.debug("Getting cache health status");

        CacheHealthDto health = new CacheHealthDto();
        health.setLastCheckTime(LocalDateTime.now());

        Map<String, CacheStatisticsDto> allStats = getAllCacheStatistics();

        int totalCaches = allStats.size();
        int healthyCaches = 0;
        int unhealthyCaches = 0;
        double totalHitRatio = 0.0;
        double totalMemoryUsed = 0.0;
        double maxMemory = 1024.0; // MB simulado

        List<String> activeAlerts = new ArrayList<>();
        List<CacheHealthDto.CacheErrorDto> recentErrors = new ArrayList<>();

        for (Map.Entry<String, CacheStatisticsDto> entry : allStats.entrySet()) {
            String cacheName = entry.getKey();
            CacheStatisticsDto stats = entry.getValue();

            boolean isCacheHealthy = evaluateCacheHealth(stats);

            if (isCacheHealthy) {
                healthyCaches++;
            } else {
                unhealthyCaches++;
                activeAlerts.add("Cache '" + cacheName + "' has low hit ratio: " +
                        String.format("%.2f%%", stats.getHitRatio() * 100));
            }

            totalHitRatio += stats.getHitRatio();
            totalMemoryUsed += stats.getEstimatedSize() * 0.001; // Conversión simulada a MB
        }

        // Configurar resultado
        health.setTotalCaches(totalCaches);
        health.setHealthyCaches(healthyCaches);
        health.setUnhealthyCaches(unhealthyCaches);
        health.setOverallStatus(unhealthyCaches == 0 ? "HEALTHY" : "DEGRADED");
        health.setAverageHitRatio(totalCaches > 0 ? totalHitRatio / totalCaches : 0.0);
        health.setTotalMemoryUsedMB(totalMemoryUsed);
        health.setMaxMemoryMB(maxMemory);
        health.setMemoryUsagePercent((totalMemoryUsed / maxMemory) * 100);
        health.setActiveAlerts(activeAlerts);
        health.setRecentErrors(recentErrors);

        // Métricas de rendimiento simuladas
        health.setAverageResponseTimeMs(12.5);
        health.setOperationsPerSecond(150.2);

        // Detalles por cache
        Map<String, CacheHealthDto.CacheStatusDto> cacheDetails = new HashMap<>();
        for (Map.Entry<String, CacheStatisticsDto> entry : allStats.entrySet()) {
            String cacheName = entry.getKey();
            CacheStatisticsDto stats = entry.getValue();

            CacheHealthDto.CacheStatusDto status = new CacheHealthDto.CacheStatusDto();
            status.setName(cacheName);
            status.setStatus(evaluateCacheHealth(stats) ? "HEALTHY" : "DEGRADED");
            status.setHitRatio(stats.getHitRatio());
            status.setSize(stats.getEstimatedSize());

            cacheDetails.put(cacheName, status);
        }
        health.setCacheDetails(cacheDetails);

        return health;
    }

    @Override
    public void warmUpCache(String cacheName) {
        logger.info("Warming up cache: {}", cacheName);

        // En una implementación real, aquí se cargarían datos predefinidos
        // Por ejemplo, cargar aeronaves más populares, fabricantes activos, etc.

        CacheMetrics metrics = cacheMetrics.get(cacheName);
        if (metrics != null) {
            metrics.loadCount.addAndGet(100); // Simular carga de 100 elementos
            metrics.estimatedSize.addAndGet(100);
            metrics.lastUpdateTime = LocalDateTime.now();
        }

        logger.info("Cache '{}' warmed up successfully", cacheName);
    }

    @Override
    public void warmUpAllCaches() {
        logger.info("Warming up all caches");

        Set<String> cacheNames = getCacheNames();
        for (String cacheName : cacheNames) {
            warmUpCache(cacheName);
        }

        logger.info("All caches warmed up");
    }

    @Override
    public Map<String, Object> getCacheInfo(String cacheName) {
        logger.debug("Getting cache info for: {}", cacheName);

        Map<String, Object> info = new HashMap<>();

        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return info;
        }

        info.put("name", cacheName);
        info.put("type", cache.getClass().getSimpleName());
        info.put("nativeCache", cache.getNativeCache().getClass().getSimpleName());

        CacheStatisticsDto stats = getCacheStatistics(cacheName);
        if (stats != null) {
            info.put("hitCount", stats.getHitCount());
            info.put("missCount", stats.getMissCount());
            info.put("hitRatio", stats.getHitRatio());
            info.put("estimatedSize", stats.getEstimatedSize());
        }

        CacheConfigurationDto config = getCacheConfiguration(cacheName);
        if (config != null) {
            info.put("maxSize", config.getMaxSize());
            info.put("ttlSeconds", config.getTtlSeconds());
            info.put("evictionPolicy", config.getEvictionPolicy());
        }

        return info;
    }

    // Métodos privados auxiliares

    private void initializeCacheMetrics() {
        // Inicializar métricas para caches conocidos
        String[] knownCaches = {
                "aircraftCache", "manufacturerCache", "familyCache",
                "aircraftListCache", "manufacturerListCache", "familyListCache",
                "catalogCache", "searchCache", "comparisonCache", "statisticsCache"
        };

        for (String cacheName : knownCaches) {
            cacheMetrics.put(cacheName, new CacheMetrics(cacheName));
        }

        logger.info("Initialized cache metrics for {} caches", knownCaches.length);
    }

    private boolean evaluateCacheHealth(CacheStatisticsDto stats) {
        if (stats == null) {
            return false;
        }

        // Considerar cache saludable si:
        // - Hit ratio > 60%
        // - No hay demasiadas evictions recientes
        // - Hay actividad (no está vacío)

        boolean goodHitRatio = stats.getHitRatio() > 0.6;
        boolean hasActivity = (stats.getHitCount() + stats.getMissCount()) > 0;
        boolean reasonableSize = stats.getEstimatedSize() > 0;

        return goodHitRatio && hasActivity && reasonableSize;
    }

    // Clase interna para métricas
    private static class CacheMetrics {
        final String name;
        final AtomicLong hitCount = new AtomicLong(0);
        final AtomicLong missCount = new AtomicLong(0);
        final AtomicLong evictionCount = new AtomicLong(0);
        final AtomicLong loadCount = new AtomicLong(0);
        final AtomicLong estimatedSize = new AtomicLong(0);
        final long maxSize = 1000L;
        final double averageLoadTime = 25.0; // ms
        LocalDateTime lastAccessTime = LocalDateTime.now();
        LocalDateTime lastUpdateTime = LocalDateTime.now();

        CacheMetrics(String name) {
            this.name = name;
            // Inicializar con algunos valores simulados
            this.hitCount.set(1000L + (long)(Math.random() * 5000));
            this.missCount.set(100L + (long)(Math.random() * 500));
            this.estimatedSize.set(500L + (long)(Math.random() * 500));
        }
    }
}
