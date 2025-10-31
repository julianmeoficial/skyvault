package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.request.SearchRequestDto;
import com.skyvault.backend.dto.response.GlobalSearchResultDto;
import com.skyvault.backend.dto.response.SearchSuggestionDto;
import com.skyvault.backend.mapper.SearchMapper;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.model.Family;
import com.skyvault.backend.model.Manufacturer;
import com.skyvault.backend.repository.AircraftModelRepository;
import com.skyvault.backend.repository.FamilyRepository;
import com.skyvault.backend.repository.ManufacturerRepository;
import com.skyvault.backend.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final AircraftModelRepository aircraftRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final FamilyRepository familyRepository;
    private final SearchMapper searchMapper;

    // Almacenamiento simple de términos de búsqueda (en producción usarías Redis/DB)
    private final ConcurrentHashMap<String, AtomicLong> searchTermCounts = new ConcurrentHashMap<>();

    @Autowired
    public SearchServiceImpl(AircraftModelRepository aircraftRepository,
                             ManufacturerRepository manufacturerRepository,
                             FamilyRepository familyRepository,
                             SearchMapper searchMapper) {
        this.aircraftRepository = aircraftRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.familyRepository = familyRepository;
        this.searchMapper = searchMapper;
    }

    @Override
    @Cacheable(value = "searchCache", key = "#request.query + ':' + #request.type + ':' + #request.limit")
    public List<SearchSuggestionDto> getSuggestions(SearchRequestDto request) {
        logger.debug("Getting suggestions for: '{}', type: {}, limit: {}",
                request.getQuery(), request.getType(), request.getLimit());

        List<SearchSuggestionDto> suggestions = new ArrayList<>();

        switch (request.getType().toLowerCase()) {
            case "aircraft" -> {
                suggestions.addAll(getAircraftSuggestions(request.getQuery(), request.getLimit()));
            }
            case "manufacturers" -> {
                suggestions.addAll(getManufacturerSuggestions(request.getQuery(), request.getLimit()));
            }
            case "families" -> {
                suggestions.addAll(getFamilySuggestions(request.getQuery(), request.getLimit()));
            }
            case "all" -> {
                int limitPerType = Math.max(1, request.getLimit() / 3);
                suggestions.addAll(getAircraftSuggestions(request.getQuery(), limitPerType));
                suggestions.addAll(getManufacturerSuggestions(request.getQuery(), limitPerType));
                suggestions.addAll(getFamilySuggestions(request.getQuery(), limitPerType));

                // Limitar al total solicitado
                if (suggestions.size() > request.getLimit()) {
                    suggestions = suggestions.subList(0, request.getLimit());
                }
            }
        }

        // Calcular relevancia básica
        for (int i = 0; i < suggestions.size(); i++) {
            SearchSuggestionDto suggestion = suggestions.get(i);
            suggestion.setRelevance(100.0 - (i * 5.0)); // Relevancia decreciente
        }

        return suggestions;
    }

    @Override
    public List<SearchSuggestionDto> getAircraftSuggestions(String query, int limit) {
        logger.debug("Getting aircraft suggestions for: '{}', limit: {}", query, limit);

        Pageable pageable = PageRequest.of(0, limit);
        List<AircraftModel> aircraft = aircraftRepository
                .findByNameContainingIgnoreCaseAndIsActiveTrue(query, pageable).getContent();

        return searchMapper.aircraftToSuggestionList(aircraft);
    }

    @Override
    public List<SearchSuggestionDto> getManufacturerSuggestions(String query, int limit) {
        logger.debug("Getting manufacturer suggestions for: '{}', limit: {}", query, limit);

        Pageable pageable = PageRequest.of(0, limit);
        List<Manufacturer> manufacturers = manufacturerRepository
                .findByNameContainingIgnoreCase(query, pageable).getContent();

        return searchMapper.manufacturerToSuggestionList(manufacturers);
    }

    @Override
    public List<SearchSuggestionDto> getFamilySuggestions(String query, int limit) {
        logger.debug("Getting family suggestions for: '{}', limit: {}", query, limit);

        Pageable pageable = PageRequest.of(0, limit);
        List<Family> families = familyRepository
                .findByNameContainingIgnoreCase(query, pageable).getContent();

        return searchMapper.familyToSuggestionList(families);
    }

    @Override
    public GlobalSearchResultDto globalSearch(String query, int limit) {
        logger.debug("Performing global search for: '{}', limit: {}", query, limit);

        GlobalSearchResultDto result = new GlobalSearchResultDto();

        // Buscar en cada tipo
        result.setAircraft(getAircraftSuggestions(query, limit));
        result.setManufacturers(getManufacturerSuggestions(query, limit));
        result.setFamilies(getFamilySuggestions(query, limit));

        // Calcular total de resultados
        int totalResults = result.getAircraft().size() +
                result.getManufacturers().size() +
                result.getFamilies().size();
        result.setTotalResults(totalResults);

        return result;
    }

    @Override
    @Cacheable(value = "searchCache", key = "'popularTerms:' + #limit")
    public List<String> getPopularSearchTerms(int limit) {
        logger.debug("Getting popular search terms, limit: {}", limit);

        return searchTermCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().get() > e1.getValue().get() ? 1 : -1)
                .limit(limit)
                .map(entry -> entry.getKey())
                .toList();
    }

    @Override
    @Transactional
    public void recordSearchTerm(String query) {
        if (query != null && !query.trim().isEmpty()) {
            String normalizedQuery = query.trim().toLowerCase();
            searchTermCounts.computeIfAbsent(normalizedQuery, k -> new AtomicLong(0))
                    .incrementAndGet();

            logger.debug("Recorded search term: '{}', count: {}",
                    normalizedQuery, searchTermCounts.get(normalizedQuery).get());
        }
    }

    @Override
    @CacheEvict(value = "searchCache", allEntries = true)
    public void clearSuggestionCache() {
        logger.info("Clearing search suggestion cache");
    }
}
