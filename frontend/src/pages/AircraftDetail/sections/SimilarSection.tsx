import { useSimilarAircraft } from '../../../features/aircraft/hooks/useSimilarAircraft';
import { SimilarAircraftCard } from '../components/SimilarAircraftCard';
import styles from './SimilarSection.module.css';

interface SimilarSectionProps {
    aircraftId: number;
}

/**
 * Sección de aeronaves similares.
 * Muestra hasta 3 aeronaves con características similares.
 *
 * Usa el hook useSimilarAircraft para obtener datos del backend.
 */
export function SimilarSection({ aircraftId }: SimilarSectionProps) {
    const { similarAircraft, isLoading, error } = useSimilarAircraft(aircraftId, {
        limit: 3,
        onlyActive: true
    });

    // No mostrar la sección si no hay aeronaves similares
    if (isLoading) {
        return (
            <section className={styles.similarSection}>
                <div className={styles.container}>
                    <div className={styles.loading}>Cargando aeronaves similares...</div>
                </div>
            </section>
        );
    }

    if (error || similarAircraft.length === 0) {
        return null; // No mostrar sección si hay error o no hay resultados
    }

    return (
        <section className={styles.similarSection}>
            <div className={styles.container}>
                {/* Header */}
                <div className={styles.header}>
                    <h2 className={styles.title}>Aeronaves Similares</h2>
                    <p className={styles.subtitle}>
                        Modelos con características y rendimiento comparables
                    </p>
                </div>

                {/* Grid de cards */}
                <div className={styles.grid}>
                    {similarAircraft.map((aircraft) => (
                        <SimilarAircraftCard
                            key={aircraft.id}
                            aircraft={aircraft}
                        />
                    ))}
                </div>
            </div>
        </section>
    );
}
