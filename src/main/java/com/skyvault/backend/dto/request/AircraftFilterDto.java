package com.skyvault.backend.dto.request;

import com.skyvault.backend.validation.ValidIds;
import com.skyvault.backend.validation.ValidPagination;
import com.skyvault.backend.validation.ValidRange;
import com.skyvault.backend.validation.ValidSortField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Filtros combinables para búsqueda de aeronaves")
@ValidPagination(maxPageSize = 100)
public class AircraftFilterDto {

    // Filtros por ID específico
    @Schema(description = "ID del fabricante", example = "1")
    private Long manufacturerId;

    @Schema(description = "ID de la familia", example = "5")
    private Long familyId;

    @Schema(description = "ID del tipo de aeronave", example = "2")
    private Long typeId;

    @Schema(description = "ID del estado de producción", example = "3")
    private Long productionStateId;

    @Schema(description = "ID de la categoría de tamaño", example = "4")
    private Long sizeCategoryId;

    // Filtros por múltiples IDs
    @ValidIds(max = 10, allowEmpty = true)
    @Schema(description = "Lista de IDs de fabricantes", example = "[1, 2]")
    private List<Long> manufacturerIds;

    @ValidIds(max = 20, allowEmpty = true)
    @Schema(description = "Lista de IDs de familias", example = "[5, 6, 7]")
    private List<Long> familyIds;

    @ValidIds(max = 10, allowEmpty = true)
    @Schema(description = "Lista de IDs de tipos", example = "[2, 3]")
    private List<Long> typeIds;

    @ValidIds(max = 10, allowEmpty = true)
    @Schema(description = "Lista de IDs de estados de producción", example = "[3, 4]")
    private List<Long> productionStateIds;

    @ValidIds(max = 10, allowEmpty = true)
    @Schema(description = "Lista de IDs de categorías de tamaño", example = "[4, 5]")
    private List<Long> sizeCategoryIds;

    // Filtros por rangos numéricos
    @Min(value = 1, message = "Minimum passengers must be at least 1")
    @Max(value = 1000, message = "Minimum passengers cannot exceed 1000")
    @Schema(description = "Número mínimo de pasajeros", example = "100")
    private Integer minPassengers;

    @Min(value = 1, message = "Maximum passengers must be at least 1")
    @Max(value = 1000, message = "Maximum passengers cannot exceed 1000")
    @Schema(description = "Número máximo de pasajeros", example = "400")
    private Integer maxPassengers;

    @Min(value = 100, message = "Minimum range must be at least 100 km")
    @Max(value = 50000, message = "Minimum range cannot exceed 50,000 km")
    @Schema(description = "Alcance mínimo en km", example = "1000")
    private Integer minRange;

    @Min(value = 100, message = "Maximum range must be at least 100 km")
    @Max(value = 50000, message = "Maximum range cannot exceed 50,000 km")
    @Schema(description = "Alcance máximo en km", example = "15000")
    private Integer maxRange;

    @Min(value = 1900, message = "Minimum introduction year must be at least 1900")
    @Max(value = 2030, message = "Minimum introduction year cannot exceed 2030")
    @Schema(description = "Año mínimo de introducción", example = "2000")
    private Integer minIntroductionYear;

    @Min(value = 1900, message = "Maximum introduction year must be at least 1900")
    @Max(value = 2030, message = "Maximum introduction year cannot exceed 2030")
    @Schema(description = "Año máximo de introducción", example = "2020")
    private Integer maxIntroductionYear;

    @Min(value = 100, message = "Minimum cruise speed must be at least 100 knots")
    @Max(value = 1000, message = "Minimum cruise speed cannot exceed 1000 knots")
    @Schema(description = "Velocidad mínima de crucero en nudos", example = "400")
    private Integer minCruiseSpeed;

    @Min(value = 100, message = "Maximum cruise speed must be at least 100 knots")
    @Max(value = 1000, message = "Maximum cruise speed cannot exceed 1000 knots")
    @Schema(description = "Velocidad máxima de crucero en nudos", example = "600")
    private Integer maxCruiseSpeed;

    // Filtros de texto
    @Size(min = 2, max = 100, message = "Search term must be between 2 and 100 characters")
    @Schema(description = "Término de búsqueda en nombre, modelo o fabricante", example = "Boeing")
    private String searchTerm;

    @Size(max = 50, message = "Manufacturer name cannot exceed 50 characters")
    @Schema(description = "Nombre del fabricante", example = "Airbus")
    private String manufacturerName;

    @Size(max = 50, message = "Family name cannot exceed 50 characters")
    @Schema(description = "Nombre de la familia", example = "A320")
    private String familyName;

    // Filtros booleanos
    @Schema(description = "Solo aeronaves activas", example = "true", defaultValue = "true")
    private Boolean onlyActive = true;

    @Schema(description = "Solo aeronaves con especificaciones técnicas", example = "false")
    private Boolean onlyWithSpecifications;

