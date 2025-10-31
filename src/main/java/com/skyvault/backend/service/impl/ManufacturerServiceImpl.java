package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.request.ManufacturerFilterDto;
import com.skyvault.backend.dto.response.ManufacturerDto;
import com.skyvault.backend.dto.response.ManufacturerStatisticsDto;
import com.skyvault.backend.dto.response.ManufacturerSummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.mapper.ManufacturerMapper;
import com.skyvault.backend.model.Manufacturer;
import com.skyvault.backend.repository.ManufacturerRepository;
import com.skyvault.backend.service.ManufacturerService;
import com.skyvault.backend.specification.ManufacturerSpecification;
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
public class ManufacturerServiceImpl implements ManufacturerService {

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerServiceImpl.class);

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository,
                                   ManufacturerMapper manufacturerMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerMapper = manufacturerMapper;
    }

    @Override
    public PagedResponseDto<ManufacturerDto> findManufacturers(ManufacturerFilterDto filterDto) {
        logger.debug("Finding manufacturers with filters: {}", filterDto);

        // Crear especificación desde filtros
        Specification<Manufacturer> spec = ManufacturerSpecification.withFilters(filterDto);

        // Crear ordenamiento
        Sort sort = Sort.by(
                "ASC".equalsIgnoreCase(filterDto.getSortDirection()) ?
                        Sort.Direction.ASC : Sort.Direction.DESC,
                filterDto.getSortField() != null ? filterDto.getSortField() : "name"
        );

        // Crear paginación
        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize(), sort);

        // Ejecutar consulta
        Page<Manufacturer> page = manufacturerRepository.findAll(spec, pageable);

        return manufacturerMapper.toPagedResponse(page);
    }

    @Override
    @Cacheable(value = "manufacturerCache", key = "#id")
    public Optional<ManufacturerDto> findById(Long id) {
        logger.debug("Finding manufacturer by ID: {}", id);

        return manufacturerRepository.findById(id)
                .map(manufacturerMapper::toDto);
    }

    @Override
    @Cacheable(value = "manufacturerCache", key = "'name:' + #name")
    public Optional<ManufacturerDto> findByName(String name) {
        logger.debug("Finding manufacturer by name: {}", name);

        return manufacturerRepository.findByNameIgnoreCase(name)
                .map(manufacturerMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return manufacturerRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return manufacturerRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public long count() {
        return manufacturerRepository.count();
    }

    @Override
    public long countActive() {
        return manufacturerRepository.countActiveManufacturers();
    }

    @Override
    @Cacheable(value = "manufacturerListCache", key = "'allSummary'")
    public List<ManufacturerSummaryDto> findAllSummary() {
        logger.debug("Finding all manufacturer summaries");

        List<Manufacturer> manufacturers = manufacturerRepository.findAllByOrderByName();
        return manufacturerMapper.toSummaryDtoList(manufacturers);
    }

    @Override
    @Cacheable(value = "manufacturerListCache", key = "'activeSummary'")
    public List<ManufacturerSummaryDto> findActiveSummary() {
        logger.debug("Finding active manufacturer summaries");

        List<Manufacturer> manufacturers = manufacturerRepository.findActiveManufacturersOrderByName();
        return manufacturerMapper.toSummaryDtoList(manufacturers);
    }

    @Override
    public List<ManufacturerSummaryDto> searchByText(String query, int limit) {
        logger.debug("Searching manufacturers by text: '{}', limit: {}", query, limit);

        Pageable pageable = PageRequest.of(0, limit);
        Page<Manufacturer> results = manufacturerRepository.findByNameContainingIgnoreCase(query, pageable);

        return manufacturerMapper.toSummaryDtoList(results.getContent());
    }

    @Override
    public List<ManufacturerDto> findByCountry(String country, Pageable pageable) {
        logger.debug("Finding manufacturers by country: {}", country);

        Page<Manufacturer> page = manufacturerRepository.findByCountryIgnoreCase(country, pageable);
        return manufacturerMapper.toDtoList(page.getContent());
    }

    @Override
    @Cacheable(value = "manufacturerListCache", key = "'countries'")
    public List<String> getAvailableCountries() {
        logger.debug("Getting available manufacturer countries");

        return manufacturerRepository.findDistinctCountries();
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'manufacturer'")
    public ManufacturerStatisticsDto getStatistics() {
        logger.debug("Getting manufacturer statistics");

        ManufacturerStatisticsDto stats = new ManufacturerStatisticsDto();

        stats.setTotalManufacturers(count());
        stats.setActiveManufacturers(countActive());

        // Calcular promedio de aeronaves por fabricante
        Double avgAircraft = manufacturerRepository.getAverageAircraftPerManufacturer();
        stats.setAverageAircraftPerManufacturer(avgAircraft != null ? avgAircraft : 0.0);

        // Obtener fabricante con más aeronaves
        Optional<Manufacturer> topManufacturer = manufacturerRepository.findTopManufacturerByAircraftCount();
        if (topManufacturer.isPresent()) {
            stats.setTopManufacturer(topManufacturer.get().getName());
        }

        // Contar países
        stats.setCountriesCount(getAvailableCountries().size());

        return stats;
    }
}