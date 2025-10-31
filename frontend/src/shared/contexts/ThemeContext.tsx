import React, { createContext, useContext, useEffect, useState } from 'react';

export type ThemeMode = 'light' | 'dark' | 'minimal';

interface ThemeContextType {
    theme: ThemeMode;
    setTheme: (theme: ThemeMode) => void;
    toggleTheme: () => void;
}

const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

export const ThemeProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const getInitialTheme = (): ThemeMode => {
        const savedTheme = localStorage.getItem('skyvault-theme') as ThemeMode;
        if (savedTheme && ['light', 'dark', 'minimal'].includes(savedTheme)) {
            return savedTheme;
        }

        if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
            return 'dark';
        }

        if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
            return 'minimal';
        }

        return 'light';
    };

    const [theme, setThemeState] = useState<ThemeMode>(getInitialTheme);

    useEffect(() => {
        document.documentElement.setAttribute('data-theme', theme);
        localStorage.setItem('skyvault-theme', theme);
    }, [theme]);

    const setTheme = (newTheme: ThemeMode) => {
        setThemeState(newTheme);
    };

    const toggleTheme = () => {
        setThemeState(current => {
            const themes: ThemeMode[] = ['light', 'dark', 'minimal'];
            const currentIndex = themes.indexOf(current);
            const nextIndex = (currentIndex + 1) % themes.length;
            return themes[nextIndex];
        });
    };

    return (
        <ThemeContext.Provider value={{ theme, setTheme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};

export const useTheme = () => {
    const context = useContext(ThemeContext);
    if (context === undefined) {
        throw new Error('useTheme debe ser usado dentro de un ThemeProvider');
    }
    return context;
};
