package com.skyvault.backend.controller.api;

import com.skyvault.backend.exception.ProblemDetail;
import com.skyvault.backend.dto.request.AircraftFilterDto;
import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.request.SimilarAircraftRequestDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.service.AircraftService;
import com.skyvault.backend.service.ComparisonService;
import com.skyvault.backend.util.StringUtils;
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
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aircraft")
@Tag(name = "Aircraft", description = "Aircraft management and comparison operations")
@Validated
@CrossOrigin(origins = {"${cors.allowed-origins}"})
public class AircraftController {

    private static final Logger logger = LoggerFactory.getLogger(AircraftController.class);

    private final AircraftService aircraftService;
    private final ComparisonService comparisonService;

    @Autowired
    public AircraftController(AircraftService aircraftService, ComparisonService comparisonService) {
        this.aircraftService = aircraftService;
        this.comparisonService = comparisonService;
    }

    @Operation(
            summary = "Get aircraft list with advanced filters",
            description = "Retrieve paginated list of aircraft with combinable filters, sorting and search capabilities"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aircraft list retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PagedResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<PagedResponseDto<AircraftCardDto>> getAircraft(
            @Valid @ModelAttribute AircraftFilterDto filterDto) {

        logger.info("Fetching aircraft with filters: manufacturerId={}, familyId={}, searchTerm={}",
                filterDto.getManufacturerId(), filterDto.getFamilyId(), filterDto.getSearchTerm());

        PagedResponseDto<AircraftCardDto> response = aircraftService.findAircraft(filterDto);

        // Cache control for public data
        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES)
                .cachePublic()
                .mustRevalidate();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(response);
    }

