import React from 'react';
import type { SpecificationRowData } from '../types/comparison.types';
import styles from './SpecificationRow.module.css';

interface SpecificationRowProps {
    data: SpecificationRowData;
    highlightBest?: boolean;
}

export const SpecificationRow: React.FC<SpecificationRowProps> = ({
                                                                      data,
                                                                      highlightBest = false
                                                                  }) => {
    const { label, unit, values } = data;

    // Encontrar el mejor valor (mayor número)
    const numericValues = values.map((v: number | string | null | undefined) =>
        (typeof v === 'number' ? v : parseFloat(String(v)) || 0)
    );
    const bestValue = Math.max(...numericValues);

    return (
        <div className={styles.row}>
            <div className={styles.label}>
                <span>{label}</span>
                {unit && <span className={styles.unit}>{unit}</span>}
            </div>
            <div className={styles.values}>
                {values.map((value: number | string | null | undefined, index: number) => {
                    const isNumeric = typeof value === 'number';
                    const numValue = typeof value === 'number' ? value : parseFloat(String(value)) || 0;
                    const isBest = highlightBest && isNumeric && numValue === bestValue && bestValue > 0;

                    return (
                        <div key={index} className={`${styles.value} ${isBest ? styles.best : ''}`}>
                            {value !== null && value !== undefined ? value : '—'}
                        </div>
                    );
                })}
            </div>
        </div>
    );
};