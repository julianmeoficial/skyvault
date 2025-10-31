import React, { useEffect, useRef, useState } from 'react';
import { LoaderProps, LoadingPhase } from './Loader.types';
import { animateLetterLoaderExit, animateDotsExit, animationTimings } from './Loader.animations';
import './Loader.css';

const Loader: React.FC<LoaderProps> = ({
                                           phase = 'dots',
                                           text = 'Construyendo',
                                           onComplete
                                       }) => {
    const [loadingPhase, setLoadingPhase] = useState<LoadingPhase>(phase);
    const dotsRef = useRef<HTMLDivElement>(null);
    const letterLoaderRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        // Secuencia completa: dots → letters → complete
        const timer1 = setTimeout(() => {
            if (dotsRef.current) {
                animateDotsExit(dotsRef.current);
            }
            setLoadingPhase('letters');
        }, animationTimings.DOTS_DURATION);

        const timer2 = setTimeout(() => {
            if (letterLoaderRef.current) {
                const timeline = animateLetterLoaderExit(letterLoaderRef.current);
                timeline.then(() => {
                    setLoadingPhase('hidden');
                    onComplete?.();
                });
            }
        }, animationTimings.DOTS_DURATION + animationTimings.LETTERS_DURATION);

        return () => {
            clearTimeout(timer1);
            clearTimeout(timer2);
        };
    }, [onComplete]);

    if (loadingPhase === 'hidden') {
        return null;
    }

    return (
        <>
            {/* Dots Loading Animation (PRIMERO) */}
            {loadingPhase === 'dots' && (
                <div className="dotsContainer" ref={dotsRef}>
                    <div className="container">
                        <div className="dot dot1"></div>
                        <div className="dot dot2"></div>
                        <div className="dot dot3"></div>
                    </div>
                    {/* Filtro SVG para dots */}
                    <svg style={{ position: 'absolute', width: 0, height: 0 }}>
                        <defs>
                            <filter id="goo">
                                <feGaussianBlur in="SourceGraphic" result="blur" stdDeviation="10" />
                                <feColorMatrix
                                    in="blur"
                                    mode="matrix"
                                    values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 18 -7"
                                    result="goo"
                                />
                                <feBlend in2="goo" in="SourceGraphic" result="mix" />
                            </filter>
                        </defs>
                    </svg>
                </div>
            )}

            {/* Letter Loader Animation (SEGUNDO) */}
            {loadingPhase === 'letters' && (
                <div className="letterLoaderContainer" ref={letterLoaderRef}>
                    <div className="loaderWrapper">
                        {text.split('').map((letter, index) => (
                            <span key={index} className="loaderLetter">
                {letter}
              </span>
                        ))}
                        <div className="loader" />
                    </div>
                </div>
            )}
        </>
    );
};

export default Loader;
