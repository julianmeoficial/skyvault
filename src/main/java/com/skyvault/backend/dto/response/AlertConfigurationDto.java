package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Configuración de alertas")
public class AlertConfigurationDto {

    @Schema(description = "ID de la configuración", example = "alert-config-1")
    private String id;

    @Schema(description = "Nombre de la configuración", example = "Cache Performance Alert")
    private String name;

    @Schema(description = "Descripción")
    private String description;

    @Schema(description = "Configuración está habilitada", example = "true")
    private Boolean enabled;

    @Schema(description = "Tipo de alerta", example = "PERFORMANCE", allowableValues = {"PERFORMANCE", "ERROR", "SECURITY", "SYSTEM", "CUSTOM"})
    private String alertType;

    @Schema(description = "Severidad mínima para disparar", example = "HIGH")
    private String minSeverity;

    @Schema(description = "Componentes a monitorear")
    private List<String> monitoredComponents;

    @Schema(description = "Operaciones a monitorear")
    private List<String> monitoredOperations;

    @Schema(description = "Condiciones para disparar la alerta")
    private Map<String, Object> triggerConditions;

    @Schema(description = "Intervalo de verificación en segundos", example = "300")
    private Integer checkIntervalSeconds;

    @Schema(description = "Período de cooldown en segundos", example = "900")
    private Integer cooldownSeconds;

    @Schema(description = "Máximo número de alertas por hora", example = "10")
    private Integer maxAlertsPerHour;

    // Configuración de notificaciones
    @Schema(description = "Notificación por email habilitada", example = "true")
    private Boolean emailEnabled;

    @Schema(description = "Direcciones de email")
    private List<String> emailRecipients;

    @Schema(description = "Plantilla de email", example = "performance-alert")
    private String emailTemplate;

    @Schema(description = "Webhook habilitado", example = "false")
    private Boolean webhookEnabled;

    @Schema(description = "URL del webhook")
    private String webhookUrl;

    @Schema(description = "Headers adicionales para webhook")
    private Map<String, String> webhookHeaders;

    @Schema(description = "Método HTTP para webhook", example = "POST")
    private String webhookMethod;

    @Schema(description = "Timeout del webhook en segundos", example = "30")
    private Integer webhookTimeoutSeconds;

    @Schema(description = "Reintentos del webhook", example = "3")
    private Integer webhookRetries;

    // Configuración de Slack
    @Schema(description = "Notificación por Slack habilitada", example = "false")
    private Boolean slackEnabled;

    @Schema(description = "Canal de Slack", example = "#alerts")
    private String slackChannel;

    @Schema(description = "Token de Slack")
    private String slackToken;

    @Schema(description = "Plantilla de mensaje de Slack")
    private String slackTemplate;

    // Configuración de SMS
    @Schema(description = "Notificación por SMS habilitada", example = "false")
    private Boolean smsEnabled;

    @Schema(description = "Números de teléfono para SMS")
    private List<String> smsRecipients;

    @Schema(description = "Plantilla de SMS")
    private String smsTemplate;

    // Filtros avanzados
    @Schema(description = "Filtros por tags")
    private Map<String, String> tagFilters;

    @Schema(description = "Expresión regular para filtrar mensajes")
    private String messageFilter;

    @Schema(description = "Excluir errores conocidos", example = "true")
    private Boolean excludeKnownErrors;

    @Schema(description = "Lista de errores conocidos a excluir")
    private List<String> knownErrorCodes;

    // Configuración de escalamiento
    @Schema(description = "Escalamiento habilitado", example = "false")
    private Boolean escalationEnabled;

    @Schema(description = "Tiempo para escalar en segundos", example = "1800")
    private Integer escalationTimeSeconds;

    @Schema(description = "Destinatarios para escalamiento")
    private List<String> escalationRecipients;

    // Metadata
    @Schema(description = "Usuario que creó la configuración")
    private String createdBy;

    @Schema(description = "Usuario que modificó la configuración")
    private String modifiedBy;

    @Schema(description = "Fecha de creación")
    private String createdAt;

    @Schema(description = "Fecha de modificación")
    private String modifiedAt;

    // Constructors
    public AlertConfigurationDto() {
        this.enabled = true;
        this.emailEnabled = false;
        this.webhookEnabled = false;
        this.slackEnabled = false;
        this.smsEnabled = false;
        this.escalationEnabled = false;
        this.excludeKnownErrors = true;
    }

    // Getters and Setters (todos los campos)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getMinSeverity() { return minSeverity; }
    public void setMinSeverity(String minSeverity) { this.minSeverity = minSeverity; }

    public List<String> getMonitoredComponents() { return monitoredComponents; }
    public void setMonitoredComponents(List<String> monitoredComponents) { this.monitoredComponents = monitoredComponents; }

    public List<String> getMonitoredOperations() { return monitoredOperations; }
    public void setMonitoredOperations(List<String> monitoredOperations) { this.monitoredOperations = monitoredOperations; }

