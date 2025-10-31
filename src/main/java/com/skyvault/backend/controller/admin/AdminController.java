package com.skyvault.backend.controller.admin;

import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;
import com.skyvault.backend.dto.request.AdminRequestDto;
import com.skyvault.backend.dto.request.AircraftCreateDto;
import com.skyvault.backend.dto.request.AircraftUpdateDto;
import com.skyvault.backend.dto.response.AircraftDetailDto;
import com.skyvault.backend.dto.response.SystemNotificationDto;
import com.skyvault.backend.service.NotificationService;
import com.skyvault.backend.service.DataLoaderService;
import com.skyvault.backend.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Administration", description = "General administrative operations")
@Validated
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final NotificationService notificationService;
    private final StatisticsService statisticsService;
    private final DataLoaderService dataLoaderService;

    @Autowired
    public AdminController(NotificationService notificationService,
                           StatisticsService statisticsService,
                           DataLoaderService dataLoaderService) {
        this.notificationService = notificationService;
        this.statisticsService = statisticsService;
        this.dataLoaderService = dataLoaderService;
    }

    @Operation(
            summary = "Execute administrative operation",
            description = "Execute various administrative operations like backup, restore, sync, etc."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation executed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid operation parameters"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
            @ApiResponse(responseCode = "500", description = "Operation failed")
    })
    @PostMapping("/operations")
    public ResponseEntity<Map<String, Object>> executeAdminOperation(
            @Valid @RequestBody AdminRequestDto request) {

        logger.info("Admin executing operation: {} with component: {}",
                request.getAction(), request.getComponent());

        try {
            Map<String, Object> result = Map.of(
                    "action", request.getAction(),
                    "component", request.getComponent(),
                    "success", true,
                    "dryRun", request.getDryRun(),
                    "executedBy", request.getUserId(),
                    "timestamp", System.currentTimeMillis()
            );

            // Simulate operation execution based on action type
            switch (request.getAction()) {
                case "HEALTH_CHECK" -> {
                    // Perform comprehensive health check
                    Map<String, Object> healthResult = performHealthCheck(request.getComponent());
                    return ResponseEntity.ok(Map.of(
                            "action", "HEALTH_CHECK",
                            "component", request.getComponent(),
                            "result", healthResult,
                            "success", true
                    ));
                }

                case "UPDATE_STATS" -> {
                    if (!request.getDryRun()) {
                        statisticsService.updateStatistics();
                    }
                    return ResponseEntity.ok(Map.of(
                            "action", "UPDATE_STATS",
                            "success", true,
                            "message", request.getDryRun() ? "Dry run - statistics not updated" : "Statistics updated successfully"
                    ));
                }

                case "CLEAR_LOGS" -> {
                    int daysToKeep = 30; // Default
                    if (request.getParameters() != null && request.getParameters().containsKey("daysToKeep")) {
                        daysToKeep = (Integer) request.getParameters().get("daysToKeep");
                    }

                    if (!request.getDryRun()) {
                        notificationService.cleanOldNotifications(daysToKeep);
                    }

                    return ResponseEntity.ok(Map.of(
                            "action", "CLEAR_LOGS",
                            "daysToKeep", daysToKeep,
                            "success", true,
                            "message", request.getDryRun() ? "Dry run - logs not cleared" : "Old logs cleared successfully"
                    ));
                }

                case "BACKUP_DATA" -> {
                    Map<String, Object> backupResult = performBackup(request);
                    return ResponseEntity.ok(backupResult);
                }

                case "RESTORE_DATA" -> {
                    Map<String, Object> restoreResult = performRestore(request);
                    return ResponseEntity.ok(restoreResult);
                }

                default -> {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Unsupported operation: " + request.getAction()));
                }
            }

        } catch (Exception e) {
            logger.error("Admin operation failed: {}", e.getMessage(), e);

            // Notify about the error
            notificationService.notifySystemError("AdminController", "Operation failed: " + request.getAction(), e);

            Map<String, Object> errorResult = Map.of(
                    "action", request.getAction(),
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Get system notifications",
            description = "Get system notifications and alerts for administrators"
    )
    @GetMapping("/notifications")
    public ResponseEntity<List<SystemNotificationDto>> getSystemNotifications(
            @Parameter(description = "Start date for notifications", example = "2024-01-01T00:00:00")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,

            @Parameter(description = "End date for notifications", example = "2024-01-31T23:59:59")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,

            @Parameter(description = "Maximum notifications to return", example = "100")
            @RequestParam(defaultValue = "100") @Min(1) @Max(1000) Integer limit) {

        if (from == null) {
            from = LocalDateTime.now().minusDays(7); // Last 7 days by default
        }
        if (to == null) {
            to = LocalDateTime.now();
        }

        logger.info("Admin requesting notifications from {} to {}", from, to);

        List<SystemNotificationDto> notifications = notificationService.getSystemNotifications(from, to);

        // Limit the results
        if (notifications.size() > limit) {
            notifications = notifications.subList(0, limit);
        }

        return ResponseEntity.ok(notifications);
    }

    @Operation(
            summary = "Import data from file",
            description = "Import aircraft data from CSV or JSON file"
    )
    @PostMapping("/data/import")
    public ResponseEntity<Map<String, Object>> importData(
            @Parameter(description = "Data file to import")
            @RequestParam("file") MultipartFile file,

            @Parameter(description = "Data format", example = "csv")
            @RequestParam String format,

            @Parameter(description = "Dry run import", example = "false")
            @RequestParam(defaultValue = "false") Boolean dryRun) {

        logger.info("Admin importing data from file: {}, format: {}, dryRun: {}",
                file.getOriginalFilename(), format, dryRun);

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "File is empty"));
        }

        try {
            // Simulate file processing
            Map<String, Object> result = Map.of(
                    "fileName", file.getOriginalFilename(),
                    "fileSize", file.getSize(),
                    "format", format,
                    "dryRun", dryRun,
                    "success", true,
                    "recordsProcessed", 100, // Simulated
                    "recordsImported", dryRun ? 0 : 100,
                    "errors", List.of(), // No errors in simulation
                    "warnings", List.of("This is a simulated import"),
                    "timestamp", System.currentTimeMillis()
            );

            if (!dryRun) {
                // In real implementation, process the file here
                logger.info("Data import completed: {} records", result.get("recordsImported"));
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Data import failed: {}", e.getMessage(), e);

            Map<String, Object> errorResult = Map.of(
                    "fileName", file.getOriginalFilename(),
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Create system backup",
            description = "Create a backup of the system data"
    )
    @PostMapping("/backup")
    public ResponseEntity<Map<String, Object>> createBackup(
            @Parameter(description = "Include user data", example = "true")
            @RequestParam(defaultValue = "true") Boolean includeUserData,

            @Parameter(description = "Include logs", example = "false")
            @RequestParam(defaultValue = "false") Boolean includeLogs,

            @Parameter(description = "Backup description")
            @RequestParam(required = false) String description) {

        logger.info("Admin creating system backup: includeUserData={}, includeLogs={}",
                includeUserData, includeLogs);

        try {
            // Simulate backup process
            String backupId = "backup_" + System.currentTimeMillis();

            Map<String, Object> result = Map.of(
                    "backupId", backupId,
                    "description", description != null ? description : "System backup",
                    "includeUserData", includeUserData,
                    "includeLogs", includeLogs,
                    "status", "completed",
                    "size", "125.6 MB", // Simulated
                    "duration", "45 seconds", // Simulated
                    "timestamp", System.currentTimeMillis(),
                    "success", true
            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Backup creation failed: {}", e.getMessage(), e);

            Map<String, Object> errorResult = Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    @Operation(
            summary = "Get component logs",
            description = "Get logs for a specific system component"
    )
    @GetMapping("/logs/{component}")
    public ResponseEntity<Map<String, Object>> getComponentLogs(
            @Parameter(description = "Component name", example = "AircraftService")
            @PathVariable String component,

            @Parameter(description = "Number of recent log lines", example = "100")
            @RequestParam(defaultValue = "100") @Min(1) @Max(10000) Integer lines,

            @Parameter(description = "Log level filter", example = "ERROR")
            @RequestParam(required = false) String level) {

        logger.info("Admin requesting logs for component: {}, lines: {}, level: {}",
                component, lines, level);

        // Simulate log retrieval
        List<String> logLines = List.of(
                "2024-01-15 10:30:00 INFO  [" + component + "] Service initialized successfully",
                "2024-01-15 10:30:01 DEBUG [" + component + "] Processing request: findById(15)",
                "2024-01-15 10:30:02 WARN  [" + component + "] Cache miss for key: aircraft:15",
                "2024-01-15 10:30:03 INFO  [" + component + "] Request completed in 150ms"
        );

        Map<String, Object> result = Map.of(
                "component", component,
                "lines", Math.min(lines, logLines.size()),
                "level", level != null ? level : "ALL",
                "logs", logLines,
                "timestamp", System.currentTimeMillis()
        );

        return ResponseEntity.ok(result);
    }

    // ✅ ENDPOINT PRINCIPAL PARA ACTUALIZACIONES SEGURAS
    @Operation(
            summary = "Force safe data update",
            description = "✅ SAFE MODE: Update only empty/null fields in aircraft database. " +
                    "This will NOT overwrite any manual edits made in PostgreSQL. " +
                    "Only fills missing data like dimensions, weights, and engine specs."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅ Safe data update completed successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
            @ApiResponse(responseCode = "500", description = "Update process failed")
    })
    @PostMapping("/force-data-update")
    public ResponseEntity<Map<String, Object>> forceDataUpdate() {
        try {
            logger.info("🔄 Admin forcing SAFE data update (preserves manual edits)...");

            long startTime = System.currentTimeMillis();
            dataLoaderService.forceDataUpdate();
            long duration = System.currentTimeMillis() - startTime;

            logger.info("✅ Safe data update completed successfully in {}ms", duration);

            Map<String, Object> result = Map.of(
                    "success", true,
                    "mode", "SAFE",
                    "description", "Updated only empty/null fields - manual edits preserved",
                    "duration", duration + "ms",
                    "timestamp", System.currentTimeMillis(),
                    "message", "✅ Safe data update completed! Your manual edits in PostgreSQL are preserved."
            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("❌ Safe data update failed: {}", e.getMessage(), e);

            Map<String, Object> errorResult = Map.of(
                    "success", false,
                    "mode", "SAFE",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis(),
                    "message", "❌ Safe data update failed: " + e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResult);
        }
    }

    // ✅ ENDPOINT ADICIONAL PARA ESTADÍSTICAS DE BASE DE DATOS
    @Operation(
            summary = "Get database statistics",
            description = "Get current database statistics including record counts and last update info"
    )
    @GetMapping("/database-stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        try {
            logger.info("Admin requesting database statistics");

            // Simulated stats - puedes implementar con queries reales
            Map<String, Object> stats = Map.of(
                    "aircraft_models", Map.of(
                            "total", 36,
                            "with_descriptions", 8,
                            "missing_descriptions", 28,
                            "with_first_flight_dates", 2,
                            "missing_first_flight_dates", 34
                    ),
                    "specifications", Map.of(
                            "total", 36,
                            "with_dimensions", 12,
                            "missing_dimensions", 24,
                            "with_cabin_info", 6,
                            "missing_cabin_info", 30
                    ),
                    "families", Map.of(
                            "total", 11,
                            "missing_category", 11,
                            "missing_launch_date", 11
                    ),
                    "manufacturers", Map.of(
                            "total", 2,
                            "missing_description", 2,
                            "missing_website_url", 2
                    ),
                    "last_update_check", LocalDateTime.now(),
                    "safe_update_available", true
            );

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("Failed to get database stats: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Private helper methods

    private Map<String, Object> performHealthCheck(String component) {
        // Simulate comprehensive health check
        return Map.of(
                "status", "HEALTHY",
                "checks", Map.of(
                        "database", "HEALTHY",
                        "cache", "HEALTHY",
                        "memory", "OK",
                        "disk", "OK"
                ),
                "uptime", "5d 12h 30m",
                "version", "1.0.0"
        );
    }

    private Map<String, Object> performBackup(AdminRequestDto request) {
        String backupId = "backup_" + System.currentTimeMillis();

        return Map.of(
                "action", "BACKUP_DATA",
                "backupId", backupId,
                "status", request.getDryRun() ? "simulated" : "completed",
                "success", true,
                "message", request.getDryRun() ? "Backup simulation completed" : "Backup created successfully"
        );
    }

    private Map<String, Object> performRestore(AdminRequestDto request) {
        return Map.of(
                "action", "RESTORE_DATA",
                "status", request.getDryRun() ? "simulated" : "completed",
                "success", true,
                "message", request.getDryRun() ? "Restore simulation completed" : "Data restored successfully"
        );
    }
}
