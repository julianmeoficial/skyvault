import { useNavigate } from 'react-router-dom';
import styles from './BackButton.module.css';

interface BackButtonProps {
    to?: string; // Ruta específica (opcional)
    label?: string; // Texto del botón (opcional)
}

/**
 * Botón circular de regreso reutilizable
 * Si no se proporciona 'to', usa navigate(-1) para volver atrás
 */
export function BackButton({ to, label = 'Volver' }: BackButtonProps) {
    const navigate = useNavigate();

    const handleClick = () => {
        if (to) {
            navigate(to);
        } else {
            navigate(-1); // Volver a la página anterior
        }
    };

    return (
        <button className={styles.backButton} onClick={handleClick} aria-label={label}>
            <svg
                height={20}
                width={20}
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 1024 1024"
                className={styles.arrow}
            >
                <path d="M874.690416 495.52477c0 11.2973-9.168824 20.466124-20.466124 20.466124l-604.773963 0 188.083679 188.083679c7.992021 7.992021 7.992021 20.947078 0 28.939099-4.001127 3.990894-9.240455 5.996574-14.46955 5.996574-5.239328 0-10.478655-1.995447-14.479783-5.996574l-223.00912-223.00912c-3.837398-3.837398-5.996574-9.046027-5.996574-14.46955 0-5.433756 2.159176-10.632151 5.996574-14.46955l223.019353-223.029586c7.992021-7.992021 20.957311-7.992021 28.949332 0 7.992021 8.002254 7.992021 20.957311 0 28.949332l-188.073446 188.073446 604.753497 0C865.521592 475.058646 874.690416 484.217237 874.690416 495.52477z" />
            </svg>
            <span className={styles.label}>{label}</span>
        </button>
    );
}
