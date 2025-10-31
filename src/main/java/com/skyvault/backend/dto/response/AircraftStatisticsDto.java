package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estadísticas de aeronaves")
public class AircraftStatisticsDto {

    @Schema(description = "Total de aeronaves", example = "36")
    private Long totalAircraft;

    @Schema(description = "Aeronaves activas", example = "32")
    private Long activeAircraft;

    @Schema(description = "Promedio de pasajeros", example = "185.5")
    private Double averagePassengers;

    @Schema(description = "Promedio de alcance", example = "6245.2")
    private Double averageRange;

    @Schema(description = "Aeronave más grande por pasajeros")
    private String largestAircraft;

    @Schema(description = "Aeronave con mayor alcance")
    private String longestRangeAircraft;

    @Schema(description = "Aeronave más nueva", example = "2019")
    private Integer newestYear;

    @Schema(description = "Aeronave más antigua", example = "1967")
    private Integer oldestYear;

    // Constructors
    public AircraftStatisticsDto() {}

    // Getters and Setters
    public Long getTotalAircraft() { return totalAircraft; }
    public void setTotalAircraft(Long totalAircraft) { this.totalAircraft = totalAircraft; }

    public Long getActiveAircraft() { return activeAircraft; }
    public void setActiveAircraft(Long activeAircraft) { this.activeAircraft = activeAircraft; }

    public Double getAveragePassengers() { return averagePassengers; }
    public void setAveragePassengers(Double averagePassengers) { this.averagePassengers = averagePassengers; }

    public Double getAverageRange() { return averageRange; }
    public void setAverageRange(Double averageRange) { this.averageRange = averageRange; }

    public String getLargestAircraft() { return largestAircraft; }
    public void setLargestAircraft(String largestAircraft) { this.largestAircraft = largestAircraft; }

    public String getLongestRangeAircraft() { return longestRangeAircraft; }
    public void setLongestRangeAircraft(String longestRangeAircraft) { this.longestRangeAircraft = longestRangeAircraft; }

    public Integer getNewestYear() { return newestYear; }
    public void setNewestYear(Integer newestYear) { this.newestYear = newestYear; }

    public Integer getOldestYear() { return oldestYear; }
    public void setOldestYear(Integer oldestYear) { this.oldestYear = oldestYear; }
}
