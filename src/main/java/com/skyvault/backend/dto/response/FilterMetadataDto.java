package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Metadatos de filtros aplicados")
public class FilterMetadataDto {

    @Schema(description = "Filtros aplicados")
    private Map<String, Object> applied;

    @Schema(description = "Número total de filtros activos", example = "3")
    private Integer activeCount;

    @Schema(description = "Se aplicaron filtros", example = "true")
    private Boolean hasFilters;

    @Schema(description = "Descripción legible de filtros", example = "Filtered by: Boeing, 150-300 passengers, Active only")
    private String description;

    // Constructors
    public FilterMetadataDto() {}

    // Getters and Setters
    public Map<String, Object> getApplied() {
        return applied;
    }

    public void setApplied(Map<String, Object> applied) {
        this.applied = applied;
    }

    public Integer getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }

    public Boolean getHasFilters() {
        return hasFilters;
    }

    public void setHasFilters(Boolean hasFilters) {
        this.hasFilters = hasFilters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
