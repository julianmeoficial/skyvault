package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseException extends RuntimeException {

    private final String type;
    private final HttpStatus status;
    private final String detail;
    private final URI instance;
    private final Map<String, Object> properties;
    private final Instant timestamp;

    protected BaseException(String type, HttpStatus status, String title, String detail, URI instance) {
        super(title);
        this.type = type;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.properties = new HashMap<>();
        this.timestamp = Instant.now();
    }

    protected BaseException(String type, HttpStatus status, String title, String detail) {
        this(type, status, title, detail, null);
    }

    // Getters
    public String getType() {
        return type;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return getMessage();
    }

    public String getDetail() {
        return detail;
    }

    public URI getInstance() {
        return instance;
    }

    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    // Helper methods
    public BaseException addProperty(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    public BaseException addProperties(Map<String, Object> properties) {
        this.properties.putAll(properties);
        return this;
    }
}
