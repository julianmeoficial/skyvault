import styles from './SpecCard.module.css';

interface SpecCardProps {
    label: string;
    value: string | number;
    unit?: string;
}

/**
 * Card futurista con animación de luz para specs técnicas.
 * Inspirado en diseño dark con gradientes radiales y dot animado.
 */
export function SpecCard({ label, value, unit }: SpecCardProps) {
    return (
        <div className={styles.outer}>
            <div className={styles.dot} />
            <div className={styles.card}>
                <div className={styles.ray} />
                <div className={styles.value}>
                    {value}
                    {unit && <span className={styles.unit}>{unit}</span>}
                </div>
                <div className={styles.label}>{label}</div>
                <div className={`${styles.line} ${styles.topl}`} />
                <div className={`${styles.line} ${styles.leftl}`} />
                <div className={`${styles.line} ${styles.bottoml}`} />
                <div className={`${styles.line} ${styles.rightl}`} />
            </div>
        </div>
    );
}
