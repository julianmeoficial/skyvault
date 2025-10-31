import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import type { AircraftDetailDto } from '../../../features/aircraft/types/aircraft.types';
import { BackButton } from '../../../shared/components/ui/BackButton';
import { CompareButton } from '../../../shared/components/ui/CompareButton';
import { CompareSelectionModal } from '../../../shared/components/ui/CompareSelectionModal';
import styles from './HeroSection.module.css';

interface HeroSectionProps {
    aircraft: AircraftDetailDto;
}

/**
 * Hero Section con imagen, navegación, y sistema de comparación
 */
export function HeroSection({ aircraft }: HeroSectionProps) {
    const navigate = useNavigate();
    const [imageError, setImageError] = useState(false);
    const [showCompareModal, setShowCompareModal] = useState(false);

    // Detectar fabricante y construir ruta correcta
    const manufacturerFolder = aircraft.manufacturer.name.toLowerCase();
    const imagePath = `/assets/images/aircraft/${manufacturerFolder}/${aircraft.name}.jpg`;
    const imageFallback = 'https://placehold.co/1920x1080/0d4b7a/ffffff/png?text=Aircraft+Image';

    // ========== HANDLE COMPARE (ABRE MODAL) ==========
    // ✅ IGUAL QUE EN AIRCRAFTCATALOG
    const handleCompare = useCallback(() => {
        setShowCompareModal(true);
    }, []);

    // ========== HANDLE MODAL CONFIRM (NAVEGA A COMPARACIÓN) ==========
    // ✅ IGUAL QUE EN AIRCRAFTCATALOG (pero con aircraft.id como firstSelectedId)
    const handleModalCompare = useCallback((secondAircraftId: number) => {
        navigate(`/compare?ids=${aircraft.id},${secondAircraftId}`);
        setShowCompareModal(false);
    }, [aircraft.id, navigate]);

    // ========== HANDLE MODAL CLOSE ==========
    // ✅ IGUAL QUE EN AIRCRAFTCATALOG
    const handleModalClose = useCallback(() => {
        setShowCompareModal(false);
    }, []);

    return (
        <>
            <section className={styles.heroSection}>
                {/* Imagen de fondo */}
                <div className={styles.heroImage}>
                    <img
                        src={imageError ? imageFallback : imagePath}
                        alt={aircraft.displayName}
                        className={styles.image}
                        onError={() => setImageError(true)}
                        loading="eager"
                    />
                    <div className={styles.overlay}></div>
                </div>

                {/* Contenido del hero */}
                <div className={styles.heroContent}>
                    <div className={styles.container}>
                        {/* Navegación superior */}
                        <div className={styles.topNav}>
                            <BackButton to="/aircraft" label="Catálogo" />
                            <CompareButton
                                onClick={handleCompare}
                                icon={
                                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                        <path d="M9 3v18M15 3v18M3 9h18M3 15h18" />
                                    </svg>
                                }
                            />
                        </div>

                        {/* Breadcrumb */}
                        <nav className={styles.breadcrumb}>
                            <a href="/">Inicio</a>
                            <span>/</span>
                            <a href="/aircraft">Aeronaves</a>
                            <span>/</span>
                            <a href={`/aircraft?manufacturer=${aircraft.manufacturer.id}`}>
                                {aircraft.manufacturer.name}
                            </a>
                            <span>/</span>
                            <span className={styles.current}>{aircraft.name}</span>
                        </nav>

                        {/* Título principal */}
                        <h1 className={styles.title}>{aircraft.displayName}</h1>

                        {/* Badges */}
                        <div className={styles.badges}>
                            {aircraft.sizeCategory && (
                                <span className={`${styles.badge} ${styles.badgeCategory}`}>
                                    {aircraft.sizeCategory.name}
                                </span>
                            )}
                            {aircraft.type && (
                                <span className={`${styles.badge} ${styles.badgeType}`}>
                                    {aircraft.type.name}
                                </span>
                            )}
                            {aircraft.productionState && (
                                <span className={`${styles.badge} ${styles.badgeState}`}>
                                    {aircraft.productionState.name}
                                </span>
                            )}
                        </div>

                        {/* Metadata */}
                        <div className={styles.metadata}>
                            {aircraft.firstFlightDate && (
                                <div className={styles.metaItem}>
                                    <span className={styles.metaLabel}>Primer Vuelo:</span>
                                    <span className={styles.metaValue}>
                                        {new Date(aircraft.firstFlightDate).toLocaleDateString('es-ES', {
                                            year: 'numeric',
                                            month: 'long',
                                            day: 'numeric'
                                        })}
                                    </span>
                                </div>
                            )}
                            {aircraft.introductionYear && (
                                <div className={styles.metaItem}>
                                    <span className={styles.metaLabel}>Introducción:</span>
                                    <span className={styles.metaValue}>{aircraft.introductionYear}</span>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </section>

            {/* ========== MODAL DE SELECCIÓN (MISMO QUE EN AIRCRAFTCATALOG) ========== */}
            <CompareSelectionModal
                isOpen={showCompareModal}
                onClose={handleModalClose}
                onConfirm={handleModalCompare}
                excludeAircraftId={aircraft.id}
            />
        </>
    );
}
