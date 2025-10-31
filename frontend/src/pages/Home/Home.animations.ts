import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

// Configuración global para mejor performance
gsap.config({
    force3D: true,
    nullTargetWarn: false,
});

// =================== ANIMACIÓN DEL HERO ===================
export const initHeroAnimations = (): void => {
    const heroContent = document.querySelector('.home__hero-content');
    const heroBg = document.querySelector('.home__hero-bg');

    if (!heroContent) return;

    // Animación de entrada del contenido del hero
    gsap.to(heroContent, {
        opacity: 1,
        y: 0,
        duration: 1.2,
        ease: "power3.out",
        delay: 0.3,
    });

    // Parallax del background (solo si existe)
    if (heroBg) {
        gsap.to(heroBg, {
            scrollTrigger: {
                trigger: '.home__hero',
                start: "top top",
                end: "bottom top",
                scrub: 1,
            },
            y: "30%",
            ease: "none",
        });
    }
};

// =================== ANIMACIONES DE SCROLL REVEAL ===================
export const initScrollAnimations = (): void => {
    // Intro Section
    const introTitle = document.querySelector('.home__intro-title');
    const introText = document.querySelector('.home__intro-text');

    if (introTitle) {
        gsap.to(introTitle, {
            scrollTrigger: {
                trigger: introTitle,
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.8,
            ease: "power2.out",
        });
    }

    if (introText) {
        gsap.to(introText, {
            scrollTrigger: {
                trigger: introText,
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.8,
            delay: 0.2,
            ease: "power2.out",
        });
    }

    // Data Grid Items con contador animado
    const dataItems = document.querySelectorAll('.home__data-item');
    dataItems.forEach((item, index) => {
        gsap.to(item, {
            scrollTrigger: {
                trigger: '.home__data-grid',
                start: "top 80%",
                toggleActions: "play none none none",
                onEnter: () => animateCounter(item as HTMLElement),
            },
            opacity: 1,
            y: 0,
            duration: 0.6,
            delay: index * 0.1,
            ease: "power2.out",
        });
    });

    // CTA Section
    const ctaTitle = document.querySelector('.home__cta-title');
    const ctaText = document.querySelector('.home__cta-text');
    const ctaButtons = document.querySelectorAll('.home__button');

    if (ctaTitle) {
        gsap.to(ctaTitle, {
            scrollTrigger: {
                trigger: '.home__cta-section',
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.8,
            ease: "power2.out",
        });
    }

    if (ctaText) {
        gsap.to(ctaText, {
            scrollTrigger: {
                trigger: '.home__cta-section',
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.8,
            delay: 0.2,
            ease: "power2.out",
        });
    }

    if (ctaButtons.length > 0) {
        gsap.to(ctaButtons, {
            scrollTrigger: {
                trigger: '.home__cta-buttons',
                start: "top 85%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.6,
            stagger: 0.1,
            delay: 0.3,
            ease: "power2.out",
        });
    }
};

// =================== CONTADOR ANIMADO ===================
const animateCounter = (item: HTMLElement): void => {
    const numberElement = item.querySelector('.home__data-number');
    if (!numberElement) return;

    const text = numberElement.textContent || '0';
    const hasPlus = text.includes('+');
    const targetValue = parseInt(text.replace('+', ''), 10);

    if (isNaN(targetValue)) return;

    const counter = { value: 0 };

    gsap.to(counter, {
        value: targetValue,
        duration: 2,
        ease: "power2.out",
        onUpdate: () => {
            const currentValue = Math.ceil(counter.value);
            numberElement.textContent = hasPlus ? `${currentValue}+` : currentValue.toString();
        }
    });
};

// =================== CLEANUP ===================
export const cleanupAnimations = (): void => {
    ScrollTrigger.getAll().forEach(trigger => trigger.kill());
};

// =================== ANIMACIONES DE FEATURED MODELS ===================
export const initFeaturedAnimations = (): void => {
    const featuredTitle = document.querySelector('.home__featured-title');
    const modelCards = document.querySelectorAll('.home__model');

    if (featuredTitle) {
        gsap.to(featuredTitle, {
            scrollTrigger: {
                trigger: '.home__featured',
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.8,
            ease: "power2.out",
        });
    }

    if (modelCards.length > 0) {
        gsap.to(modelCards, {
            scrollTrigger: {
                trigger: '.home__featured-grid',
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.6,
            stagger: 0.1,
            ease: "power2.out",
        });
    }

    // Animar data title
    const dataTitle = document.querySelector('.home__data-title');
    if (dataTitle) {
        gsap.to(dataTitle, {
            scrollTrigger: {
                trigger: '.home__data',
                start: "top 80%",
                toggleActions: "play none none none",
            },
            opacity: 1,
            y: 0,
            duration: 0.8,
            ease: "power2.out",
        });
    }
};

// =================== INICIALIZAR TODAS LAS ANIMACIONES ===================
export const initAllAnimations = (): void => {
    // Esperar a que el DOM esté listo
    requestAnimationFrame(() => {
        initHeroAnimations();
        initScrollAnimations();
        initFeaturedAnimations();
    });
};