    @Schema(description = "Solo aeronaves con imágenes", example = "false")
    private Boolean onlyWithImages;

    @Schema(description = "Solo aeronaves con imagen principal", example = "false")
    private Boolean onlyWithPrimaryImage;

    // Parámetros de paginación
    @Min(value = 0, message = "Page number cannot be negative")
    @Schema(description = "Número de página (basado en 0)", example = "0", defaultValue = "0")
    private Integer page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    @Schema(description = "Tamaño de página", example = "20", defaultValue = "20")
    private Integer size = 20;

    @ValidSortField(allowedFields = {
            // Campos directos de la entidad
            "id", "name", "model", "displayName", "introductionYear",
            "maxPassengers", "typicalPassengers", "rangeKm", "cruiseSpeedKnots",
            "createdAt", "updatedAt",

            // Relaciones (FK)
            "manufacturer.name", "family.name", "type.name",
            "productionState.name", "sizeCategory.name",

            // Alias semánticos (para mapeo en Service)
            "nombre", "alphabetical", "alfabetico",
            "capacity", "capacidad", "passengers", "pasajeros",
            "range", "alcance",
            "year", "año", "fecha",
            "speed", "velocidad",
            "manufacturer", "fabricante",
            "size", "tamaño", "category", "categoria",
            "type", "tipo"
    })
    private String sortField = "name";

    @Schema(description = "Dirección de ordenamiento", example = "ASC",
            allowableValues = {"ASC", "DESC"}, defaultValue = "ASC")
    private String sortDirection = "ASC";

    // Constructors
    public AircraftFilterDto() {}

    // Getters and Setters completos
    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getProductionStateId() {
        return productionStateId;
    }

    public void setProductionStateId(Long productionStateId) {
        this.productionStateId = productionStateId;
    }

    public Long getSizeCategoryId() {
        return sizeCategoryId;
    }

    public void setSizeCategoryId(Long sizeCategoryId) {
        this.sizeCategoryId = sizeCategoryId;
    }

    public List<Long> getManufacturerIds() {
        return manufacturerIds;
    }

    public void setManufacturerIds(List<Long> manufacturerIds) {
        this.manufacturerIds = manufacturerIds;
    }

    public List<Long> getFamilyIds() {
        return familyIds;
    }

    public void setFamilyIds(List<Long> familyIds) {
        this.familyIds = familyIds;
    }

    public List<Long> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<Long> typeIds) {
        this.typeIds = typeIds;
    }

    public List<Long> getProductionStateIds() {
        return productionStateIds;
    }

    public void setProductionStateIds(List<Long> productionStateIds) {
        this.productionStateIds = productionStateIds;
    }

    public List<Long> getSizeCategoryIds() {
        return sizeCategoryIds;
    }

    public void setSizeCategoryIds(List<Long> sizeCategoryIds) {
        this.sizeCategoryIds = sizeCategoryIds;
    }

    public Integer getMinPassengers() {
        return minPassengers;
    }

    public void setMinPassengers(Integer minPassengers) {
        this.minPassengers = minPassengers;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Integer getMinRange() {
        return minRange;
    }

    public void setMinRange(Integer minRange) {
        this.minRange = minRange;
    }

    public Integer getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Integer maxRange) {
        this.maxRange = maxRange;
    }

    public Integer getMinIntroductionYear() {
        return minIntroductionYear;
    }

    public void setMinIntroductionYear(Integer minIntroductionYear) {
        this.minIntroductionYear = minIntroductionYear;
    }

    public Integer getMaxIntroductionYear() {
        return maxIntroductionYear;
    }

    public void setMaxIntroductionYear(Integer maxIntroductionYear) {
        this.maxIntroductionYear = maxIntroductionYear;
    }

    public Integer getMinCruiseSpeed() {
        return minCruiseSpeed;
    }

    public void setMinCruiseSpeed(Integer minCruiseSpeed) {
        this.minCruiseSpeed = minCruiseSpeed;
    }

    public Integer getMaxCruiseSpeed() {
        return maxCruiseSpeed;
    }

    public void setMaxCruiseSpeed(Integer maxCruiseSpeed) {
        this.maxCruiseSpeed = maxCruiseSpeed;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }

    public Boolean getOnlyWithSpecifications() {
        return onlyWithSpecifications;
    }

    public void setOnlyWithSpecifications(Boolean onlyWithSpecifications) {
        this.onlyWithSpecifications = onlyWithSpecifications;
    }

    public Boolean getOnlyWithImages() {
        return onlyWithImages;
    }

    public void setOnlyWithImages(Boolean onlyWithImages) {
        this.onlyWithImages = onlyWithImages;
    }

    public Boolean getOnlyWithPrimaryImage() {
        return onlyWithPrimaryImage;
    }

    public void setOnlyWithPrimaryImage(Boolean onlyWithPrimaryImage) {
        this.onlyWithPrimaryImage = onlyWithPrimaryImage;
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

    public String getSortDirection(){
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
