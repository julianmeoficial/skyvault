import React, { useState, useRef, useEffect } from 'react';
import gsap from 'gsap';
import type { GroupedAircraft, AircraftOption } from '../../types/comparison.types';
import { AircraftDropdown } from '../AircraftDropdown/AircraftDropdown';
import styles from './EmptyComparisonCard.module.css';

interface EmptyComparisonCardProps {
    groupedAircraft: GroupedAircraft;
    onSelect: (aircraft: AircraftOption) => void;
    onRemove?: () => void;
    canRemove?: boolean;
    index: number;
}

export const EmptyComparisonCard: React.FC<EmptyComparisonCardProps> = ({
                                                                            groupedAircraft,
                                                                            onSelect,
                                                                            onRemove,
                                                                            canRemove = false,
                                                                            index
                                                                        }) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedAircraft, setSelectedAircraft] = useState<AircraftOption | null>(null);
    const cardRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!cardRef.current) return;

        gsap.fromTo(
            cardRef.current,
            { opacity: 0, scale: 0.9, y: 30 },
            {
                opacity: 1,
                scale: 1,
                y: 0,
                duration: 0.6,
                delay: index * 0.15,
                ease: 'back.out(1.4)'
            }
        );
    }, [index]);

    const handleRemove = () => {
        if (!cardRef.current || !onRemove) return;

        gsap.to(cardRef.current, {
            opacity: 0,
            scale: 0.8,
            y: -20,
            duration: 0.4,
            ease: 'power2.in',
            onComplete: () => {
                onRemove();
            }
        });
    };

    const handleSelect = (aircraft: AircraftOption) => {
        setSelectedAircraft(aircraft);
        onSelect(aircraft);
        setIsOpen(false);
    };

    return (
        <div className={styles.emptyCard} ref={cardRef}>
            {/* Botón X para eliminar */}
            {canRemove && (
                <button
                    className={styles.removeButton}
                    onClick={handleRemove}
                    aria-label="Eliminar selector"
                    title="Eliminar este selector"
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

            <div className={styles.content}>
                <div className={styles.iconContainer}>
                    <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                        <path
                            d="M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z"
                            fill="currentColor"
                            opacity="0.2"
                        />
                    </svg>
                </div>

                <h3 className={styles.title}>Agregar aeronave</h3>
                <p className={styles.description}>Selecciona un modelo para comparar</p>

                <div className={styles.dropdownWrapper}>
                    <AircraftDropdown
                        isOpen={isOpen}
                        groupedAircraft={groupedAircraft}
                        selectedAircraft={selectedAircraft}
                        onSelect={handleSelect}
                        onToggle={() => setIsOpen(!isOpen)}
                    />
                </div>
            </div>
        </div>
    );
};
