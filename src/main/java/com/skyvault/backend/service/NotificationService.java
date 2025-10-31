package com.skyvault.backend.service;

import com.skyvault.backend.dto.response.AlertConfigurationDto;
import com.skyvault.backend.dto.response.SystemNotificationDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Servicio para notificaciones y eventos del sistema.
 */
public interface NotificationService {

    // ========== MÉTODOS PARA NOTIFICACIONES ==========

    /**
     * Notificar error crítico del sistema
     */
    void notifySystemError(String component, String message, Throwable throwable);

    /**
     * Notificar problema de performance
     */
    void notifyPerformanceIssue(String component, String metric, double value, double threshold);

    /**
     * Notificar evento de cache
     */
    void notifyCacheEvent(String cacheName, String eventType, Map<String, Object> details);

    /**
     * Notificar evento de seguridad
     */
    void notifySecurityEvent(String eventType, String userId, String sourceIp, Map<String, Object> details);

    /**
     * Notificar problema de integridad de datos
     */
    void notifyDataIntegrityIssue(String dataType, String issue, Map<String, Object> affectedRecords);

    // ========== MÉTODOS PARA CONSULTAR NOTIFICACIONES ==========

    /**
     * Obtener notificaciones del sistema por período
     */
    List<SystemNotificationDto> getSystemNotifications(LocalDateTime from, LocalDateTime to);

    /**
     * Obtener notificaciones no leídas
     */
    List<SystemNotificationDto> getUnreadNotifications();

    /**
     * Obtener notificaciones por tipo
     */
    List<SystemNotificationDto> getNotificationsByType(String type, int limit);

    /**
     * Obtener notificaciones por categoría
     */
    List<SystemNotificationDto> getNotificationsByCategory(String category, int limit);

    /**
     * Obtener notificaciones críticas
     */
    List<SystemNotificationDto> getCriticalNotifications();

    // ========== MÉTODOS PARA GESTIONAR NOTIFICACIONES ==========

    /**
     * Marcar notificación como leída
     */
    void markNotificationAsRead(String notificationId);

    /**
     * Marcar todas las notificaciones como leídas
     */
    void markAllNotificationsAsRead();

    /**
     * Eliminar notificación
     */
    void deleteNotification(String notificationId);

    /**
     * Limpiar notificaciones antiguas
     */
    void cleanOldNotifications(int daysToKeep);

    // ========== MÉTODOS PARA CONFIGURACIÓN DE ALERTAS ==========

    /**
     * Configurar alerta
     */
    void configureAlert(String alertId, AlertConfigurationDto configuration);

    /**
     * Obtener configuración de alerta
     */
    AlertConfigurationDto getAlertConfiguration(String alertId);

    /**
     * Eliminar configuración de alerta
     */
    void removeAlertConfiguration(String alertId);

    /**
     * Obtener todas las configuraciones de alerta
     */
    List<AlertConfigurationDto> getAllAlertConfigurations();

    // ========== MÉTODOS PARA ESTADÍSTICAS ==========

    /**
     * Obtener número total de notificaciones
     */
    long getNotificationCount();

    /**
     * Obtener número de notificaciones no leídas
     */
    long getUnreadNotificationCount();

    /**
     * Obtener conteo de notificaciones por tipo
     */
    Map<String, Long> getNotificationCountByType();

    /**
     * Obtener conteo de notificaciones por categoría
     */
    Map<String, Long> getNotificationCountByCategory();
}
