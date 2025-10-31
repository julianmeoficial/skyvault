import { useState, useEffect } from 'react';

type LogoSize = 36 | 45 | 60;

interface UseResponsiveLogoReturn {
    size: LogoSize;
    logoPath: string;
}

export const useResponsiveLogo = (manufacturer: 'airbus' | 'boeing'): UseResponsiveLogoReturn => {
    const [size, setSize] = useState<LogoSize>(60);

    useEffect(() => {
        const updateSize = () => {
            const width = window.innerWidth;

            if (width < 768) {
                // Mobile
                setSize(36);
            } else if (width < 1024) {
                // Tablet
                setSize(45);
            } else {
                // Desktop
                setSize(60);
            }
        };

        updateSize();
        window.addEventListener('resize', updateSize);
        return () => window.removeEventListener('resize', updateSize);
    }, []);

    const manufacturerName = manufacturer === 'airbus' ? 'Airbus' : 'Boeing';
    const logoPath = `/assets/images/manufacturers/${manufacturerName}LightSize=${size}.svg`;

    return { size, logoPath };
};
