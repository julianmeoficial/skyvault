import React from 'react';
import { ValueCard } from '../../../shared/components/ui/ValueCard';
import { MissionGlassCard } from '../../../shared/components/ui/MissionGlassCard';

const MissionSection: React.FC = () => {
    const values = [
        {
            icon: (
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
                </svg>
            ),
            title: 'Precisión',
            description: 'Datos técnicos verificados y actualizados'
        },
        {
            icon: (
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M13 7h-2v4H7v2h4v4h2v-4h4v-2h-4V7zm-1-5C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z"/>
                </svg>
            ),
            title: 'Simplicidad',
            description: 'Interfaz minimalista e intuitiva'
        },
        {
            icon: (
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/>
                </svg>
            ),
            title: 'Accesibilidad',
            description: 'Información abierta para todos'
        }
    ];

    return (
        <section className="section missionSection">
            <div className="section__container">
                <div className="missionSection__grid">
                    <MissionGlassCard>
                        <h2 className="missionSection__title">Nuestra Misión</h2>
                        <p className="missionSection__text">
                            SkyVault nace con el objetivo de democratizar el acceso a información técnica
                            sobre aviación comercial, proporcionando una plataforma minimalista que permite
                            comparar de forma clara y precisa los 36 modelos principales de Airbus y Boeing.
                        </p>
                    </MissionGlassCard>

                    <div className="missionSection__values">
                        {values.map((value, index) => (
                            <ValueCard
                                key={index}
                                icon={value.icon}
                                title={value.title}
                                description={value.description}
                            />
                        ))}
                    </div>
                </div>
            </div>
        </section>
    );
};

export default MissionSection;
