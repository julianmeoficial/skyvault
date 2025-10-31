package com.skyvault.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Excepciones custom de nuestra aplicación
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleBaseException(BaseException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.from(ex);
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(ex.getStatus()).body(problem);
    }

    // 404 - Not Found (handler no encontrado)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/not-found",
                "Not Found",
                404,
                String.format("No handler found for %s %s", ex.getHttpMethod(), ex.getRequestURL())
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    // 400 - Bad Request (parámetros faltantes)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ProblemDetail> handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/missing-parameter",
                "Missing Parameter",
                400,
                String.format("Required parameter '%s' is missing", ex.getParameterName())
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("parameterName", ex.getParameterName());
        properties.put("parameterType", ex.getParameterType());
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    // 400 - Bad Request (tipo de parámetro incorrecto)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/type-mismatch",
                "Type Mismatch",
                400,
                String.format("Parameter '%s' with value '%s' could not be converted to type '%s'",
                        ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName())
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("parameterName", ex.getName());
        properties.put("invalidValue", ex.getValue());
        properties.put("requiredType", ex.getRequiredType().getSimpleName());
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    // 400 - Bad Request (JSON malformado)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/malformed-json",
                "Malformed JSON",
                400,
                "Request body contains malformed JSON or invalid data format"
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    // 405 - Method Not Allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/method-not-allowed",
                "Method Not Allowed",
                405,
                String.format("Method '%s' is not supported for this endpoint. Supported methods: %s",
                        ex.getMethod(), ex.getSupportedHttpMethods())
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("method", ex.getMethod());
        properties.put("supportedMethods", ex.getSupportedHttpMethods());
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(problem);
    }

    // 415 - Unsupported Media Type
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/unsupported-media-type",
                "Unsupported Media Type",
                415,
                String.format("Media type '%s' is not supported. Supported types: %s",
                        ex.getContentType(), ex.getSupportedMediaTypes())
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("contentType", ex.getContentType());
        properties.put("supportedTypes", ex.getSupportedMediaTypes());
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problem);
    }

    // 422 - Validation errors (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/validation-failed",
                "Validation Failed",
                422,
                "One or more fields have validation errors"
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("fieldErrors", fieldErrors);
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    // 422 - Validation errors (BindException)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException ex, HttpServletRequest request) {
        Map<String, List<String>> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/binding-failed",
                "Binding Failed",
                422,
                "One or more request parameters have binding errors"
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("fieldErrors", fieldErrors);
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    // 422 - Constraint violations (Bean Validation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, List<String>> fieldErrors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));

        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/constraint-violation",
                "Constraint Violation",
                422,
                "One or more constraints have been violated"
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        Map<String, Object> properties = new HashMap<>();
        properties.put("fieldErrors", fieldErrors);
        problem.setProperties(properties);

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    // 409 - Data Integrity Violation (duplicados, constraints)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String detail = "Data integrity constraint violation occurred";

        // Intentar extraer información más específica
        String message = ex.getMostSpecificCause().getMessage();
        if (message != null) {
            if (message.contains("duplicate key") || message.contains("unique constraint")) {
                detail = "A record with the same unique field already exists";
            } else if (message.contains("foreign key")) {
                detail = "Referenced record does not exist or cannot be deleted due to existing references";
            }
        }

        ProblemDetail problem = new ProblemDetail(
                "https://api.skyvault.com/problems/data-integrity-violation",
                "Data Integrity Violation",
                409,
                detail
        );
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    // 500 - Internal Server Error (catch-all)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.from(ex, 500, "Internal Server Error");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setCorrelationId(getOrCreateCorrelationId());

        // No exponer detalles internos en producción
        if (isProductionProfile()) {
            problem.setDetail("An unexpected error occurred. Please try again later.");
        }

        logException(ex, problem.getCorrelationId());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    // Helper methods
    private String getOrCreateCorrelationId() {
        String correlationId = MDC.get("correlationId");
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
            MDC.put("correlationId", correlationId);
        }
        return correlationId;
    }

    private void logException(Exception ex, String correlationId) {
        if (ex instanceof BaseException baseEx) {
            if (baseEx.getStatus().is4xxClientError()) {
                logger.warn("Client error [{}]: {} - {}", correlationId, baseEx.getTitle(), baseEx.getDetail());
            } else {
                logger.error("Server error [{}]: {} - {}", correlationId, baseEx.getTitle(), baseEx.getDetail(), ex);
            }
        } else {
            logger.error("Unhandled exception [{}]: {}", correlationId, ex.getMessage(), ex);
        }
    }

    private boolean isProductionProfile() {
        // Implementar lógica para detectar profile de producción
        // Por ejemplo, usando @Value o Environment
        String activeProfile = System.getProperty("spring.profiles.active");
        return "prod".equals(activeProfile) || "production".equals(activeProfile);
    }
}
