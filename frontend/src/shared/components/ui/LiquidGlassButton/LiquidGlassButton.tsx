import React, { useRef, useEffect } from 'react';
import gsap from 'gsap';
import styles from './LiquidGlassButton.module.css';

interface LiquidGlassButtonProps {
    children: React.ReactNode;
    onClick?: () => void;
    disabled?: boolean;
    variant?: 'primary' | 'secondary' | 'add';
    type?: 'button' | 'submit' | 'reset';
    className?: string;
    icon?: React.ReactNode;
}

export const LiquidGlassButton: React.FC<LiquidGlassButtonProps> = ({
                                                                        children,
                                                                        onClick,
                                                                        disabled = false,
                                                                        variant = 'primary',
                                                                        type = 'button',
                                                                        className = '',
                                                                        icon
                                                                    }) => {
    const buttonRef = useRef<HTMLButtonElement>(null);
    const liquidRef = useRef<HTMLDivElement>(null);
    const glowRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!buttonRef.current || !liquidRef.current || !glowRef.current) return;

        const button = buttonRef.current;
        const liquid = liquidRef.current;
        const glow = glowRef.current;

        // Animación de entrada
        gsap.fromTo(
            button,
            { scale: 0.95, opacity: 0 },
            { scale: 1, opacity: 1, duration: 0.5, ease: 'back.out(1.4)' }
        );

        const handleMouseEnter = (e: MouseEvent) => {
            const rect = button.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            // Efecto líquido expansivo
            gsap.to(liquid, {
                duration: 0.8,
                scale: 1.5,
                x: x - rect.width / 2,
                y: y - rect.height / 2,
                opacity: 1,
                ease: 'power2.out'
            });

            // Glow effect
            gsap.to(glow, {
                duration: 0.6,
                scale: 1.1,
                opacity: 0.8,
                ease: 'power2.out'
            });

            // Elevación del botón
            gsap.to(button, {
                duration: 0.3,
                y: -4,
                ease: 'power2.out'
            });
        };

        const handleMouseLeave = () => {
            gsap.to(liquid, {
                duration: 0.6,
                scale: 0,
                opacity: 0,
                ease: 'power2.in'
            });

            gsap.to(glow, {
                duration: 0.5,
                scale: 1,
                opacity: 0,
                ease: 'power2.in'
            });

            gsap.to(button, {
                duration: 0.3,
                y: 0,
                ease: 'power2.in'
            });
        };

        const handleMouseMove = (e: MouseEvent) => {
            const rect = button.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            gsap.to(liquid, {
                duration: 0.3,
                x: x - rect.width / 2,
                y: y - rect.height / 2,
                ease: 'power2.out'
            });
        };

        const handleClick = () => {
            // Animación de click (ripple effect)
            gsap.fromTo(
                liquid,
                { scale: 0.8, opacity: 1 },
                {
                    scale: 2,
                    opacity: 0,
                    duration: 0.6,
                    ease: 'power3.out',
                    onComplete: () => {
                        gsap.set(liquid, { scale: 0, opacity: 0 });
                    }
                }
            );
        };

        button.addEventListener('mouseenter', handleMouseEnter);
        button.addEventListener('mouseleave', handleMouseLeave);
        button.addEventListener('mousemove', handleMouseMove);
        button.addEventListener('click', handleClick);

        return () => {
            button.removeEventListener('mouseenter', handleMouseEnter);
            button.removeEventListener('mouseleave', handleMouseLeave);
            button.removeEventListener('mousemove', handleMouseMove);
            button.removeEventListener('click', handleClick);
        };
    }, []);

    return (
        <button
            ref={buttonRef}
            type={type}
            onClick={onClick}
            disabled={disabled}
            className={`${styles.liquidButton} ${styles[variant]} ${disabled ? styles.disabled : ''} ${className}`}
        >
            <div ref={glowRef} className={styles.glowEffect} />
            <div ref={liquidRef} className={styles.liquidEffect} />
            <span className={styles.buttonContent}>
        {children}
                {icon && <span className={styles.icon}>{icon}</span>}
      </span>
        </button>
    );
};

export type { LiquidGlassButtonProps };