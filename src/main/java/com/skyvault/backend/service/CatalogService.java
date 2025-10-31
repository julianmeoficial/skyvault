package com.skyvault.backend.service;

import com.skyvault.backend.dto.response.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio para catálogos de tipos, estados de producción y categorías de tamaño.
 */
public interface CatalogService {

    // Tipos de aeronave
    List<TypeDto> getAllTypes();
    Optional<TypeDto> getTypeById(Long id);
    boolean existsTypeById(Long id);

    // Estados de producción
    List<ProductionStateDto> getAllProductionStates();
    Optional<ProductionStateDto> getProductionStateById(Long id);
    boolean existsProductionStateById(Long id);
    List<ProductionStateDto> getActiveProductionStates();

    // Categorías de tamaño
    List<SizeCategoryDto> getAllSizeCategories();
    Optional<SizeCategoryDto> getSizeCategoryById(Long id);
    boolean existsSizeCategoryById(Long id);
    SizeCategoryDto determineSizeCategory(int passengers);

    // Operaciones combinadas
    CatalogSummaryDto getAllCatalogs();

    /**
     * Actualizar contadores de catálogos (para mantener estadísticas)
     */
    void updateCatalogCounts();

    /**
     * Invalidar cache de catálogos
     */
    void evictCatalogCache();

    List<TypeDto> getActiveTypes();
    Optional<TypeDto> getTypeByName(String name);
    Optional<ProductionStateDto> getProductionStateByName(String name);
    List<SizeCategoryDto> getActiveSizeCategories();
    Optional<SizeCategoryDto> getSizeCategoryByName(String name);
    List<SizeCategoryDto> getSizeCategoriesForPassengerRange(int minPassengers, int maxPassengers);
    long countAircraftByType(Long typeId);
    long countAircraftByProductionState(Long stateId);
    long countAircraftBySizeCategory(Long categoryId);
    boolean isTypeInUse(Long typeId);
    boolean isProductionStateInUse(Long stateId);
    boolean isSizeCategoryInUse(Long categoryId);
    Map<String, Object> getCatalogStatistics();
    void refreshCatalogs();
}
