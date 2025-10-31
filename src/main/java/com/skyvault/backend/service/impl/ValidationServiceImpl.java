package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.repository.AircraftModelRepository;
import com.skyvault.backend.repository.FamilyRepository;
import com.skyvault.backend.repository.ManufacturerRepository;
import com.skyvault.backend.service.ValidationService;
import com.skyvault.backend.validation.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ValidationServiceImpl implements ValidationService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

    private final AircraftModelRepository aircraftRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final FamilyRepository familyRepository;

    // Constantes de validación
    private static final int MIN_PASSENGERS = 1;
    private static final int MAX_PASSENGERS = 1000;
    private static final int MIN_RANGE = 100;
    private static final int MAX_RANGE = 50000;
    private static final int MIN_SPEED = 100;
    private static final int MAX_SPEED = 1000;
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2030;
    private static final int MIN_COMPARISON_AIRCRAFT = 2;
    private static final int MAX_COMPARISON_AIRCRAFT = 5;

    @Autowired
    public ValidationServiceImpl(AircraftModelRepository aircraftRepository,
                                 ManufacturerRepository manufacturerRepository,
                                 FamilyRepository familyRepository) {
        this.aircraftRepository = aircraftRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.familyRepository = familyRepository;
    }

    @Override
    public ValidationResult validateAircraftModel(AircraftModel aircraft) {
        logger.debug("Validating aircraft model: {}", aircraft != null ? aircraft.getName() : "null");

        ValidationResult result = new ValidationResult();

        if (aircraft == null) {
            result.addError("Aircraft model cannot be null");
            return result;
        }

        // Validar nombre
        if (aircraft.getName() == null || aircraft.getName().trim().isEmpty()) {
            result.addError("Aircraft name is required");
        } else if (aircraft.getName().length() > 100) {
            result.addError("Aircraft name cannot exceed 100 characters");
        }

        // Validar modelo
        if (aircraft.getModel() == null || aircraft.getModel().trim().isEmpty()) {
            result.addError("Aircraft model is required");
        } else if (aircraft.getModel().length() > 50) {
            result.addError("Aircraft model cannot exceed 50 characters");
        }

        // Validar pasajeros
        if (aircraft.getTypicalPassengers() != null) {
            if (aircraft.getTypicalPassengers() < MIN_PASSENGERS || aircraft.getTypicalPassengers() > MAX_PASSENGERS) {
                result.addError(String.format("Typical passengers must be between %d and %d", MIN_PASSENGERS, MAX_PASSENGERS));
            }
        }

        if (aircraft.getMaxPassengers() != null) {
            if (aircraft.getMaxPassengers() < MIN_PASSENGERS || aircraft.getMaxPassengers() > MAX_PASSENGERS) {
                result.addError(String.format("Maximum passengers must be between %d and %d", MIN_PASSENGERS, MAX_PASSENGERS));
            }

            // Validar que max >= typical
            if (aircraft.getTypicalPassengers() != null &&
                    aircraft.getMaxPassengers() < aircraft.getTypicalPassengers()) {
                result.addError("Maximum passengers cannot be less than typical passengers");
            }
        }

        // Validar alcance
        if (aircraft.getRangeKm() != null) {
            if (aircraft.getRangeKm() < MIN_RANGE || aircraft.getRangeKm() > MAX_RANGE) {
                result.addError(String.format("Range must be between %d and %d km", MIN_RANGE, MAX_RANGE));
            }
        }

        // Validar velocidad
        if (aircraft.getCruiseSpeedKnots() != null) {
            if (aircraft.getCruiseSpeedKnots() < MIN_SPEED || aircraft.getCruiseSpeedKnots() > MAX_SPEED) {
                result.addError(String.format("Cruise speed must be between %d and %d knots", MIN_SPEED, MAX_SPEED));
            }
        }

        // Validar año de introducción
        if (aircraft.getIntroductionYear() != null) {
            if (aircraft.getIntroductionYear() < MIN_YEAR || aircraft.getIntroductionYear() > MAX_YEAR) {
                result.addError(String.format("Introduction year must be between %d and %d", MIN_YEAR, MAX_YEAR));
            }
        }

        // Validar tripulación mínima
        if (aircraft.getMinCrew() != null) {
            if (aircraft.getMinCrew() < 1 || aircraft.getMinCrew() > 10) {
                result.addError("Minimum crew must be between 1 and 10");
            }
        }

        // Validar relaciones con otras entidades
        validateAircraftRelationships(aircraft, result);

        // Validar unicidad del nombre (solo si no es una actualización)
        if (aircraft.getId() == null) {
            validateAircraftUniqueness(aircraft, result);
        }

        logger.debug("Aircraft validation completed with {} errors", result.getErrors().size());

        return result;
    }

    @Override
    public ValidationResult validateComparisonRequest(CompareRequestDto request) {
        logger.debug("Validating comparison request");

        ValidationResult result = new ValidationResult();

        if (request == null) {
            result.addError("Comparison request cannot be null");
            return result;
        }

        // Validar lista de IDs
        if (request.getAircraftIds() == null || request.getAircraftIds().isEmpty()) {
            result.addError("Aircraft IDs list cannot be empty");
        } else {
            // Validar cantidad de aeronaves
            if (request.getAircraftIds().size() < MIN_COMPARISON_AIRCRAFT) {
                result.addError(String.format("At least %d aircraft are required for comparison", MIN_COMPARISON_AIRCRAFT));
            }

            if (request.getAircraftIds().size() > MAX_COMPARISON_AIRCRAFT) {
                result.addError(String.format("Maximum %d aircraft allowed for comparison", MAX_COMPARISON_AIRCRAFT));
            }

            // Validar que no hay IDs duplicados
            Set<Long> uniqueIds = new HashSet<>(request.getAircraftIds());
            if (uniqueIds.size() != request.getAircraftIds().size()) {
                result.addError("Duplicate aircraft IDs are not allowed");
            }

            // Validar que todas las aeronaves existen y están activas
            for (Long id : request.getAircraftIds()) {
                if (id == null || id <= 0) {
                    result.addError("Invalid aircraft ID: " + id);
                } else {
                    Optional<AircraftModel> aircraft = aircraftRepository.findById(id);
                    if (aircraft.isEmpty()) {
                        result.addError("Aircraft not found: " + id);
                    } else if (!aircraft.get().getIsActive()) {
                        result.addWarning("Aircraft is inactive: " + aircraft.get().getName());
                    }
                }
            }
        }

        // Validar formato de unidades
        if (request.getUnitFormat() != null &&
                !Arrays.asList("metric", "imperial", "mixed").contains(request.getUnitFormat().toLowerCase())) {
            result.addError("Unit format must be 'metric', 'imperial', or 'mixed'");
        }

        return result;
    }

    @Override
    public ValidationResult validateIds(List<Long> ids) {
        logger.debug("Validating ID list of size: {}", ids != null ? ids.size() : 0);

        ValidationResult result = new ValidationResult();

        if (ids == null || ids.isEmpty()) {
            result.addError("IDs list cannot be null or empty");
            return result;
        }

        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            if (id == null) {
                result.addError(String.format("ID at position %d cannot be null", i));
            } else if (id <= 0) {
                result.addError(String.format("ID at position %d must be positive: %d", i, id));
            }
        }

        // Verificar duplicados
        Set<Long> uniqueIds = new HashSet<>();
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            if (id != null && !uniqueIds.add(id)) {
                result.addError(String.format("Duplicate ID found: %d", id));
            }
        }

        return result;
    }

    @Override
    public ValidationResult validatePaginationParameters(int page, int size) {
        logger.debug("Validating pagination: page={}, size={}", page, size);

        ValidationResult result = new ValidationResult();

        if (page < 0) {
            result.addError("Page number cannot be negative");
        }

        if (size <= 0) {
            result.addError("Page size must be positive");
        } else if (size > 100) {
            result.addError("Page size cannot exceed 100");
        }

        return result;
    }

    @Override
    public ValidationResult validateSortField(String field, String direction) {
        logger.debug("Validating sort parameters: field={}, direction={}", field, direction);

        ValidationResult result = new ValidationResult();

        // Validar dirección
        if (direction != null &&
                !Arrays.asList("ASC", "DESC", "asc", "desc").contains(direction)) {
            result.addError("Sort direction must be 'ASC' or 'DESC'");
        }

        // Validar campo (lista de campos permitidos para ordenamiento)
        if (field != null) {
            Set<String> allowedFields = Set.of(
                    "name", "model", "maxPassengers", "typicalPassengers",
                    "rangeKm", "cruiseSpeedKnots", "introductionYear",
                    "manufacturer.name", "family.name", "type.name"
            );

            if (!allowedFields.contains(field)) {
                result.addError("Invalid sort field: " + field);
            }
        }

        return result;
    }

    @Override
    public ValidationResult validateSearchQuery(String query) {
        logger.debug("Validating search query: '{}'", query);

        ValidationResult result = new ValidationResult();

        if (query == null || query.trim().isEmpty()) {
            result.addError("Search query cannot be empty");
        } else {
            String trimmedQuery = query.trim();

            if (trimmedQuery.length() < 2) {
                result.addError("Search query must be at least 2 characters long");
            } else if (trimmedQuery.length() > 100) {
                result.addError("Search query cannot exceed 100 characters");
            }

            // Validar caracteres especiales peligrosos
            if (containsDangerousCharacters(trimmedQuery)) {
                result.addWarning("Search query contains special characters that may affect results");
            }

            // Sugerir correcciones para consultas comunes mal escritas
            if (containsCommonMisspellings(trimmedQuery)) {
                result.addWarning("Search query may contain misspellings");
            }
        }

        return result;
    }

    @Override
    public boolean isValidAircraftId(Long aircraftId) {
        if (aircraftId == null || aircraftId <= 0) {
            return false;
        }

        return aircraftRepository.existsById(aircraftId);
    }

    @Override
    public boolean isValidManufacturerId(Long manufacturerId) {
        if (manufacturerId == null || manufacturerId <= 0) {
            return false;
        }

        return manufacturerRepository.existsById(manufacturerId);
    }

    @Override
    public boolean isValidFamilyId(Long familyId) {
        if (familyId == null || familyId <= 0) {
            return false;
        }

        return familyRepository.existsById(familyId);
    }

    @Override
    public ValidationResult validateAircraftForComparison(List<Long> aircraftIds) {
        logger.debug("Validating aircraft for comparison: {}", aircraftIds);

        ValidationResult result = validateIds(aircraftIds);

        if (!result.isValid()) {
            return result;
        }

        // Validaciones específicas para comparación
        if (aircraftIds.size() < MIN_COMPARISON_AIRCRAFT) {
            result.addError(String.format("At least %d aircraft required for comparison", MIN_COMPARISON_AIRCRAFT));
        }

        if (aircraftIds.size() > MAX_COMPARISON_AIRCRAFT) {
            result.addError(String.format("Maximum %d aircraft allowed for comparison", MAX_COMPARISON_AIRCRAFT));
        }

        // Verificar que las aeronaves existen y están activas
        List<AircraftModel> aircraft = aircraftRepository.findAllById(aircraftIds);

        if (aircraft.size() != aircraftIds.size()) {
            result.addError("Some aircraft were not found");
        }

        long inactiveCount = aircraft.stream()
                .mapToLong(a -> a.getIsActive() ? 0 : 1)
                .sum();

        if (inactiveCount > 0) {
            result.addWarning(String.format("%d aircraft are inactive", inactiveCount));
        }

        // Verificar que no son todas del mismo modelo (menos útil para comparación)
        Set<String> uniqueModels = aircraft.stream()
                .map(AircraftModel::getModel)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);

        if (uniqueModels.size() == 1) {
            result.addWarning("All aircraft are the same model - comparison may be less useful");
        }

        return result;
    }

    @Override
    public List<String> getValidationMessages(ValidationResult result) {
        List<String> messages = new ArrayList<>();

        if (result != null) {
            if (result.getErrors() != null) {
                messages.addAll(result.getErrors());
            }
            if (result.getWarnings() != null) {
                messages.addAll(result.getWarnings());
            }
        }

        return messages;
    }

    // Métodos privados auxiliares

    private void validateAircraftRelationships(AircraftModel aircraft, ValidationResult result) {
        // Validar que el fabricante existe
        if (aircraft.getManufacturer() != null && aircraft.getManufacturer().getId() != null) {
            if (!manufacturerRepository.existsById(aircraft.getManufacturer().getId())) {
                result.addError("Referenced manufacturer does not exist");
            }
        }

        // Validar que la familia existe
        if (aircraft.getFamily() != null && aircraft.getFamily().getId() != null) {
            if (!familyRepository.existsById(aircraft.getFamily().getId())) {
                result.addError("Referenced family does not exist");
            }
        }
    }

    private void validateAircraftUniqueness(AircraftModel aircraft, ValidationResult result) {
        // Verificar que no existe otra aeronave con el mismo nombre
        if (aircraft.getName() != null) {
            boolean exists = aircraftRepository.existsByNameIgnoreCase(aircraft.getName());
            if (exists) {
                result.addError("Aircraft with this name already exists");
            }
        }

        // Verificar combinación modelo + fabricante
        if (aircraft.getModel() != null && aircraft.getManufacturer() != null) {
            boolean exists = aircraftRepository.existsByModelAndManufacturerId(
                    aircraft.getModel(), aircraft.getManufacturer().getId()
            );
            if (exists) {
                result.addWarning("Aircraft with this model and manufacturer combination already exists");
            }
        }
    }

    private boolean containsDangerousCharacters(String query) {
        // Buscar caracteres que podrían ser problemáticos
        String dangerous = "<>\"'%;()&+";
        return query.chars().anyMatch(c -> dangerous.indexOf(c) >= 0);
    }

    private boolean containsCommonMisspellings(String query) {
        String lowerQuery = query.toLowerCase();

        // Errores comunes en nombres de aeronaves
        String[] misspellings = {
                "boing", "airb us", "787-8", "a320neo", "737max"
        };

        return Arrays.stream(misspellings)
                .anyMatch(lowerQuery::contains);
    }
}
