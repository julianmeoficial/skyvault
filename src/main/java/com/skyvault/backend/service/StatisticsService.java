package com.skyvault.backend.service;

import com.skyvault.backend.dto.response.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Servicio para generar estadísticas y métricas del sistema.
 */
public interface StatisticsService {

    /**
     * Obtener estadísticas generales del sistema
     */
    SystemStatisticsDto getSystemStatistics();

    /**
     * Obtener estadísticas de aeronaves
     */
    AircraftStatisticsDto getAircraftStatistics();

    /**
     * Obtener estadísticas de fabricantes
     */
    ManufacturerStatisticsDto getManufacturerStatistics();

    /**
     * Obtener estadísticas de familias
     */
    FamilyStatisticsDto getFamilyStatistics();

    /**
     * Obtener estadísticas de búsquedas
     */
    SearchStatisticsDto getSearchStatistics();

    /**
     * Obtener distribución por fabricante
     */
    Map<String, Long> getManufacturerDistribution();

    /**
     * Obtener distribución por tipo de aeronave
     */
    Map<String, Long> getTypeDistribution();

    /**
     * Obtener distribución por estado de producción
     */
    Map<String, Long> getProductionStateDistribution();

    /**
     * Obtener distribución por categoría de tamaño
     */
    Map<String, Long> getSizeCategoryDistribution();

    /**
     * Obtener distribución por rango de años de introducción
     */
    Map<String, Long> getIntroductionYearDistribution();

    /**
     * Obtener estadísticas de uso por período
     */
    Map<LocalDate, Long> getUsageStatistics(LocalDate from, LocalDate to);

    /**
     * Obtener aeronaves más buscadas
     */
    List<AircraftPopularityDto> getMostSearchedAircraft(int limit);

    /**
     * Obtener comparaciones más frecuentes
     */
    List<ComparisonPopularityDto> getMostFrequentComparisons(int limit);

    /**
     * Registrar vista de aeronave
     */
    void recordAircraftView(Long aircraftId);

    /**
     * Registrar comparación
     */
    void recordComparison(List<Long> aircraftIds);

    /**
     * Registrar búsqueda
     */
    void recordSearch(String query, String type, int resultsCount);

    /**
     * Actualizar estadísticas (ejecutar periódicamente)
     */
    void updateStatistics();

    /**
     * Limpiar estadísticas antiguas
     */
    void cleanOldStatistics(int daysToKeep);

    /**
     * Resetear estadísticas
     */
    void resetStatistics();

    /**
     * Obtener métricas del sistema
     */
    Map<String, Long> getSystemMetrics();

    /**
     * Incrementar métrica
     */
    void incrementMetric(String metricName);

    /**
     * Incrementar métrica con valor específico
     */
    void incrementMetric(String metricName, long increment);

    /**
     * Obtener valor de métrica
     */
    long getMetricValue(String metricName);
}
