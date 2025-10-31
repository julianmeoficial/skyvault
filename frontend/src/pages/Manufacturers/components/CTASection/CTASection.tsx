import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { useNavigate } from 'react-router-dom';
import styles from './CTASection.module.css';

const CTASection: React.FC = () => {
    const sectionRef = useRef<HTMLElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const descRef = useRef<HTMLParagraphElement>(null);
    const buttonsRef = useRef<HTMLDivElement>(null);
    const backgroundRef = useRef<HTMLDivElement>(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!sectionRef.current) return;

        const ctx = gsap.context(() => {
            // Hardware acceleration
            gsap.set([titleRef.current, descRef.current, buttonsRef.current, backgroundRef.current], {
                force3D: true,
                backfaceVisibility: 'hidden'
            });

            // Animated background gradient
            gsap.to(backgroundRef.current, {
                rotation: 360,
                duration: 20,
                repeat: -1,
                ease: 'none'
            });

            // Title animation with split
            gsap.fromTo(
                titleRef.current,
                {
                    opacity: 0,
                    y: 80,
                    rotationX: -45
                },
                {
                    opacity: 1,
                    y: 0,
                    rotationX: 0,
                    duration: 1.2,
                    ease: 'expo.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: titleRef.current,
                        start: 'top 80%',
                        once: true
                    }
                }
            );

            // Description fade in
            gsap.fromTo(
                descRef.current,
                { opacity: 0, y: 40 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 0.8,
                    ease: 'power3.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: descRef.current,
                        start: 'top 80%',
                        once: true
                    }
                }
            );

            // Buttons stagger animation
            const buttons = buttonsRef.current?.children;
            if (buttons) {
                gsap.fromTo(
                    Array.from(buttons),
                    {
                        opacity: 0,
                        y: 50,
                        scale: 0.8
                    },
                    {
                        opacity: 1,
                        y: 0,
                        scale: 1,
                        duration: 0.6,
                        stagger: 0.15,
                        ease: 'back.out(1.7)',
                        force3D: true,
                        scrollTrigger: {
                            trigger: buttonsRef.current,
                            start: 'top 80%',
                            once: true
                        }
                    }
                );
            }

            // Parallax effect on scroll
            gsap.to(sectionRef.current, {
                y: -50,
                ease: 'none',
                scrollTrigger: {
                    trigger: sectionRef.current,
                    start: 'top bottom',
                    end: 'bottom top',
                    scrub: 1
                }
            });

        }, sectionRef);

        return () => ctx.revert();
    }, []);

    return (
        <section ref={sectionRef} className={styles.ctaSection}>
            {/* Animated background */}
            <div ref={backgroundRef} className={styles.animatedBackground}>
                <div className={styles.gradientOrb}></div>
            </div>

            <div className={styles.container}>
                <h2 ref={titleRef} className={styles.title}>
                    ¿Listo para explorar?
                </h2>

                <p ref={descRef} className={styles.description}>
                    Descubre las especificaciones técnicas completas, capacidades y
                    características de cada aeronave en nuestro catálogo interactivo.
                </p>

                <div ref={buttonsRef} className={styles.buttons}>
                    <button
                        className={`${styles.ctaButton} ${styles.primary}`}
                        onClick={() => navigate('/catalog')}
                    >
                        <span className={styles.buttonContent}>
                            <svg className={styles.buttonIcon} viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
                            </svg>
                            Ver Catálogo Completo
                        </span>
                        <div className={styles.buttonGlow}></div>
                    </button>

                    <button
                        className={`${styles.ctaButton} ${styles.secondary}`}
                        onClick={() => navigate('/compare')}
                    >
                        <span className={styles.buttonContent}>
                            <svg className={styles.buttonIcon} viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                            </svg>
                            Comparar Modelos
                        </span>
                        <div className={styles.buttonGlow}></div>
                    </button>
                </div>

                {/* Floating elements */}
                <div className={styles.floatingElements}>
                    <div className={`${styles.floatElement} ${styles.float1}`}></div>
                    <div className={`${styles.floatElement} ${styles.float2}`}></div>
                    <div className={`${styles.floatElement} ${styles.float3}`}></div>
                </div>
            </div>
        </section>
    );
};

export default CTASection;
