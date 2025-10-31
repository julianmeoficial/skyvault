package com.skyvault.backend.controller.api;

import com.skyvault.backend.dto.request.ManufacturerFilterDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.service.ManufacturerService;
import com.skyvault.backend.service.AircraftService;
import com.skyvault.backend.service.FamilyService;
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
@RequestMapping("/api/v1/manufacturers")
@Tag(name = "ManufacturersPage", description = "Aircraft manufacturer operations")
@Validated
@CrossOrigin(origins = {"${cors.allowed-origins}"})
public class ManufacturerController {

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    private final ManufacturerService manufacturerService;
    private final AircraftService aircraftService;
    private final FamilyService familyService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService,
                                  AircraftService aircraftService,
                                  FamilyService familyService) {
        this.manufacturerService = manufacturerService;
        this.aircraftService = aircraftService;
        this.familyService = familyService;
    }

    @Operation(
            summary = "Get manufacturers list",
            description = "Retrieve paginated list of manufacturers with optional filters"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ManufacturersPage retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PagedResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResponseDto<ManufacturerDto>> getManufacturers(
            @Valid @ModelAttribute ManufacturerFilterDto filterDto) {

        logger.info("Fetching manufacturers with filters: name={}, country={}",
                filterDto.getName(), filterDto.getCountry());

        PagedResponseDto<ManufacturerDto> response = manufacturerService.findManufacturers(filterDto);

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic()
                .mustRevalidate();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(response);
    }

    @Operation(
            summary = "Get manufacturer by ID",
            description = "Retrieve detailed information about a specific manufacturer"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manufacturer found",
                    content = @Content(schema = @Schema(implementation = ManufacturerDto.class))),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDto> getManufacturerById(
            @Parameter(description = "Manufacturer ID", example = "1")
            @PathVariable Long id) {

        logger.info("Fetching manufacturer details for ID: {}", id);

        return manufacturerService.findById(id)
                .map(manufacturer -> {
                    CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                            .cachePublic();
                    return ResponseEntity.ok()
                            .cacheControl(cacheControl)
                            .body(manufacturer);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get manufacturer families",
            description = "Get all aircraft families for a specific manufacturer"
    )
    @GetMapping("/{id}/families")
    public ResponseEntity<List<FamilyDto>> getManufacturerFamilies(
            @Parameter(description = "Manufacturer ID", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) Integer page,

            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size) {

        if (!manufacturerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Fetching families for manufacturer ID: {}", id);

        Pageable pageable = PageRequest.of(page, size);
        List<FamilyDto> families = familyService.findByManufacturer(id, pageable);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(families);
    }

    @Operation(
            summary = "Get manufacturer aircraft",
            description = "Get all aircraft for a specific manufacturer"
    )
    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<AircraftCardDto>> getManufacturerAircraft(
            @Parameter(description = "Manufacturer ID", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) Integer page,

            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size) {

        if (!manufacturerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Fetching aircraft for manufacturer ID: {}", id);

        Pageable pageable = PageRequest.of(page, size);
        List<AircraftCardDto> aircraft = aircraftService.findByManufacturer(id, pageable);

        CacheControl cacheControl = CacheControl.maxAge(15, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(aircraft);
    }

    @Operation(
            summary = "Get manufacturer summary list",
            description = "Get simplified list of all manufacturers for dropdowns"
    )
    @GetMapping("/summary")
    public ResponseEntity<List<ManufacturerSummaryDto>> getManufacturersSummary(
            @Parameter(description = "Only active manufacturers", example = "true")
            @RequestParam(defaultValue = "true") Boolean onlyActive) {

        logger.info("Fetching manufacturer summary, onlyActive: {}", onlyActive);

        List<ManufacturerSummaryDto> summary = onlyActive
                ? manufacturerService.findActiveSummary()
                : manufacturerService.findAllSummary();

        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(summary);
    }

    @Operation(
            summary = "Get available countries",
            description = "Get list of all countries that have manufacturers"
    )
    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAvailableCountries() {

        logger.info("Fetching available countries");

        List<String> countries = manufacturerService.getAvailableCountries();

        CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(countries);
    }

    @Operation(
            summary = "Search manufacturers",
            description = "Search manufacturers by name or country"
    )
    @GetMapping("/search")
    public ResponseEntity<List<ManufacturerSummaryDto>> searchManufacturers(
            @Parameter(description = "Search query", example = "Boeing")
            @RequestParam String q,

            @Parameter(description = "Maximum results", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        if (q == null || q.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Searching manufacturers with query: '{}', limit: {}", q, limit);

        List<ManufacturerSummaryDto> results = manufacturerService.searchByText(q.trim(), limit);

        return ResponseEntity.ok(results);
    }

    @Operation(
            summary = "Get manufacturer statistics",
            description = "Get statistics about manufacturers in the system"
    )
    @GetMapping("/statistics")
    public ResponseEntity<ManufacturerStatisticsDto> getManufacturerStatistics() {

        logger.info("Fetching manufacturer statistics");

        ManufacturerStatisticsDto statistics = manufacturerService.getStatistics();

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }
}
