import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import ThemeToggle from '../../shared/components/ui/ThemeToggle/ThemeToggle';
import SectionTitle from '../../shared/components/ui/SectionTitle/SectionTitle';
import AnimatedBorderCard from '../../shared/components/ui/AnimatedBorderCard/AnimatedBorderCard';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './Cookies.module.css';

interface CookieType {
    name: string;
    description: string;
    icon: React.ReactNode;
}

const cookieTypes: CookieType[] = [
    {
        name: 'Cookies esenciales',
        description: 'Necesarias para que la plataforma funcione correctamente, por ejemplo para mantener tu sesión iniciada.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
            </svg>
        )
    },
    {
        name: 'Cookies de rendimiento',
        description: 'Nos permiten analizar el uso del sitio y optimizar su funcionamiento.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
            </svg>
        )
    },
    {
        name: 'Cookies de funcionalidad',
        description: 'Guardan tus preferencias, como idioma o temas visuales.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
        )
    },
    {
        name: 'Cookies publicitarias',
        description: 'Se usan para mostrar contenido personalizado y analizar campañas (si aplican).',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
            </svg>
        )
    },
    {
        name: 'Cookies de análisis',
        description: 'Recopilan información sobre cómo interactúas con el sitio para mejorar tu experiencia.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
            </svg>
        )
    },
    {
        name: 'Cookies de terceros',
        description: 'Provienen de servicios externos integrados en la plataforma, como análisis o redes sociales.',
        icon: (
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
        )
    }
];

const Cookies: React.FC = () => {
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
        <div className={styles.cookiesPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al inicio" />
                <ThemeToggle />
            </div>

            <div className={styles.container}>
                <div className={styles.header}>
                    <SectionTitle
                        icon={
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                        }
                    >
                        Política de Cookies
                    </SectionTitle>
                </div>

                <div ref={contentRef} className={styles.content}>
                    <div className={styles.intro}>
                        <p>
                            En SkyVault utilizamos cookies para mejorar tu experiencia al navegar en nuestra
                            plataforma. A continuación te explicamos qué son, cómo las usamos y cómo puedes
                            gestionarlas.
                        </p>
                    </div>

                    <div className={styles.whatAreCookies}>
                        <h2 className={styles.sectionTitle}>¿Qué son las cookies?</h2>
                        <p className={styles.sectionText}>
                            Las cookies son pequeños archivos de texto que se almacenan en tu dispositivo cuando
                            visitas un sitio web. Nos ayudan a recordar tus preferencias, facilitar el uso del
                            sitio y recopilar datos anónimos para mejorar nuestros servicios.
                        </p>
                    </div>

                    <div className={styles.cookieTypesSection}>
                        <h2 className={styles.sectionTitle}>Cookies que utilizamos</h2>
                        <div className={styles.cookiesGrid}>
                            {cookieTypes.map((cookie, index) => (
                                <div key={index} ref={addToRefs} className={styles.cookieCard}>
                                    <AnimatedBorderCard>
                                        <div className={styles.cardContent}>
                                            <div className={styles.cardIcon}>
                                                {cookie.icon}
                                            </div>
                                            <h3 className={styles.cardTitle}>{cookie.name}</h3>
                                            <p className={styles.cardDescription}>{cookie.description}</p>
                                        </div>
                                    </AnimatedBorderCard>
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className={styles.management}>
                        <h2 className={styles.sectionTitle}>Gestión y control de cookies</h2>
                        <p className={styles.sectionText}>
                            Puedes configurar tu navegador para aceptar, bloquear o eliminar cookies según tu
                            preferencia. Ten en cuenta que desactivar algunas cookies puede afectar la
                            funcionalidad del sitio.
                        </p>
                    </div>

                    <div className={styles.consent}>
                        <h2 className={styles.sectionTitle}>Consentimiento</h2>
                        <p className={styles.sectionText}>
                            Al continuar navegando en SkyVault, aceptas el uso de cookies conforme a esta
                            política. Puedes cambiar tu configuración en cualquier momento.
                        </p>
                    </div>

                    <div className={styles.lastUpdated}>
                        Última actualización: Octubre 2025
                    </div>
                </div>
            </div>

            <Footer />
        </div>
    );
};

export default Cookies;
