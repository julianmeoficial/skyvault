import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { SplitText } from 'gsap/SplitText';
import styles from './HeroSection.module.css';

gsap.registerPlugin(SplitText);

const HeroSection: React.FC = () => {
    const heroRef = useRef<HTMLElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);
    const scrollIndicatorRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!heroRef.current || !titleRef.current || !subtitleRef.current) return;

        const ctx = gsap.context(() => {
            // Hardware acceleration
            gsap.set([heroRef.current, titleRef.current, subtitleRef.current, scrollIndicatorRef.current], {
                force3D: true,
                backfaceVisibility: 'hidden'
            });

            // Split text animation (GSAP style)
            const split = new SplitText(titleRef.current, { type: 'chars, words' });

            const tl = gsap.timeline({ defaults: { ease: 'power4.out' } });

            tl.fromTo(
                split.chars,
                {
                    opacity: 0,
                    y: 100,
                    rotationX: -90,
                    transformOrigin: '0% 50% -50'
                },
                {
                    opacity: 1,
                    y: 0,
                    rotationX: 0,
                    duration: 1,
                    stagger: 0.03,
                    force3D: true
                }
            ).fromTo(
                subtitleRef.current,
                { opacity: 0, y: 50, scale: 0.95 },
                { opacity: 1, y: 0, scale: 1, duration: 0.8, force3D: true },
                '-=0.5'
            ).fromTo(
                scrollIndicatorRef.current,
                { opacity: 0, y: -20 },
                { opacity: 1, y: 0, duration: 0.6, force3D: true },
                '-=0.3'
            );

            // Floating animation for scroll indicator
            gsap.to(scrollIndicatorRef.current, {
                y: 15,
                duration: 1.5,
                repeat: -1,
                yoyo: true,
                ease: 'sine.inOut'
            });

            // Mouse move parallax effect
            const handleMouseMove = (e: MouseEvent) => {
                const { clientX, clientY } = e;
                const { innerWidth, innerHeight } = window;

                const xPos = (clientX / innerWidth - 0.5) * 30;
                const yPos = (clientY / innerHeight - 0.5) * 30;

                gsap.to(titleRef.current, {
                    x: xPos,
                    y: yPos,
                    duration: 0.5,
                    ease: 'power2.out'
                });
            };

            window.addEventListener('mousemove', handleMouseMove);

            return () => {
                window.removeEventListener('mousemove', handleMouseMove);
                split.revert();
            };
        }, heroRef);

        return () => ctx.revert();
    }, []);

    return (
        <section ref={heroRef} className={styles.hero}>
            <div className={styles.heroContent}>
                <h1 ref={titleRef} className={styles.title}>
                    Fabricantes Aeroespaciales
                </h1>
                <p ref={subtitleRef} className={styles.subtitle}>
                    Explora los líderes mundiales en innovación aeronáutica
                </p>
            </div>

            <div ref={scrollIndicatorRef} className={styles.scrollIndicator}>
                <div className={styles.mouse}>
                    <div className={styles.wheel}></div>
                </div>
                <span className={styles.scrollText}>Scroll para explorar</span>
            </div>

            {/* Animated background grid */}
            <div className={styles.backgroundGrid}></div>
        </section>
    );
};

export default HeroSection;
