import React, { useRef, useEffect } from 'react';
import gsap from 'gsap';
// @ts-ignore
import { useComparisonSelector } from '@/features/comparison/hooks/useComparisonSelector';
// @ts-ignore
import { SelectorCard } from '@/features/comparison/components/SelectorCard/SelectorCard';
// @ts-ignore
import { LiquidGlassButton } from '@/shared/components/ui/LiquidGlassButton/LiquidGlassButton';
// @ts-ignore
import type { AircraftSelector } from '@/features/comparison/types/comparison.types';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import styles from './ComparisonSelectorPage.module.css';

export const ComparisonSelectorPage: React.FC = () => {
    // Ocultar footer en esta página
    useEffect(() => {
        const footer = document.querySelector('footer');
        if (footer) {
            footer.style.display = 'none';
        }

        // Cleanup: restaurar footer al salir
        return () => {
            const footer = document.querySelector('footer');
            if (footer) {
                footer.style.display = '';
            }
        };
    }, []);
    const {
        selectors,
        groupedAircraft,
        isLoading,
        error,
        canCompare,
        canAddSelector,
        selectAircraft,
        clearSelection,
        toggleDropdown,
        addSelector,
        removeSelector,
        handleCompare
    } = useComparisonSelector();

    const containerRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const gridRef = useRef<HTMLDivElement>(null);
    const actionsRef = useRef<HTMLDivElement>(null);

    // Animación de entrada de la página
    useEffect(() => {
        if (isLoading) return;

        const tl = gsap.timeline();

        // Fade in del contenedor
        gsap.set(containerRef.current, { opacity: 1 });

        // Secuencia de animaciones
        tl.fromTo(
            titleRef.current,
            { opacity: 0, y: -40, scale: 0.9 },
            { opacity: 1, y: 0, scale: 1, duration: 0.8, ease: 'power3.out' }
        )
            .fromTo(
                subtitleRef.current,
                { opacity: 0, y: -25 },
                { opacity: 1, y: 0, duration: 0.6, ease: 'power2.out' },
                '-=0.5'
            )
            .fromTo(
                gridRef.current,
                { opacity: 0, y: 30 },
                { opacity: 1, y: 0, duration: 0.7, ease: 'power2.out' },
                '-=0.4'
            )
            .fromTo(
                actionsRef.current,
                { opacity: 0, scale: 0.9 },
                { opacity: 1, scale: 1, duration: 0.6, ease: 'back.out(1.5)' },
                '-=0.3'
            );
    }, [isLoading]);

    if (isLoading) {
        return (
            <div className={styles.loadingContainer}>
                <div className={styles.loaderWrapper}>
                    <div className={styles.loader}>
                        <div className={styles.loaderPlane}>
                            <svg width="60" height="60" viewBox="0 0 24 24" fill="none">
                                <path
                                    d="M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z"
                                    fill="currentColor"
                                />
                            </svg>
                        </div>
                    </div>
                    <p className={styles.loadingText}>Cargando aeronaves...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className={styles.errorContainer}>
                <div className={styles.errorContent}>
                    <svg className={styles.errorIcon} width="80" height="80" viewBox="0 0 24 24" fill="none">
                        <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="2"/>
                        <path d="M12 8v4M12 16h.01" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
                    </svg>
                    <h2 className={styles.errorTitle}>Error al cargar</h2>
                    <p className={styles.errorMessage}>{error}</p>
                    <LiquidGlassButton
                        onClick={() => window.location.reload()}
                        variant="primary"
                    >
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
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
                        Reintentar
                    </LiquidGlassButton>
                </div>
            </div>
        );
    }

    return (
        <div className={styles.pageContainer} ref={containerRef}>
            <div className={styles.backgroundOverlay} />

            <div className={styles.contentWrapper}>
                {/* BACKBUTTON AQUÍ */}
                <BackButton to="/catalog" label="Regresar al catálogo" />

                {/* Header */}
                <header className={styles.pageHeader}>
                <h1 className={styles.title} ref={titleRef}>
                        Compara Aeronaves
                    </h1>
                    <p className={styles.subtitle} ref={subtitleRef}>
                        Selecciona entre <strong>2 y 3 aeronaves</strong> para comparar sus especificaciones técnicas en detalle
                    </p>
                </header>

                {/* Grid de Selectores con botón flotante */}
                <div className={styles.selectorsContainer}>
                    <div
                        className={styles.selectorsGrid}
                        ref={gridRef}
                        style={{
                            gridTemplateColumns: `repeat(${selectors.length}, 1fr)`
                        }}
                    >
                        {selectors.map((selector: AircraftSelector, index: number) => (
                            <SelectorCard
                                key={selector.id}
                                selector={selector}
                                index={index}
                                groupedAircraft={groupedAircraft}
                                canRemove={selectors.length > 2}
                                onSelect={selectAircraft}
                                onClear={clearSelection}
                                onToggle={toggleDropdown}
                                onRemove={removeSelector}
                            />
                        ))}

                        {/* Botón circular flotante para agregar */}
                        {canAddSelector && (
                            <button
                                className={styles.addCircleButton}
                                onClick={addSelector}
                                aria-label="Agregar aeronave"
                                title="Agregar tercera aeronave"
                            >
                                <svg width="32" height="32" viewBox="0 0 32 32" fill="none">
                                    <path
                                        d="M16 8V24M8 16H24"
                                        stroke="currentColor"
                                        strokeWidth="3"
                                        strokeLinecap="round"
                                    />
                                </svg>
                            </button>
                        )}
                    </div>
                </div>

                {/* Botón de Comparar centrado */}
                <div className={styles.actions} ref={actionsRef}>
                    <LiquidGlassButton
                        variant="primary"
                        onClick={handleCompare}
                        disabled={!canCompare}
                        className={styles.compareButton}
                        icon={
                            <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                                <path
                                    d="M7.5 15L12.5 10L7.5 5"
                                    stroke="currentColor"
                                    strokeWidth="2.5"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        }
                    >
                        Comparar Aeronaves
                    </LiquidGlassButton>

                    {/* Indicador de progreso */}
                    <div className={styles.progressIndicator}>
                        <div className={styles.progressDots}>
                            {[0, 1, 2].map((i: number) => (
                                <div
                                    key={i}
                                    className={`${styles.progressDot} ${
                                        selectors[i]?.selectedAircraft ? styles.active : ''
                                    }`}
                                />
                            ))}
                        </div>
                        <p className={styles.progressText}>
                            {selectors.filter((s: AircraftSelector) => s.selectedAircraft).length} de {selectors.length} seleccionadas
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ComparisonSelectorPage;
