import React from 'react';
import { SearchLoader } from './SearchLoader';
import { getErrorSuggestion } from '../../../shared/utils/errorMessages';
import styles from './EmptyState.module.css';

interface EmptyStateProps {
    type: 'no-results' | 'error' | 'loading';
    searchTerm?: string;
    errorMessage?: string;
    onRetry?: () => void;
    onReset?: () => void;
}

export const EmptyState: React.FC<EmptyStateProps> = ({
                                                          type,
                                                          searchTerm,
                                                          errorMessage,
                                                          onRetry,
                                                          onReset
                                                      }) => {
    if (type === 'loading') {
        return (
            <div className={styles.emptyState}>
                <SearchLoader />
                <h3 className={styles.title}>Buscando aeronaves...</h3>
                <p className={styles.subtitle}>Esto solo tomará un momento</p>
            </div>
        );
    }

    if (type === 'no-results') {
        return (
            <div className={styles.emptyState}>
                <SearchLoader />
                <h3 className={styles.title}>
                    No encontramos resultados
                    {searchTerm && (
                        <span className={styles.searchTerm}> para "{searchTerm}"</span>
                    )}
                </h3>
                <p className={styles.subtitle}>
                    Intenta con otro término de búsqueda o revisa la ortografía
                </p>
                {onReset && (
                    <button onClick={onReset} className={styles.button}>
                        Ver todas las aeronaves
                    </button>
                )}
            </div>
        );
    }

    if (type === 'error') {
        // ✅ Obtener sugerencia contextual
        const suggestion = errorMessage ? getErrorSuggestion(errorMessage) : '';

        return (
            <div className={styles.emptyState}>
                <SearchLoader />
                <h3 className={styles.title}>Algo salió mal</h3>
                <p className={styles.subtitle}>
                    {errorMessage || 'No pudimos cargar las aeronaves en este momento'}
                </p>
                {suggestion && (
                    <p className={styles.suggestion}>💡 {suggestion}</p>
                )}
                <div className={styles.buttonGroup}>
                    {onRetry && (
                        <button onClick={onRetry} className={styles.button}>
                            Intentar de nuevo
                        </button>
                    )}
                    {onReset && (
                        <button onClick={onReset} className={`${styles.button} ${styles.buttonSecondary}`}>
                            Limpiar filtros
                        </button>
                    )}
                </div>
            </div>
        );
    }

    return null;
};
