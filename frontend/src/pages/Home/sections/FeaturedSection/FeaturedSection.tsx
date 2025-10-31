import React, { useEffect, useRef } from 'react';
import { animateFeaturedSection } from './FeaturedSection.animations';
import './FeaturedSection.css';

interface AircraftCard {
    id: string;
    name: string;
    manufacturer: 'Airbus' | 'Boeing';
    image: string;
    category: string;
    description: string;
}

const featuredAircraft: AircraftCard[] = [
    {
        id: 'a350',
        name: 'A350-1000',
        manufacturer: 'Airbus',
        image: '/assets/images/aircraft/airbus/a350.jpg',
        category: 'Wide-body',
        description: 'El avión wide-body más moderno y eficiente de Airbus'
    },
    {
        id: 'b787',
        name: '787 Dreamliner',
        manufacturer: 'Boeing',
        image: '/assets/images/aircraft/boeing/b787.jpg',
        category: 'Wide-body',
        description: 'Innovación en materiales compuestos y eficiencia'
    },
    {
        id: 'a320neo',
        name: 'A320neo',
        manufacturer: 'Airbus',
        image: '/assets/images/aircraft/airbus/a320neo.jpg',
        category: 'Narrow-body',
        description: 'La familia más vendida del mundo con tecnología NEO'
    },
    {
        id: 'b737max',
        name: '737 MAX',
        manufacturer: 'Boeing',
        image: '/assets/images/aircraft/boeing/b737max.jpg',
        category: 'Narrow-body',
        description: 'Evolución del avión comercial más exitoso de Boeing'
    }
];

const FeaturedSection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (sectionRef.current) {
            animateFeaturedSection(sectionRef.current);
        }
    }, []);

    return (
        <section className="featuredSection" id="featured" ref={sectionRef}>
            <div className="featuredSection__container">
                <div className="featuredSection__header">
                    <h2 className="featuredSection__title">Modelos Destacados</h2>
                    <p className="featuredSection__subtitle">
                        Explora los aviones más populares de Airbus y Boeing
                    </p>
                </div>

                <div className="featuredSection__grid">
                    {featuredAircraft.map((aircraft) => (
                        <div
                            key={aircraft.id}
                            className="featuredCard liquidGlass"
                        >
                            <div className="featuredCard__image-container">
                                <div className="featuredCard__image-placeholder">
                                    <span className="featuredCard__manufacturer-badge">
                                        {aircraft.manufacturer}
                                    </span>
                                    <div className="featuredCard__icon">✈️</div>
                                </div>
                            </div>

                            <div className="featuredCard__content">
                                <span className="featuredCard__category">{aircraft.category}</span>
                                <h3 className="featuredCard__name">{aircraft.name}</h3>
                                <p className="featuredCard__description">{aircraft.description}</p>

                                <a href={`/aircraft/${aircraft.id}`} className="featuredCard__button">
                                    <span>Ver Detalles</span>
                                    <span className="featuredCard__button-arrow">→</span>
                                </a>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="featuredSection__cta">
                    <a href="/catalog" className="featuredSection__button liquidGlass">
                        <span>Ver Catálogo Completo</span>
                    </a>
                </div>
            </div>
        </section>
    );
};

export default FeaturedSection;
