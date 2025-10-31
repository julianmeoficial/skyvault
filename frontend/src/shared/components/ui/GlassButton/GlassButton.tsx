import React from 'react';
import './GlassButton.css';

interface GlassButtonProps {
    children: React.ReactNode;
    onClick?: () => void;
    className?: string;
    type?: 'button' | 'submit' | 'reset';
}

const GlassButton: React.FC<GlassButtonProps> = ({
                                                     children,
                                                     onClick,
                                                     className = '',
                                                     type = 'button'
                                                 }) => {
    return (
        <button
            type={type}
            className={`glassButton ${className}`}
            onClick={onClick}
        >
            <span className="glassButton__text">{children}</span>
        </button>
    );
};

export default GlassButton;
