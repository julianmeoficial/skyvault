import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

gsap.registerPlugin(ScrollTrigger);

export const animateStatsSection = (sectionElement: HTMLElement): void => {
    gsap.from(sectionElement.querySelector('.statsSection__title'), {
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

    gsap.from(sectionElement.querySelector('.statsSection__subtitle'), {
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

    const statCards = sectionElement.querySelectorAll('.statCard');

    statCards.forEach((card, index) => {
        gsap.from(card, {
            scrollTrigger: {
                trigger: sectionElement.querySelector('.statsSection__grid'),
                start: "top 80%",
                toggleActions: "play none none reverse",
                onEnter: () => {
                    animateCounter(card);
                }
            },
            opacity: 0,
            y: 60,
            scale: 0.8,
            duration: 0.7,
            delay: index * 0.15,
            ease: "back.out(1.5)",
        });
    });
};

const animateCounter = (card: Element): void => {
    const numberElement = card.querySelector('.statCard__number');
    if (!numberElement) return;

    // Obtener el valor objetivo directamente del contenido del elemento
    const finalValue = parseInt(numberElement.textContent || '0', 10);

    // Crear un objeto temporal para animar
    const counter = { value: 0 };

    gsap.to(counter, {
        value: finalValue,
        duration: 2,
        ease: "power2.out",
        onUpdate: () => {
            if (numberElement) {
                numberElement.textContent = Math.ceil(counter.value).toString();
            }
        }
    });
};
