package com.skyvault.backend.validation;

/**
 * Grupos de validación para diferentes operaciones
 */
public final class ValidationGroups {

    /**
     * Validación para operaciones de creación
     */
    public interface Create {}

    /**
     * Validación para operaciones de actualización
     */
    public interface Update {}

    /**
     * Validación para operaciones de eliminación
     */
    public interface Delete {}

    /**
     * Validación para filtros de búsqueda
     */
    public interface Search {}

    /**
     * Validación para comparaciones
     */
    public interface Compare {}

    /**
     * Validación completa (todos los campos)
     */
    public interface Full extends Create, Update {}

    private ValidationGroups() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
