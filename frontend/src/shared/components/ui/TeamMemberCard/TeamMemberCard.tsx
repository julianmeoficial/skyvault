import React from 'react';
import './TeamMemberCard.css';

interface TeamMemberCardProps {
    avatar: string;
    name: string;
    role: string;
    bio: string;
}

const TeamMemberCard: React.FC<TeamMemberCardProps> = ({ avatar, name, role, bio }) => {
    // Detectar si el avatar es una imagen (string con extensión)
    const isImage = avatar.endsWith('.png') ||
        avatar.endsWith('.svg') ||
        avatar.endsWith('.jpg') ||
        avatar.endsWith('.jpeg');

    return (
        <div className="teamMemberCard">
            <div className="teamMemberCard__icon">
                {isImage ? (
                    <img
                        src={avatar}
                        alt={`${name} avatar`}
                        className="teamMemberCard__avatarImage"
                    />
                ) : (
                    avatar
                )}
            </div>
            <div className="teamMemberCard__content">
                <h3 className="teamMemberCard__name">{name}</h3>
                <p className="teamMemberCard__role">{role}</p>
                <p className="teamMemberCard__bio">{bio}</p>
            </div>
        </div>
    );
};

export default TeamMemberCard;
