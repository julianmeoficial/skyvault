import React, { useRef, useEffect } from 'react';
import gsap from 'gsap';
import type { AircraftOption, GroupedAircraft } from '../../types/comparison.types';
import styles from './AircraftDropdown.module.css';

interface AircraftDropdownProps {
    isOpen: boolean;
    groupedAircraft: GroupedAircraft;
    selectedAircraft: AircraftOption | null;
    onSelect: (aircraft: AircraftOption) => void;
    onToggle: () => void;
}

export const AircraftDropdown: React.FC<AircraftDropdownProps> = ({
                                                                      isOpen,
                                                                      groupedAircraft,
                                                                      selectedAircraft,
                                                                      onSelect,
                                                                      onToggle
                                                                  }) => {
    const dropdownRef = useRef<HTMLDivElement>(null);
    const contentRef = useRef<HTMLDivElement>(null);
    const backdropRef = useRef<HTMLDivElement>(null);

    // Animación de apertura/cierre
    useEffect(() => {
        if (!contentRef.current || !backdropRef.current) return;

        if (isOpen) {
            // Animación de entrada
            const tl = gsap.timeline();

            tl.to(backdropRef.current, {
                opacity: 1,
                duration: 0.3,
                ease: 'power2.out'
            })
                .fromTo(
                    contentRef.current,
                    {
                        opacity: 0,
                        y: -20,
                        scale: 0.95
                    },
                    {
                        opacity: 1,
                        y: 0,
                        scale: 1,
                        duration: 0.4,
                        ease: 'back.out(1.5)'
                    },
                    '-=0.2'
                );

            // Animar cada item
            const items = contentRef.current.querySelectorAll(`.${styles.aircraftOption}`);
            gsap.fromTo(
                items,
                { opacity: 0, x: -10 },
                {
                    opacity: 1,
                    x: 0,
                    duration: 0.3,
                    stagger: 0.03,
                    ease: 'power2.out'
                }
            );
        } else {
            gsap.to(backdropRef.current, {
                opacity: 0,
                duration: 0.2,
                ease: 'power2.in'
            });
        }
    }, [isOpen]);

    return (
        <div className={styles.dropdownContainer} ref={dropdownRef}>
            <button
                className={`${styles.dropdownTrigger} ${isOpen ? styles.active : ''} ${selectedAircraft ? styles.hasSelection : ''}`}
                onClick={onToggle}
                type="button"
            >
        <span className={styles.selectedText}>
          {selectedAircraft ? (
              <>
                  <span className={styles.aircraftName}>{selectedAircraft.displayName}</span>
                  <span className={styles.aircraftModel}>{selectedAircraft.model}</span>
              </>
          ) : (
              <span className={styles.placeholder}>Selecciona una aeronave</span>
          )}
        </span>
                <svg
                    className={`${styles.chevron} ${isOpen ? styles.rotated : ''}`}
                    width="20"
                    height="20"
                    viewBox="0 0 20 20"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                >
                    <path
                        d="M5 7.5L10 12.5L15 7.5"
                        stroke="currentColor"
                        strokeWidth="1.5"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    />
                </svg>
            </button>

            {isOpen && (
                <>
                    <div
                        ref={backdropRef}
                        className={styles.backdrop}
                        onClick={onToggle}
                    />
                    <div className={styles.dropdownContent} ref={contentRef}>
                        {/* Airbus Section */}
                        {groupedAircraft.Airbus.length > 0 && (
                            <div className={styles.manufacturerSection}>
                                <div className={styles.manufacturerHeader}>
                                    <div className={styles.manufacturerBadge}>
                                        <span className={styles.manufacturerName}>Airbus</span>
                                        <span className={styles.aircraftCount}>
                      {groupedAircraft.Airbus.length} modelos
                    </span>
                                    </div>
                                </div>
                                <div className={styles.aircraftList}>
                                    {groupedAircraft.Airbus.map((aircraft) => (
                                        <button
                                            key={aircraft.id}
                                            className={`${styles.aircraftOption} ${selectedAircraft?.id === aircraft.id ? styles.selected : ''}`}
                                            onClick={() => onSelect(aircraft)}
                                            type="button"
                                        >
                                            <div className={styles.aircraftInfo}>
                                                <span className={styles.optionName}>{aircraft.displayName}</span>
                                                <span className={styles.optionDetails}>
                          {aircraft.model} • {aircraft.family.name}
                        </span>
                                            </div>
                                            {selectedAircraft?.id === aircraft.id && (
                                                <svg
                                                    className={styles.checkIcon}
                                                    width="20"
                                                    height="20"
                                                    viewBox="0 0 20 20"
                                                    fill="none"
                                                >
                                                    <path
                                                        d="M16.6666 5L7.49998 14.1667L3.33331 10"
                                                        stroke="currentColor"
                                                        strokeWidth="2"
                                                        strokeLinecap="round"
                                                        strokeLinejoin="round"
                                                    />
                                                </svg>
                                            )}
                                        </button>
                                    ))}
                                </div>
                            </div>
                        )}

                        {/* Boeing Section */}
                        {groupedAircraft.Boeing.length > 0 && (
                            <div className={styles.manufacturerSection}>
                                <div className={styles.manufacturerHeader}>
                                    <div className={styles.manufacturerBadge}>
                                        <span className={styles.manufacturerName}>Boeing</span>
                                        <span className={styles.aircraftCount}>
                      {groupedAircraft.Boeing.length} modelos
                    </span>
                                    </div>
                                </div>
                                <div className={styles.aircraftList}>
                                    {groupedAircraft.Boeing.map((aircraft) => (
                                        <button
                                            key={aircraft.id}
                                            className={`${styles.aircraftOption} ${selectedAircraft?.id === aircraft.id ? styles.selected : ''}`}
                                            onClick={() => onSelect(aircraft)}
                                            type="button"
                                        >
                                            <div className={styles.aircraftInfo}>
                                                <span className={styles.optionName}>{aircraft.displayName}</span>
                                                <span className={styles.optionDetails}>
                          {aircraft.model} • {aircraft.family.name}
                        </span>
                                            </div>
                                            {selectedAircraft?.id === aircraft.id && (
                                                <svg
                                                    className={styles.checkIcon}
                                                    width="20"
                                                    height="20"
                                                    viewBox="0 0 20 20"
                                                    fill="none"
                                                >
                                                    <path
                                                        d="M16.6666 5L7.49998 14.1667L3.33331 10"
                                                        stroke="currentColor"
                                                        strokeWidth="2"
                                                        strokeLinecap="round"
                                                        strokeLinejoin="round"
                                                    />
                                                </svg>
                                            )}
                                        </button>
                                    ))}
                                </div>
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    );
};

export type { AircraftDropdownProps };