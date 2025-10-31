import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import ThemeToggle from '../../shared/components/ui/ThemeToggle/ThemeToggle';
import SectionTitle from '../../shared/components/ui/SectionTitle/SectionTitle';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './Terms.module.css';

const Terms: React.FC = () => {
    const contentRef = useRef<HTMLDivElement>(null);
    const sectionsRef = useRef<HTMLDivElement[]>([]);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (contentRef.current) {
            gsap.fromTo(
                contentRef.current,
                { opacity: 0, y: 30 },
                { opacity: 1, y: 0, duration: 0.8, ease: 'power3.out' }
            );
        }

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
        <div className={styles.termsPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al inicio" />
                <ThemeToggle />
            </div>

            <div className={styles.container}>
                <div className={styles.header}>
                    <SectionTitle
                        icon={
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                            </svg>
                        }
                    >
                        Términos y Condiciones
                    </SectionTitle>
                </div>

                <div ref={contentRef} className={styles.content}>
                    <div className={styles.intro}>
                        <p>
                            Al utilizar la plataforma SkyVault, aceptas cumplir con estos Términos de Servicio
                            y todas las leyes aplicables. Si no estás de acuerdo, por favor no uses el sitio.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>1. Uso de la plataforma</h2>
                        <p className={styles.sectionText}>
                            SkyVault proporciona un servicio de comparación de modelos de aeronaves para uso
                            personal e informativo. Queda prohibido usar la plataforma para fines ilegales,
                            fraudulentos o que dañen la experiencia de otros usuarios.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>2. Registro y seguridad</h2>
                        <p className={styles.sectionText}>
                            Para ciertas funcionalidades, deberás crear una cuenta. Eres responsable de mantener
                            la confidencialidad de tus credenciales y de todas las actividades que ocurran bajo
                            tu cuenta.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>3. Propiedad intelectual</h2>
                        <p className={styles.sectionText}>
                            Todos los contenidos, imágenes, logos y datos presentados en SkyVault son propiedad
                            de sus respectivos dueños o licenciantes, protegidos por leyes de propiedad intelectual.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>4. Limitación de responsabilidad</h2>
                        <p className={styles.sectionText}>
                            SkyVault se esfuerza por proporcionar información precisa, pero no garantiza la
                            exactitud, completitud o actualidad de los datos. No somos responsables por daños
                            directos o indirectos derivados del uso de la plataforma.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>5. Modificaciones del servicio y términos</h2>
                        <p className={styles.sectionText}>
                            SkyVault se reserva el derecho de modificar o interrumpir el servicio, así como
                            actualizar estos términos en cualquier momento. Las modificaciones serán publicadas
                            en esta sección.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>6. Terminación</h2>
                        <p className={styles.sectionText}>
                            Podemos suspender o cancelar tu acceso si violas estos términos o por motivos
                            justificados.
                        </p>
                    </div>

                    <div ref={addToRefs} className={styles.section}>
                        <h2 className={styles.sectionTitle}>7. Ley aplicable y jurisdicción</h2>
                        <p className={styles.sectionText}>
                            Estos términos se rigen por la legislación colombiana y cualquier disputa será
                            resuelta por los tribunales competentes en Colombia.
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

export default Terms;
