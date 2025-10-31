import React, { useRef, useEffect } from 'react';
import { gsap } from 'gsap';
import './MissionGlassCard.css';

interface MissionGlassCardProps {
    children: React.ReactNode;
}

const MissionGlassCard: React.FC<MissionGlassCardProps> = ({ children }) => {
    const cardRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (cardRef.current) {
            gsap.fromTo(
                cardRef.current,
                { opacity: 0, y: 30, scale: 0.95 },
                {
                    opacity: 1,
                    y: 0,
                    scale: 1,
                    duration: 0.8,
                    ease: 'power2.out',
                    delay: 0.2
                }
            );
        }
    }, []);

    return (
        <div className="missionGlassCard" ref={cardRef}>
            {children}
        </div>
    );
};

export default MissionGlassCard;
