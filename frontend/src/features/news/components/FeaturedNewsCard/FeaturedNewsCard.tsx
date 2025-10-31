import React from 'react';
import type { NewsArticle } from '../../types/news.types';
import styles from './FeaturedNewsCard.module.css';

interface FeaturedNewsCardProps {
    article: NewsArticle;
    onClick: () => void;
}

export const FeaturedNewsCard: React.FC<FeaturedNewsCardProps> = ({ article, onClick }) => {
    const formatDate = (dateString: string) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        }).format(date);
    };

    return (
        <article className={styles.featuredCard} onClick={onClick}>
            {/* Imagen horizontal en la parte superior */}
            <div className={styles.imageSection}>
                <img
                    src={article.imageUrl}
                    alt={article.title}
                    className={styles.image}
                    loading="eager"
                />
                <div className={styles.featuredBadge}>
                    <svg className={styles.starIcon} viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z" />
                    </svg>
                    NOTICIA DESTACADA
                </div>
                <div className={styles.overlay} />
            </div>

            {/* Contenido debajo de la imagen */}
            <div className={styles.contentSection}>
                <div className={styles.meta}>
                    <span className={styles.categoryBadge}>{article.category}</span>
                    <time className={styles.date}>{formatDate(article.date)}</time>
                </div>

                <h2 className={styles.title}>{article.title}</h2>

                <div className={styles.excerpt}>
                    {article.content.split('\n\n').slice(0, 2).map((paragraph, index) => (
                        <p key={index} className={styles.paragraph}>{paragraph}</p>
                    ))}
                </div>

                {article.tags && article.tags.length > 0 && (
                    <div className={styles.tags}>
                        {article.tags.map((tag, index) => (
                            <span key={index} className={styles.tag}>
                                {tag}
                            </span>
                        ))}
                    </div>
                )}

                <button className={styles.readFullButton}>
                    <span>Leer artículo completo</span>
                    <svg className={styles.buttonIcon} viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 8l4 4m0 0l-4 4m4-4H3" />
                    </svg>
                </button>
            </div>

            <div className={styles.glowEffect} />
        </article>
    );
};
