import React from 'react';
import { MissionGlassCard } from '../../../shared/components/ui/MissionGlassCard';
import { AnimatedBorderCard } from '../../../shared/components/ui/AnimatedBorderCard';
import { SectionTitle } from '../../../shared/components/ui/SectionTitle';

const TechnologySection: React.FC = () => {
    const technologies = [
        { name: 'React 18', category: 'Frontend', description: 'Biblioteca UI moderna y reactiva' },
        { name: 'TypeScript', category: 'Frontend', description: 'Tipado estático para mayor seguridad' },
        { name: 'Vite', category: 'Frontend', description: 'Build tool ultrarrápido' },
        { name: 'GSAP', category: 'Frontend', description: 'Animaciones fluidas y profesionales' },
        { name: 'Spring Boot', category: 'Backend', description: 'Framework robusto para APIs REST' },
        { name: 'PostgreSQL', category: 'Backend', description: 'Base de datos relacional potente' }
    ];

    const stats = [
        { value: '36', label: 'Aeronaves' },
        { value: '2', label: 'Fabricantes' },
        { value: '12', label: 'Familias' }
    ];

    return (
        <section className="section technologySection">
            <div className="section__container">
                <SectionTitle>Stack Tecnológico</SectionTitle>

                <div className="technologySection__grid">
                    <MissionGlassCard>
                        <div className="technologySection__techList">
                            {technologies.map((tech, index) => (
                                <div key={index} className="techItem">
                                    <div className="techItem__info">
                                        <h3 className="techItem__name">{tech.name}</h3>
                                        <span className="techItem__category">{tech.category}</span>
                                    </div>
                                    <p className="techItem__description">{tech.description}</p>
                                </div>
                            ))}
                        </div>
                    </MissionGlassCard>

                    <AnimatedBorderCard
                        icon=""
                        title="Estadísticas del Proyecto"
                        description=""
                    >
                        <div className="technologySection__statsGrid">
                            {stats.map((stat, index) => (
                                <div key={index} className="statItem">
                                    <div className="statItem__value">{stat.value}</div>
                                    <div className="statItem__label">{stat.label}</div>
                                </div>
                            ))}
                        </div>
                    </AnimatedBorderCard>
                </div>
            </div>
        </section>
    );
};

export default TechnologySection;
