import React from 'react';
import type { NewsArticle } from '../../types/news.types';
import styles from './NewsCard.module.css';

interface NewsCardProps {
    article: NewsArticle;
    onClick: () => void;
}

export const NewsCard: React.FC<NewsCardProps> = ({ article, onClick }) => {
    const formatDate = (dateString: string) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        }).format(date);
    };

    const truncateText = (text: string, maxLength: number) => {
        if (text.length <= maxLength) return text;
        return text.substring(0, maxLength).trim() + '...';
    };

    return (
        <article className={styles.newsCard} onClick={onClick}>
            <div className={styles.imageContainer}>
                <img
                    src={article.imageUrl}
                    alt={article.title}
                    className={styles.image}
                    loading="lazy"
                />
                <div className={styles.categoryBadge}>
                    {article.category}
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.meta}>
                    <time className={styles.date}>{formatDate(article.date)}</time>
                    {article.tags && article.tags.length > 0 && (
                        <div className={styles.tags}>
                            {article.tags.slice(0, 2).map((tag, index) => (
                                <span key={index} className={styles.tag}>
                                    {tag}
                                </span>
                            ))}
                        </div>
                    )}
                </div>

                <h3 className={styles.title}>{article.title}</h3>

                <p className={styles.excerpt}>
                    {truncateText(article.content, 150)}
                </p>

                <button className={styles.readMore}>
                    Leer más
                    <svg
                        className={styles.arrow}
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M9 5l7 7-7 7"
                        />
                    </svg>
                </button>
            </div>

            <div className={styles.glassEffect} />
            <div className={styles.borderGlow} />
        </article>
    );
};
