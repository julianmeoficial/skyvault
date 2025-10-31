import React, { useState, useEffect } from 'react';
import { Loader } from '../../shared/components/ui/Loader';
import Header from '../../shared/components/layout/Header';
import Footer from '../../shared/components/layout/Footer';
import { initAllAnimations, cleanupAnimations } from './Home.animations';
import FlightRoutes from '../../shared/components/ui/FlightRoutes';
import './Home.css';

const Home: React.FC = () => {
    const [showLoader, setShowLoader] = useState(true);

    const handleLoaderComplete = () => {
        setShowLoader(false);
    };

    useEffect(() => {
        if (!showLoader) {
            initAllAnimations();
        }
        return () => {
            cleanupAnimations();
        };
    }, [showLoader]);

    return (
        <>
            {showLoader && <Loader onComplete={handleLoaderComplete} text="Cargando" />}

            {!showLoader && (
                <>
                    <Header />

                    <main className="home">
                        {/* Hero Section */}
                        <section className="home__hero">
                            <div className="home__hero-bg"></div>
                            <div className="home__hero-content">
                                <div className="home__logo">
                                    <img src="/assets/icons/SkyVaultLogoPLB.svg" alt="SkyVault" />
                                </div>
                                <h1 className="home__title">
                                    Compara 36 modelos<br />de aviación comercial
                                </h1>
                                <p className="home__subtitle">
                                    Airbus vs Boeing — Especificaciones, rendimiento y análisis detallado
                                </p>
                                <a href="/catalog" className="home__cta">Explorar Catálogo</a>
                            </div>
                            <div className="home__scroll-hint">Desliza para explorar</div>
                        </section>

                        {/* Intro Text */}
                        <section className="home__intro">
                            <div className="home__container">
                                <div className="home__intro-content">
                                    <h2 className="home__intro-title">
                                        La plataforma definitiva para comparar aeronaves comerciales
                                    </h2>
                                    <p className="home__intro-text">
                                        SkyVault te proporciona acceso instantáneo a especificaciones técnicas, datos de rendimiento y características de diseño de los modelos más importantes de Airbus y Boeing. Toma decisiones informadas con información precisa y actualizada.
                                    </p>
                                </div>
                            </div>

                            {/* Orbital Decoration */}
                            <div className="home__intro-decoration">
                                <div className="intro-orbital">
                                    <div className="intro-orbital-center"></div>
                                    <div className="intro-orbital-orbit">
                                        <div className="intro-orbital-dot"></div>
                                        <div className="intro-orbital-dot"></div>
                                        <div className="intro-orbital-dot"></div>
                                        <div className="intro-orbital-dot"></div>
                                    </div>
                                </div>
                            </div>
                        </section>

                        {/* Featured Models - 6 MODELOS */}
                        <section className="home__featured">
                            <div className="home__container">
                                <h2 className="home__featured-title">Modelos destacados</h2>

                                <div className="home__featured-grid">
                                    {/* Airbus A350-1000 */}
                                    <div className="home__model">
                                        <div className="home__model-header">
                                            <span className="home__model-manufacturer">Airbus</span>
                                            <span className="home__model-category">Wide-body</span>
                                        </div>
                                        <h3 className="home__model-name">A350-1000</h3>
                                        <p className="home__model-desc">
                                            El wide-body más avanzado de Airbus con materiales compuestos y eficiencia superior
                                        </p>
                                        <div className="home__model-specs">
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Pasajeros</span>
                                                <span className="home__model-spec-value">366</span>
                                            </div>
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Alcance</span>
                                                <span className="home__model-spec-value">16,100 km</span>
                                            </div>
                                        </div>
                                        <a href="/aircraft/a350-1000" className="home__model-link">Ver detalles →</a>
                                    </div>

                                    {/* Airbus A321XLR */}
                                    <div className="home__model">
                                        <div className="home__model-header">
                                            <span className="home__model-manufacturer">Airbus</span>
                                            <span className="home__model-category">Narrow-body</span>
                                        </div>
                                        <h3 className="home__model-name">A321XLR</h3>
                                        <p className="home__model-desc">
                                            Alcance extralargo en un narrow-body, revolucionando las rutas transatlánticas
                                        </p>
                                        <div className="home__model-specs">
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Pasajeros</span>
                                                <span className="home__model-spec-value">220</span>
                                            </div>
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Alcance</span>
                                                <span className="home__model-spec-value">8,700 km</span>
                                            </div>
                                        </div>
                                        <a href="/aircraft/a321xlr" className="home__model-link">Ver detalles →</a>
                                    </div>

                                    {/* Boeing 787-10 Dreamliner */}
                                    <div className="home__model">
                                        <div className="home__model-header">
                                            <span className="home__model-manufacturer">Boeing</span>
                                            <span className="home__model-category">Wide-body</span>
                                        </div>
                                        <h3 className="home__model-name">787-10 Dreamliner</h3>
                                        <p className="home__model-desc">
                                            Innovación en materiales compuestos y cabina presurizada para mayor confort
                                        </p>
                                        <div className="home__model-specs">
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Pasajeros</span>
                                                <span className="home__model-spec-value">330</span>
                                            </div>
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Alcance</span>
                                                <span className="home__model-spec-value">11,910 km</span>
                                            </div>
                                        </div>
                                        <a href="/aircraft/b787-10" className="home__model-link">Ver detalles →</a>
                                    </div>

                                    {/* Boeing 737 MAX 8 */}
                                    <div className="home__model">
                                        <div className="home__model-header">
                                            <span className="home__model-manufacturer">Boeing</span>
                                            <span className="home__model-category">Narrow-body</span>
                                        </div>
                                        <h3 className="home__model-name">737 MAX 8</h3>
                                        <p className="home__model-desc">
                                            Evolución del avión comercial más exitoso con motores LEAP-1B de última generación
                                        </p>
                                        <div className="home__model-specs">
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Pasajeros</span>
                                                <span className="home__model-spec-value">178</span>
                                            </div>
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Alcance</span>
                                                <span className="home__model-spec-value">6,510 km</span>
                                            </div>
                                        </div>
                                        <a href="/aircraft/b737-max-8" className="home__model-link">Ver detalles →</a>
                                    </div>

                                    {/* Airbus ACJ350 */}
                                    <div className="home__model">
                                        <div className="home__model-header">
                                            <span className="home__model-manufacturer">Airbus</span>
                                            <span className="home__model-category">Corporate Jet</span>
                                        </div>
                                        <h3 className="home__model-name">ACJ350</h3>
                                        <p className="home__model-desc">
                                            Versión ejecutiva del A350, el jet corporativo más espacioso y lujoso del mercado
                                        </p>
                                        <div className="home__model-specs">
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Pasajeros</span>
                                                <span className="home__model-spec-value">25</span>
                                            </div>
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Alcance</span>
                                                <span className="home__model-spec-value">18,000 km</span>
                                            </div>
                                        </div>
                                        <a href="/aircraft/acj350" className="home__model-link">Ver detalles →</a>
                                    </div>

                                    {/* Airbus A380 */}
                                    <div className="home__model">
                                        <div className="home__model-header">
                                            <span className="home__model-manufacturer">Airbus</span>
                                            <span className="home__model-category">Wide-body</span>
                                        </div>
                                        <h3 className="home__model-name">A380</h3>
                                        <p className="home__model-desc">
                                            El avión de pasajeros más grande del mundo con capacidad de dos pisos completos
                                        </p>
                                        <div className="home__model-specs">
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Pasajeros</span>
                                                <span className="home__model-spec-value">555</span>
                                            </div>
                                            <div className="home__model-spec">
                                                <span className="home__model-spec-label">Alcance</span>
                                                <span className="home__model-spec-value">15,200 km</span>
                                            </div>
                                        </div>
                                        <a href="/aircraft/a380" className="home__model-link">Ver detalles →</a>
                                    </div>
                                </div>

                                <div className="home__featured-cta">
                                    <a href="/catalog" className="home__button-minimal">
                                        Ver los 36 modelos completos →
                                    </a>
                                </div>
                            </div>
                        </section>

                        {/* Data Grid */}
                        <section className="home__data">
                            {/* Vignette overlay */}
                            <div className="home__data-overlay"></div>

                            <div className="home__container">
                                <h2 className="home__data-title">SkyVault en números</h2>
                                <div className="home__data-grid">
                                    <div className="home__data-item">
                                        <span className="home__data-number">36</span>
                                        <span className="home__data-label">Modelos disponibles</span>
                                    </div>
                                    <div className="home__data-item">
                                        <span className="home__data-number">2</span>
                                        <span className="home__data-label">Fabricantes principales</span>
                                    </div>
                                    <div className="home__data-item">
                                        <span className="home__data-number">12+</span>
                                        <span className="home__data-label">Familias de aeronaves</span>
                                    </div>
                                    <div className="home__data-item">
                                        <span className="home__data-number">50+</span>
                                        <span className="home__data-label">Especificaciones técnicas</span>
                                    </div>
                                </div>
                            </div>
                        </section>

                        {/* CTA Final con Flight Routes Background */}
                        <section className="home__cta-section">
                            {/* Flight Routes Background */}
                            <FlightRoutes />

                            <div className="home__container">
                                <h2 className="home__cta-title">¿Listo para comparar?</h2>
                                <p className="home__cta-text">
                                    Accede a especificaciones detalladas y compara hasta 3 modelos simultáneamente
                                </p>
                                <div className="home__cta-buttons">
                                    <a href="/catalog" className="home__button home__button--primary">Ver Catálogo Completo</a>
                                    <a href="/compare" className="home__button home__button--secondary">Iniciar Comparación</a>
                                </div>
                            </div>
                        </section>
                    </main>

                    <Footer />
                </>
            )}
        </>
    );
};

export default Home;
