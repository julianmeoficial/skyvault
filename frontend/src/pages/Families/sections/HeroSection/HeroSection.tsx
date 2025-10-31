/**
 * @file HeroSection.tsx
 * @description Sección Hero con título rainbow animado y efectos GSAP supremos
 * Botón volver izquierda, Theme toggle derecha
 */

import React, { useEffect, useRef } from 'react';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import styles from './HeroSection.module.css';
import { BackButton } from '../../../../shared/components/ui/BackButton';
import ThemeSlider from '../../../../shared/components/ui/ThemeToggle';

gsap.registerPlugin(ScrollTrigger);

const HeroSection: React.FC = () => {
    const heroRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const rainbowRef = useRef<HTMLSpanElement>(null);
    const statsRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!heroRef.current || !titleRef.current || !subtitleRef.current) return;

        const ctx = gsap.context(() => {
            // Pre-optimize GPU
            gsap.set(
                [
                    titleRef.current,
                    subtitleRef.current,
                    rainbowRef.current,
                    statsRef.current,
                ],
                {
                    force3D: true,
                    backfaceVisibility: 'hidden',
                    perspective: 1200,
                    willChange: 'transform, opacity',
                }
            );

            // Entrada principal
            const tl = gsap.timeline({
                scrollTrigger: {
                    trigger: heroRef.current,
                    start: 'top center',
                    once: true,
                },
            });

            tl.from(titleRef.current, {
                y: 80,
                opacity: 0,
                duration: 1.2,
                ease: 'cubic.out',
            })
                .from(
                    subtitleRef.current,
                    {
                        y: 50,
                        opacity: 0,
                        duration: 1,
                        ease: 'cubic.out',
                    },
                    '-=0.8'
                )
                .from(
                    statsRef.current,
                    {
                        y: 40,
                        opacity: 0,
                        duration: 0.9,
                        ease: 'cubic.out',
                    },
                    '-=0.6'
                )
                .from(
                    '.hero-controls',
                    {
                        opacity: 0,
                        duration: 0.8,
                        ease: 'power3.out',
                    },
                    0
                );

            // Rainbow hover timeline
            if (rainbowRef.current) {
                const rainbowElement = rainbowRef.current;
                const rainbowTl = gsap.timeline({ paused: true });

                rainbowTl
                    .to(
                        rainbowElement,
                        {
                            backgroundPosition: '200% center',
                            duration: 1.5,
                            ease: 'sine.inOut',
                        },
                        0
                    )
                    .to(
                        rainbowElement,
                        {
                            textShadow: '0 0 30px rgba(255, 0, 128, 0.5)',
                            duration: 0.6,
                            ease: 'power2.out',
                        },
                        0
                    )
                    .to(
                        rainbowElement,
                        {
                            scale: 1.05,
                            duration: 0.6,
                            ease: 'back.out(1.5)',
                        },
                        0
                    );

                const handleMouseEnter = () => {
                    rainbowTl.restart();
                    rainbowTl.play();
                };

                const handleMouseLeave = () => {
                    rainbowTl.reverse();
                };

                rainbowElement.addEventListener('mouseenter', handleMouseEnter);
                rainbowElement.addEventListener('mouseleave', handleMouseLeave);

                return () => {
                    rainbowElement.removeEventListener('mouseenter', handleMouseEnter);
                    rainbowElement.removeEventListener('mouseleave', handleMouseLeave);
                };
            }
        }, heroRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach((st) => st.kill());
        };
    }, []);

    return (
        <section ref={heroRef} className={styles.hero}>
            {/* Fondo animado */}
            <div className={styles.bgGradient} />

            {/* Controles: Back izquierda, Theme derecha */}
            <div className={`${styles.controlsLeft} hero-controls`}>
                <BackButton />
            </div>

            <div className={`${styles.controlsRight} hero-controls`}>
                <ThemeSlider />
            </div>

            {/* Contenido principal */}
            <div className={styles.content}>
                <h1 ref={titleRef} className={styles.title}>
                    Familias de{' '}
                    <span ref={rainbowRef} className={styles.rainbow}>
            Aeronaves
          </span>
                </h1>

                <p ref={subtitleRef} className={styles.subtitle}>
                    Explora la evolución histórica de 10 familias de aeronaves comerciales,
                    desde los primeros diseños hasta las generaciones más modernas. 36
                    modelos, décadas de innovación.
                </p>

                <div ref={statsRef} className={styles.stats}>
                    <div className={styles.stat}>
                        <span className={styles.number}>36</span>
                        <span className={styles.label}>Modelos</span>
                    </div>
                    <div className={styles.stat}>
                        <span className={styles.number}>10</span>
                        <span className={styles.label}>Familias</span>
                    </div>
                    <div className={styles.stat}>
                        <span className={styles.number}>2</span>
                        <span className={styles.label}>Fabricantes</span>
                    </div>
                </div>
            </div>

            {/* Scroll indicator */}
            <div className={styles.scrollIndicator}>
                <div className={styles.scrollDot} />
                <p className={styles.scrollText}>Desplázate para explorar</p>
            </div>
        </section>
    );
};

export default HeroSection;
