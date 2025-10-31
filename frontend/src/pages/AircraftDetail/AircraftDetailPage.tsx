import { useParams } from 'react-router-dom';
import { useAircraftDetail } from '../../features/aircraft/hooks/useAircraftDetails';
import { HeroSection, OverviewSection, SpecsSection, EngineSection, FunFactsSection, SimilarSection } from './sections';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider';
import styles from './AircraftDetailPage.module.css';
import Footer from '../../shared/components/layout/Footer/Footer';

/**
 * Página principal de detalle de aeronave.
 * Soporta URLs por ID numérico (/aircraft/51) o slug textual (/aircraft/a350-1000).
 */
export function AircraftDetailPage() {
    const { identifier } = useParams<{ identifier: string }>();
    const { aircraft, isLoading, error } = useAircraftDetail(identifier);

    // ========== ESTADOS DE CARGA Y ERROR ==========

    if (isLoading) {
        return (
            <div className={styles.loadingContainer}>
                {/* Crystal Slider en loading */}
                <CrystalSlider position="top-right" />

                <div className={styles.loader}>
                    <div className={styles.spinner}></div>
                    <p>Loading aircraft details...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className={styles.errorContainer}>
                {/* Crystal Slider en error */}
                <CrystalSlider position="top-right" />

                <div className={styles.errorContent}>
                    <h1>❌ Aircraft Not Found</h1>
                    <p>{error}</p>
                    <a href="/aircraft" className={styles.backButton}>
                        ← Back to Catalog
                    </a>
                </div>
            </div>
        );
    }

    if (!aircraft) {
        return (
            <div className={styles.errorContainer}>
                {/* Crystal Slider en sin datos */}
                <CrystalSlider position="top-right" />

                <div className={styles.errorContent}>
                    <h1>❌ No Data Available</h1>
                    <p>The aircraft data could not be loaded.</p>
                    <a href="/aircraft" className={styles.backButton}>
                        ← Back to Catalog
                    </a>
                </div>
            </div>
        );
    }

    // ========== RENDER PRINCIPAL ==========

    return (
        <main className={styles.pageContainer}>
            {/* Crystal Slider - Controles de tema flotantes */}
            <CrystalSlider position="top-right" />

            {/* Hero Section con imagen y título */}
            <HeroSection aircraft={aircraft} />

            {/* Overview Section con 8 cards */}
            <OverviewSection aircraft={aircraft} />

            {/* Specs Section con tabs técnicos */}
            <SpecsSection aircraft={aircraft} />

            {/* Engine Section - Planta de poder */}
            <EngineSection aircraft={aircraft} />

            {/* Fun Facts Section - Datos Curiosos */}
            <FunFactsSection aircraft={aircraft} />

            {/* Similar Section con aeronaves similares */}
            <SimilarSection aircraftId={aircraft.id} />

            {/* Footer */}
            <Footer />
        </main>
    );
}

// Export named para consistencia con tu arquitectura
export { AircraftDetailPage as default };