    public Map<String, Object> getTriggerConditions() { return triggerConditions; }
    public void setTriggerConditions(Map<String, Object> triggerConditions) { this.triggerConditions = triggerConditions; }

    public Integer getCheckIntervalSeconds() { return checkIntervalSeconds; }
    public void setCheckIntervalSeconds(Integer checkIntervalSeconds) { this.checkIntervalSeconds = checkIntervalSeconds; }

    public Integer getCooldownSeconds() { return cooldownSeconds; }
    public void setCooldownSeconds(Integer cooldownSeconds) { this.cooldownSeconds = cooldownSeconds; }

    public Integer getMaxAlertsPerHour() { return maxAlertsPerHour; }
    public void setMaxAlertsPerHour(Integer maxAlertsPerHour) { this.maxAlertsPerHour = maxAlertsPerHour; }

    public Boolean getEmailEnabled() { return emailEnabled; }
    public void setEmailEnabled(Boolean emailEnabled) { this.emailEnabled = emailEnabled; }

    public List<String> getEmailRecipients() { return emailRecipients; }
    public void setEmailRecipients(List<String> emailRecipients) { this.emailRecipients = emailRecipients; }

    public String getEmailTemplate() { return emailTemplate; }
    public void setEmailTemplate(String emailTemplate) { this.emailTemplate = emailTemplate; }

    public Boolean getWebhookEnabled() { return webhookEnabled; }
    public void setWebhookEnabled(Boolean webhookEnabled) { this.webhookEnabled = webhookEnabled; }

    public String getWebhookUrl() { return webhookUrl; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }

    public Map<String, String> getWebhookHeaders() { return webhookHeaders; }
    public void setWebhookHeaders(Map<String, String> webhookHeaders) { this.webhookHeaders = webhookHeaders; }

    public String getWebhookMethod() { return webhookMethod; }
    public void setWebhookMethod(String webhookMethod) { this.webhookMethod = webhookMethod; }

    public Integer getWebhookTimeoutSeconds() { return webhookTimeoutSeconds; }
    public void setWebhookTimeoutSeconds(Integer webhookTimeoutSeconds) { this.webhookTimeoutSeconds = webhookTimeoutSeconds; }

    public Integer getWebhookRetries() { return webhookRetries; }
    public void setWebhookRetries(Integer webhookRetries) { this.webhookRetries = webhookRetries; }

    public Boolean getSlackEnabled() { return slackEnabled; }
    public void setSlackEnabled(Boolean slackEnabled) { this.slackEnabled = slackEnabled; }

    public String getSlackChannel() { return slackChannel; }
    public void setSlackChannel(String slackChannel) { this.slackChannel = slackChannel; }

    public String getSlackToken() { return slackToken; }
    public void setSlackToken(String slackToken) { this.slackToken = slackToken; }

    public String getSlackTemplate() { return slackTemplate; }
    public void setSlackTemplate(String slackTemplate) { this.slackTemplate = slackTemplate; }

    public Boolean getSmsEnabled() { return smsEnabled; }
    public void setSmsEnabled(Boolean smsEnabled) { this.smsEnabled = smsEnabled; }

    public List<String> getSmsRecipients() { return smsRecipients; }
    public void setSmsRecipients(List<String> smsRecipients) { this.smsRecipients = smsRecipients; }

    public String getSmsTemplate() { return smsTemplate; }
    public void setSmsTemplate(String smsTemplate) { this.smsTemplate = smsTemplate; }

    public Map<String, String> getTagFilters() { return tagFilters; }
    public void setTagFilters(Map<String, String> tagFilters) { this.tagFilters = tagFilters; }

    public String getMessageFilter() { return messageFilter; }
    public void setMessageFilter(String messageFilter) { this.messageFilter = messageFilter; }

    public Boolean getExcludeKnownErrors() { return excludeKnownErrors; }
    public void setExcludeKnownErrors(Boolean excludeKnownErrors) { this.excludeKnownErrors = excludeKnownErrors; }

    public List<String> getKnownErrorCodes() { return knownErrorCodes; }
    public void setKnownErrorCodes(List<String> knownErrorCodes) { this.knownErrorCodes = knownErrorCodes; }

    public Boolean getEscalationEnabled() { return escalationEnabled; }
    public void setEscalationEnabled(Boolean escalationEnabled) { this.escalationEnabled = escalationEnabled; }

    public Integer getEscalationTimeSeconds() { return escalationTimeSeconds; }
    public void setEscalationTimeSeconds(Integer escalationTimeSeconds) { this.escalationTimeSeconds = escalationTimeSeconds; }

    public List<String> getEscalationRecipients() { return escalationRecipients; }
    public void setEscalationRecipients(List<String> escalationRecipients) { this.escalationRecipients = escalationRecipients; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(String modifiedAt) { this.modifiedAt = modifiedAt; }

}
