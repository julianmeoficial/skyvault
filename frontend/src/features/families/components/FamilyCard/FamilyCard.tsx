/**
 * @file FamilyCard.tsx
 * @description Card individual de familia de aeronaves
 * ACTUALIZADO: Muestra activeModelCount en lugar de "—"
 */

import React, { useEffect, useRef } from 'react';
import gsap from 'gsap';
import {
    CheckCircleIcon,
    StarIcon,
    CogIcon,
    ExclamationTriangleIcon,
} from '@heroicons/react/24/solid';
import { getAircraftImage } from '../../../../config/aircraftImageMapping';
import styles from './FamilyCard.module.css';
import type { FamilyDto } from '../../types';

interface FamilyCardProps {
    family: FamilyDto;
}

const FamilyCard: React.FC<FamilyCardProps> = ({ family }) => {
    const cardRef = useRef<HTMLDivElement>(null);
    const contentRef = useRef<HTMLDivElement>(null);
    const imageRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!cardRef.current || !contentRef.current || !imageRef.current) return;

        const ctx = gsap.context(() => {
            gsap.set(cardRef.current, {
                force3D: true,
                backfaceVisibility: 'hidden',
                perspective: 1000,
                willChange: 'transform',
            });

            const hoverTl = gsap.timeline({ paused: true });

            hoverTl
                .to(
                    cardRef.current,
                    {
                        y: -8,
                        boxShadow: '0 20px 40px rgba(0, 0, 0, 0.15)',
                        duration: 0.35,
                        ease: 'cubic.out',
                    },
                    0
                )
                .to(
                    imageRef.current,
                    {
                        scale: 1.08,
                        duration: 0.35,
                        ease: 'cubic.out',
                    },
                    0
                )
                .to(
                    '.family-card-badge',
                    {
                        rotate: 3,
                        duration: 0.25,
                        ease: 'back.out(1.2)',
                    },
                    0.05
                );

            const handleMouseEnter = () => hoverTl.play();
            const handleMouseLeave = () => hoverTl.reverse();

            if (cardRef.current) {
                cardRef.current.addEventListener('mouseenter', handleMouseEnter);
                cardRef.current.addEventListener('mouseleave', handleMouseLeave);
            }

            return () => {
                if (cardRef.current) {
                    cardRef.current.removeEventListener('mouseenter', handleMouseEnter);
                    cardRef.current.removeEventListener('mouseleave', handleMouseLeave);
                }
            };
        }, cardRef);

        return () => {
            ctx.revert();
        };
    }, []);

    const categoryColor: Record<string, string> = {
        'Narrow-body': styles.narrowBody,
        'Wide-body': styles.wideBody,
        Jumbo: styles.jumbo,
        'Single-aisle': styles.narrowBody,
        'Medium-wide': styles.wideBody,
        'Small single-aisle': styles.narrowBody,
        'Large widebody': styles.wideBody,
        'Medium widebody': styles.wideBody,
        'Super-Jumbo': styles.jumbo,
    };

    const getManufacturerLogo = () => {
        const isAirbus = family.manufacturer?.name === 'Airbus';
        return isAirbus
            ? '/assets/images/manufacturers/AirbusLightSize=60.svg'
            : '/assets/images/manufacturers/BoeingLightSize=60.svg';
    };

    const getStatusIcon = (status: string) => {
        const iconProps = {
            className: styles.statusIcon,
            'aria-hidden': 'true',
        } as const;

        switch (status) {
            case 'En Producción':
                return <CheckCircleIcon {...iconProps} style={{ color: '#10b981' }} />;
            case 'Finalizado':
                return <StarIcon {...iconProps} style={{ color: '#f59e0b' }} />;
            case 'En Desarrollo':
                return <CogIcon {...iconProps} style={{ color: '#3b82f6' }} />;
            default:
                return <ExclamationTriangleIcon {...iconProps} style={{ color: '#ef4444' }} />;
        }
    };

    const formattedDate = family.launchDate
        ? new Date(family.launchDate).toLocaleDateString('es-ES', {
            year: 'numeric',
            month: 'long',
        })
        : '—';

    return (
        <div
            ref={cardRef}
            className={styles.card}
            role="article"
            aria-label={`Familia ${family.name}`}
        >
            <div className={styles.bgGradient} />

            <div ref={imageRef} className={styles.imageContainer}>
                <img
                    src={getAircraftImage(family.name)}
                    alt={`Familia ${family.name}`}
                    className={styles.familyImage}
                    loading="lazy"
                    onError={(e) => {
                        console.error(`[FamilyCard] Error imagen ${family.name}`);
                        (e.currentTarget as HTMLImageElement).style.display = 'none';
                        const container = (e.currentTarget as HTMLImageElement).parentElement;
                        if (container) {
                            container.style.background =
                                'linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%)';
                        }
                    }}
                />

                <img
                    src={getManufacturerLogo()}
                    alt={`${family.manufacturer?.name} logo`}
                    className={styles.manufacturerLogo}
                    loading="lazy"
                />

                <div className={styles.overlay} />
            </div>

            <div ref={contentRef} className={styles.content}>
                <div className={styles.header}>
                    <div className={styles.headerLeft}>
                        <h3 className={styles.familyName}>{family.name}</h3>
                        <span className={styles.manufacturer}>{family.manufacturer?.name}</span>
                    </div>
                    <span
                        className={`${styles.badge} family-card-badge ${
                            categoryColor[family.category] || styles.narrowBody
                        }`}
                    >
                        {family.category}
                    </span>
                </div>

                <p className={styles.description}>{family.description}</p>

                <div className={styles.statusContainer}>
                    <div className={styles.statusBadge}>
                        {getStatusIcon('En Producción')}
                        <span className={styles.statusText}>En Producción</span>
                    </div>
                </div>

                <div className={styles.meta}>
                    <div className={styles.metaItem}>
                        <span className={styles.metaLabel}>Lanzamiento</span>
                        <span className={styles.metaValue}>{formattedDate}</span>
                    </div>
                    {/* CAMBIO: Mostrar activeModelCount */}
                    <div className={styles.metaItem}>
                        <span className={styles.metaLabel}>Modelos</span>
                        <span className={styles.metaValue}>
                            {family.activeModelCount ?? 0}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default FamilyCard;
