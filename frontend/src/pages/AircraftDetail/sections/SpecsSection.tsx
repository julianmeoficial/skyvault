import { useState } from 'react';
import { SpecCard } from '../components/SpecCard';
import type { AircraftDetailDto } from '../../../features/aircraft/types/aircraft.types';
import styles from './SpecsSection.module.css';

interface SpecsSectionProps {
    aircraft: AircraftDetailDto;
}

type TabType = 'dimensions' | 'weights' | 'performance' | 'cabin';

export function SpecsSection({ aircraft }: SpecsSectionProps) {
    const [activeTab, setActiveTab] = useState<TabType>('dimensions');
    const specs = aircraft.specifications;

    const formatNumber = (num: number | string | undefined | null): string => {
        if (num === undefined || num === null) return 'N/A';
        const parsed = typeof num === 'string' ? parseFloat(num.replace(/,/g, '')) : num;
        if (isNaN(parsed)) return 'N/A';
        return parsed.toLocaleString('es-ES', { maximumFractionDigits: 0 });
    };

    const formatDecimal = (num: number | string | undefined | null, decimals: number = 2): string => {
        if (num === undefined || num === null) return 'N/A';
        const parsed = typeof num === 'string' ? parseFloat(num.replace(/,/g, '')) : num;
        if (isNaN(parsed)) return 'N/A';
        return parsed.toLocaleString('es-ES', {
            minimumFractionDigits: decimals,
            maximumFractionDigits: decimals
        });
    };

    const tabs = [
        { id: 'dimensions' as TabType, label: 'Dimensiones' },
        { id: 'weights' as TabType, label: 'Pesos' },
        { id: 'performance' as TabType, label: 'Performance' },
        { id: 'cabin' as TabType, label: 'Cabina' }
    ];

    return (
        <section className={styles.specsSection}>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h2 className={styles.title}>Especificaciones Técnicas</h2>
                    <p className={styles.subtitle}>Datos detallados de ingeniería</p>
                </div>

                {/* Tabs Navigation */}
                <div className={styles.tabsContainer}>
                    {tabs.map(tab => (
                        <button
                            key={tab.id}
                            className={`${styles.tab} ${activeTab === tab.id ? styles.tabActive : ''}`}
                            onClick={() => setActiveTab(tab.id)}
                        >
                            {tab.label}
                        </button>
                    ))}
                </div>

                {/* Tab Content */}
                <div className={styles.tabContent}>
                    {activeTab === 'dimensions' && (
                        <div className={styles.grid}>
                            <SpecCard label="Longitud" value={formatDecimal(specs?.lengthM, 1)} unit="m" />
                            <SpecCard label="Envergadura" value={formatDecimal(specs?.wingspanM, 1)} unit="m" />
                            <SpecCard label="Altura" value={formatDecimal(specs?.heightM, 1)} unit="m" />
                            <SpecCard label="Área Alar" value={formatDecimal(specs?.wingAreaM2, 2)} unit="m²" />
                            <SpecCard label="Cabina Longitud" value={formatDecimal(specs?.cabinLengthM, 2)} unit="m" />
                            <SpecCard label="Cabina Ancho" value={formatDecimal(specs?.cabinWidthM, 2)} unit="m" />
                            <SpecCard label="Cabina Altura" value={formatDecimal(specs?.cabinHeightM, 2)} unit="m" />
                        </div>
                    )}

                    {activeTab === 'weights' && (
                        <div className={styles.grid}>
                            <SpecCard label="Peso Vacío" value={formatNumber(specs?.emptyWeightKg)} unit="kg" />
                            <SpecCard label="MTOW" value={formatNumber(specs?.maxTakeoffWeightKg)} unit="kg" />
                            <SpecCard label="Peso Aterrizaje" value={formatNumber(specs?.maxLandingWeightKg)} unit="kg" />
                            <SpecCard label="Carga Útil" value={formatNumber(specs?.maxPayloadKg)} unit="kg" />
                        </div>
                    )}

                    {activeTab === 'performance' && (
                        <div className={styles.grid}>
                            <SpecCard label="Velocidad Máx" value={formatNumber(specs?.maxSpeedKmh)} unit="km/h" />
                            <SpecCard label="Velocidad Crucero" value={formatNumber(aircraft.cruiseSpeedKnots)} unit="kts" />
                            <SpecCard label="Techo de Servicio" value={formatNumber(specs?.serviceCeilingM)} unit="m" />
                            <SpecCard label="Alcance Máx" value={formatNumber(aircraft.rangeKm)} unit="km" />
                            <SpecCard label="Alcance Max Pax" value={formatNumber(specs?.rangeWithMaxPaxKm)} unit="km" />
                            <SpecCard label="Alcance Max Carga" value={formatNumber(specs?.rangeWithMaxPayloadKm)} unit="km" />
                            <SpecCard label="Distancia Despegue" value={formatNumber(specs?.takeoffDistanceM)} unit="m" />
                            <SpecCard label="Distancia Aterrizaje" value={formatNumber(specs?.landingDistanceM)} unit="m" />
                        </div>
                    )}

                    {activeTab === 'cabin' && (
                        <div className={styles.grid}>
                            <SpecCard label="Primera Clase" value={formatNumber(specs?.firstClassSeats)} unit="asientos" />
                            <SpecCard label="Clase Ejecutiva" value={formatNumber(specs?.businessClassSeats)} unit="asientos" />
                            <SpecCard label="Clase Económica" value={formatNumber(specs?.economyClassSeats)} unit="asientos" />
                            <SpecCard label="Pitch Económica" value={formatDecimal(specs?.seatPitchEconomyCm, 0)} unit="cm" />
                            <SpecCard label="Ancho Asiento" value={formatDecimal(specs?.seatWidthEconomyCm, 1)} unit="cm" />
                            <SpecCard label="Volumen Cargo" value={formatDecimal(specs?.cargoVolumeM3, 2)} unit="m³" />
                        </div>
                    )}
                </div>
            </div>
        </section>
    );
}
