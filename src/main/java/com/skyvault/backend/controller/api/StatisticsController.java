package com.skyvault.backend.controller.api;

import com.skyvault.backend.dto.request.StatisticsRequestDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics", description = "System statistics and metrics")
@Validated
@CrossOrigin(origins = {"${cors.allowed-origins}"})
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Operation(
            summary = "Get system statistics",
            description = "Retrieve comprehensive system statistics"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "System statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SystemStatisticsDto.class)))
    })
    @GetMapping("/system")
    public ResponseEntity<SystemStatisticsDto> getSystemStatistics() {

        logger.info("Fetching system statistics");

        SystemStatisticsDto statistics = statisticsService.getSystemStatistics();

        CacheControl cacheControl = CacheControl.maxAge(15, TimeUnit.MINUTES)
                .cachePublic()
                .mustRevalidate();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }

    @Operation(
            summary = "Get aircraft statistics",
            description = "Retrieve statistics specific to aircraft"
    )
    @GetMapping("/aircraft")
    public ResponseEntity<AircraftStatisticsDto> getAircraftStatistics() {

        logger.info("Fetching aircraft statistics");

        AircraftStatisticsDto statistics = statisticsService.getAircraftStatistics();

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }

    @Operation(
            summary = "Get manufacturer statistics",
            description = "Retrieve statistics specific to manufacturers"
    )
    @GetMapping("/manufacturers")
    public ResponseEntity<ManufacturerStatisticsDto> getManufacturerStatistics() {

        logger.info("Fetching manufacturer statistics");

        ManufacturerStatisticsDto statistics = statisticsService.getManufacturerStatistics();

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }

    @Operation(
            summary = "Get family statistics",
            description = "Retrieve statistics specific to aircraft families"
    )
    @GetMapping("/families")
    public ResponseEntity<FamilyStatisticsDto> getFamilyStatistics() {

        logger.info("Fetching family statistics");

        FamilyStatisticsDto statistics = statisticsService.getFamilyStatistics();

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }

    @Operation(
            summary = "Get search statistics",
            description = "Retrieve statistics about search behavior"
    )
    @GetMapping("/search")
    public ResponseEntity<SearchStatisticsDto> getSearchStatistics() {

        logger.info("Fetching search statistics");

        SearchStatisticsDto statistics = statisticsService.getSearchStatistics();

        CacheControl cacheControl = CacheControl.maxAge(15, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }

    @Operation(
            summary = "Get distribution by manufacturer",
            description = "Get aircraft distribution grouped by manufacturer"
    )
    @GetMapping("/distributions/manufacturers")
    public ResponseEntity<Map<String, Long>> getManufacturerDistribution() {

        logger.info("Fetching manufacturer distribution");

        Map<String, Long> distribution = statisticsService.getManufacturerDistribution();

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(distribution);
    }

    @Operation(
            summary = "Get distribution by type",
            description = "Get aircraft distribution grouped by type"
    )
    @GetMapping("/distributions/types")
    public ResponseEntity<Map<String, Long>> getTypeDistribution() {

        logger.info("Fetching type distribution");

        Map<String, Long> distribution = statisticsService.getTypeDistribution();

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(distribution);
    }

    @Operation(
            summary = "Get distribution by production state",
            description = "Get aircraft distribution grouped by production state"
    )
    @GetMapping("/distributions/production-states")
    public ResponseEntity<Map<String, Long>> getProductionStateDistribution() {

        logger.info("Fetching production state distribution");

        Map<String, Long> distribution = statisticsService.getProductionStateDistribution();

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(distribution);
    }

    @Operation(
            summary = "Get distribution by size category",
            description = "Get aircraft distribution grouped by size category"
    )
    @GetMapping("/distributions/size-categories")
    public ResponseEntity<Map<String, Long>> getSizeCategoryDistribution() {

        logger.info("Fetching size category distribution");

        Map<String, Long> distribution = statisticsService.getSizeCategoryDistribution();

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(distribution);
    }

    @Operation(
            summary = "Get distribution by introduction year",
            description = "Get aircraft distribution grouped by introduction year ranges"
    )
    @GetMapping("/distributions/introduction-years")
    public ResponseEntity<Map<String, Long>> getIntroductionYearDistribution() {

        logger.info("Fetching introduction year distribution");

        Map<String, Long> distribution = statisticsService.getIntroductionYearDistribution();

        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(distribution);
    }

    @Operation(
            summary = "Get usage statistics by date range",
            description = "Get system usage statistics for a specific period"
    )
    @GetMapping("/usage")
    public ResponseEntity<Map<LocalDate, Long>> getUsageStatistics(
            @Parameter(description = "Start date", example = "2024-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

            @Parameter(description = "End date", example = "2024-01-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        if (from.isAfter(to)) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Fetching usage statistics from {} to {}", from, to);

        Map<LocalDate, Long> usage = statisticsService.getUsageStatistics(from, to);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(usage);
    }

    @Operation(
            summary = "Get most searched aircraft",
            description = "Get aircraft ordered by search popularity"
    )
    @GetMapping("/popular/aircraft")
    public ResponseEntity<List<AircraftPopularityDto>> getMostSearchedAircraft(
            @Parameter(description = "Maximum results", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit) {

        logger.info("Fetching most searched aircraft, limit: {}", limit);

        List<AircraftPopularityDto> popular = statisticsService.getMostSearchedAircraft(limit);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(popular);
    }

    @Operation(
            summary = "Get most frequent comparisons",
            description = "Get aircraft comparisons ordered by frequency"
    )
    @GetMapping("/popular/comparisons")
    public ResponseEntity<List<ComparisonPopularityDto>> getMostFrequentComparisons(
            @Parameter(description = "Maximum results", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        logger.info("Fetching most frequent comparisons, limit: {}", limit);

        List<ComparisonPopularityDto> popular = statisticsService.getMostFrequentComparisons(limit);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(popular);
    }

    @Operation(
            summary = "Get custom statistics",
            description = "Get statistics with custom filters and parameters"
    )
    @PostMapping("/custom")
    public ResponseEntity<Object> getCustomStatistics(
            @Valid @RequestBody StatisticsRequestDto request) {

        logger.info("Fetching custom statistics: type={}, granularity={}, dateFrom={}, dateTo={}",
                request.getStatisticType(), request.getGranularity(),
                request.getDateFrom(), request.getDateTo());

        // This would return different types based on the request
        // For now, return system statistics as a fallback
        Object statistics = statisticsService.getSystemStatistics();

        return ResponseEntity.ok(statistics);
    }
}
