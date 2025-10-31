package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public class InternalServerErrorException extends BaseException {

    private static final String TYPE = "https://api.skyvault.com/problems/internal-server-error";
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String TITLE = "Internal Server Error";

    public InternalServerErrorException(String detail) {
        super(TYPE, STATUS, TITLE, detail);
    }

    public InternalServerErrorException(String detail, URI instance) {
        super(TYPE, STATUS, TITLE, detail, instance);
    }

    public InternalServerErrorException(String detail, Throwable cause) {
        super(TYPE, STATUS, TITLE, detail);
        addProperty("errorClass", cause.getClass().getSimpleName());
        addProperty("errorMessage", cause.getMessage());
    }

    // ✅ FACTORY METHODS CORREGIDOS - RETORNAN InternalServerErrorException
    public static InternalServerErrorException databaseConnectionError() {
        return new InternalServerErrorException("Database connection error occurred");
    }

    public static InternalServerErrorException mappingError(String sourceType, String targetType) {
        InternalServerErrorException exception = new InternalServerErrorException(
                String.format("Error mapping from %s to %s", sourceType, targetType)
        );
        exception.addProperty("sourceType", sourceType);
        exception.addProperty("targetType", targetType);
        return exception;
    }

    public static InternalServerErrorException cacheError(String operation) {
        InternalServerErrorException exception = new InternalServerErrorException(
                String.format("Cache operation '%s' failed", operation)
        );
        exception.addProperty("operation", operation);
        return exception;
    }

    public static InternalServerErrorException unexpectedError(Throwable cause) {
        return new InternalServerErrorException("An unexpected error occurred", cause);
    }
}
