package com.skyvault.backend.specification;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public final class SpecificationUtils {

    private SpecificationUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Combina múltiples especificaciones con AND
     */
    @SafeVarargs
    public static <T> Specification<T> and(Specification<T>... specs) {
        Specification<T> result = null;

        for (Specification<T> spec : specs) {
            if (spec != null) {
                result = (result == null) ? spec : result.and(spec);
            }
        }

        return result;
    }

    /**
     * Combina múltiples especificaciones con OR
     */
    @SafeVarargs
    public static <T> Specification<T> or(Specification<T>... specs) {
        Specification<T> result = null;

        for (Specification<T> spec : specs) {
            if (spec != null) {
                result = (result == null) ? spec : result.or(spec);
            }
        }

        return result;
    }

    /**
     * Valida campos de ordenamiento permitidos
     */
    public static Sort validateAndCreateSort(String sortField, String sortDirection, List<String> allowedFields) {
        // Validar campo
        if (sortField == null || sortField.trim().isEmpty()) {
            return Sort.by(Sort.Direction.ASC, "id"); // Default
        }

        if (!allowedFields.contains(sortField)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField +
                    ". Allowed fields: " + allowedFields);
        }

        // Validar dirección
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection != null ? sortDirection : "ASC");
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC; // Default
        }

        return Sort.by(direction, sortField);
    }

    /**
     * Campos de ordenamiento permitidos para Aircraft
     */
    public static final List<String> AIRCRAFT_SORT_FIELDS = Arrays.asList(
            "id", "name", "model", "displayName", "introductionYear",
            "maxPassengers", "typicalPassengers", "rangeKm", "cruiseSpeedKnots",
            "manufacturer.name", "family.name", "type.name",
            "productionState.name", "sizeCategory.name", "createdAt", "updatedAt"
    );

    /**
     * Campos de ordenamiento permitidos para Manufacturer
     */
    public static final List<String> MANUFACTURER_SORT_FIELDS = Arrays.asList(
            "id", "name", "country", "foundedDate", "createdAt", "updatedAt"
    );

    /**
     * Campos de ordenamiento permitidos para Family
     */
    public static final List<String> FAMILY_SORT_FIELDS = Arrays.asList(
            "id", "name", "category", "launchDate",
            "manufacturer.name", "createdAt", "updatedAt"
    );

    /**
     * Crea un Sort para Aircraft con validación
     */
    public static Sort createAircraftSort(String sortField, String sortDirection) {
        return validateAndCreateSort(sortField, sortDirection, AIRCRAFT_SORT_FIELDS);
    }

    /**
     * Crea un Sort para Manufacturer con validación
     */
    public static Sort createManufacturerSort(String sortField, String sortDirection) {
        return validateAndCreateSort(sortField, sortDirection, MANUFACTURER_SORT_FIELDS);
    }

    /**
     * Crea un Sort para Family con validación
     */
    public static Sort createFamilySort(String sortField, String sortDirection) {
        return validateAndCreateSort(sortField, sortDirection, FAMILY_SORT_FIELDS);
    }

    /**
     * Crea ordenamiento multi-campo
     */
    public static Sort createMultiFieldSort(String... fieldDirections) {
        if (fieldDirections == null || fieldDirections.length == 0) {
            return Sort.by(Sort.Direction.ASC, "id");
        }

        Sort.Order[] orders = new Sort.Order[fieldDirections.length];

        for (int i = 0; i < fieldDirections.length; i++) {
            String[] parts = fieldDirections[i].split(":");
            String field = parts[0];
            Sort.Direction direction = parts.length > 1 ?
                    Sort.Direction.fromString(parts[1]) : Sort.Direction.ASC;

            orders[i] = new Sort.Order(direction, field);
        }

        return Sort.by(orders);
    }
}
