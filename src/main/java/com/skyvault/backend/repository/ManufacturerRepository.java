package com.skyvault.backend.repository;

import com.skyvault.backend.model.Manufacturer;
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
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>,
        JpaSpecificationExecutor<Manufacturer> {

    // ========== MÉTODOS BÁSICOS COMPATIBLES ==========

    // Buscar por nombre exacto (NECESARIO PARA DataLoader)
    Optional<Manufacturer> findByName(String name);

    // Búsqueda por nombre ignorando mayúsculas
    Optional<Manufacturer> findByNameIgnoreCase(String name);

    // Verificar si existe por nombre
    boolean existsByNameIgnoreCase(String name);
    boolean existsByName(String name);

    // Obtener todos ordenados por nombre
    List<Manufacturer> findAllByOrderByName();

    // ========== MÉTODOS DE BÚSQUEDA ==========

    // Búsqueda con LIKE
    @Query("SELECT m FROM Manufacturer m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Manufacturer> findByNameContainingIgnoreCase(@Param("name") String name);

    // Búsqueda paginada
    Page<Manufacturer> findByNameContainingIgnoreCase(String query, Pageable pageable);

    // Búsqueda por país
    Page<Manufacturer> findByCountryIgnoreCase(String country, Pageable pageable);

    Page<Manufacturer> findByCountryContainingIgnoreCaseOrderByName(String country, Pageable pageable);

    // ========== MÉTODOS CON AIRCRAFT MODELS (USANDO TUS RELACIONES) ==========

    // Contar manufacturers que tienen aeronaves activas
    @Query("SELECT COUNT(DISTINCT m) FROM Manufacturer m " +
            "JOIN m.aircraftModels am " +
            "WHERE am.isActive = true")
    long countActiveManufacturers();

    // Obtener manufacturers que tienen aeronaves activas
    @Query("SELECT DISTINCT m FROM Manufacturer m " +
            "JOIN m.aircraftModels am " +
            "WHERE am.isActive = true " +
            "ORDER BY m.name")
    List<Manufacturer> findActiveManufacturersOrderByName();

    // ManufacturersPage con aeronaves activas
    @Query("SELECT DISTINCT m FROM Manufacturer m " +
            "JOIN m.aircraftModels am " +
            "WHERE am.isActive = true " +
            "ORDER BY m.name")
    List<Manufacturer> findManufacturersWithActiveAircraft();

    // ========== MÉTODOS DE ESTADÍSTICAS ==========

    // Búsqueda general con término
    @Query("SELECT m FROM Manufacturer m " +
            "WHERE (:searchTerm IS NULL OR " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(m.country) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "ORDER BY m.name")
    Page<Manufacturer> findWithSearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Obtener países distintos
    @Query("SELECT DISTINCT m.country FROM Manufacturer m WHERE m.country IS NOT NULL ORDER BY m.country")
    List<String> findDistinctCountries();

    // Para autocompletado
    @Query("SELECT m.name FROM Manufacturer m " +
            "WHERE LOWER(m.name) LIKE LOWER(CONCAT(:query, '%')) " +
            "ORDER BY m.name")
    List<String> findManufacturerNamesForSuggestions(@Param("query") String query);

    // Contar modelos de aeronaves por manufacturer
    @Query("SELECT m.id, COUNT(am) FROM Manufacturer m " +
            "LEFT JOIN m.aircraftModels am " +
            "WHERE am.isActive = true " +
            "GROUP BY m.id")
    List<Object[]> countActiveAircraftModelsByManufacturer();

    // Obtener promedio de aeronaves por manufacturer
    @Query("SELECT AVG(aircraftCount) FROM (" +
            "SELECT COUNT(am) as aircraftCount FROM Manufacturer m " +
            "LEFT JOIN m.aircraftModels am " +
            "WHERE am.isActive = true " +
            "GROUP BY m.id)")
    Double getAverageAircraftPerManufacturer();

    // Manufacturer con más aeronaves
    @Query("SELECT m FROM Manufacturer m " +
            "LEFT JOIN m.aircraftModels am " +
            "WHERE am.isActive = true " +
            "GROUP BY m " +
            "ORDER BY COUNT(am) DESC")
    Optional<Manufacturer> findTopManufacturerByAircraftCount();
}
