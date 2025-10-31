package com.skyvault.backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public final class DateUtils {

    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    private DateUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Obtiene la fecha actual
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * Obtiene el año actual
     */
    public static int currentYear() {
        return Year.now().getValue();
    }

    /**
     * Convierte string a LocalDate de forma segura
     */
    public static Optional<LocalDate> parseDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDate.parse(dateString, ISO_DATE_FORMATTER));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Convierte string en formato dd/MM/yyyy a LocalDate
     */
    public static Optional<LocalDate> parseDisplayDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDate.parse(dateString, DISPLAY_DATE_FORMATTER));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Convierte LocalDate a string ISO
     */
    public static String formatIsoDate(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(ISO_DATE_FORMATTER);
    }

    /**
     * Convierte LocalDate a formato de visualización
     */
    public static String formatDisplayDate(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(DISPLAY_DATE_FORMATTER);
    }

    /**
     * Convierte LocalDate a formato año/mes
     */
    public static String formatYearMonth(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(YEAR_MONTH_FORMATTER);
    }

    /**
     * Calcula la diferencia en años entre dos fechas
     */
    public static long yearsBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        return ChronoUnit.YEARS.between(startDate, endDate);
    }

    /**
     * Calcula la diferencia en días entre dos fechas
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Calcula la edad de una aeronave desde su primer vuelo
     */
    public static int calculateAircraftAge(LocalDate firstFlightDate) {
        if (firstFlightDate == null) {
            return 0;
        }

        return (int) yearsBetween(firstFlightDate, LocalDate.now());
    }

    /**
     * Calcula los años desde la introducción
     */
    public static int yearsSinceIntroduction(Integer introductionYear) {
        if (introductionYear == null) {
            return 0;
        }

        return currentYear() - introductionYear;
    }

    /**
     * Verifica si una fecha está en el pasado
     */
    public static boolean isInPast(LocalDate date) {
        if (date == null) {
            return false;
        }

        return date.isBefore(LocalDate.now());
    }

    /**
     * Verifica si una fecha está en el futuro
     */
    public static boolean isInFuture(LocalDate date) {
        if (date == null) {
            return false;
        }

        return date.isAfter(LocalDate.now());
    }

    /**
     * Verifica si una fecha es hoy
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }

        return date.equals(LocalDate.now());
    }

    /**
     * Obtiene el primer día del año
     */
    public static LocalDate startOfYear(int year) {
        return LocalDate.of(year, 1, 1);
    }

    /**
     * Obtiene el último día del año
     */
    public static LocalDate endOfYear(int year) {
        return LocalDate.of(year, 12, 31);
    }

    /**
     * Verifica si una fecha está dentro de un rango
     */
    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null) {
            return false;
        }

        if (startDate != null && date.isBefore(startDate)) {
            return false;
        }

        if (endDate != null && date.isAfter(endDate)) {
            return false;
        }

        return true;
    }

    /**
     * Obtiene una fecha relativa (ej: hace 5 años)
     */
    public static LocalDate yearsAgo(int years) {
        return LocalDate.now().minusYears(years);
    }

    /**
     * Obtiene una fecha futura (ej: en 5 años)
     */
    public static LocalDate yearsFromNow(int years) {
        return LocalDate.now().plusYears(years);
    }

    /**
     * Formatea una edad en años de forma descriptiva
     */
    public static String formatAge(int years) {
        if (years == 0) {
            return "Nuevo";
        } else if (years == 1) {
            return "1 año";
        } else {
            return years + " años";
        }
    }

    /**
     * Genera un rango de años desde introducción hasta presente
     */
    public static String formatProductionPeriod(Integer introductionYear, LocalDate lastProductionDate) {
        if (introductionYear == null) {
            return "Desconocido";
        }

        String start = introductionYear.toString();

        if (lastProductionDate == null) {
            return start + " - presente";
        }

        String end = String.valueOf(lastProductionDate.getYear());

        if (start.equals(end)) {
            return start;
        }

        return start + " - " + end;
    }

    /**
     * Convierte año Integer a LocalDate (1 de enero)
     */
    public static LocalDate yearToDate(Integer year) {
        if (year == null) {
            return null;
        }

        return LocalDate.of(year, 1, 1);
    }

    /**
     * Extrae el año de una LocalDate
     */
    public static Integer dateToYear(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }

    /**
     * Genera timestamp para logging
     */
    public static String currentTimestamp() {
        return LocalDateTime.now().toString();
    }

    /**
     * Verifica si es un año válido para aeronaves
     */
    public static boolean isValidAircraftYear(Integer year) {
        if (year == null) {
            return true;
        }

        int currentYear = currentYear();
        return year >= 1900 && year <= currentYear + 10; // Permitir hasta 10 años en el futuro
    }

    /**
     * Genera texto descriptivo de una fecha
     */
    public static String getDateDescription(LocalDate date) {
        if (date == null) {
            return "Fecha no disponible";
        }

        long daysDiff = daysBetween(date, LocalDate.now());

        if (daysDiff == 0) {
            return "Hoy";
        } else if (daysDiff == 1) {
            return "Ayer";
        } else if (daysDiff == -1) {
            return "Mañana";
        } else if (daysDiff > 0 && daysDiff <= 7) {
            return "Hace " + daysDiff + " días";
        } else if (daysDiff < 0 && daysDiff >= -7) {
            return "En " + Math.abs(daysDiff) + " días";
        } else {
            return formatDisplayDate(date);
        }
    }
}
