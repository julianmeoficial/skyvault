package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class BadRequestException extends BaseException {

    private static final String TYPE = "https://api.skyvault.com/problems/bad-request";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    private static final String TITLE = "Bad Request";

    public BadRequestException(String detail) {
        super(TYPE, STATUS, TITLE, detail);
    }

    public BadRequestException(String detail, URI instance) {
        super(TYPE, STATUS, TITLE, detail, instance);
    }

    public BadRequestException(String detail, String field, Object invalidValue) {
        super(TYPE, STATUS, TITLE, detail);
        addProperty("field", field);
        addProperty("invalidValue", invalidValue);
    }

    public BadRequestException(String detail, Map<String, Object> validationErrors) {
        super(TYPE, STATUS, TITLE, detail);
        addProperty("validationErrors", validationErrors);
    }

    // ✅ FACTORY METHODS CORREGIDOS - RETORNAN BadRequestException
    public static BadRequestException invalidParameter(String parameterName, Object value, String reason) {
        BadRequestException exception = new BadRequestException(
                String.format("Invalid parameter '%s' with value '%s': %s", parameterName, value, reason),
                parameterName, value
        );
        exception.addProperty("reason", reason);
        return exception;
    }

    public static BadRequestException invalidRange(String fieldName, Object min, Object max) {
        BadRequestException exception = new BadRequestException(
                String.format("Invalid range for '%s': min (%s) cannot be greater than max (%s)", fieldName, min, max)
        );
        exception.addProperty("field", fieldName);
        exception.addProperty("minValue", min);
        exception.addProperty("maxValue", max);
        return exception;
    }

    public static BadRequestException invalidPageSize(Integer size, Integer maxAllowed) {
        BadRequestException exception = new BadRequestException(
                String.format("Page size %d exceeds maximum allowed size of %d", size, maxAllowed)
        );
        exception.addProperty("requestedSize", size);
        exception.addProperty("maxAllowedSize", maxAllowed);
        return exception;
    }

    public static BadRequestException invalidSortField(String field, List<String> allowedFields) {
        BadRequestException exception = new BadRequestException(
                String.format("Invalid sort field '%s'. Allowed fields: %s", field, allowedFields)
        );
        exception.addProperty("invalidField", field);
        exception.addProperty("allowedFields", allowedFields);
        return exception;
    }

    public static BadRequestException emptyComparisonList() {
        return new BadRequestException("Comparison requires at least one aircraft model ID");
    }

    public static BadRequestException tooManyComparisonItems(Integer count, Integer maxAllowed) {
        BadRequestException exception = new BadRequestException(
                String.format("Cannot compare %d items. Maximum allowed: %d", count, maxAllowed)
        );
        exception.addProperty("requestedCount", count);
        exception.addProperty("maxAllowed", maxAllowed);
        return exception;
    }

    public static BadRequestException duplicatedComparisonIds(List<Long> duplicates) {
        BadRequestException exception = new BadRequestException(
                "Duplicate aircraft model IDs found in comparison request"
        );
        exception.addProperty("duplicatedIds", duplicates);
        return exception;
    }
}
