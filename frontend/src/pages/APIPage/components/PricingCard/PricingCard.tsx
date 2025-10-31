import React, { useRef, useEffect } from 'react';
import { gsap } from 'gsap';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './PricingCard.module.css';

interface PricingCardProps {
    name: string;
    price: string;
    period?: string;
    description: string;
    features: string[];
    highlighted?: boolean;
    buttonText: string;
}

const PricingCard: React.FC<PricingCardProps> = ({
                                                     name,
                                                     price,
                                                     period,
                                                     description,
                                                     features,
                                                     highlighted = false,
                                                     buttonText
                                                 }) => {
    const cardRef = useRef<HTMLDivElement>(null);
    const { theme } = useTheme();

    useEffect(() => {
        if (!cardRef.current) return;

        // Hardware acceleration setup
        gsap.set(cardRef.current, {
            force3D: true,
            backfaceVisibility: 'hidden'
        });

        // Hover optimizado con quickTo para scale
        const quickScale = gsap.quickTo(cardRef.current, 'scale', {
            duration: 0.4,
            ease: 'power2.out'
        });

        const quickY = gsap.quickTo(cardRef.current, 'y', {
            duration: 0.4,
            ease: 'power2.out'
        });

        const handleMouseEnter = () => {
            if (!highlighted) {
                quickY(-10);
            }
            quickScale(highlighted ? 1.05 : 1.02);
        };

        const handleMouseLeave = () => {
            quickY(0);
            quickScale(highlighted ? 1.05 : 1);
        };

        cardRef.current.addEventListener('mouseenter', handleMouseEnter);
        cardRef.current.addEventListener('mouseleave', handleMouseLeave);

        return () => {
            cardRef.current?.removeEventListener('mouseenter', handleMouseEnter);
            cardRef.current?.removeEventListener('mouseleave', handleMouseLeave);
        };
    }, [highlighted]);

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    return (
        <div
            ref={cardRef}
            className={`${styles.card} ${highlighted ? styles.highlighted : ''} ${getThemeClass()}`}
        >
            {highlighted && (
                <div className={styles.badge}>
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z"/>
                    </svg>
                    Más popular
                </div>
            )}

            <div className={styles.header}>
                <p className={styles.name}>{name}</p>
                <div className={styles.priceWrapper}>
                    <span className={styles.price}>{price}</span>
                    {period && <span className={styles.period}>{period}</span>}
                </div>
                <p className={styles.description}>{description}</p>
            </div>

            <ul className={styles.features}>
                {features.map((feature, index) => (
                    <li key={index} className={styles.feature}>
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5">
                            <polyline points="20 6 9 17 4 12"/>
                        </svg>
                        <span>{feature}</span>
                    </li>
                ))}
            </ul>

            <button
                className={`${styles.button} ${highlighted ? styles.primaryButton : styles.secondaryButton}`}
                type="button"
            >
                {buttonText}
            </button>
        </div>
    );
};

export default React.memo(PricingCard);
