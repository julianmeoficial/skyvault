import React from 'react';
import type { ComparisonItem } from '../types/comparison.types';
import styles from './ComparisonRow.module.css';

interface ComparisonRowProps {
    label: string;
    unit?: string;
    items: ComparisonItem[];
    getValue: (item: ComparisonItem) => string | number | null | undefined;
    findBest?: boolean;
    aircraftColors: string[];
}

export const ComparisonRow: React.FC<ComparisonRowProps> = ({
                                                                label,
                                                                unit,
                                                                items,
                                                                getValue,
                                                                findBest = false,
                                                                aircraftColors,
                                                            }) => {
    const values = items.map((item) => getValue(item));
    const numericValues = values.filter((v): v is number => typeof v === 'number');

    let bestValue: number | null = null;
    if (findBest && numericValues.length > 0) {
        bestValue = Math.max(...numericValues);
    }

    return (
        <div className={styles.row}>
            <div className={styles.label}>
                <span className={styles.labelMain}>{label}</span>
                {unit && <span className={styles.labelUnit}>{unit}</span>}
            </div>

            <div className={styles.values}>
                {items.map((item, index) => {
                    const value = getValue(item);
                    const isBest = findBest && typeof value === 'number' && value === bestValue;

                    const displayValue =
                        typeof value === 'number' ? value.toLocaleString('es-ES') : value || '-';

                    return (
                        <div
                            key={item.id}
                            className={`${styles.value} ${isBest ? styles.valueBest : ''}`}
                            style={{
                                backgroundColor: aircraftColors[index],
                                borderColor: isBest ? 'var(--color-accent)' : 'transparent',
                            }}
                        >
                            {displayValue}
                        </div>
                    );
                })}
            </div>
        </div>
    );
};
