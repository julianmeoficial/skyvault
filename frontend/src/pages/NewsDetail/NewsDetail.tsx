import React, { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider/CrystalSlider';
import Footer from '../../shared/components/layout/Footer/Footer';
import { newsData } from '../../data/newsData';
import styles from './NewsDetail.module.css';

const NewsDetail: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const article = newsData.find(news => news.id === id);

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [id]);

    if (!article) {
        return (
            <div className={styles.errorPage}>
                <div className={styles.topControls}>
                    <BackButton to="/news" label="Volver a Noticias" />
                    <CrystalSlider position="top-right" />
                </div>
                <div className={styles.errorContent}>
                    <h1>Artículo no encontrado</h1>
                    <button onClick={() => navigate('/news')} className={styles.backButton}>
                        Volver a Noticias
                    </button>
                </div>
            </div>
        );
    }

    const formatDate = (dateString: string) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        }).format(date);
    };

    const contentParagraphs = article.content.split('\n\n');

    return (
        <div className={styles.newsDetailPage}>
            <div className={styles.topControls}>
                <BackButton to="/news" label="Volver a Noticias" />
                <CrystalSlider position="top-right" />
            </div>

            <article className={styles.article}>
                <header className={styles.articleHeader}>
                    <div className={styles.headerContent}>
                        <h1 className={styles.title}>{article.title}</h1>
                        <div className={styles.meta}>
                            <time className={styles.date}>{formatDate(article.date)}</time>
                            {article.tags && article.tags.length > 0 && (
                                <div className={styles.tags}>
                                    {article.tags.map((tag, index) => (
                                        <span key={index} className={styles.tag}>
                                            {tag}
                                        </span>
                                    ))}
                                </div>
                            )}
                        </div>
                    </div>
                </header>

                <div className={styles.featuredImage}>
                    <img src={article.imageUrl} alt={article.title} />
                    {article.isFeatured && (
                        <div className={styles.featuredBadge}>
                            <svg className={styles.starIcon} viewBox="0 0 24 24" fill="currentColor">
                                <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z" />
                            </svg>
                            NOTICIA DESTACADA
                        </div>
                    )}
                </div>

                <div className={styles.articleContent}>
                    <div className={styles.contentWrapper}>
                        {contentParagraphs.map((paragraph, index) => {
                            const isFirstParagraph = index === 0;
                            const isSecondParagraph = index === 1;
                            const isThirdParagraph = index === 2;
                            const remainingParagraphs = index > 2;

                            if (isFirstParagraph) {
                                return (
                                    <div key={index} className={styles.textImageRow}>
                                        <p className={styles.paragraphHalf}>{paragraph}</p>
                                        <div className={styles.inlineImage}>
                                            <img src={article.imageUrl2 || article.imageUrl} alt={article.title} />
                                        </div>
                                    </div>
                                );
                            }

                            if (isSecondParagraph) {
                                return (
                                    <p key={index} className={styles.paragraphFull}>
                                        {paragraph}
                                    </p>
                                );
                            }

                            if (isThirdParagraph) {
                                return (
                                    <div key={index} className={styles.textImageRowReverse}>
                                        <div className={styles.inlineImage}>
                                            <img src={article.imageUrl3 || article.imageUrl} alt={article.title} />
                                        </div>
                                        <p className={styles.paragraphHalf}>{paragraph}</p>
                                    </div>
                                );
                            }

                            if (remainingParagraphs) {
                                return (
                                    <p key={index} className={styles.paragraphFull}>
                                        {paragraph}
                                    </p>
                                );
                            }

                            return null;
                        })}
                    </div>

                    <aside className={styles.sidebar}>
                    <div className={styles.sidebarCard}>
                            <h3 className={styles.sidebarTitle}>Información del artículo</h3>
                            <div className={styles.sidebarContent}>
                                <div className={styles.infoItem}>
                                    <span className={styles.infoLabel}>Categoría:</span>
                                    <span className={styles.infoValue}>{article.category}</span>
                                </div>
                                <div className={styles.infoItem}>
                                    <span className={styles.infoLabel}>Fecha:</span>
                                    <span className={styles.infoValue}>{formatDate(article.date)}</span>
                                </div>
                                {article.tags && article.tags.length > 0 && (
                                    <div className={styles.infoItem}>
                                        <span className={styles.infoLabel}>Etiquetas:</span>
                                        <div className={styles.sidebarTags}>
                                            {article.tags.map((tag, index) => (
                                                <span key={index} className={styles.sidebarTag}>
                                                    {tag}
                                                </span>
                                            ))}
                                        </div>
                                    </div>
                                )}
                            </div>
                        </div>
                    </aside>
                </div>

                <div className={styles.articleFooter}>
                    <button onClick={() => navigate('/news')} className={styles.backToNewsButton}>
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                        </svg>
                        Volver a todas las noticias
                    </button>
                </div>
            </article>

            <Footer />
        </div>
    );
};

export default NewsDetail;
