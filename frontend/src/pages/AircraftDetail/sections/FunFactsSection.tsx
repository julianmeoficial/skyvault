import { useRef } from 'react';
import type { AircraftDetailDto } from '../../../features/aircraft/types/aircraft.types';
import { funFactsData } from '../../../data/funFactsData';
import styles from './FunFactsSection.module.css';

// Importar los iconos
import fuelSavingIcon from '/assets/icons/funfacts/fuelsaving.png';
import airQualityIcon from '/assets/icons/funfacts/airquality.png';
import componentsIcon from '/assets/icons/funfacts/components.png';
import operatorsIcon from '/assets/icons/funfacts/operators.png';
import funFactsIcon from '/assets/icons/funfacts/funfacts.png';

interface FunFactsSectionProps {
    aircraft: AircraftDetailDto;
}

/**
 * Sección de datos curiosos de la aeronave.
 * Sistema de fallback inteligente para encontrar datos incluso con variaciones de nomenclatura.
 */
export function FunFactsSection({ aircraft }: FunFactsSectionProps) {
    const sectionRef = useRef<HTMLElement>(null);

    /**
     * ✅ BÚSQUEDA INTELIGENTE CON FALLBACKS
     * Intenta múltiples variantes del slug hasta encontrar una coincidencia
     */
    const findFunFacts = (model: string | undefined) => {
        if (!model) return null;

        // 1. Normalización base: lowercase y espacios → guiones
        const baseSlug = model.toLowerCase()
            .replace(/\s+/g, '-')
            .replace(/[^a-z0-9-]/g, '');

        // 2. Generar variantes del slug (orden de prioridad)
        const slugVariants = [
            // Variante 1: Sin prefijos ni sufijos (más limpia)
            baseSlug
                .replace(/^(boeing-|airbus-)/gi, '')
                .replace(/-(dreamliner|neo|ceo)$/gi, ''),

            // Variante 2: Sin prefijos, pero con sufijos
            baseSlug.replace(/^(boeing-|airbus-)/gi, ''),

            // Variante 3: Slug completo original
            baseSlug,

            // Variante 4: Sin sufijos pegados (neo, ceo) - para casos como "a330-900neo"
            baseSlug
                .replace(/^(boeing-|airbus-)/gi, '')
                .replace(/(neo|ceo|dreamliner)$/gi, ''),

            // Variante 5: Solo números y guiones (para casos edge)
            baseSlug.replace(/[^0-9-]/g, '')
        ];

        // 3. Buscar en funFactsData con cada variante
        for (const variant of slugVariants) {
            if (variant && funFactsData[variant]) {
                console.log(`[FunFacts] ✅ Match found! Model: "${model}" → Slug: "${variant}"`);
                return { slug: variant, data: funFactsData[variant] };
            }
        }

        // 4. Si ninguna variante funciona, log de debug
        console.warn(
            `[FunFacts] ❌ No data found for model: "${model}"\n` +
            `Tried variants: ${slugVariants.filter(Boolean).join(', ')}\n` +
            `Available keys: ${Object.keys(funFactsData).slice(0, 10).join(', ')}...`
        );
        return null;
    };

    const result = findFunFacts(aircraft?.model);

    // ✅ Si no hay datos, NO renderiza la sección
    if (!result) {
        return null;
    }

    const facts = result.data;

    return (
        <section ref={sectionRef} className={styles.section}>
            <div className={styles.container}>
                {/* Título principal */}
                <div className={styles.header}>
                    <h2 className={styles.title}>Datos Curiosos</h2>
                    <p className={styles.subtitle}>
                        Descubre las características únicas del {aircraft.displayName || aircraft.name}
                    </p>
                </div>

                {/* Grid de cards */}
                <div className={styles.grid}>
                    {/* Card 1: Ahorro de Combustible */}
                    <div className={styles.card}>
                        <div className={styles.iconCircle}>
                            <img src={fuelSavingIcon} alt="Ahorro de Combustible" className={styles.icon} />
                        </div>
                        <h3 className={styles.cardTitle}>Ahorro de Combustible</h3>
                        <p className={styles.cardDescription}>{facts.fuelSaving}</p>
                    </div>

                    {/* Card 2: Calidad de Aire */}
                    <div className={styles.card}>
                        <div className={styles.iconCircle}>
                            <img src={airQualityIcon} alt="Calidad de Aire" className={styles.icon} />
                        </div>
                        <h3 className={styles.cardTitle}>Calidad de Aire</h3>
                        <p className={styles.cardDescription}>{facts.airQuality}</p>
                    </div>

                    {/* Card 3: Componentes */}
                    <div className={styles.card}>
                        <div className={styles.iconCircle}>
                            <img src={componentsIcon} alt="Componentes" className={styles.icon} />
                        </div>
                        <h3 className={styles.cardTitle}>Componentes</h3>
                        <p className={styles.cardDescription}>{facts.components}</p>
                    </div>

                    {/* Card 4: Aerolíneas Operadoras */}
                    <div className={styles.card}>
                        <div className={styles.iconCircle}>
                            <img src={operatorsIcon} alt="Aerolíneas" className={styles.icon} />
                        </div>
                        <h3 className={styles.cardTitle}>Aerolíneas que lo Operan</h3>
                        <div className={styles.operatorsList}>
                            {facts.operators.map((operator, index) => (
                                <span key={index} className={styles.operatorTag}>
                                    {operator}
                                </span>
                            ))}
                        </div>
                    </div>

                    {/* Card 5: Curiosidades (2 columnas en desktop) */}
                    <div className={`${styles.card} ${styles.cardWide}`}>
                        <div className={styles.iconCircle}>
                            <img src={funFactsIcon} alt="Curiosidades" className={styles.icon} />
                        </div>
                        <h3 className={styles.cardTitle}>Curiosidades</h3>
                        <p className={styles.cardDescription}>{facts.curiosities}</p>
                    </div>
                </div>
            </div>
        </section>
    );
}
