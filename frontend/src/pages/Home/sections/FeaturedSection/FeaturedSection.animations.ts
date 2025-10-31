import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

export const animateFeaturedSection = (sectionElement: HTMLElement): void => {
    gsap.from(sectionElement.querySelector('.featuredSection__title'), {
        scrollTrigger: {
            trigger: sectionElement,
            start: "top 80%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        y: 50,
        duration: 0.8,
        ease: "power2.out",
    });

    gsap.from(sectionElement.querySelector('.featuredSection__subtitle'), {
        scrollTrigger: {
            trigger: sectionElement,
            start: "top 75%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        y: 30,
        duration: 0.6,
        ease: "power2.out",
    });

    gsap.from(sectionElement.querySelectorAll('.featuredCard'), {
        scrollTrigger: {
            trigger: sectionElement.querySelector('.featuredSection__grid'),
            start: "top 80%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        y: 60,
        scale: 0.9,
        duration: 0.7,
        stagger: 0.15,
        ease: "back.out(1.4)",
    });

    gsap.from(sectionElement.querySelector('.featuredSection__button'), {
        scrollTrigger: {
            trigger: sectionElement.querySelector('.featuredSection__cta'),
            start: "top 85%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        y: 40,
        scale: 0.95,
        duration: 0.6,
        ease: "back.out(1.5)",
    });
};

export default { animateFeaturedSection };
