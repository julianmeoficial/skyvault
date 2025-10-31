import React from 'react';
import type { ComparisonItem } from '../../types/comparison.types';
import { ComparisonRow } from '../ComparisonRow';
import styles from './ComparisonSection.module.css';

interface GeneralInfoSectionProps {
    items: ComparisonItem[];
    aircraftColors: string[];
}

export const GeneralInfoSection: React.FC<GeneralInfoSectionProps> = ({ items, aircraftColors }) => {
    const specs = [
        {
            label: 'Tripulación mínima',
            unit: 'personas',
            getValue: (item: ComparisonItem) => item.minCrew || item.min_crew || 2,
        },
        {
            label: 'Año de introducción',
            getValue: (item: ComparisonItem) => item.introductionYear || item.introduction_year,
        },
        {
            label: 'Estado de producción',
            getValue: (item: ComparisonItem) => item.productionState?.name || item.production_state?.name || 'En Producción',
        },
    ];

    return (
        <div className={styles.section}>
            <div className={styles.sectionHeader}>
                <h3 className={styles.sectionTitle}>INFORMACIÓN GENERAL</h3>
            </div>
            <div className={styles.sectionContent}>
                {specs.map((spec) => (
                    <ComparisonRow
                        key={spec.label}
                        label={spec.label}
                        unit={spec.unit}
                        items={items}
                        getValue={spec.getValue}
                        aircraftColors={aircraftColors}
                    />
                ))}
            </div>
        </div>
    );
};
