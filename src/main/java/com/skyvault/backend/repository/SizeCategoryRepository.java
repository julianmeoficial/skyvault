package com.skyvault.backend.repository;

import com.skyvault.backend.model.SizeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeCategoryRepository extends JpaRepository<SizeCategory, Long> {

    // ========== MÉTODOS EXISTENTES ==========
    Optional<SizeCategory> findByName(String name);
    boolean existsByName(String name);
    List<SizeCategory> findAllByOrderByName();

    // ========== MÉTODOS FALTANTES AGREGADOS ==========

    // ✅ Para CatalogServiceImpl.findByNameIgnoreCase()
    Optional<SizeCategory> findByNameIgnoreCase(String name);

    // ✅ Para CatalogServiceImpl.getActiveSizeCategories()
    // Simulado - ordenado por minPassengers
    @Query("SELECT sc FROM SizeCategory sc ORDER BY sc.minPassengers")
    List<SizeCategory> findByIsActiveTrueOrderByMinPassengers();

    // ✅ Para CatalogServiceImpl.findSizeCategoryForPassengerCount()
    Optional<SizeCategory> findTopByMinPassengersLessThanEqualAndMaxPassengersGreaterThanEqual(
            int minPassengers, int maxPassengers
    );

    // ✅ Para CatalogServiceImpl.findSizeCategoriesInRange()
    @Query("SELECT sc FROM SizeCategory sc WHERE sc.minPassengers <= :maxPassengers AND sc.maxPassengers >= :minPassengers")
    List<SizeCategory> findCategoriesInPassengerRange(
            @Param("minPassengers") int minPassengers,
            @Param("maxPassengers") int maxPassengers
    );

    // ✅ Para CatalogServiceImpl.updateCatalogStatistics()
    @Query("SELECT COUNT(am) FROM SizeCategory sc LEFT JOIN sc.aircraftModels am WHERE sc.id = :sizeCategoryId AND am.isActive = true")
    long countAircraftBySizeCategoryId(@Param("sizeCategoryId") Long sizeCategoryId);
}
