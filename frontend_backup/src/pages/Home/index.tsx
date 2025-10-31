import React, { useState, useRef } from 'react';
import { Loader } from '../../shared/components/ui/Loader';
import { initializeGlassEffect, animateButtonsEnter, createAdvancedHoverEffect } from './Home.animations';
import './Home.css';

const Home: React.FC = () => {
    const [showLoader, setShowLoader] = useState(true);
    const [showButtons, setShowButtons] = useState(false);
    const [showBackground, setShowBackground] = useState(false);
    const [showComingSoon, setShowComingSoon] = useState(false);
    const buttonsContainerRef = useRef<HTMLDivElement>(null);
    const backgroundRef = useRef<HTMLDivElement>(null);

    const handleLoaderComplete = () => {
        setShowLoader(false);
        setShowButtons(true);

        setTimeout(() => {
            initializeGlassEffect();
            if (buttonsContainerRef.current) {
                const timeline = animateButtonsEnter(buttonsContainerRef.current);
                timeline.then(() => {
                    const buttons = buttonsContainerRef.current?.querySelectorAll('.glassButton');
                    buttons?.forEach(button => createAdvancedHoverEffect(button as HTMLElement));
                });
            }
        }, 100);

        setTimeout(() => {
            setShowBackground(true);
        }, 500);

        setTimeout(() => {
            setShowComingSoon(true);
        }, 1100);
    };

    return (
        <div className="homeContainer">
            {showLoader && <Loader onComplete={handleLoaderComplete} text="Construyendo" />}

            {showButtons && (
                <div className="buttonsContainer" ref={buttonsContainerRef}>
                    <button className="glassButton mainButton">
                        <span>SkyVault</span>
                    </button>

                    {showComingSoon && (
                        <button className="glassButton comingSoonButton">
                            <span>Muy Pronto</span>
                        </button>
                    )}

                    <svg style={{ position: 'absolute', width: 0, height: 0 }}>
                        <filter
                            id="glass"
                            x="-50%"
                            y="-50%"
                            width="200%"
                            height="200%"
                            primitiveUnits="objectBoundingBox"
                        >
                            <feImage
                                id="glassImage"
                                x="-50%"
                                y="-50%"
                                width="200%"
                                height="200%"
                                result="map"
                            />
                            <feGaussianBlur in="SourceGraphic" stdDeviation="0.02" result="blur" />
                            <feDisplacementMap
                                id="disp"
                                in="blur"
                                in2="map"
                                scale="0.8"
                                xChannelSelector="R"
                                yChannelSelector="G"
                            />
                        </filter>
                    </svg>
                </div>
            )}

            <div
                className={`backgroundContainer ${showBackground ? 'backgroundVisible' : ''}`}
                ref={backgroundRef}
            />
        </div>
    );
};

export default Home;
