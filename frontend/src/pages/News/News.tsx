import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider/CrystalSlider';
import GlassCard from '../../shared/components/ui/GlassCard/GlassCard';
import SectionTitle from '../../shared/components/ui/SectionTitle/SectionTitle';
import { NewsCard } from '../../features/news/components/NewsCard';
import { FeaturedNewsCard } from '../../features/news/components/FeaturedNewsCard';
import { NewsSidebar } from '../../features/news/components/NewsSidebar';
import Footer from '../../shared/components/layout/Footer/Footer';
import type { NewsCategory } from '../../features/news/types/news.types';
import { newsData, welcomeText } from '../../data/newsData';
import styles from './News.module.css';

const News: React.FC = () => {
    const navigate = useNavigate();
    const [selectedCategory, setSelectedCategory] = useState<NewsCategory>('Todas');

    const featuredArticle = newsData.find(article => article.isFeatured);

    const filteredNews = newsData.filter(article => {
        if (!article.isFeatured && (selectedCategory === 'Todas' || article.category === selectedCategory)) {
            return true;
        }
        return false;
    });

    const handleArticleClick = (articleId: string) => {
        navigate(`/news/${articleId}`);
    };

    const showFeatured = selectedCategory === 'Todas';

    return (
        <div className={styles.newsPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al inicio" />
                <CrystalSlider position="top-right" />
            </div>

            <div className={styles.container}>
                <div className={styles.header}>
                    <SectionTitle
                        icon={
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z" />
                            </svg>
                        }
                    >
                        Noticias de Aviación
                    </SectionTitle>
                </div>

                <div className={styles.welcomeSection}>
                    <GlassCard className={styles.welcomeCard}>
                        <p className={styles.welcomeText}>{welcomeText}</p>
                    </GlassCard>
                </div>

                <NewsSidebar
                    selectedCategory={selectedCategory}
                    onCategoryChange={setSelectedCategory}
                />

                <div className={styles.contentArea}>
                    {showFeatured && featuredArticle && (
                        <div className={styles.featuredSection}>
                            <h2 className={styles.sectionTitle}>Noticia Destacada</h2>
                            <FeaturedNewsCard
                                article={featuredArticle}
                                onClick={() => handleArticleClick(featuredArticle.id)}
                            />
                        </div>
                    )}

                    <div className={styles.newsSection}>
                        <h2 className={styles.sectionTitle}>
                            {selectedCategory === 'Todas'
                                ? 'Todas las Noticias'
                                : `Noticias sobre ${selectedCategory}`}
                        </h2>
                        <div className={styles.newsGrid}>
                            {filteredNews.length > 0 ? (
                                filteredNews.map((article, index) => (
                                    <div
                                        key={article.id}
                                        className={styles.newsCardWrapper}
                                        style={{ animationDelay: `${index * 0.1}s` }}
                                    >
                                        <NewsCard
                                            article={article}
                                            onClick={() => handleArticleClick(article.id)}
                                        />
                                    </div>
                                ))
                            ) : (
                                <div className={styles.emptyState}>
                                    <p>No hay noticias disponibles en esta categoría.</p>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>

            <Footer />
        </div>
    );
};

export default News;