    @Operation(
            summary = "Get aircraft by ID or slug",
            description = "Retrieve detailed information about a specific aircraft. Accepts numeric ID (ex: 51) or text slug (ex: a350-1000)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aircraft found",
                    content = @Content(schema = @Schema(implementation = AircraftDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "Aircraft not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{identifier}")
    public ResponseEntity<AircraftDetailDto> getAircraftById(
            @Parameter(description = "Aircraft ID (numeric) or slug (text). Examples: '51' or 'a350-1000'",
                    example = "51")
            @PathVariable String identifier) {

        logger.info("Fetching aircraft details for identifier: {}", identifier);

        Optional<AircraftDetailDto> aircraft;

        // Detectar si es ID numérico o slug textual
        if (identifier.matches("\\d+")) {
            // Es un número -> buscar por ID
            Long id = Long.parseLong(identifier);
            logger.debug("Identifier is numeric ID: {}", id);
            aircraft = aircraftService.findById(id);
        } else {
            // Es texto -> buscar por nombre/modelo (slug)
            logger.debug("Identifier is text slug: {}", identifier);
            aircraft = aircraftService.findByIdentifier(identifier);
        }

        return aircraft
                .map(dto -> {
                    CacheControl cacheControl = CacheControl.maxAge(15, TimeUnit.MINUTES)
                            .cachePublic();
                    return ResponseEntity.ok()
                            .cacheControl(cacheControl)
                            .body(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Compare aircraft",
            description = "Compare multiple aircraft side by side with detailed specifications"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comparison completed successfully",
                    content = @Content(schema = @Schema(implementation = CompareResultDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid aircraft IDs or comparison parameters",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/compare")
    public ResponseEntity<CompareResultDto> compareAircraft(
            @Parameter(description = "Comma-separated aircraft IDs", example = "1,5,10")
            @RequestParam @NotEmpty String ids,

            @Parameter(description = "Include technical specifications", example = "true")
            @RequestParam(defaultValue = "true") Boolean includeSpecifications,

            @Parameter(description = "Include images", example = "true")
            @RequestParam(defaultValue = "true") Boolean includeImages,

            @Parameter(description = "Normalize units", example = "true")
            @RequestParam(defaultValue = "true") Boolean normalizeUnits,

            @Parameter(description = "Unit format", example = "metric")
            @RequestParam(defaultValue = "metric") String unitFormat) {

        // Parse and validate IDs
        List<Long> aircraftIds;
        try {
            aircraftIds = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            logger.warn("Invalid aircraft IDs format: {}", ids);
            return ResponseEntity.badRequest().build();
        }

        if (aircraftIds.size() < 2 || aircraftIds.size() > 5) {
            logger.warn("Invalid number of aircraft for comparison: {}", aircraftIds.size());
            return ResponseEntity.badRequest().build();
        }

        logger.info("Comparing aircraft: {}", aircraftIds);

        CompareRequestDto compareRequest = new CompareRequestDto();
        compareRequest.setAircraftIds(aircraftIds);
        compareRequest.setIncludeSpecifications(includeSpecifications);
        compareRequest.setIncludeImages(includeImages);
        compareRequest.setNormalizeUnits(normalizeUnits);
        compareRequest.setUnitFormat(unitFormat);

        CompareResultDto result = comparisonService.compareAircraft(compareRequest);

        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(result);
    }

    @Operation(
            summary = "Compare aircraft (POST)",
            description = "Compare aircraft using POST request with detailed configuration"
    )
    @PostMapping("/compare")
    public ResponseEntity<CompareResultDto> compareAircraftPost(
            @Valid @RequestBody CompareRequestDto compareRequest) {

        logger.info("Comparing aircraft (POST): {}", compareRequest.getAircraftIds());

        CompareResultDto result = comparisonService.compareAircraft(compareRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Get similar aircraft",
            description = "Find aircraft similar to the specified one based on specifications"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Similar aircraft found",
                    content = @Content(schema = @Schema(implementation = AircraftCardDto.class))),
            @ApiResponse(responseCode = "404", description = "Aircraft not found")
    })
    @GetMapping("/{id}/similar")
    public ResponseEntity<List<AircraftCardDto>> getSimilarAircraft(
            @Parameter(description = "Aircraft ID", example = "15")
            @PathVariable Long id,

            @Parameter(description = "Passenger tolerance", example = "50")
            @RequestParam(defaultValue = "50") @Min(1) @Max(500) Integer passengerTolerance,

            @Parameter(description = "Range tolerance in km", example = "1000")
            @RequestParam(defaultValue = "1000") @Min(100) @Max(5000) Integer rangeTolerance,

            @Parameter(description = "Maximum results", example = "5")
            @RequestParam(defaultValue = "5") @Min(1) @Max(20) Integer limit) {

        if (!aircraftService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Finding similar aircraft for ID: {} with tolerance: passengers={}, range={}",
                id, passengerTolerance, rangeTolerance);

        SimilarAircraftRequestDto request = new SimilarAircraftRequestDto();
        request.setAircraftId(id);
        request.setPassengerTolerance(passengerTolerance);
        request.setRangeTolerance(rangeTolerance);
        request.setLimit(limit);
        request.setOnlyActive(true);

        List<AircraftCardDto> similarAircraft = aircraftService.findSimilarAircraft(request);

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(similarAircraft);
    }

    @Operation(
            summary = "Get popular aircraft",
            description = "Get most searched/viewed aircraft"
    )
    @GetMapping("/popular")
    public ResponseEntity<List<AircraftCardDto>> getPopularAircraft(
            @Parameter(description = "Maximum results", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        logger.info("Fetching popular aircraft, limit: {}", limit);

        List<AircraftCardDto> popular = aircraftService.getPopularAircraft(limit);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(popular);
    }

    @Operation(
            summary = "Get featured aircraft",
            description = "Get featured/highlighted aircraft"
    )
    @GetMapping("/featured")
    public ResponseEntity<List<AircraftCardDto>> getFeaturedAircraft(
            @Parameter(description = "Maximum results", example = "8")
            @RequestParam(defaultValue = "8") @Min(1) @Max(20) Integer limit) {

        logger.info("Fetching featured aircraft, limit: {}", limit);

        List<AircraftCardDto> featured = aircraftService.getFeaturedAircraft(limit);

        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(featured);
    }

    @Operation(
            summary = "Get newest aircraft",
            description = "Get most recently introduced aircraft"
    )
    @GetMapping("/newest")
    public ResponseEntity<List<AircraftCardDto>> getNewestAircraft(
            @Parameter(description = "Maximum results", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        logger.info("Fetching newest aircraft, limit: {}", limit);

        List<AircraftCardDto> newest = aircraftService.getNewestAircraft(limit);

        CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(newest);
    }

    @Operation(
            summary = "Get aircraft statistics",
            description = "Get general statistics about aircraft in the system"
    )
    @GetMapping("/statistics")
    public ResponseEntity<AircraftStatisticsDto> getAircraftStatistics() {

        logger.info("Fetching aircraft statistics");

        AircraftStatisticsDto statistics = aircraftService.getStatistics();

        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(statistics);
    }

    @Operation(
            summary = "Search aircraft by text",
            description = "Full-text search across aircraft names, models and manufacturers"
    )
    @GetMapping("/search")
    public ResponseEntity<List<AircraftCardDto>> searchAircraft(
            @Parameter(description = "Search query", example = "Boeing 737")
            @RequestParam String q,

            @Parameter(description = "Maximum results", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit) {

        if (StringUtils.isEmpty(q) || q.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Searching aircraft with query: '{}', limit: {}", q, limit);

        List<AircraftCardDto> results = aircraftService.searchByText(q.trim(), limit);

        return ResponseEntity.ok(results);
    }
}
