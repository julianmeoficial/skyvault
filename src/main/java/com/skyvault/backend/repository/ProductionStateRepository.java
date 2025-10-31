package com.skyvault.backend.repository;

import com.skyvault.backend.model.ProductionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductionStateRepository extends JpaRepository<ProductionState, Long> {

    // ========== MÉTODOS EXISTENTES ==========
    Optional<ProductionState> findByName(String name);
    boolean existsByName(String name);
    List<ProductionState> findAllByOrderByName();

    // ========== MÉTODOS FALTANTES AGREGADOS ==========

    // ✅ Para CatalogServiceImpl.findByNameIgnoreCase()
    Optional<ProductionState> findByNameIgnoreCase(String name);

    // ✅ Para CatalogServiceImpl.getActiveProductionStates()
    List<ProductionState> findByIsActiveTrueOrderByName();

    // ✅ Para CatalogServiceImpl.updateCatalogStatistics()
    @Query("SELECT COUNT(am) FROM ProductionState ps LEFT JOIN ps.aircraftModels am WHERE ps.id = :productionStateId AND am.isActive = true")
    long countAircraftByProductionStateId(@Param("productionStateId") Long productionStateId);
}
