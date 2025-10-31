/**
 * @file ModernAircraftGallery.tsx
 * @description Galería moderna con GRID responsivo - UX optimizada
 * Cards grandes, legibles, con hover effects sutiles
 */

import React, { useState, useMemo } from 'react';
import AIRCRAFT_IMAGES_DATA, { getAircraftByManufacturer } from '../../../../config/aircraftImagesData';
import styles from './ModernAircraftGallery.module.css';

const ModernAircraftGallery: React.FC = () => {
    const [selectedManufacturer, setSelectedManufacturer] = useState<'Airbus' | 'Boeing' | 'All'>('All');
    const [selectedAircraft, setSelectedAircraft] = useState<typeof AIRCRAFT_IMAGES_DATA[0] | null>(null);

    const filteredAircraft = useMemo(() => {
        let aircraft = [...AIRCRAFT_IMAGES_DATA];
        if (selectedManufacturer !== 'All') {
            aircraft = getAircraftByManufacturer(selectedManufacturer);
        }
        return aircraft.sort((a, b) => a.year - b.year);
    }, [selectedManufacturer]);

    return (
        <section className={styles.section}>
            <div className={styles.container}>
                {/* HEADER */}
                <div className={styles.header}>
                    <h2 className={styles.title}>Galería de Aeronaves</h2>
                    <p className={styles.subtitle}>
                        Explora las 36 aeronaves comerciales más avanzadas del mundo
                    </p>
                </div>

                {/* FILTROS */}
                <div className={styles.filterContainer}>
                    <span className={styles.filterLabel}>Fabricante:</span>
                    <div className={styles.filterButtons}>
                        {(['All', 'Airbus', 'Boeing'] as const).map((mfr) => (
                            <button
                                key={mfr}
                                className={`${styles.filterButton} ${
                                    selectedManufacturer === (mfr === 'All' ? 'All' : mfr)
                                        ? styles.active
                                        : ''
                                }`}
                                onClick={() => setSelectedManufacturer(mfr === 'All' ? 'All' : mfr as 'Airbus' | 'Boeing')}
                            >
                                {mfr === 'All' ? 'Todos' : mfr}
                            </button>
                        ))}
                    </div>
                </div>

                {/* GRID DE TARJETAS */}
                <div className={styles.grid}>
                    {filteredAircraft.map((aircraft) => (
                        <AircraftCard
                            key={aircraft.name}
                            aircraft={aircraft}
                            onClick={() => setSelectedAircraft(aircraft)}
                        />
                    ))}
                </div>

                {/* STATS */}
                <div className={styles.stats}>
                    <p className={styles.statText}>
                        Mostrando <strong>{filteredAircraft.length}</strong> de{' '}
                        <strong>36</strong> aeronaves
                    </p>
                </div>
            </div>

            {/* MODAL DE DETALLES */}
            {selectedAircraft && (
                <AircraftModal
                    aircraft={selectedAircraft}
                    onClose={() => setSelectedAircraft(null)}
                />
            )}
        </section>
    );
};

// ============================================
// AIRCRAFT CARD COMPONENT
// ============================================
interface AircraftCardProps {
    aircraft: typeof AIRCRAFT_IMAGES_DATA[0];
    onClick: () => void;
}

const AircraftCard: React.FC<AircraftCardProps> = ({ aircraft, onClick }) => {
    return (
        <div className={styles.card} onClick={onClick}>
            <div className={styles.cardImageContainer}>
                <img
                    src={aircraft.imagePath}
                    alt={aircraft.name}
                    className={styles.cardImage}
                    loading="lazy"
                />
                <div className={styles.cardBadge}>{aircraft.year}</div>
            </div>

            <div className={styles.cardContent}>
                <div className={styles.cardHeader}>
                    <h3 className={styles.cardName}>{aircraft.name}</h3>
                    <span className={styles.cardFamily}>{aircraft.family}</span>
                </div>

                <p className={styles.cardDescription}>{aircraft.description}</p>

                {((aircraft.passengers && aircraft.passengers > 0) || (aircraft.range && aircraft.range > 0)) && (
                    <div className={styles.cardSpecs}>
                        {aircraft.passengers && aircraft.passengers > 0 && (
                            <div className={styles.spec}>
                                <span className={styles.specIcon}>👥</span>
                                <span className={styles.specValue}>{aircraft.passengers}</span>
                            </div>
                        )}
                        {aircraft.range && aircraft.range > 0 && (
                            <div className={styles.spec}>
                                <span className={styles.specIcon}>✈️</span>
                                <span className={styles.specValue}>
                                    {aircraft.range.toLocaleString()} km
                                </span>
                            </div>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
};

// ============================================
// MODAL COMPONENT
// ============================================
interface AircraftModalProps {
    aircraft: typeof AIRCRAFT_IMAGES_DATA[0];
    onClose: () => void;
}

const AircraftModal: React.FC<AircraftModalProps> = ({ aircraft, onClose }) => {
    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
                <button className={styles.modalClose} onClick={onClose}>
                    ✕
                </button>

                <div className={styles.modalContent}>
                    <div className={styles.modalImage}>
                        <img src={aircraft.imagePath} alt={aircraft.name} />
                    </div>

                    <div className={styles.modalInfo}>
                        <div className={styles.modalHeader}>
                            <h2 className={styles.modalTitle}>{aircraft.name}</h2>
                            <span className={styles.modalBadge}>{aircraft.manufacturer}</span>
                        </div>

                        <div className={styles.modalMeta}>
                            <span className={styles.modalFamily}>{aircraft.family}</span>
                            <span className={styles.modalYear}>Año: {aircraft.year}</span>
                        </div>

                        <p className={styles.modalDescription}>{aircraft.description}</p>

                        <div className={styles.modalSpecs}>
                            {aircraft.passengers && aircraft.passengers > 0 && (
                                <div className={styles.modalSpec}>
                                    <span className={styles.modalSpecLabel}>Pasajeros</span>
                                    <span className={styles.modalSpecValue}>{aircraft.passengers}</span>
                                </div>
                            )}
                            {aircraft.range && aircraft.range > 0 && (
                                <div className={styles.modalSpec}>
                                    <span className={styles.modalSpecLabel}>Alcance</span>
                                    <span className={styles.modalSpecValue}>
                                        {aircraft.range.toLocaleString()} km
                                    </span>
                                </div>
                            )}
                            {aircraft.engines && (
                                <div className={styles.modalSpec}>
                                    <span className={styles.modalSpecLabel}>Motores</span>
                                    <span className={styles.modalSpecValue}>{aircraft.engines}</span>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ModernAircraftGallery;
