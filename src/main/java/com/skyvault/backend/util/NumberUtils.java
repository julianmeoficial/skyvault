package com.skyvault.backend.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumberUtils {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    private static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,##0");
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    private NumberUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Verifica si un número es null o cero
     */
    public static boolean isNullOrZero(Integer number) {
        return number == null || number == 0;
    }

    /**
     * Verifica si un BigDecimal es null o cero
     */
    public static boolean isNullOrZero(BigDecimal number) {
        return number == null || number.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Verifica si un número es positivo
     */
    public static boolean isPositive(Integer number) {
        return number != null && number > 0;
    }

    /**
     * Verifica si un BigDecimal es positivo
     */
    public static boolean isPositive(BigDecimal number) {
        return number != null && number.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Convierte metros a pies
     */
    public static BigDecimal metersToFeet(BigDecimal meters) {
        if (meters == null) {
            return null;
        }

        return meters.multiply(new BigDecimal("3.28084")).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte pies a metros
     */
    public static BigDecimal feetToMeters(BigDecimal feet) {
        if (feet == null) {
            return null;
        }

        return feet.divide(new BigDecimal("3.28084"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte kilómetros a millas náuticas
     */
    public static Integer kmToNauticalMiles(Integer km) {
        if (km == null) {
            return null;
        }

        return Math.round(km / 1.852f);
    }

    /**
     * Convierte millas náuticas a kilómetros
     */
    public static Integer nauticalMilesToKm(Integer nauticalMiles) {
        if (nauticalMiles == null) {
            return null;
        }

        return Math.round(nauticalMiles * 1.852f);
    }

    /**
     * Convierte km/h a nudos
     */
    public static Integer kmhToKnots(Integer kmh) {
        if (kmh == null) {
            return null;
        }

        return Math.round(kmh / 1.852f);
    }

    /**
     * Convierte nudos a km/h
     */
    public static Integer knotsToKmh(Integer knots) {
        if (knots == null) {
            return null;
        }

        return Math.round(knots * 1.852f);
    }

    /**
     * Convierte kilogramos a libras
     */
    public static BigDecimal kgToPounds(BigDecimal kg) {
        if (kg == null) {
            return null;
        }

        return kg.multiply(new BigDecimal("2.20462")).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte libras a kilogramos
     */
    public static BigDecimal poundsToKg(BigDecimal pounds) {
        if (pounds == null) {
            return null;
        }

        return pounds.divide(new BigDecimal("2.20462"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte litros a galones US
     */
    public static BigDecimal litersToGallons(Integer liters) {
        if (liters == null) {
            return null;
        }

        return new BigDecimal(liters).divide(new BigDecimal("3.78541"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte galones US a litros
     */
    public static Integer gallonsToLiters(BigDecimal gallons) {
        if (gallons == null) {
            return null;
        }

        return gallons.multiply(new BigDecimal("3.78541")).intValue();
    }

    /**
     * Formatea un número entero con separadores de miles
     */
    public static String formatInteger(Integer number) {
        if (number == null) {
            return "N/A";
        }

        return INTEGER_FORMAT.format(number);
    }

    /**
     * Formatea un BigDecimal con separadores de miles y decimales
     */
    public static String formatDecimal(BigDecimal number) {
        if (number == null) {
            return "N/A";
        }

        return DECIMAL_FORMAT.format(number);
    }

    /**
     * Formatea un número como moneda USD
     */
    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "N/A";
        }

        return CURRENCY_FORMAT.format(amount);
    }

    /**
     * Formatea capacidad de pasajeros
     */
    public static String formatPassengerCapacity(Integer typical, Integer maximum) {
        if (typical == null && maximum == null) {
            return "N/A";
        }

        if (typical == null) {
            return formatInteger(maximum) + " (máx)";
        }

        if (maximum == null) {
            return formatInteger(typical) + " (típico)";
        }

        if (typical.equals(maximum)) {
            return formatInteger(typical);
        }

        return formatInteger(typical) + " - " + formatInteger(maximum);
    }

    /**
     * Formatea rango de aeronave
     */
    public static String formatRange(Integer rangeKm) {
        if (rangeKm == null) {
            return "N/A";
        }

        return formatInteger(rangeKm) + " km (" + formatInteger(kmToNauticalMiles(rangeKm)) + " nm)";
    }

    /**
     * Formatea velocidad
     */
    public static String formatSpeed(Integer speedKmh) {
        if (speedKmh == null) {
            return "N/A";
        }

        return formatInteger(speedKmh) + " km/h (" + formatInteger(kmhToKnots(speedKmh)) + " kts)";
    }

    /**
     * Formatea dimensiones físicas
     */
    public static String formatDimension(BigDecimal meters, String unit) {
        if (meters == null) {
            return "N/A";
        }

        BigDecimal feet = metersToFeet(meters);
        return formatDecimal(meters) + " m (" + formatDecimal(feet) + " ft)" +
                (StringUtils.isNotEmpty(unit) ? " " + unit : "");
    }

    /**
     * Formatea peso
     */
    public static String formatWeight(BigDecimal kg) {
        if (kg == null) {
            return "N/A";
        }

        BigDecimal pounds = kgToPounds(kg);
        return formatDecimal(kg) + " kg (" + formatDecimal(pounds) + " lb)";
    }

    /**
     * Formatea capacidad de combustible
     */
    public static String formatFuelCapacity(Integer liters) {
        if (liters == null) {
            return "N/A";
        }

        BigDecimal gallons = litersToGallons(liters);
        return formatInteger(liters) + " L (" + formatDecimal(gallons) + " gal)";
    }

    /**
     * Formatea empuje de motor
     */
    public static String formatThrust(BigDecimal newtons) {
        if (newtons == null) {
            return "N/A";
        }

        // Convertir Newtons a pounds-force (lbf)
        BigDecimal lbf = newtons.divide(new BigDecimal("4.44822"), 0, RoundingMode.HALF_UP);

        return formatDecimal(newtons) + " N (" + formatDecimal(lbf) + " lbf)";
    }

    /**
     * Calcula porcentaje
     */
    public static BigDecimal calculatePercentage(BigDecimal part, BigDecimal total) {
        if (part == null || total == null || total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return part.divide(total, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Formatea porcentaje
     */
    public static String formatPercentage(BigDecimal percentage) {
        if (percentage == null) {
            return "0%";
        }

        return formatDecimal(percentage) + "%";
    }

    /**
     * Redondea a entero más cercano
     */
    public static Integer roundToInteger(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    /**
     * Obtiene el valor seguro (nunca null)
     */
    public static Integer safeInteger(Integer number, Integer defaultValue) {
        return number != null ? number : defaultValue;
    }

    /**
     * Obtiene el valor seguro (nunca null)
     */
    public static BigDecimal safeBigDecimal(BigDecimal number, BigDecimal defaultValue) {
        return number != null ? number : defaultValue;
    }

    /**
     * Compara dos números de forma segura
     */
    public static int safeCompare(Integer a, Integer b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

    /**
     * Compara dos BigDecimal de forma segura
     */
    public static int safeCompare(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }
}
