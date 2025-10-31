import { gsap } from "gsap";

// Animación de entrada del header
export const animateHeaderEnter = (headerElement: HTMLElement): gsap.core.Timeline => {
    const tl = gsap.timeline();

    gsap.set(headerElement, {
        opacity: 0,
        y: -30,
    });

    tl.to(headerElement, {
        opacity: 1,
        y: 0,
        duration: 0.8,
        ease: "power3.out",
        delay: 0.2,
    });

    return tl;
};

// Animación al hacer scroll
export const animateHeaderScroll = (
    headerElement: HTMLElement,
    isScrolled: boolean
): void => {
    if (isScrolled) {
        gsap.to(headerElement, {
            padding: "0.6rem 0",
            duration: 0.4,
            ease: "power2.out",
        });
    } else {
        gsap.to(headerElement, {
            padding: "1rem 0",
            duration: 0.4,
            ease: "power2.out",
        });
    }
};

export default {
    animateHeaderEnter,
    animateHeaderScroll,
};
