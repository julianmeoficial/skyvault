import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider/CrystalSlider';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './FAQ.module.css';

interface FAQItem {
    question: string;
    answer: string;
}

interface FAQCategory {
    id: string;
    name: string;
    // @ts-ignore
    icon: JSX.Element;
    faqs: FAQItem[];
}

const FAQ: React.FC = () => {
    const navigate = useNavigate();
    const [selectedCategory, setSelectedCategory] = useState<string>('general');
    const [expandedFAQ, setExpandedFAQ] = useState<number | null>(null);
    const [isMobile, setIsMobile] = useState(window.innerWidth <= 1024);

    useEffect(() => {
        window.scrollTo(0, 0);

        const handleResize = () => {
            setIsMobile(window.innerWidth <= 1024);
        };

        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    const categories: FAQCategory[] = [
        {
            id: 'general',
            name: 'General',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M12 21a9.004 9.004 0 008.716-6.747M12 21a9.004 9.004 0 01-8.716-6.747M12 21c2.485 0 4.5-4.03 4.5-9S14.485 3 12 3m0 18c-2.485 0-4.5-4.03-4.5-9S9.515 3 12 3m0 0a8.997 8.997 0 017.843 4.582M12 3a8.997 8.997 0 00-7.843 4.582m15.686 0A11.953 11.953 0 0112 10.5c-2.998 0-5.74-1.1-7.843-2.918m15.686 0A8.959 8.959 0 0121 12c0 .778-.099 1.533-.284 2.253m0 0A17.919 17.919 0 0112 16.5c-3.162 0-6.133-.815-8.716-2.247m0 0A9.015 9.015 0 013 12c0-1.605.42-3.113 1.157-4.418" />
                </svg>
            ),
            faqs: [
                {
                    question: '¿Cómo puedo guardar las comparaciones?',
                    answer: 'Al iniciar sesión, el historial de tus comparaciones se guarda automáticamente. Ahí podrás revisar y guardar tus comparaciones favoritas.'
                },
                {
                    question: '¿Qué criterios se usan para comparar las aeronaves?',
                    answer: 'Comparamos aspectos técnicos como capacidad, alcance, eficiencia de combustible, tecnología a bordo y costes operativos.'
                },
                {
                    question: '¿Puedo comparar modelos de diferentes fabricantes en una misma búsqueda?',
                    answer: 'Sí, nuestra plataforma permite comparar modelos de Airbus y Boeing juntos para facilitar la elección más adecuada.'
                },
                {
                    question: '¿Con qué frecuencia se actualizan las comparaciones y datos?',
                    answer: 'Actualizamos la información regularmente con las últimas novedades del sector para asegurarte datos precisos y actuales.'
                },
                {
                    question: '¿La plataforma es gratuita?',
                    answer: 'Sí, todas las funcionalidades básicas de comparación son completamente gratuitas. Algunas características avanzadas pueden requerir registro.'
                }
            ]
        },
        {
            id: 'modelos',
            name: 'Modelos de aeronaves',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 12L3.269 3.126A59.768 59.768 0 0121.485 12 59.77 59.77 0 013.27 20.876L5.999 12zm0 0h7.5" />
                </svg>
            ),
            faqs: [
                {
                    question: '¿Cuántos modelos de aeronaves puedo comparar?',
                    answer: 'Puedes comparar hasta 3 modelos simultáneamente para obtener una visión completa de las diferencias técnicas y operativas.'
                },
                {
                    question: '¿Los datos técnicos son oficiales?',
                    answer: 'Sí, todos los datos provienen de fuentes oficiales de los fabricantes y organismos aeronáuticos certificados.'
                },
                {
                    question: '¿Puedo exportar las comparaciones?',
                    answer: 'Actualmente estamos desarrollando esta función. Próximamente podrás exportar comparaciones en formato PDF.'
                },
                {
                    question: '¿Qué información técnica incluye cada modelo?',
                    answer: 'Incluimos capacidad de pasajeros, alcance, velocidad, dimensiones, motores, consumo de combustible y mucho más.'
                },
                {
                    question: '¿Puedo sugerir nuevos modelos?',
                    answer: 'Sí, valoramos tus sugerencias. Contáctanos a través del formulario de contacto con el modelo que te gustaría ver.'
                }
            ]
        },
        {
            id: 'familias',
            name: 'Familias',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M6.429 9.75L2.25 12l4.179 2.25m0-4.5l5.571 3 5.571-3m-11.142 0L2.25 7.5 12 2.25l9.75 5.25-4.179 2.25m0 0L21.75 12l-4.179 2.25m0 0l4.179 2.25L12 21.75 2.25 16.5l4.179-2.25m11.142 0l-5.571 3-5.571-3" />
                </svg>
            ),
            faqs: [
                {
                    question: '¿Qué ventajas ofrece comparar familias de aeronaves?',
                    answer: 'Comparar familias permite identificar las diferencias entre las distintas variantes de un mismo modelo, ayudándote a elegir la opción más adecuada según tamaño, capacidad y uso.'
                },
                {
                    question: '¿Qué es una familia de aeronaves?',
                    answer: 'Una familia agrupa variantes del mismo modelo base con diferentes capacidades, alcances y configuraciones adaptadas a distintas necesidades operativas.'
                },
                {
                    question: '¿Cómo selecciono una familia específica?',
                    answer: 'En la página principal, utiliza el filtro de "Familias" o busca directamente el nombre de la familia (ejemplo: A320 Family, 737 MAX).'
                },
                {
                    question: '¿Las familias incluyen versiones cargo?',
                    answer: 'Sí, incluimos todas las variantes disponibles: pasajeros, cargo, ejecutivas y especiales cuando estén disponibles.'
                },
                {
                    question: '¿Puedo ver la evolución histórica de una familia?',
                    answer: 'Sí, cada familia incluye una línea de tiempo con las mejoras y cambios implementados en cada generación.'
                }
            ]
        },
        {
            id: 'airbus',
            name: 'Airbus',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 13.5l10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75z" />
                </svg>
            ),
            faqs: [
                {
                    question: '¿Qué modelos de Airbus están disponibles?',
                    answer: 'Contamos con toda la gama de Airbus: desde el A220 hasta el A380, incluyendo las familias A320neo, A330neo y A350 XWB.'
                },
                {
                    question: '¿Incluyen tecnología Fly-by-Wire?',
                    answer: 'Sí, todos los datos técnicos incluyen información sobre los sistemas de control fly-by-wire característicos de Airbus.'
                },
                {
                    question: '¿Qué diferencia los modelos "neo" de los anteriores?',
                    answer: 'Los modelos "neo" (New Engine Option) incorporan motores más eficientes, winglets mejorados y tecnología de cabina avanzada.'
                },
                {
                    question: '¿Están incluidos los modelos ACJ (Airbus Corporate Jets)?',
                    answer: 'Sí, incluimos las versiones ejecutivas cuando existen diferencias técnicas significativas con las versiones comerciales.'
                },
                {
                    question: '¿Cuál es el modelo Airbus más popular?',
                    answer: 'El A320 es el modelo más vendido de Airbus, con más de 16,000 unidades pedidas. Su versatilidad lo hace ideal para rutas cortas y medias.'
                }
            ]
        },
        {
            id: 'boeing',
            name: 'Boeing',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M15.59 14.37a6 6 0 01-5.84 7.38v-4.8m5.84-2.58a14.98 14.98 0 006.16-12.12A14.98 14.98 0 009.631 8.41m5.96 5.96a14.926 14.926 0 01-5.841 2.58m-.119-8.54a6 6 0 00-7.381 5.84h4.8m2.581-5.84a14.927 14.927 0 00-2.58 5.84m2.699 2.7c-.103.021-.207.041-.311.06a15.09 15.09 0 01-2.448-2.448 14.9 14.9 0 01.06-.312m-2.24 2.39a4.493 4.493 0 00-1.757 4.306 4.493 4.493 0 004.306-1.758M16.5 9a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                </svg>
            ),
            faqs: [
                {
                    question: '¿Qué modelos de Boeing puedo consultar?',
                    answer: 'Tenemos desde el 737 hasta el 787 Dreamliner, incluyendo las series 747, 757, 767, 777 y las variantes MAX.'
                },
                {
                    question: '¿Incluyen los modelos 737 MAX después de las actualizaciones?',
                    answer: 'Sí, todos los datos del 737 MAX reflejan las especificaciones actualizadas tras las certificaciones de seguridad.'
                },
                {
                    question: '¿Qué hace especial al 787 Dreamliner?',
                    answer: 'El 787 utiliza materiales compuestos avanzados (50% de su estructura), ventanas más grandes y sistemas de presurización mejorados para mayor comodidad.'
                },
                {
                    question: '¿Por qué se descontinuó el 747?',
                    answer: 'El mercado evolucionó hacia aviones bimotores más eficientes. Boeing entregó el último 747-8 en 2023 tras 54 años de producción.'
                },
                {
                    question: '¿Cuál es la diferencia entre 777 y 777X?',
                    answer: 'El 777X incorpora alas plegables, motores GE9X más potentes, mayor eficiencia de combustible y tecnologías del 787 Dreamliner.'
                }
            ]
        }
    ];

    const selectedCategoryData = categories.find(cat => cat.id === selectedCategory);

    const toggleFAQ = (index: number) => {
        setExpandedFAQ(expandedFAQ === index ? null : index);
    };

    const SidebarComponent = () => (
        <aside className={styles.sidebar}>
            <div className={styles.sidebarCard}>
                <h2 className={styles.sidebarTitle}>Categorías de preguntas</h2>
                <nav className={styles.categoryNav}>
                    {categories.map((category) => (
                        <button
                            key={category.id}
                            className={`${styles.categoryButton} ${selectedCategory === category.id ? styles.active : ''}`}
                            onClick={() => {
                                setSelectedCategory(category.id);
                                setExpandedFAQ(null);
                            }}
                        >
                            <span className={styles.categoryIcon}>{category.icon}</span>
                            <span className={styles.categoryName}>{category.name}</span>
                        </button>
                    ))}
                </nav>
            </div>
        </aside>
    );

    const IntroCard = () => (
        <div className={styles.introCard}>
            <h2 className={styles.introTitle}>Bienvenido a nuestra sección de Preguntas Frecuentes</h2>
            <p className={styles.introText}>
                Diseñada para resolver tus dudas más comunes sobre la comparación de aeronaves. Aquí encontrarás respuestas claras y organizadas por categorías que facilitan la navegación, desde temas generales hasta detalles específicos de modelos, familias, Airbus y Boeing. Utiliza esta herramienta para obtener información rápida y confiable que te ayudará a aprovechar al máximo las funcionalidades de nuestra plataforma y mejorar tu experiencia en la elección de aeronaves.
            </p>
        </div>
    );

    const FAQSection = () => (
        <div className={styles.faqSection}>
            <h3 className={styles.categoryTitle}>
                <span className={styles.categoryTitleIcon}>{selectedCategoryData?.icon}</span>
                {selectedCategoryData?.name}
            </h3>
            <div className={styles.faqList}>
                {selectedCategoryData?.faqs.map((faq, index) => (
                    <div
                        key={index}
                        className={`${styles.faqItem} ${expandedFAQ === index ? styles.expanded : ''}`}
                    >
                        <button
                            className={styles.faqQuestion}
                            onClick={() => toggleFAQ(index)}
                        >
                            <span className={styles.questionText}>{faq.question}</span>
                            <svg className={styles.expandIcon} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                {expandedFAQ === index ? (
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M5 15l7-7 7 7" />
                                ) : (
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M19 9l-7 7-7-7" />
                                )}
                            </svg>
                        </button>
                        <div className={styles.faqAnswer}>
                            <p>{faq.answer}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );

    const HelpCard = () => (
        <div className={styles.helpCard}>
            <div className={styles.helpIcon}>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M8.625 12a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H8.25m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H12m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 01-2.555-.337A5.972 5.972 0 015.41 20.97a5.969 5.969 0 01-.474-.065 4.48 4.48 0 00.978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25z" />
                </svg>
            </div>
            <h3 className={styles.helpTitle}>¿No encontraste lo que buscabas?</h3>
            <p className={styles.helpText}>
                Nuestro equipo está aquí para ayudarte. Contáctanos y resolveremos tus dudas.
            </p>
            <button
                onClick={() => navigate('/contacto')}
                className={styles.contactButton}
            >
                Ir a Contacto
            </button>
        </div>
    );

    return (
        <div className={styles.faqPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al Inicio" />
                <CrystalSlider position="top-right" />
            </div>

            <div className={styles.container}>
                <header className={styles.header}>
                    <div className={styles.headerIcon}>
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9 5.25h.008v.008H12v-.008z" />
                        </svg>
                    </div>
                    <h1 className={styles.title}>Preguntas Frecuentes</h1>
                    <p className={styles.subtitle}>Encuentra respuestas rápidas a tus dudas más comunes</p>
                </header>

                {isMobile ? (
                    /* MOBILE: Orden específico */
                    <div className={styles.content}>
                        <IntroCard />
                        <SidebarComponent />
                        <FAQSection />
                        <HelpCard />
                    </div>
                ) : (
                    /* DESKTOP: Grid normal */
                    <div className={styles.content}>
                        <SidebarComponent />
                        <main className={styles.mainContent}>
                            <IntroCard />
                            <FAQSection />
                            <HelpCard />
                        </main>
                    </div>
                )}
            </div>

            <Footer />
        </div>
    );
};

export default FAQ;
