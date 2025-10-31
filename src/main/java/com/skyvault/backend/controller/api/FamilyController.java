package com.skyvault.backend.controller.api;

import com.skyvault.backend.dto.request.FamilyFilterDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.service.FamilyService;
import com.skyvault.backend.service.AircraftService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/families")
@Tag(name = "Families", description = "Aircraft family operations")
@Validated
@CrossOrigin(origins = {"${cors.allowed-origins}"})
public class FamilyController {

    private static final Logger logger = LoggerFactory.getLogger(FamilyController.class);

    private final FamilyService familyService;
    private final AircraftService aircraftService;

    @Autowired
    public FamilyController(FamilyService familyService, AircraftService aircraftService) {
        this.familyService = familyService;
        this.aircraftService = aircraftService;
    }

    @Operation(
            summary = "Get families list",
            description = "Retrieve paginated list of aircraft families with optional filters"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Families retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PagedResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResponseDto<FamilyDto>> getFamilies(
            @Valid @ModelAttribute FamilyFilterDto filterDto) {

        logger.info("Fetching families with filters: manufacturerId={}, name={}, category={}",
                filterDto.getManufacturerId(), filterDto.getName(), filterDto.getCategory());

        PagedResponseDto<FamilyDto> response = familyService.findFamilies(filterDto);

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic()
                .mustRevalidate();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(response);
    }

    @Operation(
            summary = "Get family by ID",
            description = "Retrieve detailed information about a specific aircraft family"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Family found",
                    content = @Content(schema = @Schema(implementation = FamilyDto.class))),
            @ApiResponse(responseCode = "404", description = "Family not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FamilyDto> getFamilyById(
            @Parameter(description = "Family ID", example = "5")
            @PathVariable Long id) {

        logger.info("Fetching family details for ID: {}", id);

        return familyService.findById(id)
                .map(family -> {
                    CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                            .cachePublic();
                    return ResponseEntity.ok()
                            .cacheControl(cacheControl)
                            .body(family);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get family aircraft",
            description = "Get all aircraft models for a specific family"
    )
    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<AircraftCardDto>> getFamilyAircraft(
            @Parameter(description = "Family ID", example = "5")
            @PathVariable Long id,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) Integer page,

            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size) {

        if (!familyService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Fetching aircraft for family ID: {}", id);

        Pageable pageable = PageRequest.of(page, size);
        List<AircraftCardDto> aircraft = aircraftService.findByFamily(id, pageable);

        CacheControl cacheControl = CacheControl.maxAge(15, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(aircraft);
    }

    @Operation(
            summary = "Get family summary list",
            description = "Get simplified list of all families for dropdowns"
    )
    @GetMapping("/summary")
    public ResponseEntity<List<FamilySummaryDto>> getFamiliesSummary(
            @Parameter(description = "Manufacturer ID filter", example = "1")
            @RequestParam(required = false) Long manufacturerId) {

        logger.info("Fetching family summary for manufacturer: {}", manufacturerId);

        List<FamilySummaryDto> summary = (manufacturerId != null)
                ? familyService.findSummaryByManufacturer(manufacturerId)
                : familyService.findAllSummary();

        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(summary);
    }

    @Operation(
            summary = "Get available categories",
            description = "Get list of all family categories"
    )
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAvailableCategories() {

        logger.info("Fetching available categories");

        List<String> categories = familyService.getAvailableCategories();

        CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(categories);
    }

    @Operation(
            summary = "Get popular families",
            description = "Get families with the most aircraft models"
    )
    @GetMapping("/popular")
    public ResponseEntity<List<FamilyDto>> getPopularFamilies(
            @Parameter(description = "Maximum results", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        logger.info("Fetching popular families, limit: {}", limit);

        List<FamilyDto> popular = familyService.getPopularFamilies(limit);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(popular);
    }

    @Operation(
            summary = "Search families",
            description = "Search families by name or category"
    )
    @GetMapping("/search")
    public ResponseEntity<List<FamilySummaryDto>> searchFamilies(
            @Parameter(description = "Search query", example = "737")
            @RequestParam String q,

            @Parameter(description = "Maximum results", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        if (q == null || q.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Searching families with query: '{}', limit: {}", q, limit);

        List<FamilySummaryDto> results = familyService.searchByText(q.trim(), limit);

        return ResponseEntity.ok(results);
    }

    @Operation(
            summary = "Get family statistics",
            description = "Get statistics about families in the system"
    )
    @GetMapping("/statistics")
    public ResponseEntity<FamilyStatisticsDto> getFamilyStatistics() {

        logger.info("Fetching family statistics");

        FamilyStatisticsDto statistics = familyService.getStatistics();

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }
}
