import React, { useEffect, useRef } from 'react';
import { animateCTASection } from './CTASection.animations';
import './CTASection.css';

const CTASection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (sectionRef.current) {
            animateCTASection(sectionRef.current);
        }
    }, []);

    return (
        <section className="ctaSection" ref={sectionRef}>
            <div className="ctaSection__container">
                <div className="ctaSection__content liquidGlass">
                    <div className="ctaSection__icon">🚀</div>
                    <h2 className="ctaSection__title">
                        ¿Listo para Explorar el Catálogo?
                    </h2>
                    <p className="ctaSection__text">
                        Compara especificaciones, descubre diferencias y encuentra el avión perfecto
                        para tus necesidades. Todo en un solo lugar.
                    </p>

                    <div className="ctaSection__buttons">
                        <a href="/catalog" className="ctaSection__button ctaSection__button--primary liquidGlass">
                            <span>Explorar Catálogo</span>
                        </a>
                        <a href="/compare" className="ctaSection__button ctaSection__button--secondary liquidGlass">
                            <span>Comparar Modelos</span>
                        </a>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default CTASection;
