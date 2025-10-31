import React from 'react';
import ThemeToggle from '../ThemeToggle/ThemeToggle';
import styles from './CrystalSlider.module.css';

interface CrystalSliderProps {
    position?: 'top-right' | 'top-left' | 'bottom-right' | 'bottom-left';
    className?: string;
}

/**
 * CrystalSlider - Componente flotante con efecto glassmorphism
 * que contiene los controles de tema (ThemeToggle).
 *
 * Se puede usar en páginas donde no aparece el Header.
 *
 * @param position - Posición del slider en la pantalla (default: 'top-right')
 * @param className - Clases CSS adicionales
 */
export const CrystalSlider: React.FC<CrystalSliderProps> = ({
                                                                position = 'top-right',
                                                                className = ''
                                                            }) => {
    return (
        <div
            className={`${styles.crystalSlider} ${styles[position]} ${className}`}
            role="toolbar"
            aria-label="Controles de tema"
        >
            <div className={styles.crystalContainer}>
                {/* Efecto de brillo superior */}
                <div className={styles.glassShine} />

                {/* Contenido: ThemeToggle */}
                <div className={styles.content}>
                    <ThemeToggle />
                </div>

                {/* Borde interior de cristal */}
                <div className={styles.innerBorder} />
            </div>
        </div>
    );
};

export default CrystalSlider;
