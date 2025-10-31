import React, { useRef, useEffect } from 'react';
import gsap from 'gsap';
import { AircraftCard } from '../AircraftCard';
import type { AircraftGridProps } from '../../types/aircraft.types';
import styles from './AircraftGrid.module.css';

export const AircraftGrid: React.FC<AircraftGridProps> = ({
                                                              aircraft,
                                                              isLoading = false,
                                                              onCompare,
                                                              onLoadMore,
                                                              hasMore = false,
                                                          }) => {
    const gridRef = useRef<HTMLDivElement>(null);
    const observerRef = useRef<IntersectionObserver | null>(null);
    const loadMoreRef = useRef<HTMLDivElement>(null);

    // Infinite scroll setup
    useEffect(() => {
        if (!loadMoreRef.current || !onLoadMore || !hasMore) return;

        observerRef.current = new IntersectionObserver(
            (entries) => {
                if (entries[0].isIntersecting && !isLoading) {
                    onLoadMore();
                }
            },
            { threshold: 0.1 }
        );

        observerRef.current.observe(loadMoreRef.current);

        return () => {
            if (observerRef.current) {
                observerRef.current.disconnect();
            }
        };
    }, [onLoadMore, hasMore, isLoading]);

    // Grid entrance animation
    useEffect(() => {
        if (!gridRef.current || aircraft.length === 0) return;

        const cards = gridRef.current.querySelectorAll(`.${styles.gridItem}`);

        gsap.fromTo(
            cards,
            { opacity: 0, y: 20 },
            {
                opacity: 1,
                y: 0,
                duration: 0.4,
                stagger: 0.08,
                ease: 'power2.out',
            }
        );
    }, [aircraft.length]);

    if (aircraft.length === 0 && !isLoading) {
        return (
            <div className={styles.emptyState}>
                <svg width="120" height="120" viewBox="0 0 120 120" fill="none">
                    <path
                        d="M60 20C37.9 20 20 37.9 20 60C20 82.1 37.9 100 60 100C82.1 100 100 82.1 100 60C100 37.9 82.1 20 60 20ZM60 90C43.45 90 30 76.55 30 60C30 43.45 43.45 30 60 30C76.55 30 90 43.45 90 60C90 76.55 76.55 90 60 90Z"
                        fill="#D1D5DB"
                    />
                    <path
                        d="M45 55H55V75H45V55ZM65 55H75V75H65V55Z"
                        fill="#D1D5DB"
                    />
                </svg>
                <h3 className={styles.emptyTitle}>No se encontraron aeronaves</h3>
                <p className={styles.emptyDescription}>
                    Intenta ajustar los filtros o realizar una nueva búsqueda
                </p>
            </div>
        );
    }

    return (
        <div className={styles.container}>
            <div className={styles.grid} ref={gridRef}>
                {aircraft.map((item, index) => (
                    <div key={item.id} className={styles.gridItem}>
                        <AircraftCard
                            aircraft={item}
                            onCompare={onCompare}
                            index={index}
                        />
                    </div>
                ))}
            </div>

            {/* Infinite scroll trigger */}
            {hasMore && (
                <div ref={loadMoreRef} className={styles.loadMore}>
                    {isLoading && (
                        <div className={styles.loader}>
                            <div className={styles.spinner} />
                            <p>Cargando más aeronaves...</p>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};
