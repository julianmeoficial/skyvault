import { gsap } from "gsap";

// Timings de animaciones optimizados
export const animationTimings = {
    DOTS_DURATION: 2000,
    LETTERS_DURATION: 1500,
    TRANSITION_DURATION: 400
};

// Configuración del efecto Glass
export const glassConfig = {
    mapImageUrl: "https://essykings.github.io/JavaScript/map.png",
    blurIntensity: 0.02,
    displacementScale: 0.8
};

// Animación de salida del loader de letras
export const animateLetterLoaderExit = (letterLoaderContainer: HTMLElement): gsap.core.Timeline => {
    const tl = gsap.timeline();

    tl.to(letterLoaderContainer.querySelectorAll('.loaderLetter'), {
        scale: 0,
        opacity: 0,
        duration: 0.3,
        stagger: 0.03,
        ease: "back.in(1.7)"
    })
        .to(letterLoaderContainer.querySelector('.loader'), {
            scale: 0,
            opacity: 0,
            duration: 0.4,
            ease: "power2.in"
        }, "-=0.2")
        .to(letterLoaderContainer, {
            opacity: 0,
            scale: 1.1,
            duration: 0.5,
            ease: "power2.inOut",
            onComplete: () => {
                letterLoaderContainer.style.visibility = 'hidden';
                letterLoaderContainer.style.pointerEvents = 'none';
            }
        }, "-=0.3");

    return tl;
};

// Animación de salida de dots
export const animateDotsExit = (dotsContainer: HTMLElement): gsap.core.Tween => {
    return gsap.to(dotsContainer, {
        opacity: 0,
        scale: 1.05,
        duration: 0.6,
        ease: "power2.inOut",
        onComplete: () => {
            dotsContainer.style.visibility = 'hidden';
            dotsContainer.style.pointerEvents = 'none';
        }
    });
};

// Configuración de easing personalizado
export const easingFunctions = {
    glassTransition: 'cubic-bezier(0.175, 0.885, 0.32, 2.2)',
    smoothFade: 'cubic-bezier(0.4, 0, 0.2, 1)',
    bounceIn: 'cubic-bezier(0.68, -0.55, 0.265, 1.55)',
    elasticOut: 'cubic-bezier(0.68, -0.55, 0.265, 1.55)',
    dramaticEnter: 'cubic-bezier(0.175, 0.885, 0.32, 2.2)'
};

// Función de limpieza para URLs creadas
export const cleanupObjectURLs = (): void => {
    const images = document.querySelectorAll('feImage[href^="blob:"]');
    images.forEach(img => {
        const href = img.getAttribute('href');
        if (href && href.startsWith('blob:')) {
            URL.revokeObjectURL(href);
        }
    });
};

export default {
    animationTimings,
    glassConfig,
    animateLetterLoaderExit,
    animateDotsExit,
    easingFunctions,
    cleanupObjectURLs
};
