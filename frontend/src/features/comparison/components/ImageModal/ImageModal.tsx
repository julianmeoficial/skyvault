import React, { useEffect, useRef } from 'react';
import gsap from 'gsap';
import styles from './ImageModal.module.css';

interface ImageModalProps {
    isOpen: boolean;
    imageUrl: string;
    alt: string;
    onClose: () => void;
}

export const ImageModal: React.FC<ImageModalProps> = ({ isOpen, imageUrl, alt, onClose }) => {
    const overlayRef = useRef<HTMLDivElement>(null);
    const contentRef = useRef<HTMLDivElement>(null);
    const imageRef = useRef<HTMLImageElement>(null);

    // Animación de entrada/salida
    useEffect(() => {
        if (!overlayRef.current || !contentRef.current || !imageRef.current) return;

        if (isOpen) {
            // Bloquear scroll del body
            document.body.style.overflow = 'hidden';

            // Animación de entrada
            gsap.fromTo(
                overlayRef.current,
                { opacity: 0 },
                { opacity: 1, duration: 0.3, ease: 'power2.out' }
            );

            gsap.fromTo(
                contentRef.current,
                { scale: 0.8, opacity: 0 },
                { scale: 1, opacity: 1, duration: 0.4, ease: 'back.out(1.4)', delay: 0.1 }
            );

            gsap.fromTo(
                imageRef.current,
                { scale: 0.95, opacity: 0 },
                { scale: 1, opacity: 1, duration: 0.5, ease: 'power2.out', delay: 0.2 }
            );
        } else {
            // Restaurar scroll del body
            document.body.style.overflow = '';
        }

        return () => {
            document.body.style.overflow = '';
        };
    }, [isOpen]);

    // Cerrar con tecla ESC
    useEffect(() => {
        const handleEscape = (e: KeyboardEvent) => {
            if (e.key === 'Escape' && isOpen) {
                onClose();
            }
        };

        document.addEventListener('keydown', handleEscape);
        return () => document.removeEventListener('keydown', handleEscape);
    }, [isOpen, onClose]);

    // Cerrar al hacer click en el overlay
    const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
        if (e.target === e.currentTarget) {
            onClose();
        }
    };

    if (!isOpen) return null;

    return (
        <div
            className={styles.overlay}
            ref={overlayRef}
            onClick={handleOverlayClick}
            role="dialog"
            aria-modal="true"
            aria-label="Vista ampliada de imagen"
        >
            <div className={styles.content} ref={contentRef}>
                <button
                    className={styles.closeButton}
                    onClick={onClose}
                    aria-label="Cerrar vista ampliada"
                >
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path
                            d="M18 6L6 18M6 6L18 18"
                            stroke="currentColor"
                            strokeWidth="2"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </button>

                <div className={styles.imageWrapper}>
                    <img
                        ref={imageRef}
                        src={imageUrl}
                        alt={alt}
                        className={styles.image}
                        draggable={false}
                    />
                </div>

                <p className={styles.caption}>{alt}</p>
            </div>
        </div>
    );
};
