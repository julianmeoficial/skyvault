export interface APITheme {
    name: 'light' | 'dark' | 'minimal';
    background: {
        gradient: string;
        stars: string;
        nebula: string;
    };
    colors: {
        primary: string;
        secondary: string;
        text: string;
        textSecondary: string;
        border: string;
        cardBg: string;
        accent: string;
    };
}

export const API_THEMES: Record<string, APITheme> = {
    light: {
        name: 'light',
        background: {
            gradient: 'linear-gradient(180deg, #e8f4ff 0%, #d0e8ff 50%, #b8dcff 100%)',
            stars: '#1e40af',
            nebula: 'rgba(59, 130, 246, 0.15)'
        },
        colors: {
            primary: '#0d4b7a',
            secondary: '#4a9fd8',
            text: '#0d4b7a',
            textSecondary: 'rgba(13, 75, 122, 0.8)',
            border: 'rgba(13, 75, 122, 0.3)',
            cardBg: 'rgba(255, 255, 255, 0.7)',
            accent: '#4a9fd8'
        }
    },
    dark: {
        name: 'dark',
        background: {
            gradient: 'linear-gradient(180deg, #000000 0%, #0a0a1a 50%, #050510 100%)',
            stars: '#ffffff',
            nebula: 'rgba(139, 92, 246, 0.2)'
        },
        colors: {
            primary: '#ffffff',
            secondary: '#a78bfa',
            text: '#ffffff',
            textSecondary: 'rgba(255, 255, 255, 0.7)',
            border: 'rgba(139, 92, 246, 0.3)',
            cardBg: 'rgba(255, 255, 255, 0.05)',
            accent: '#8b5cf6'
        }
    },
    minimal: {
        name: 'minimal',
        background: {
            gradient: 'linear-gradient(180deg, #f0f0f5 0%, #e5e5ea 50%, #d8d8dd 100%)',
            stars: '#5a6c7d',
            nebula: 'rgba(123, 140, 158, 0.12)'
        },
        colors: {
            primary: '#2c3e50',
            secondary: '#5a6c7d',
            text: '#2c3e50',
            textSecondary: '#5a6c7d',
            border: 'rgba(90, 108, 125, 0.3)',
            cardBg: 'rgba(255, 255, 255, 0.8)',
            accent: '#7b8c9e'
        }
    }
};
