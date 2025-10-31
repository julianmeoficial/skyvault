package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public class ConflictException extends BaseException {

    private static final String TYPE = "https://api.skyvault.com/problems/conflict";
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String TITLE = "Conflict";

    public ConflictException(String detail) {
        super(TYPE, STATUS, TITLE, detail);
    }

    public ConflictException(String detail, URI instance) {
        super(TYPE, STATUS, TITLE, detail, instance);
    }

    public ConflictException(String detail, String field, Object conflictingValue) {
        super(TYPE, STATUS, TITLE, detail);
        addProperty("field", field);
        addProperty("conflictingValue", conflictingValue);
    }

    // ✅ FACTORY METHODS CORREGIDOS - RETORNAN ConflictException
    public static ConflictException duplicatedName(String resourceType, String name) {
        ConflictException exception = new ConflictException(
                String.format("%s with name '%s' already exists", resourceType, name),
                "name", name
        );
        exception.addProperty("resourceType", resourceType);
        return exception;
    }

    public static ConflictException duplicatedManufacturerName(String name) {
        return duplicatedName("Manufacturer", name);
    }

    public static ConflictException duplicatedFamilyName(String familyName, String manufacturerName) {
        ConflictException exception = new ConflictException(
                String.format("Family '%s' already exists for manufacturer '%s'", familyName, manufacturerName)
        );
        exception.addProperty("familyName", familyName);
        exception.addProperty("manufacturerName", manufacturerName);
        return exception;
    }

    public static ConflictException duplicatedAircraftModel(String modelName, String manufacturerName) {
        ConflictException exception = new ConflictException(
                String.format("Aircraft model '%s' already exists for manufacturer '%s'", modelName, manufacturerName)
        );
        exception.addProperty("modelName", modelName);
        exception.addProperty("manufacturerName", manufacturerName);
        return exception;
    }

    public static ConflictException specificationAlreadyExists(Long aircraftModelId) {
        ConflictException exception = new ConflictException(
                String.format("Specifications already exist for aircraft model with id %d", aircraftModelId)
        );
        exception.addProperty("aircraftModelId", aircraftModelId);
        return exception;
    }
}
