import React from 'react';
import { AnimatedBorderCard } from '../../../shared/components/ui/AnimatedBorderCard';
import { SectionTitle } from '../../../shared/components/ui/SectionTitle';

// Importa los iconos desde public/assets
import planeIcon from '/assets/icons/specifications/planeminimal.png';
import visualizationIcon from '/assets/icons/specifications/visualization.png';
import dataIcon from '/assets/icons/specifications/data.png';

const ProjectSection: React.FC = () => {
    const features = [
        {
            icon: planeIcon,
            title: '36 Modelos Comparables',
            description: 'Catálogo completo de aviones comerciales de Airbus y Boeing con especificaciones detalladas'
        },
        {
            icon: visualizationIcon,
            title: 'Visualización Intuitiva',
            description: 'Comparaciones lado a lado con gráficos y tablas interactivas de fácil interpretación'
        },
        {
            icon: dataIcon,
            title: 'Datos Técnicos Precisos',
            description: 'Información verificada sobre alcance, capacidad, dimensiones y rendimiento'
        }
    ];

    return (
        <section className="section projectSection">
            <div className="section__container">
                <SectionTitle>El Proyecto</SectionTitle>
                <div className="projectSection__grid">
                    {features.map((feature, index) => (
                        <AnimatedBorderCard
                            key={index}
                            icon={feature.icon}
                            title={feature.title}
                            description={feature.description}
                        />
                    ))}
                </div>
            </div>
        </section>
    );
};

export default ProjectSection;
