import React, { useEffect, useRef } from 'react';
import { animateHeroEnter, animateHeroScroll } from './HeroSection.animations';
import './HeroSection.css';

const HeroSection: React.FC = () => {
    const heroRef = useRef<HTMLDivElement>(null);
    const contentRef = useRef<HTMLDivElement>(null);
    const backgroundRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (heroRef.current && contentRef.current && backgroundRef.current) {
            animateHeroEnter(contentRef.current);
            animateHeroScroll(heroRef.current, backgroundRef.current, contentRef.current);
        }
    }, []);

    return (
        <section className="heroSection" ref={heroRef}>
            <div className="heroSection__background" ref={backgroundRef} />
            <div className="heroSection__overlay" />

            <div className="heroSection__content" ref={contentRef}>
                <div className="heroSection__logo">
                    <img
                        src="/assets/icons/SkyVaultLogoPLB.svg"
                        alt="SkyVault Logo"
                        className="heroSection__logo-image"
                    />
                </div>

                <h1 className="heroSection__title">
                    <span className="heroSection__title-main">SkyVault</span>
                    <span className="heroSection__title-sub">
                        Compara 36 Modelos de Aviación Comercial
                    </span>
                </h1>

                <div className="heroSection__cta">
                    <a href="#featured" className="heroSection__button liquidGlass">
                        <span>Explorar Catálogo</span>
                    </a>

                    <a href="/compare" className="heroSection__button heroSection__button--secondary liquidGlass">
                        <span>Comparar Modelos</span>
                    </a>
                </div>

                <div className="heroSection__scrollIndicator">
                    <div className="heroSection__scrollIndicator-line" />
                    <span className="heroSection__scrollIndicator-text">Desliza para explorar</span>
                </div>
            </div>

            {/* SVG Filter para Liquid Glass */}
            <svg style={{ position: 'absolute', width: 0, height: 0 }}>
                <defs>
                    <filter
                        id="liquidGlass"
                        x="-50%"
                        y="-50%"
                        width="200%"
                        height="200%"
                    >
                        <feImage
                            xlinkHref="https://essykings.github.io/JavaScript/map.png"
                            x="-50%"
                            y="-50%"
                            width="200%"
                            height="200%"
                            result="map"
                        />
                        <feGaussianBlur in="SourceGraphic" stdDeviation="0.025" result="blur" />
                        <feDisplacementMap
                            in="blur"
                            in2="map"
                            scale="12"
                            xChannelSelector="R"
                            yChannelSelector="G"
                        />
                    </filter>
                </defs>
            </svg>
        </section>
    );
};

export default HeroSection;
