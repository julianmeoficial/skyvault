package com.skyvault.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Parámetros para búsqueda y sugerencias")
public class SearchRequestDto {

    @NotBlank(message = "Search query is required")
    @Size(min = 2, max = 100, message = "Search query must be between 2 and 100 characters")
    @Schema(description = "Término de búsqueda",
            example = "Boeing 737",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String query;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 20, message = "Limit cannot exceed 20")
    @Schema(description = "Número máximo de sugerencias",
            example = "10",
            defaultValue = "10")
    private Integer limit = 10;

    @Schema(description = "Tipo de búsqueda",
            example = "all",
            defaultValue = "all",
            allowableValues = {"all", "aircraft", "manufacturers", "families"})
    private String type = "all";

    @Schema(description = "Solo elementos activos",
            example = "true",
            defaultValue = "true")
    private Boolean onlyActive = true;

    // Constructors
    public SearchRequestDto() {}

    public SearchRequestDto(String query) {
        this.query = query;
    }

    public SearchRequestDto(String query, Integer limit) {
        this.query = query;
        this.limit = limit;
    }

    // Getters and Setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }
}
