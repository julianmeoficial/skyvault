import React from 'react';
import { TeamMemberCard } from '../../../shared/components/ui/TeamMemberCard';
import { SectionTitle } from '../../../shared/components/ui/SectionTitle';

// Importa los iconos desde public/assets
import fullStackIcon from '/assets/icons/specifications/personfullstack.png';
import uxuiIcon from '/assets/icons/specifications/uxuicon.png';

const TeamSection: React.FC = () => {
    const team = [
        {
            name: 'Julian Espitia',
            role: 'Full Stack Developer',
            bio: 'Desarrollador apasionado por la aviación y la tecnología web moderna',
            avatar: fullStackIcon
        },
        {
            name: 'Monica Vellojin',
            role: 'UI/UX Designer',
            bio: 'Especialista en diseño de experiencias visuales minimalistas',
            avatar: uxuiIcon
        }
    ];

    return (
        <section className="section teamSection">
            <div className="section__container">
                <SectionTitle>Equipo</SectionTitle>
                <div className="teamSection__grid">
                    {team.map((member, index) => (
                        <TeamMemberCard
                            key={index}
                            avatar={member.avatar}
                            name={member.name}
                            role={member.role}
                            bio={member.bio}
                        />
                    ))}
                </div>
            </div>
        </section>
    );
};

export default TeamSection;
