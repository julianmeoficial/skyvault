import React from 'react';
import './AnimatedBorderCard.css';

interface AnimatedBorderCardProps {
    icon?: React.ReactNode | string;
    title?: string;
    description?: string;
    children?: React.ReactNode;
}

const AnimatedBorderCard: React.FC<AnimatedBorderCardProps> = ({
                                                                   icon,
                                                                   title,
                                                                   description,
                                                                   children
                                                               }) => {
    // Detectar si el icon es una imagen (string con extensión)
    const isImage = typeof icon === 'string' && (
        icon.endsWith('.png') ||
        icon.endsWith('.svg') ||
        icon.endsWith('.jpg') ||
        icon.endsWith('.jpeg')
    );

    return (
        <div className="animatedBorderCard">
            <div className="animatedBorderCard__bg">
                {children ? (
                    children
                ) : (
                    <>
                        {icon && (
                            <div className="animatedBorderCard__icon">
                                {isImage ? (
                                    <img
                                        src={icon as string}
                                        alt=""
                                        className="animatedBorderCard__iconImage"
                                    />
                                ) : (
                                    icon
                                )}
                            </div>
                        )}
                        {title && <h3 className="animatedBorderCard__title">{title}</h3>}
                        {description && <p className="animatedBorderCard__description">{description}</p>}
                    </>
                )}
            </div>
            <div className="animatedBorderCard__blob" />
        </div>
    );
};

export default AnimatedBorderCard;
