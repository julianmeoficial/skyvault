package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.mapper.*;
import com.skyvault.backend.model.*;
import com.skyvault.backend.repository.*;
import com.skyvault.backend.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    private final TypeRepository typeRepository;
    private final ProductionStateRepository productionStateRepository;
    private final SizeCategoryRepository sizeCategoryRepository;
    private final TypeMapper typeMapper;
    private final ProductionStateMapper productionStateMapper;
    private final SizeCategoryMapper sizeCategoryMapper;

    @Autowired
    public CatalogServiceImpl(TypeRepository typeRepository,
                              ProductionStateRepository productionStateRepository,
                              SizeCategoryRepository sizeCategoryRepository,
                              TypeMapper typeMapper,
                              ProductionStateMapper productionStateMapper,
                              SizeCategoryMapper sizeCategoryMapper) {
        this.typeRepository = typeRepository;
        this.productionStateRepository = productionStateRepository;
        this.sizeCategoryRepository = sizeCategoryRepository;
        this.typeMapper = typeMapper;
        this.productionStateMapper = productionStateMapper;
        this.sizeCategoryMapper = sizeCategoryMapper;
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'allCatalogs'")
    public CatalogSummaryDto getAllCatalogs() {
        logger.debug("Getting all catalogs");

        CatalogSummaryDto summary = new CatalogSummaryDto();

        // Obtener todos los catálogos
        List<TypeDto> types = getAllTypes();
        List<ProductionStateDto> states = getAllProductionStates();
        List<SizeCategoryDto> categories = getAllSizeCategories();

        summary.setTypes(types);
        summary.setProductionStates(states);
        summary.setSizeCategories(categories);

        // Metadatos
        summary.setTypesCount(types.size());
        summary.setProductionStatesCount(states.size());
        summary.setSizeCategoriesCount(categories.size());
        summary.setLastUpdated(java.time.LocalDateTime.now());

        return summary;
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'allTypes'")
    public List<TypeDto> getAllTypes() {
        logger.debug("Getting all types");

        List<Type> types = typeRepository.findAll(Sort.by("name"));
        return typeMapper.toDtoList(types);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'activeTypes'")
    public List<TypeDto> getActiveTypes() {
        logger.debug("Getting active types");

        List<Type> types = typeRepository.findByIsActiveTrueOrderByName();
        return typeMapper.toDtoList(types);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'type:' + #id")
    public Optional<TypeDto> getTypeById(Long id) {
        logger.debug("Getting type by ID: {}", id);

        return typeRepository.findById(id)
                .map(typeMapper::toDto);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'typeByName:' + #name")
    public Optional<TypeDto> getTypeByName(String name) {
        logger.debug("Getting type by name: {}", name);

        return typeRepository.findByNameIgnoreCase(name)
                .map(typeMapper::toDto);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'allProductionStates'")
    public List<ProductionStateDto> getAllProductionStates() {
        logger.debug("Getting all production states");

        List<ProductionState> states = productionStateRepository.findAll(Sort.by("name"));
        return productionStateMapper.toDtoList(states);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'activeProductionStates'")
    public List<ProductionStateDto> getActiveProductionStates() {
        logger.debug("Getting active production states");

        List<ProductionState> states = productionStateRepository.findByIsActiveTrueOrderByName();
        return productionStateMapper.toDtoList(states);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'productionState:' + #id")
    public Optional<ProductionStateDto> getProductionStateById(Long id) {
        logger.debug("Getting production state by ID: {}", id);

        return productionStateRepository.findById(id)
                .map(productionStateMapper::toDto);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'productionStateByName:' + #name")
    public Optional<ProductionStateDto> getProductionStateByName(String name) {
        logger.debug("Getting production state by name: {}", name);

        return productionStateRepository.findByNameIgnoreCase(name)
                .map(productionStateMapper::toDto);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'allSizeCategories'")
    public List<SizeCategoryDto> getAllSizeCategories() {
        logger.debug("Getting all size categories");

        List<SizeCategory> categories = sizeCategoryRepository.findAll(Sort.by("minPassengers"));
        return sizeCategoryMapper.toDtoList(categories);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'activeSizeCategories'")
    public List<SizeCategoryDto> getActiveSizeCategories() {
        logger.debug("Getting active size categories");

        List<SizeCategory> categories = sizeCategoryRepository.findByIsActiveTrueOrderByMinPassengers();
        return sizeCategoryMapper.toDtoList(categories);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'sizeCategory:' + #id")
    public Optional<SizeCategoryDto> getSizeCategoryById(Long id) {
        logger.debug("Getting size category by ID: {}", id);

        return sizeCategoryRepository.findById(id)
                .map(sizeCategoryMapper::toDto);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'sizeCategoryByName:' + #name")
    public Optional<SizeCategoryDto> getSizeCategoryByName(String name) {
        logger.debug("Getting size category by name: {}", name);

        return sizeCategoryRepository.findByNameIgnoreCase(name)
                .map(sizeCategoryMapper::toDto);
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'determineSizeCategory:' + #passengers")
    public SizeCategoryDto determineSizeCategory(int passengers) {
        logger.debug("Determining size category for {} passengers", passengers);

        // Buscar la categoría apropiada basada en el número de pasajeros
        Optional<SizeCategory> category = sizeCategoryRepository
                .findTopByMinPassengersLessThanEqualAndMaxPassengersGreaterThanEqual(
                        passengers, passengers
                );

        if (category.isPresent()) {
            return sizeCategoryMapper.toDto(category.get());
        }

        // Si no se encuentra una categoría exacta, buscar la más cercana
        List<SizeCategory> allCategories = sizeCategoryRepository.findAll(
                Sort.by("minPassengers")
        );

        for (SizeCategory cat : allCategories) {
            if (passengers >= cat.getMinPassengers() &&
                    passengers <= cat.getMaxPassengers()) {
                return sizeCategoryMapper.toDto(cat);
            }
        }

        // Fallback: retornar la primera categoría si no se encuentra ninguna
        if (!allCategories.isEmpty()) {
            logger.warn("No exact size category found for {} passengers, returning first available", passengers);
            return sizeCategoryMapper.toDto(allCategories.get(0));
        }

        // Si no hay categorías, crear una por defecto
        logger.error("No size categories found in database");
        SizeCategoryDto defaultCategory = new SizeCategoryDto();
        defaultCategory.setName("Unknown");
        defaultCategory.setDescription("Unknown size category");
        defaultCategory.setMinPassengers(0);
        defaultCategory.setMaxPassengers(1000);
        return defaultCategory;
    }

    @Override
    public List<SizeCategoryDto> getSizeCategoriesForPassengerRange(int minPassengers, int maxPassengers) {
        logger.debug("Getting size categories for passenger range: {} - {}", minPassengers, maxPassengers);

        List<SizeCategory> categories = sizeCategoryRepository
                .findCategoriesInPassengerRange(minPassengers, maxPassengers);

        return sizeCategoryMapper.toDtoList(categories);
    }

    @Override
    public long countAircraftByType(Long typeId) {
        return typeRepository.countAircraftByTypeId(typeId);
    }

    @Override
    public long countAircraftByProductionState(Long stateId) {
        return productionStateRepository.countAircraftByProductionStateId(stateId);
    }

    @Override
    public long countAircraftBySizeCategory(Long categoryId) {
        return sizeCategoryRepository.countAircraftBySizeCategoryId(categoryId);
    }

    @Override
    public boolean isTypeInUse(Long typeId) {
        return countAircraftByType(typeId) > 0;
    }

    @Override
    public boolean isProductionStateInUse(Long stateId) {
        return countAircraftByProductionState(stateId) > 0;
    }

    @Override
    public boolean isSizeCategoryInUse(Long categoryId) {
        return countAircraftBySizeCategory(categoryId) > 0;
    }

    @Override
    @Cacheable(value = "catalogCache", key = "'catalogStatistics'")
    public Map<String, Object> getCatalogStatistics() {
        logger.debug("Getting catalog statistics");

        Map<String, Object> stats = new java.util.HashMap<>();

        // Estadísticas de tipos
        List<TypeDto> types = getAllTypes();
        Map<String, Object> typeStats = new java.util.HashMap<>();
        typeStats.put("total", types.size());
        typeStats.put("active", types.stream().mapToInt(t -> Boolean.TRUE.equals(t.getIsActive()) ? 1 : 0).sum());
        typeStats.put("withAircraft", types.stream().mapToLong(t -> countAircraftByType(t.getId())).sum());
        stats.put("types", typeStats);

        // Estadísticas de estados de producción
        List<ProductionStateDto> states = getAllProductionStates();
        Map<String, Object> stateStats = new java.util.HashMap<>();
        stateStats.put("total", states.size());
        stateStats.put("active", states.stream().mapToInt(s -> Boolean.TRUE.equals(s.getIsActive()) ? 1 : 0).sum());
        stateStats.put("withAircraft", states.stream().mapToLong(s -> countAircraftByProductionState(s.getId())).sum());
        stats.put("productionStates", stateStats);

        // Estadísticas de categorías de tamaño
        List<SizeCategoryDto> categories = getAllSizeCategories();
        Map<String, Object> categoryStats = new java.util.HashMap<>();
        categoryStats.put("total", categories.size());
        categoryStats.put("active", categories.stream().mapToInt(c -> Boolean.TRUE.equals(c.getIsActive()) ? 1 : 0).sum());
        categoryStats.put("withAircraft", categories.stream().mapToLong(c -> countAircraftBySizeCategory(c.getId())).sum());
        stats.put("sizeCategories", categoryStats);

        return stats;
    }

    @Override
    public void refreshCatalogs() {
        logger.info("Refreshing all catalog caches");

        // En una implementación real, aquí:
        // 1. Invalidar caches específicos
        // 2. Recargar datos desde fuentes externas
        // 3. Validar integridad de datos
        // 4. Notificar a otros servicios

        // Por ahora, simplemente invalidamos los caches
        // (esto se haría mediante @CacheEvict en un método separado)
    }

    @Override
    public boolean existsTypeById(Long id) {
        return typeRepository.existsById(id);
    }

    @Override
    public boolean existsProductionStateById(Long id) {
        return productionStateRepository.existsById(id);
    }

    @Override
    public boolean existsSizeCategoryById(Long id) {
        return sizeCategoryRepository.existsById(id);
    }

    @Override
    public void updateCatalogCounts() {
        logger.info("Updating catalog counts");

        // En una implementación real aquí se actualizarían contadores
        // Por ahora, es un placeholder para funcionalidad futura
    }

    @Override
    public void evictCatalogCache() {
        logger.info("Evicting catalog caches");

        // En una implementación real se invalidarían los caches
        // Por ahora, es un placeholder para funcionalidad futura
    }
}
