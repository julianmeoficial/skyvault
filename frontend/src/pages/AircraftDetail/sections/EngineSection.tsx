import type { AircraftDetailDto } from '../../../features/aircraft/types/aircraft.types';
import styles from './EngineSection.module.css';

interface EngineSectionProps {
    aircraft: AircraftDetailDto;
}

export function EngineSection({ aircraft }: EngineSectionProps) {
    const specs = aircraft.specifications;

    // Helper para formatear números con separador de miles (puntos)
    const formatNumber = (value: string | number | undefined | null): string => {
        if (value === undefined || value === null) return 'N/A';

        // Si es string, parsearlo primero (eliminar comas si existen)
        const num = typeof value === 'string' ? parseFloat(value.replace(/,/g, '')) : value;

        if (isNaN(num)) return 'N/A';

        // Formatear con separador de miles (puntos estilo europeo)
        return num.toLocaleString('es-ES', { maximumFractionDigits: 0 });
    };

    // Helper específico para texto (sin parsear)
    const formatText = (value: string | number | undefined | null): string => {
        if (value === undefined || value === null) return 'N/A';
        return String(value);
    };

    return (
        <section className={styles.engineSection}>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h2 className={styles.title}>Planta de Poder</h2>
                    <p className={styles.subtitle}>Propulsión y combustible</p>
                </div>

                <div className={styles.grid}>
                    {/* Card 1: Engine Count */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Motores</span>
                            <span className={styles.value}>{formatText(specs?.engineCount)}x</span>
                            <span className={styles.sublabel}>cantidad</span>
                        </div>
                    </div>

                    {/* Card 2: Engine Manufacturer */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Fabricante</span>
                            <span className={styles.value}>{formatText(specs?.engineManufacturer)}</span>
                            <span className={styles.sublabel}>motor</span>
                        </div>
                    </div>

                    {/* Card 3: Engine Model */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Modelo</span>
                            <span className={styles.value}>{formatText(specs?.engineModel)}</span>
                            <span className={styles.sublabel}>designación</span>
                        </div>
                    </div>

                    {/* Card 4: Engine Thrust (per engine) */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Empuje por Motor</span>
                            <span className={styles.value}>{formatNumber(specs?.engineThrustN)}</span>
                            <span className={styles.sublabel}>Newton</span>
                        </div>
                    </div>

                    {/* Card 5: Total Thrust */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Empuje Total</span>
                            <span className={styles.value}>{formatNumber(specs?.totalThrustN)}</span>
                            <span className={styles.sublabel}>Newton</span>
                        </div>
                    </div>

                    {/* Card 6: Fuel Capacity */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Capacidad Combustible</span>
                            <span className={styles.value}>{formatNumber(specs?.fuelCapacityLiters)}</span>
                            <span className={styles.sublabel}>litros</span>
                        </div>
                    </div>

                    {/* Card 7: Fuel Consumption */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Consumo Combustible</span>
                            <span className={styles.value}>{formatNumber(specs?.fuelConsumptionLph)}</span>
                            <span className={styles.sublabel}>litros/hora</span>
                        </div>
                    </div>

                    {/* Card 8: Certification Authorities */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Certificaciones</span>
                            <span className={styles.value}>{formatText(specs?.certificationAuthorities)}</span>
                            <span className={styles.sublabel}>autoridades</span>
                        </div>
                    </div>

                    {/* Card 9: Noise Level */}
                    <div className={styles.card}>
                        <div className={styles.cardGlow} />
                        <div className={styles.cardContent}>
                            <span className={styles.label}>Nivel de Ruido</span>
                            <span className={styles.value}>{specs?.noiseLevelDb ? `${formatText(specs.noiseLevelDb)} dB` : 'N/A'}</span>
                            <span className={styles.sublabel}>acústico</span>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}
