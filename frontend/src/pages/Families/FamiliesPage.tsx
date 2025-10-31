/**
 * @file FamiliesPage.tsx
 * @description Página principal de familias de aeronaves
 * ACTUALIZADO: Incluye Hero, Grid, Galería Horizontal y Timeline
 */

import React from 'react';
import HeroSection from './sections/HeroSection/HeroSection';
import FamiliesGrid from './sections/FamiliesGrid/FamiliesGrid';
import ModernAircraftGallery from './sections/TimelineSection/ModernAircraftGallery';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './FamiliesPage.module.css';

const FamiliesPage: React.FC = () => {
    return (
        <div className={styles.page}>
            <HeroSection />
            <FamiliesGrid />
            <ModernAircraftGallery />
            <Footer />
        </div>
    );
};

export default FamiliesPage;
