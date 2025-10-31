package com.skyvault.backend.service;

import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.response.CompareResultDto;
import com.skyvault.backend.dto.response.ComparisonSummaryDto;

import java.util.List;

/**
 * Servicio especializado para comparaciones de aeronaves.
 */
public interface ComparisonService {

    /**
     * Comparar aeronaves con configuración personalizada
     */
    CompareResultDto compareAircraft(CompareRequestDto request);

    /**
     * Comparar aeronaves con configuración por defecto
     */
    CompareResultDto compareAircraftDefault(List<Long> aircraftIds);

    /**
     * Generar resumen de comparación
     */
    ComparisonSummaryDto generateSummary(List<Long> aircraftIds);

    /**
     * Validar que las aeronaves existen y están activas
     */
    boolean validateAircraftForComparison(List<Long> aircraftIds);

    /**
     * Obtener comparaciones sugeridas basadas en similaridad
     */
    List<CompareResultDto> getSuggestedComparisons(Long aircraftId, int limit);

    /**
     * Obtener comparaciones populares
     */
    List<CompareResultDto> getPopularComparisons(int limit);

    /**
     * Normalizar unidades según configuración
     */
    CompareResultDto normalizeUnits(CompareResultDto result, String unitSystem);

    /**
     * Exportar comparación a diferentes formatos
     */
    String exportComparison(CompareResultDto result, String format);
}
