/**
 * @file TimelineSection.tsx
 * @description Línea de tiempo vertical DESLUMBRANTE con GSAP
 * Parallax, Stagger, Light Effects, Glassmorphism
 */

import React, { useState, useMemo, useEffect, useRef } from 'react';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import AIRCRAFT_IMAGES_DATA, { getAircraftByManufacturer } from '../../../../config/aircraftImagesData';
import styles from './TimelineSection.module.css';

gsap.registerPlugin(ScrollTrigger);

const TimelineSection: React.FC = () => {
    const [selectedManufacturer, setSelectedManufacturer] = useState<'Airbus' | 'Boeing' | 'All'>('All');
    const containerRef = useRef<HTMLDivElement>(null);
    const timelineRef = useRef<HTMLDivElement>(null);

    const filteredAircraft = useMemo(() => {
        let aircraft = [...AIRCRAFT_IMAGES_DATA];
        if (selectedManufacturer !== 'All') {
            aircraft = getAircraftByManufacturer(selectedManufacturer);
        }
        return aircraft.sort((a, b) => a.year - b.year);
    }, [selectedManufacturer]);

    // GSAP ANIMATIONS - PARALLAX + STAGGER
    useEffect(() => {
        if (!containerRef.current || !timelineRef.current) return;

        const ctx = gsap.context(() => {
            // GPU OPTIMIZATION
            gsap.set('.timeline-item', {
                force3D: true,
                backfaceVisibility: 'hidden',
                perspective: 2000,
                willChange: 'transform, opacity, filter',
            });

            gsap.set('.timeline-dot', { force3D: true });
            gsap.set('.timeline-image', { force3D: true });

            // ENTRADA CON STAGGER ESPECTACULAR
            const tl = gsap.timeline({
                scrollTrigger: {
                    trigger: timelineRef.current,
                    start: 'top 80%',
                    end: 'bottom 20%',
                    scrub: 1.5,
                    markers: false,
                },
            });

            // Stagger de items
            tl.from('.timeline-item', {
                opacity: 0,
                y: 100,
                rotateZ: 5,
                filter: 'blur(20px)',
                stagger: {
                    each: 0.15,
                    from: 'start',
                },
                duration: 1,
                ease: 'back.out(1.2)',
            }, 0);

            // Dots con efecto pop
            ScrollTrigger.batch('.timeline-dot', {
                onEnter: (batch) => {
                    gsap.to(batch, {
                        scale: 1,
                        opacity: 1,
                        boxShadow: '0 0 30px rgba(59, 159, 255, 0.6)',
                        stagger: {
                            each: 0.12,
                            from: 'start',
                        },
                        duration: 0.7,
                        ease: 'elastic.out(1, 0.5)',
                    });
                },
                start: 'top 85%',
            });

            // PARALLAX EN SCROLL
            gsap.to('.timeline-image', {
                yPercent: 20,
                scrollTrigger: {
                    trigger: timelineRef.current,
                    scrub: 1,
                },
            });

            // Línea de conexión animada
            if (timelineRef.current) {
                const line = timelineRef.current.querySelector('.timeline-line');
                if (line) {
                    ScrollTrigger.create({
                        trigger: timelineRef.current,
                        start: 'top center',
                        end: 'bottom center',
                        onUpdate: (self) => {
                            gsap.to(line, {
                                scaleY: Math.max(0.5, Math.min(1.5, self.getVelocity() / 300)),
                                overwrite: 'auto',
                            });
                        },
                    });
                }
            }
        }, containerRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach((st) => st.kill());
        };
    }, [filteredAircraft]);

    // HOVER EFFECTS AVANZADOS
    useEffect(() => {
        const items = document.querySelectorAll('.timeline-item');

        items.forEach((item) => {
            const ctx = gsap.context(() => {
                const hoverTl = gsap.timeline({ paused: true });
                const content = item.querySelector('.timeline-content');
                const image = item.querySelector('.timeline-image');
                const dot = item.querySelector('.timeline-dot');

                hoverTl
                    .to(dot, {
                        scale: 1.4,
                        boxShadow: '0 0 50px rgba(59, 159, 255, 0.8)',
                        duration: 0.3,
                    }, 0)
                    .to(image, {
                        scale: 1.1,
                        filter: 'brightness(1.2)',
                        duration: 0.3,
                    }, 0)
                    .to(content, {
                        x: 12,
                        boxShadow: '0 20px 60px rgba(59, 159, 255, 0.3)',
                        duration: 0.3,
                    }, 0);

                item.addEventListener('mouseenter', () => hoverTl.play());
                item.addEventListener('mouseleave', () => hoverTl.reverse());

                return () => {
                    item.removeEventListener('mouseenter', () => hoverTl.play());
                    item.removeEventListener('mouseleave', () => hoverTl.reverse());
                };
            }, item);

            return () => ctx.revert();
        });
    }, [filteredAircraft]);

    const handleManufacturerChange = (mfr: 'Airbus' | 'Boeing' | 'All') => {
        gsap.to(timelineRef.current, {
            opacity: 0.3,
            duration: 0.2,
            onComplete: () => {
                setSelectedManufacturer(mfr);
                gsap.to(timelineRef.current, {
                    opacity: 1,
                    duration: 0.5,
                    ease: 'power2.inOut',
                });
            },
        });
    };

    return (
        <section ref={containerRef} className={styles.section}>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h2 className={styles.title}>Línea de Tiempo - 36 Aeronaves</h2>
                    <p className={styles.subtitle}>Evolución histórica de la aviación comercial</p>
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

                <div ref={timelineRef} className={styles.timeline}>
                    <div className={styles.timelineLine} />
                    {filteredAircraft.map((aircraft) => (
                        <TimelineItem key={aircraft.name} aircraft={aircraft} />
                    ))}
                </div>

                <div className={styles.stats}>
                    <div className={styles.statItem}>
                        <span className={styles.statNumber}>{filteredAircraft.length}</span>
                        <span className={styles.statLabel}>Aeronaves</span>
                    </div>
                    <div className={styles.statItem}>
                        <span className={styles.statNumber}>{filteredAircraft[0]?.year}</span>
                        <span className={styles.statLabel}>Primer Vuelo</span>
                    </div>
                    <div className={styles.statItem}>
                        <span className={styles.statNumber}>{filteredAircraft[filteredAircraft.length - 1]?.year}</span>
                        <span className={styles.statLabel}>Más Reciente</span>
                    </div>
                </div>
            </div>
        </section>
    );
};

