import React from 'react';
import { GlassButton } from '../../../shared/components/ui/GlassButton';
import { useNavigate } from 'react-router-dom';

const CTASection: React.FC = () => {
    const navigate = useNavigate();

    return (
        <section className="ctaSection">
            <div className="ctaSection__content">
                <h2 className="ctaSection__title">Explora el Catálogo</h2>
                <p className="ctaSection__description">
                    Descubre y compara los 36 modelos de aviones comerciales más importantes del mundo
                </p>
                <GlassButton onClick={() => navigate('/aircraft')}>
                    Ver Aeronaves
                </GlassButton>
            </div>
        </section>
    );
};

export default CTASection;
