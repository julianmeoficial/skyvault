import { useEffect, useRef } from 'react';
import gsap from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { MotionPathPlugin } from 'gsap/MotionPathPlugin';
import { StatCard } from '../components/StatCard';
import type { AircraftDetailDto } from '../../../features/aircraft/types/aircraft.types';
import styles from './OverviewSection.module.css';

gsap.registerPlugin(ScrollTrigger, MotionPathPlugin);

interface OverviewSectionProps {
    aircraft: AircraftDetailDto;
}

export function OverviewSection({ aircraft }: OverviewSectionProps) {
    const sectionRef = useRef<HTMLElement>(null);
    const cardsRefs = useRef<Array<HTMLDivElement | null>>([]);
    const airplaneRef = useRef<HTMLDivElement>(null);
    const pathRef = useRef<SVGPathElement>(null);

    const specs = aircraft.specifications;

    // ✅ Formatear números con separador de miles
    const formatNumber = (num: number | undefined | null): string => {
        if (num === undefined || num === null) return 'N/A';
        return num.toLocaleString('es-ES', { maximumFractionDigits: 0 });
    };

 ////

    // 8 Cards data con truncamiento inteligente
    const cardsData = [
        // FILA 1: Pasajeros → Alcance → Velocidad → Introducción
        {
            label: 'Pasajeros',
            value: formatNumber(aircraft.typicalPassengers),
            sublabel: '(típico)',
            icon: '/assets/icons/specifications/specs/passenger.webp',
            variant: 'primary' as const
        },
        {
            label: 'Alcance',
            value: aircraft.rangeKm ? `${formatNumber(aircraft.rangeKm)} km` : 'N/A',
            sublabel: '(máximo)',
            icon: '/assets/icons/specifications/specs/range.webp',
            variant: 'secondary' as const
        },
        {
            label: 'Velocidad',
            value: aircraft.cruiseSpeedKnots ? `${formatNumber(aircraft.cruiseSpeedKnots)} kts` : 'N/A',
            sublabel: '(crucero)',
            icon: '/assets/icons/specifications/specs/speedometer.webp',
            variant: 'accent' as const
        },
        {
            label: 'Introducción',
            value: aircraft.introductionYear || 'N/A',
            sublabel: '(año)',
            icon: '/assets/icons/specifications/specs/calendar.webp',
            variant: 'neutral' as const
        },

        // FILA 2: Fabricante → Producción → Modelo → Motores
        {
            label: 'Fabricante',
            value: aircraft.manufacturer.name,
            sublabel: aircraft.manufacturer.country || '',
            icon: '/assets/icons/specifications/specs/manufacturer.webp',
            variant: 'neutral' as const
        },
        {
            label: 'Producción',
            value: aircraft.productionState?.name || 'Desconocido',
            sublabel: '(estado)',
            icon: '/assets/icons/specifications/specs/production.webp',
            variant: 'accent' as const
        },
        {
            label: 'Modelo',
            value: specs?.engineModel || 'N/A',
            sublabel: specs?.engineManufacturer || '',
            icon: '/assets/icons/specifications/specs/engine-model.webp',
            variant: 'secondary' as const
        },

        {
            label: 'Motores',
            value: specs?.engineCount ? `${specs.engineCount}x` : 'N/A',
            sublabel: '(cantidad)',
            icon: '/assets/icons/specifications/specs/engine.webp',
            variant: 'primary' as const
        }
    ];

    useEffect(() => {
        if (!sectionRef.current || !airplaneRef.current || !pathRef.current) return;

        const ctx = gsap.context(() => {
            const tl = gsap.timeline({
                scrollTrigger: {
                    trigger: sectionRef.current,
                    start: 'top top',
                    end: '+=200%',
                    scrub: 1,
                    pin: true,
                    anticipatePin: 1,
                    markers: false
                }
            });

            if (airplaneRef.current && pathRef.current) {
                tl.to(airplaneRef.current, {
                    motionPath: {
                        path: pathRef.current,
                        align: pathRef.current,
                        autoRotate: true,
                        alignOrigin: [0.5, 0.5]
                    },
                    duration: 10,
                    ease: 'none'
                }, 0);
            }

            const animationOrder = [0, 1, 2, 3, 7, 6, 5, 4];

            animationOrder.forEach((cardIndex, sequenceIndex) => {
                const card = cardsRefs.current[cardIndex];
                if (!card) return;

                const startTime = (sequenceIndex / 8) * 10;
                const endTime = ((sequenceIndex + 1) / 8) * 10;

                tl.fromTo(card,
                    {
                        opacity: 0.3,
                        scale: 0.95,
                        y: 20
                    },
                    {
                        opacity: 1,
                        scale: 1,
                        y: 0,
                        duration: 0.6,
                        ease: 'back.out(1.7)'
                    },
                    startTime
                );

                tl.to(card,
                    {
                        opacity: 0.85,
                        scale: 1,
                        duration: 0.4
                    },
                    endTime - 0.5
                );
            });

        }, sectionRef);

        return () => ctx.revert();
    }, [aircraft]);

    return (
        <section ref={sectionRef} className={styles.overviewSection}>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h2 className={styles.title}>Vista General</h2>
                    <p className={styles.subtitle}>Especificaciones clave de un vistazo</p>
                </div>

                <div className={styles.cardsContainer}>
                    {cardsData.map((cardData, index) => (
                        <div
                            key={index}
                            ref={(el) => {
                                cardsRefs.current[index] = el;
                            }}
                            className={styles.cardWrapper}
                        >
                            <StatCard {...cardData} />
                        </div>
                    ))}
                </div>
            </div>

            <svg className={styles.pathSvg} viewBox="0 0 1400 600" xmlns="http://www.w3.org/2000/svg">
                <defs>
                    <linearGradient id="trailGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                        <stop offset="0%" stopColor="rgba(50, 184, 198, 0)" />
                        <stop offset="50%" stopColor="rgba(50, 184, 198, 0.6)" />
                        <stop offset="100%" stopColor="rgba(50, 184, 198, 0)" />
                    </linearGradient>
                </defs>

                <path
                    ref={pathRef}
                    d="M 10 110
                       L 60 110
                       Q 217 110, 242 110
                       L 375 110
                       Q 624 110, 649 110
                       L 874 110
                       Q 1093 110, 1118 110
                       L 1313 110
                       Q 1363 110, 1393 180
                       Q 1420 250, 1393 320
                       Q 1363 320, 1313 320
                       L 874 320
                       Q 624 320, 599 320
                       L 375 320
                       Q 217 320, 192 320
                       L 60 320
                       L 10 320"
                    fill="none"
                    stroke="rgba(50, 184, 198, 0.4)"
                    strokeWidth="3"
                    strokeDasharray="12,8"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                />

                <circle cx="60" cy="110" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="375" cy="110" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="874" cy="110" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="1313" cy="110" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="1313" cy="320" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="874" cy="320" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="375" cy="320" r="6" fill="rgba(50, 184, 198, 0.6)" />
                <circle cx="60" cy="320" r="6" fill="rgba(50, 184, 198, 0.6)" />
            </svg>

            <div ref={airplaneRef} className={styles.airplane}>
                <img
                    src="/assets/icons/specifications/airplane-side.png"
                    alt="Aircraft animation"
                    className={styles.airplaneImage}
                />
                <div className={styles.airplaneTrail}></div>
            </div>
        </section>
    );
}
