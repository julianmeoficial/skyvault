package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationException extends BaseException {

    private static final String TYPE = "https://api.skyvault.com/problems/validation-failed";
    private static final HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;
    private static final String TITLE = "Validation Failed";

    public ValidationException(String detail) {
        super(TYPE, STATUS, TITLE, detail);
    }

    public ValidationException(String detail, URI instance) {
        super(TYPE, STATUS, TITLE, detail, instance);
    }

    public ValidationException(String detail, Map<String, List<String>> fieldErrors) {
        super(TYPE, STATUS, TITLE, detail);
        addProperty("fieldErrors", fieldErrors);
    }

    public ValidationException(Map<String, List<String>> fieldErrors) {
        super(TYPE, STATUS, TITLE, "One or more fields have validation errors");
        addProperty("fieldErrors", fieldErrors);
    }

    // Factory methods
    public static ValidationException singleField(String field, String error) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(field, List.of(error));
        return new ValidationException(errors);
    }

    public static ValidationException singleField(String field, List<String> errors) {
        Map<String, List<String>> fieldErrors = new HashMap<>();
        fieldErrors.put(field, errors);
        return new ValidationException(fieldErrors);
    }

    public static ValidationException multipleFields(Map<String, List<String>> fieldErrors) {
        return new ValidationException(fieldErrors);
    }

    // Métodos específicos del dominio
    public static ValidationException invalidPassengerRange(Integer min, Integer max) {
        return singleField("passengers",
                String.format("Invalid passenger range: min (%d) cannot be greater than max (%d)", min, max));
    }

    public static ValidationException invalidRangeKm(Integer min, Integer max) {
        return singleField("rangeKm",
                String.format("Invalid range: min (%d) cannot be greater than max (%d)", min, max));
    }

    public static ValidationException invalidYear(Integer year) {
        return singleField("year",
                String.format("Invalid year %d. Must be between 1900 and current year", year));
    }

    public static ValidationException requiredField(String fieldName) {
        return singleField(fieldName, String.format("Field '%s' is required", fieldName));
    }

    public static ValidationException invalidFormat(String fieldName, String expectedFormat) {
        return singleField(fieldName,
                String.format("Field '%s' has invalid format. Expected: %s", fieldName, expectedFormat));
    }
}
