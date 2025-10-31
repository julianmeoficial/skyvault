import React, { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { useComparison } from '../../features/comparison/hooks/useComparison';
import { ComparisonCard } from '../../features/comparison/components/ComparisonCard';
import { ComparisonGrid } from '../../features/comparison/components/ComparisonGrid';
import { EmptyComparisonCard } from '../../features/comparison/components/EmptyComparisonCard';
import { comparisonService } from '../../features/comparison/services/comparisonService';
import type { GroupedAircraft, AircraftOption, AircraftSelector } from '../../features/comparison/types/comparison.types';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import styles from './ComparePage.module.css';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider';
import Footer from "../../shared/components/layout/Footer";

export const ComparePage: React.FC = () => {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const idsParam = searchParams.get('ids');
    const initialIds = idsParam ? idsParam.split(',') : [];

    const { items, isLoading, error, removeItem, canAddMore } = useComparison(initialIds);

    const [groupedAircraft, setGroupedAircraft] = useState<GroupedAircraft>({
        Airbus: [],
        Boeing: []
    });

    const [selectors, setSelectors] = useState<AircraftSelector[]>(() => [
        { id: `selector-${Date.now()}-0`, selectedAircraft: null, isOpen: false },
        { id: `selector-${Date.now()}-1`, selectedAircraft: null, isOpen: false }
    ]);

    useEffect(() => {
        document.title = 'Comparar Aeronaves - SkyVault';
    }, []);

    useEffect(() => {
        const loadAircraft = async () => {
            try {
                const aircraft = await comparisonService.getGroupedAircraft();
                setGroupedAircraft(aircraft);
            } catch (error) {
                console.error('Error loading aircraft:', error);
            }
        };
        loadAircraft();
    }, []);

    const handleAddAircraft = (aircraft: AircraftOption) => {
        const newIds = [...items.map(i => i.id), aircraft.id];
        navigate(`/compare?ids=${newIds.join(',')}`);
    };

    const handleSelectAircraft = (selectorId: string, aircraft: AircraftOption) => {
        setSelectors(prev => prev.map(selector =>
            selector.id === selectorId
                ? { ...selector, selectedAircraft: aircraft, isOpen: false }
                : selector
        ));
    };

    const handleAddSelector = () => {
        if (selectors.length < 3) {
            setSelectors(prev => [
                ...prev,
                {
                    id: `selector-${Date.now()}-${Math.random()}`,
                    selectedAircraft: null,
                    isOpen: false
                }
            ]);
        }
    };

    const handleCompareFromSelector = () => {
        const selectedIds = selectors
            .filter(s => s.selectedAircraft !== null)
            .map(s => s.selectedAircraft!.id);

        if (selectedIds.length >= 2) {
            navigate(`/compare?ids=${selectedIds.join(',')}`);
        }
    };

    const canCompare = selectors.filter(s => s.selectedAircraft !== null).length >= 2;

    const handleRemoveSelector = (selectorId: string) => {
        if (selectors.length > 2) {
            setSelectors(prev => prev.filter(s => s.id !== selectorId));
        }
    };


    return (
        <>
            <div className={styles.page}>
                {/* Crystal Slider - Controles de tema flotantes */}
                <CrystalSlider position="top-right" />

                <div className={styles.container}>
                    {/* AGREGAR BACKBUTTON */}
                    <BackButton to="/catalog" label="Regresar al catálogo" />

                    <header className={styles.header}>
                        <h1 className={styles.title}>Comparador de Aeronaves</h1>
                        <p className={styles.subtitle}>Compara hasta 3 modelos lado a lado</p>
                    </header>

                    {isLoading && (
                        <div className={styles.loading}>
                            <div className={styles.spinner}></div>
                            <p>Cargando comparación...</p>
                        </div>
                    )}

                    {error && (
                        <div className={styles.error}>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path
                                    d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                    strokeLinecap="round"
                                />
                            </svg>
                            <p>{error}</p>
                        </div>
                    )}

                    {!isLoading && items.length === 0 && (
                        <div className={styles.selectorMode}>
                            <div className={styles.selectorGrid}>
                                {selectors.map((selector, index) => (
                                    <EmptyComparisonCard
                                        key={selector.id}
                                        groupedAircraft={groupedAircraft}
                                        onSelect={(aircraft) => handleSelectAircraft(selector.id, aircraft)}
                                        onRemove={() => handleRemoveSelector(selector.id)}
                                        canRemove={selectors.length > 2}
                                        index={index}
                                    />
                                ))}

                                {selectors.length < 3 && (
                                    <button
                                        className={styles.addSelectorButton}
                                        onClick={handleAddSelector}
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

                            <div className={styles.compareButtonContainer}>
                                <button
                                    className={`${styles.compareButton} ${canCompare ? styles.active : ''}`}
                                    onClick={handleCompareFromSelector}
                                    disabled={!canCompare}
                                >
                                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                                        <path
                                            d="M7.5 15L12.5 10L7.5 5"
                                            stroke="currentColor"
                                            strokeWidth="2.5"
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                        />
                                    </svg>
                                    Comparar Aeronaves
                                </button>
                                <p className={styles.selectionCount}>
                                    {selectors.filter(s => s.selectedAircraft).length} de {selectors.length} seleccionadas
                                </p>
                            </div>
                        </div>
                    )}

                    {!isLoading && items.length > 0 && (
                        <>
                            <div className={styles.cards}>
                                {items.map((item, index) => (
                                    <ComparisonCard
                                        key={item.id}
                                        item={item}
                                        onRemove={removeItem}
                                        index={index}
                                    />
                                ))}

                                {canAddMore && (
                                    <EmptyComparisonCard
                                        groupedAircraft={groupedAircraft}
                                        onSelect={handleAddAircraft}
                                        index={items.length}
                                    />
                                )}
                            </div>

                            <ComparisonGrid items={items} />
                        </>
                    )}
                </div>
                <Footer />
            </div>
        </>
    );
};
