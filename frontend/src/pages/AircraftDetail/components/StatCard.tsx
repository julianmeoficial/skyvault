import styles from './StatCard.module.css';

interface StatCardProps {
    label: string;
    value: string | number;
    sublabel?: string;
    icon?: string; // Ruta del ícono PNG
    variant?: 'primary' | 'secondary' | 'accent' | 'neutral';
}

/**
 * Componente StatCard reutilizable con Glass Effect.
 * Diseñado para mostrar estadísticas clave con iconos 3D PNG.
 *
 * @param label - Título principal (ej: "Passengers")
 * @param value - Valor numérico o texto (ej: "375" o "16,700 km")
 * @param sublabel - Texto secundario (ej: "(typical)")
 * @param icon - Ruta del ícono PNG 3D (opcional)
 * @param variant - Variante de color (primary, secondary, accent, neutral)
 */
export function StatCard({ label, value, sublabel, icon, variant = 'primary' }: StatCardProps) {
    return (
        <div className={`${styles.statCard} ${styles[`variant-${variant}`]}`}>
            {/* Ícono opcional */}
            {icon && (
                <div className={styles.iconContainer}>
                    <img src={icon} alt={label} className={styles.icon} />
                </div>
            )}

            {/* Contenido */}
            <div className={styles.content}>
                <span className={styles.label}>{label}</span>
                <span className={styles.value}>{value}</span>
                {sublabel && <span className={styles.sublabel}>{sublabel}</span>}
            </div>
        </div>
    );
}