interface TimelineItemProps {
    aircraft: (typeof AIRCRAFT_IMAGES_DATA)[0];
}

const TimelineItem: React.FC<TimelineItemProps> = ({ aircraft }) => {
    const isAirbus = aircraft.manufacturer === 'Airbus';

    return (
        <div className={`${styles.timelineItem} timeline-item`}>
            <div className={`${styles.timelineDot} timeline-dot ${isAirbus ? styles.airbus : styles.boeing}`} />

            <div className={`${styles.itemContent} timeline-content`}>
                <div className={styles.itemHeader}>
                    <h3 className={styles.itemTitle}>{aircraft.name}</h3>
                    <span className={styles.itemYear}>{aircraft.year}</span>
                </div>

                {aircraft.imagePath && (
                    <img
                        src={aircraft.imagePath}
                        alt={aircraft.name}
                        className={`${styles.itemImage} timeline-image`}
                        loading="lazy"
                    />
                )}

                <p className={styles.itemDescription}>{aircraft.description}</p>

                <div className={styles.itemSpecs}>
                    {aircraft.passengers && aircraft.passengers > 0 && (
                        <span className={styles.spec}>
                         <strong>{aircraft.passengers}</strong> pasajeros
                          </span>
                    )}
                    {aircraft.range && aircraft.range > 0 && (
                        <span className={styles.spec}>
                             <strong>{aircraft.range.toLocaleString()}</strong> km alcance
                         </span>
                    )}
                    {aircraft.engines && aircraft.engines > 0 && (
                        <span className={styles.spec}>
                        <strong>{aircraft.engines}</strong> motores
                        </span>
                    )}
                </div>

                <span className={`${styles.badge} ${isAirbus ? styles.badgeAirbus : styles.badgeBoeing}`}>
                    {aircraft.manufacturer}
                </span>
            </div>
        </div>
    );
};

export default TimelineSection;
