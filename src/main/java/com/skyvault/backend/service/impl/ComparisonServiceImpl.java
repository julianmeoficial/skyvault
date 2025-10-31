package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.mapper.ComparisonMapper;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.repository.AircraftModelRepository;
import com.skyvault.backend.service.ComparisonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ComparisonServiceImpl implements ComparisonService {

    private static final Logger logger = LoggerFactory.getLogger(ComparisonServiceImpl.class);

    private final AircraftModelRepository aircraftRepository;
    private final ComparisonMapper comparisonMapper;

    @Autowired
    public ComparisonServiceImpl(AircraftModelRepository aircraftRepository,
                                 ComparisonMapper comparisonMapper) {
        this.aircraftRepository = aircraftRepository;
        this.comparisonMapper = comparisonMapper;
    }

    @Override
    @Cacheable(value = "comparisonCache",
            key = "#request.aircraftIds.toString() + ':' + #request.includeSpecifications + ':' + #request.normalizeUnits")
    public CompareResultDto compareAircraft(CompareRequestDto request) {
        logger.debug("Comparing aircraft: {}", request.getAircraftIds());

        // Validar que las aeronaves existen
        if (!validateAircraftForComparison(request.getAircraftIds())) {
            throw new IllegalArgumentException("One or more aircraft not found or inactive");
        }

        // Obtener aeronaves a comparar
        List<AircraftModel> aircraftList = aircraftRepository.findByIdsForComparison(request.getAircraftIds());

        // Crear resultado base
        CompareResultDto result = comparisonMapper.createCompareResult(aircraftList, request);

        // Generar tabla de comparación
        Map<String, ComparisonRowDto> comparisonTable = generateComparisonTable(aircraftList, request);
        result.setComparisonTable(comparisonTable);

        // Generar resumen
        ComparisonSummaryDto summary = generateSummary(request.getAircraftIds());
        result.setSummary(summary);

        // Normalizar unidades si se solicita
        if (Boolean.TRUE.equals(request.getNormalizeUnits())) {
            result = normalizeUnits(result, request.getUnitFormat());
        }

        return result;
    }

    @Override
    public CompareResultDto compareAircraftDefault(List<Long> aircraftIds) {
        logger.debug("Comparing aircraft with default settings: {}", aircraftIds);

        CompareRequestDto defaultRequest = new CompareRequestDto();
        defaultRequest.setAircraftIds(aircraftIds);
        defaultRequest.setIncludeSpecifications(true);
        defaultRequest.setIncludeImages(true);
        defaultRequest.setNormalizeUnits(true);
        defaultRequest.setUnitFormat("metric");

        return compareAircraft(defaultRequest);
    }

    @Override
    public ComparisonSummaryDto generateSummary(List<Long> aircraftIds) {
        logger.debug("Generating comparison summary for: {}", aircraftIds);

        List<AircraftModel> aircraft = aircraftRepository.findAllById(aircraftIds);

        ComparisonSummaryDto summary = new ComparisonSummaryDto();

        if (aircraft.isEmpty()) {
            return summary;
        }

        // Encontrar extremos
        AircraftModel largestCapacity = aircraft.stream()
                .max(Comparator.comparing(AircraftModel::getMaxPassengers))
                .orElse(null);

        AircraftModel longestRange = aircraft.stream()
                .max(Comparator.comparing(AircraftModel::getRangeKm))
                .orElse(null);

        AircraftModel fastest = aircraft.stream()
                .max(Comparator.comparing(AircraftModel::getCruiseSpeedKnots))
                .orElse(null);

        AircraftModel newest = aircraft.stream()
                .max(Comparator.comparing(AircraftModel::getIntroductionYear))
                .orElse(null);

        AircraftModel oldest = aircraft.stream()
                .min(Comparator.comparing(AircraftModel::getIntroductionYear))
                .orElse(null);

        // Convertir a summary DTOs
        if (largestCapacity != null) {
            summary.setLargestCapacity(createAircraftSummary(largestCapacity,
                    largestCapacity.getMaxPassengers() + " passengers"));
        }

        if (longestRange != null) {
            summary.setLongestRange(createAircraftSummary(longestRange,
                    longestRange.getRangeKm() + " km"));
        }

        if (fastest != null) {
            summary.setFastest(createAircraftSummary(fastest,
                    fastest.getCruiseSpeedKnots() + " knots"));
        }

        if (newest != null) {
            summary.setNewest(createAircraftSummary(newest,
                    "Introduced " + newest.getIntroductionYear()));
        }

        if (oldest != null) {
            summary.setOldest(createAircraftSummary(oldest,
                    "Introduced " + oldest.getIntroductionYear()));
        }

        // Calcular promedios
        summary.setAveragePassengers(aircraft.stream()
                .mapToInt(AircraftModel::getMaxPassengers)
                .average().orElse(0.0));

        summary.setAverageRange(aircraft.stream()
                .mapToInt(AircraftModel::getRangeKm)
                .average().orElse(0.0));

        summary.setAverageSpeed(aircraft.stream()
                .mapToInt(AircraftModel::getCruiseSpeedKnots)
                .average().orElse(0.0));

        // Calcular diferencias (spread)
        IntSummaryStatistics passengerStats = aircraft.stream()
                .mapToInt(AircraftModel::getMaxPassengers)
                .summaryStatistics();
        summary.setPassengerSpread(passengerStats.getMax() - passengerStats.getMin());

        IntSummaryStatistics rangeStats = aircraft.stream()
                .mapToInt(AircraftModel::getRangeKm)
                .summaryStatistics();
        summary.setRangeSpread(rangeStats.getMax() - rangeStats.getMin());

        return summary;
    }

    @Override
    public boolean validateAircraftForComparison(List<Long> aircraftIds) {
        if (aircraftIds == null || aircraftIds.isEmpty()) {
            logger.warn("Aircraft IDs list is null or empty");
            return false;
        }

        if (aircraftIds.size() < 2) {
            logger.warn("Need at least 2 aircraft for comparison, got: {}", aircraftIds.size());
            return false;
        }

        if (aircraftIds.size() > 5) {
            logger.warn("Too many aircraft for comparison, max 5, got: {}", aircraftIds.size());
            return false;
        }

        // Verificar que todas las aeronaves existen y están activas
        long existingCount = aircraftRepository.countByIdInAndIsActiveTrue(aircraftIds);
        boolean valid = existingCount == aircraftIds.size();

        if (!valid) {
            logger.warn("Not all aircraft exist or are active. Expected: {}, Found: {}",
                    aircraftIds.size(), existingCount);
        }

        return valid;
    }

    @Override
    public List<CompareResultDto> getSuggestedComparisons(Long aircraftId, int limit) {
        logger.debug("Getting suggested comparisons for aircraft: {}, limit: {}", aircraftId, limit);

        // Implementación simplificada - en producción usarías algoritmos más sofisticados
        Optional<AircraftModel> baseAircraft = aircraftRepository.findById(aircraftId);
        if (baseAircraft.isEmpty()) {
            return List.of();
        }

        AircraftModel base = baseAircraft.get();

        // Buscar aeronaves similares
        List<AircraftModel> similar = aircraftRepository.findSimilarAircraft(
                aircraftId,
                base.getMaxPassengers(),
                base.getRangeKm(),
                50,
                1000,
                org.springframework.data.domain.PageRequest.of(0, limit * 2 )
        );

        List<CompareResultDto> suggestions = new ArrayList<>();

        // Crear comparaciones sugeridas
        for (int i = 0; i < Math.min(similar.size(), limit); i++) {
            AircraftModel compareWith = similar.get(i);
            List<Long> ids = List.of(aircraftId, compareWith.getId());

            try {
                CompareResultDto comparison = compareAircraftDefault(ids);
                suggestions.add(comparison);
            } catch (Exception e) {
                logger.warn("Failed to create suggested comparison: {}", e.getMessage());
            }
        }

        return suggestions;
    }

    @Override
    public List<CompareResultDto> getPopularComparisons(int limit) {
        logger.debug("Getting popular comparisons, limit: {}", limit);

        // En producción esto vendría de una tabla de estadísticas
        // Por ahora retornamos lista vacía
        return List.of();
    }

    @Override
    public CompareResultDto normalizeUnits(CompareResultDto result, String unitSystem) {
        logger.debug("Normalizing units to: {}", unitSystem);

        // Implementación básica - en producción sería más completa
        if ("metric".equals(unitSystem)) {
            // Ya está en métrico por defecto
            return result;
        } else if ("imperial".equals(unitSystem)) {
            // Convertir a imperial
            return convertToImperial(result);
        }

        return result;
    }

    @Override
    public String exportComparison(CompareResultDto result, String format) {
        logger.debug("Exporting comparison to format: {}", format);

        switch (format.toLowerCase()) {
            case "csv" -> {
                return exportToCsv(result);
            }
            case "json" -> {
                return exportToJson(result);
            }
            case "xml" -> {
                return exportToXml(result);
            }
            default -> {
                throw new IllegalArgumentException("Unsupported export format: " + format);
            }
        }
    }

    // Métodos privados auxiliares

    private Map<String, ComparisonRowDto> generateComparisonTable(List<AircraftModel> aircraft, CompareRequestDto request) {
        Map<String, ComparisonRowDto> table = new LinkedHashMap<>();

        // Campos básicos siempre incluidos
        addComparisonRow(table, "Max Passengers", "numeric", "passengers",
                aircraft.stream().map(a -> a.getMaxPassengers().toString()).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Range", "numeric", "km",
                aircraft.stream().map(a -> a.getRangeKm().toString()).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Cruise Speed", "numeric", "knots",
                aircraft.stream().map(a -> a.getCruiseSpeedKnots().toString()).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Introduction Year", "numeric", "year",
                aircraft.stream().map(a -> a.getIntroductionYear().toString()).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Manufacturer", "text", "",
                aircraft.stream().map(a -> a.getManufacturer().getName()).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Family", "text", "",
                aircraft.stream().map(a -> a.getFamily().getName()).collect(Collectors.toList()),
                aircraft);

            // Agregar especificaciones si se solicita
        if (Boolean.TRUE.equals(request.getIncludeSpecifications())) {
            addSpecificationsRows(table, aircraft);
        }

        return table;
    }

    private void addComparisonRow(Map<String, ComparisonRowDto> table, String fieldName,
                                  String fieldType, String unit, List<String> values,
                                  List<AircraftModel> aircraft) {
        ComparisonRowDto row = new ComparisonRowDto();
        row.setFieldName(fieldName);
        row.setFieldType(fieldType);
        row.setUnit(unit);

        List<ComparisonValueDto> comparisonValues = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            ComparisonValueDto value = new ComparisonValueDto();
            value.setAircraftId(aircraft.get(i).getId());
            value.setOriginalValue(values.get(i));
            value.setDisplayValue(values.get(i) + (unit.isEmpty() ? "" : " " + unit));

            if ("numeric".equals(fieldType)) {
                try {
                    value.setNumericValue(Double.parseDouble(values.get(i)));
                } catch (NumberFormatException e) {
                    value.setNumericValue(0.0);
                }
            }

            comparisonValues.add(value);
        }

        row.setValues(comparisonValues);

        // Calcular min/max para campos numéricos
        if ("numeric".equals(fieldType)) {
            OptionalDouble min = comparisonValues.stream()
                    .mapToDouble(v -> v.getNumericValue() != null ? v.getNumericValue() : 0.0)
                    .min();
            OptionalDouble max = comparisonValues.stream()
                    .mapToDouble(v -> v.getNumericValue() != null ? v.getNumericValue() : 0.0)
                    .max();

            if (min.isPresent() && max.isPresent()) {
                // Marcar valores extremos
                comparisonValues.forEach(v -> {
                    if (v.getNumericValue() != null) {
                        v.setIsLowest(v.getNumericValue().equals(min.getAsDouble()));
                        v.setIsHighest(v.getNumericValue().equals(max.getAsDouble()));
                    }
                });

                row.setDifference(String.format("%.1f", max.getAsDouble() - min.getAsDouble()));
            }
        }

        table.put(fieldName, row);
    }

    private AircraftSummaryDto createAircraftSummary(AircraftModel aircraft, String highlightValue) {
        AircraftSummaryDto summary = new AircraftSummaryDto();
        summary.setId(aircraft.getId());
        summary.setName(aircraft.getName());
        summary.setManufacturerName(aircraft.getManufacturer().getName());
        summary.setHighlightValue(highlightValue);
        return summary;
    }

    private CompareResultDto convertToImperial(CompareResultDto result) {
        // Implementación de conversión a unidades imperiales
        // Por simplicidad, retornamos el resultado sin cambios
        return result;
    }

    private String exportToCsv(CompareResultDto result) {
        StringBuilder csv = new StringBuilder();
        csv.append("Aircraft Comparison Export\n");
        // Implementar exportación CSV
        return csv.toString();
    }

    private String exportToJson(CompareResultDto result) {
        // Usar ObjectMapper para serializar a JSON
        return "{}"; // Placeholder
    }

    private String exportToXml(CompareResultDto result) {
        // Implementar exportación XML
        return "<comparison></comparison>"; // Placeholder
    }

    private void addSpecificationsRows(Map<String, ComparisonRowDto> table, List<AircraftModel> aircraft) {
        // Combustible máximo
        addComparisonRow(table, "Combustible máximo", "numeric", "litros",
                aircraft.stream().map(a -> {
                    if (a.getMaxFuelTons() != null) {
                        return String.valueOf(a.getMaxFuelTons().multiply(java.math.BigDecimal.valueOf(1000)).intValue());
                    }
                    return "-";
                }).collect(Collectors.toList()),
                aircraft);

        // Dimensiones
        addComparisonRow(table, "Longitud", "numeric", "m",
                aircraft.stream().map(a -> {
                    if (a.hasSpecifications() && a.getSpecifications().getLengthM() != null) {
                        return String.format("%.1f", a.getSpecifications().getLengthM().doubleValue());
                    }
                    return "-";
                }).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Envergadura", "numeric", "m",
                aircraft.stream().map(a -> {
                    if (a.hasSpecifications() && a.getSpecifications().getWingspanM() != null) {
                        return String.format("%.1f", a.getSpecifications().getWingspanM().doubleValue());
                    }
                    return "-";
                }).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Altura", "numeric", "m",
                aircraft.stream().map(a -> {
                    if (a.hasSpecifications() && a.getSpecifications().getHeightM() != null) {
                        return String.format("%.1f", a.getSpecifications().getHeightM().doubleValue());
                    }
                    return "-";
                }).collect(Collectors.toList()),
                aircraft);

        // Motor
        addComparisonRow(table, "Fabricante del motor", "text", "",
                aircraft.stream().map(a -> {
                    if (a.hasSpecifications() && a.getSpecifications().getEngineManufacturer() != null) {
                        return a.getSpecifications().getEngineManufacturer();
                    }
                    return "-";
                }).collect(Collectors.toList()),
                aircraft);

        addComparisonRow(table, "Modelo del motor", "text", "",
                aircraft.stream().map(a -> {
                    if (a.hasSpecifications() && a.getSpecifications().getEngineModel() != null) {
                        return a.getSpecifications().getEngineModel();
                    }
                    return "-";
                }).collect(Collectors.toList()),
                aircraft);
    }
}
