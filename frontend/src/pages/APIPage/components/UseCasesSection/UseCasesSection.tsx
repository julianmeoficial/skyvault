import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './UseCasesSection.module.css';

// Registrar ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const UseCasesSection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const cardsRef = useRef<(HTMLDivElement | null)[]>([]);
    const { theme } = useTheme();

    const useCases = [
        {
            icon: (
                <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                    <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                </svg>
            ),
            title: 'Fabricantes',
            description: 'Integra especificaciones técnicas en tiempo real en tus sistemas de producción y catálogos digitales.',
            benefits: [
                'Actualización automática de datos',
                'Comparativas entre modelos',
                'Histórico de versiones'
            ]
        },
        {
            icon: (
                <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <circle cx="12" cy="12" r="10"/>
                    <path d="M8 14s1.5 2 4 2 4-2 4-2"/>
                    <line x1="9" y1="9" x2="9.01" y2="9"/>
                    <line x1="15" y1="9" x2="15.01" y2="9"/>
                </svg>
            ),
            title: 'Entusiastas',
            description: 'Crea aplicaciones, wikis y herramientas para la comunidad de aficionados a la aviación comercial.',
            benefits: [
                'Datos completos y precisos',
                'Imágenes en alta resolución',
                'APIs RESTful intuitivas'
            ]
        },
        {
            icon: (
                <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                    <polyline points="7.5 4.21 12 6.81 16.5 4.21"/>
                    <polyline points="7.5 19.79 7.5 14.6 3 12"/>
                    <polyline points="21 12 16.5 14.6 16.5 19.79"/>
                    <polyline points="3.27 6.96 12 12.01 20.73 6.96"/>
                    <line x1="12" y1="22.08" x2="12" y2="12"/>
                </svg>
            ),
            title: 'Aerolíneas',
            description: 'Optimiza la gestión de flotas con información actualizada sobre capacidad, consumo y rendimiento.',
            benefits: [
                'Análisis de eficiencia',
                'Planificación de rutas',
                'Informes personalizados'
            ]
        }
    ];

    useEffect(() => {
        // Hardware acceleration setup
        gsap.set([titleRef.current, subtitleRef.current, ...cardsRef.current], {
            force3D: true,
            backfaceVisibility: 'hidden',
            perspective: 1000
        });

        const ctx = gsap.context(() => {
            // Animación de títulos
            gsap.fromTo(
                titleRef.current,
                {
                    opacity: 0,
                    y: 60,
                    scale: 0.95
                },
                {
                    opacity: 1,
                    y: 0,
                    scale: 1,
                    duration: 1.3,
                    ease: 'expo.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: titleRef.current,
                        start: 'top 80%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            gsap.fromTo(
                subtitleRef.current,
                {
                    opacity: 0,
                    y: 30
                },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1.1,
                    ease: 'power3.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: subtitleRef.current,
                        start: 'top 85%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            // Animación optimizada de cards con parallax
            cardsRef.current.forEach((card, index) => {
                if (!card) return;

                // Tu animación de entrada parallax MANTENIDA pero optimizada
                gsap.fromTo(
                    card,
                    {
                        opacity: 0,
                        x: index % 2 === 0 ? -100 : 100,
                        rotateY: index % 2 === 0 ? -15 : 15
                    },
                    {
                        opacity: 1,
                        x: 0,
                        rotateY: 0,
                        duration: 1.2,
                        ease: 'power3.out',
                        force3D: true,
                        scrollTrigger: {
                            trigger: card,
                            start: 'top 75%',
                            end: 'bottom 25%',
                            toggleActions: 'play none none reverse'
                        }
                    }
                );

                // Hover optimizado con quickTo (TU ANIMACIÓN MANTENIDA)
                const quickScale = gsap.quickTo(card, 'scale', {
                    duration: 0.3,
                    ease: 'power2.out'
                });

                card.addEventListener('mouseenter', () => {
                    quickScale(1.05);
                });

                card.addEventListener('mouseleave', () => {
                    quickScale(1);
                });
            });
        }, sectionRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(st => st.kill());
        };
    }, []);

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    return (
        <section ref={sectionRef} className={`${styles.useCases} ${getThemeClass()}`}>
            <div className={styles.container}>
                <h2 ref={titleRef} className={styles.sectionTitle}>
                    ¿Quién usa SkyVault API?
                </h2>
                <p ref={subtitleRef} className={styles.sectionSubtitle}>
                    Desde startups hasta empresas Fortune 500, profesionales de todo el mundo confían en nuestra infraestructura
                </p>

                <div className={styles.grid}>
                    {useCases.map((useCase, index) => (
                        <div
                            key={index}
                            ref={(el: HTMLDivElement | null) => {
                                cardsRef.current[index] = el;
                            }}
                            className={styles.useCaseCard}
                        >
                            <div className={styles.iconWrapper}>
                                {useCase.icon}
                            </div>
                            <h3 className={styles.cardTitle}>{useCase.title}</h3>
                            <p className={styles.cardDescription}>{useCase.description}</p>

                            <ul className={styles.benefitsList}>
                                {useCase.benefits.map((benefit, idx) => (
                                    <li key={idx} className={styles.benefitItem}>
                                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="3">
                                            <polyline points="20 6 9 17 4 12"/>
                                        </svg>
                                        <span>{benefit}</span>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default UseCasesSection;
