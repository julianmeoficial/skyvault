import React from 'react';
import type { NewsCategory } from '../../types/news.types';
import styles from './NewsSidebar.module.css';

interface NewsSidebarProps {
    selectedCategory: NewsCategory;
    onCategoryChange: (category: NewsCategory) => void;
}

export const NewsSidebar: React.FC<NewsSidebarProps> = ({
                                                            selectedCategory,
                                                            onCategoryChange
                                                        }) => {
    const categories: { name: NewsCategory; // @ts-ignore
        icon: JSX.Element }[] = [
        {
            name: 'Todas',
            icon: (
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M4 6h16M4 10h16M4 14h16M4 18h16" stroke="currentColor" strokeWidth={2} strokeLinecap="round" />
                </svg>
            )
        },
        {
            name: 'Airbus',
            icon: (
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M20.56 3.91C21.15 4.5 21.15 5.45 20.56 6.03L16.67 9.92L18.79 19.11L17.38 20.53L13.5 13.1L9.6 17L9.96 19.47L8.89 20.53L7.13 17.35L3.94 15.58L5 14.5L7.5 14.87L11.37 11L3.94 7.09L5.36 5.68L14.55 7.8L18.44 3.91C19 3.33 20 3.33 20.56 3.91Z" />
                </svg>
            )
        },
        {
            name: 'Boeing',
            icon: (
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M21,16V14L13,9V3.5A1.5,1.5 0 0,0 11.5,2A1.5,1.5 0 0,0 10,3.5V9L2,14V16L10,13.5V19L8,20.5V22L11.5,21L15,22V20.5L13,19V13.5L21,16Z" />
                </svg>
            )
        },
        {
            name: 'Aerolíneas',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
            )
        },
        {
            name: 'Infraestructura',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                </svg>
            )
        }
    ];

    return (
        <nav className={styles.sidebar}>
            <div className={styles.categoryList}>
                {categories.map((category) => (
                    <button
                        key={category.name}
                        className={`${styles.categoryButton} ${
                            selectedCategory === category.name ? styles.active : ''
                        }`}
                        onClick={() => onCategoryChange(category.name)}
                    >
                        <span className={styles.categoryIcon}>
                            {category.icon}
                        </span>
                        <span className={styles.categoryLabel}>{category.name}</span>
                    </button>
                ))}
            </div>
        </nav>
    );
};
