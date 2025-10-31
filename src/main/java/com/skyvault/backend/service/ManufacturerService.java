package com.skyvault.backend.service;

import com.skyvault.backend.dto.request.ManufacturerFilterDto;
import com.skyvault.backend.dto.response.ManufacturerDto;
import com.skyvault.backend.dto.response.ManufacturerStatisticsDto;
import com.skyvault.backend.dto.response.ManufacturerSummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para operaciones con fabricantes.
 */
public interface ManufacturerService {

    /**
     * Buscar fabricantes con filtros y paginación
     */
    PagedResponseDto<ManufacturerDto> findManufacturers(ManufacturerFilterDto filterDto);

    /**
     * Obtener fabricante por ID
     */
    Optional<ManufacturerDto> findById(Long id);

    /**
     * Obtener fabricante por nombre
     */
    Optional<ManufacturerDto> findByName(String name);

    /**
     * Verificar si existe un fabricante por ID
     */
    boolean existsById(Long id);

    /**
     * Verificar si existe un fabricante por nombre
     */
    boolean existsByName(String name);

    /**
     * Contar fabricantes total
     */
    long count();

    /**
     * Contar fabricantes activos (con aeronaves activas)
     */
    long countActive();

    /**
     * Obtener todos los fabricantes resumidos (para listas desplegables)
     */
    List<ManufacturerSummaryDto> findAllSummary();

    /**
     * Obtener fabricantes activos resumidos
     */
    List<ManufacturerSummaryDto> findActiveSummary();

    /**
     * Búsqueda de fabricantes por texto
     */
    List<ManufacturerSummaryDto> searchByText(String query, int limit);

    /**
     * Obtener fabricantes por país
     */
    List<ManufacturerDto> findByCountry(String country, Pageable pageable);

    /**
     * Obtener países disponibles
     */
    List<String> getAvailableCountries();

    /**
     * Obtener estadísticas de fabricantes
     */
    ManufacturerStatisticsDto getStatistics();
}
