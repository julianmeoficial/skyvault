import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './FeaturesSection.module.css';

// Registrar plugin de ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const FeaturesSection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const cardsRef = useRef<(HTMLDivElement | null)[]>([]);
    const { theme } = useTheme();

    const features = [
        {
            icon: (
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z"/>
                </svg>
            ),
            title: 'Velocidad sin límites',
            description: 'Respuestas en milisegundos con arquitectura optimizada y caching inteligente.'
        },
        {
            icon: (
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                </svg>
            ),
            title: 'Seguridad robusta',
            description: 'Autenticación OAuth 2.0 y encriptación end-to-end para proteger tus datos.'
        },
        {
            icon: (
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                    <polyline points="3.27 6.96 12 12.01 20.73 6.96"/>
                    <line x1="12" y1="22.08" x2="12" y2="12"/>
                </svg>
            ),
            title: 'Datos completos',
            description: 'Acceso a información técnica detallada de más de 36 modelos de aeronaves comerciales.'
        },
        {
            icon: (
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/>
                </svg>
            ),
            title: '99.9% Uptime',
            description: 'Infraestructura redundante y monitoreo 24/7 garantizan disponibilidad constante.'
        },
        {
            icon: (
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/>
                </svg>
            ),
            title: 'Fácil integración',
            description: 'SDKs oficiales para JavaScript, Python, Java y documentación exhaustiva.'
        },
        {
            icon: (
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <line x1="12" y1="1" x2="12" y2="23"/>
                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                </svg>
            ),
            title: 'Precios flexibles',
            description: 'Planes escalables desde desarrollo gratuito hasta enterprise sin límites.'
        }
    ];

    useEffect(() => {
        // Configurar hardware acceleration
        gsap.set([titleRef.current, subtitleRef.current, ...cardsRef.current], {
            force3D: true,
            backfaceVisibility: 'hidden',
            perspective: 1000
        });

        const ctx = gsap.context(() => {
            // Animación del título con reveal effect
            gsap.fromTo(
                titleRef.current,
                {
                    opacity: 0,
                    y: 80,
                    scale: 0.9
                },
                {
                    opacity: 1,
                    y: 0,
                    scale: 1,
                    duration: 1.4,
                    ease: 'expo.out',
                    scrollTrigger: {
                        trigger: titleRef.current,
                        start: 'top 80%',
                        end: 'bottom 60%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            // Animación del subtítulo
            gsap.fromTo(
                subtitleRef.current,
                {
                    opacity: 0,
                    y: 40
                },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1.2,
                    ease: 'power3.out',
                    scrollTrigger: {
                        trigger: subtitleRef.current,
                        start: 'top 85%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            // Animación de cards con stagger optimizado
            cardsRef.current.forEach((card, index) => {
                if (!card) return;

                // Reveal animation con ScrollTrigger
                gsap.fromTo(
                    card,
                    {
                        opacity: 0,
                        y: 100,
                        scale: 0.8,
                        rotateX: -15
                    },
                    {
                        opacity: 1,
                        y: 0,
                        scale: 1,
                        rotateX: 0,
                        duration: 1.2,
                        delay: index * 0.1,
                        ease: 'expo.out',
                        force3D: true,
                        scrollTrigger: {
                            trigger: card,
                            start: 'top 90%',
                            end: 'bottom 20%',
                            toggleActions: 'play none none reverse',
                            once: false
                        }
                    }
                );
            });
        }, sectionRef);

        return () => {
            ctx.revert(); // Cleanup de GSAP context
            ScrollTrigger.getAll().forEach(st => st.kill());
        };
    }, []);

    // Efecto magnético en hover con quickTo para 120fps
    const handleCardMouseMove = (e: React.MouseEvent<HTMLDivElement>, index: number) => {
        const card = cardsRef.current[index];
        if (!card) return;

        const rect = card.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        const centerX = rect.width / 2;
        const centerY = rect.height / 2;

        const rotateX = (y - centerY) / 20;
        const rotateY = (centerX - x) / 20;

        gsap.to(card, {
            rotateX: rotateX,
            rotateY: rotateY,
            scale: 1.05,
            duration: 0.4,
            ease: 'power2.out',
            force3D: true,
            transformPerspective: 1000
        });

        // Efecto de glow siguiendo el cursor
        const glowX = (x / rect.width) * 100;
        const glowY = (y / rect.height) * 100;

        gsap.set(card, {
            '--glow-x': `${glowX}%`,
            '--glow-y': `${glowY}%`
        });
    };

    const handleCardMouseLeave = (index: number) => {
        const card = cardsRef.current[index];
        if (!card) return;

        gsap.to(card, {
            rotateX: 0,
            rotateY: 0,
            scale: 1,
            duration: 0.6,
            ease: 'power2.out',
            force3D: true
        });
    };

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    return (
        <section ref={sectionRef} className={`${styles.features} ${getThemeClass()}`}>
            <div className={styles.container}>
                <h2 ref={titleRef} className={styles.sectionTitle}>
                    Diseñada para desarrolladores
                </h2>
                <p ref={subtitleRef} className={styles.sectionSubtitle}>
                    Una API que combina potencia, simplicidad y confiabilidad
                </p>

                <div className={styles.grid}>
                    {features.map((feature, index) => (
                        <div
                            key={index}
                            ref={(el: HTMLDivElement | null) => {
                                cardsRef.current[index] = el;
                            }}
                            className={styles.featureCard}
                            onMouseMove={(e) => handleCardMouseMove(e, index)}
                            onMouseLeave={() => handleCardMouseLeave(index)}
                        >
                            <div className={styles.iconWrapper}>
                                {feature.icon}
                            </div>
                            <h3 className={styles.featureTitle}>{feature.title}</h3>
                            <p className={styles.featureDescription}>{feature.description}</p>
                            <div className={styles.cardGlow}></div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default FeaturesSection;
