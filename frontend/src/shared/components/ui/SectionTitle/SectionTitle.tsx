import React from 'react';
import './SectionTitle.css';

interface SectionTitleProps {
    children: React.ReactNode;
    icon?: React.ReactNode;
}

const SectionTitle: React.FC<SectionTitleProps> = ({ children, icon }) => {
    return (
        <div className="sectionTitle">
            {icon && <div className="sectionTitle__icon">{icon}</div>}
            <span className="sectionTitle__text">{children}</span>
        </div>
    );
};

export default SectionTitle;
