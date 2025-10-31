package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.request.FamilyFilterDto;
import com.skyvault.backend.dto.response.FamilyDto;
import com.skyvault.backend.dto.response.FamilyStatisticsDto;
import com.skyvault.backend.dto.response.FamilySummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.mapper.FamilyMapper;
import com.skyvault.backend.model.Family;
import com.skyvault.backend.repository.FamilyRepository;
import com.skyvault.backend.service.FamilyService;
import com.skyvault.backend.specification.FamilySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FamilyServiceImpl implements FamilyService {

    private static final Logger logger = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository,
                             FamilyMapper familyMapper) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
    }

    @Override
    public PagedResponseDto<FamilyDto> findFamilies(FamilyFilterDto filterDto) {
        logger.debug("Finding families with filters: {}", filterDto);

        // Crear especificación desde filtros
        Specification<Family> spec = FamilySpecification.withFilters(filterDto);

        // Crear ordenamiento
        Sort sort = Sort.by(
                "ASC".equalsIgnoreCase(filterDto.getSortDirection()) ?
                        Sort.Direction.ASC : Sort.Direction.DESC,
                filterDto.getSortField() != null ? filterDto.getSortField() : "name"
        );

        // Crear paginación
        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize(), sort);

        // Ejecutar consulta
        Page<Family> page = familyRepository.findAll(spec, pageable);

        return familyMapper.toPagedResponse(page);
    }

    @Override
    @Cacheable(value = "familyCache", key = "#id")
    public Optional<FamilyDto> findById(Long id) {
        logger.debug("Finding family by ID: {}", id);

        return familyRepository.findById(id)
                .map(familyMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return familyRepository.existsById(id);
    }

    @Override
    public long count() {
        return familyRepository.count();
    }

    @Override
    public long countActive() {
        return familyRepository.countActiveFamilies();
    }

    @Override
    public long countByManufacturer(Long manufacturerId) {
        return familyRepository.countByManufacturerId(manufacturerId);
    }

    @Override
    @Cacheable(value = "familyListCache", key = "'allSummary'")
    public List<FamilySummaryDto> findAllSummary() {
        logger.debug("Finding all family summaries");

        List<Family> families = familyRepository.findAllByOrderByName();
        return familyMapper.toSummaryDtoList(families);
    }

    @Override
    public List<FamilyDto> findByManufacturer(Long manufacturerId, Pageable pageable) {
        logger.debug("Finding families by manufacturer: {}", manufacturerId);

        Page<Family> page = familyRepository.findByManufacturerId(manufacturerId, pageable);
        return familyMapper.toDtoList(page.getContent());
    }

    @Override
    @Cacheable(value = "familyListCache", key = "'manufacturer:' + #manufacturerId")
    public List<FamilySummaryDto> findSummaryByManufacturer(Long manufacturerId) {
        logger.debug("Finding family summaries by manufacturer: {}", manufacturerId);

        List<Family> families = familyRepository.findByManufacturerIdOrderByName(manufacturerId);
        return familyMapper.toSummaryDtoList(families);
    }

    @Override
    public List<FamilyDto> findByCategory(String category, Pageable pageable) {
        logger.debug("Finding families by category: {}", category);

        Page<Family> page = familyRepository.findByCategoryIgnoreCase(category, pageable);
        return familyMapper.toDtoList(page.getContent());
    }

    @Override
    public List<FamilySummaryDto> searchByText(String query, int limit) {
        logger.debug("Searching families by text: '{}', limit: {}", query, limit);

        Pageable pageable = PageRequest.of(0, limit);
        Page<Family> results = familyRepository.findByNameContainingIgnoreCase(query, pageable);

        return familyMapper.toSummaryDtoList(results.getContent());
    }

    @Override
    @Cacheable(value = "familyListCache", key = "'categories'")
    public List<String> getAvailableCategories() {
        logger.debug("Getting available family categories");

        return familyRepository.findDistinctCategories();
    }

    @Override
    @Cacheable(value = "familyListCache", key = "'popular:' + #limit")
    public List<FamilyDto> getPopularFamilies(int limit) {
        logger.debug("Getting popular families, limit: {}", limit);

        Pageable pageable = PageRequest.of(0, limit);
        Page<Family> popular = familyRepository.findPopularFamilies(pageable);

        return familyMapper.toDtoList(popular.getContent());
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'family'")
    public FamilyStatisticsDto getStatistics() {
        logger.debug("Getting family statistics");

        FamilyStatisticsDto stats = new FamilyStatisticsDto();

        stats.setTotalFamilies(count());
        stats.setActiveFamilies(countActive());

        // Calcular promedio de modelos por familia
        Double avgModels = familyRepository.getAverageModelsPerFamily();
        stats.setAverageModelsPerFamily(avgModels != null ? avgModels : 0.0);

        // Obtener familia con más modelos
        Optional<Family> topFamily = familyRepository.findTopFamilyByModelCount();
        if (topFamily.isPresent()) {
            stats.setTopFamily(topFamily.get().getName());
        }

        // Contar categorías
        stats.setCategoriesCount(getAvailableCategories().size());

        return stats;
    }
}
