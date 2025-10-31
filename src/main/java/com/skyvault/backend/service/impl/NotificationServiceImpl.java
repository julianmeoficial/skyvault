package com.skyvault.backend.service.impl;

import com.skyvault.backend.dto.response.AlertConfigurationDto;
import com.skyvault.backend.dto.response.SystemNotificationDto;
import com.skyvault.backend.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    // Almacenamiento en memoria para notificaciones (en producción sería una base de datos)
    private final Queue<SystemNotificationDto> notificationQueue = new ConcurrentLinkedQueue<>();
    private final Map<String, AlertConfigurationDto> alertConfigurations = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastAlertTimes = new ConcurrentHashMap<>();

    // Configuración por defecto
    private static final int MAX_NOTIFICATIONS = 10000;
    private static final int DEFAULT_COOLDOWN_MINUTES = 15;

    public NotificationServiceImpl() {
        initializeDefaultAlerts();
    }

    @Override
    @Transactional
    public void notifySystemError(String component, String message, Throwable throwable) {
        logger.error("System error in component '{}': {}", component, message, throwable);

        SystemNotificationDto notification = createNotification(
                "ERROR",
                "System Error",
                String.format("Error in component '%s': %s", component, message),
                component
        );

        notification.setSeverity("HIGH");
        notification.setCategory("SYSTEM");
        notification.setErrorCode("SYS_ERROR_001");

        if (throwable != null) {
            notification.setStackTrace(getStackTraceString(throwable));
        }

        addNotification(notification);

        // Verificar si se debe enviar alerta
        checkAndSendAlert("system_error", notification);
    }

    @Override
    @Transactional
    public void notifyPerformanceIssue(String component, String metric, double value, double threshold) {
        logger.warn("Performance issue in component '{}': {} = {} (threshold: {})",
                component, metric, value, threshold);

        SystemNotificationDto notification = createNotification(
                "WARNING",
                "Performance Issue",
                String.format("Performance threshold exceeded in '%s': %s = %.2f (threshold: %.2f)",
                        component, metric, value, threshold),
                component
        );

        notification.setSeverity("MEDIUM");
        notification.setCategory("PERFORMANCE");
        notification.setErrorCode("PERF_001");

        Map<String, Object> details = new HashMap<>();
        details.put("metric", metric);
        details.put("actualValue", value);
        details.put("threshold", threshold);
        details.put("exceedsBy", value - threshold);
        details.put("percentageOver", ((value - threshold) / threshold) * 100);
        notification.setDetails(details);

        addNotification(notification);

        checkAndSendAlert("performance_issue", notification);
    }

    @Override
    @Transactional
    public void notifyCacheEvent(String cacheName, String eventType, Map<String, Object> details) {
        logger.info("Cache event in '{}': {} - {}", cacheName, eventType, details);

        String severity = determineCacheEventSeverity(eventType);
        String title = String.format("Cache Event: %s", eventType);
        String message = String.format("Cache '%s' triggered event: %s", cacheName, eventType);

        SystemNotificationDto notification = createNotification(
                "INFO",
                title,
                message,
                "CacheService"
        );

        notification.setSeverity(severity);
        notification.setCategory("CACHE");
        notification.setErrorCode("CACHE_" + eventType.toUpperCase());
        notification.setDetails(details);

        // Agregar información específica del cache
        if (details != null) {
            details.put("cacheName", cacheName);
            details.put("eventTime", LocalDateTime.now().toString());
        }

        addNotification(notification);

        // Solo alertar en eventos críticos de cache
        if ("CRITICAL".equals(severity)) {
            checkAndSendAlert("cache_critical", notification);
        }
    }

    @Override
    @Transactional
    public void notifySecurityEvent(String eventType, String userId, String sourceIp, Map<String, Object> details) {
        logger.warn("Security event '{}' from user '{}' at IP '{}': {}", eventType, userId, sourceIp, details);

        SystemNotificationDto notification = createNotification(
                "WARNING",
                "Security Event",
                String.format("Security event detected: %s", eventType),
                "SecurityService"
        );

        notification.setSeverity("HIGH");
        notification.setCategory("SECURITY");
        notification.setErrorCode("SEC_" + eventType.toUpperCase());
        notification.setUserId(userId);
        notification.setSourceIp(sourceIp);
        notification.setDetails(details);

        // Configurar recomendación de acción
        notification.setRecommendedAction(getSecurityRecommendation(eventType));

        addNotification(notification);

        // Siempre alertar en eventos de seguridad
        checkAndSendAlert("security_event", notification);
    }

    @Override
    @Transactional
    public void notifyDataIntegrityIssue(String dataType, String issue, Map<String, Object> affectedRecords) {
        logger.error("Data integrity issue with '{}': {} - Affected: {}", dataType, issue, affectedRecords);

        SystemNotificationDto notification = createNotification(
                "ERROR",
                "Data Integrity Issue",
                String.format("Data integrity problem detected in %s: %s", dataType, issue),
                "DataService"
        );

        notification.setSeverity("CRITICAL");
        notification.setCategory("DATA");
        notification.setErrorCode("DATA_INTEGRITY_001");
        notification.setDetails(affectedRecords);

        notification.setRecommendedAction("Review and correct data integrity issues immediately");

        addNotification(notification);

        checkAndSendAlert("data_integrity", notification);
    }

    @Override
    public List<SystemNotificationDto> getSystemNotifications(LocalDateTime from, LocalDateTime to) {
        return notificationQueue.stream()
                .filter(notification ->
                        notification.getTimestamp().isAfter(from) &&
                                notification.getTimestamp().isBefore(to))
                .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp())) // Más recientes primero
                .collect(Collectors.toList());
    }

    @Override
    public List<SystemNotificationDto> getUnreadNotifications() {
        return notificationQueue.stream()
                .filter(notification -> !notification.getRead())
                .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SystemNotificationDto> getNotificationsByType(String type, int limit) {
        return notificationQueue.stream()
                .filter(notification -> type.equalsIgnoreCase(notification.getType()))
                .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<SystemNotificationDto> getNotificationsByCategory(String category, int limit) {
        return notificationQueue.stream()
                .filter(notification -> category.equalsIgnoreCase(notification.getCategory()))
                .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<SystemNotificationDto> getCriticalNotifications() {
        return notificationQueue.stream()
                .filter(notification -> "CRITICAL".equalsIgnoreCase(notification.getSeverity()))
                .filter(notification -> !notification.getRead())
                .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markNotificationAsRead(String notificationId) {
        notificationQueue.stream()
                .filter(notification -> notificationId.equals(notification.getId()))
                .findFirst()
                .ifPresent(notification -> {
                    notification.setRead(true);
                    notification.setReadAt(LocalDateTime.now());
                    logger.debug("Marked notification as read: {}", notificationId);
                });
    }

    @Override
    @Transactional
    public void markAllNotificationsAsRead() {
        LocalDateTime now = LocalDateTime.now();
        notificationQueue.forEach(notification -> {
            if (!notification.getRead()) {
                notification.setRead(true);
                notification.setReadAt(now);
            }
        });

        logger.info("Marked all notifications as read");
    }

    @Override
    @Transactional
    public void deleteNotification(String notificationId) {
        boolean removed = notificationQueue.removeIf(notification ->
                notificationId.equals(notification.getId()));

        if (removed) {
            logger.info("Deleted notification: {}", notificationId);
        } else {
            logger.warn("Notification not found for deletion: {}", notificationId);
        }
    }

    @Override
    @Transactional
    public void cleanOldNotifications(int daysToKeep) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(daysToKeep);

        long removedCount = notificationQueue.stream()
                .filter(notification -> notification.getTimestamp().isBefore(cutoff))
                .count();

        notificationQueue.removeIf(notification -> notification.getTimestamp().isBefore(cutoff));

        logger.info("Cleaned up {} old notifications(older than {} days)", removedCount);
    }

    @Override
    public void configureAlert(String alertId, AlertConfigurationDto configuration) {
        alertConfigurations.put(alertId, configuration);
        logger.info("Configured alert: {}", alertId);
    }

    @Override
    public AlertConfigurationDto getAlertConfiguration(String alertId) {
        return alertConfigurations.get(alertId);
    }

    @Override
    public void removeAlertConfiguration(String alertId) {
        AlertConfigurationDto removed = alertConfigurations.remove(alertId);
        if (removed != null) {
            logger.info("Removed alert configuration: {}", alertId);
        }
    }

    @Override
    public List<AlertConfigurationDto> getAllAlertConfigurations() {
        return new ArrayList<>(alertConfigurations.values());
    }

    @Override
    public long getNotificationCount() {
        return notificationQueue.size();
    }

    @Override
    public long getUnreadNotificationCount() {
        return notificationQueue.stream()
                .filter(notification -> !Boolean.TRUE.equals(notification.getRead()))
                .count();
    }

    @Override
    public Map<String, Long> getNotificationCountByType() {
        return notificationQueue.stream()
                .collect(Collectors.groupingBy(
                        SystemNotificationDto::getType,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> getNotificationCountByCategory() {
        return notificationQueue.stream()
                .collect(Collectors.groupingBy(
                        notification -> notification.getCategory() != null ? notification.getCategory() : "UNKNOWN",
                        Collectors.counting()
                ));
    }

    // Métodos privados auxiliares

    private void addNotification(SystemNotificationDto notification) {
        // Limpiar notificaciones antiguas si se excede el límite
        while (notificationQueue.size() >= MAX_NOTIFICATIONS) {
            SystemNotificationDto oldest = notificationQueue.poll();
            if (oldest != null) {
                logger.debug("Removed oldest notification: {}", oldest.getId());
            }
        }

        notificationQueue.offer(notification);
        logger.debug("Added notification: {} - {}", notification.getType(), notification.getTitle());
    }

    private SystemNotificationDto createNotification(String type, String title, String message, String component) {
        SystemNotificationDto notification = new SystemNotificationDto();
        notification.setId(UUID.randomUUID().toString());
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setComponent(component);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        notification.setProcessed(false);
        notification.setDismissible(true);
        notification.setTtlMinutes(1440); // 24 horas por defecto

        return notification;
    }

    private void checkAndSendAlert(String alertType, SystemNotificationDto notification) {
        AlertConfigurationDto config = alertConfigurations.get(alertType);
        if (config == null || !config.getEnabled()) {
            return;
        }

        // Verificar cooldown
        String cooldownKey = alertType + "_" + notification.getComponent();
        LocalDateTime lastAlert = lastAlertTimes.get(cooldownKey);
        if (lastAlert != null && lastAlert.plusMinutes(DEFAULT_COOLDOWN_MINUTES).isAfter(LocalDateTime.now())) {
            logger.debug("Alert '{}' is in cooldown period", alertType);
            return;
        }

        // Verificar severidad mínima
        if (!meetsMinimumSeverity(notification.getSeverity(), config.getMinSeverity())) {
            return;
        }

        // Simular envío de alerta (en producción integraría con servicios reales)
        logger.info("ALERT TRIGGERED: {} - {} - {}", alertType, notification.getTitle(), notification.getMessage());

        if (config.getEmailEnabled()) {
            sendEmailAlert(config, notification);
        }

        if (config.getWebhookEnabled()) {
            sendWebhookAlert(config, notification);
        }

        if (config.getSlackEnabled()) {
            sendSlackAlert(config, notification);
        }

        // Registrar tiempo de la última alerta
        lastAlertTimes.put(cooldownKey, LocalDateTime.now());
    }

    private void initializeDefaultAlerts() {
        // Alerta de errores del sistema
        AlertConfigurationDto systemError = new AlertConfigurationDto();
        systemError.setId("system_error");
        systemError.setName("System Error Alert");
        systemError.setEnabled(true);
        systemError.setAlertType("ERROR");
        systemError.setMinSeverity("HIGH");
        systemError.setEmailEnabled(true);
        alertConfigurations.put("system_error", systemError);

        // Alerta de problemas de rendimiento
        AlertConfigurationDto performance = new AlertConfigurationDto();
        performance.setId("performance_issue");
        performance.setName("Performance Issue Alert");
        performance.setEnabled(true);
        performance.setAlertType("PERFORMANCE");
        performance.setMinSeverity("MEDIUM");
        performance.setEmailEnabled(false);
        alertConfigurations.put("performance_issue", performance);

        // Alerta de eventos de seguridad
        AlertConfigurationDto security = new AlertConfigurationDto();
        security.setId("security_event");
        security.setName("Security Event Alert");
        security.setEnabled(true);
        security.setAlertType("SECURITY");
        security.setMinSeverity("HIGH");
        security.setEmailEnabled(true);
        security.setWebhookEnabled(true);
        alertConfigurations.put("security_event", security);

        // Alerta de integridad de datos
        AlertConfigurationDto dataIntegrity = new AlertConfigurationDto();
        dataIntegrity.setId("data_integrity");
        dataIntegrity.setName("Data Integrity Alert");
        dataIntegrity.setEnabled(true);
        dataIntegrity.setAlertType("SYSTEM");
        dataIntegrity.setMinSeverity("CRITICAL");
        dataIntegrity.setEmailEnabled(true);
        alertConfigurations.put("data_integrity", dataIntegrity);

        logger.info("Initialized {} default alert configurations", alertConfigurations.size());
    }

    private String determineCacheEventSeverity(String eventType) {
        return switch (eventType.toUpperCase()) {
            case "EVICTION", "CLEAR", "MISS" -> "MEDIUM";
            case "ERROR", "FAILURE" -> "CRITICAL";
            case "HIT", "LOAD", "REFRESH" -> "LOW";
            default -> "LOW";
        };
    }

    private String getSecurityRecommendation(String eventType) {
        return switch (eventType.toUpperCase()) {
            case "FAILED_LOGIN" -> "Review user access logs and consider account lockout policies";
            case "UNAUTHORIZED_ACCESS" -> "Investigate access patterns and strengthen authentication";
            case "SUSPICIOUS_ACTIVITY" -> "Monitor user behavior and review security policies";
            case "PRIVILEGE_ESCALATION" -> "Review role assignments and audit user permissions";
            default -> "Review security logs and take appropriate action";
        };
    }

    private boolean meetsMinimumSeverity(String notificationSeverity, String minimumSeverity) {
        if (notificationSeverity == null || minimumSeverity == null) {
            return true;
        }

        Map<String, Integer> severityLevels = Map.of(
                "LOW", 1,
                "MEDIUM", 2,
                "HIGH", 3,
                "CRITICAL", 4
        );

        int notificationLevel = severityLevels.getOrDefault(notificationSeverity.toUpperCase(), 1);
        int minimumLevel = severityLevels.getOrDefault(minimumSeverity.toUpperCase(), 1);

        return notificationLevel >= minimumLevel;
    }

    private void sendEmailAlert(AlertConfigurationDto config, SystemNotificationDto notification) {
        // Simulación - en producción integraría con un servicio de email real
        logger.info("EMAIL ALERT sent for: {} to recipients: {}",
                notification.getTitle(), config.getEmailRecipients());
    }

    private void sendWebhookAlert(AlertConfigurationDto config, SystemNotificationDto notification) {
        // Simulación - en producción haría una llamada HTTP real
        logger.info("WEBHOOK ALERT sent to: {} for: {}",
                config.getWebhookUrl(), notification.getTitle());
    }

    private void sendSlackAlert(AlertConfigurationDto config, SystemNotificationDto notification) {
        // Simulación - en producción usaría la API de Slack
        logger.info("SLACK ALERT sent to channel: {} for: {}",
                config.getSlackChannel(), notification.getTitle());
    }

    private String getStackTraceString(Throwable throwable) {
        java.io.StringWriter sw = new java.io.StringWriter();
        throwable.printStackTrace(new java.io.PrintWriter(sw));
        return sw.toString();
    }
}
