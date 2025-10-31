package com.skyvault.backend.repository;

import com.skyvault.backend.model.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpecificationsRepository extends JpaRepository<Specifications, Long>,
        JpaSpecificationExecutor<Specifications> {

    // Buscar por ID del modelo de aeronave
    Optional<Specifications> findByAircraftModelId(Long aircraftModelId);

    // Buscar especificaciones de múltiples aeronaves para comparación
    @Query("SELECT s FROM Specifications s WHERE s.aircraftModel.id IN :aircraftIds")
    List<Specifications> findByAircraftModelIds(@Param("aircraftIds") List<Long> aircraftIds);

    // Verificar existencia por modelo de aeronave
    boolean existsByAircraftModelId(Long aircraftModelId);

    // Especificaciones con datos básicos completos
    @Query("SELECT s FROM Specifications s " +
            "WHERE s.lengthM IS NOT NULL " +
            "AND s.wingspanM IS NOT NULL " +
            "AND s.heightM IS NOT NULL " +
            "AND s.emptyWeightKg IS NOT NULL")
    List<Specifications> findWithCompleteBasicSpecs();

    // Especificaciones con información de motores
    @Query("SELECT s FROM Specifications s " +
            "WHERE s.engineManufacturer IS NOT NULL " +
            "AND s.engineModel IS NOT NULL " +
            "AND s.engineCount IS NOT NULL")
    List<Specifications> findWithEngineInfo();

    // Buscar por fabricante de motor
    List<Specifications> findByEngineManufacturerIgnoreCaseOrderByEngineModel(String engineManufacturer);

    // Buscar por modelo de motor
    List<Specifications> findByEngineModelIgnoreCaseOrderByAircraftModel_Name(String engineModel);

    // Rangos de dimensiones
    @Query("SELECT s FROM Specifications s " +
            "WHERE (:minLength IS NULL OR s.lengthM >= :minLength) " +
            "AND (:maxLength IS NULL OR s.lengthM <= :maxLength) " +
            "AND (:minWingspan IS NULL OR s.wingspanM >= :minWingspan) " +
            "AND (:maxWingspan IS NULL OR s.wingspanM <= :maxWingspan)")
    List<Specifications> findByDimensionRanges(@Param("minLength") BigDecimal minLength,
                                               @Param("maxLength") BigDecimal maxLength,
                                               @Param("minWingspan") BigDecimal minWingspan,
                                               @Param("maxWingspan") BigDecimal maxWingspan);

    // Rangos de peso
    @Query("SELECT s FROM Specifications s " +
            "WHERE (:minWeight IS NULL OR s.maxTakeoffWeightKg >= :minWeight) " +
            "AND (:maxWeight IS NULL OR s.maxTakeoffWeightKg <= :maxWeight)")
    List<Specifications> findByWeightRange(@Param("minWeight") BigDecimal minWeight,
                                           @Param("maxWeight") BigDecimal maxWeight);

    // Rangos de velocidad
    @Query("SELECT s FROM Specifications s " +
            "WHERE (:minSpeed IS NULL OR s.maxSpeedKmh >= :minSpeed) " +
            "AND (:maxSpeed IS NULL OR s.maxSpeedKmh <= :maxSpeed)")
    List<Specifications> findBySpeedRange(@Param("minSpeed") Integer minSpeed,
                                          @Param("maxSpeed") Integer maxSpeed);

    // Estadísticas de dimensiones
    @Query("SELECT " +
            "AVG(s.lengthM), MAX(s.lengthM), MIN(s.lengthM), " +
            "AVG(s.wingspanM), MAX(s.wingspanM), MIN(s.wingspanM), " +
            "AVG(s.heightM), MAX(s.heightM), MIN(s.heightM) " +
            "FROM Specifications s " +
            "WHERE s.lengthM IS NOT NULL AND s.wingspanM IS NOT NULL AND s.heightM IS NOT NULL")
    Object[] getDimensionStatistics();

    // Estadísticas de peso
    @Query("SELECT " +
            "AVG(s.emptyWeightKg), MAX(s.emptyWeightKg), MIN(s.emptyWeightKg), " +
            "AVG(s.maxTakeoffWeightKg), MAX(s.maxTakeoffWeightKg), MIN(s.maxTakeoffWeightKg), " +
            "AVG(s.maxPayloadKg), MAX(s.maxPayloadKg), MIN(s.maxPayloadKg) " +
            "FROM Specifications s " +
            "WHERE s.emptyWeightKg IS NOT NULL")
    Object[] getWeightStatistics();

    // Fabricantes de motores únicos
    @Query("SELECT DISTINCT s.engineManufacturer FROM Specifications s " +
            "WHERE s.engineManufacturer IS NOT NULL " +
            "ORDER BY s.engineManufacturer")
    List<String> findDistinctEngineManufacturers();

    // Modelos de motor únicos
    @Query("SELECT DISTINCT s.engineModel FROM Specifications s " +
            "WHERE s.engineModel IS NOT NULL " +
            "ORDER BY s.engineModel")
    List<String> findDistinctEngineModels();

    // Autoridades de certificación únicas
    @Query("SELECT DISTINCT s.certificationAuthorities FROM Specifications s " +
            "WHERE s.certificationAuthorities IS NOT NULL " +
            "ORDER BY s.certificationAuthorities")
    List<String> findDistinctCertificationAuthorities();

    // Especificaciones más eficientes en combustible
    @Query("SELECT s FROM Specifications s " +
            "WHERE s.fuelConsumptionLph IS NOT NULL " +
            "AND s.aircraftModel.maxPassengers IS NOT NULL " +
            "ORDER BY (s.fuelConsumptionLph / s.aircraftModel.maxPassengers) ASC")
    List<Specifications> findMostFuelEfficientPerPassenger();

    // Top aeronaves por empuje total
    @Query("SELECT s FROM Specifications s " +
            "WHERE s.totalThrustN IS NOT NULL " +
            "ORDER BY s.totalThrustN DESC")
    List<Specifications> findByHighestThrust();
}
