package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estadísticas de familias")
public class FamilyStatisticsDto {

    @Schema(description = "Total de familias", example = "12")
    private Long totalFamilies;

    @Schema(description = "Familias activas", example = "10")
    private Long activeFamilies;

    @Schema(description = "Promedio de modelos por familia", example = "3.2")
    private Double averageModelsPerFamily;

    @Schema(description = "Familia con más modelos")
    private String topFamily;

    @Schema(description = "Número de categorías", example = "3")
    private Integer categoriesCount;

    // Constructors y getters/setters
    public FamilyStatisticsDto() {}

    public Long getTotalFamilies() { return totalFamilies; }
    public void setTotalFamilies(Long totalFamilies) { this.totalFamilies = totalFamilies; }

    public Long getActiveFamilies() { return activeFamilies; }
    public void setActiveFamilies(Long activeFamilies) { this.activeFamilies = activeFamilies; }

    public Double getAverageModelsPerFamily() { return averageModelsPerFamily; }
    public void setAverageModelsPerFamily(Double averageModelsPerFamily) { this.averageModelsPerFamily = averageModelsPerFamily; }

    public String getTopFamily() { return topFamily; }
    public void setTopFamily(String topFamily) { this.topFamily = topFamily; }

    public Integer getCategoriesCount() { return categoriesCount; }
    public void setCategoriesCount(Integer categoriesCount) { this.categoriesCount = categoriesCount; }
}
