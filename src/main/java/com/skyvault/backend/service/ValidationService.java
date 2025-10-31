package com.skyvault.backend.service;

import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.validation.ValidationResult;

import java.util.List;

/**
 * Servicio para validaciones de reglas de negocio complejas.
 */
public interface ValidationService {

    /**
     * Validar modelo de aeronave
     */
    ValidationResult validateAircraftModel(AircraftModel aircraft);

    /**
     * Validar request de comparación
     */
    ValidationResult validateComparisonRequest(CompareRequestDto request);

    /**
     * Validar lista de IDs
     */
    ValidationResult validateIds(List<Long> ids);

    /**
     * Validar parámetros de paginación
     */
    ValidationResult validatePaginationParameters(int page, int size);

    /**
     * Validar campo de ordenamiento
     */
    ValidationResult validateSortField(String field, String direction);

    /**
     * Validar consulta de búsqueda
     */
    ValidationResult validateSearchQuery(String query);

    /**
     * Validar ID de aeronave
     */
    boolean isValidAircraftId(Long aircraftId);

    /**
     * Validar ID de fabricante
     */
    boolean isValidManufacturerId(Long manufacturerId);

    /**
     * Validar ID de familia
     */
    boolean isValidFamilyId(Long familyId);

    /**
     * Validar aeronaves para comparación
     */
    ValidationResult validateAircraftForComparison(List<Long> aircraftIds);

    /**
     * Obtener mensajes de validación
     */
    List<String> getValidationMessages(ValidationResult result);
}
