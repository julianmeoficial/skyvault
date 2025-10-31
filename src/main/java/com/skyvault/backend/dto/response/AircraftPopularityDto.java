package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Popularidad de aeronave")
public class AircraftPopularityDto {

    @Schema(description = "ID de la aeronave", example = "15")
    private Long aircraftId;

    @Schema(description = "Nombre de la aeronave", example = "Boeing 737-800")
    private String aircraftName;

    @Schema(description = "Fabricante", example = "Boeing")
    private String manufacturerName;

    @Schema(description = "Número de búsquedas", example = "1250")
    private Long searchCount;

    @Schema(description = "Número de visualizaciones", example = "3456")
    private Long viewCount;

    @Schema(description = "Número de comparaciones", example = "890")
    private Long comparisonCount;

    @Schema(description = "Puntuación de popularidad", example = "95.8")
    private Double popularityScore;

    @Schema(description = "Ranking de popularidad", example = "1")
    private Integer ranking;

    @Schema(description = "Cambio en ranking desde período anterior", example = "+2")
    private Integer rankingChange;

    @Schema(description = "Búsquedas en los últimos 7 días", example = "125")
    private Long recentSearches;

    @Schema(description = "Tendencia", example = "UP", allowableValues = {"UP", "DOWN", "STABLE"})
    private String trend;

    @Schema(description = "Porcentaje del total de búsquedas", example = "8.5")
    private Double searchPercentage;

    @Schema(description = "Primera búsqueda registrada")
    private LocalDateTime firstSearchDate;

    @Schema(description = "Última búsqueda")
    private LocalDateTime lastSearchDate;

    @Schema(description = "Promedio de búsquedas diarias", example = "18.2")
    private Double averageDailySearches;

    // Constructors
    public AircraftPopularityDto() {}

    public AircraftPopularityDto(Long aircraftId, String aircraftName) {
        this.aircraftId = aircraftId;
        this.aircraftName = aircraftName;
    }

    // Getters and Setters
    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getAircraftName() {
        return aircraftName;
    }

    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Long getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Long searchCount) {
        this.searchCount = searchCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getComparisonCount() {
        return comparisonCount;
    }

    public void setComparisonCount(Long comparisonCount) {
        this.comparisonCount = comparisonCount;
    }

    public Double getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Double popularityScore) {
        this.popularityScore = popularityScore;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getRankingChange() {
        return rankingChange;
    }

    public void setRankingChange(Integer rankingChange) {
        this.rankingChange = rankingChange;
    }

    public Long getRecentSearches() {
        return recentSearches;
    }

    public void setRecentSearches(Long recentSearches) {
        this.recentSearches = recentSearches;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public Double getSearchPercentage() {
        return searchPercentage;
    }

    public void setSearchPercentage(Double searchPercentage) {
        this.searchPercentage = searchPercentage;
    }

    public LocalDateTime getFirstSearchDate() {
        return firstSearchDate;
    }

    public void setFirstSearchDate(LocalDateTime firstSearchDate) {
        this.firstSearchDate = firstSearchDate;
    }

    public LocalDateTime getLastSearchDate() {
        return lastSearchDate;
    }

    public void setLastSearchDate(LocalDateTime lastSearchDate) {
        this.lastSearchDate = lastSearchDate;
    }

    public Double getAverageDailySearches() {
        return averageDailySearches;
    }

    public void setAverageDailySearches(Double averageDailySearches) {
        this.averageDailySearches = averageDailySearches;
    }
}
