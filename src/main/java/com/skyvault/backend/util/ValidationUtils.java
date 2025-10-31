package com.skyvault.backend.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.regex.Pattern;

public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[+]?[1-9]\\d{1,14}$"
    );

    private static final int MIN_AIRCRAFT_YEAR = 1900;
    private static final int MAX_PASSENGERS = 10000;
    private static final int MAX_RANGE_KM = 50000;
    private static final int MAX_SPEED_KMH = 5000;

    private ValidationUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Valida dirección de email
     */
    public static boolean isValidEmail(String email) {
        return StringUtils.isNotEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Valida número de teléfono
     */
    public static boolean isValidPhone(String phone) {
        return StringUtils.isNotEmpty(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Valida año de introducción de aeronave
     */
    public static boolean isValidAircraftYear(Integer year) {
        if (year == null) {
            return true; // Permitir null
        }

        int currentYear = Year.now().getValue();
        return year >= MIN_AIRCRAFT_YEAR && year <= currentYear + 5; // Permitir hasta 5 años en el futuro
    }

    /**
     * Valida capacidad de pasajeros
     */
    public static boolean isValidPassengerCount(Integer passengers) {
        if (passengers == null) {
            return true; // Permitir null
        }

        return passengers > 0 && passengers <= MAX_PASSENGERS;
    }

    /**
     * Valida rango de aeronave en kilómetros
     */
    public static boolean isValidRangeKm(Integer rangeKm) {
        if (rangeKm == null) {
            return true; // Permitir null
        }

        return rangeKm > 0 && rangeKm <= MAX_RANGE_KM;
    }

    /**
     * Valida velocidad en km/h
     */
    public static boolean isValidSpeedKmh(Integer speedKmh) {
        if (speedKmh == null) {
            return true; // Permitir null
        }

        return speedKmh > 0 && speedKmh <= MAX_SPEED_KMH;
    }

    /**
     * Valida fecha de primer vuelo
     */
    public static boolean isValidFirstFlightDate(LocalDate date) {
        if (date == null) {
            return true; // Permitir null
        }

        LocalDate minDate = LocalDate.of(MIN_AIRCRAFT_YEAR, 1, 1);
        LocalDate maxDate = LocalDate.now().plusYears(5);

        return !date.isBefore(minDate) && !date.isAfter(maxDate);
    }

    /**
     * Valida dimensiones físicas (longitud, envergadura, altura) en metros
     */
    public static boolean isValidDimension(BigDecimal dimension) {
        if (dimension == null) {
            return true; // Permitir null
        }

        return dimension.compareTo(BigDecimal.ZERO) > 0 &&
                dimension.compareTo(new BigDecimal("200")) <= 0; // Máximo 200 metros
    }

    /**
     * Valida peso en kilogramos
     */
    public static boolean isValidWeight(BigDecimal weight) {
        if (weight == null) {
            return true; // Permitir null
        }

        return weight.compareTo(BigDecimal.ZERO) > 0 &&
                weight.compareTo(new BigDecimal("1000000")) <= 0; // Máximo 1,000 toneladas
    }

    /**
     * Valida empuje en Newtons
     */
    public static boolean isValidThrust(BigDecimal thrust) {
        if (thrust == null) {
            return true; // Permitir null
        }

        return thrust.compareTo(BigDecimal.ZERO) > 0 &&
                thrust.compareTo(new BigDecimal("1000000")) <= 0; // Máximo 1,000,000 N
    }

    /**
     * Valida capacidad de combustible en litros
     */
    public static boolean isValidFuelCapacity(Integer fuelCapacity) {
        if (fuelCapacity == null) {
            return true; // Permitir null
        }

        return fuelCapacity > 0 && fuelCapacity <= 500000; // Máximo 500,000 litros
    }

    /**
     * Valida rango de valores para filtros
     */
    public static boolean isValidRange(Integer min, Integer max) {
        if (min == null && max == null) {
            return true;
        }

        if (min == null || max == null) {
            return true; // Un solo valor es válido
        }

        return min <= max;
    }

    /**
     * Valida rango de valores BigDecimal para filtros
     */
    public static boolean isValidRange(BigDecimal min, BigDecimal max) {
        if (min == null && max == null) {
            return true;
        }

        if (min == null || max == null) {
            return true; // Un solo valor es válido
        }

        return min.compareTo(max) <= 0;
    }

    /**
     * Valida parámetros de paginación
     */
    public static boolean isValidPageSize(Integer size) {
        return size != null && size > 0 && size <= 100; // Máximo 100 elementos por página
    }

    /**
     * Valida número de página
     */
    public static boolean isValidPageNumber(Integer page) {
        return page != null && page >= 0;
    }

    /**
     * Valida lista de IDs para comparación
     */
    public static boolean isValidComparisonIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }

        // Máximo 5 aeronaves para comparar
        if (ids.size() > 5) {
            return false;
        }

        // No debe haber IDs duplicados
        return ids.size() == ids.stream().distinct().count();
    }

    /**
     * Valida orden de imagen
     */
    public static boolean isValidDisplayOrder(Integer order) {
        return order != null && order >= 0 && order <= 999;
    }

    /**
     * Valida tipo de imagen
     */
    public static boolean isValidImageType(String imageType) {
        if (StringUtils.isEmpty(imageType)) {
            return false;
        }

        List<String> validTypes = List.of("thumbnail", "main", "comparison", "gallery", "detail");
        return validTypes.contains(imageType.toLowerCase());
    }

    /**
     * Valida formato de archivo de imagen
     */
    public static boolean isValidImageFormat(String format) {
        if (StringUtils.isEmpty(format)) {
            return false;
        }

        List<String> validFormats = List.of("jpg", "jpeg", "png", "webp", "avif");
        return validFormats.contains(format.toLowerCase());
    }

    /**
     * Valida tamaño de archivo en bytes
     */
    public static boolean isValidFileSize(Long sizeBytes) {
        if (sizeBytes == null) {
            return true;
        }

        // Máximo 10MB para imágenes
        return sizeBytes > 0 && sizeBytes <= 10 * 1024 * 1024;
    }

    /**
     * Valida dimensiones de imagen en píxeles
     */
    public static boolean isValidImageDimensions(Integer width, Integer height) {
        if (width == null || height == null) {
            return true;
        }

        return width > 0 && height > 0 &&
                width <= 5000 && height <= 5000; // Máximo 5000x5000px
    }

    /**
     * Valida que el texto de búsqueda tenga longitud mínima
     */
    public static boolean isValidSearchTerm(String searchTerm) {
        if (StringUtils.isEmpty(searchTerm)) {
            return true; // Permitir búsquedas vacías
        }

        String trimmed = searchTerm.trim();
        return trimmed.length() >= 2 && trimmed.length() <= 100;
    }

    /**
     * Valida número de motores
     */
    public static boolean isValidEngineCount(Integer count) {
        if (count == null) {
            return true;
        }

        return count >= 1 && count <= 8; // Entre 1 y 8 motores
    }

    /**
     * Valida número de tripulación mínima
     */
    public static boolean isValidCrewCount(Integer crew) {
        if (crew == null) {
            return true;
        }

        return crew >= 1 && crew <= 20; // Entre 1 y 20 tripulantes
    }

    /**
     * Valida configuración de asientos
     */
    public static boolean isValidSeatConfiguration(Integer firstClass, Integer business, Integer economy) {
        // Al menos uno debe ser mayor a 0 si se especifica
        if (firstClass != null && firstClass < 0) return false;
        if (business != null && business < 0) return false;
        if (economy != null && economy < 0) return false;

        // Si todos son 0 o null, es inválido
        int total = (firstClass != null ? firstClass : 0) +
                (business != null ? business : 0) +
                (economy != null ? economy : 0);

        return total > 0;
    }
}
