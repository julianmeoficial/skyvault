/**
 * @file FamiliesGrid.tsx
 * @description Grid de cards de familias con nuevo diseño minimalista
 * Cards estilizadas sin detalles expandibles, animaciones GSAP 120fps optimizadas
 */

import React, { useEffect, useRef } from 'react';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { useFamilies } from '../../../../features/families/hooks';
import type { FamilyDto } from '../../../../features/families/types';
import FamilyCard from '../../../../features/families/components/FamilyCard';
import styles from './FamiliesGrid.module.css';

gsap.registerPlugin(ScrollTrigger);

interface FamiliesGridProps {
    manufacturer?: 'Airbus' | 'Boeing';
    limit?: number;
}

const FamiliesGrid: React.FC<FamiliesGridProps> = ({ manufacturer, limit }) => {
    const containerRef = useRef<HTMLDivElement>(null);
    const gridRef = useRef<HTMLDivElement>(null);
    const { families, loading, error, fetchFamilies } = useFamilies(0, limit || 20);

    useEffect(() => {
        if (containerRef.current && gridRef.current && !loading) {
            const ctx = gsap.context(() => {
                // 120 fps optimized animations
                gsap.set('.family-card-item', {
                    force3D: true,
                    backfaceVisibility: 'hidden',
                    perspective: 1200,
                    willChange: 'transform, opacity',
                });

                // Stagger animation on scroll with improved easing
                ScrollTrigger.batch('.family-card-item', {
                    onEnter: (batch) => {
                        gsap.to(batch, {
                            opacity: 1,
                            y: 0,
                            scale: 1,
                            stagger: {
                                each: 0.1,
                                from: 'start',
                            },
                            duration: 0.7,
                            ease: 'cubic.out',
                        });
                    },
                    start: 'top 85%',
                    onLeave: () => {
                        gsap.set('.family-card-item', {
                            willChange: 'auto',
                        });
                    },
                });
            }, containerRef);

            return () => {
                ctx.revert();
                ScrollTrigger.getAll().forEach((st) => st.kill());
            };
        }
    }, [loading, families]);

    useEffect(() => {
        if (manufacturer) {
            const manufacturerId = manufacturer === 'Airbus' ? 1 : 2;
            void fetchFamilies({ manufacturerId });
        }
    }, [manufacturer, fetchFamilies]);

    let displayedFamilies: FamilyDto[] = families;
    if (limit) {
        displayedFamilies = families.slice(0, limit);
    }

    return (
        <section ref={containerRef} className={styles.section}>
            <div className={styles.container}>
                {/* Header */}
                <div className={styles.header}>
                    <h2 className={styles.title}>
                        {manufacturer ? `Familias ${manufacturer}` : 'Todas las Familias'}
                    </h2>
                    <p className={styles.subtitle}>
                        Explora cada familia de aeronaves con información completa y datos históricos
                    </p>
                </div>

                {/* Error state */}
                {error && (
                    <div className={styles.error}>
                        <div className={styles.errorIcon}>⚠️</div>
                        <p className={styles.errorText}>Error al cargar familias: {error}</p>
                        <button
                            onClick={() => void fetchFamilies()}
                            className={styles.retryButton}
                        >
                            Intentar de nuevo
                        </button>
                    </div>
                )}

                {/* Loading state */}
                {loading && (
                    <div className={styles.loading}>
                        {[...Array(6)].map((_, i) => (
                            <div key={i} className={styles.skeletonCard}>
                                <div className={styles.skeletonHeader} />
                                <div className={styles.skeletonBody} />
                                <div className={styles.skeletonFooter} />
                            </div>
                        ))}
                    </div>
                )}

                {/* Grid */}
                {!loading && displayedFamilies.length > 0 && (
                    <div ref={gridRef} className={styles.grid}>
                        {displayedFamilies.map((family) => (
                            <div key={family.id} className={`${styles.gridItem} family-card-item`}>
                                <FamilyCard family={family} />
                            </div>
                        ))}
                    </div>
                )}

                {/* Empty state */}
                {!loading && displayedFamilies.length === 0 && (
                    <div className={styles.empty}>
                        <div className={styles.emptyIcon}>📭</div>
                        <p>No hay familias disponibles</p>
                    </div>
                )}
            </div>
        </section>
    );
};

export default FamiliesGrid;
