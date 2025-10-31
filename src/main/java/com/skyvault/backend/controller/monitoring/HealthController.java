package com.skyvault.backend.controller.monitoring;

import com.skyvault.backend.service.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health Monitoring", description = "Custom health check endpoints for monitoring")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    private final DataSource dataSource;
    private final CacheService cacheService;

    private final long startTime;

    @Autowired
    public HealthController(DataSource dataSource, CacheService cacheService) {
        this.dataSource = dataSource;
        this.cacheService = cacheService;

        this.startTime = System.currentTimeMillis();
    }

    @Operation(
            summary = "Liveness probe",
            description = "Check if the application is running (for Kubernetes liveness probe)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application is alive"),
            @ApiResponse(responseCode = "503", description = "Application is not responding")
    })
    @GetMapping("/liveness")
    public ResponseEntity<Map<String, Object>> liveness() {

        // Simple liveness check - just return OK if we can respond
        Map<String, Object> response = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "application", "SkyVault Backend",
                "version", "1.0.0"
        );

        logger.debug("Liveness probe - OK");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Readiness probe",
            description = "Check if the application is ready to serve requests (for Kubernetes readiness probe)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application is ready"),
            @ApiResponse(responseCode = "503", description = "Application is not ready")
    })
    @GetMapping("/readiness")
    public ResponseEntity<Map<String, Object>> readiness() {

        try {
            // Check critical dependencies
            boolean dbReady = isDatabaseReady();
            boolean cacheReady = isCacheReady();

            String status = (dbReady && cacheReady) ? "UP" : "DOWN";
            int httpStatus = (dbReady && cacheReady) ? 200 : 503;

            Map<String, Object> response = Map.of(
                    "status", status,
                    "timestamp", LocalDateTime.now(),
                    "checks", Map.of(
                            "database", dbReady ? "UP" : "DOWN",
                            "cache", cacheReady ? "UP" : "DOWN"
                    ),
                    "ready", dbReady && cacheReady
            );

            logger.debug("Readiness probe - Database: {}, Cache: {}", dbReady, cacheReady);

            return ResponseEntity.status(httpStatus).body(response);

        } catch (Exception e) {
            logger.error("Readiness probe failed: {}", e.getMessage());

            Map<String, Object> response = Map.of(
                    "status", "DOWN",
                    "timestamp", LocalDateTime.now(),
                    "error", e.getMessage(),
                    "ready", false
            );

            return ResponseEntity.status(503).body(response);
        }
    }

    @Operation(
            summary = "Database health check",
            description = "Check database connectivity and performance"
    )
    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> databaseHealth() {

        try {
            long startTime = System.currentTimeMillis();

            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                boolean isValid = connection.isValid(5); // 5 second timeout
                long responseTime = System.currentTimeMillis() - startTime;

                String status = isValid ? "UP" : "DOWN";

                Map<String, Object> response = Map.of(
                        "status", status,
                        "timestamp", LocalDateTime.now(),
                        "database", Map.of(
                                "connected", isValid,
                                "responseTimeMs", responseTime,
                                "url", connection.getMetaData().getURL(),
                                "driver", connection.getMetaData().getDriverName()
                        )
                );

                logger.debug("Database health check - Status: {}, Response time: {}ms", status, responseTime);

                return isValid ? ResponseEntity.ok(response) : ResponseEntity.status(503).body(response);
            }

        } catch (Exception e) {
            logger.error("Database health check failed: {}", e.getMessage());

            Map<String, Object> response = Map.of(
                    "status", "DOWN",
                    "timestamp", LocalDateTime.now(),
                    "error", e.getMessage(),
                    "database", Map.of("connected", false)
            );

            return ResponseEntity.status(503).body(response);
        }
    }

    @Operation(
            summary = "Cache health check",
            description = "Check cache system health and performance"
    )
    @GetMapping("/cache")
    public ResponseEntity<Map<String, Object>> cacheHealth() {

        try {
            logger.debug("Performing cache health check");

            var cacheHealthDto = cacheService.getCacheHealth();

            boolean healthy = "HEALTHY".equals(cacheHealthDto.getOverallStatus());
            String status = healthy ? "UP" : "DOWN";

            Map<String, Object> response = Map.of(
                    "status", status,
                    "timestamp", LocalDateTime.now(),
                    "cache", Map.of(
                            "overallStatus", cacheHealthDto.getOverallStatus(),
                            "totalCaches", cacheHealthDto.getTotalCaches(),
                            "healthyCaches", cacheHealthDto.getHealthyCaches(),
                            "unhealthyCaches", cacheHealthDto.getUnhealthyCaches(),
                            "averageHitRatio", cacheHealthDto.getAverageHitRatio(),
                            "memoryUsagePercent", cacheHealthDto.getMemoryUsagePercent()
                    )
            );

            logger.debug("Cache health check - Status: {}, Healthy caches: {}/{}",
                    status, cacheHealthDto.getHealthyCaches(), cacheHealthDto.getTotalCaches());

            return healthy ? ResponseEntity.ok(response) : ResponseEntity.status(503).body(response);

        } catch (Exception e) {
            logger.error("Cache health check failed: {}", e.getMessage());

            Map<String, Object> response = Map.of(
                    "status", "DOWN",
                    "timestamp", LocalDateTime.now(),
                    "error", e.getMessage(),
                    "cache", Map.of("available", false)
            );

            return ResponseEntity.status(503).body(response);
        }
    }

    @Operation(
            summary = "External services health check",
            description = "Check health of external dependencies and services"
    )
    @GetMapping("/external")
    public ResponseEntity<Map<String, Object>> externalServicesHealth() {

        // In a real application, this would check external APIs, message queues, etc.
        // For SkyVault, we mainly check internal components

        Map<String, Object> checks = Map.of(
                "swagger-ui", checkSwaggerUI(),
                "actuator", checkActuator()
        );

        boolean allHealthy = checks.values().stream()
                .allMatch(check -> "UP".equals(((Map<String, Object>) check).get("status")));

        String overallStatus = allHealthy ? "UP" : "DOWN";

        Map<String, Object> response = Map.of(
                "status", overallStatus,
                "timestamp", LocalDateTime.now(),
                "external", checks
        );

        logger.debug("External services health check - Overall status: {}", overallStatus);

        return allHealthy ? ResponseEntity.ok(response) : ResponseEntity.status(503).body(response);
    }

    @Operation(
            summary = "Comprehensive health check",
            description = "Complete health check of all system components"
    )
    @GetMapping("/complete")
    public ResponseEntity<Map<String, Object>> completeHealthCheck() {

        try {
            boolean dbReady = isDatabaseReady();
            boolean cacheReady = isCacheReady();

            // Get runtime information
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            double memoryUsagePercent = (double) usedMemory / totalMemory * 100;

            boolean memoryOK = memoryUsagePercent < 90; // Less than 90% memory usage
            boolean overallHealthy = dbReady && cacheReady && memoryOK;

            String status = overallHealthy ? "UP" : "DOWN";

            Map<String, Object> response = Map.of(
                    "status", status,
                    "timestamp", LocalDateTime.now(),
                    "application", Map.of(
                            "name", "SkyVault Backend",
                            "version", "1.0.0",
                            "profiles", "dev" // Could be dynamic
                    ),
                    "components", Map.of(
                            "database", dbReady ? "UP" : "DOWN",
                            "cache", cacheReady ? "UP" : "DOWN",
                            "memory", memoryOK ? "UP" : "WARN"
                    ),
                    "system", Map.of(
                            "memoryUsagePercent", Math.round(memoryUsagePercent * 100.0) / 100.0,
                            "totalMemoryMB", totalMemory / 1024 / 1024,
                            "usedMemoryMB", usedMemory / 1024 / 1024,
                            "processors", runtime.availableProcessors(),
                            "uptime", getUptime()
                    )
            );

            logger.info("Complete health check - Status: {}, DB: {}, Cache: {}, Memory: {}%",
                    status, dbReady, cacheReady, Math.round(memoryUsagePercent));

            return overallHealthy ? ResponseEntity.ok(response) : ResponseEntity.status(503).body(response);

        } catch (Exception e) {
            logger.error("Complete health check failed: {}", e.getMessage());

            Map<String, Object> response = Map.of(
                    "status", "DOWN",
                    "timestamp", LocalDateTime.now(),
                    "error", e.getMessage()
            );

            return ResponseEntity.status(503).body(response);
        }
    }

    // Private helper methods

    private boolean isDatabaseReady() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(3);
        } catch (Exception e) {
            logger.warn("Database readiness check failed: {}", e.getMessage());
            return false;
        }
    }

    private boolean isCacheReady() {
        try {
            var cacheHealth = cacheService.getCacheHealth();
            return "HEALTHY".equals(cacheHealth.getOverallStatus());
        } catch (Exception e) {
            logger.warn("Cache readiness check failed: {}", e.getMessage());
            return false;
        }
    }

    private Map<String, Object> checkSwaggerUI() {
        // Simulate check
        return Map.of(
                "status", "UP",
                "url", "/swagger-ui/index.html",
                "description", "API Documentation"
        );
    }

    private Map<String, Object> checkActuator() {
        // Simulate check
        return Map.of(
                "status", "UP",
                "url", "/actuator",
                "description", "Spring Boot Actuator"
        );
    }

    private String getUptime() {
        long uptimeMs = System.currentTimeMillis() - startTime;
        long days = uptimeMs / (1000 * 60 * 60 * 24);
        long hours = (uptimeMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (uptimeMs % (1000 * 60 * 60)) / (1000 * 60);

        return String.format("%dd %02dh %02dm", days, hours, minutes);
    }
}
