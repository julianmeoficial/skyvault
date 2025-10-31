import React, { useRef, useEffect, useState } from 'react';
import gsap from 'gsap';
import type { AircraftSelector, GroupedAircraft, AircraftOption } from '../../types/comparison.types';
import { AircraftDropdown } from '../AircraftDropdown/AircraftDropdown';
import { ImageModal } from '../ImageModal';
import styles from './SelectorCard.module.css';

interface SelectorCardProps {
    selector: AircraftSelector;
    index: number;
    groupedAircraft: GroupedAircraft;
    canRemove: boolean;
    onSelect: (selectorId: string, aircraft: AircraftOption) => void;
    onClear: (selectorId: string) => void;
    onToggle: (selectorId: string) => void;
    onRemove: (selectorId: string) => void;
}

export const SelectorCard: React.FC<SelectorCardProps> = ({
                                                              selector,
                                                              index,
                                                              groupedAircraft,
                                                              canRemove,
                                                              onSelect,
                                                              onClear,
                                                              onToggle,
                                                              onRemove
                                                          }) => {
    const cardRef = useRef<HTMLDivElement>(null);
    const previewRef = useRef<HTMLDivElement>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    // Animación de entrada del card
    useEffect(() => {
        if (!cardRef.current) return;

        gsap.fromTo(
            cardRef.current,
            {
                opacity: 0,
                y: 40,
                scale: 0.9
            },
            {
                opacity: 1,
                y: 0,
                scale: 1,
                duration: 0.6,
                delay: index * 0.15,
                ease: 'back.out(1.4)'
            }
        );
    }, [index]);

    // Animación de preview cuando se selecciona
    useEffect(() => {
        if (!previewRef.current) return;

        if (selector.selectedAircraft) {
            gsap.fromTo(
                previewRef.current,
                { opacity: 0, y: 20, scale: 0.95 },
                {
                    opacity: 1,
                    y: 0,
                    scale: 1,
                    duration: 0.5,
                    ease: 'back.out(1.5)'
                }
            );
        }
    }, [selector.selectedAircraft]);

    // Determinar clase de badge según manufacturer
    const getManufacturerClass = () => {
        if (!selector.selectedAircraft) return '';
        return selector.selectedAircraft.manufacturer.name === 'Airbus'
            ? styles.badgeAirbus
            : styles.badgeBoeing;
    };

    return (
        <>
            <div
                className={`${styles.selectorCard} ${selector.selectedAircraft ? styles.hasSelection : ''}`}
                ref={cardRef}
            >
                {/* Header del Card */}
                <div className={styles.cardHeader}>
                    <div className={styles.headerLeft}>
                        <div className={styles.headerInfo}>
                            <span className={styles.selectorLabel}>Aeronave {index + 1}</span>
                            {selector.selectedAircraft && (
                                <span className={`${styles.manufacturer} ${getManufacturerClass()}`}>
                                    {selector.selectedAircraft.manufacturer.name}
                                </span>
                            )}
                        </div>
                    </div>
                    {canRemove && (
                        <button
                            className={styles.removeButton}
                            onClick={() => onRemove(selector.id)}
                            type="button"
                            aria-label="Eliminar selector"
                            title="Eliminar aeronave"
                        >
                            <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                                <path
                                    d="M15 5L5 15M5 5L15 15"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                />
                            </svg>
                        </button>
                    )}
                </div>

                {/* Body del Card */}
                <div className={styles.cardBody}>
                    {/* Dropdown siempre visible cuando NO hay selección */}
                    {!selector.selectedAircraft && (
                        <div className={styles.emptyState}>
                            <div className={styles.emptyIcon}>
                                <svg width="48" height="48" viewBox="0 0 24 24" fill="none">
                                    <path
                                        d="M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z"
                                        fill="currentColor"
                                        opacity="0.2"
                                    />
                                </svg>
                            </div>
                            <p className={styles.emptyText}>Selecciona una aeronave</p>
                        </div>
                    )}

                    {/* Dropdown */}
                    <div className={styles.dropdownWrapper}>
                        <AircraftDropdown
                            isOpen={selector.isOpen}
                            groupedAircraft={groupedAircraft}
                            selectedAircraft={selector.selectedAircraft}
                            onSelect={(aircraft) => onSelect(selector.id, aircraft)}
                            onToggle={() => onToggle(selector.id)}
                        />
                    </div>

                    {/* Preview de aeronave seleccionada */}
                    {selector.selectedAircraft && (
                        <div className={styles.selectedPreview} ref={previewRef}>
                            <div className={styles.previewContent}>
                                {/* Thumbnail */}
                                <div
                                    className={styles.previewImageContainer}
                                    onClick={() => setIsModalOpen(true)}
                                    role="button"
                                    tabIndex={0}
                                    aria-label="Ver imagen en grande"
                                    style={{ cursor: 'zoom-in' }}
                                    onKeyDown={(e) => {
                                        if (e.key === 'Enter' || e.key === ' ') {
                                            e.preventDefault();
                                            setIsModalOpen(true);
                                        }
                                    }}
                                >
                                    {selector.selectedAircraft.thumbnailUrl ? (
                                        <img
                                            src={selector.selectedAircraft.thumbnailUrl}
                                            alt={selector.selectedAircraft.displayName}
                                            className={styles.aircraftImage}
                                            loading="lazy"
                                        />
                                    ) : (
                                        <div className={styles.imagePlaceholder}>
                                            <svg width="32" height="32" viewBox="0 0 24 24" fill="none">
                                                <path
                                                    d="M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z"
                                                    fill="currentColor"
                                                    opacity="0.3"
                                                />
                                            </svg>
                                        </div>
                                    )}
                                </div>

                                {/* Info */}
                                <div className={styles.previewInfo}>
                                    <h3 className={styles.previewTitle}>
                                        {selector.selectedAircraft.displayName}
                                    </h3>

                                    {/* Quick specs */}
                                    <div className={styles.previewDetails}>
                                        {selector.selectedAircraft.typicalPassengers && (
                                            <div className={styles.detailItem}>
                                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                    <path
                                                        d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"
                                                        stroke="currentColor"
                                                        strokeWidth="2"
                                                        strokeLinecap="round"
                                                        strokeLinejoin="round"
                                                    />
                                                </svg>
                                                <span>{selector.selectedAircraft.typicalPassengers.toLocaleString()} pax</span>
                                            </div>
                                        )}
                                        {selector.selectedAircraft.rangeKm && (
                                            <div className={styles.detailItem}>
                                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                    <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="2" />
                                                    <path d="M12 6v6l4 2" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
                                                </svg>
                                                <span>{selector.selectedAircraft.rangeKm.toLocaleString()} km</span>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </div>

                            {/* Botón cambiar */}
                            <button
                                className={styles.clearButton}
                                onClick={() => onClear(selector.id)}
                                type="button"
                            >
                                <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                                    <path
                                        d="M1 4v6h6M23 20v-6h-6"
                                        stroke="currentColor"
                                        strokeWidth="2"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                    />
                                    <path
                                        d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 0 1 3.51 15"
                                        stroke="currentColor"
                                        strokeWidth="2"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                    />
                                </svg>
                                Cambiar aeronave
                            </button>
                        </div>
                    )}
                </div>
            </div>

            {/* Modal fuera del card principal */}
            {selector.selectedAircraft?.thumbnailUrl && (
                <ImageModal
                    isOpen={isModalOpen}
                    imageUrl={selector.selectedAircraft.thumbnailUrl}
                    alt={selector.selectedAircraft.displayName}
                    onClose={() => setIsModalOpen(false)}
                />
            )}
        </>
    );
};

export type { SelectorCardProps };
