import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import ThemeToggle from '../../shared/components/ui/ThemeToggle/ThemeToggle';
import SectionTitle from '../../shared/components/ui/SectionTitle/SectionTitle';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './Privacy.module.css';

const Privacy: React.FC = () => {
    const contentRef = useRef<HTMLDivElement>(null);
    const sectionsRef = useRef<HTMLDivElement[]>([]);

    useEffect(() => {
        window.scrollTo(0, 0);

        // Animación de entrada
        if (contentRef.current) {
            gsap.fromTo(
                contentRef.current,
                { opacity: 0, y: 30 },
                { opacity: 1, y: 0, duration: 0.8, ease: 'power3.out' }
            );
        }

        // Animación de secciones con stagger
        if (sectionsRef.current.length > 0) {
            gsap.fromTo(
                sectionsRef.current,
                { opacity: 0, x: -20 },
                {
                    opacity: 1,
                    x: 0,
                    duration: 0.6,
                    stagger: 0.1,
                    ease: 'power2.out',
                    delay: 0.3
                }
            );
        }
    }, []);

    const addToRefs = (el: HTMLDivElement | null) => {
        if (el && !sectionsRef.current.includes(el)) {
            sectionsRef.current.push(el);
        }
    };

    return (
        <div className={styles.privacyPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al inicio" />
                <ThemeToggle />
            </div>

            <div className={styles.container}>
                <div className={styles.header}>
                    <SectionTitle
                        icon={
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                            </svg>
                        }
                    >
                        Política de Privacidad
                    </SectionTitle>
                </div>

                <div ref={contentRef} className={styles.content}>
                    <div className={styles.intro}>
                        <p>
                            En SkyVault, valoramos y respetamos tu privacidad. Esta política describe cómo
                            recopilamos, usamos y protegemos la información personal que obtienes al utilizar
                            nuestra plataforma de comparación de aeronaves.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>Información que recopilamos</h2>
                        <p className={styles.sectionText}>
                            Recopilamos datos personales que tú nos proporcionas al registrarte, iniciar sesión,
                            completar formularios o interactuar con nuestros servicios, tales como nombre, correo
                            electrónico y preferencias de usuario.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>Uso de la información</h2>
                        <p className={styles.sectionText}>
                            Utilizamos tu información para ofrecer un servicio personalizado, guardar tu historial
                            de comparaciones, responder tus consultas, enviarte notificaciones sobre actualizaciones
                            y mejorar nuestra plataforma.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>Protección de datos</h2>
                        <p className={styles.sectionText}>
                            Implementamos medidas de seguridad técnicas y administrativas para proteger tu
                            información contra acceso no autorizado, alteración o pérdida.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>Compartir información</h2>
                        <p className={styles.sectionText}>
                            No vendemos ni alquilamos tu información personal a terceros. Solo compartimos datos con
                            proveedores de servicios bajo acuerdos estrictos de confidencialidad para el
                            funcionamiento de la plataforma.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>Tus derechos</h2>
                        <p className={styles.sectionText}>
                            Puedes acceder, corregir o eliminar tus datos personales en cualquier momento
                            contactándonos a través de nuestra sección de contacto.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>Cambios en la política</h2>
                        <p className={styles.sectionText}>
                            Nos reservamos el derecho de actualizar esta política según la legislación vigente o
                            mejoras en nuestros procesos. Te notificaremos cualquier cambio relevante.
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

export default Privacy;
