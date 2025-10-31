package com.skyvault.backend.dto.request;

import com.skyvault.backend.validation.ValidIds;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Parámetros para comparación de aeronaves")
public class CompareRequestDto {

    @NotEmpty(message = "Aircraft IDs list cannot be empty")
    @ValidIds(max = 5, allowEmpty = false)
    @Schema(description = "Lista de IDs de aeronaves a comparar (máximo 5)",
            example = "[1, 5, 10]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> aircraftIds;

    @Schema(description = "Incluir especificaciones técnicas en la comparación",
            example = "true",
            defaultValue = "true")
    private Boolean includeSpecifications = true;

    @Schema(description = "Incluir imágenes en la comparación",
            example = "true",
            defaultValue = "true")
    private Boolean includeImages = true;

    @Schema(description = "Normalizar unidades para comparación",
            example = "true",
            defaultValue = "true")
    private Boolean normalizeUnits = true;

    @Schema(description = "Formato de unidades (metric/imperial)",
            example = "metric",
            defaultValue = "metric",
            allowableValues = {"metric", "imperial"})
    private String unitFormat = "metric";

    // Constructors
    public CompareRequestDto() {}

    public CompareRequestDto(List<Long> aircraftIds) {
        this.aircraftIds = aircraftIds;
    }

    // Getters and Setters
    public List<Long> getAircraftIds() {
        return aircraftIds;
    }

    public void setAircraftIds(List<Long> aircraftIds) {
        this.aircraftIds = aircraftIds;
    }

    public Boolean getIncludeSpecifications() {
        return includeSpecifications;
    }

    public void setIncludeSpecifications(Boolean includeSpecifications) {
        this.includeSpecifications = includeSpecifications;
    }

    public Boolean getIncludeImages() {
        return includeImages;
    }

    public void setIncludeImages(Boolean includeImages) {
        this.includeImages = includeImages;
    }

    public Boolean getNormalizeUnits() {
        return normalizeUnits;
    }

    public void setNormalizeUnits(Boolean normalizeUnits) {
        this.normalizeUnits = normalizeUnits;
    }

    public String getUnitFormat() {
        return unitFormat;
    }

    public void setUnitFormat(String unitFormat) {
        this.unitFormat = unitFormat;
    }
}
