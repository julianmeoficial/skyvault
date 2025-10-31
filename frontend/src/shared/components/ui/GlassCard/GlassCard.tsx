import React from 'react';
import './GlassCard.css';

interface GlassCardProps {
    children: React.ReactNode;
    className?: string;
    hover?: boolean;
}

const GlassCard: React.FC<GlassCardProps> = ({
                                                 children,
                                                 className = '',
                                                 hover = false
                                             }) => {
    return (
        <div className={`glassCard ${hover ? 'glassCard--hover' : ''} ${className}`}>
            {children}
        </div>
    );
};

export default GlassCard;
