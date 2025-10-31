import { gsap } from "gsap";

// Configuración del efecto Glass
export const glassConfig = {
    mapImageUrl: "https://essykings.github.io/JavaScript/map.png",
    blurIntensity: 0.02,
    displacementScale: 0.8
};

// Inicializar efecto Glass
export const initializeGlassEffect = (): void => {
    const feImage = document.querySelector("#glassImage");

    if (!feImage) {
        console.warn("Glass image element not found");
        return;
    }

    fetch(glassConfig.mapImageUrl)
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.blob();
        })
        .then((blob) => {
            const objURL = URL.createObjectURL(blob);
            feImage.setAttribute("href", objURL);
        })
        .catch((error) => {
            console.error("Error loading glass effect image:", error);
            feImage.setAttribute("href", "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTAwIiBoZWlnaHQ9IjEwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48ZGVmcz48bGluZWFyR3JhZGllbnQgaWQ9ImciPjxzdG9wIG9mZnNldD0iMCUiIHN0b3AtY29sb3I9IiNmZmYiLz48c3RvcCBvZmZzZXQ9IjEwMCUiIHN0b3AtY29sb3I9IiMwMDAiLz48L2xpbmVhckdyYWRpZW50PjwvZGVmcz48cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJ1cmwoI2cpIi8+PC9zdmc+");
        });
};

// Animación de entrada de botones (más dramática)
export const animateButtonsEnter = (buttonsContainer: HTMLElement): gsap.core.Timeline => {
    const tl = gsap.timeline();

    gsap.set(buttonsContainer, {
        opacity: 0,
        scale: 0.7,
        rotationY: 180,
        transformPerspective: 1000
    });

    tl.to(buttonsContainer, {
        opacity: 1,
        scale: 1,
        rotationY: 0,
        duration: 1.2,
        ease: "elastic.out(1, 0.8)",
        delay: 0.1
    })
        .to(buttonsContainer.querySelector('.mainButton'), {
            filter: "drop-shadow(0 0 20px rgba(255, 255, 255, 0.3))",
            duration: 0.8,
            ease: "power2.out"
        }, "-=0.5");

    return tl;
};

// Efectos hover mejorados con GSAP para liquid glass premium
export const createAdvancedHoverEffect = (element: HTMLElement): void => {
    if (!element) return;

    let hoverTl: gsap.core.Timeline | null = null;

    element.addEventListener('mouseenter', () => {
        if (hoverTl) hoverTl.kill();

        hoverTl = gsap.timeline();

        hoverTl.to(element, {
            scale: 1.08,
            duration: 0.6,
            ease: "power2.out"
        })
            .to(element.querySelector('span'), {
                textShadow: "0 0 30px rgba(255, 255, 255, 0.9), 0 2px 6px rgba(0, 0, 0, 0.4)",
                duration: 0.4,
                ease: "power2.out"
            }, "-=0.4")
            .to(element, {
                filter: "drop-shadow(0 0 30px rgba(255, 255, 255, 0.4)) brightness(1.15) saturate(1.2)",
                duration: 0.3,
                ease: "power2.out"
            }, "-=0.3");
    });

    element.addEventListener('mouseleave', () => {
        if (hoverTl) hoverTl.kill();

        hoverTl = gsap.timeline();

        hoverTl.to(element, {
            scale: 1,
            filter: "drop-shadow(0 0 0px rgba(255, 255, 255, 0)) brightness(1) saturate(1)",
            duration: 0.8,
            ease: "power2.out"
        })
            .to(element.querySelector('span'), {
                textShadow: "0 0 20px rgba(255, 255, 255, 0.6), 0 2px 4px rgba(0, 0, 0, 0.3)",
                duration: 0.6,
                ease: "power2.out"
            }, "-=0.6");
    });

    element.addEventListener('mousedown', () => {
        gsap.to(element, {
            scale: 0.95,
            duration: 0.1,
            ease: "power2.out"
        });
    });

    element.addEventListener('mouseup', () => {
        gsap.to(element, {
            scale: 1.08,
            duration: 0.2,
            ease: "elastic.out(1, 0.5)"
        });
    });
};

export default {
    glassConfig,
    initializeGlassEffect,
    animateButtonsEnter,
    createAdvancedHoverEffect
};
