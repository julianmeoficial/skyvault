import React from 'react';
import type { ComparisonItem } from '../../types/comparison.types';
import { ComparisonRow } from '../ComparisonRow';
import styles from './ComparisonSection.module.css';

interface PerformanceSectionProps {
    items: ComparisonItem[];
    aircraftColors: string[];
}

export const PerformanceSection: React.FC<PerformanceSectionProps> = ({ items, aircraftColors }) => {
    const specs = [
        {
            label: 'Alcance',
            unit: 'km',
            getValue: (item: ComparisonItem) => item.rangeKm || item.range_km,
            findBest: true,
        },
        {
            label: 'Velocidad de crucero',
            unit: 'knots',
            getValue: (item: ComparisonItem) => item.cruiseSpeedKnots || item.cruise_speed_knots,
            findBest: true,
        },
    ];

    return (
        <div className={styles.section}>
            <div className={styles.sectionHeader}>
                <h3 className={styles.sectionTitle}>RENDIMIENTO</h3>
            </div>
            <div className={styles.sectionContent}>
                {specs.map((spec) => (
                    <ComparisonRow
                        key={spec.label}
                        label={spec.label}
                        unit={spec.unit}
                        items={items}
                        getValue={spec.getValue}
                        findBest={spec.findBest}
                        aircraftColors={aircraftColors}
                    />
                ))}
            </div>
        </div>
    );
};
