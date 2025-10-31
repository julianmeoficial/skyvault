package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Fila de datos en tabla comparativa")
public class ComparisonRowDto {

    @Schema(description = "Nombre del campo", example = "Max Passengers")
    private String fieldName;

    @Schema(description = "Tipo de campo", example = "numeric")
    private String fieldType;

    @Schema(description = "Unidad de medida", example = "passengers")
    private String unit;

    @Schema(description = "Valores para cada aeronave")
    private List<ComparisonValueDto> values;

    @Schema(description = "Valor mínimo de la comparación")
    private ComparisonValueDto minValue;

    @Schema(description = "Valor máximo de la comparación")
    private ComparisonValueDto maxValue;

    @Schema(description = "Diferencia entre máximo y mínimo")
    private String difference;

    // Constructors
    public ComparisonRowDto() {}

    // Getters and Setters
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<ComparisonValueDto> getValues() {
        return values;
    }

    public void setValues(List<ComparisonValueDto> values) {
        this.values = values;
    }

    public ComparisonValueDto getMinValue() {
        return minValue;
    }

    public void setMinValue(ComparisonValueDto minValue) {
        this.minValue = minValue;
    }

    public ComparisonValueDto getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(ComparisonValueDto maxValue) {
        this.maxValue = maxValue;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }
}
