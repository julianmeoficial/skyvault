package com.skyvault.backend.util;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class StringUtils {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9\\s]");
    private static final Pattern SLUG_PATTERN = Pattern.compile("[^a-z0-9]+");

    private StringUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    //Verifica si una cadena es null o vacía/
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    //Verifica si una cadena no es null ni vacía/
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    //Verifica si una cadena es null, vacía o contiene solo espacios en blanco/
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Verifica si una cadena no es null, vacía ni contiene solo espacios
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Capitaliza la primera letra de cada palabra
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return Arrays.stream(str.toLowerCase().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    /**
     * Capitaliza solo la primera letra de la cadena
     */
    public static String capitalizeFirst(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Normaliza espacios en blanco (elimina múltiples espacios)
     */
    public static String normalizeWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return WHITESPACE_PATTERN.matcher(str.trim()).replaceAll(" ");
    }

    /**
     * Crea un slug URL-friendly desde una cadena
     */
    public static String toSlug(String str) {
        if (isEmpty(str)) {
            return "";
        }

        // Normalizar caracteres acentuados
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);

        // Eliminar diacríticos
        String withoutAccents = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Convertir a minúsculas y reemplazar caracteres no alfanuméricos
        return SLUG_PATTERN.matcher(withoutAccents.toLowerCase())
                .replaceAll("-")
                .replaceAll("^-+|-+$", ""); // Eliminar guiones al inicio y final
    }

    /**
     * Limpia caracteres especiales para búsquedas
     */
    public static String cleanForSearch(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return SPECIAL_CHARS_PATTERN.matcher(str.toLowerCase().trim())
                .replaceAll(" ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    /**
     * Trunca una cadena a la longitud especificada
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }

        return str.substring(0, maxLength);
    }

    /**
     * Trunca una cadena agregando "..." si es necesario
     */
    public static String truncateWithEllipsis(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }

        if (maxLength <= 3) {
            return str.substring(0, maxLength);
        }

        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * Convierte una lista de strings en una cadena separada por comas
     */
    public static String join(List<String> items, String delimiter) {
        if (items == null || items.isEmpty()) {
            return "";
        }

        return items.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Divide una cadena por comas y limpia espacios
     */
    public static List<String> splitAndTrim(String str, String delimiter) {
        if (isEmpty(str)) {
            return List.of();
        }

        return Arrays.stream(str.split(delimiter))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    /**
     * Enmascara caracteres sensibles (para logging)
     */
    public static String mask(String str, int visibleChars) {
        if (isEmpty(str) || str.length() <= visibleChars) {
            return "*".repeat(Math.max(str != null ? str.length() : 0, 3));
        }

        return str.substring(0, visibleChars) + "*".repeat(str.length() - visibleChars);
    }

    /**
     * Genera un display name para aeronaves
     */
    public static String generateAircraftDisplayName(String manufacturerName, String aircraftName) {
        if (isEmpty(manufacturerName) && isEmpty(aircraftName)) {
            return "Unknown Aircraft";
        }

        if (isEmpty(manufacturerName)) {
            return aircraftName.trim();
        }

        if (isEmpty(aircraftName)) {
            return manufacturerName.trim();
        }

        return manufacturerName.trim() + " " + aircraftName.trim();
    }

    /**
     * Normaliza nombres de fabricantes
     */
    public static String normalizeManufacturerName(String name) {
        if (isEmpty(name)) {
            return name;
        }

        String normalized = name.trim();

        // Casos especiales para fabricantes conocidos
        switch (normalized.toLowerCase()) {
            case "airbus":
            case "airbus s.a.s":
            case "airbus sas":
                return "Airbus";
            case "boeing":
            case "the boeing company":
            case "boeing company":
                return "Boeing";
            default:
                return capitalize(normalized);
        }
    }

    /**
     * Valida formato de URL
     */
    public static boolean isValidUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }

        return url.matches("^https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+$");
    }

    /**
     * Limpia y valida URLs
     */
    public static String cleanUrl(String url) {
        if (isEmpty(url)) {
            return null;
        }

        String cleaned = url.trim();

        if (!cleaned.startsWith("http://") && !cleaned.startsWith("https://")) {
            cleaned = "https://" + cleaned;
        }

        return isValidUrl(cleaned) ? cleaned : null;
    }

    //Genera un código de modelo normalizado/
    public static String generateModelCode(String manufacturerName, String modelName) {
        if (isEmpty(manufacturerName) || isEmpty(modelName)) {
            return null;
        }

        String manufacturer = manufacturerName.substring(0, Math.min(3, manufacturerName.length())).toUpperCase();
        String model = cleanForSearch(modelName).replaceAll("\\s+", "").toUpperCase();

        return manufacturer + "-" + model;
    }
}
