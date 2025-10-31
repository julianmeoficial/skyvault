import React from 'react';
import type { ComparisonItem } from '../types/comparison.types';
import { PerformanceSection } from './sections/PerformanceSection';
import { CapacitySection } from './sections/CapacitySection';
import { DimensionsSection } from './sections/DimensionsSection';
import { EngineSection } from './sections/EngineSection';
import { GeneralInfoSection } from './sections/GeneralInfoSection';
import styles from './ComparisonGrid.module.css';

interface ComparisonGridProps {
    items: ComparisonItem[];
}

const AIRCRAFT_COLORS = ['#E3F2FD', '#FFF3E0', '#F3E5F5'];

export const ComparisonGrid: React.FC<ComparisonGridProps> = ({ items }) => {
    if (items.length === 0) {
        return (
            <div className={styles.grid}>
                <div className={styles.empty}>No hay datos para comparar</div>
            </div>
        );
    }

    const aircraftColors = items.map((_, index) => AIRCRAFT_COLORS[index]);

    return (
        <div className={styles.grid}>
            <PerformanceSection items={items} aircraftColors={aircraftColors} />
            <CapacitySection items={items} aircraftColors={aircraftColors} />
            <DimensionsSection items={items} aircraftColors={aircraftColors} />
            <EngineSection items={items} aircraftColors={aircraftColors} />
            <GeneralInfoSection items={items} aircraftColors={aircraftColors} />
        </div>
    );
};
