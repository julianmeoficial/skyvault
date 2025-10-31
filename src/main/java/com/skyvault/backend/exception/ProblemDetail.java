package com.skyvault.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("instance")
    private URI instance;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", timezone = "UTC")
    private Instant timestamp;

    @JsonProperty("correlationId")
    private String correlationId;

    // Propiedades adicionales dinámicas
    private Map<String, Object> properties;

    // Constructors
    public ProblemDetail() {}

    public ProblemDetail(String type, String title, Integer status, String detail) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.timestamp = Instant.now();
    }

    // Factory method desde BaseException
    public static ProblemDetail from(BaseException exception) {
        ProblemDetail problem = new ProblemDetail();
        problem.type = exception.getType();
        problem.title = exception.getTitle();
        problem.status = exception.getStatus().value();
        problem.detail = exception.getDetail();
        problem.instance = exception.getInstance();
        problem.timestamp = exception.getTimestamp();
        problem.properties = exception.getProperties();
        return problem;
    }

    // Factory method para excepciones no manejadas
    public static ProblemDetail from(Exception exception, int statusCode, String title) {
        ProblemDetail problem = new ProblemDetail();
        problem.type = "https://api.skyvault.com/problems/generic-error";
        problem.title = title;
        problem.status = statusCode;
        problem.detail = exception.getMessage();
        problem.timestamp = Instant.now();
        return problem;
    }

    // Getters y Setters completos
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public URI getInstance() {
        return instance;
    }

    public void setInstance(URI instance) {
        this.instance = instance;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    // Helper para agregar propiedades dinámicas
    @JsonProperty
    public Map<String, Object> getAdditionalProperties() {
        return properties;
    }
}
