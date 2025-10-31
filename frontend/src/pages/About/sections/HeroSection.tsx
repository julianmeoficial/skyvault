import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';

const HeroSection: React.FC = () => {
    const titleRef = useRef<HTMLHeadingElement>(null);
    const subtitleRef = useRef<HTMLParagraphElement>(null);

    useEffect(() => {
        const titleText = 'Acerca de SkyVault';
        const subtitleText = 'Comparador de Aviación Comercial';

        if (titleRef.current && subtitleRef.current) {
            // Limpiar contenido inicial
            titleRef.current.textContent = '';
            subtitleRef.current.textContent = '';

            // Animación typewriter para el título
            const titleChars = titleText.split('');
            titleChars.forEach((char, index) => {
                const span = document.createElement('span');
                span.textContent = char === ' ' ? '\u00A0' : char;
                span.style.opacity = '0';
                titleRef.current?.appendChild(span);

                gsap.to(span, {
                    opacity: 1,
                    duration: 0.05,
                    delay: index * 0.08,
                    ease: 'none'
                });
            });

            // Animación typewriter para el subtítulo (empieza después del título)
            const subtitleDelay = titleChars.length * 0.08 + 0.5;
            const subtitleChars = subtitleText.split('');

            subtitleChars.forEach((char, index) => {
                const span = document.createElement('span');
                span.textContent = char === ' ' ? '\u00A0' : char;
                span.style.opacity = '0';
                subtitleRef.current?.appendChild(span);

                gsap.to(span, {
                    opacity: 1,
                    duration: 0.05,
                    delay: subtitleDelay + index * 0.06,
                    ease: 'none'
                });
            });

            // Animación del scroll indicator
            gsap.fromTo(
                '.heroSection__scroll',
                { opacity: 0, y: -20 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 0.8,
                    delay: subtitleDelay + subtitleChars.length * 0.06 + 0.3,
                    ease: 'power2.out'
                }
            );
        }
    }, []);

    return (
        <section className="heroSection">
            <div className="heroSection__overlay"></div>
            <div className="heroSection__content">
                <h1 className="heroSection__title" ref={titleRef}></h1>
                <p className="heroSection__subtitle" ref={subtitleRef}></p>
                <div className="heroSection__scroll">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path
                            d="M12 5V19M12 19L5 12M12 19L19 12"
                            stroke="white"
                            strokeWidth="2"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
            </div>
        </section>
    );
};

export default HeroSection;
