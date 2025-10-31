import React, { useEffect, useRef } from 'react';
import { animateStatsSection } from './StatsSection.animations';
import './StatsSection.css';

interface Stat {
    id: string;
    value: number;
    suffix: string;
    label: string;
    icon: string;
}

const stats: Stat[] = [
    {
        id: 'models',
        value: 36,
        suffix: '',
        label: 'Modelos Disponibles',
        icon: '✈️'
    },
    {
        id: 'manufacturers',
        value: 2,
        suffix: '',
        label: 'Fabricantes Principales',
        icon: '🏭'
    },
    {
        id: 'families',
        value: 12,
        suffix: '+',
        label: 'Familias de Aeronaves',
        icon: '📊'
    },
    {
        id: 'specs',
        value: 50,
        suffix: '+',
        label: 'Especificaciones Técnicas',
        icon: '📈'
    }
];

const StatsSection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (sectionRef.current) {
            animateStatsSection(sectionRef.current);
        }
    }, []);

    return (
        <section className="statsSection" ref={sectionRef}>
            <div className="statsSection__container">
                <div className="statsSection__header">
                    <h2 className="statsSection__title">SkyVault en Números</h2>
                    <p className="statsSection__subtitle">
                        Datos y estadísticas de nuestra plataforma
                    </p>
                </div>

                <div className="statsSection__grid">
                    {stats.map((stat) => (
                        <div key={stat.id} className="statCard liquidGlass">
                            <div className="statCard__icon">{stat.icon}</div>
                            <div className="statCard__value">
                                <span className="statCard__number">{stat.value}</span>
                                <span className="statCard__suffix">{stat.suffix}</span>
                            </div>
                            <div className="statCard__label">{stat.label}</div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default StatsSection;
