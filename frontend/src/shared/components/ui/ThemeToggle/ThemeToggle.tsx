import React from 'react';
import { useTheme } from '../../../contexts/ThemeContext';
import styles from './ThemeToggle.module.css';

const ThemeToggle: React.FC = () => {
    const { theme, setTheme } = useTheme();

    const handleDayNightToggle = () => {
        setTheme(theme === 'light' ? 'dark' : 'light');
    };

    const handleMinimalToggle = () => {
        setTheme(theme === 'minimal' ? 'light' : 'minimal');
    };

    const isDayNightDark = theme === 'dark';
    const isMinimal = theme === 'minimal';

    return (
        <div className={styles.themeToggleContainer}>
            {/* Day/Night Slider Switch */}
            <div className={styles.dayNightSwitch}>
                <input
                    type="checkbox"
                    id="dayNightToggle"
                    className={styles.switchCheckbox}
                    checked={isDayNightDark}
                    onChange={handleDayNightToggle}
                    aria-label="Alternar entre modo día y noche"
                />
                <label htmlFor="dayNightToggle" className={styles.switchLabel}>
                    <div className={styles.slider}>
                        {/* Sun/Moon Circle with rotation effect */}
                        <div className={styles.sunMoon}>
                            {/* Moon dots (visible in dark mode) */}
                            <svg className={`${styles.moonDot} ${styles.moonDot1}`} viewBox="0 0 100 100">
                                <circle cx="50" cy="50" r="50" />
                            </svg>
                            <svg className={`${styles.moonDot} ${styles.moonDot2}`} viewBox="0 0 100 100">
                                <circle cx="50" cy="50" r="50" />
                            </svg>
                            <svg className={`${styles.moonDot} ${styles.moonDot3}`} viewBox="0 0 100 100">
                                <circle cx="50" cy="50" r="50" />
                            </svg>

                            {/* Sun rays (visible in light mode) */}
                            <svg className={`${styles.lightRay} ${styles.lightRay1}`} viewBox="0 0 100 100">
                                <circle cx="50" cy="50" r="50" />
                            </svg>
                            <svg className={`${styles.lightRay} ${styles.lightRay2}`} viewBox="0 0 100 100">
                                <circle cx="50" cy="50" r="50" />
                            </svg>
                            <svg className={`${styles.lightRay} ${styles.lightRay3}`} viewBox="0 0 100 100">
                                <circle cx="50" cy="50" r="50" />
                            </svg>
                        </div>

                        {/* Stars (visible in dark mode) */}
                        <div className={styles.stars}>
                            <svg className={`${styles.star} ${styles.star1}`} viewBox="0 0 20 20">
                                <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z" />
                            </svg>
                            <svg className={`${styles.star} ${styles.star2}`} viewBox="0 0 20 20">
                                <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z" />
                            </svg>
                            <svg className={`${styles.star} ${styles.star3}`} viewBox="0 0 20 20">
                                <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z" />
                            </svg>
                            <svg className={`${styles.star} ${styles.star4}`} viewBox="0 0 20 20">
                                <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z" />
                            </svg>
                        </div>
                    </div>
                </label>
            </div>

            {/* Minimal Mode Button */}
            <div className={styles.minimalSwitch}>
                <input
                    type="checkbox"
                    id="minimalToggle"
                    className={styles.minimalCheckbox}
                    checked={isMinimal}
                    onChange={handleMinimalToggle}
                    aria-label="Alternar modo minimalista"
                />
                <label htmlFor="minimalToggle" className={styles.minimalLabel}>
                    <div className={styles.toggle}>
                        <div className={styles.led} />
                    </div>
                </label>
            </div>
        </div>
    );
};

export default ThemeToggle;
