import React, { useState } from 'react';
import type { ComparisonItem } from '../types/comparison.types';
import { ManufacturerLogo } from '../../../shared/components/ManufacturerLogo';
import { ImageModal } from "./ImageModal";
import styles from './ComparisonCard.module.css';

interface ComparisonCardProps {
    item: ComparisonItem;
    onRemove: (id: number) => void;
    index: number;
}

const AIRCRAFT_COLORS = [
    { bg: '#E3F2FD', border: '#90CAF9', glass: 'rgba(227, 242, 253, 0.4)' },
    { bg: '#FFF3E0', border: '#FFCC80', glass: 'rgba(255, 243, 224, 0.4)' },
    { bg: '#F3E5F5', border: '#CE93D8', glass: 'rgba(243, 229, 245, 0.4)' },
];

export const ComparisonCard: React.FC<ComparisonCardProps> = ({ item, onRemove, index }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const imageUrl = item.images?.[0]?.url || item.thumbnailUrl || '/placeholder-aircraft.jpg';
    const colors = AIRCRAFT_COLORS[index] || AIRCRAFT_COLORS[0];

    return (
        <>
            <div
                className={styles.card}
                style={{
                    '--card-color': colors.glass,
                    '--card-border': colors.border,
                    '--card-bg': colors.bg,
                } as React.CSSProperties}
            >
                <div className={styles.glassEffect}></div>
                <div className={styles.glassEffect}></div>
                <div className={styles.liquidLayer}></div>

                <button
                    className={styles.removeButton}
                    onClick={() => onRemove(item.id)}
                    aria-label="Remover de comparación"
                >
                    ×
                </button>

                <div
                    className={styles.imageContainer}
                    onClick={() => setIsModalOpen(true)}
                    role="button"
                    tabIndex={0}
                    aria-label="Ver imagen en grande"
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key === ' ') {
                            e.preventDefault();
                            setIsModalOpen(true);
                        }
                    }}
                >
                    <img
                        src={imageUrl}
                        alt={item.displayName || item.name}
                        className={styles.image}
                        onError={(e) => {
                            e.currentTarget.src = '/placeholder-aircraft.jpg';
                        }}
                    />
                    <div className={styles.imageOverlay}>
                        <svg width="32" height="32" viewBox="0 0 24 24" fill="none">
                            <path
                                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0zM10 7v6m3-3H7"
                                stroke="currentColor"
                                strokeWidth="2"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                            />
                        </svg>
                    </div>
                </div>

                <div className={styles.content}>
                    <h3 className={styles.title}>{item.displayName || item.name}</h3>

                    <div className={styles.manufacturerContainer}>
                        <ManufacturerLogo
                            manufacturer={item.manufacturer?.name || 'Unknown'}
                            className={styles.manufacturerLogo}
                        />
                    </div>

                    <div className={styles.specs}>
                        <div className={styles.specItem}>
                            <span className={styles.specLabel}>Pasajeros:</span>
                            <span className={styles.specValue}>
                                {item.maxPassengers || item.max_passengers || item.typicalPassengers || item.typical_passengers || '-'}
                            </span>
                        </div>

                        <div className={styles.specItem}>
                            <span className={styles.specLabel}>Alcance:</span>
                            <span className={styles.specValue}>
                                {(item.rangeKm || item.range_km)?.toLocaleString() || '-'} km
                            </span>
                        </div>

                        <div className={styles.specItem}>
                            <span className={styles.specLabel}>Velocidad:</span>
                            <span className={styles.specValue}>
                                {item.cruiseSpeedKnots || item.cruise_speed_knots || '-'} knots
                            </span>
                        </div>

                        <div className={styles.specItem}>
                            <span className={styles.specLabel}>Año:</span>
                            <span className={styles.specValue}>
                                {item.introductionYear || item.introduction_year || '-'}
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <ImageModal
                isOpen={isModalOpen}
                imageUrl={imageUrl}
                alt={item.displayName || item.name}
                onClose={() => setIsModalOpen(false)}
            />
        </>
    );
};
