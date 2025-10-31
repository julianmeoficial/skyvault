package com.skyvault.backend.repository;

import com.skyvault.backend.model.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>,
        JpaSpecificationExecutor<Family> { // ⭐ CRÍTICO PARA SPECIFICATIONS

    // ========== MÉTODOS EXISTENTES ==========
    Optional<Family> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT f FROM Family f WHERE f.name = :name AND f.manufacturer.id = :manufacturerId")
    Optional<Family> findByNameAndManufacturerId(@Param("name") String name, @Param("manufacturerId") Long manufacturerId);

    List<Family> findByManufacturerIdOrderByName(Long manufacturerId);
    List<Family> findAllByOrderByName();

    // ========== MÉTODOS FALTANTES AGREGADOS ==========

    // ✅ Para FamilyServiceImpl.findAll() con Specification + Pageable
    // Ya incluido automáticamente por JpaSpecificationExecutor<Family>

    // ✅ Para FamilyServiceImpl.countActiveFamilies()
    @Query("SELECT COUNT(DISTINCT f) FROM Family f JOIN f.aircraftModels am WHERE am.isActive = true")
    long countActiveFamilies();

    // ✅ Para FamilyServiceImpl.countByManufacturerId()
    long countByManufacturerId(Long manufacturerId);

    // ✅ Para FamilyServiceImpl.findByManufacturerId() con paginación
    Page<Family> findByManufacturerId(Long manufacturerId, Pageable pageable);

    // ✅ Para FamilyServiceImpl.findByCategoryIgnoreCase()
    Page<Family> findByCategoryIgnoreCase(String category, Pageable pageable);

    // ✅ Para FamilyServiceImpl + SearchServiceImpl.findByNameContainingIgnoreCase()
    Page<Family> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // ✅ Para FamilyServiceImpl.getAvailableCategories()
    @Query("SELECT DISTINCT f.category FROM Family f WHERE f.category IS NOT NULL ORDER BY f.category")
    List<String> findDistinctCategories();

    // ✅ Para FamilyServiceImpl.findPopularFamilies()
    @Query("SELECT f FROM Family f LEFT JOIN f.aircraftModels am WHERE am.isActive = true GROUP BY f ORDER BY COUNT(am) DESC")
    Page<Family> findPopularFamilies(Pageable pageable);

    // ✅ Para FamilyServiceImpl.getStatistics() - promedio
    @Query("SELECT AVG(modelCount) FROM (SELECT COUNT(am) as modelCount FROM Family f LEFT JOIN f.aircraftModels am WHERE am.isActive = true GROUP BY f.id)")
    Double getAverageModelsPerFamily();

    // ✅ Para FamilyServiceImpl.getStatistics() - top family
    @Query("SELECT f FROM Family f LEFT JOIN f.aircraftModels am WHERE am.isActive = true GROUP BY f ORDER BY COUNT(am) DESC")
    Optional<Family> findTopFamilyByModelCount();
}
