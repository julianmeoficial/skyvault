package com.skyvault.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Schema(description = "DTO para solicitar estadísticas con filtros")
public class StatisticsRequestDto {

    @Schema(description = "Fecha de inicio para el rango", example = "2024-01-01")
    private LocalDate dateFrom;

    @Schema(description = "Fecha de fin para el rango", example = "2024-12-31")
    private LocalDate dateTo;

    @Schema(description = "Tipo de estadística", example = "aircraft",
            allowableValues = {"aircraft", "manufacturer", "family", "search", "comparison", "system"})
    private String statisticType;

    @Schema(description = "Granularidad temporal", example = "daily",
            allowableValues = {"hourly", "daily", "weekly", "monthly", "yearly"})
    private String granularity = "daily";

    @Schema(description = "Incluir datos históricos", example = "true", defaultValue = "false")
    private Boolean includeHistorical = false;

    @Schema(description = "Solo elementos activos", example = "true", defaultValue = "true")
    private Boolean onlyActive = true;

    @Schema(description = "ID del fabricante para filtrar")
    private Long manufacturerId;

    @Schema(description = "ID de la familia para filtrar")
    private Long familyId;

    @Schema(description = "Grupo por campo específico", example = "manufacturer")
    private String groupBy;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 1000, message = "Limit cannot exceed 1000")
    @Schema(description = "Límite de resultados", example = "100", defaultValue = "100")
    private Integer limit = 100;

    @Schema(description = "Campo de ordenamiento", example = "count")
    private String sortBy = "count";

    @Pattern(regexp = "^(ASC|DESC)$", message = "Sort direction must be ASC or DESC")
    @Schema(description = "Dirección de ordenamiento", example = "DESC", defaultValue = "DESC")
    private String sortDirection = "DESC";

    @Schema(description = "Incluir comparaciones con período anterior", example = "false")
    private Boolean includePreviousPeriod = false;

    @Schema(description = "Formato de salida", example = "json",
            allowableValues = {"json", "csv", "excel"})
    private String outputFormat = "json";

    // Constructors
    public StatisticsRequestDto() {}

    // Getters and Setters
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(String statisticType) {
        this.statisticType = statisticType;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public Boolean getIncludeHistorical() {
        return includeHistorical;
    }

    public void setIncludeHistorical(Boolean includeHistorical) {
        this.includeHistorical = includeHistorical;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
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

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Boolean getIncludePreviousPeriod() {
        return includePreviousPeriod;
    }

    public void setIncludePreviousPeriod(Boolean includePreviousPeriod) {
        this.includePreviousPeriod = includePreviousPeriod;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }
}
