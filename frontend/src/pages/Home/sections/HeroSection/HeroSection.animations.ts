import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

export const animateHeroEnter = (contentElement: HTMLElement): gsap.core.Timeline => {
    const tl = gsap.timeline();

    tl.from(contentElement, {
        opacity: 0,
        y: 50,
        duration: 1.2,
        ease: "power3.out",
    })
        .from(contentElement.querySelector('.heroSection__logo'), {
            scale: 0,
            rotation: 180,
            duration: 1,
            ease: "elastic.out(1, 0.6)",
        }, "-=0.8")
        .from(contentElement.querySelector('.heroSection__title-main'), {
            opacity: 0,
            y: 30,
            duration: 0.8,
            ease: "power2.out",
        }, "-=0.5")
        .from(contentElement.querySelector('.heroSection__title-sub'), {
            opacity: 0,
            y: 20,
            duration: 0.6,
            ease: "power2.out",
        }, "-=0.4")
        .from(contentElement.querySelectorAll('.heroSection__button'), {
            opacity: 0,
            y: 20,
            scale: 0.9,
            duration: 0.6,
            stagger: 0.1,
            ease: "back.out(1.5)",
        }, "-=0.3")
        .from(contentElement.querySelector('.heroSection__scrollIndicator'), {
            opacity: 0,
            y: -20,
            duration: 0.8,
            ease: "power2.out",
        }, "-=0.4");

    return tl;
};

export const animateHeroScroll = (
    heroElement: HTMLElement,
    backgroundElement: HTMLElement,
    contentElement: HTMLElement
): void => {
    gsap.to(backgroundElement, {
        scrollTrigger: {
            trigger: heroElement,
            start: "top top",
            end: "bottom top",
            scrub: 1,
        },
        y: "30%",
        scale: 1.2,
        opacity: 0.3,
        ease: "none",
    });

    gsap.to(contentElement, {
        scrollTrigger: {
            trigger: heroElement,
            start: "top top",
            end: "50% top",
            scrub: 1,
        },
        y: -100,
        opacity: 0,
        scale: 0.95,
        ease: "none",
    });

    gsap.to(heroElement.querySelector('.heroSection__overlay'), {
        scrollTrigger: {
            trigger: heroElement,
            start: "top top",
            end: "bottom top",
            scrub: 1,
        },
        background: "linear-gradient(to bottom, rgba(0,0,0,0.8), rgba(0,0,0,0.9), rgba(0,0,0,1))",
        ease: "none",
    });
};

export default {
    animateHeroEnter,
    animateHeroScroll,
};
