import React from 'react';
import './ValueCard.css';

interface ValueCardProps {
    icon: React.ReactNode;
    title: string;
    description: string;
}

const ValueCard: React.FC<ValueCardProps> = ({ icon, title, description }) => {
    return (
        <div className="valueCard">
            <div className="valueCard__content">
                <div className="valueCard__icon">{icon}</div>
                <div className="valueCard__text">
                    <h3 className="valueCard__title">{title}</h3>
                    <p className="valueCard__description">{description}</p>
                </div>
            </div>
        </div>
    );
};

export default ValueCard;
