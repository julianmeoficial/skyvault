package com.skyvault.backend.dto.request;

import com.skyvault.backend.validation.ValidAircraftModel;
import com.skyvault.backend.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "DTO para crear nueva aeronave")
@ValidAircraftModel(groups = ValidationGroups.Create.class)
public class AircraftCreateDto {

    @NotBlank(message = "Aircraft name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Nombre de la aeronave", example = "Boeing 737 MAX 8", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
    @Schema(description = "Modelo específico", example = "737 MAX 8", requiredMode = Schema.RequiredMode.REQUIRED)
    private String model;

    @Size(max = 100, message = "Display name cannot exceed 100 characters")
    @Schema(description = "Nombre para mostrar", example = "Boeing 737 MAX 8")
    private String displayName;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Schema(description = "Descripción de la aeronave")
    private String description;

    @NotNull(message = "Manufacturer ID is required")
    @Positive(message = "Manufacturer ID must be positive")
    @Schema(description = "ID del fabricante", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long manufacturerId;

    @NotNull(message = "Family ID is required")
    @Positive(message = "Family ID must be positive")
    @Schema(description = "ID de la familia", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long familyId;

    @NotNull(message = "Type ID is required")
    @Positive(message = "Type ID must be positive")
    @Schema(description = "ID del tipo", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long typeId;

    @NotNull(message = "Production state ID is required")
    @Positive(message = "Production state ID must be positive")
    @Schema(description = "ID del estado de producción", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productionStateId;

    @NotNull(message = "Size category ID is required")
    @Positive(message = "Size category ID must be positive")
    @Schema(description = "ID de la categoría de tamaño", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sizeCategoryId;

    @NotNull(message = "Introduction year is required")
    @Min(value = 1900, message = "Introduction year must be at least 1900")
    @Max(value = 2030, message = "Introduction year cannot exceed 2030")
    @Schema(description = "Año de introducción", example = "2016", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer introductionYear;

    @Schema(description = "Fecha del primer vuelo", example = "2016-01-29")
    private LocalDate firstFlightDate;

    @NotNull(message = "Typical passengers is required")
    @Min(value = 1, message = "Typical passengers must be at least 1")
    @Max(value = 1000, message = "Typical passengers cannot exceed 1000")
    @Schema(description = "Capacidad típica de pasajeros", example = "162", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer typicalPassengers;

    @NotNull(message = "Maximum passengers is required")
    @Min(value = 1, message = "Maximum passengers must be at least 1")
    @Max(value = 1000, message = "Maximum passengers cannot exceed 1000")
    @Schema(description = "Capacidad máxima de pasajeros", example = "189", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer maxPassengers;

    @NotNull(message = "Range is required")
    @Min(value = 100, message = "Range must be at least 100 km")
    @Max(value = 50000, message = "Range cannot exceed 50000 km")
    @Schema(description = "Alcance en km", example = "6570", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer rangeKm;

    @NotNull(message = "Cruise speed is required")
    @Min(value = 100, message = "Cruise speed must be at least 100 knots")
    @Max(value = 1000, message = "Cruise speed cannot exceed 1000 knots")
    @Schema(description = "Velocidad de crucero en nudos", example = "453", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cruiseSpeedKnots;

    @Min(value = 1000, message = "Service ceiling must be at least 1000 ft")
    @Max(value = 60000, message = "Service ceiling cannot exceed 60000 ft")
    @Schema(description = "Techo de servicio en pies", example = "41000")
    private Integer serviceCeilingFt;

    @Min(value = 1, message = "Minimum crew must be at least 1")
    @Max(value = 10, message = "Minimum crew cannot exceed 10")
    @Schema(description = "Tripulación mínima", example = "2")
    private Integer minCrew;

    @Schema(description = "Aeronave activa", example = "true", defaultValue = "true")
    private Boolean isActive = true;

    // Constructors
    public AircraftCreateDto() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Integer getIntroductionYear() {
        return introductionYear;
    }

    public void setIntroductionYear(Integer introductionYear) {
        this.introductionYear = introductionYear;
    }

    public LocalDate getFirstFlightDate() {
        return firstFlightDate;
    }

    public void setFirstFlightDate(LocalDate firstFlightDate) {
        this.firstFlightDate = firstFlightDate;
    }

    public Integer getTypicalPassengers() {
        return typicalPassengers;
    }

    public void setTypicalPassengers(Integer typicalPassengers) {
        this.typicalPassengers = typicalPassengers;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Integer getRangeKm() {
        return rangeKm;
    }

    public void setRangeKm(Integer rangeKm) {
        this.rangeKm = rangeKm;
    }

    public Integer getCruiseSpeedKnots() {
        return cruiseSpeedKnots;
    }

    public void setCruiseSpeedKnots(Integer cruiseSpeedKnots) {
        this.cruiseSpeedKnots = cruiseSpeedKnots;
    }

    public Integer getServiceCeilingFt() {
        return serviceCeilingFt;
    }

    public void setServiceCeilingFt(Integer serviceCeilingFt) {
        this.serviceCeilingFt = serviceCeilingFt;
    }

    public Integer getMinCrew() {
        return minCrew;
    }

    public void setMinCrew(Integer minCrew) {
        this.minCrew = minCrew;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
