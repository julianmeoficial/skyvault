import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import ThemeToggle from '../../shared/components/ui/ThemeToggle/ThemeToggle';
import SectionTitle from '../../shared/components/ui/SectionTitle/SectionTitle';
import AnimatedBorderCard from '../../shared/components/ui/AnimatedBorderCard/AnimatedBorderCard';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './DataSources.module.css';

interface DataSource {
    name: string;
    description: string;
    icon: React.ReactNode;
}

const dataSources: DataSource[] = [
    {
        name: 'Airbus',
        description: 'Especificaciones técnicas oficiales de aeronaves Airbus obtenidas directamente de la documentación del fabricante.',
        icon: (
            <svg viewBox="0 0 24 24" fill="currentColor" stroke="none">
                <path d="M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z"/>
            </svg>
        )
    },
    {
        name: 'Boeing',
        description: 'Datos técnicos y características oficiales de modelos Boeing extraídos de documentación técnica certificada.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
            </svg>
        )
    },
    {
        name: 'Aviation Week',
        description: 'Información actualizada sobre tendencias de la industria, análisis de mercado y desarrollos tecnológicos.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
        )
    },
    {
        name: 'FlightGlobal',
        description: 'Base de datos especializada en aviación comercial con estadísticas operativas y datos de flota actualizados.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
        )
    },
    {
        name: 'Jane\'s Aircraft',
        description: 'Referencia reconocida mundialmente en especificaciones técnicas, historial y capacidades de aeronaves.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
            </svg>
        )
    },
    {
        name: 'ICAO',
        description: 'Estándares internacionales de aviación civil, normativas de seguridad y certificaciones emitidas por la OACI.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
            </svg>
        )
    }
];

const DataSources: React.FC = () => {
    const contentRef = useRef<HTMLDivElement>(null);
    const cardsRef = useRef<HTMLDivElement[]>([]);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (contentRef.current) {
            gsap.fromTo(
                contentRef.current,
                { opacity: 0, y: 30 },
                { opacity: 1, y: 0, duration: 0.8, ease: 'power3.out' }
            );
        }

        if (cardsRef.current.length > 0) {
            gsap.fromTo(
                cardsRef.current,
                { opacity: 0, scale: 0.9 },
                {
                    opacity: 1,
                    scale: 1,
                    duration: 0.5,
                    stagger: 0.1,
                    ease: 'back.out(1.2)',
                    delay: 0.3
                }
            );
        }
    }, []);

    const addToRefs = (el: HTMLDivElement | null) => {
        if (el && !cardsRef.current.includes(el)) {
            cardsRef.current.push(el);
        }
    };

    return (
        <div className={styles.dataSourcesPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al inicio" />
                <ThemeToggle />
            </div>

            <div className={styles.container}>
                <div className={styles.header}>
                    <SectionTitle
                        icon={
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 7v10c0 2.21 3.582 4 8 4s8-1.79 8-4V7M4 7c0 2.21 3.582 4 8 4s8-1.79 8-4M4 7c0-2.21 3.582-4 8-4s8 1.79 8 4m0 5c0 2.21-3.582 4-8 4s-8-1.79-8-4" />
                            </svg>
                        }
                    >
                        Fuentes de Datos
                    </SectionTitle>
                </div>

                <div ref={contentRef} className={styles.content}>
                    <div className={styles.intro}>
                        <p>
                            La información presentada en SkyVault proviene de fuentes oficiales y reconocidas
                            en la industria de la aviación. Nos comprometemos a mantener datos precisos y
                            actualizados para ofrecer comparaciones confiables.
                        </p>
                    </div>

                    <div className={styles.sourcesGrid}>
                        {dataSources.map((source, index) => (
                            <div key={index} ref={addToRefs} className={styles.sourceCard}>
                                <AnimatedBorderCard>
                                    <div className={styles.cardContent}>
                                        <div className={styles.cardIcon}>
                                            {source.icon}
                                        </div>
                                        <h3 className={styles.cardTitle}>{source.name}</h3>
                                        <p className={styles.cardDescription}>{source.description}</p>
                                    </div>
                                </AnimatedBorderCard>
                            </div>
                        ))}
                    </div>

                    <div className={styles.disclaimer}>
                        <h3 className={styles.disclaimerTitle}>Compromiso con la Calidad</h3>
                        <p className={styles.disclaimerText}>
                            Todas las especificaciones y datos técnicos se verifican regularmente para
                            garantizar su precisión. Si detectas alguna inconsistencia, por favor contáctanos
                            para que podamos corregirla de inmediato.
                        </p>
                    </div>

                    <div className={styles.lastUpdated}>
                        Última verificación de fuentes: Octubre 2025
                    </div>
                </div>
            </div>

            <Footer />
        </div>
    );
};

export default DataSources;
