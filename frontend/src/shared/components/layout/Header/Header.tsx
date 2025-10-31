import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import ThemeToggle from '../../ui/ThemeToggle';
import './Header.css';

const Header: React.FC = () => {
    const [isScrolled, setIsScrolled] = useState(false);
    const headerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const handleScroll = () => {
            setIsScrolled(window.scrollY > 50);
        };

        window.addEventListener('scroll', handleScroll, { passive: true });
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    return (
        <>
            <header
                className={`header ${isScrolled ? 'header--scrolled' : ''}`}
                ref={headerRef}
            >
                <div className="header__container">
                    {/* Logo */}
                    <Link to="/" className="header__logo">
                        <img
                            src="/assets/icons/SkyVaultLogoPLB.svg"
                            alt="SkyVault"
                            className="header__logo-icon"
                        />
                        <span className="header__logo-text">SkyVault</span>
                    </Link>

                    {/* Navigation */}
                    <nav className="header__nav">
                        <Link to="/news" className="header__link">
                            Noticias
                        </Link>
                        <Link to="/compare" className="header__link">
                            Comparar
                        </Link>

                        {/* Theme Toggle reemplaza Iniciar Sesión */}
                        <div className="header__theme-toggle">
                            <ThemeToggle />
                        </div>
                    </nav>
                </div>

                {/* Liquid Glass Background */}
                <div className="header__glass" />
            </header>

            {/* SVG Filters para Liquid Glass */}
            <svg style={{ position: 'absolute', width: 0, height: 0 }}>
                <defs>
                    <filter id="headerGlass" x="-50%" y="-50%" width="200%" height="200%">
                        <feImage
                            xlinkHref="https://essykings.github.io/JavaScript/map.png"
                            x="-50%"
                            y="-50%"
                            width="200%"
                            height="200%"
                            result="map"
                        />
                        <feGaussianBlur in="SourceGraphic" stdDeviation="0.02" result="blur" />
                        <feDisplacementMap
                            in="blur"
                            in2="map"
                            scale="10"
                            xChannelSelector="R"
                            yChannelSelector="G"
                            result="displacement"
                        />
                        <feGaussianBlur in="displacement" stdDeviation="0.5" result="blur2" />
                    </filter>
                </defs>
            </svg>
        </>
    );
};

export default Header;
