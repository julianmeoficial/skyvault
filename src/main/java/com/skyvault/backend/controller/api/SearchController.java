package com.skyvault.backend.controller.api;

import com.skyvault.backend.dto.request.SearchRequestDto;
import com.skyvault.backend.dto.response.GlobalSearchResultDto;
import com.skyvault.backend.dto.response.SearchSuggestionDto;
import com.skyvault.backend.service.SearchService;
import com.skyvault.backend.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
@RequestMapping("/api/v1/search")
@Tag(name = "Search", description = "Search and suggestion operations")
@Validated
@CrossOrigin(origins = {"${cors.allowed-origins}"})
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(
            summary = "Get search suggestions",
            description = "Get intelligent suggestions for aircraft, manufacturers and families"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suggestions retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SearchSuggestionDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/suggest")
    public ResponseEntity<List<SearchSuggestionDto>> getSuggestions(
            @Parameter(description = "Search query", example = "boeing")
            @RequestParam String q,

            @Parameter(description = "Maximum suggestions", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(20) Integer limit,

            @Parameter(description = "Search type filter", example = "all")
            @RequestParam(defaultValue = "all")
            @Pattern(regexp = "^(all|aircraft|manufacturers|families)$") String type,

            @Parameter(description = "Only active items", example = "true")
            @RequestParam(defaultValue = "true") Boolean onlyActive) {

        if (StringUtils.isEmpty(q) || q.trim().length() < 2) {
            logger.warn("Search query too short: '{}'", q);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Getting suggestions for query: '{}', type: {}, limit: {}", q, type, limit);

        SearchRequestDto request = new SearchRequestDto();
        request.setQuery(q.trim());
        request.setLimit(limit);
        request.setType(type);
        request.setOnlyActive(onlyActive);

        List<SearchSuggestionDto> suggestions = searchService.getSuggestions(request);

        // Record search term for statistics
        searchService.recordSearchTerm(q.trim());

        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(suggestions);
    }

    @Operation(
            summary = "Global search",
            description = "Perform comprehensive search across all entities"
    )
    @GetMapping("/global")
    public ResponseEntity<GlobalSearchResultDto> globalSearch(
            @Parameter(description = "Search query", example = "737")
            @RequestParam String q,

            @Parameter(description = "Maximum results per type", example = "5")
            @RequestParam(defaultValue = "5") @Min(1) @Max(20) Integer limit) {

        if (StringUtils.isEmpty(q) || q.trim().length() < 2) {
            logger.warn("Search query too short for global search: '{}'", q);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Performing global search for query: '{}', limit: {}", q, limit);

        GlobalSearchResultDto results = searchService.globalSearch(q.trim(), limit);

        // Record search term
        searchService.recordSearchTerm(q.trim());

        return ResponseEntity.ok(results);
    }

    @Operation(
            summary = "Get aircraft suggestions",
            description = "Get suggestions specifically for aircraft"
    )
    @GetMapping("/aircraft")
    public ResponseEntity<List<SearchSuggestionDto>> getAircraftSuggestions(
            @Parameter(description = "Search query", example = "boeing")
            @RequestParam String q,

            @Parameter(description = "Maximum suggestions", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(20) Integer limit) {

        if (StringUtils.isEmpty(q) || q.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Getting aircraft suggestions for query: '{}', limit: {}", q, limit);

        List<SearchSuggestionDto> suggestions = searchService.getAircraftSuggestions(q.trim(), limit);

        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(suggestions);
    }

    @Operation(
            summary = "Get manufacturer suggestions",
            description = "Get suggestions specifically for manufacturers"
    )
    @GetMapping("/manufacturers")
    public ResponseEntity<List<SearchSuggestionDto>> getManufacturerSuggestions(
            @Parameter(description = "Search query", example = "boeing")
            @RequestParam String q,

            @Parameter(description = "Maximum suggestions", example = "5")
            @RequestParam(defaultValue = "5") @Min(1) @Max(10) Integer limit) {

        if (StringUtils.isEmpty(q) || q.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Getting manufacturer suggestions for query: '{}', limit: {}", q, limit);

        List<SearchSuggestionDto> suggestions = searchService.getManufacturerSuggestions(q.trim(), limit);

        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(suggestions);
    }

    @Operation(
            summary = "Get family suggestions",
            description = "Get suggestions specifically for aircraft families"
    )
    @GetMapping("/families")
    public ResponseEntity<List<SearchSuggestionDto>> getFamilySuggestions(
            @Parameter(description = "Search query", example = "737")
            @RequestParam String q,

            @Parameter(description = "Maximum suggestions", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(20) Integer limit) {

        if (StringUtils.isEmpty(q) || q.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Getting family suggestions for query: '{}', limit: {}", q, limit);

        List<SearchSuggestionDto> suggestions = searchService.getFamilySuggestions(q.trim(), limit);

        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.MINUTES)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(suggestions);
    }

    @Operation(
            summary = "Get popular search terms",
            description = "Get most frequently searched terms"
    )
    @GetMapping("/popular-terms")
    public ResponseEntity<List<String>> getPopularSearchTerms(
            @Parameter(description = "Maximum terms", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit) {

        logger.info("Fetching popular search terms, limit: {}", limit);

        List<String> popularTerms = searchService.getPopularSearchTerms(limit);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS)
                .cachePublic();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(popularTerms);
    }

    @Operation(
            summary = "Advanced search with POST",
            description = "Perform advanced search with detailed configuration"
    )
    @PostMapping("/advanced")
    public ResponseEntity<List<SearchSuggestionDto>> advancedSearch(
            @Valid @RequestBody SearchRequestDto searchRequest) {

        logger.info("Performing advanced search: query='{}', type={}, limit={}",
                searchRequest.getQuery(), searchRequest.getType(), searchRequest.getLimit());

        List<SearchSuggestionDto> results = searchService.getSuggestions(searchRequest);

        // Record search term
        searchService.recordSearchTerm(searchRequest.getQuery());

        return ResponseEntity.ok(results);
    }

    @Operation(
            summary = "Record search term",
            description = "Record a search term for analytics (internal use)"
    )
    @PostMapping("/record")
    public ResponseEntity<Void> recordSearchTerm(
            @Parameter(description = "Search term to record")
            @RequestParam String term) {

        if (StringUtils.isNotEmpty(term) && term.trim().length() >= 2) {
            logger.debug("Recording search term: '{}'", term);
            searchService.recordSearchTerm(term.trim());
        }

        return ResponseEntity.ok().build();
    }
}
