import React, { useEffect, useRef, useState, useMemo, useCallback } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import CodeBlock from '../CodeBlock/CodeBlock';
import styles from './EndpointsPreview.module.css';

gsap.registerPlugin(ScrollTrigger);

const EndpointsPreview: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const tabsRef = useRef<(HTMLButtonElement | null)[]>([]);
    const contentRef = useRef<HTMLDivElement>(null);
    const timelineRef = useRef<gsap.core.Timeline | null>(null);

    const [activeEndpoint, setActiveEndpoint] = useState(0);
    const { theme } = useTheme();

    // Memoizar endpoints data para evitar recreaciones
    const endpoints = useMemo(() => [
        {
            method: 'GET',
            path: '/api/v1/aircraft',
            description: 'Obtén el catálogo completo de aeronaves con paginación y filtros avanzados',
            params: ['page', 'size', 'manufacturer', 'family'],
            response: `{
  "content": [
    {
      "id": "A350-900",
      "manufacturer": "Airbus",
      "family": "A350",
      "model": "A350-900",
      "range": 15000,
      "capacity": {
        "min": 300,
        "max": 350
      },
      "engines": "Rolls-Royce Trent XWB"
    }
  ],
  "totalElements": 36,
  "totalPages": 4
}`
        },
        {
            method: 'GET',
            path: '/api/v1/aircraft/{id}',
            description: 'Consulta información detallada de una aeronave específica',
            params: ['id'],
            response: `{
  "id": "B787-9",
  "manufacturer": "Boeing",
  "family": "787",
  "model": "787-9 Dreamliner",
  "specifications": {
    "range": 14140,
    "cruiseSpeed": 0.85,
    "maxTakeoffWeight": 254000,
    "wingspan": 60.1,
    "length": 62.8
  },
  "performance": {
    "fuelEfficiency": "20% mejor",
    "emissionReduction": "25% menos CO2"
  }
}`
        },
        {
            method: 'GET',
            path: '/api/v1/manufacturers',
            description: 'Lista todos los fabricantes con estadísticas de sus flotas',
            params: ['includeStats'],
            response: `{
  "manufacturers": [
    {
      "id": "airbus",
      "name": "Airbus",
      "country": "France",
      "aircraftCount": 18,
      "families": ["A220", "A320", "A330", "A350", "A380"]
    },
    {
      "id": "boeing",
      "name": "Boeing",
      "country": "United States",
      "aircraftCount": 18,
      "families": ["737", "747", "767", "777", "787"]
    }
  ]
}`
        },
        {
            method: 'POST',
            path: '/api/v1/compare',
            description: 'Compara hasta 3 aeronaves con métricas detalladas',
            params: ['aircraftIds[]'],
            response: `{
  "comparison": {
    "aircraft": ["A350-900", "B787-9"],
    "metrics": {
      "range": {
        "A350-900": 15000,
        "B787-9": 14140,
        "winner": "A350-900"
      },
      "capacity": {
        "A350-900": 325,
        "B787-9": 290,
        "winner": "A350-900"
      }
    }
  }
}`
        }
    ], []);

    // Animaciones de entrada SOLO UNA VEZ
    useEffect(() => {
        gsap.set([titleRef.current, subtitleRef.current, contentRef.current], {
            force3D: true,
            backfaceVisibility: 'hidden',
            perspective: 1000
        });

        const ctx = gsap.context(() => {
            // Título
            gsap.fromTo(
                titleRef.current,
                { opacity: 0, y: 60, scale: 0.95 },
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

            // Subtítulo
            gsap.fromTo(
                subtitleRef.current,
                { opacity: 0, y: 30 },
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

            // Tabs con stagger - SOLO una vez
            tabsRef.current.forEach((tab, index) => {
                if (!tab) return;
                gsap.fromTo(
                    tab,
                    { opacity: 0, x: -80, rotateY: -15 },
                    {
                        opacity: 1,
                        x: 0,
                        rotateY: 0,
                        duration: 0.8,
                        delay: index * 0.08,
                        ease: 'expo.out',
                        force3D: true,
                        scrollTrigger: {
                            trigger: sectionRef.current,
                            start: 'top 70%',
                            toggleActions: 'play none none reverse'
                        }
                    }
                );
            });

            // Content container
            gsap.fromTo(
                contentRef.current,
                { opacity: 0, scale: 0.92, y: 50 },
                {
                    opacity: 1,
                    scale: 1,
                    y: 0,
                    duration: 1,
                    ease: 'expo.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: contentRef.current,
                        start: 'top 75%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );
        }, sectionRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(st => st.kill());
        };
    }, []); // ✅ Sin dependencias - solo una vez

    // Cambio de endpoint optimizado con FLIP technique
    const handleEndpointChange = useCallback((newIndex: number) => {
        if (newIndex === activeEndpoint) return;

        // Matar animación previa si existe
        if (timelineRef.current) {
            timelineRef.current.kill();
        }

        // Timeline ultra rápida para cambio de contenido
        const tl = gsap.timeline({
            defaults: {
                ease: 'power3.inOut',
                force3D: true
            }
        });

        // Salida del contenido actual
        tl.to(contentRef.current, {
            opacity: 0,
            x: -30,
            duration: 0.2,
            onComplete: () => {
                setActiveEndpoint(newIndex);
            }
        });

        // Entrada del nuevo contenido
        tl.fromTo(
            contentRef.current,
            { opacity: 0, x: 30 },
            { opacity: 1, x: 0, duration: 0.3 },
            '+=0.05'
        );

        timelineRef.current = tl;
    }, [activeEndpoint]);

    // Copiar al portapapeles
    const handleCopy = useCallback(() => {
        navigator.clipboard.writeText(endpoints[activeEndpoint].response);
        // Feedback visual con GSAP (opcional)
    }, [activeEndpoint, endpoints]);

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    // Memoizar endpoint activo para evitar recalculos
    const currentEndpoint = useMemo(() => endpoints[activeEndpoint], [endpoints, activeEndpoint]);

    return (
        <section ref={sectionRef} className={`${styles.endpoints} ${getThemeClass()}`}>
            <div className={styles.container}>
                <h2 ref={titleRef} className={styles.sectionTitle}>
                    Endpoints poderosos
                </h2>
                <p ref={subtitleRef} className={styles.sectionSubtitle}>
                    RESTful API con respuestas JSON optimizadas para máximo rendimiento
                </p>

                <div className={styles.content}>
                    {/* Tabs de endpoints */}
                    <div className={styles.endpointTabs}>
                        {endpoints.map((endpoint, index) => (
                            <button
                                key={index}
                                ref={(el: HTMLButtonElement | null) => {
                                    tabsRef.current[index] = el;
                                }}
                                className={`${styles.endpointTab} ${activeEndpoint === index ? styles.active : ''}`}
                                onClick={() => handleEndpointChange(index)}
                                type="button"
                            >
                                <span className={`${styles.method} ${styles[endpoint.method.toLowerCase()]}`}>
                                    {endpoint.method}
                                </span>
                                <span className={styles.path}>{endpoint.path}</span>
                            </button>
                        ))}
                    </div>

                    {/* Detalles del endpoint activo */}
                    <div ref={contentRef} className={styles.endpointDetails}>
                        <div className={styles.detailsHeader}>
                            <div className={styles.methodBadge}>
                                <span className={`${styles.methodLarge} ${styles[currentEndpoint.method.toLowerCase()]}`}>
                                    {currentEndpoint.method}
                                </span>
                                <span className={styles.pathLarge}>{currentEndpoint.path}</span>
                            </div>
                            <p className={styles.description}>{currentEndpoint.description}</p>
                        </div>

                        {/* Parámetros */}
                        <div className={styles.parameters}>
                            <h4 className={styles.parametersTitle}>
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <circle cx="12" cy="12" r="3"/>
                                    <path d="M12 1v6m0 6v6m5.2-10.2l-4.3 4.3m0 5.8l4.3 4.3M23 12h-6m-6 0H5m15.2 5.2l-4.3-4.3m-5.8 0l-4.3 4.3"/>
                                </svg>
                                Parámetros
                            </h4>
                            <div className={styles.paramsList}>
                                {currentEndpoint.params.map((param, idx) => (
                                    <span key={idx} className={styles.param}>{param}</span>
                                ))}
                            </div>
                        </div>

                        {/* Código de respuesta */}
                        <div className={styles.codeBlockWrapper}>
                            <div className={styles.codeHeader}>
                                <span className={styles.responseLabel}>Response JSON</span>
                                <div className={styles.codeActions}>
                                    <button
                                        className={styles.copyButton}
                                        onClick={handleCopy}
                                        type="button"
                                    >
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                            <rect x="9" y="9" width="13" height="13" rx="2" ry="2"/>
                                            <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/>
                                        </svg>
                                        Copy
                                    </button>
                                </div>
                            </div>
                            <CodeBlock code={currentEndpoint.response} language="json" />
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default React.memo(EndpointsPreview);
