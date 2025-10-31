package com.skyvault.backend.controller.monitoring;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/metrics")
@Tag(name = "Metrics Monitoring", description = "Custom application metrics and performance monitoring")
@Validated
public class MetricsController {

    private static final Logger logger = LoggerFactory.getLogger(MetricsController.class);

    // Simulated metrics storage - in production this would come from actual metrics systems
    private static final Map<String, Object> endpointMetrics = new HashMap<>();
    private static final Map<String, Object> performanceMetrics = new HashMap<>();
    private static final Map<String, Object> errorMetrics = new HashMap<>();
    private static final Map<String, Object> businessMetrics = new HashMap<>();

    static {
        // Initialize with some sample data
        initializeSampleMetrics();
    }

    @Operation(
            summary = "Get endpoint metrics",
            description = "Get performance metrics for API endpoints"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endpoint metrics retrieved successfully")
    })
    @GetMapping("/endpoints")
    public ResponseEntity<Map<String, Object>> getEndpointMetrics(
            @Parameter(description = "Time period in hours", example = "24")
            @RequestParam(defaultValue = "24") Integer hours,

            @Parameter(description = "Include detailed breakdown", example = "false")
            @RequestParam(defaultValue = "false") Boolean detailed) {

        logger.debug("Fetching endpoint metrics for last {} hours, detailed: {}", hours, detailed);

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("timeframe", hours + " hours");
        metrics.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // Simulated endpoint metrics
        Map<String, Object> endpoints = Map.of(
                "/api/v1/aircraft", Map.of(
                        "totalRequests", 15420,
                        "averageResponseTime", 145,
                        "minResponseTime", 23,
                        "maxResponseTime", 1245,
                        "errorRate", 0.02,
                        "successRate", 99.98
                ),
                "/api/v1/aircraft/{id}", Map.of(
                        "totalRequests", 8965,
                        "averageResponseTime", 89,
                        "minResponseTime", 15,
                        "maxResponseTime", 567,
                        "errorRate", 0.01,
                        "successRate", 99.99
                ),
                "/api/v1/aircraft/compare", Map.of(
                        "totalRequests", 2341,
                        "averageResponseTime", 234,
                        "minResponseTime", 156,
                        "maxResponseTime", 890,
                        "errorRate", 0.05,
                        "successRate", 99.95
                )
        );

        metrics.put("endpoints", endpoints);

        if (detailed) {
            metrics.put("topSlowEndpoints", List.of(
                    Map.of("endpoint", "/api/v1/aircraft/compare", "avgResponseTime", 234),
                    Map.of("endpoint", "/api/v1/aircraft", "avgResponseTime", 145),
                    Map.of("endpoint", "/api/v1/aircraft/{id}", "avgResponseTime", 89)
            ));

            metrics.put("topErrorEndpoints", List.of(
                    Map.of("endpoint", "/api/v1/aircraft/compare", "errorRate", 0.05),
                    Map.of("endpoint", "/api/v1/aircraft", "errorRate", 0.02),
                    Map.of("endpoint", "/api/v1/aircraft/{id}", "errorRate", 0.01)
            ));
        }

        return ResponseEntity.ok(metrics);
    }

    @Operation(
            summary = "Get performance metrics",
            description = "Get overall system performance metrics"
    )
    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getPerformanceMetrics() {

        logger.debug("Fetching performance metrics");

        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        Map<String, Object> metrics = Map.of(
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),

                "memory", Map.of(
                        "totalMB", totalMemory / 1024 / 1024,
                        "usedMB", usedMemory / 1024 / 1024,
                        "freeMB", freeMemory / 1024 / 1024,
                        "usagePercent", Math.round((double) usedMemory / totalMemory * 100 * 100.0) / 100.0
                ),

                "cpu", Map.of(
                        "availableProcessors", runtime.availableProcessors(),
                        "systemLoadAverage", getSystemLoadAverage()
                ),

                "requests", Map.of(
                        "totalRequests", 26726,
                        "requestsPerSecond", 15.2,
                        "averageResponseTime", 156,
                        "p95ResponseTime", 450,
                        "p99ResponseTime", 890
                ),

                "database", Map.of(
                        "activeConnections", 8,
                        "maxConnections", 20,
                        "averageQueryTime", 23,
                        "connectionPoolUsage", 40.0
                ),

                "cache", Map.of(
                        "hitRatio", 0.87,
                        "missRatio", 0.13,
                        "evictions", 342,
                        "totalKeys", 1543
                )
        );

        return ResponseEntity.ok(metrics);
    }

    @Operation(
            summary = "Get error metrics",
            description = "Get error rates and error breakdown metrics"
    )
    @GetMapping("/errors")
    public ResponseEntity<Map<String, Object>> getErrorMetrics(
            @Parameter(description = "Time period in hours", example = "24")
            @RequestParam(defaultValue = "24") Integer hours) {

        logger.debug("Fetching error metrics for last {} hours", hours);

        Map<String, Object> metrics = Map.of(
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "timeframe", hours + " hours",

                "overview", Map.of(
                        "totalErrors", 45,
                        "errorRate", 0.17,
                        "totalRequests", 26726
                ),

                "byStatusCode", Map.of(
                        "400", 12,  // Bad Request
                        "404", 18,  // Not Found
                        "500", 8,   // Internal Server Error
                        "503", 7    // Service Unavailable
                ),

                "byEndpoint", Map.of(
                        "/api/v1/aircraft/compare", 15,
                        "/api/v1/aircraft", 12,
                        "/api/v1/search/suggest", 8,
                        "/api/v1/manufacturers", 5,
                        "others", 5
                ),

                "recentErrors", List.of(
                        Map.of(
                                "timestamp", LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                "endpoint", "/api/v1/aircraft/compare",
                                "status", 400,
                                "message", "Invalid aircraft IDs format",
                                "count", 3
                        ),
                        Map.of(
                                "timestamp", LocalDateTime.now().minusMinutes(15).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                "endpoint", "/api/v1/aircraft/999",
                                "status", 404,
                                "message", "Aircraft not found",
                                "count", 1
                        )
                ),

                "errorTrends", Map.of(
                        "lastHour", 8,
                        "last6Hours", 24,
                        "last24Hours", 45,
                        "trend", "stable"
                )
        );

        return ResponseEntity.ok(metrics);
    }

    @Operation(
            summary = "Get business metrics",
            description = "Get application-specific business metrics"
    )
    @GetMapping("/business")
    public ResponseEntity<Map<String, Object>> getBusinessMetrics(
            @Parameter(description = "Time period in hours", example = "24")
            @RequestParam(defaultValue = "24") Integer hours) {

        logger.debug("Fetching business metrics for last {} hours", hours);

        Map<String, Object> metrics = Map.of(
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "timeframe", hours + " hours",

                "aircraft", Map.of(
                        "totalViews", 8965,
                        "uniqueViews", 3421,
                        "popularAircraft", List.of("Boeing 737-800", "Airbus A320", "Boeing 777-300ER"),
                        "averageViewDuration", "2m 34s"
                ),

                "comparisons", Map.of(
                        "totalComparisons", 2341,
                        "uniqueUsers", 1876,
                        "averageAircraftPerComparison", 2.8,
                        "mostComparedPair", "Boeing 737-800 vs Airbus A320"
                ),

                "search", Map.of(
                        "totalSearches", 5432,
                        "uniqueSearchTerms", 1234,
                        "popularSearches", List.of("Boeing 737", "Airbus A320", "wide body", "777"),
                        "averageResultsPerSearch", 8.5,
                        "noResultSearches", 23
                ),

                "manufacturers", Map.of(
                        "mostViewedManufacturer", "Boeing",
                        "manufacturerViewDistribution", Map.of(
                                "Boeing", 54.2,
                                "Airbus", 43.8,
                                "Others", 2.0
                        )
                ),

                "performance", Map.of(
                        "cacheHitRatio", 87.3,
                        "databaseQueryAvg", "23ms",
                        "userSatisfactionScore", 4.2
                )
        );

        return ResponseEntity.ok(metrics);
    }

    @Operation(
            summary = "Get real-time metrics summary",
            description = "Get current real-time system metrics summary"
    )
    @GetMapping("/realtime")
    public ResponseEntity<Map<String, Object>> getRealtimeMetrics() {

        logger.debug("Fetching real-time metrics");

        Map<String, Object> metrics = Map.of(
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),

                "current", Map.of(
                        "activeUsers", 127,
                        "requestsPerMinute", 456,
                        "averageResponseTime", 156,
                        "errorRate", 0.02
                ),

                "lastMinute", Map.of(
                        "requests", 456,
                        "errors", 2,
                        "newUsers", 12,
                        "comparisons", 23
                ),

                "system", Map.of(
                        "memoryUsage", 67.8,
                        "cpuUsage", 23.4,
                        "diskUsage", 42.1,
                        "networkIn", "2.3 MB/s",
                        "networkOut", "4.1 MB/s"
                ),

                "alerts", List.of(
                        // No current alerts in simulation
                ),

                "topActivity", Map.of(
                        "mostViewedAircraft", "Boeing 737-800",
                        "mostActiveEndpoint", "/api/v1/aircraft",
                        "busiestHour", "14:00-15:00"
                )
        );

        return ResponseEntity.ok(metrics);
    }

    @Operation(
            summary = "Get custom metrics",
            description = "Get custom application metrics by category"
    )
    @GetMapping("/custom/{category}")
    public ResponseEntity<Map<String, Object>> getCustomMetrics(
            @Parameter(description = "Metrics category", example = "cache")
            @PathVariable String category,

            @Parameter(description = "Time period in hours", example = "24")
            @RequestParam(defaultValue = "24") Integer hours) {

        logger.debug("Fetching custom metrics for category: {} over {} hours", category, hours);

        Map<String, Object> baseResponse = Map.of(
                "category", category,
                "timeframe", hours + " hours",
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        Map<String, Object> categoryMetrics = switch (category.toLowerCase()) {
            case "cache" -> getCacheMetrics();
            case "database" -> getDatabaseMetrics();
            case "api" -> getApiMetrics();
            case "users" -> getUserMetrics();
            default -> Map.of("error", "Unknown category: " + category);
        };

        Map<String, Object> response = new HashMap<>(baseResponse);
        response.put("metrics", categoryMetrics);

        return ResponseEntity.ok(response);
    }

    // Private helper methods

    private static void initializeSampleMetrics() {
        // Initialize sample data for metrics
        endpointMetrics.put("initialized", true);
        performanceMetrics.put("initialized", true);
        errorMetrics.put("initialized", true);
        businessMetrics.put("initialized", true);
    }

    private double getSystemLoadAverage() {
        // Simulate system load average
        return Math.round(Math.random() * 2.0 * 100.0) / 100.0;
    }

    private Map<String, Object> getCacheMetrics() {
        return Map.of(
                "hitRatio", 87.3,
                "missRatio", 12.7,
                "totalKeys", 1543,
                "evictions", 342,
                "memoryUsage", "45.2 MB",
                "topCaches", List.of("aircraftCache", "manufacturerCache", "familyCache")
        );
    }

    private Map<String, Object> getDatabaseMetrics() {
        return Map.of(
                "activeConnections", 8,
                "maxConnections", 20,
                "averageQueryTime", "23ms",
                "slowQueries", 3,
                "connectionPoolUsage", 40.0,
                "deadlocks", 0
        );
    }

    private Map<String, Object> getApiMetrics() {
        return Map.of(
                "totalEndpoints", 53,
                "totalRequests", 26726,
                "averageResponseTime", 156,
                "errorRate", 0.17,
                "rateLimitHits", 12,
                "authenticationFailures", 5
        );
    }

    private Map<String, Object> getUserMetrics() {
        return Map.of(
                "activeUsers", 127,
                "newUsersToday", 43,
                "returningUsers", 84,
                "averageSessionDuration", "8m 42s",
                "bounceRate", 23.4,
                "topUserActions", List.of("view_aircraft", "compare_aircraft", "search")
        );
    }
}
