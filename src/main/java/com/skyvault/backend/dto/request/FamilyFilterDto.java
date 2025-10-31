package com.skyvault.backend.dto.request;

import com.skyvault.backend.validation.ValidIds;
import com.skyvault.backend.validation.ValidPagination;
import com.skyvault.backend.validation.ValidSortField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Filtros para búsqueda de familias de aeronaves")
@ValidPagination(maxPageSize = 100)
public class FamilyFilterDto {

    @Schema(description = "ID del fabricante", example = "1")
    private Long manufacturerId;

    @ValidIds(max = 10, allowEmpty = true)
    @Schema(description = "Lista de IDs de fabricantes", example = "[1, 2]")
    private List<Long> manufacturerIds;

    @Size(min = 1, max = 100, message = "Name filter must be between 1 and 100 characters")
    @Schema(description = "Filtro por nombre de familia", example = "A320")
    private String name;

    @Size(min = 1, max = 50, message = "Category filter must be between 1 and 50 characters")
    @Schema(description = "Filtro por categoría", example = "Narrow-body")
    private String category;

    @Schema(description = "Lista de categorías", example = "[\"Narrow-body\", \"Wide-body\"]")
    private List<String> categories;

    @Size(min = 2, max = 100, message = "Search term must be between 2 and 100 characters")
    @Schema(description = "Término de búsqueda general", example = "Boeing")
    private String searchTerm;

    @Schema(description = "Solo familias con aeronaves activas", example = "true")
    private Boolean onlyWithActiveAircraft;

    // Paginación
    @Min(value = 0, message = "Page number cannot be negative")
    @Schema(description = "Número de página", example = "0", defaultValue = "0")
    private Integer page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    @Schema(description = "Tamaño de página", example = "20", defaultValue = "20")
    private Integer size = 20;

    // Ordenamiento
    @ValidSortField(allowedFields = {
            "id", "name", "category", "launchDate", "manufacturer.name", "createdAt", "updatedAt"
    })
    @Schema(description = "Campo de ordenamiento", example = "name", defaultValue = "name")
    private String sortField = "name";

    @Schema(description = "Dirección de ordenamiento", example = "ASC", defaultValue = "ASC")
    private String sortDirection = "ASC";

    // Constructors
    public FamilyFilterDto() {}

    // Getters and Setters
    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public List<Long> getManufacturerIds() {
        return manufacturerIds;
    }

    public void setManufacturerIds(List<Long> manufacturerIds) {
        this.manufacturerIds = manufacturerIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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
