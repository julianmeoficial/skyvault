import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import styles from './ManufacturerInfo.module.css';

gsap.registerPlugin(ScrollTrigger);

interface ManufacturerInfoProps {
    manufacturer: {
        id: string;
        name: string;
        description: string;
        images: string[];
        stats: Array<{ label: string; value: string }>;
        note?: string;
    };
    index: number;
}

const ManufacturerInfo: React.FC<ManufacturerInfoProps> = ({ manufacturer }) => {
    const sectionRef = useRef<HTMLElement>(null);
    const titleRef = useRef<HTMLHeadingElement>(null);
    const descRef = useRef<HTMLParagraphElement>(null);
    const imageRefs = useRef<(HTMLDivElement | null)[]>([]);
    const statsRef = useRef<HTMLDivElement>(null);
    const containerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!sectionRef.current) return;

        const ctx = gsap.context(() => {
            // Hardware acceleration setup
            gsap.set([
                sectionRef.current,
                titleRef.current,
                descRef.current,
                statsRef.current,
                containerRef.current,
                ...imageRefs.current.filter(Boolean)
            ], {
                force3D: true,
                backfaceVisibility: 'hidden',
                perspective: 1000
            });

            // TITLE ANIMATION - Scroll reveal with rotation
            gsap.fromTo(
                titleRef.current,
                {
                    opacity: 0,
                    y: 120,
                    scale: 0.8,
                    rotationX: 45
                },
                {
                    opacity: 1,
                    y: 0,
                    scale: 1,
                    rotationX: 0,
                    duration: 1.4,
                    ease: 'expo.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: titleRef.current,
                        start: 'top 85%',
                        end: 'top 35%',
                        scrub: 0.5,
                        onUpdate: (self) => {
                            gsap.set(titleRef.current, {
                                opacity: gsap.utils.mapRange(0, 1, 0, 1, self.progress)
                            });
                        }
                    }
                }
            );

            // DESCRIPTION FADE IN - Smooth reveal
            gsap.fromTo(
                descRef.current,
                { opacity: 0, y: 60, filter: 'blur(10px)' },
                {
                    opacity: 1,
                    y: 0,
                    filter: 'blur(0px)',
                    duration: 1,
                    ease: 'power3.out',
                    force3D: true,
                    scrollTrigger: {
                        trigger: descRef.current,
                        start: 'top 80%',
                        once: true
                    }
                }
            );

            // IMAGES PARALLAX WITH BETTER REVEAL
            imageRefs.current.filter(Boolean).forEach((img, idx) => {
                // Initial reveal animation
                gsap.fromTo(
                    img,
                    {
                        opacity: 0,
                        scale: 0.75,
                        y: 80,
                        filter: 'blur(15px)'
                    },
                    {
                        opacity: 1,
                        scale: 1,
                        y: 0,
                        filter: 'blur(0px)',
                        duration: 1.2,
                        ease: 'expo.out',
                        force3D: true,
                        delay: idx * 0.15,
                        scrollTrigger: {
                            trigger: img,
                            start: 'top 88%',
                            once: true
                        }
                    }
                );

                // Smooth parallax effect on scroll
                gsap.to(img, {
                    y: idx % 2 === 0 ? 40 : -40,
                    ease: 'none',
                    force3D: true,
                    scrollTrigger: {
                        trigger: sectionRef.current,
                        start: 'top 20%',
                        end: 'bottom 80%',
                        scrub: 1.5,
                        toggleActions: 'play play reverse reverse'
                    }
                });
            });

            // STATS ANIMATION - Staggered counter reveal
            const statCards = statsRef.current?.children;
            if (statCards) {
                gsap.fromTo(
                    Array.from(statCards),
                    {
                        opacity: 0,
                        y: 80,
                        scale: 0.8,
                        filter: 'blur(10px)'
                    },
                    {
                        opacity: 1,
                        y: 0,
                        scale: 1,
                        filter: 'blur(0px)',
                        duration: 0.8,
                        stagger: 0.12,
                        ease: 'back.out(1.5)',
                        force3D: true,
                        scrollTrigger: {
                            trigger: statsRef.current,
                            start: 'top 82%',
                            once: true
                        }
                    }
                );

                // Hover animation for stat cards with better timing
                Array.from(statCards).forEach((card: Element) => {
                    (card as HTMLElement).addEventListener('mouseenter', () => {
                        gsap.to(card, {
                            y: -8,
                            duration: 0.4,
                            ease: 'power2.out',
                            overwrite: 'auto'
                        });
                    });

                    (card as HTMLElement).addEventListener('mouseleave', () => {
                        gsap.to(card, {
                            y: 0,
                            duration: 0.4,
                            ease: 'power2.out',
                            overwrite: 'auto'
                        });
                    });
                });
            }

            // CONTAINER PARALLAX - Subtle depth effect
            gsap.to(containerRef.current, {
                y: -30,
                ease: 'none',
                force3D: true,
                scrollTrigger: {
                    trigger: sectionRef.current,
                    start: 'top center',
                    end: 'bottom center',
                    scrub: 2
                }
            });

        }, sectionRef);

        return () => {
            ctx.revert();
            ScrollTrigger.getAll().forEach(trigger => trigger.kill());
        };
    }, []);

    const isAirbus = manufacturer.id === 'airbus';

    return (
        <section
            ref={sectionRef}
            className={`${styles.manufacturerInfo} ${isAirbus ? styles.airbus : styles.boeing}`}
        >
            <div ref={containerRef} className={styles.container}>
                {/* Title with hover effect */}
                <h2
                    ref={titleRef}
                    className={styles.manufacturerTitle}
                >
                    {manufacturer.name}
                </h2>

                {/* Description */}
                <div className={styles.descriptionWrapper}>
                    <p ref={descRef} className={styles.description}>
                        {manufacturer.description}
                    </p>
                    {manufacturer.note && (
                        <div className={styles.note}>
                            <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"/>
                            </svg>
                            <span>{manufacturer.note}</span>
                        </div>
                    )}
                </div>

                {/* Image Gallery with Parallax */}
                <div className={styles.imageGallery}>
                    {manufacturer.images.slice(0, 3).map((img, idx) => (
                        <div
                            key={idx}
                            ref={(el: HTMLDivElement | null) => {
                                if (el) imageRefs.current[idx] = el;
                            }}
                            className={styles.imageWrapper}
                        >
                            <img src={img} alt={`${manufacturer.name} ${idx + 1}`} loading="lazy" />
                            <div className={styles.imageOverlay}></div>
                        </div>
                    ))}
                </div>

                {/* Stats Grid */}
                <div ref={statsRef} className={styles.statsGrid}>
                    {manufacturer.stats.map((stat, idx) => (
                        <div key={idx} className={styles.statCard}>
                            <span className={styles.statValue}>{stat.value}</span>
                            <span className={styles.statLabel}>{stat.label}</span>
                        </div>
                    ))}
                </div>
            </div>

            {/* Section Divider Animation */}
            <div className={styles.sectionDivider}>
                <svg viewBox="0 0 1200 120" preserveAspectRatio="none">
                    <path d="M0,0V46.29c47.79,22.2,103.59,32.17,158,28,70.36-5.37,136.33-33.31,206.8-37.5C438.64,32.43,512.34,53.67,583,72.05c69.27,18,138.3,24.88,209.4,13.08,36.15-6,69.85-17.84,104.45-29.34C989.49,25,1113-14.29,1200,52.47V0Z" opacity=".25"></path>
                    <path d="M0,0V15.81C13,36.92,27.64,56.86,47.69,72.05,99.41,111.27,165,111,224.58,91.58c31.15-10.15,60.09-26.07,89.67-39.8,40.92-19,84.73-46,130.83-49.67,36.26-2.85,70.9,9.42,98.6,31.56,31.77,25.39,62.32,62,103.63,73,40.44,10.79,81.35-6.69,119.13-24.28s75.16-39,116.92-43.05c59.73-5.85,113.28,22.88,168.9,38.84,30.2,8.66,59,6.17,87.09-7.5,22.43-10.89,48-26.93,60.65-49.24V0Z" opacity=".5"></path>
                    <path d="M0,0V5.63C149.93,59,314.09,71.32,475.83,42.57c43-7.64,84.23-20.12,127.61-26.46,59-8.63,112.48,12.24,165.56,35.4C827.93,77.22,886,95.24,951.2,90c86.53-7,172.46-45.71,248.8-84.81V0Z"></path>
                </svg>
            </div>
        </section>
    );
};

export default ManufacturerInfo;
