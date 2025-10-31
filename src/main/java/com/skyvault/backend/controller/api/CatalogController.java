package com.skyvault.backend.controller.api;

import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/catalogs")
@Tag(name = "Catalogs", description = "System catalogs operations")
@Validated
@CrossOrigin(origins = {"${cors.allowed-origins}"})
public class CatalogController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Operation(
            summary = "Get all catalogs",
            description = "Retrieve all system catalogs (types, production states, size categories)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catalogs retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CatalogSummaryDto.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<CatalogSummaryDto> getAllCatalogs() {

        logger.info("Fetching all catalogs");

        CatalogSummaryDto catalogs = catalogService.getAllCatalogs();

        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.HOURS)
                .cachePublic()
                .mustRevalidate();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(catalogs);
    }

    @Operation(
            summary = "Get aircraft types",
            description = "Retrieve all aircraft types"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aircraft types retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TypeDto.class))))
    })
    @GetMapping("/types")
    public ResponseEntity<List<TypeDto>> getAircraftTypes() {

        logger.info("Fetching aircraft types");

        List<TypeDto> types = catalogService.getAllTypes();

        CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(types);
    }

    @Operation(
            summary = "Get aircraft type by ID",
            description = "Retrieve specific aircraft type by ID"
    )
    @GetMapping("/types/{id}")
    public ResponseEntity<TypeDto> getAircraftTypeById(
            @Parameter(description = "Type ID", example = "2")
            @PathVariable Long id) {

        logger.info("Fetching aircraft type for ID: {}", id);

        return catalogService.getTypeById(id)
                .map(type -> {
                    CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                            .cachePublic();
                    return ResponseEntity.ok()
                            .cacheControl(cacheControl)
                            .body(type);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get production states",
            description = "Retrieve all production states"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Production states retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductionStateDto.class))))
    })
    @GetMapping("/production-states")
    public ResponseEntity<List<ProductionStateDto>> getProductionStates(
            @Parameter(description = "Only active states", example = "false")
            @RequestParam(defaultValue = "false") Boolean onlyActive) {

        logger.info("Fetching production states, onlyActive: {}", onlyActive);

        List<ProductionStateDto> states = onlyActive
                ? catalogService.getActiveProductionStates()
                : catalogService.getAllProductionStates();

        CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(states);
    }

    @Operation(
            summary = "Get production state by ID",
            description = "Retrieve specific production state by ID"
    )
    @GetMapping("/production-states/{id}")
    public ResponseEntity<ProductionStateDto> getProductionStateById(
            @Parameter(description = "Production state ID", example = "3")
            @PathVariable Long id) {

        logger.info("Fetching production state for ID: {}", id);

        return catalogService.getProductionStateById(id)
                .map(state -> {
                    CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                            .cachePublic();
                    return ResponseEntity.ok()
                            .cacheControl(cacheControl)
                            .body(state);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get size categories",
            description = "Retrieve all aircraft size categories"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Size categories retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SizeCategoryDto.class))))
    })
    @GetMapping("/size-categories")
    public ResponseEntity<List<SizeCategoryDto>> getSizeCategories() {

        logger.info("Fetching size categories");

        List<SizeCategoryDto> categories = catalogService.getAllSizeCategories();

        CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(categories);
    }

    @Operation(
            summary = "Get size category by ID",
            description = "Retrieve specific size category by ID"
    )
    @GetMapping("/size-categories/{id}")
    public ResponseEntity<SizeCategoryDto> getSizeCategoryById(
            @Parameter(description = "Size category ID", example = "4")
            @PathVariable Long id) {

        logger.info("Fetching size category for ID: {}", id);

        return catalogService.getSizeCategoryById(id)
                .map(category -> {
                    CacheControl cacheControl = CacheControl.maxAge(4, TimeUnit.HOURS)
                            .cachePublic();
                    return ResponseEntity.ok()
                            .cacheControl(cacheControl)
                            .body(category);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Determine size category",
            description = "Get the appropriate size category for a given passenger count"
    )
    @GetMapping("/size-categories/determine")
    public ResponseEntity<SizeCategoryDto> determineSizeCategory(
            @Parameter(description = "Number of passengers", example = "180")
            @RequestParam @Min(1) @Max(1000) Integer passengers) {

        logger.info("Determining size category for {} passengers", passengers);

        SizeCategoryDto category = catalogService.determineSizeCategory(passengers);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(category);
    }
}
