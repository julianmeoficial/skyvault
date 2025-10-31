package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Valor individual en comparación")
public class ComparisonValueDto {

    @Schema(description = "ID de la aeronave", example = "15")
    private Long aircraftId;

    @Schema(description = "Valor original", example = "162")
    private String originalValue;

    @Schema(description = "Valor formateado para mostrar", example = "162 passengers")
    private String displayValue;

    @Schema(description = "Valor numérico para comparaciones", example = "162.0")
    private Double numericValue;

    @Schema(description = "Es el valor más alto", example = "false")
    private Boolean isHighest;

    @Schema(description = "Es el valor más bajo", example = "true")
    private Boolean isLowest;

    @Schema(description = "Posición relativa (0-100)", example = "25.5")
    private Double relativePosition;

    // Constructors
    public ComparisonValueDto() {}

    public ComparisonValueDto(Long aircraftId, String originalValue, String displayValue) {
        this.aircraftId = aircraftId;
        this.originalValue = originalValue;
        this.displayValue = displayValue;
    }

    // Getters and Setters
    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Double numericValue) {
        this.numericValue = numericValue;
    }

    public Boolean getIsHighest() {
        return isHighest;
    }

    public void setIsHighest(Boolean isHighest) {
        this.isHighest = isHighest;
    }

    public Boolean getIsLowest() {
        return isLowest;
    }

    public void setIsLowest(Boolean isLowest) {
        this.isLowest = isLowest;
    }

    public Double getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Double relativePosition) {
        this.relativePosition = relativePosition;
    }
}
