import React, { useState, useCallback, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAircraftCatalog } from '../../features/aircraft/hooks/useAircraftCatalog';
import { AircraftGrid } from '../../features/aircraft/components/AircraftGrid';
import { LiquidGlassButton } from '../../shared/components/ui/LiquidGlassButton';
import { EmptyState } from '../../shared/components/ui/EmptyState';
import { GlassSearchBar } from '../../shared/components/ui/GlassSearchBar/GlassSearchBar';
import { CompareSelectionModal } from '../../shared/components/ui/CompareSelectionModal';
import Header from '../../shared/components/layout/Header/Header';
import Footer from '../../shared/components/layout/Footer/Footer';
import type { SortOption } from '../../features/aircraft/types/aircraft.types';
import styles from './AircraftCatalog.module.css';

export const AircraftCatalog: React.FC = () => {
    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedManufacturer, setSelectedManufacturer] = useState<number | undefined>();
    const [showCompareModal, setShowCompareModal] = useState(false);
    const [firstSelectedId, setFirstSelectedId] = useState<number | null>(null);

    const {
        aircraft,
        manufacturers,
        isLoading,
        error,
        totalElements,
        hasMore,
        sort,
        setFilters,
        setSort,
        loadMore,
        reset,
    } = useAircraftCatalog();

    // ========== OPCIONES DE ORDENAMIENTO ==========
    const sortOptions = useMemo(() => [
        { label: 'Nombre A-Z', value: 'name-asc' },
        { label: 'Nombre Z-A', value: 'name-desc' },
        { label: 'Más recientes', value: 'introductionYear-desc' },
        { label: 'Más antiguos', value: 'introductionYear-asc' },
        { label: 'Mayor capacidad', value: 'typicalPassengers-desc' },
        { label: 'Mayor alcance', value: 'rangeKm-desc' },
    ], []);

    // ========== HANDLE SEARCH ==========
    const handleSearch = useCallback((value: string) => {
        setSearchTerm(value);
        setFilters(prev => ({
            ...prev,
            searchTerm: value || undefined
        }));
    }, [setFilters]);

    // ========== HANDLE MANUFACTURER FILTER ==========
    const handleManufacturerChange = useCallback((value: string) => {
        const manufacturerId = value ? Number(value) : undefined;
        setSelectedManufacturer(manufacturerId);
        setFilters(prev => ({
            ...prev,
            manufacturerId
        }));
    }, [setFilters]);

    // ========== HANDLE SORT CHANGE ==========
    const handleSortChange = useCallback((value: string) => {
        console.log('🔄 Sort changed to:', value);

        let field: SortOption['field'];
        let direction: 'asc' | 'desc';

        switch (value) {
            case 'name-asc':
                field = 'name';
                direction = 'asc';
                break;
            case 'name-desc':
                field = 'name';
                direction = 'desc';
                break;
            case 'introductionYear-desc':
                field = 'introductionYear';
                direction = 'desc';
                break;
            case 'introductionYear-asc':
                field = 'introductionYear';
                direction = 'asc';
                break;
            case 'typicalPassengers-desc':
                field = 'maxPassengers';
                direction = 'desc';
                break;
            case 'rangeKm-desc':
                field = 'rangeKm';
                direction = 'desc';
                break;
            default:
                field = 'name';
                direction = 'asc';
        }

        console.log('✅ Setting sort:', { field, direction });
        setSort({ field, direction });
    }, [setSort]);

    // ========== HANDLE COMPARE (ABRE MODAL) ==========
    const handleCompare = useCallback((aircraftId: number) => {
        setFirstSelectedId(aircraftId);
        setShowCompareModal(true);
    }, []);

    // ========== HANDLE MODAL CONFIRM (NAVEGA A COMPARACIÓN) ==========
    const handleModalCompare = useCallback((secondAircraftId: number) => {
        if (firstSelectedId) {
            navigate(`/compare?ids=${firstSelectedId},${secondAircraftId}`);
            setShowCompareModal(false);
            setFirstSelectedId(null);
        }
    }, [firstSelectedId, navigate]);

    // ========== HANDLE MODAL CLOSE ==========
    const handleModalClose = useCallback(() => {
        setShowCompareModal(false);
        setFirstSelectedId(null);
    }, []);

    // ========== HANDLE RESET ==========
    const handleReset = useCallback(() => {
        setSearchTerm('');
        setSelectedManufacturer(undefined);
        reset();
    }, [reset]);

    const sortValue = `${sort.field === 'maxPassengers' ? 'typicalPassengers' : sort.field}-${sort.direction}`;

    return (
        <>
            {/* ========== HEADER ========== */}
            <Header />

            <div className={styles.page}>
                {/* Hero Header Mejorado */}
                <header className={styles.hero}>
                    <div className={styles.heroBackground}>
                        <div className={styles.heroGradient} />
                        <div className={styles.heroPattern} />
                    </div>
                    <div className={styles.heroContent}>
                        <h1 className={styles.heroTitle}>Catálogo de Aeronaves</h1>
                        <p className={styles.heroSubtitle}>
                            Explora y compara <span className={styles.heroHighlight}>{totalElements}</span> modelos comerciales
                        </p>
                    </div>
                </header>

                {/* ========== GLASS SEARCH BAR ========== */}
                <GlassSearchBar
                    value={searchTerm}
                    onSearch={handleSearch}
                    placeholder="Buscar aeronave... (ej: A350, 777, Airbus)"
                    manufacturerValue={selectedManufacturer?.toString() || ''}
                    onManufacturerChange={handleManufacturerChange}
                    manufacturers={manufacturers}
                    sortValue={sortValue}
                    onSortChange={handleSortChange}
                    sortOptions={sortOptions}
                />

                {/* Results Info */}
                <div className={styles.resultsInfo}>
                    <div className={styles.resultsContent}>
                        <p className={styles.resultsText}>
                            {isLoading ? 'Cargando...' : `${totalElements} aeronaves encontradas`}
                        </p>

                        {(searchTerm || selectedManufacturer) && (
                            <LiquidGlassButton onClick={handleReset} variant="secondary">
                                Limpiar filtros
                            </LiquidGlassButton>
                        )}
                    </div>
                </div>

                {/* Main Content */}
                <main className={styles.main}>
                    {error ? (
                        <EmptyState
                            type="error"
                            errorMessage={error}
                            onRetry={() => window.location.reload()}
                            onReset={handleReset}
                        />
                    ) : aircraft.length === 0 && !isLoading ? (
                        <EmptyState
                            type="no-results"
                            searchTerm={searchTerm}
                            onReset={handleReset}
                        />
                    ) : (
                        <AircraftGrid
                            aircraft={aircraft}
                            isLoading={isLoading}
                            onCompare={handleCompare}
                            onLoadMore={loadMore}
                            hasMore={hasMore}
                        />
                    )}
                </main>
                {/* Footer */}
                <Footer />
            </div>

            {/* ========== MODAL DE SELECCIÓN DE COMPARACIÓN ========== */}
            <CompareSelectionModal
                isOpen={showCompareModal}
                onClose={handleModalClose}
                onConfirm={handleModalCompare}
                excludeAircraftId={firstSelectedId || undefined}
            />
        </>
    );
};
