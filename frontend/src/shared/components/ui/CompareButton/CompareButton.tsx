import styles from './CompareButton.module.css';

interface CompareButtonProps {
    onClick?: () => void;
    label?: string;
    disabled?: boolean;
}

/**
 * Botón de comparar con efecto de flecha animada
 * Inspirado en diseño moderno con círculo + ícono SVG
 * Colores SkyVault (#32b8c6)
 */
export function CompareButton({ onClick, label = 'Comparar', disabled = false }: CompareButtonProps) {
    return (
        <button
            className={styles.compareButton}
            onClick={onClick}
            disabled={disabled}
        >
            <span className={styles.iconWrapper}>
                {/* Flecha original */}
                <svg
                    viewBox="0 0 14 15"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    className={styles.iconSvg}
                    width="10"
                >
                    <path
                        d="M13.376 11.552l-.264-10.44-10.44-.24.024 2.28 6.96-.048L.2 12.56l1.488 1.488 9.432-9.432-.048 6.912 2.304.024z"
                        fill="currentColor"
                    />
                </svg>

                {/* Flecha copia (para animación) */}
                <svg
                    viewBox="0 0 14 15"
                    fill="none"
                    width="10"
                    xmlns="http://www.w3.org/2000/svg"
                    className={`${styles.iconSvg} ${styles.iconSvgCopy}`}
                >
                    <path
                        d="M13.376 11.552l-.264-10.44-10.44-.24.024 2.28 6.96-.048L.2 12.56l1.488 1.488 9.432-9.432-.048 6.912 2.304.024z"
                        fill="currentColor"
                    />
                </svg>
            </span>
            <span className={styles.label}>{label}</span>
        </button>
    );
}
