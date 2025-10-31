package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public class ResourceNotFoundException extends BaseException {

    private static final String TYPE = "https://api.skyvault.com/problems/resource-not-found";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String TITLE = "Resource Not Found";

    public ResourceNotFoundException(String detail) {
        super(TYPE, STATUS, TITLE, detail);
    }

    public ResourceNotFoundException(String detail, URI instance) {
        super(TYPE, STATUS, TITLE, detail, instance);
    }

    public ResourceNotFoundException(String resourceType, Object resourceId) {
        super(TYPE, STATUS, TITLE,
                String.format("%s with id '%s' was not found", resourceType, resourceId));
        addProperty("resourceType", resourceType);
        addProperty("resourceId", resourceId);
    }

    public ResourceNotFoundException(String resourceType, String field, Object value) {
        super(TYPE, STATUS, TITLE,
                String.format("%s with %s '%s' was not found", resourceType, field, value));
        addProperty("resourceType", resourceType);
        addProperty("field", field);
        addProperty("value", value);
    }

    // Factory methods específicos del dominio
    public static ResourceNotFoundException aircraftModel(Long id) {
        return new ResourceNotFoundException("Aircraft Model", id);
    }

    public static ResourceNotFoundException manufacturer(Long id) {
        return new ResourceNotFoundException("Manufacturer", id);
    }

    public static ResourceNotFoundException manufacturerByName(String name) {
        return new ResourceNotFoundException("Manufacturer", "name", name);
    }

    public static ResourceNotFoundException family(Long id) {
        return new ResourceNotFoundException("Family", id);
    }

    public static ResourceNotFoundException specifications(Long aircraftModelId) {
        return new ResourceNotFoundException("Specifications", "aircraftModelId", aircraftModelId);
    }

    public static ResourceNotFoundException image(Long id) {
        return new ResourceNotFoundException("Image", id);
    }

    public static ResourceNotFoundException type(Long id) {
        return new ResourceNotFoundException("Type", id);
    }

    public static ResourceNotFoundException productionState(Long id) {
        return new ResourceNotFoundException("Production State", id);
    }

    public static ResourceNotFoundException sizeCategory(Long id) {
        return new ResourceNotFoundException("Size Category", id);
    }
}