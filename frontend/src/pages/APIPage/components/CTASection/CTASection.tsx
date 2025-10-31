import React, { useEffect, useRef, useMemo } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import PricingCard from '../PricingCard/PricingCard';
import styles from './CTASection.module.css';

gsap.registerPlugin(ScrollTrigger);

const CTASection: React.FC = () => {
    const sectionRef = useRef<HTMLDivElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const cardsRef = useRef<(HTMLDivElement | null)[]>([]);
    const ctaBoxRef = useRef<HTMLDivElement>(null);
    const { theme } = useTheme();
    const navigate = useNavigate();

    // Memoizar pricing plans
    const pricingPlans = useMemo(() => [
        {
            name: 'Developer',
            price: 'Gratis',
            description: 'Perfecto para empezar y proyectos personales',
            features: [
                '1,000 requests/mes',
                'Documentación completa',
                'Soporte por email',
                'Acceso a todos los endpoints',
                'Rate limit: 10 req/min'
            ],
            highlighted: false,
            buttonText: 'Comenzar gratis'
        },
        {
            name: 'Professional',
            price: '$49',
            period: '/mes',
            description: 'Para startups y equipos pequeños',
            features: [
                '100,000 requests/mes',
                'Documentación avanzada',
                'Soporte prioritario 24/7',
                'Webhooks y callbacks',
                'Rate limit: 100 req/min',
                'SLA 99.9% uptime',
                'Analytics dashboard'
            ],
            highlighted: true,
            buttonText: 'Empezar ahora'
        },
        {
            name: 'Enterprise',
            price: 'Custom',
            description: 'Para grandes empresas con necesidades específicas',
            features: [
                'Requests ilimitados',
                'Documentación dedicada',
                'Account manager dedicado',
                'Custom endpoints',
                'Sin rate limits',
                'SLA 99.99% uptime',
                'On-premise deployment',
                'Soporte telefónico 24/7'
            ],
            highlighted: false,
            buttonText: 'Contactar ventas'
        }
    ], []);

    useEffect(() => {
        // Hardware acceleration
        gsap.set([titleRef.current, subtitleRef.current, ctaBoxRef.current, ...cardsRef.current], {
            force3D: true,
            backfaceVisibility: 'hidden',
            perspective: 1000
        });

        const ctx = gsap.context(() => {
            // Título pricing
            gsap.fromTo(
                titleRef.current,
                { opacity: 0, y: 60 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1.2,
                    ease: 'expo.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: titleRef.current,
                        start: 'top 80%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            // Subtítulo pricing
            gsap.fromTo(
                subtitleRef.current,
                { opacity: 0, y: 30 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 1,
                    ease: 'power3.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: subtitleRef.current,
                        start: 'top 85%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );

            // Cards con stagger
            cardsRef.current.forEach((card, index) => {
                if (!card) return;
                gsap.fromTo(
                    card,
                    { opacity: 0, y: 80, scale: 0.9 },
                    {
                        opacity: 1,
                        y: 0,
                        scale: 1,
                        duration: 1.2,
                        delay: index * 0.15,
                        ease: 'expo.out',
                        force3D: true,
                        scrollTrigger: {
                            trigger: card,
                            start: 'top 85%',
                            toggleActions: 'play none none reverse'
                        }
                    }
                );
            });

            // CTA Box final
            gsap.fromTo(
                ctaBoxRef.current,
                { opacity: 0, scale: 0.92, y: 60 },
                {
                    opacity: 1,
                    scale: 1,
                    y: 0,
                    duration: 1.4,
                    ease: 'expo.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: ctaBoxRef.current,
                        start: 'top 80%',
                        toggleActions: 'play none none reverse'
                    }
                }
            );
        }, sectionRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(st => st.kill());
        };
    }, []);

    const handleEarlyAccess = () => {
        navigate('/contact');
    };

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    return (
        <section ref={sectionRef} className={`${styles.cta} ${getThemeClass()}`}>
            <div className={styles.container}>
                {/* Pricing Section */}
                <div className={styles.pricingSection}>
                    <h2 ref={titleRef} className={styles.sectionTitle}>
                        Planes flexibles para cada necesidad
                    </h2>
                    <p ref={subtitleRef} className={styles.sectionSubtitle}>
                        Comienza gratis y escala según crezcas. Sin tarjeta de crédito requerida.
                    </p>

                    <div className={styles.pricingGrid}>
                        {pricingPlans.map((plan, index) => (
                            <div
                                key={index}
                                ref={(el: HTMLDivElement | null) => {
                                    cardsRef.current[index] = el;
                                }}
                            >
                                <PricingCard {...plan} />
                            </div>
                        ))}
                    </div>
                </div>

                {/* CTA Box */}
                <div ref={ctaBoxRef} className={styles.ctaBox}>
                    <div className={styles.ctaContent}>
                        <h2 className={styles.ctaTitle}>¿Listo para despegar?</h2>
                        <p className={styles.ctaDescription}>
                            Únete a cientos de desarrolladores que ya están construyendo
                            el futuro de la aviación comercial con SkyVault API
                        </p>

                        <button
                            className={styles.ctaButton}
                            onClick={handleEarlyAccess}
                            type="button"
                        >
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                            </svg>
                            Contáctanos para Early Access
                        </button>
                    </div>

                    <div className={styles.ctaIcon}>
                        <svg width="120" height="120" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                            <circle cx="1212" cy="12" r="10"/>
                            <path d="M12 6v6l4 2"/>
                        </svg>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default React.memo(CTASection);
