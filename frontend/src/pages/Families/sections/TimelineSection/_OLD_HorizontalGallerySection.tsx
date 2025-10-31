/**
 * @file HorizontalGallerySection.tsx
 * @description Galería horizontal DESLUMBRANTE con GSAP 120 FPS
 * Parallax, Tilt 3D, Glassmorphism, Light Effects
 */

import React, { useState, useMemo, useEffect, useRef } from 'react';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import AIRCRAFT_IMAGES_DATA, { getAircraftByManufacturer } from '../../../../config/aircraftImagesData';
import styles from './HorizontalGallerySection.module.css';

gsap.registerPlugin(ScrollTrigger);
console.log('GSAP Version:', gsap.version);

const HorizontalGallerySection: React.FC = () => {
    const [selectedManufacturer, setSelectedManufacturer] = useState<'Airbus' | 'Boeing' | 'All'>('All');
    const [focusedIndex] = useState(0);
    const containerRef = useRef<HTMLDivElement>(null);
    const galleryRef = useRef<HTMLDivElement>(null);
    const cardsRef = useRef<(HTMLDivElement | null)[]>([]);
    const scrollContainerRef = useRef<HTMLDivElement>(null);

    const filteredAircraft = useMemo(() => {
        let aircraft = [...AIRCRAFT_IMAGES_DATA];
        if (selectedManufacturer !== 'All') {
            aircraft = getAircraftByManufacturer(selectedManufacturer);
        }
        return aircraft.sort((a, b) => a.year - b.year);
    }, [selectedManufacturer]);

    // ANIMACIONES CON GSAP
    useEffect(() => {
        if (!containerRef.current || !galleryRef.current || !scrollContainerRef.current) return;

        const ctx = gsap.context(() => {
            // GPU OPTIMIZATION
            gsap.set('.gallery-card', {
                force3D: true,
                backfaceVisibility: 'hidden',
                perspective: 2000,
                willChange: 'transform, opacity, scale, filter',
                scale: 0.7,           /* INICIALIZA ESCALA CON GSAP */
                opacity: 0.5,         /* INICIALIZA OPACIDAD CON GSAP */
                transformOrigin: 'center center',
            });

            gsap.set('.gallery-card-image', { force3D: true });
            gsap.set('.gallery-card-light', { force3D: true });

            const updateFocus = () => {
                cardsRef.current.forEach((card, idx) => {
                    if (!card || !scrollContainerRef.current || idx < 0) return;

                    const rect = card.getBoundingClientRect();
                    const containerRect = scrollContainerRef.current.getBoundingClientRect();
                    const cardCenter = rect.left + rect.width / 2;
                    const containerCenter = containerRect.left + containerRect.width / 2;
                    const distance = Math.abs(cardCenter - containerCenter);

                    // VALORES OPTIMIZADOS PARA MEJOR LEGIBILIDAD
                    let scale = 0.96;  // CASI TAMAÑO COMPLETO
                    let opacity = 0.90; // MUY VISIBLE
                    let y = 0;
                    let rotateZ = 0; // SIN ROTACIÓN por defecto
                    let filter = 'blur(0px) brightness(0.95)'; // SIN BLUR

                    if (distance < 120) {
                        // SUPER FOCUS - CENTRO
                        scale = 1.15;  // MÁS GRANDE pero no exagerado
                        opacity = 1;
                        y = -15;  // Menos elevación
                        rotateZ = 0;
                        filter = 'blur(0px) brightness(1.05)';
                    } else if (distance < 300) {
                        // FOCUS CERCANO
                        scale = 1.02;  // CASI igual de grande
                        opacity = 0.95;
                        y = -5;
                        rotateZ = 0;
                        filter = 'blur(0px) brightness(1)';
                    } else if (distance < 500) {
                        // SEMI FOCUS
                        scale = 0.98;  // LIGERAMENTE más pequeño
                        opacity = 0.92;
                        y = 0;
                        rotateZ = 0;
                        filter = 'blur(0px) brightness(0.97)';
                    }

                    gsap.to(card, {
                        scale,
                        opacity,
                        y,
                        rotateZ,
                        filter,
                        duration: 0.6,
                        ease: 'power2.out',
                        overwrite: 'auto',
                    });

                    // EFECTO LUZ PARALAX
                    const lightEl = card.querySelector('.gallery-card-light') as HTMLElement;
                    if (lightEl) {
                        gsap.to(lightEl, {
                            opacity: Math.max(0, 1 - distance / 300),
                            duration: 0.4,
                            overwrite: 'auto',
                        });
                    }
                });
            };

            if (scrollContainerRef.current) {
                scrollContainerRef.current.addEventListener('scroll', updateFocus, { passive: true });
            }
            window.addEventListener('resize', updateFocus);

            // ANIMACIÓN INICIAL DE ENTRADA
            gsap.from('.gallery-card', {
                opacity: 0,
                scale: 0.5,
                rotateZ: 15,
                duration: 0.8,
                stagger: 0.06,
                ease: 'back.out(1.4)',
                overwrite: false,
            });

            const currentScrollContainer = scrollContainerRef.current;
            return () => {
                currentScrollContainer?.removeEventListener('scroll', updateFocus);
                window.removeEventListener('resize', updateFocus);
            };
        }, containerRef);

        return () => ctx.revert();
    }, [filteredAircraft]);

    // HOVER EFFECTS CON TIMELINE
    useEffect(() => {
        cardsRef.current.forEach((card) => {
            if (!card) return;

            const imageEl = card.querySelector('.gallery-card-image');
            const infoEl = card.querySelector('.gallery-card-info');
            const overlayEl = card.querySelector('.gallery-card-overlay') as HTMLElement;

            if (!imageEl || !infoEl) return;

            const ctx = gsap.context(() => {
                const hoverTl = gsap.timeline({ paused: true });

                hoverTl
                    .to(imageEl, {
                        scale: 1.25,
                        filter: 'brightness(1.2)',
                        duration: 0.4,
                        ease: 'power2.out',
                    }, 0)
                    .to(infoEl, {
                        y: -12,
                        opacity: 1,
                        duration: 0.4,
                        ease: 'power2.out',
                    }, 0)
                    .to(overlayEl, {
                        opacity: 0.8,
                        duration: 0.3,
                    }, 0);

                card.addEventListener('mouseenter', () => hoverTl.play());
                card.addEventListener('mouseleave', () => hoverTl.reverse());

                return () => {
                    card.removeEventListener('mouseenter', () => hoverTl.play());
                    card.removeEventListener('mouseleave', () => hoverTl.reverse());
                };
            }, card);

            return () => ctx.revert();
        });
    }, [filteredAircraft]);

    const handleManufacturerChange = (mfr: 'Airbus' | 'Boeing' | 'All') => {
        gsap.to(scrollContainerRef.current, {
            opacity: 0.3,
            duration: 0.2,
            onComplete: () => {
                setSelectedManufacturer(mfr);
                gsap.to(scrollContainerRef.current, {
                    opacity: 1,
                    duration: 0.5,
                    ease: 'power2.inOut',
                });
                scrollContainerRef.current?.scrollTo({ left: 0, behavior: 'smooth' });
            },
        });
    };

    return (
        <section ref={containerRef} className={styles.section}>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h2 className={styles.title}>Galería de 36 Aeronaves</h2>
                    <p className={styles.subtitle}>Explora todas las aeronaves comerciales con sus imágenes reales</p>
                </div>

                <div className={styles.filterContainer}>
                    <span className={styles.filterLabel}>Fabricante:</span>
                    <div className={styles.filterButtons}>
                        {(['All', 'Airbus', 'Boeing'] as const).map((mfr) => (
                            <button
                                key={mfr}
                                className={`${styles.filterButton} ${selectedManufacturer === (mfr === 'All' ? 'All' : mfr) ? styles.active : ''}`}
                                onClick={() => handleManufacturerChange(mfr === 'All' ? 'All' : (mfr as 'Airbus' | 'Boeing'))}
                            >
                                {mfr === 'All' ? 'Todos' : mfr}
                            </button>
                        ))}
                    </div>
                </div>

                <div ref={scrollContainerRef} className={styles.scrollContainer}>
                    <div ref={galleryRef} className={styles.gallery}>
                        {filteredAircraft.map((aircraft, idx) => (
                            <div key={aircraft.name} ref={(el) => { cardsRef.current[idx] = el; }} className="gallery-card">
                                <AircraftCard aircraft={aircraft} />
                            </div>
                        ))}
                    </div>
                </div>

                {filteredAircraft[focusedIndex] && (
                    <div className={styles.focusedInfo}>
                        <div className={styles.focusedContent}>
                            <h3 className={styles.focusedName}>{filteredAircraft[focusedIndex].name}</h3>
                            <p className={styles.focusedEvent}>{filteredAircraft[focusedIndex].family}</p>
                            <p className={styles.focusedDescription}>{filteredAircraft[focusedIndex].description}</p>
                            {filteredAircraft[focusedIndex].passengers > 0 && (
                                <p className={styles.focusedSpecs}><strong>Pasajeros:</strong> {filteredAircraft[focusedIndex].passengers}</p>
                            )}
                            {filteredAircraft[focusedIndex].range > 0 && (
                                <p className={styles.focusedSpecs}><strong>Alcance:</strong> {filteredAircraft[focusedIndex].range.toLocaleString()} km</p>
                            )}
                        </div>
                    </div>
                )}

                <div className={styles.stats}>
                    <p className={styles.statText}>Mostrando <strong className={styles.statNumber}>{filteredAircraft.length}</strong> de <strong className={styles.statNumber}>36</strong> aeronaves</p>
                </div>
            </div>
        </section>
    );
};

const AircraftCard: React.FC<{ aircraft: typeof AIRCRAFT_IMAGES_DATA[0] }> = ({ aircraft }) => {
    return (
        <div className={styles.card}>
            <div className="gallery-card-image">
                <img src={aircraft.imagePath} alt={aircraft.name} className={styles.cardImage} loading="lazy" />
                <div className="gallery-card-overlay" style={{}} />
                <div className="gallery-card-light" style={{}} />
            </div>
            <div className="gallery-card-info">
                <h4 className={styles.cardName}>{aircraft.name}</h4>
                <p className={styles.cardFamily}>{aircraft.family}</p>
                <div className={styles.cardSpecs}>
                    {aircraft.passengers && aircraft.passengers > 0 && (
                        <span className={styles.spec}><strong>{aircraft.passengers}</strong> pas.</span>
                    )}
                    {aircraft.range && aircraft.range > 0 && (
                        <span className={styles.spec}><strong>{aircraft.range.toLocaleString()}</strong> km</span>
                    )}
                </div>
                <span className={styles.cardYear}>{aircraft.year}</span>
            </div>
        </div>
    );
};

export default HorizontalGallerySection;
