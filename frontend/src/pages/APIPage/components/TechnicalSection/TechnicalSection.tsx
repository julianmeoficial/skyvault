import React, { useEffect, useRef, useMemo } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './TechnicalSection.module.css';

gsap.registerPlugin(ScrollTrigger);

const TechnicalSection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const statsRef = useRef<(HTMLDivElement | null)[]>([]);
    const cardsRef = useRef<(HTMLDivElement | null)[]>([]);
    const architectureRef = useRef<HTMLDivElement>(null);
    const counterRefs = useRef<(HTMLSpanElement | null)[]>([]);
    const { theme } = useTheme();

    // Memoizar stats para evitar recreaciones
    const stats = useMemo(() => [
        { value: 36, suffix: '+', decimals: 0, label: 'Modelos de aeronaves' },
        { value: 99.9, suffix: '%', decimals: 1, label: 'Uptime garantizado' },
        { value: 50, suffix: 'ms', decimals: 0, label: 'Tiempo de respuesta' },
        { value: 10000, suffix: '+', decimals: 0, label: 'Requests por segundo' }
    ], []);

    // Memoizar techStack completo
    const techStack = useMemo(() => [
        {
            category: 'Backend',
            items: [
                {
                    name: 'Spring Boot 3.2',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M12 2L2 7l10 5 10-5-10-5z"/>
                            <path d="M2 17l10 5 10-5"/>
                            <path d="M2 12l10 5 10-5"/>
                        </svg>
                    )
                },
                {
                    name: 'PostgreSQL 16',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                        </svg>
                    )
                },
                {
                    name: 'Redis Cache',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z"/>
                        </svg>
                    )
                },
                {
                    name: 'Docker',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <rect x="2" y="6" width="20" height="12" rx="2"/>
                            <path d="M12 6v-4"/>
                            <path d="M8 6v-2"/>
                            <path d="M16 6v-2"/>
                        </svg>
                    )
                }
            ]
        },
        {
            category: 'API',
            items: [
                {
                    name: 'RESTful Architecture',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                            <circle cx="9" cy="7" r="4"/>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                        </svg>
                    )
                },
                {
                    name: 'OpenAPI 3.0',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                            <polyline points="14 2 14 8 20 8"/>
                            <line x1="16" y1="13" x2="8" y2="13"/>
                            <line x1="16" y1="17" x2="8" y2="17"/>
                            <polyline points="10 9 9 9 8 9"/>
                        </svg>
                    )
                },
                {
                    name: 'OAuth 2.0',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                            <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                        </svg>
                    )
                },
                {
                    name: 'Rate Limiting',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <circle cx="12" cy="12" r="10"/>
                            <polyline points="12 6 12 12 16 14"/>
                        </svg>
                    )
                }
            ]
        },
        {
            category: 'Performance',
            items: [
                {
                    name: 'CDN Global',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <circle cx="12" cy="12" r="10"/>
                            <line x1="2" y1="12" x2="22" y2="12"/>
                            <path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"/>
                        </svg>
                    )
                },
                {
                    name: 'Load Balancing',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <line x1="12" y1="20" x2="12" y2="10"/>
                            <line x1="18" y1="20" x2="18" y2="4"/>
                            <line x1="6" y1="20" x2="6" y2="16"/>
                        </svg>
                    )
                },
                {
                    name: 'Auto-scaling',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
                            <polyline points="17 6 23 6 23 12"/>
                        </svg>
                    )
                },
                {
                    name: 'Monitoring 24/7',
                    icon: (
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                            <circle cx="12" cy="12" r="3"/>
                        </svg>
                    )
                }
            ]
        }
    ], []);

    useEffect(() => {
        // Hardware acceleration setup
        gsap.set([
            titleRef.current,
            subtitleRef.current,
            architectureRef.current,
            ...statsRef.current,
            ...cardsRef.current,
            ...counterRefs.current
        ], {
            force3D: true,
            backfaceVisibility: 'hidden',
            perspective: 1000
        });

        const ctx = gsap.context(() => {
            // ✅ ANIMAR COUNTERS DIRECTAMENTE
            stats.forEach((stat, index) => {
                const counterElement = counterRefs.current[index];
                if (!counterElement) return;

                const valueObj = { value: 0 };

                gsap.to(valueObj, {
                    value: stat.value,
                    duration: 2,
                    ease: 'power2.out',
                    onUpdate: () => {
                        counterElement.textContent = `${valueObj.value.toFixed(stat.decimals)}${stat.suffix}`;
                    },
                    onComplete: () => {
                        counterElement.textContent = `${stat.value.toFixed(stat.decimals)}${stat.suffix}`;
                    },
                    scrollTrigger: {
                        trigger: statsRef.current[index],
                        start: 'top 80%',
                        once: true
                    }
                });
            });

            // Stats cards con stagger ultra fluido
            statsRef.current.forEach((stat, index) => {
                if (!stat) return;
                gsap.fromTo(
                    stat,
                    { opacity: 0, y: 60, scale: 0.9 },
                    {
                        opacity: 1,
                        y: 0,
                        scale: 1,
                        duration: 1,
                        delay: index * 0.1,
                        ease: 'expo.out',
                        force3D: true,
                        scrollTrigger: {
                            trigger: stat,
                            start: 'top 85%',
                            toggleActions: 'play none none reverse'
                        }
                    }
                );
            });

            // Título y subtítulo
            gsap.fromTo(
                titleRef.current,
                { opacity: 0, y: 60 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1.2,
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
                { opacity: 0, y: 30 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1,
                    ease: 'power3.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: subtitleRef.current,
                        start: 'top 85%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            // Tech cards con parallax lateral alternado
            cardsRef.current.forEach((card, index) => {
                if (!card) return;

                gsap.fromTo(
                    card,
                    {
                        opacity: 0,
                        x: index % 2 === 0 ? -80 : 80,
                        rotateY: index % 2 === 0 ? -12 : 12,
                        scale: 0.9
                    },
                    {
                        opacity: 1,
                        x: 0,
                        rotateY: 0,
                        scale: 1,
                        duration: 1.2,
                        delay: index * 0.15,
                        ease: 'expo.out',
                        force3D: true,
                        scrollTrigger: {
                            trigger: card,
                            start: 'top 85%',
                            toggleActions: 'play none none reverse'
                        }
                    }
                );
            });

            // Architecture diagram con cascada
            const layers = architectureRef.current?.querySelectorAll(`.${styles.layerBox}, .${styles.layerGroup}`);
            if (layers) {
                gsap.fromTo(
                    layers,
                    { opacity: 0, scale: 0.85, y: 40 },
                    {
                        opacity: 1,
                        scale: 1,
                        y: 0,
                        duration: 0.8,
                        stagger: 0.15,
                        ease: 'back.out(1.2)',
                        force3D: true,
                        scrollTrigger: {
                            trigger: architectureRef.current,
                            start: 'top 75%',
                            toggleActions: 'play none none reverse'
                        }
                    }
                );
            }
        }, sectionRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(st => st.kill());
        };
    }, [stats]); // ✅ Dependencia necesaria para counters

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    return (
        <section ref={sectionRef} className={`${styles.technical} ${getThemeClass()}`}>
            <div className={styles.container}>
                {/* Stats Grid */}
                <div className={styles.statsGrid}>
                    {stats.map((stat, index) => (
                        <div
                            key={index}
                            ref={(el: HTMLDivElement | null) => {
                                statsRef.current[index] = el;
                            }}
                            className={styles.statCard}
                        >
                            <div className={styles.statValue}>
                                <span
                                    ref={(el: HTMLSpanElement | null) => {
                                        counterRefs.current[index] = el;
                                    }}
                                    className={styles.counter}
                                >
                                    0{stat.suffix}
                                </span>
                            </div>
                            <p className={styles.statLabel}>{stat.label}</p>
                        </div>
                    ))}
                </div>

                {/* Header */}
                <div className={styles.header}>
                    <h2 ref={titleRef} className={styles.sectionTitle}>
                        Stack tecnológico empresarial
                    </h2>
                    <p ref={subtitleRef} className={styles.sectionSubtitle}>
                        Construida con las mejores tecnologías para garantizar escalabilidad y rendimiento
                    </p>
                </div>

                {/* Tech Stack Grid */}
                <div className={styles.techGrid}>
                    {techStack.map((section, index) => (
                        <div
                            key={index}
                            ref={(el: HTMLDivElement | null) => {
                                cardsRef.current[index] = el;
                            }}
                            className={styles.techCard}
                        >
                            <h3 className={styles.techCategory}>{section.category}</h3>
                            <ul className={styles.techList}>
                                {section.items.map((item, idx) => (
                                    <li key={idx} className={styles.techItem}>
                                        <span className={styles.techIcon}>{item.icon}</span>
                                        <span className={styles.techName}>{item.name}</span>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ))}
                </div>

                {/* Architecture Diagram */}
                <div ref={architectureRef} className={styles.architecture}>
                    <h3 className={styles.architectureTitle}>Arquitectura de la API</h3>
                    <div className={styles.architectureDiagram}>
                        <div className={styles.layer}>
                            <div className={styles.layerBox}>
                                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <circle cx="12" cy="12" r="10"/>
                                    <path d="M2 12h20"/>
                                </svg>
                                <span>Cliente (Tu App)</span>
                            </div>
                        </div>

                        <div className={styles.arrow}>↓</div>

                        <div className={styles.layer}>
                            <div className={styles.layerBox}>
                                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <path d="M12 2L2 7l10 5 10-5-10-5z"/>
                                    <path d="M2 17l10 5 10-5"/>
                                    <path d="M2 12l10 5 10-5"/>
                                </svg>
                                <span>API Gateway + Rate Limiter</span>
                            </div>
                        </div>

                        <div className={styles.arrow}>↓</div>

                        <div className={styles.layer}>
                            <div className={`${styles.layerBox} ${styles.wide}`}>
                                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <rect x="2" y="3" width="20" height="14" rx="2" ry="2"/>
                                    <line x1="8" y1="21" x2="16" y2="21"/>
                                    <line x1="12" y1="17" x2="12" y2="21"/>
                                </svg>
                                <span>Spring Boot Application</span>
                            </div>
                        </div>

                        <div className={styles.arrow}>↓</div>

                        <div className={styles.layer}>
                            <div className={styles.layerGroup}>
                                <div className={styles.layerBox}>
                                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                        <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                                    </svg>
                                    <span>PostgreSQL</span>
                                </div>
                                <div className={styles.layerBox}>
                                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                        <path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z"/>
                                    </svg>
                                    <span>Redis Cache</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default React.memo(TechnicalSection);
