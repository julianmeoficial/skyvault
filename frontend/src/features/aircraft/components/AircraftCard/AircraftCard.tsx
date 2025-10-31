import React, { useRef, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import gsap from 'gsap';
import type { AircraftCardDto } from '../../types/aircraft.types';
import styles from './AircraftCard.module.css';

interface AircraftCardProps {
    aircraft: AircraftCardDto;
    onCompare?: (id: number) => void;
    index?: number;
}

export const AircraftCard: React.FC<AircraftCardProps> = ({
                                                              aircraft,
                                                              onCompare,
                                                              index = 0
                                                          }) => {
    const navigate = useNavigate();
    const cardRef = useRef<HTMLDivElement>(null);
    const [isHovered, setIsHovered] = useState(false);

    // Animación de entrada con GSAP
    useEffect(() => {
        if (!cardRef.current) return;

        gsap.fromTo(
            cardRef.current,
            {
                opacity: 0,
                y: 30,
                scale: 0.95
            },
            {
                opacity: 1,
                y: 0,
                scale: 1,
                duration: 0.5,
                delay: index * 0.08,
                ease: 'power3.out'
            }
        );
    }, [index]);

    const handleCardClick = () => {
        navigate(`/aircraft/${aircraft.id}`);
    };

    const handleCompareClick = (e: React.MouseEvent) => {
        e.stopPropagation();
        if (onCompare) {
            onCompare(aircraft.id); // Esto abre el modal en Catalog
        }
    };

    // Formatear números
    const formatNumber = (num?: number) => {
        if (!num) return 'N/A';
        return num.toLocaleString('es-ES');
    };

    // Obtener thumbnailUrl del backend
    const thumbnailUrl = aircraft.thumbnailUrl;

    return (
        <div
            ref={cardRef}
            className={`${styles.card} ${isHovered ? styles.cardHover : ''}`}
            onClick={handleCardClick}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
            role="button"
            tabIndex={0}
            aria-label={`Ver detalles de ${aircraft.displayName || aircraft.name}`}
        >
            {/* Imagen */}
            <div className={styles.imageContainer}>
                {thumbnailUrl ? (
                    <img
                        src={thumbnailUrl}
                        alt={aircraft.displayName || aircraft.name}
                        className={styles.image}
                        loading="lazy"
                    />
                ) : (
                    <div className={styles.imagePlaceholder}>
                        <svg width="64" height="64" viewBox="0 0 64 64" fill="none">
                            <path
                                d="M56 42.67v-5.34l-21.33-13.33V10.67c0-2.21-1.79-4-4-4s-4 1.79-4 4V24L5.33 37.33v5.34l21.34-6.67V50.67L21.33 54v4L30.67 56 40 58v-4l-5.33-3.33V36l21.33 6.67z"
                                fill="#D0D5DD"
                            />
                        </svg>
                    </div>
                )}
            </div>

            {/* Contenido */}
            <div className={styles.content}>
                <h3 className={styles.model}>{aircraft.displayName || aircraft.name}</h3>
                <p className={styles.family}>{aircraft.family?.name || 'N/A'}</p>
            </div>

            {/* Especificaciones con iconos de Figma */}
            <div className={styles.specsList}>
                {/* Capacidad */}
                <div className={styles.specItem}>
                    <div className={styles.iconContainer}>
                        <svg width="19" height="19" viewBox="0 0 19 19" fill="none" xmlns="http://www.w3.org/2000/svg" className={styles.icon}>
                            <path d="M18.5 5.60064C18.5 5.05654 18.14 4.51245 17.78 4.2404L12.11 0.703771C11.8768 0.570218 11.6132 0.5 11.345 0.5C11.0768 0.5 10.8132 0.570218 10.58 0.703771L1.31 6.14474C0.86 6.3261 0.5 6.8702 0.5 7.4143V13.3994C0.5 13.8528 0.86 14.4876 1.22 14.7596L6.89 18.2962C7.12317 18.4298 7.38679 18.5 7.655 18.5C7.92321 18.5 8.18683 18.4298 8.42 18.2962L17.69 12.8553C18.14 12.5832 18.5 11.9484 18.5 11.495V5.60064Z" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
                            <path d="M7.70003 18.4776V11.3137M7.70003 11.3137L0.590027 6.8702M7.70003 11.3137L18.41 5.05655M11.3 16.5733V9.22796M14.9 14.4876V7.14225" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
                        </svg>
                    </div>
                    <p className={styles.specText}>
                        <span className={styles.specLabel}>Capacidad:</span> <span className={styles.specValue}>{formatNumber(aircraft.typicalPassengers)} pax</span>
                    </p>
                </div>

                {/* Alcance */}
                <div className={styles.specItem}>
                    <div className={styles.iconContainer}>
                        <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg" className={styles.icon}>
                            <path d="M8.05987 9C8.05987 9 9.03655 9 10.1302 9C10.8866 9 11.5 9.6803 11.5 10.5195C11.5 11.3587 10.8868 12.039 10.1304 12.039H7.83448C7.09747 12.039 6.5 12.7018 6.5 13.5195C6.5 14.3371 7.09747 15 7.83448 15H9.97347" stroke="currentColor" strokeLinecap="round"/>
                            <path d="M2.5 5.03195C2.5 3.98592 3.37444 3.125 4.45312 3.125C5.53181 3.125 6.40625 3.98592 6.40625 5.03195C6.40625 5.36939 6.31409 5.70433 6.13914 5.996L5.40095 7.22677C4.97931 7.92975 4.76848 8.28125 4.45312 8.28125C4.13777 8.28125 3.92694 7.92975 3.5053 7.22677L2.76711 5.996C2.59216 5.70433 2.5 5.36939 2.5 5.03195ZM13.5938 13.6257C13.5938 12.5797 14.4682 11.7188 15.5469 11.7188C16.6256 11.7188 17.5 12.5797 17.5 13.6257C17.5 13.9631 17.4078 14.2981 17.2328 14.5897L16.4947 15.8205C16.0731 16.5234 15.8622 16.875 15.5469 16.875C15.2315 16.875 15.0207 16.5234 14.599 15.8205L13.8609 14.5897C13.6859 14.2981 13.5938 13.9631 13.5938 13.6257Z" stroke="currentColor"/>
                            <path d="M5 5.07812C5 5.38016 4.75516 5.625 4.45312 5.625C4.15109 5.625 3.90625 5.38016 3.90625 5.07812C3.90625 4.77609 4.15109 4.53125 4.45312 4.53125C4.75516 4.53125 5 4.77609 5 5.07812ZM16.0938 13.6719C16.0938 13.9739 15.8489 14.2188 15.5469 14.2188C15.2448 14.2188 15 13.9739 15 13.6719C15 13.3698 15.2448 13.125 15.5469 13.125C15.8489 13.125 16.0938 13.3698 16.0938 13.6719Z" fill="currentColor"/>
                        </svg>
                    </div>
                    <p className={styles.specText}>
                        <span className={styles.specLabel}>Alcance:</span> <span className={styles.specValue}>{formatNumber(aircraft.rangeKm)} km</span>
                    </p>
                </div>

                {/* Año */}
                <div className={styles.specItem}>
                    <div className={styles.iconContainer}>
                        <svg width="18" height="18" viewBox="0 0 18 18" fill="none" xmlns="http://www.w3.org/2000/svg" className={styles.icon}>
                            <path d="M14.25 3H12.75V2.25C12.75 2.05109 12.671 1.86032 12.5303 1.71967C12.3897 1.57902 12.1989 1.5 12 1.5C11.8011 1.5 11.6103 1.57902 11.4697 1.71967C11.329 1.86032 11.25 2.05109 11.25 2.25V3H6.75V2.25C6.75 2.05109 6.67098 1.86032 6.53033 1.71967C6.38968 1.57902 6.19891 1.5 6 1.5C5.80109 1.5 5.61032 1.57902 5.46967 1.71967C5.32902 1.86032 5.25 2.05109 5.25 2.25V3H3.75C3.15326 3 2.58097 3.23705 2.15901 3.65901C1.73705 4.08097 1.5 4.65326 1.5 5.25V14.25C1.5 14.8467 1.73705 15.419 2.15901 15.841C2.58097 16.2629 3.15326 16.5 3.75 16.5H14.25C14.8467 16.5 15.419 16.2629 15.841 15.841C16.2629 15.419 16.5 14.8467 16.5 14.25V5.25C16.5 4.65326 16.2629 4.08097 15.841 3.65901C15.419 3.23705 14.8467 3 14.25 3ZM15 14.25C15 14.4489 14.921 14.6397 14.7803 14.7803C14.6397 14.921 14.4489 15 14.25 15H3.75C3.55109 15 3.36032 14.921 3.21967 14.7803C3.07902 14.6397 3 14.4489 3 14.25V9H15V14.25ZM15 7.5H3V5.25C3 5.05109 3.07902 4.86032 3.21967 4.71967C3.36032 4.57902 3.55109 4.5 3.75 4.5H5.25V5.25C5.25 5.44891 5.32902 5.63968 5.46967 5.78033C5.61032 5.92098 5.80109 6 6 6C6.19891 6 6.38968 5.92098 6.53033 5.78033C6.67098 5.63968 6.75 5.44891 6.75 5.25V4.5H11.25V5.25C11.25 5.44891 11.329 5.63968 11.4697 5.78033C11.6103 5.92098 11.8011 6 12 6C12.1989 6 12.3897 5.92098 12.5303 5.78033C12.671 5.63968 12.75 5.44891 12.75 5.25V4.5H14.25C14.4489 4.5 14.6397 4.57902 14.7803 4.71967C14.921 4.86032 15 5.05109 15 5.25V7.5Z" fill="currentColor"/>
                        </svg>
                    </div>
                    <p className={styles.specText}>
                        <span className={styles.specLabel}>Año:</span> <span className={styles.specValue}>{aircraft.introductionYear || 'N/A'}</span>
                    </p>
                </div>
            </div>

            {/* Botón Comparar */}
            <button
                className={styles.compareButton}
                onClick={handleCompareClick}
                aria-label={`Agregar ${aircraft.displayName} a comparación`}
            >
                Comparar
            </button>
        </div>
    );
};
