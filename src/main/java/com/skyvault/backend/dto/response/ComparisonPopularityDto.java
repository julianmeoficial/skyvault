package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Popularidad de comparación")
public class ComparisonPopularityDto {

    @Schema(description = "IDs de aeronaves comparadas")
    private List<Long> aircraftIds;

    @Schema(description = "Nombres de aeronaves comparadas")
    private List<String> aircraftNames;

    @Schema(description = "Número de veces comparadas", example = "456")
    private Long comparisonCount;

    @Schema(description = "Puntuación de popularidad", example = "87.3")
    private Double popularityScore;

    @Schema(description = "Ranking de popularidad", example = "3")
    private Integer ranking;

    @Schema(description = "Cambio en ranking", example = "-1")
    private Integer rankingChange;

    @Schema(description = "Comparaciones recientes (7 días)", example = "45")
    private Long recentComparisons;

    @Schema(description = "Tendencia", example = "STABLE", allowableValues = {"UP", "DOWN", "STABLE"})
    private String trend;

    @Schema(description = "Porcentaje del total de comparaciones", example = "12.3")
    private Double comparisonPercentage;

    @Schema(description = "Primera comparación registrada")
    private LocalDateTime firstComparisonDate;

    @Schema(description = "Última comparación")
    private LocalDateTime lastComparisonDate;

    @Schema(description = "Promedio de comparaciones diarias", example = "6.5")
    private Double averageDailyComparisons;

    @Schema(description = "Categoría de comparación", example = "Narrow-body vs Wide-body")
    private String comparisonCategory;

    @Schema(description = "Diferencia promedio en pasajeros", example = "125")
    private Integer averagePassengerDifference;

    @Schema(description = "Diferencia promedio en alcance", example = "2500")
    private Integer averageRangeDifference;

    @Schema(description = "Tags de la comparación")
    private List<String> tags;

    // Constructors
    public ComparisonPopularityDto() {}

    public ComparisonPopularityDto(List<Long> aircraftIds) {
        this.aircraftIds = aircraftIds;
    }

    // Getters and Setters
    public List<Long> getAircraftIds() {
        return aircraftIds;
    }

    public void setAircraftIds(List<Long> aircraftIds) {
        this.aircraftIds = aircraftIds;
    }

    public List<String> getAircraftNames() {
        return aircraftNames;
    }

    public void setAircraftNames(List<String> aircraftNames) {
        this.aircraftNames = aircraftNames;
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

    public Long getRecentComparisons() {
        return recentComparisons;
    }

    public void setRecentComparisons(Long recentComparisons) {
        this.recentComparisons = recentComparisons;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public Double getComparisonPercentage() {
        return comparisonPercentage;
    }

    public void setComparisonPercentage(Double comparisonPercentage) {
        this.comparisonPercentage = comparisonPercentage;
    }

    public LocalDateTime getFirstComparisonDate() {
        return firstComparisonDate;
    }

    public void setFirstComparisonDate(LocalDateTime firstComparisonDate) {
        this.firstComparisonDate = firstComparisonDate;
    }

    public LocalDateTime getLastComparisonDate() {
        return lastComparisonDate;
    }

    public void setLastComparisonDate(LocalDateTime lastComparisonDate) {
        this.lastComparisonDate = lastComparisonDate;
    }

    public Double getAverageDailyComparisons() {
        return averageDailyComparisons;
    }

    public void setAverageDailyComparisons(Double averageDailyComparisons) {
        this.averageDailyComparisons = averageDailyComparisons;
    }

    public String getComparisonCategory() {
        return comparisonCategory;
    }

    public void setComparisonCategory(String comparisonCategory) {
        this.comparisonCategory = comparisonCategory;
    }

    public Integer getAveragePassengerDifference() {
        return averagePassengerDifference;
    }

    public void setAveragePassengerDifference(Integer averagePassengerDifference) {
        this.averagePassengerDifference = averagePassengerDifference;
    }

    public Integer getAverageRangeDifference() {
        return averageRangeDifference;
    }

    public void setAverageRangeDifference(Integer averageRangeDifference) {
        this.averageRangeDifference = averageRangeDifference;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
