import React, { useEffect } from 'react';
import HeroSection from './sections/HeroSection';
import MissionSection from './sections/MissionSection';
import ProjectSection from './sections/ProjectSection';
import TechnologySection from './sections/TechnologySection';
import TeamSection from './sections/TeamSection';
import CTASection from './sections/CTASection';
import Footer from '../../shared/components/layout/Footer';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider';
import { initScrollAnimations } from './About.animations';
import './About.css';

const About: React.FC = () => {
    useEffect(() => {
        initScrollAnimations();
    }, []);

    return (
        <div className="aboutPage">
            {/* Crystal Slider - Controles de tema flotantes */}
            <CrystalSlider position="top-right" />

            <HeroSection />
            <MissionSection />
            <ProjectSection />
            <TechnologySection />
            <TeamSection />
            <CTASection />
            <Footer />
        </div>
    );
};

export default About;
