import React, { useEffect, useRef } from 'react';
import { animateAboutSection } from './AboutSection.animations';
import './AboutSection.css';

const AboutSection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (sectionRef.current) {
            animateAboutSection(sectionRef.current);
        }
    }, []);

    return (
        <section className="aboutSection" ref={sectionRef}>
            <div className="aboutSection__container">
                <div className="aboutSection__content">
                    <h2 className="aboutSection__title">¿Qué es SkyVault?</h2>
                    <p className="aboutSection__text">
                        SkyVault es la plataforma definitiva para comparar modelos de aviación comercial.
                        Explora especificaciones técnicas, rendimiento y características de 36 aeronaves
                        de <strong>Airbus</strong> y <strong>Boeing</strong> en un solo lugar.
                    </p>
                    <p className="aboutSection__text">
                        Diseñada para entusiastas de la aviación, profesionales de la industria y estudiantes,
                        nuestra plataforma ofrece datos precisos y actualizados con una experiencia visual minimalista.
                    </p>
                </div>

                <div className="aboutSection__features">
                    <div className="aboutSection__feature liquidGlass">
                        <div className="aboutSection__feature-icon">✈️</div>
                        <h3 className="aboutSection__feature-title">36 Modelos</h3>
                        <p className="aboutSection__feature-text">
                            Catálogo completo de aeronaves comerciales Airbus y Boeing
                        </p>
                    </div>

                    <div className="aboutSection__feature liquidGlass">
                        <div className="aboutSection__feature-icon">📊</div>
                        <h3 className="aboutSection__feature-title">Datos Precisos</h3>
                        <p className="aboutSection__feature-text">
                            Especificaciones técnicas verificadas y actualizadas
                        </p>
                    </div>

                    <div className="aboutSection__feature liquidGlass">
                        <div className="aboutSection__feature-icon">⚡</div>
                        <h3 className="aboutSection__feature-title">Comparación Rápida</h3>
                        <p className="aboutSection__feature-text">
                            Compara hasta 3 modelos simultáneamente
                        </p>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default AboutSection;
