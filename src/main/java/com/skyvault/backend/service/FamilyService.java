package com.skyvault.backend.service;

import com.skyvault.backend.dto.request.FamilyFilterDto;
import com.skyvault.backend.dto.response.FamilyDto;
import com.skyvault.backend.dto.response.FamilyStatisticsDto;
import com.skyvault.backend.dto.response.FamilySummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para operaciones con familias de aeronaves.
 */
public interface FamilyService {

    /**
     * Buscar familias con filtros y paginación
     */
    PagedResponseDto<FamilyDto> findFamilies(FamilyFilterDto filterDto);

    /**
     * Obtener familia por ID
     */
    Optional<FamilyDto> findById(Long id);

    /**
     * Verificar si existe una familia por ID
     */
    boolean existsById(Long id);

    /**
     * Contar familias total
     */
    long count();

    /**
     * Contar familias activas (con aeronaves activas)
     */
    long countActive();

    /**
     * Contar familias por fabricante
     */
    long countByManufacturer(Long manufacturerId);

    /**
     * Obtener todas las familias resumidas (para listas desplegables)
     */
    List<FamilySummaryDto> findAllSummary();

    /**
     * Obtener familias por fabricante
     */
    List<FamilyDto> findByManufacturer(Long manufacturerId, Pageable pageable);

    /**
     * Obtener familias resumidas por fabricante
     */
    List<FamilySummaryDto> findSummaryByManufacturer(Long manufacturerId);

    /**
     * Obtener familias por categoría
     */
    List<FamilyDto> findByCategory(String category, Pageable pageable);

    /**
     * Búsqueda de familias por texto
     */
    List<FamilySummaryDto> searchByText(String query, int limit);

    /**
     * Obtener categorías disponibles
     */
    List<String> getAvailableCategories();

    /**
     * Obtener familias más populares (por número de modelos)
     */
    List<FamilyDto> getPopularFamilies(int limit);

    /**
     * Obtener estadísticas de familias
     */
    FamilyStatisticsDto getStatistics();
}
