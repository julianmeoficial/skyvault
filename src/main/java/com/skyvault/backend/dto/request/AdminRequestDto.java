package com.skyvault.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Map;

@Schema(description = "DTO para operaciones administrativas")
public class AdminRequestDto {

    @NotBlank(message = "Action is required")
    @Pattern(regexp = "^(HEALTH_CHECK|RESTART_COMPONENT|UPDATE_CONFIG|BACKUP_DATA|RESTORE_DATA|CLEAR_LOGS|UPDATE_STATS|SYNC_DATA)$",
            message = "Invalid admin action")
    @Schema(description = "Acción administrativa a realizar", example = "HEALTH_CHECK",
            allowableValues = {"HEALTH_CHECK", "RESTART_COMPONENT", "UPDATE_CONFIG", "BACKUP_DATA",
                    "RESTORE_DATA", "CLEAR_LOGS", "UPDATE_STATS", "SYNC_DATA"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String action;

    @Size(max = 100, message = "Component name cannot exceed 100 characters")
    @Schema(description = "Componente específico", example = "AircraftService")
    private String component;

    @Schema(description = "Parámetros para la acción")
    private Map<String, Object> parameters;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Descripción de la acción")
    private String description;

    @Schema(description = "Ejecutar en modo dry-run (simulación)", example = "false")
    private Boolean dryRun = false;

    @Schema(description = "Forzar acción aunque haya advertencias", example = "false")
    private Boolean force = false;

    @Schema(description = "Ejecutar de forma asíncrona", example = "true")
    private Boolean async = true;

    @Size(max = 100, message = "User ID cannot exceed 100 characters")
    @Schema(description = "Usuario que ejecuta la acción", example = "admin")
    private String userId;

    @Schema(description = "IP desde donde se ejecuta la acción")
    private String sourceIp;

    @Schema(description = "Notificar resultado", example = "true")
    private Boolean notifyResult = true;

    @Schema(description = "Nivel de logging para la operación", example = "INFO",
            allowableValues = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR"})
    private String logLevel = "INFO";

    @Schema(description = "Timeout en segundos", example = "300")
    private Integer timeoutSeconds = 300;

    @Schema(description = "Email para notificaciones")
    private String notificationEmail;

    @Schema(description = "Backup antes de ejecutar la acción", example = "false")
    private Boolean createBackup = false;

    // Constructors
    public AdminRequestDto() {}

    public AdminRequestDto(String action) {
        this.action = action;
    }

    public AdminRequestDto(String action, String component) {
        this.action = action;
        this.component = component;
    }

    // Getters and Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Boolean getNotifyResult() {
        return notifyResult;
    }

    public void setNotifyResult(Boolean notifyResult) {
        this.notifyResult = notifyResult;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public Boolean getCreateBackup() {
        return createBackup;
    }

    public void setCreateBackup(Boolean createBackup) {
        this.createBackup = createBackup;
    }
}
