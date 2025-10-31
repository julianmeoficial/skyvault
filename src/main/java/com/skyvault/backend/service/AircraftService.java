package com.skyvault.backend.service;

import com.skyvault.backend.dto.request.AircraftFilterDto;
import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.request.SimilarAircraftRequestDto;
import com.skyvault.backend.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Servicio principal para operaciones con aeronaves.
 * Incluye filtros combinables, comparación y búsqueda avanzada.
 */
public interface AircraftService {

    /**
     * Buscar aeronaves con filtros combinables y paginación
     */
    PagedResponseDto<AircraftCardDto> findAircraft(AircraftFilterDto filterDto);

    /**
     * Obtener aeronave por ID con cache
     */
    Optional<AircraftDetailDto> findById(Long id);

    /**
     * Buscar aeronave por nombre o modelo (case-insensitive).
     * Acepta nombres con espacios o guiones (ej: "A350-1000" o "A350 1000").
     *
     * @param identifier Nombre o modelo de la aeronave
     * @return Optional con AircraftDetailDto si se encuentra
     */
    Optional<AircraftDetailDto> findByIdentifier(String identifier);

    /**
     * Verificar si existe una aeronave por ID
     */
    boolean existsById(Long id);

    /**
     * Contar aeronaves total
     */
    long count();

    /**
     * Contar aeronaves activas
     */
    long countActive();

    /**
     * Contar aeronaves por fabricante
     */
    long countByManufacturer(Long manufacturerId);

    /**
     * Contar aeronaves por familia
     */
    long countByFamily(Long familyId);

    /**
     * Obtener todas las aeronaves activas (para comparación)
     */
    List<AircraftCardDto> findAllActive();

    /**
     * Obtener aeronaves por fabricante
     */
    List<AircraftCardDto> findByManufacturer(Long manufacturerId, Pageable pageable);

    /**
     * Obtener aeronaves por familia
     */
    List<AircraftCardDto> findByFamily(Long familyId, Pageable pageable);

    /**
     * Obtener aeronaves por tipo
     */
    List<AircraftCardDto> findByType(Long typeId, Pageable pageable);

    /**
     * Obtener aeronaves similares por especificaciones
     */
    List<AircraftCardDto> findSimilarAircraft(SimilarAircraftRequestDto request);

    /**
     * Comparar múltiples aeronaves
     */
    CompareResultDto compareAircraft(CompareRequestDto request);

    /**
     * Búsqueda de texto completo en aeronaves
     */
    List<AircraftCardDto> searchByText(String query, int limit);

    /**
     * Obtener aeronaves destacadas (por criterio de negocio)
     */
    List<AircraftCardDto> getFeaturedAircraft(int limit);

    /**
     * Obtener aeronaves más populares (por búsquedas/views)
     */
    List<AircraftCardDto> getPopularAircraft(int limit);

    /**
     * Obtener aeronaves más recientes (por año de introducción)
     */
    List<AircraftCardDto> getNewestAircraft(int limit);

    /**
     * Obtener estadísticas generales de aeronaves
     */
    AircraftStatisticsDto getStatistics();

    /**
     * Invalidar cache de una aeronave específica
     */
    void evictCache(Long id);

    /**
     * Invalidar todo el cache de aeronaves
     */
    void evictAllCache();
}
