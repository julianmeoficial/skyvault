package com.skyvault.backend.dto.request;

import com.skyvault.backend.validation.ValidPagination;
import com.skyvault.backend.validation.ValidSortField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "Filtros para búsqueda de fabricantes")
@ValidPagination(maxPageSize = 50)
public class ManufacturerFilterDto {

    @Size(min = 1, max = 100, message = "Name filter must be between 1 and 100 characters")
    @Schema(description = "Filtro por nombre del fabricante", example = "Boeing")
    private String name;

    @Size(min = 1, max = 100, message = "Country filter must be between 1 and 100 characters")
    @Schema(description = "Filtro por país", example = "United States")
    private String country;

    @Size(min = 2, max = 100, message = "Search term must be between 2 and 100 characters")
    @Schema(description = "Término de búsqueda general", example = "Airbus")
    private String searchTerm;

    @Schema(description = "Solo fabricantes con aeronaves activas", example = "true")
    private Boolean onlyWithActiveAircraft;

    @Schema(description = "Solo fabricantes con familias", example = "false")
    private Boolean onlyWithFamilies;

    // Paginación
    @Min(value = 0, message = "Page number cannot be negative")
    @Schema(description = "Número de página", example = "0", defaultValue = "0")
    private Integer page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 50, message = "Page size cannot exceed 50")
    @Schema(description = "Tamaño de página", example = "10", defaultValue = "10")
    private Integer size = 10;

    // Ordenamiento
    @ValidSortField(allowedFields = {"id", "name", "country", "foundedDate", "createdAt", "updatedAt"})
    @Schema(description = "Campo de ordenamiento", example = "name", defaultValue = "name")
    private String sortField = "name";

    @Schema(description = "Dirección de ordenamiento", example = "ASC", defaultValue = "ASC")
    private String sortDirection = "ASC";

    // Constructors
    public ManufacturerFilterDto() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Boolean getOnlyWithActiveAircraft() {
        return onlyWithActiveAircraft;
    }

    public void setOnlyWithActiveAircraft(Boolean onlyWithActiveAircraft) {
        this.onlyWithActiveAircraft = onlyWithActiveAircraft;
    }

    public Boolean getOnlyWithFamilies() {
        return onlyWithFamilies;
    }

    public void setOnlyWithFamilies(Boolean onlyWithFamilies) {
        this.onlyWithFamilies = onlyWithFamilies;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
