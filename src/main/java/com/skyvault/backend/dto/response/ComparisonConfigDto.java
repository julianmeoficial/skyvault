package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Configuración utilizada en la comparación")
public class ComparisonConfigDto {

    @Schema(description = "Se incluyeron especificaciones técnicas", example = "true")
    private Boolean includeSpecifications;

    @Schema(description = "Se incluyeron imágenes", example = "true")
    private Boolean includeImages;

    @Schema(description = "Se normalizaron las unidades", example = "true")
    private Boolean normalizeUnits;

    @Schema(description = "Formato de unidades utilizado", example = "metric")
    private String unitFormat;

    @Schema(description = "Campos incluidos en la comparación")
    private List<String> includedFields;

    @Schema(description = "Campos excluidos de la comparación")
    private List<String> excludedFields;

    // Constructors
    public ComparisonConfigDto() {}

    // Getters and Setters
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

    public List<String> getIncludedFields() {
        return includedFields;
    }

    public void setIncludedFields(List<String> includedFields) {
        this.includedFields = includedFields;
    }

    public List<String> getExcludedFields() {
        return excludedFields;
    }

    public void setExcludedFields(List<String> excludedFields) {
        this.excludedFields = excludedFields;
    }
}
