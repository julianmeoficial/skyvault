import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import ThemeToggle from '../../shared/components/ui/ThemeToggle/ThemeToggle';
import Footer from '../../shared/components/layout/Footer/Footer';
import GalaxyBackground from './components/GalaxyBackground/GalaxyBackground';
import HeroSection from './components/HeroSection/HeroSection';
import FeaturesSection from './components/FeaturesSection/FeaturesSection';
import UseCasesSection from './components/UseCasesSection/UseCasesSection';
import EndpointsPreview from './components/EndpointsPreview/EndpointsPreview';
import TechnicalSection from './components/TechnicalSection/TechnicalSection';
import CTASection from './components/CTASection/CTASection';
import styles from './APIPage.module.css';

gsap.registerPlugin(ScrollTrigger);

const APIPage: React.FC = () => {
    const pageRef = useRef<HTMLDivElement>(null);
    const floatingControlsRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!pageRef.current || !floatingControlsRef.current) return;

        // Hardware acceleration setup
        gsap.set([pageRef.current, floatingControlsRef.current], {
            force3D: true,
            backfaceVisibility: 'hidden'
        });

        const ctx = gsap.context(() => {
            // Animación inicial ultra smooth de fade in
            gsap.fromTo(
                pageRef.current,
                { opacity: 0, y: 20 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1.4,
                    ease: 'power2.out',
                    force3D: true
                }
            );

            // Animación de floating controls con delay
            gsap.fromTo(
                floatingControlsRef.current,
                { opacity: 0, scale: 0.8 },
                {
                    opacity: 1,
                    scale: 1,
                    duration: 0.8,
                    delay: 0.5,
                    ease: 'back.out(1.4)',
                    force3D: true
                }
            );

            // Auto-hide floating controls al hacer scroll (mejora UX)
            ScrollTrigger.create({
                trigger: pageRef.current,
                start: 'top -100',
                end: 'bottom bottom',
                onUpdate: (self) => {
                    const progress = self.progress;
                    if (progress > 0.1 && progress < 0.95) {
                        gsap.to(floatingControlsRef.current, {
                            opacity: 0.6,
                            duration: 0.3
                        });
                    } else {
                        gsap.to(floatingControlsRef.current, {
                            opacity: 1,
                            duration: 0.3
                        });
                    }
                }
            });
        }, pageRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(trigger => trigger.kill());
        };
    }, []);

    return (
        <div ref={pageRef} className={styles.apiPage}>
            <GalaxyBackground />

            {/* FLOATING CONTROLS (sin header feo) */}
            <div ref={floatingControlsRef} className={styles.floatingControls}>
                <div className={styles.backButtonWrapper}>
                    <BackButton to="/" />
                </div>
                <div className={styles.themeToggleWrapper}>
                    <ThemeToggle />
                </div>
            </div>

            {/* Contenido principal */}
            <main className={styles.content}>
                <HeroSection />
                <FeaturesSection />
                <UseCasesSection />
                <EndpointsPreview />
                <TechnicalSection />
                <CTASection />
            </main>

            {/* Footer */}
            <Footer />
        </div>
    );
};

export default APIPage;
