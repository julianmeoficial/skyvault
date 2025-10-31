package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Estadísticas del sistema")
public class SystemStatisticsDto {

    // Estadísticas básicas
    private Long totalAircraft;
    private Long activeAircraft;
    private Long totalManufacturers;
    private Long activeManufacturers;
    private Long totalFamilies;
    private Long activeFamilies;
    private Long totalTypes;
    private Long totalProductionStates;
    private Long totalSizeCategories;

    // Métricas de uso
    private Long totalSearches;
    private Long totalComparisons;
    private Long totalViews;

    // Cálculos derivados
    private Double averageAircraftPerManufacturer;
    private Double averageAircraftPerFamily;

    // Metadata
    private LocalDateTime lastUpdated;
    private LocalDate dataValidFrom;
    private LocalDate dataValidTo;

    public SystemStatisticsDto() {}

    // Getters y Setters
    public Long getTotalAircraft() { return totalAircraft; }
    public void setTotalAircraft(Long totalAircraft) { this.totalAircraft = totalAircraft; }

    public Long getActiveAircraft() { return activeAircraft; }
    public void setActiveAircraft(Long activeAircraft) { this.activeAircraft = activeAircraft; }

    public Long getTotalManufacturers() { return totalManufacturers; }
    public void setTotalManufacturers(Long totalManufacturers) { this.totalManufacturers = totalManufacturers; }

    public Long getActiveManufacturers() { return activeManufacturers; }
    public void setActiveManufacturers(Long activeManufacturers) { this.activeManufacturers = activeManufacturers; }

    public Long getTotalFamilies() { return totalFamilies; }
    public void setTotalFamilies(Long totalFamilies) { this.totalFamilies = totalFamilies; }

    public Long getActiveFamilies() { return activeFamilies; }
    public void setActiveFamilies(Long activeFamilies) { this.activeFamilies = activeFamilies; }

    public Long getTotalTypes() { return totalTypes; }
    public void setTotalTypes(Long totalTypes) { this.totalTypes = totalTypes; }

    public Long getTotalProductionStates() { return totalProductionStates; }
    public void setTotalProductionStates(Long totalProductionStates) { this.totalProductionStates = totalProductionStates; }

    public Long getTotalSizeCategories() { return totalSizeCategories; }
    public void setTotalSizeCategories(Long totalSizeCategories) { this.totalSizeCategories = totalSizeCategories; }

    public Long getTotalSearches() { return totalSearches; }
    public void setTotalSearches(Long totalSearches) { this.totalSearches = totalSearches; }

    public Long getTotalComparisons() { return totalComparisons; }
    public void setTotalComparisons(Long totalComparisons) { this.totalComparisons = totalComparisons; }

    public Long getTotalViews() { return totalViews; }
    public void setTotalViews(Long totalViews) { this.totalViews = totalViews; }

    public Double getAverageAircraftPerManufacturer() { return averageAircraftPerManufacturer; }
    public void setAverageAircraftPerManufacturer(Double averageAircraftPerManufacturer) { this.averageAircraftPerManufacturer = averageAircraftPerManufacturer; }

    public Double getAverageAircraftPerFamily() { return averageAircraftPerFamily; }
    public void setAverageAircraftPerFamily(Double averageAircraftPerFamily) { this.averageAircraftPerFamily = averageAircraftPerFamily; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public LocalDate getDataValidFrom() { return dataValidFrom; }
    public void setDataValidFrom(LocalDate dataValidFrom) { this.dataValidFrom = dataValidFrom; }

    public LocalDate getDataValidTo() { return dataValidTo; }
    public void setDataValidTo(LocalDate dataValidTo) { this.dataValidTo = dataValidTo; }
}
