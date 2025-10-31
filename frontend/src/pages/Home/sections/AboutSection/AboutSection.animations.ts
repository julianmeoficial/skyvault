import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

export const animateAboutSection = (sectionElement: HTMLElement): void => {
    gsap.from(sectionElement.querySelector('.aboutSection__title'), {
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

    gsap.from(sectionElement.querySelectorAll('.aboutSection__text'), {
        scrollTrigger: {
            trigger: sectionElement,
            start: "top 75%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        y: 30,
        duration: 0.6,
        stagger: 0.2,
        ease: "power2.out",
    });

    gsap.from(sectionElement.querySelectorAll('.aboutSection__feature'), {
        scrollTrigger: {
            trigger: sectionElement.querySelector('.aboutSection__features'),
            start: "top 80%",
            toggleActions: "play none none reverse",
        },
        opacity: 0,
        y: 40,
        scale: 0.9,
        duration: 0.6,
        stagger: 0.15,
        ease: "back.out(1.5)",
    });
};

export default { animateAboutSection };
