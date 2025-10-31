package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.request.AircraftFilterDto;
import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.request.SimilarAircraftRequestDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.mapper.AircraftMapper;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.model.Manufacturer;
import com.skyvault.backend.repository.AircraftModelRepository;
import com.skyvault.backend.service.AircraftService;
import com.skyvault.backend.service.ComparisonService;
import com.skyvault.backend.specification.AircraftSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AircraftServiceImpl implements AircraftService {

    private static final Logger logger = LoggerFactory.getLogger(AircraftServiceImpl.class);

    private final AircraftModelRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;
    private final ComparisonService comparisonService;

    @Autowired
    public AircraftServiceImpl(AircraftModelRepository aircraftRepository,
                               AircraftMapper aircraftMapper,
                               ComparisonService comparisonService) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftMapper = aircraftMapper;
        this.comparisonService = comparisonService;
    }

    @Override
    public PagedResponseDto<AircraftCardDto> findAircraft(AircraftFilterDto filterDto) {
        logger.debug("Finding aircraft with filters: manufacturerId={}, searchTerm={}, onlyActive={}",
                filterDto.getManufacturerId(), filterDto.getSearchTerm(), filterDto.getOnlyActive());

        // Parsear y mapear el sort del frontend
        String sortField = mapSortField(filterDto.getSortField());
        Sort.Direction sortDirection = parseSortDirection(filterDto.getSortDirection());

        logger.debug("Mapped sort: field={}, direction={}", sortField, sortDirection);

        // METODO hasAdvancedFilters de SPECIFICATION
        Specification<AircraftModel> spec = AircraftSpecification.hasAdvancedFilters(
                filterDto.getManufacturerId(),
                filterDto.getManufacturerIds(),
                filterDto.getFamilyId(),
                filterDto.getFamilyIds(),
                filterDto.getTypeId(),
                filterDto.getTypeIds(),
                filterDto.getProductionStateId(),
                filterDto.getProductionStateIds(),
                filterDto.getSizeCategoryId(),
                filterDto.getSizeCategoryIds(),
                filterDto.getMinPassengers(),
                filterDto.getMaxPassengers(),
                filterDto.getMinRange(),
                filterDto.getMaxRange(),
                filterDto.getMinIntroductionYear(),
                filterDto.getMaxIntroductionYear(),
                filterDto.getMinCruiseSpeed(),
                filterDto.getMaxCruiseSpeed(),
                filterDto.getSearchTerm(),
                filterDto.getOnlyActive(),
                filterDto.getOnlyWithSpecifications(),
                filterDto.getOnlyWithImages()
        );

        // Crear ordenamiento con las variables mapeadas
        Sort sort = Sort.by(sortDirection, sortField);

        // Crear paginación
        Pageable pageable = PageRequest.of(
                filterDto.getPage() != null ? filterDto.getPage() : 0,
                filterDto.getSize() != null ? filterDto.getSize() : 20,
                sort
        );

        // Ejecutar consulta con Specification
        Page<AircraftModel> page = aircraftRepository.findAll(spec, pageable);

        logger.debug("Found {} aircraft, page {} of {}",
                page.getTotalElements(), page.getNumber() + 1, page.getTotalPages());

        // Mapear a DTOs
        return aircraftMapper.toPagedCardResponse(page);
    }

    /**
     * Mapea valores semánticos de ordenamiento a campos reales de la entidad
     */
    private String mapSortField(String sortField) {
        if (sortField == null || sortField.trim().isEmpty()) {
            return "name"; // Default
        }

        // Normalizar el campo
        String normalized = sortField.trim().toLowerCase();

        // Mapeo semántico: frontend -> backend
        switch (normalized) {
            // Ordenamiento alfabético
            case "name":
            case "nombre":
            case "alphabetical":
            case "alfabetico":
                return "name";

            // Capacidad de pasajeros
            case "capacity":
            case "capacidad":
            case "passengers":
            case "pasajeros":
            case "maxpassengers":
            case "max_passengers":
                return "maxPassengers";

            // Alcance
            case "range":
            case "alcance":
            case "rangekm":
            case "range_km":
                return "rangeKm";

            // Año de introducción
            case "year":
            case "año":
            case "introductionyear":
            case "introduction_year":
            case "fecha":
                return "introductionYear";

            // Velocidad de crucero
            case "speed":
            case "velocidad":
            case "cruisespeed":
            case "cruise_speed":
                return "cruiseSpeedKnots";

            // Fabricante
            case "manufacturer":
            case "fabricante":
                return "manufacturer.name";

            // Categoría de tamaño
            case "size":
            case "tamaño":
            case "category":
            case "categoria":
                return "sizeCategory.name";

            // Tipo
            case "type":
            case "tipo":
                return "type.name";

            // Si es un campo válido directo, devolverlo
            default:
                // Validar que el campo esté en la lista permitida
                String[] allowedFields = {
                        "id", "name", "model", "displayName", "introductionYear",
                        "maxPassengers", "typicalPassengers", "rangeKm", "cruiseSpeedKnots",
                        "manufacturer.name", "family.name", "type.name",
                        "productionState.name", "sizeCategory.name", "createdAt", "updatedAt"
                };

                for (String allowed : allowedFields) {
                    if (allowed.equalsIgnoreCase(sortField)) {
                        return allowed;
                    }
                }

                // Si no es válido, devolver default
                logger.warn("Invalid sort field '{}', using default 'name'", sortField);
                return "name";
        }
    }

    /**
     * Parsea la dirección de ordenamiento
     */
    private Sort.Direction parseSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.trim().isEmpty()) {
            return Sort.Direction.ASC; // Default
        }

        String normalized = sortDirection.trim().toLowerCase();

        // Soportar múltiples formatos
        switch (normalized) {
            case "desc":
            case "descending":
            case "descendente":
            case "mayor":
            case "down":
            case "z-a":
                return Sort.Direction.DESC;

            case "asc":
            case "ascending":
            case "ascendente":
            case "menor":
            case "up":
            case "a-z":
            default:
                return Sort.Direction.ASC;
        }
    }

    @Override
    @Cacheable(value = "aircraftCache", key = "#id")
    public Optional<AircraftDetailDto> findById(Long id) {
        logger.debug("Finding aircraft by ID: {}", id);
        Optional<AircraftModel> aircraft = aircraftRepository.findById(id);
        return aircraft.map(aircraftMapper::toDetailDto);
    }

    @Override
    @Cacheable(value = "aircraftCache", key = "'slug:' + #identifier")
    public Optional<AircraftDetailDto> findByIdentifier(String identifier) {
        logger.debug("Finding aircraft by identifier: {}", identifier);

        // Limpiar el identifier: convertir guiones a espacios para buscar en BD
        String cleanIdentifier = identifier.trim().replace("-", " ");

        logger.debug("Cleaned identifier for search: {}", cleanIdentifier);

        Optional<AircraftModel> aircraft = aircraftRepository.findByNameOrModelIgnoreCase(cleanIdentifier);

        if (aircraft.isEmpty()) {
            logger.warn("Aircraft not found with identifier: {}", identifier);
        } else {
            logger.debug("Aircraft found: {} (ID: {})", aircraft.get().getName(), aircraft.get().getId());
        }

        return aircraft.map(aircraftMapper::toDetailDto);
    }

    @Override
    public boolean existsById(Long id) {
        return aircraftRepository.existsById(id);
    }

    @Override
    public long count() {
        return aircraftRepository.count();
    }

    @Override
    public long countActive() {
        return aircraftRepository.countByIsActiveTrue();
    }

    @Override
    public long countByManufacturer(Long manufacturerId) {
        return aircraftRepository.countByManufacturerIdAndIsActiveTrue(manufacturerId);
    }

    @Override
    public long countByFamily(Long familyId) {
        return aircraftRepository.countByFamilyIdAndIsActiveTrue(familyId);
    }

    @Override
    @Cacheable(value = "aircraftListCache", key = "'allActive'")
    public List<AircraftCardDto> findAllActive() {
        logger.debug("Finding all active aircraft");
        List<AircraftModel> activeAircraft = aircraftRepository.findByIsActiveTrueOrderByName();
        return aircraftMapper.toCardDtoList(activeAircraft);
    }

    @Override
    public List<AircraftCardDto> findByManufacturer(Long manufacturerId, Pageable pageable) {
        logger.debug("Finding aircraft by manufacturer: {}", manufacturerId);
        Page<AircraftModel> page = aircraftRepository.findByManufacturerIdAndIsActiveTrue(manufacturerId, pageable);
        return aircraftMapper.toCardDtoList(page.getContent());
    }

    @Override
    public List<AircraftCardDto> findByFamily(Long familyId, Pageable pageable) {
        logger.debug("Finding aircraft by family: {}", familyId);
        Page<AircraftModel> page = aircraftRepository.findByFamilyIdAndIsActiveTrue(familyId, pageable);
        return aircraftMapper.toCardDtoList(page.getContent());
    }

    @Override
    public List<AircraftCardDto> findByType(Long typeId, Pageable pageable) {
        logger.debug("Finding aircraft by type: {}", typeId);
        Page<AircraftModel> page = aircraftRepository.findByTypeIdAndIsActiveTrue(typeId, pageable);
        return aircraftMapper.toCardDtoList(page.getContent());
    }

    @Override
    public List<AircraftCardDto> findSimilarAircraft(SimilarAircraftRequestDto request) {
        logger.debug("Finding similar aircraft for: {}", request.getAircraftId());
        Pageable pageable = PageRequest.of(0, request.getLimit());
        Optional<AircraftModel> baseAircraft = aircraftRepository.findById(request.getAircraftId());
        if (baseAircraft.isEmpty()) {
            return List.of();
        }
        AircraftModel base = baseAircraft.get();
        int minPassengers = Math.max(1, base.getMaxPassengers() - request.getPassengerTolerance());
        int maxPassengers = base.getMaxPassengers() + request.getPassengerTolerance();
        List<AircraftModel> similar = aircraftRepository.findByMaxPassengersBetweenAndIsActiveTrueAndIdNot(
                minPassengers, maxPassengers, request.getAircraftId(), pageable
        ).getContent();
        return aircraftMapper.toCardDtoList(similar);
    }

    @Override
    public CompareResultDto compareAircraft(CompareRequestDto request) {
        logger.debug("Comparing aircraft: {}", request.getAircraftIds());
        return comparisonService.compareAircraft(request);
    }

    @Override
    public List<AircraftCardDto> searchByText(String query, int limit) {
        logger.debug("Searching aircraft by text: '{}', limit: {}", query, limit);
        Pageable pageable = PageRequest.of(0, limit);
        Page<AircraftModel> results = aircraftRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(query, pageable);
        return aircraftMapper.toCardDtoList(results.getContent());
    }

    @Override
    @Cacheable(value = "aircraftListCache", key = "'featured:' + #limit")
    public List<AircraftCardDto> getFeaturedAircraft(int limit) {
        logger.debug("Getting featured aircraft, limit: {}", limit);
        Pageable pageable = PageRequest.of(0, limit);
        Page<AircraftModel> featured = aircraftRepository.findByIsActiveTrueOrderByMaxPassengersDesc(pageable);
        return aircraftMapper.toCardDtoList(featured.getContent());
    }

    @Override
    @Cacheable(value = "aircraftListCache", key = "'popular:' + #limit")
    public List<AircraftCardDto> getPopularAircraft(int limit) {
        logger.debug("Getting popular aircraft, limit: {}", limit);
        Pageable pageable = PageRequest.of(0, limit);
        Page<AircraftModel> popular = aircraftRepository.findByIsActiveTrueOrderByIntroductionYearDesc(pageable);
        return aircraftMapper.toCardDtoList(popular.getContent());
    }

    @Override
    @Cacheable(value = "aircraftListCache", key = "'newest:' + #limit")
    public List<AircraftCardDto> getNewestAircraft(int limit) {
        logger.debug("Getting newest aircraft, limit: {}", limit);
        Pageable pageable = PageRequest.of(0, limit);
        Page<AircraftModel> newest = aircraftRepository.findByIsActiveTrueOrderByIntroductionYearDesc(pageable);
        return aircraftMapper.toCardDtoList(newest.getContent());
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'aircraft'")
    public AircraftStatisticsDto getStatistics() {
        logger.debug("Getting aircraft statistics");
        long total = count();
        long active = countActive();
        AircraftStatisticsDto stats = new AircraftStatisticsDto();
        stats.setTotalAircraft(total);
        stats.setActiveAircraft(active);
        try {
            Double avgPassengers = aircraftRepository.getAveragePassengers();
            stats.setAveragePassengers(avgPassengers != null ? avgPassengers : 185.0);
        } catch (Exception e) {
            stats.setAveragePassengers(185.0);
        }
        try {
            Double avgRange = aircraftRepository.getAverageRange();
            stats.setAverageRange(avgRange != null ? avgRange : 6000.0);
        } catch (Exception e) {
            stats.setAverageRange(6000.0);
        }
        try {
            Optional<AircraftModel> largest = aircraftRepository.findTopByIsActiveTrueOrderByMaxPassengersDesc();
            largest.ifPresent(model -> stats.setLargestAircraft(model.getName()));
        } catch (Exception e) {
            logger.warn("Could not find largest aircraft");
        }
        try {
            Optional<AircraftModel> longestRange = aircraftRepository.findTopByIsActiveTrueOrderByRangeKmDesc();
            longestRange.ifPresent(model -> stats.setLongestRangeAircraft(model.getName()));
        } catch (Exception e) {
            logger.warn("Could not find longest range aircraft");
        }
        try {
            Optional<Integer> newest = aircraftRepository.findMaxIntroductionYear();
            stats.setNewestYear(newest.orElse(2024));
        } catch (Exception e) {
            stats.setNewestYear(2024);
        }
        try {
            Optional<Integer> oldest = aircraftRepository.findMinIntroductionYear();
            stats.setOldestYear(oldest.orElse(1970));
        } catch (Exception e) {
            stats.setOldestYear(1970);
        }
        return stats;
    }

    @Override
    @CacheEvict(value = "aircraftCache", key = "#id")
    public void evictCache(Long id) {
        logger.debug("Evicting cache for aircraft ID: {}", id);
    }

    @Override
    @CacheEvict(value = {"aircraftCache", "aircraftListCache", "statisticsCache"}, allEntries = true)
    public void evictAllCache() {
        logger.info("Evicting all aircraft caches");
    }
}
