package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estadísticas de fabricantes")
public class ManufacturerStatisticsDto {

    @Schema(description = "Total de fabricantes", example = "2")
    private Long totalManufacturers;

    @Schema(description = "Fabricantes activos", example = "2")
    private Long activeManufacturers;

    @Schema(description = "Promedio de aeronaves por fabricante", example = "18.0")
    private Double averageAircraftPerManufacturer;

    @Schema(description = "Fabricante con más aeronaves")
    private String topManufacturer;

    @Schema(description = "Número de países representados", example = "2")
    private Integer countriesCount;

    // Constructors y getters/setters
    public ManufacturerStatisticsDto() {}

    public Long getTotalManufacturers() { return totalManufacturers; }
    public void setTotalManufacturers(Long totalManufacturers) { this.totalManufacturers = totalManufacturers; }

    public Long getActiveManufacturers() { return activeManufacturers; }
    public void setActiveManufacturers(Long activeManufacturers) { this.activeManufacturers = activeManufacturers; }

    public Double getAverageAircraftPerManufacturer() { return averageAircraftPerManufacturer; }
    public void setAverageAircraftPerManufacturer(Double averageAircraftPerManufacturer) { this.averageAircraftPerManufacturer = averageAircraftPerManufacturer; }

    public String getTopManufacturer() { return topManufacturer; }
    public void setTopManufacturer(String topManufacturer) { this.topManufacturer = topManufacturer; }

    public Integer getCountriesCount() { return countriesCount; }
    public void setCountriesCount(Integer countriesCount) { this.countriesCount = countriesCount; }
}
