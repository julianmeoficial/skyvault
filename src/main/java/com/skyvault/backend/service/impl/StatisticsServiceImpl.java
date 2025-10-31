package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.service.StatisticsService;
import com.skyvault.backend.service.AircraftService;
import com.skyvault.backend.service.ManufacturerService;
import com.skyvault.backend.service.FamilyService;
import com.skyvault.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private final AircraftService aircraftService;
    private final ManufacturerService manufacturerService;
    private final FamilyService familyService;
    private final AircraftModelRepository aircraftRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final FamilyRepository familyRepository;
    private final TypeRepository typeRepository;
    private final ProductionStateRepository productionStateRepository;
    private final SizeCategoryRepository sizeCategoryRepository;

    // Almacenamiento en memoria para métricas (en producción sería una base de datos)
    private final ConcurrentHashMap<String, AtomicLong> metrics = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<LocalDate, AtomicLong> dailyUsage = new ConcurrentHashMap<>();

    @Autowired
    public StatisticsServiceImpl(AircraftService aircraftService,
                                 ManufacturerService manufacturerService,
                                 FamilyService familyService,
                                 AircraftModelRepository aircraftRepository,
                                 ManufacturerRepository manufacturerRepository,
                                 FamilyRepository familyRepository,
                                 TypeRepository typeRepository,
                                 ProductionStateRepository productionStateRepository,
                                 SizeCategoryRepository sizeCategoryRepository) {
        this.aircraftService = aircraftService;
        this.manufacturerService = manufacturerService;
        this.familyService = familyService;
        this.aircraftRepository = aircraftRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.familyRepository = familyRepository;
        this.typeRepository = typeRepository;
        this.productionStateRepository = productionStateRepository;
        this.sizeCategoryRepository = sizeCategoryRepository;

        initializeMetrics();
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'system'")
    public SystemStatisticsDto getSystemStatistics() {
        logger.debug("Getting system statistics");

        SystemStatisticsDto stats = new SystemStatisticsDto();

        // Estadísticas básicas
        stats.setTotalAircraft(aircraftService.count());
        stats.setActiveAircraft(aircraftService.countActive());
        stats.setTotalManufacturers(manufacturerService.count());
        stats.setActiveManufacturers(manufacturerService.countActive());
        stats.setTotalFamilies(familyService.count());
        stats.setActiveFamilies(familyService.countActive());

        // Contadores adicionales
        stats.setTotalTypes(typeRepository.count());
        stats.setTotalProductionStates(productionStateRepository.count());
        stats.setTotalSizeCategories(sizeCategoryRepository.count());

        // Métricas de uso (simuladas)
        stats.setTotalSearches(getMetricValue("total_searches"));
        stats.setTotalComparisons(getMetricValue("total_comparisons"));
        stats.setTotalViews(getMetricValue("total_views"));

        // Cálculos derivados
        stats.setAverageAircraftPerManufacturer(
                stats.getTotalManufacturers() > 0 ?
                        (double) stats.getTotalAircraft() / stats.getTotalManufacturers() : 0.0
        );

        stats.setAverageAircraftPerFamily(
                stats.getTotalFamilies() > 0 ?
                        (double) stats.getTotalAircraft() / stats.getTotalFamilies() : 0.0
        );

        // Metadata
        stats.setLastUpdated(LocalDateTime.now());
        stats.setDataValidFrom(LocalDate.now().minusDays(30));
        stats.setDataValidTo(LocalDate.now());

        return stats;
    }

    @Override
    public AircraftStatisticsDto getAircraftStatistics() {
        logger.debug("Getting aircraft statistics");

        return aircraftService.getStatistics();
    }

    @Override
    public ManufacturerStatisticsDto getManufacturerStatistics() {
        logger.debug("Getting manufacturer statistics");

        return manufacturerService.getStatistics();
    }

    @Override
    public FamilyStatisticsDto getFamilyStatistics() {
        logger.debug("Getting family statistics");

        return familyService.getStatistics();
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'search'")
    public SearchStatisticsDto getSearchStatistics() {
        logger.debug("Getting search statistics");

        SearchStatisticsDto stats = new SearchStatisticsDto();

        // Métricas básicas de búsqueda
        stats.setTotalSearches(getMetricValue("total_searches"));
        stats.setUniqueSearchTerms(getMetricValue("unique_search_terms"));
        stats.setAverageSearchesPerDay(getMetricValue("total_searches") / 30); // Últimos 30 días

        // Términos populares (simulados)
        stats.setPopularSearchTerms(List.of("Boeing 737", "Airbus A320", "777", "A350", "narrow body"));

        // Búsquedas sin resultados
        stats.setNoResultSearches(getMetricValue("no_result_searches"));
        stats.setNoResultRate(
                stats.getTotalSearches() > 0 ?
                        (double) stats.getNoResultSearches() / stats.getTotalSearches() * 100 : 0.0
        );

        // Distribución por tipo
        Map<String, Long> searchByType = new LinkedHashMap<>();
        searchByType.put("aircraft", getMetricValue("aircraft_searches"));
        searchByType.put("manufacturers", getMetricValue("manufacturer_searches"));
        searchByType.put("families", getMetricValue("family_searches"));
        stats.setSearchesByType(searchByType);

        return stats;
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'manufacturerDistribution'")
    public Map<String, Long> getManufacturerDistribution() {
        logger.debug("Getting manufacturer distribution");

        Map<String, Long> distribution = new LinkedHashMap<>();

        // Obtener distribución real de la base de datos
        List<Object[]> results = aircraftRepository.countAircraftGroupedByManufacturer();

        for (Object[] result : results) {
            String manufacturerName = (String) result[0];
            Long count = (Long) result[1];
            distribution.put(manufacturerName, count);
        }

        // Ordenar por cantidad descendente
        return distribution.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'typeDistribution'")
    public Map<String, Long> getTypeDistribution() {
        logger.debug("Getting type distribution");

        Map<String, Long> distribution = new LinkedHashMap<>();

        List<Object[]> results = aircraftRepository.countAircraftGroupedByType();

        for (Object[] result : results) {
            String typeName = (String) result[0];
            Long count = (Long) result[1];
            distribution.put(typeName, count);
        }

        return distribution.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'productionStateDistribution'")
    public Map<String, Long> getProductionStateDistribution() {
        logger.debug("Getting production state distribution");

        Map<String, Long> distribution = new LinkedHashMap<>();

        List<Object[]> results = aircraftRepository.countAircraftGroupedByProductionState();

        for (Object[] result : results) {
            String stateName = (String) result[0];
            Long count = (Long) result[1];
            distribution.put(stateName, count);
        }

        return distribution;
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'sizeCategoryDistribution'")
    public Map<String, Long> getSizeCategoryDistribution() {
        logger.debug("Getting size category distribution");

        Map<String, Long> distribution = new LinkedHashMap<>();

        List<Object[]> results = aircraftRepository.countAircraftGroupedBySizeCategory();

        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long count = (Long) result[1];
            distribution.put(categoryName, count);
        }

        return distribution.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'introductionYearDistribution'")
    public Map<String, Long> getIntroductionYearDistribution() {
        logger.debug("Getting introduction year distribution");

        Map<String, Long> distribution = new LinkedHashMap<>();

        // Agrupar por décadas
        List<Object[]> results = aircraftRepository.countAircraftGroupedByIntroductionYearDecade();

        for (Object[] result : results) {
            Integer decade = (Integer) result[0];
            Long count = (Long) result[1];

            String decadeLabel = decade + "s";
            distribution.put(decadeLabel, count);
        }

        return distribution;
    }

    @Override
    public Map<LocalDate, Long> getUsageStatistics(LocalDate from, LocalDate to) {
        logger.debug("Getting usage statistics from {} to {}", from, to);

        Map<LocalDate, Long> usage = new LinkedHashMap<>();

        // Generar datos para el rango solicitado
        LocalDate current = from;
        while (!current.isAfter(to)) {
            Long dayUsage = dailyUsage.getOrDefault(current, new AtomicLong(0)).get();
            usage.put(current, dayUsage);
            current = current.plusDays(1);
        }

        return usage;
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'popularAircraft:' + #limit")
    public List<AircraftPopularityDto> getMostSearchedAircraft(int limit) {
        logger.debug("Getting most searched aircraft, limit: {}", limit);

        // En una implementación real, esto vendría de una tabla de métricas
        // Por ahora, simulamos con datos de ejemplo
        List<AircraftPopularityDto> popular = List.of(
                createAircraftPopularity(1L, "Boeing 737-800", "Boeing", 1250L, 1),
                createAircraftPopularity(2L, "Airbus A320", "Airbus", 1180L, 2),
                createAircraftPopularity(3L, "Boeing 777-300ER", "Boeing", 980L, 3),
                createAircraftPopularity(4L, "Airbus A350-900", "Airbus", 850L, 4),
                createAircraftPopularity(5L, "Boeing 787-9", "Boeing", 720L, 5)
        );

        return popular.stream().limit(limit).toList();
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'popularComparisons:' + #limit")
    public List<ComparisonPopularityDto> getMostFrequentComparisons(int limit) {
        logger.debug("Getting most frequent comparisons, limit: {}", limit);

        // Simulación de comparaciones populares
        List<ComparisonPopularityDto> popular = List.of(
                createComparisonPopularity(List.of(1L, 2L), List.of("Boeing 737-800", "Airbus A320"), 456L, 1),
                createComparisonPopularity(List.of(3L, 4L), List.of("Boeing 777-300ER", "Airbus A350-900"), 324L, 2),
                createComparisonPopularity(List.of(5L, 6L), List.of("Boeing 787-9", "Airbus A330-900neo"), 298L, 3)
        );

        return popular.stream().limit(limit).toList();
    }

    @Override
    @Transactional
    public void recordAircraftView(Long aircraftId) {
        incrementMetric("total_views");
        incrementMetric("aircraft_views_" + aircraftId);
        recordDailyUsage();

        logger.debug("Recorded aircraft view for ID: {}", aircraftId);
    }

    @Override
    @Transactional
    public void recordComparison(List<Long> aircraftIds) {
        incrementMetric("total_comparisons");

        // Registrar cada par de comparación
        for (int i = 0; i < aircraftIds.size(); i++) {
            for (int j = i + 1; j < aircraftIds.size(); j++) {
                String comparisonKey = "comparison_" +
                        Math.min(aircraftIds.get(i), aircraftIds.get(j)) + "_" +
                        Math.max(aircraftIds.get(i), aircraftIds.get(j));
                incrementMetric(comparisonKey);
            }
        }

        recordDailyUsage();

        logger.debug("Recorded comparison for aircraft IDs: {}", aircraftIds);
    }

    @Override
    @Transactional
    public void recordSearch(String query, String type, int resultsCount) {
        incrementMetric("total_searches");
        incrementMetric(type + "_searches");

        if (resultsCount == 0) {
            incrementMetric("no_result_searches");
        }

        recordDailyUsage();

        logger.debug("Recorded search: query='{}', type='{}', results={}", query, type, resultsCount);
    }

    @Override
    @CacheEvict(value = "statisticsCache", allEntries = true)
    @Transactional
    public void updateStatistics() {
        logger.info("Updating statistics - clearing cache and recalculating");

        // En una implementación real, aquí:
        // - Consolidar métricas diarias
        // - Calcular rankings de popularidad
        // - Generar reportes agregados
        // - Limpiar datos antiguos
    }

    @Override
    public void resetStatistics() {
        logger.warn("Resetting all statistics");

        metrics.clear();
        dailyUsage.clear();
        initializeMetrics();
    }

    @Override
    public Map<String, Long> getSystemMetrics() {
        Map<String, Long> systemMetrics = new LinkedHashMap<>();

        metrics.forEach((key, value) -> systemMetrics.put(key, value.get()));

        return systemMetrics;
    }

    @Override
    public void incrementMetric(String metricName) {
        metrics.computeIfAbsent(metricName, k -> new AtomicLong(0))
                .incrementAndGet();
    }

    @Override
    public void incrementMetric(String metricName, long increment) {
        metrics.computeIfAbsent(metricName, k -> new AtomicLong(0))
                .addAndGet(increment);
    }

    @Override
    public long getMetricValue(String metricName) {
        return metrics.getOrDefault(metricName, new AtomicLong(0)).get();
    }

    // Métodos privados auxiliares

    private void initializeMetrics() {
        // Inicializar métricas básicas con valores por defecto
        metrics.put("total_searches", new AtomicLong(15420));
        metrics.put("total_comparisons", new AtomicLong(3256));
        metrics.put("total_views", new AtomicLong(45230));
        metrics.put("aircraft_searches", new AtomicLong(8970));
        metrics.put("manufacturer_searches", new AtomicLong(3450));
        metrics.put("family_searches", new AtomicLong(2800));
        metrics.put("no_result_searches", new AtomicLong(234));
        metrics.put("unique_search_terms", new AtomicLong(1543));

        logger.info("Initialized statistics metrics");
    }

    private void recordDailyUsage() {
        LocalDate today = LocalDate.now();
        dailyUsage.computeIfAbsent(today, k -> new AtomicLong(0))
                .incrementAndGet();
    }

    private AircraftPopularityDto createAircraftPopularity(Long id, String name, String manufacturer,
                                                           Long searchCount, Integer ranking) {
        AircraftPopularityDto popularity = new AircraftPopularityDto();
        popularity.setAircraftId(id);
        popularity.setAircraftName(name);
        popularity.setManufacturerName(manufacturer);
        popularity.setSearchCount(searchCount);
        popularity.setRanking(ranking);
        popularity.setPopularityScore(100.0 - (ranking - 1) * 10);
        popularity.setTrend("STABLE");
        return popularity;
    }

    private ComparisonPopularityDto createComparisonPopularity(List<Long> aircraftIds, List<String> aircraftNames,
                                                               Long comparisonCount, Integer ranking) {
        ComparisonPopularityDto popularity = new ComparisonPopularityDto();
        popularity.setAircraftIds(aircraftIds);
        popularity.setAircraftNames(aircraftNames);
        popularity.setComparisonCount(comparisonCount);
        popularity.setRanking(ranking);
        popularity.setPopularityScore(100.0 - (ranking - 1) * 15);
        popularity.setTrend("STABLE");
        return popularity;
    }

    @Override
    public void cleanOldStatistics(int daysToKeep) {
        logger.info("Cleaning old statistics older than {} days", daysToKeep);

        LocalDate cutoff = LocalDate.now().minusDays(daysToKeep);

        // Limpiar uso diario antiguo
        long removedCount = dailyUsage.entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(cutoff))
                .count();

        dailyUsage.entrySet().removeIf(entry -> entry.getKey().isBefore(cutoff));

        logger.info("Removed {} old daily usage entries", removedCount);
    }
}
