import React from 'react';
import type { ComparisonItem } from '../../types/comparison.types';
import { ComparisonRow } from '../ComparisonRow';
import styles from './ComparisonSection.module.css';

interface DimensionsSectionProps {
    items: ComparisonItem[];
    aircraftColors: string[];
}

export const DimensionsSection: React.FC<DimensionsSectionProps> = ({ items, aircraftColors }) => {
    const specs = [
        {
            label: 'Longitud',
            unit: 'm',
            getValue: (item: ComparisonItem) => {
                if (item.specifications?.lengthM) {
                    return parseFloat(item.specifications.lengthM);
                }
                return null;
            },
        },
        {
            label: 'Envergadura',
            unit: 'm',
            getValue: (item: ComparisonItem) => {
                if (item.specifications?.wingspanM) {
                    return parseFloat(item.specifications.wingspanM);
                }
                return null;
            },
        },
        {
            label: 'Altura',
            unit: 'm',
            getValue: (item: ComparisonItem) => {
                if (item.specifications?.heightM) {
                    return parseFloat(item.specifications.heightM);
                }
                return null;
            },
        },
    ];

    return (
        <div className={styles.section}>
            <div className={styles.sectionHeader}>
                <h3 className={styles.sectionTitle}>DIMENSIONES</h3>
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
