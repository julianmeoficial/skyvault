import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { BackButton } from '../../shared/components/ui/BackButton';
import ThemeToggle from '../../shared/components/ui/ThemeToggle';
import HeroSection from './components/HeroSection/HeroSection';
import ManufacturerInfo from './components/ManufacturerInfo/ManufacturerInfo';
import CTASection from './components/CTASection/CTASection';
import { manufacturersData } from '../../data/manufacturersData';
import styles from './Manufacturers.module.css';
import Footer from "../../shared/components/layout/Footer";

gsap.registerPlugin(ScrollTrigger);

const Manufacturers: React.FC = () => {
    const pageRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!pageRef.current) return;

        const ctx = gsap.context(() => {
            // Smooth scroll setup
            gsap.set(pageRef.current, {
                force3D: true,
                backfaceVisibility: 'hidden',
                perspective: 1000
            });
        }, pageRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(st => st.kill());
        };
    }, []);

    return (
        <div ref={pageRef} className={styles.manufacturersPage}>
            {/* Fixed Controls */}
            <div className={styles.fixedControls}>
                <BackButton to="/" label="Inicio" />
                <ThemeToggle />
            </div>

            {/* Hero Section */}
            <HeroSection />

            {/* Manufacturers Info Sections */}
            {manufacturersData.map((manufacturer, index) => (
                <ManufacturerInfo
                    key={manufacturer.id}
                    manufacturer={manufacturer}
                    index={index}
                />
            ))}

            {/* CTA Section */}
            <CTASection />

            {/* Footer */}
            <Footer />
        </div>
    );
};

export default Manufacturers;
