import { useTheme } from '../../../shared/contexts/ThemeContext';
import { API_THEMES } from '../utils/apiThemes';
import type { APITheme } from '../utils/apiThemes';

export const useAPITheme = (): APITheme => {
    const { theme } = useTheme();
    return API_THEMES[theme] || API_THEMES.dark;
};