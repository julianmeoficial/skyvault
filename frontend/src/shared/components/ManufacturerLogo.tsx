import React from 'react';
import { useResponsiveLogo } from '../hooks/useResponsiveLogo';
import styles from './ManufacturerLogo.module.css';

interface ManufacturerLogoProps {
    manufacturer: string;
    className?: string;
}

export const ManufacturerLogo: React.FC<ManufacturerLogoProps> = ({
                                                                      manufacturer,
                                                                      className = ''
                                                                  }) => {
    // Normalizar nombre del fabricante
    const normalizedManufacturer = manufacturer.toLowerCase();

    // Solo soportamos Airbus y Boeing por ahora
    if (normalizedManufacturer !== 'airbus' && normalizedManufacturer !== 'boeing') {
        return (
            <span className={`${styles.fallbackText} ${className}`}>
        {manufacturer}
      </span>
        );
    }

    const { size, logoPath } = useResponsiveLogo(normalizedManufacturer as 'airbus' | 'boeing');

    return (
        <img
            src={logoPath}
            alt={`${manufacturer} Logo`}
            className={`${styles.logo} ${className}`}
            width={size}
            height={size}
            loading="lazy"
        />
    );
};
