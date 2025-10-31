import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

export const animateCTASection = (sectionElement: HTMLElement): void => {
    const content = sectionElement.querySelector('.ctaSection__content');
    if (!content) return;

    gsap.from(content, {
        scrollTrigger: {
            trigger: sectionElement,
            start: "top 80%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        scale: 0.9,
        y: 60,
        duration: 1,
        ease: "back.out(1.4)",
    });

    const icon = content.querySelector('.ctaSection__icon');
    if (icon) {
        gsap.from(icon, {
            scrollTrigger: {
                trigger: content,
                start: "top 75%",
                toggleActions: "play none none reverse",
            },
            opacity: 0,
            scale: 0,
            rotation: 180,
            duration: 0.8,
            ease: "elastic.out(1, 0.6)",
        });
    }

    const title = content.querySelector('.ctaSection__title');
    if (title) {
        gsap.from(title, {
            scrollTrigger: {
                trigger: content,
                start: "top 70%",
                toggleActions: "play none none reverse",
            },
            opacity: 0,
            y: 30,
            duration: 0.7,
            ease: "power2.out",
        });
    }

    const text = content.querySelector('.ctaSection__text');
    if (text) {
        gsap.from(text, {
            scrollTrigger: {
                trigger: content,
                start: "top 65%",
                toggleActions: "play none none reverse",
            },
            opacity: 0,
            y: 20,
            duration: 0.6,
            ease: "power2.out",
        });
    }

    const buttons = content.querySelectorAll('.ctaSection__button');
    const buttonsContainer = content.querySelector('.ctaSection__buttons');
    if (buttons.length > 0 && buttonsContainer) {
        gsap.from(buttons, {
            scrollTrigger: {
                trigger: buttonsContainer,
                start: "top 80%",
                toggleActions: "play none none reverse",
            },
            opacity: 0,
            y: 30,
            scale: 0.9,
            duration: 0.6,
            stagger: 0.15,
            ease: "back.out(1.5)",
        });
    }
};
