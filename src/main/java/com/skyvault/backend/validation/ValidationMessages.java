package com.skyvault.backend.validation;

/**
 * Constantes para mensajes de validación
 */
public final class ValidationMessages {

    // Mensajes generales
    public static final String REQUIRED = "This field is required";
    public static final String INVALID_FORMAT = "Invalid format";
    public static final String OUT_OF_RANGE = "Value is out of allowed range";

    // Mensajes específicos para Aircraft
    public static final String AIRCRAFT_NAME_REQUIRED = "Aircraft name is required";
    public static final String AIRCRAFT_MODEL_REQUIRED = "Aircraft model is required";
    public static final String AIRCRAFT_NAME_SIZE = "Aircraft name must be between 2 and 100 characters";
    public static final String AIRCRAFT_MODEL_SIZE = "Aircraft model must be between 2 and 50 characters";

    // Mensajes para rangos
    public static final String PASSENGERS_RANGE = "Passengers must be between 1 and 1,000";
    public static final String RANGE_KM_RANGE = "Range must be between 100 and 50,000 km";
    public static final String SPEED_RANGE = "Speed must be between 100 and 1,000 knots";
    public static final String YEAR_RANGE = "Year must be between 1900 and current year + 5";

    // Mensajes para IDs
    public static final String INVALID_ID = "Invalid ID";
    public static final String ID_POSITIVE = "ID must be a positive number";
    public static final String TOO_MANY_IDS = "Too many IDs provided";
    public static final String DUPLICATE_IDS = "Duplicate IDs are not allowed";

    // Mensajes para paginación
    public static final String PAGE_NEGATIVE = "Page number cannot be negative";
    public static final String PAGE_SIZE_POSITIVE = "Page size must be positive";
    public static final String PAGE_SIZE_LIMIT = "Page size exceeds maximum limit";

    // Mensajes para ordenamiento
    public static final String INVALID_SORT_FIELD = "Invalid sort field";
    public static final String SORT_FIELD_REQUIRED = "Sort field is required";

    private ValidationMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
