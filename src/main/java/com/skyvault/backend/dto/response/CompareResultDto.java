package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Resultado de comparación de aeronaves")
public class CompareResultDto {

    @Schema(description = "Lista de aeronaves comparadas")
    private List<AircraftDetailDto> aircraft;

    @Schema(description = "Tabla comparativa con datos normalizados")
    private Map<String, ComparisonRowDto> comparisonTable;

    @Schema(description = "Configuración de comparación utilizada")
    private ComparisonConfigDto config;

    @Schema(description = "Resumen estadístico de la comparación")
    private ComparisonSummaryDto summary;

    @Schema(description = "Número total de aeronaves comparadas", example = "3")
    private Integer totalAircraft;

    @Schema(description = "Fecha y hora de la comparación")
    private String comparedAt;

    // Constructors
    public CompareResultDto() {}

    // Getters and Setters
    public List<AircraftDetailDto> getAircraft() {
        return aircraft;
    }

    public void setAircraft(List<AircraftDetailDto> aircraft) {
        this.aircraft = aircraft;
    }

    public Map<String, ComparisonRowDto> getComparisonTable() {
        return comparisonTable;
    }

    public void setComparisonTable(Map<String, ComparisonRowDto> comparisonTable) {
        this.comparisonTable = comparisonTable;
    }

    public ComparisonConfigDto getConfig() {
        return config;
    }

    public void setConfig(ComparisonConfigDto config) {
        this.config = config;
    }

    public ComparisonSummaryDto getSummary() {
        return summary;
    }

    public void setSummary(ComparisonSummaryDto summary) {
        this.summary = summary;
    }

    public Integer getTotalAircraft() {
        return totalAircraft;
    }

    public void setTotalAircraft(Integer totalAircraft) {
        this.totalAircraft = totalAircraft;
    }

    public String getComparedAt() {
        return comparedAt;
    }

    public void setComparedAt(String comparedAt) {
        this.comparedAt = comparedAt;
    }
}
