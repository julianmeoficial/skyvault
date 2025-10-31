import { Link } from 'react-router-dom';
import type { AircraftCardDto } from '../../../features/aircraft/types/aircraft.types';
import styles from './SimilarAircraftCard.module.css';

interface SimilarAircraftCardProps {
    aircraft: AircraftCardDto;
}

/**
 * Card horizontal para aeronaves similares.
 * Incluye imagen, nombre, fabricante, y datos clave (pasajeros + alcance).
 * Clickeable para navegar a la página de detalle.
 *
 * IMPORTANTE: Usa las propiedades correctas de AircraftCardDto:
 * - thumbnailUrl (no imageUrl)
 * - manufacturer.name (objeto ManufacturerDto)
 * - family.name (objeto FamilyDto)
 * - id (para navegación)
 */
export function SimilarAircraftCard({ aircraft }: SimilarAircraftCardProps) {
    // ✅ CORRECCIÓN: Usar thumbnailUrl del DTO
    const imageUrl = aircraft.thumbnailUrl || '/assets/images/aircraft-placeholder.jpg';

    // Formatear números con separador de miles (puntos)
    const formatNumber = (num: number | undefined | null): string => {
        if (num === undefined || num === null) return 'N/A';
        return num.toLocaleString('es-ES', { maximumFractionDigits: 0 });
    };

    return (
        <Link
            to={`/aircraft/${aircraft.id}`}
            className={styles.card}
            aria-label={`Ver detalles de ${aircraft.displayName}`}
        >
            {/* Imagen */}
            <div className={styles.imageContainer}>
                <img
                    src={imageUrl}
                    alt={aircraft.displayName}
                    className={styles.image}
                    loading="lazy"
                />
            </div>

            {/* Contenido */}
            <div className={styles.content}>
                {/* Fabricante */}
                <span className={styles.manufacturer}>{aircraft.manufacturer.name}</span>

                {/* Nombre del modelo */}
                <h3 className={styles.name}>{aircraft.displayName}</h3>

                {/* Especificaciones clave */}
                <div className={styles.specs}>
                    <div className={styles.spec}>
                        <span className={styles.specLabel}>Pasajeros</span>
                        <span className={styles.specValue}>{formatNumber(aircraft.typicalPassengers)}</span>
                    </div>
                    <div className={styles.specDivider} />
                    <div className={styles.spec}>
                        <span className={styles.specLabel}>Alcance</span>
                        <span className={styles.specValue}>{formatNumber(aircraft.rangeKm)} km</span>
                    </div>
                </div>

                {/* Badge de familia */}
                {aircraft.family && (
                    <div className={styles.badge}>{aircraft.family.name}</div>
                )}
            </div>

            {/* Hover overlay */}
            <div className={styles.hoverOverlay}>
                <span className={styles.viewDetailsText}>Ver detalles →</span>
            </div>
        </Link>
    );
}
