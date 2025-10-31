package com.skyvault.backend.repository;

import com.skyvault.backend.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    // ========== MÉTODOS EXISTENTES ==========
    Optional<Type> findByName(String name);
    boolean existsByName(String name);
    List<Type> findAllByOrderByName();

    // ========== MÉTODOS FALTANTES AGREGADOS ==========

    // ✅ Para CatalogServiceImpl.findByNameIgnoreCase()
    Optional<Type> findByNameIgnoreCase(String name);

    // ✅ Para CatalogServiceImpl.getActiveTypes()
    // Simulado ya que no tienes campo 'active' en Type
    @Query("SELECT t FROM Type t ORDER BY t.name")
    List<Type> findByIsActiveTrueOrderByName();

    // ✅ Para CatalogServiceImpl.updateCatalogStatistics()
    @Query("SELECT COUNT(am) FROM Type t LEFT JOIN t.aircraftModels am WHERE t.id = :typeId AND am.isActive = true")
    long countAircraftByTypeId(@Param("typeId") Long typeId);
}
