import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './HeroSection.module.css';

const HeroSection: React.FC = () => {
    const heroRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const badgeRef = useRef<HTMLDivElement>(null);

    // ✅ DECLARAR ESTOS useRef AQUÍ, ANTES DEL useEffect
    const rafIdRef = useRef<number | null>(null);
    const quickToFunctions = useRef<Map<Element, any>>(new Map());

    const { theme } = useTheme();

    useEffect(() => {
        // Optimización para 60 FPS - Force hardware acceleration
        gsap.set([badgeRef.current, titleRef.current, subtitleRef.current], {
            force3D: true,
            transformStyle: 'preserve-3d',
            backfaceVisibility: 'hidden',
            perspective: 1000
        });

        const timeline = gsap.timeline({
            delay: 0.2,
            defaults: {
                ease: 'power4.out',
                force3D: true
            }
        });

        // Animación del badge - Ultra smooth con elastic ease
        timeline.fromTo(
            badgeRef.current,
            {
                scale: 0.7,
                opacity: 0,
                y: -30,
                rotationX: -15
            },
            {
                scale: 1,
                opacity: 1,
                y: 0,
                rotationX: 0,
                duration: 1.2,
                ease: 'elastic.out(1, 0.6)'
            }
        );

        // Animación del título - Aparecer suave con split characters
        const titleChars = titleRef.current?.querySelectorAll('.char');
        if (titleChars && titleChars.length > 0) {
            timeline.fromTo(
                titleChars,
                {
                    opacity: 0,
                    y: 100,
                    rotationX: -90,
                    scale: 0.8
                },
                {
                    opacity: 1,
                    y: 0,
                    rotationX: 0,
                    scale: 1,
                    duration: 1.6,
                    stagger: {
                        amount: 0.6,
                        ease: 'power2.out'
                    },
                    ease: 'expo.out'
                },
                '-=0.8'
            );
        }

        // Animación del subtítulo con blur effect
        timeline.fromTo(
            subtitleRef.current,
            {
                opacity: 0,
                y: 40,
                filter: 'blur(10px)'
            },
            {
                opacity: 1,
                y: 0,
                filter: 'blur(0px)',
                duration: 1.4,
                ease: 'expo.out'
            },
            '-=1.2'
        );

        // Efecto de flotación sutil continuo en badge - 60 FPS optimizado
        gsap.to(badgeRef.current, {
            y: -8,
            duration: 2.5,
            ease: 'sine.inOut',
            repeat: -1,
            yoyo: true
        });

        // Cleanup al desmontar el componente
        return () => {
            if (rafIdRef.current) {
                cancelAnimationFrame(rafIdRef.current);
            }
            quickToFunctions.current.clear();
        };
    }, []);

    // ========== EFECTO HOVER INTERACTIVO 120FPS CON quickTo ==========
    const handleMouseMove = (e: React.MouseEvent<HTMLHeadingElement>) => {
        const chars = titleRef.current?.querySelectorAll('.char');
        if (!chars || chars.length === 0) return;

        const rect = titleRef.current?.getBoundingClientRect();
        if (!rect) return;

        const mouseX = e.clientX - rect.left;
        const mouseY = e.clientY - rect.top;

        // Cancelar animación previa para evitar queue
        if (rafIdRef.current) {
            cancelAnimationFrame(rafIdRef.current);
        }

        // Usar requestAnimationFrame para sincronizar con el repaint
        rafIdRef.current = requestAnimationFrame(() => {
            chars.forEach((char, index) => {
                const charRect = (char as HTMLElement).getBoundingClientRect();
                const charX = charRect.left - rect.left + charRect.width / 2;
                const charY = charRect.top - rect.top + charRect.height / 2;

                const distance = Math.sqrt(
                    Math.pow(mouseX - charX, 2) +
                    Math.pow(mouseY - charY, 2)
                );

                const maxDistance = 150;

                if (distance < maxDistance) {
                    const intensity = 1 - (distance / maxDistance);
                    const hue = (index * 20) % 360;

                    // Crear quickTo functions solo una vez por letra
                    if (!quickToFunctions.current.has(char)) {
                        quickToFunctions.current.set(char, {
                            scale: gsap.quickTo(char, 'scale', { duration: 0.2, ease: 'power3.out' })
                        });
                    }

                    const quickTo = quickToFunctions.current.get(char);

                    // Aplicar cambios directamente sin gsap.to()
                    gsap.set(char, {
                        color: `hsl(${hue}, 100%, ${50 + intensity * 20}%)`,
                        textShadow: `0 0 ${20 * intensity}px hsla(${hue}, 100%, 60%, ${0.8 * intensity}), 0 0 ${40 * intensity}px hsla(${hue}, 100%, 50%, ${0.5 * intensity})`,
                        force3D: true
                    });

                    // Solo animar scale con quickTo
                    quickTo.scale(1 + (0.15 * intensity));
                } else {
                    if (quickToFunctions.current.has(char)) {
                        const quickTo = quickToFunctions.current.get(char);
                        quickTo.scale(1);
                    }

                    gsap.set(char, {
                        color: 'inherit',
                        textShadow: 'none'
                    });
                }
            });
        });
    };

    const handleMouseLeave = () => {
        const chars = titleRef.current?.querySelectorAll('.char');
        if (!chars) return;

        // Cancelar cualquier RAF pendiente
        if (rafIdRef.current) {
            cancelAnimationFrame(rafIdRef.current);
            rafIdRef.current = null;
        }

        // Reset inmediato con gsap.set (sin animación)
        gsap.set(chars, {
            color: 'inherit',
            textShadow: 'none',
            scale: 1
        });

        // Limpiar quickTo functions
        quickToFunctions.current.clear();
    };

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    // Split text en caracteres individuales para animación hover
    const splitText = (text: string) => {
        return text.split('').map((char, index) => (
            <span key={index} className="char" style={{ display: 'inline-block' }}>
                {char === ' ' ? '\u00A0' : char}
            </span>
        ));
    };

    return (
        <section ref={heroRef} className={`${styles.hero} ${getThemeClass()}`}>
            <div className={styles.container}>
                <div ref={badgeRef} className={styles.badge}>
                    <svg className={styles.badgeIcon} width="20" height="20" viewBox="0 0 20 20" fill="none">
                        <path d="M10 2L12.5 7.5L18 8.5L14 13L15 18.5L10 16L5 18.5L6 13L2 8.5L7.5 7.5L10 2Z"
                              fill="currentColor" stroke="currentColor" strokeWidth="1.5"/>
                    </svg>
                    <span>Próximamente</span>
                </div>

                <h1
                    ref={titleRef}
                    className={styles.title}
                    onMouseMove={handleMouseMove}
                    onMouseLeave={handleMouseLeave}
                >
                    {splitText('SkyVault API v1.0')}
                </h1>

                <p ref={subtitleRef} className={styles.subtitle}>
                    El poder de la aviación comercial al alcance de tus manos.
                    Accede a información detallada de aeronaves, fabricantes y especificaciones
                    técnicas con una API diseñada para profesionales.
                </p>
            </div>
        </section>
    );
};

export default HeroSection;
