import React, { useEffect, useRef, useMemo } from 'react';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './GalaxyBackground.module.css';

interface Star {
    x: number;
    y: number;
    radius: number;
    opacity: number;
    speed: number;
    twinkleSpeed: number;
}

const GalaxyBackground: React.FC = () => {
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const starsRef = useRef<Star[]>([]);
    const animationIdRef = useRef<number>(0);
    const { theme } = useTheme();

    // Memoizar colores por tema
    const themeColors = useMemo(() => {
        switch (theme) {
            case 'light':
                return {
                    stars: '#1e40af',
                    nebula: 'rgba(59, 130, 246, 0.15)'
                };
            case 'dark':
                return {
                    stars: '#ffffff',
                    nebula: 'rgba(139, 92, 246, 0.2)'
                };
            case 'minimal':
                return {
                    stars: '#5a6c7d',
                    nebula: 'rgba(123, 140, 158, 0.12)'
                };
            default:
                return {
                    stars: '#ffffff',
                    nebula: 'rgba(139, 92, 246, 0.2)'
                };
        }
    }, [theme]);

    useEffect(() => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const ctx = canvas.getContext('2d', {
            alpha: false,
            desynchronized: true // ✅ Performance boost para canvas
        });
        if (!ctx) return;

        // ✅ Optimización: Configurar tamaño del canvas una sola vez
        const resizeCanvas = () => {
            const dpr = window.devicePixelRatio || 1;
            const rect = canvas.getBoundingClientRect();

            canvas.width = rect.width * dpr;
            canvas.height = rect.height * dpr;

            ctx.scale(dpr, dpr);
            canvas.style.width = `${rect.width}px`;
            canvas.style.height = `${rect.height}px`;
        };

        resizeCanvas();
        window.addEventListener('resize', resizeCanvas);

        // ✅ Generar estrellas SOLO una vez
        if (starsRef.current.length === 0) {
            const numStars = window.innerWidth > 768 ? 200 : 100; // Menos estrellas en móvil
            for (let i = 0; i < numStars; i++) {
                starsRef.current.push({
                    x: Math.random() * canvas.width,
                    y: Math.random() * canvas.height,
                    radius: Math.random() * 2,
                    opacity: Math.random(),
                    speed: Math.random() * 0.5 + 0.1,
                    twinkleSpeed: Math.random() * 0.02 + 0.005
                });
            }
        }

        const stars = starsRef.current;

        // ✅ Usar requestAnimationFrame con throttle para 60fps constantes
        let lastTime = 0;
        const fps = 60;
        const interval = 1000 / fps;

        const animate = (currentTime: number) => {
            const deltaTime = currentTime - lastTime;

            if (deltaTime >= interval) {
                lastTime = currentTime - (deltaTime % interval);

                // Limpiar canvas
                ctx.clearRect(0, 0, canvas.width, canvas.height);

                // ✅ Dibujar nebulas (solo 3 círculos)
                for (let i = 0; i < 3; i++) {
                    const gradient = ctx.createRadialGradient(
                        canvas.width * (0.2 + i * 0.3),
                        canvas.height * (0.3 + i * 0.2),
                        0,
                        canvas.width * (0.2 + i * 0.3),
                        canvas.height * (0.3 + i * 0.2),
                        300
                    );
                    gradient.addColorStop(0, themeColors.nebula);
                    gradient.addColorStop(1, 'transparent');
                    ctx.fillStyle = gradient;
                    ctx.fillRect(0, 0, canvas.width, canvas.height);
                }

                // ✅ Batch rendering: Dibujar todas las estrellas en un solo path
                ctx.fillStyle = themeColors.stars;

                stars.forEach(star => {
                    // Efecto de parpadeo
                    star.opacity += star.twinkleSpeed;
                    if (star.opacity > 1 || star.opacity < 0.3) {
                        star.twinkleSpeed *= -1;
                    }

                    ctx.globalAlpha = star.opacity;
                    ctx.beginPath();
                    ctx.arc(star.x, star.y, star.radius, 0, Math.PI * 2);
                    ctx.fill();

                    // Movimiento sutil
                    star.y += star.speed;
                    if (star.y > canvas.height) {
                        star.y = 0;
                        star.x = Math.random() * canvas.width;
                    }
                });

                ctx.globalAlpha = 1;
            }

            animationIdRef.current = requestAnimationFrame(animate);
        };

        animationIdRef.current = requestAnimationFrame(animate);

        return () => {
            window.removeEventListener('resize', resizeCanvas);
            cancelAnimationFrame(animationIdRef.current);
        };
    }, [themeColors]);

    return <canvas ref={canvasRef} className={styles.galaxyCanvas} />;
};

export default React.memo(GalaxyBackground);
