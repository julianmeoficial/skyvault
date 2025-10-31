import React from 'react';
import type { ComparisonItem } from '../../types/comparison.types';
import { ComparisonRow } from '../ComparisonRow';
import styles from './ComparisonSection.module.css';

interface CapacitySectionProps {
    items: ComparisonItem[];
    aircraftColors: string[];
}

export const CapacitySection: React.FC<CapacitySectionProps> = ({ items, aircraftColors }) => {
    const specs = [
        {
            label: 'Capacidad máxima',
            unit: 'pasajeros',
            getValue: (item: ComparisonItem) => item.maxPassengers,
            findBest: true,
        },
        {
            label: 'Combustible máximo',
            unit: 'litros',
            getValue: (item: ComparisonItem) => {
                // Parsear de specifications.fuelCapacityLiters (viene como "27,178")
                if (item.specifications?.fuelCapacityLiters) {
                    const value = item.specifications.fuelCapacityLiters.replace(/,/g, '');
                    return parseInt(value, 10);
                }
                return null;
            },
            findBest: true,
        },
    ];

    return (
        <div className={styles.section}>
            <div className={styles.sectionHeader}>
                <h3 className={styles.sectionTitle}>CAPACIDAD</h3>
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
