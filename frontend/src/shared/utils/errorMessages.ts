/**
 * Transforma errores técnicos en mensajes amigables para usuarios
 */
export const getUserFriendlyError = (errorMessage: string): string => {
    // Normalizar el mensaje a minúsculas para comparación
    const lowerError = errorMessage.toLowerCase();

    // ========== ERRORES DE CONEXIÓN ==========
    if (lowerError.includes('network') ||
        lowerError.includes('fetch') ||
        lowerError.includes('failed to fetch')) {
        return 'No pudimos conectar con el servidor. Verifica tu conexión a internet.';
    }

    // ========== ERRORES 404 ==========
    if (lowerError.includes('404') || lowerError.includes('not found')) {
        return 'No encontramos los datos solicitados.';
    }

    // ========== ERRORES 500 ==========
    if (lowerError.includes('500') ||
        lowerError.includes('internal server') ||
        lowerError.includes('server error')) {
        return 'El servidor está experimentando problemas. Intenta de nuevo en unos momentos.';
    }

    // ========== ERRORES 422 (Validación) ==========
    if (lowerError.includes('422') || lowerError.includes('validation')) {
        return 'Los datos proporcionados no son válidos. Revisa los filtros de búsqueda.';
    }

    // ========== ERRORES 401/403 (Autenticación) ==========
    if (lowerError.includes('401') ||
        lowerError.includes('403') ||
        lowerError.includes('unauthorized') ||
        lowerError.includes('forbidden')) {
        return 'No tienes permisos para acceder a estos datos.';
    }

    // ========== ERRORES DE TIMEOUT ==========
    if (lowerError.includes('timeout') || lowerError.includes('timed out')) {
        return 'La solicitud tardó demasiado. Intenta de nuevo.';
    }

    // ========== ERRORES TÉCNICOS COMUNES ==========

    // "Cannot read properties of null"
    if (lowerError.includes('cannot read') &&
        (lowerError.includes('null') || lowerError.includes('undefined'))) {
        return 'Ocurrió un error al procesar los datos. Intenta refrescar la página.';
    }

    // "TypeError"
    if (lowerError.includes('typeerror')) {
        return 'Ocurrió un error inesperado. Intenta de nuevo.';
    }

    // "SyntaxError"
    if (lowerError.includes('syntaxerror') || lowerError.includes('unexpected token')) {
        return 'Recibimos datos en formato incorrecto. Por favor recarga la página.';
    }

    // "ReferenceError"
    if (lowerError.includes('referenceerror') || lowerError.includes('is not defined')) {
        return 'Ocurrió un error técnico. Por favor recarga la página.';
    }

    // ========== ERRORES GENÉRICOS ==========

    // Si el mensaje es muy corto o genérico
    if (errorMessage.length < 10) {
        return 'Ocurrió un error. Por favor intenta de nuevo.';
    }

    // Si contiene código técnico (paréntesis, dos puntos, comillas)
    if (errorMessage.includes('(') && errorMessage.includes(')') ||
        errorMessage.includes(':') && errorMessage.includes("'")) {
        return 'Ocurrió un error técnico. Por favor recarga la página o intenta de nuevo.';
    }

    // ========== MENSAJE ORIGINAL (si no coincide con nada) ==========
    // Si el mensaje ya parece amigable (no tiene términos técnicos), devolverlo
    const technicalTerms = [
        'null', 'undefined', 'error', 'exception',
        'stack', 'trace', 'at ', 'function', 'async'
    ];

    const hasTechnicalTerms = technicalTerms.some(term => lowerError.includes(term));

    if (!hasTechnicalTerms && errorMessage.length < 100) {
        return errorMessage;
    }

    // Fallback final
    return 'Algo salió mal. Por favor recarga la página o intenta de nuevo.';
};

/**
 * Obtiene un mensaje de sugerencia basado en el tipo de error
 */
export const getErrorSuggestion = (errorMessage: string): string => {
    const lowerError = errorMessage.toLowerCase();

    if (lowerError.includes('network') || lowerError.includes('fetch')) {
        return 'Revisa tu conexión a internet y vuelve a intentar';
    }

    if (lowerError.includes('500') || lowerError.includes('server')) {
        return 'El problema es temporal, intenta nuevamente en unos minutos';
    }

    if (lowerError.includes('timeout')) {
        return 'La conexión es lenta, intenta con una mejor conexión';
    }

    if (lowerError.includes('422') || lowerError.includes('validation')) {
        return 'Prueba con otros términos de búsqueda o limpia los filtros';
    }

    return 'Intenta recargar la página o limpiar los filtros';
};
