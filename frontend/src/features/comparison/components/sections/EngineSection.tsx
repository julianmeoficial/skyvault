import React from 'react';
import type { ComparisonItem } from '../../types/comparison.types';
import { ComparisonRow } from '../ComparisonRow';
import styles from './ComparisonSection.module.css';

interface EngineSectionProps {
    items: ComparisonItem[];
    aircraftColors: string[];
}

export const EngineSection: React.FC<EngineSectionProps> = ({ items, aircraftColors }) => {
    const specs = [
        {
            label: 'Fabricante del motor',
            getValue: (item: ComparisonItem) =>
                item.specifications?.engineManufacturer || '-',
        },
        {
            label: 'Modelo del motor',
            getValue: (item: ComparisonItem) =>
                item.specifications?.engineModel || '-',
        },
    ];

    return (
        <div className={styles.section}>
            <div className={styles.sectionHeader}>
                <h3 className={styles.sectionTitle}>MOTOR</h3>
            </div>
            <div className={styles.sectionContent}>
                {specs.map((spec) => (
                    <ComparisonRow
                        key={spec.label}
                        label={spec.label}
                        items={items}
                        getValue={spec.getValue}
                        aircraftColors={aircraftColors}
                    />
                ))}
            </div>
        </div>
    );
};
