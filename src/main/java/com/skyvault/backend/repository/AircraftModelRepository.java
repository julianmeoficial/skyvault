package com.skyvault.backend.repository;

import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.model.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftModelRepository extends JpaRepository<AircraftModel, Long>,
        JpaSpecificationExecutor<AircraftModel> {

    // Buscar por ID con todas las relaciones cargadas para detalle
    @EntityGraph(attributePaths = {"manufacturer", "family", "type", "productionState",
            "sizeCategory", "specifications", "images"})
    Optional<AircraftModel> findByIdAndIsActiveTrue(Long id);

    // Búsqueda por nombre o modelo
    @EntityGraph(attributePaths = {"manufacturer", "family", "type", "productionState",
            "sizeCategory", "specifications"})
    @Query("SELECT am FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "AND (" +
            "LOWER(am.name) = LOWER(:identifier) " +
            "OR LOWER(am.model) = LOWER(:identifier) " +
            "OR LOWER(REPLACE(am.name, ' ', '-')) = LOWER(:identifier) " +
            "OR LOWER(REPLACE(am.model, ' ', '-')) = LOWER(:identifier) " +
            "OR LOWER(REPLACE(am.name, '-', ' ')) = LOWER(:identifier) " +
            "OR LOWER(REPLACE(am.model, '-', ' ')) = LOWER(:identifier) " +
            "OR LOWER(am.name) LIKE LOWER(CONCAT('%', :identifier, '%')) " +
            "OR LOWER(am.model) LIKE LOWER(CONCAT('%', :identifier, '%'))" +
            ")")
    Optional<AircraftModel> findByNameOrModelIgnoreCase(@Param("identifier") String identifier);

    // Búsqueda por fabricante
    @EntityGraph(attributePaths = {"manufacturer", "family", "type", "productionState", "sizeCategory"})
    Page<AircraftModel> findByManufacturerIdAndIsActiveTrueOrderByName(Long manufacturerId, Pageable pageable);

    // Búsqueda por familia
    @EntityGraph(attributePaths = {"manufacturer", "family", "type", "productionState", "sizeCategory"})
    Page<AircraftModel> findByFamilyIdAndIsActiveTrueOrderByName(Long familyId, Pageable pageable);

    // Búsqueda por tipo
    @EntityGraph(attributePaths = {"manufacturer", "family", "type", "productionState", "sizeCategory"})
    Page<AircraftModel> findByTypeIdAndIsActiveTrueOrderByName(Long typeId, Pageable pageable);

    // Búsqueda por estado de producción
    Page<AircraftModel> findByProductionStateIdAndIsActiveTrueOrderByName(Long productionStateId, Pageable pageable);

    // Búsqueda por categoría de tamaño
    Page<AircraftModel> findBySizeCategoryIdAndIsActiveTrueOrderByName(Long sizeCategoryId, Pageable pageable);

    // Filtros por rango de pasajeros
    @Query("SELECT am FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "AND (:minPax IS NULL OR am.maxPassengers >= :minPax) " +
            "AND (:maxPax IS NULL OR am.maxPassengers <= :maxPax) " +
            "ORDER BY am.maxPassengers ASC")
    Page<AircraftModel> findByPassengerRange(@Param("minPax") Integer minPax,
                                             @Param("maxPax") Integer maxPax,
                                             Pageable pageable);

    // Filtros por rango de alcance
    @Query("SELECT am FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "AND (:minRange IS NULL OR am.rangeKm >= :minRange) " +
            "AND (:maxRange IS NULL OR am.rangeKm <= :maxRange) " +
            "ORDER BY am.rangeKm ASC")
    Page<AircraftModel> findByRangeKm(@Param("minRange") Integer minRange,
                                      @Param("maxRange") Integer maxRange,
                                      Pageable pageable);

    // Búsqueda completa con múltiples filtros
    @Query("SELECT am FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "AND (:manufacturerId IS NULL OR am.manufacturer.id = :manufacturerId) " +
            "AND (:familyId IS NULL OR am.family.id = :familyId) " +
            "AND (:typeId IS NULL OR am.type.id = :typeId) " +
            "AND (:productionStateId IS NULL OR am.productionState.id = :productionStateId) " +
            "AND (:sizeCategoryId IS NULL OR am.sizeCategory.id = :sizeCategoryId) " +
            "AND (:minPax IS NULL OR am.maxPassengers >= :minPax) " +
            "AND (:maxPax IS NULL OR am.maxPassengers <= :maxPax) " +
            "AND (:minRange IS NULL OR am.rangeKm >= :minRange) " +
            "AND (:maxRange IS NULL OR am.rangeKm <= :maxRange) " +
            "AND (:searchTerm IS NULL OR " +
            "LOWER(am.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(am.model) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(am.displayName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(am.manufacturer.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(am.family.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<AircraftModel> findWithAllFilters(@Param("manufacturerId") Long manufacturerId,
                                           @Param("familyId") Long familyId,
                                           @Param("typeId") Long typeId,
                                           @Param("productionStateId") Long productionStateId,
                                           @Param("sizeCategoryId") Long sizeCategoryId,
                                           @Param("minPax") Integer minPax,
                                           @Param("maxPax") Integer maxPax,
                                           @Param("minRange") Integer minRange,
                                           @Param("maxRange") Integer maxRange,
                                           @Param("searchTerm") String searchTerm,
                                           Pageable pageable);

    // Para comparación - buscar múltiples IDs
    @EntityGraph(attributePaths = {"manufacturer", "family", "type", "productionState",
            "sizeCategory", "specifications", "images"})
    @Query("SELECT am FROM AircraftModel am WHERE am.id IN :ids AND am.isActive = true")
    List<AircraftModel> findByIdsForComparison(@Param("ids") List<Long> ids);

    // Proyección ligera para listas (AircraftCard)
    @Query("SELECT am.id, am.name, am.model, am.displayName, " +
            "am.maxPassengers, am.rangeKm, am.introductionYear, " +
            "m.name, f.name, " +
            "(SELECT i.url FROM Image i WHERE i.aircraftModel = am AND i.imageType = 'thumbnail' AND i.isPrimary = true ORDER BY i.displayOrder LIMIT 1) " +
            "FROM AircraftModel am " +
            "JOIN am.manufacturer m " +
            "JOIN am.family f " +
            "WHERE am.isActive = true " +
            "ORDER BY am.name")
    Page<Object[]> findAircraftCardProjection(Pageable pageable);

    // Sugerencias para autocompletado
    @Query("SELECT am.name FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "AND LOWER(am.name) LIKE LOWER(CONCAT(:query, '%')) " +
            "ORDER BY am.name " +
            "LIMIT 10")
    List<String> findAircraftNamesForSuggestions(@Param("query") String query);

    // Sugerencias combinadas (nombre + fabricante)
    @Query("SELECT CONCAT(m.name, ' ', am.name) FROM AircraftModel am " +
            "JOIN am.manufacturer m " +
            "WHERE am.isActive = true " +
            "AND (LOWER(am.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "ORDER BY am.name " +
            "LIMIT 10")
    List<String> findCombinedSuggestions(@Param("query") String query);

    // Modelos similares por capacidad de pasajeros y rango
    @Query("SELECT am FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "AND am.id != :excludeId " +
            "AND ABS(am.maxPassengers - :passengers) <= :passengerTolerance " +
            "AND ABS(am.rangeKm - :range) <= :rangeTolerance " +
            "ORDER BY (ABS(am.maxPassengers - :passengers) + ABS(am.rangeKm - :range)) ASC")
    List<AircraftModel> findSimilarAircraft(@Param("excludeId") Long excludeId,
                                            @Param("passengers") Integer passengers,
                                            @Param("range") Integer range,
                                            @Param("passengerTolerance") Integer passengerTolerance,
                                            @Param("rangeTolerance") Integer rangeTolerance,
                                            Pageable pageable);

    // Modelos más populares (podrías usar métricas de views más adelante)
    @Query("SELECT am FROM AircraftModel am " +
            "WHERE am.isActive = true " +
            "ORDER BY am.maxPassengers DESC, am.rangeKm DESC")
    List<AircraftModel> findMostPopularAircraft(Pageable pageable);

    @Query("SELECT AVG(a.maxPassengers) FROM AircraftModel a WHERE a.isActive = true")
    Double getAveragePassengers();

    @Query("SELECT AVG(a.rangeKm) FROM AircraftModel a WHERE a.isActive = true")
    Double getAverageRange();

    Optional<AircraftModel> findTopByIsActiveTrueOrderByMaxPassengersDesc();
    Optional<AircraftModel> findTopByIsActiveTrueOrderByRangeKmDesc();

    @Query("SELECT MAX(a.introductionYear) FROM AircraftModel a WHERE a.isActive = true")
    Optional<Integer> findMaxIntroductionYear();

    @Query("SELECT MIN(a.introductionYear) FROM AircraftModel a WHERE a.isActive = true")
    Optional<Integer> findMinIntroductionYear();

    // Estadísticas básicas
    @Query("SELECT " +
            "COUNT(am), " +
            "AVG(am.maxPassengers), " +
            "AVG(am.rangeKm), " +
            "MIN(am.introductionYear), " +
            "MAX(am.introductionYear) " +
            "FROM AircraftModel am " +
            "WHERE am.isActive = true")
    Object[] getBasicStatistics();

    // Verificar existencia por nombre y fabricante
    boolean existsByNameIgnoreCaseAndManufacturerIdAndIsActiveTrue(String name, Long manufacturerId);

    // Contar modelos activos
    long countByIsActiveTrue();

    // Contar por fabricante
    long countByManufacturerIdAndIsActiveTrue(Long manufacturerId);

    // Contar por familia
    long countByFamilyIdAndIsActiveTrue(Long familyId);

    // ========== MÉTODOS ADICIONALES PARA AIRCRAFTSERVICEIMPL ==========

    // Para findSimilarAircraft corregido
    Page<AircraftModel> findByMaxPassengersBetweenAndIsActiveTrueAndIdNot(
            Integer minPassengers,
            Integer maxPassengers,
            Long excludeId,
            Pageable pageable);

    // Para searchByText corregido
    Page<AircraftModel> findByNameContainingIgnoreCaseAndIsActiveTrue(String query, Pageable pageable);

    // Para getFeaturedAircraft corregido
    Page<AircraftModel> findByIsActiveTrueOrderByMaxPassengersDesc(Pageable pageable);

    // Para findAllActive corregido
    List<AircraftModel> findByIsActiveTrueOrderByName();

    // Para findByManufacturer, findByFamily, findByType corregidos
    Page<AircraftModel> findByManufacturerIdAndIsActiveTrue(Long manufacturerId, Pageable pageable);
    Page<AircraftModel> findByFamilyIdAndIsActiveTrue(Long familyId, Pageable pageable);
    Page<AircraftModel> findByTypeIdAndIsActiveTrue(Long typeId, Pageable pageable);

    // Para validación en ComparisonService
    @Query("SELECT COUNT(am) FROM AircraftModel am WHERE am.id IN :ids AND am.isActive = true")
    long countByIdInAndIsActiveTrue(@Param("ids") List<Long> ids);

    // ========== MÉTODOS PARA STATISTICSSERVICEIMPL ==========

    // Agrupar aeronaves por fabricante
    @Query("SELECT m.name, COUNT(am) FROM AircraftModel am JOIN am.manufacturer m WHERE am.isActive = true GROUP BY m.name")
    List<Object[]> countAircraftGroupedByManufacturer();

    // Agrupar aeronaves por tipo
    @Query("SELECT t.name, COUNT(am) FROM AircraftModel am JOIN am.type t WHERE am.isActive = true GROUP BY t.name")
    List<Object[]> countAircraftGroupedByType();

    // Agrupar aeronaves por estado de producción
    @Query("SELECT ps.name, COUNT(am) FROM AircraftModel am JOIN am.productionState ps WHERE am.isActive = true GROUP BY ps.name")
    List<Object[]> countAircraftGroupedByProductionState();

    // Agrupar aeronaves por categoría de tamaño
    @Query("SELECT sc.name, COUNT(am) FROM AircraftModel am JOIN am.sizeCategory sc WHERE am.isActive = true GROUP BY sc.name")
    List<Object[]> countAircraftGroupedBySizeCategory();

    // Agrupar aeronaves por década de introducción
    @Query("SELECT (am.introductionYear / 10) * 10, COUNT(am) FROM AircraftModel am WHERE am.isActive = true GROUP BY (am.introductionYear / 10) * 10")
    List<Object[]> countAircraftGroupedByIntroductionYearDecade();

    // ========== MÉTODOS FALTANTES PARA VALIDATIONSERVICEIMPL ==========

    // Para validar unicidad por nombre
    boolean existsByNameIgnoreCase(String name);

    // Para validar unicidad por modelo y fabricante
    @Query("SELECT COUNT(am) > 0 FROM AircraftModel am WHERE LOWER(am.model) = LOWER(:model) AND am.manufacturer.id = :manufacturerId")
    boolean existsByModelAndManufacturerId(@Param("model") String model, @Param("manufacturerId") Long manufacturerId);

    Optional<AircraftModel> findByName(String name);
    boolean existsByNameAndManufacturer(String name, Manufacturer manufacturer);

    // ========== MÉTODO PARA ORDENAR POR AÑO DE INTRODUCCIÓN ==========

    /**
     * Busca aeronaves activas ordenadas por año de introducción (más recientes primero).
     * Usado en servicios para obtener aeronaves nuevas o featured.
     */
    @Query("SELECT am FROM AircraftModel am WHERE am.isActive = true ORDER BY am.introductionYear DESC")
    Page<AircraftModel> findByIsActiveTrueOrderByIntroductionYearDesc(Pageable pageable);

}
