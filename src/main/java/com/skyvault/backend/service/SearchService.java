package com.skyvault.backend.service;

import com.skyvault.backend.dto.request.SearchRequestDto;
import com.skyvault.backend.dto.response.GlobalSearchResultDto;
import com.skyvault.backend.dto.response.SearchSuggestionDto;

import java.util.List;

/**
 * Servicio para búsqueda global y sugerencias.
 */
public interface SearchService {

    /**
     * Obtener sugerencias de búsqueda combinando aeronaves, fabricantes y familias
     */
    List<SearchSuggestionDto> getSuggestions(SearchRequestDto request);

    /**
     * Sugerencias solo de aeronaves
     */
    List<SearchSuggestionDto> getAircraftSuggestions(String query, int limit);

    /**
     * Sugerencias solo de fabricantes
     */
    List<SearchSuggestionDto> getManufacturerSuggestions(String query, int limit);

    /**
     * Sugerencias solo de familias
     */
    List<SearchSuggestionDto> getFamilySuggestions(String query, int limit);

    /**
     * Búsqueda global con resultados mixtos
     */
    GlobalSearchResultDto globalSearch(String query, int limit);

    /**
     * Obtener términos de búsqueda populares
     */
    List<String> getPopularSearchTerms(int limit);

    /**
     * Registrar término de búsqueda (para estadísticas)
     */
    void recordSearchTerm(String query);

    /**
     * Limpiar cache de sugerencias
     */
    void clearSuggestionCache();
}
