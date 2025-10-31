import { useEffect, useMemo, useRef } from 'react';
import type { RefObject } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

interface ScrollRevealOptions {
    trigger?: string | Element;
    start?: string;
    end?: string;
    scrub?: boolean | number;
    markers?: boolean;
    animation?: gsap.TweenVars;
}

export const useScrollReveal = (
    ref: RefObject<HTMLElement>,
    options: ScrollRevealOptions = {}
) => {
    const tweenRef = useRef<gsap.core.Tween | null>(null);

    // SOLUCIÓN: Memoizar options para evitar recreación
    const memoizedOptions = useMemo(() => ({
        trigger: options.trigger,
        start: options.start || 'top 80%',
        end: options.end || 'bottom 20%',
        scrub: options.scrub || false,
        markers: options.markers || false,
        animation: options.animation || { opacity: 0, y: 50 }
    }), [
        options.trigger,
        options.start,
        options.end,
        options.scrub,
        options.markers,
        JSON.stringify(options.animation) // Stringificar objeto animation
    ]);

    useEffect(() => {
        if (!ref.current) return;

        const element = ref.current;
        const {
            trigger = element,
            start,
            end,
            scrub,
            markers,
            animation
        } = memoizedOptions;

        //  Hardware acceleration
        gsap.set(element, {
            force3D: true,
            backfaceVisibility: 'hidden'
        });

        //  Guardar referencia del tween
        tweenRef.current = gsap.fromTo(
            element,
            animation,
            {
                opacity: 1,
                y: 0,
                duration: 1,
                ease: 'power3.out',
                force3D: true,
                scrollTrigger: {
                    trigger,
                    start,
                    end,
                    scrub,
                    markers,
                    toggleActions: 'play none none reverse'
                }
            }
        );

        // CLEANUP OPTIMIZADO: Matar directamente el tween
        return () => {
            if (tweenRef.current) {
                tweenRef.current.kill(); // Mata tween + su ScrollTrigger
                tweenRef.current = null;
            }
        };
    }, [ref, memoizedOptions]); //  Ahora sí es estable
};
