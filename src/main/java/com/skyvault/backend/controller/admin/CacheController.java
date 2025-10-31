package com.skyvault.backend.controller.admin;

import com.skyvault.backend.dto.request.CacheOperationDto;
import com.skyvault.backend.dto.response.CacheConfigurationDto;
import com.skyvault.backend.dto.response.CacheHealthDto;
import com.skyvault.backend.dto.response.CacheStatisticsDto;
import com.skyvault.backend.service.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/cache")
@Tag(name = "Cache Administration", description = "Cache management operations for administrators")
@Validated
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    private final CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Operation(
            summary = "Get cache health status",
            description = "Get comprehensive health status of all caches in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cache health retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CacheHealthDto.class))),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    @GetMapping("/health")
    public ResponseEntity<CacheHealthDto> getCacheHealth() {

        logger.info("Admin requesting cache health status");

        CacheHealthDto health = cacheService.getCacheHealth();

        return ResponseEntity.ok(health);
    }

    @Operation(
            summary = "Get all cache names",
            description = "Get list of all cache names in the system"
    )
    @GetMapping("/names")
    public ResponseEntity<Set<String>> getCacheNames() {

        logger.info("Admin requesting cache names");

        Set<String> cacheNames = cacheService.getCacheNames();

        return ResponseEntity.ok(cacheNames);
    }

    @Operation(
            summary = "Get cache statistics for all caches",
            description = "Get performance statistics for all caches"
    )
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, CacheStatisticsDto>> getAllCacheStatistics() {

        logger.info("Admin requesting all cache statistics");

        Map<String, CacheStatisticsDto> statistics = cacheService.getAllCacheStatistics();

        return ResponseEntity.ok(statistics);
    }

    @Operation(
            summary = "Get cache statistics for specific cache",
            description = "Get performance statistics for a specific cache"
    )
    @GetMapping("/statistics/{cacheName}")
    public ResponseEntity<CacheStatisticsDto> getCacheStatistics(
            @Parameter(description = "Cache name", example = "aircraftCache")
            @PathVariable @NotBlank String cacheName) {

        logger.info("Admin requesting statistics for cache: {}", cacheName);

        CacheStatisticsDto statistics = cacheService.getCacheStatistics(cacheName);

        if (statistics == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(statistics);
    }

    @Operation(
            summary = "Get cache configuration",
            description = "Get configuration details for a specific cache"
    )
    @GetMapping("/configuration/{cacheName}")
    public ResponseEntity<CacheConfigurationDto> getCacheConfiguration(
            @Parameter(description = "Cache name", example = "aircraftCache")
            @PathVariable @NotBlank String cacheName) {

        logger.info("Admin requesting configuration for cache: {}", cacheName);

        CacheConfigurationDto config = cacheService.getCacheConfiguration(cacheName);

        if (config == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(config);
    }

    @Operation(
            summary = "Execute cache operation",
            description = "Execute various cache operations like clear, evict, warm-up, etc."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cache operation executed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid operation parameters"),
            @ApiResponse(responseCode = "500", description = "Cache operation failed")
    })
    @PostMapping("/operations")
    public ResponseEntity<Map<String, Object>> executeCacheOperation(
            @Valid @RequestBody CacheOperationDto operationDto) {

        logger.info("Admin executing cache operation: {} on cache: {}",
                operationDto.getOperation(), operationDto.getCacheName());

        try {
            Map<String, Object> result = Map.of(
                    "operation", operationDto.getOperation(),
                    "cacheName", operationDto.getCacheName(),
                    "success", true,
                    "timestamp", System.currentTimeMillis(),
                    "executedBy", operationDto.getExecutedBy()
            );

            // Execute the operation based on type
            switch (operationDto.getOperation()) {
                case "CLEAR" -> {
                    if (operationDto.getCacheName() != null) {
                        cacheService.evictCache(operationDto.getCacheName());
                    }
                }
                case "CLEAR_ALL" -> cacheService.evictAllCaches();
                case "EVICT_KEY" -> {
                    if (operationDto.getCacheName() != null && operationDto.getCacheKey() != null) {
                        cacheService.evictCacheEntry(operationDto.getCacheName(), operationDto.getCacheKey());
                    }
                }
                case "GET_STATS" -> {
                    CacheStatisticsDto stats = cacheService.getCacheStatistics(operationDto.getCacheName());
                    return ResponseEntity.ok(Map.of("statistics", stats));
                }
                case "GET_HEALTH" -> {
                    CacheHealthDto health = cacheService.getCacheHealth();
                    return ResponseEntity.ok(Map.of("health", health));
                }
                default -> {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Unsupported operation: " + operationDto.getOperation()));
                }
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Cache operation failed: {}", e.getMessage(), e);

            Map<String, Object> errorResult = Map.of(
                    "operation", operationDto.getOperation(),
                    "cacheName", operationDto.getCacheName(),
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Clear specific cache",
            description = "Clear all entries from a specific cache"
    )
    @DeleteMapping("/{cacheName}")
    public ResponseEntity<Map<String, Object>> clearCache(
            @Parameter(description = "Cache name to clear", example = "aircraftCache")
            @PathVariable @NotBlank String cacheName) {

        logger.warn("Admin clearing cache: {}", cacheName);

        try {
            cacheService.evictCache(cacheName);

            Map<String, Object> result = Map.of(
                    "cacheName", cacheName,
                    "action", "cleared",
                    "success", true,
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Failed to clear cache {}: {}", cacheName, e.getMessage());

            Map<String, Object> errorResult = Map.of(
                    "cacheName", cacheName,
                    "action", "clear_failed",
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Clear all caches",
            description = "Clear all entries from all caches in the system"
    )
    @DeleteMapping("/all")
    public ResponseEntity<Map<String, Object>> clearAllCaches(
            @Parameter(description = "Force clear even if risky", example = "false")
            @RequestParam(defaultValue = "false") Boolean force) {

        if (force) {
            logger.warn("Admin force-clearing ALL caches");
        } else {
            logger.warn("Admin clearing ALL caches");
        }

        try {
            cacheService.evictAllCaches();

            Map<String, Object> result = Map.of(
                    "action", "cleared_all",
                    "success", true,
                    "force", force,
                    "warning", "All caches have been cleared - performance may be impacted",
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Failed to clear all caches: {}", e.getMessage());

            Map<String, Object> errorResult = Map.of(
                    "action", "clear_all_failed",
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Evict specific cache entry",
            description = "Remove a specific key from a cache"
    )
    @DeleteMapping("/{cacheName}/keys/{key}")
    public ResponseEntity<Map<String, Object>> evictCacheEntry(
            @Parameter(description = "Cache name", example = "aircraftCache")
            @PathVariable @NotBlank String cacheName,

            @Parameter(description = "Cache key to evict", example = "aircraft:15")
            @PathVariable @NotBlank String key) {

        logger.info("Admin evicting key '{}' from cache '{}'", key, cacheName);

        try {
            cacheService.evictCacheEntry(cacheName, key);

            Map<String, Object> result = Map.of(
                    "cacheName", cacheName,
                    "key", key,
                    "action", "evicted",
                    "success", true,
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Failed to evict key '{}' from cache '{}': {}", key, cacheName, e.getMessage());

            Map<String, Object> errorResult = Map.of(
                    "cacheName", cacheName,
                    "key", key,
                    "action", "evict_failed",
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Check if key exists in cache",
            description = "Check if a specific key exists in a cache"
    )
    @GetMapping("/{cacheName}/keys/{key}/exists")
    public ResponseEntity<Map<String, Object>> checkCacheKey(
            @Parameter(description = "Cache name", example = "aircraftCache")
            @PathVariable @NotBlank String cacheName,

            @Parameter(description = "Cache key to check", example = "aircraft:15")
            @PathVariable @NotBlank String key) {

        logger.debug("Admin checking existence of key '{}' in cache '{}'", key, cacheName);

        boolean exists = cacheService.isCached(cacheName, key);

        Map<String, Object> result = Map.of(
                "cacheName", cacheName,
                "key", key,
                "exists", exists,
                "timestamp", System.currentTimeMillis()
        );

        return ResponseEntity.ok(result);
    }
}
