package com.skyvault.backend.dto.response;

import java.util.List;

public class CatalogSummaryDto {
    private List<TypeDto> types;
    private List<ProductionStateDto> productionStates;
    private List<SizeCategoryDto> sizeCategories;

    public CatalogSummaryDto() {}

    public List<TypeDto> getTypes() { return types; }
    public void setTypes(List<TypeDto> types) { this.types = types; }

    public List<ProductionStateDto> getProductionStates() { return productionStates; }
    public void setProductionStates(List<ProductionStateDto> productionStates) { this.productionStates = productionStates; }

    public List<SizeCategoryDto> getSizeCategories() { return sizeCategories; }
    public void setSizeCategories(List<SizeCategoryDto> sizeCategories) { this.sizeCategories = sizeCategories; }

    private Integer typesCount;
    private Integer productionStatesCount;
    private Integer sizeCategoriesCount;
    private java.time.LocalDateTime lastUpdated;

    //GETTERS Y SETTERS:
    public Integer getTypesCount() { return typesCount; }
    public void setTypesCount(Integer typesCount) { this.typesCount = typesCount; }

    public Integer getProductionStatesCount() { return productionStatesCount; }
    public void setProductionStatesCount(Integer productionStatesCount) { this.productionStatesCount = productionStatesCount; }

    public Integer getSizeCategoriesCount() { return sizeCategoriesCount; }
    public void setSizeCategoriesCount(Integer sizeCategoriesCount) { this.sizeCategoriesCount = sizeCategoriesCount; }

    public java.time.LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(java.time.LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}