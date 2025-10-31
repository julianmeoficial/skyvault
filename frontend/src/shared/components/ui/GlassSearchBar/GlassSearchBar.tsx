import React, { useState, useRef, useEffect } from 'react';
import styles from './GlassSearchBar.module.css';

interface FilterOption {
    label: string;
    value: string;
}

interface GlassSearchBarProps {
    value: string;
    onSearch: (value: string) => void;
    placeholder?: string;
    manufacturerValue?: string;
    onManufacturerChange: (value: string) => void;
    manufacturers: { id: number; name: string }[];
    sortValue: string;
    onSortChange: (value: string) => void;
    sortOptions: FilterOption[];
}

export const GlassSearchBar: React.FC<GlassSearchBarProps> = ({
                                                                  value,
                                                                  onSearch,
                                                                  placeholder = 'Buscar aeronave...',
                                                                  manufacturerValue = '',
                                                                  onManufacturerChange,
                                                                  manufacturers,
                                                                  sortValue,
                                                                  onSortChange,
                                                                  sortOptions,
                                                              }) => {
    const [isFilterOpen, setIsFilterOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null);

    // Cerrar dropdown al hacer click fuera
    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setIsFilterOpen(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    return (
        <div className={styles.container}>
            <div className={styles.searchWrapper}>
                {/* Efectos de Liquid Glass */}
                <div className={styles.glow} />
                <div className={styles.darkBorderBg} />
                <div className={styles.darkBorderBg} />
                <div className={styles.darkBorderBg} />
                <div className={styles.white} />
                <div className={styles.border} />

                {/* Input principal */}
                <div className={styles.mainInput}>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder={placeholder}
                        value={value}
                        onChange={(e) => onSearch(e.target.value)}
                        aria-label="Buscar aeronave"
                    />

                    {/* Máscara de efecto */}
                    <div className={styles.pinkMask} />

                    {/* Borde animado del botón filtro */}
                    <div className={styles.filterBorder} />

                    {/* Botón de filtros */}
                    <button
                        className={styles.filterIcon}
                        onClick={() => setIsFilterOpen(!isFilterOpen)}
                        aria-label="Abrir filtros"
                        aria-expanded={isFilterOpen}
                    >
                        <svg
                            fill="none"
                            viewBox="4.8 4.56 14.832 15.408"
                            width={24}
                            height={24}
                            preserveAspectRatio="none"
                        >
                            <path
                                strokeLinejoin="round"
                                strokeLinecap="round"
                                strokeMiterlimit={10}
                                strokeWidth={1.5}
                                stroke="currentColor"
                                d="M8.16 6.65002H15.83C16.47 6.65002 16.99 7.17002 16.99 7.81002V9.09002C16.99 9.56002 16.7 10.14 16.41 10.43L13.91 12.64C13.56 12.93 13.33 13.51 13.33 13.98V16.48C13.33 16.83 13.1 17.29 12.81 17.47L12 17.98C11.24 18.45 10.2 17.92 10.2 16.99V13.91C10.2 13.5 9.97 12.98 9.73 12.69L7.52 10.36C7.23 10.08 7 9.55002 7 9.20002V7.87002C7 7.17002 7.52 6.65002 8.16 6.65002Z"
                            />
                        </svg>
                    </button>

                    {/* Icono de búsqueda */}
                    <div className={styles.searchIcon}>
                        <svg
                            fill="none"
                            height={22}
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            viewBox="0 0 24 24"
                            width={22}
                            xmlns="http://www.w3.org/2000/svg"
                        >
                            <circle cx={11} cy={11} r={8} stroke="url(#search)" />
                            <line x1={22} x2="16.65" y1={22} y2="16.65" stroke="url(#searchl)" />
                            <defs>
                                <linearGradient id="search" gradientTransform="rotate(50)">
                                    <stop offset="0%" stopColor="#B2E5FF" />
                                    <stop offset="50%" stopColor="#D3E9FF" />
                                    <stop offset="100%" stopColor="#0D4B7A" />
                                </linearGradient>
                                <linearGradient id="searchl">
                                    <stop offset="0%" stopColor="#D3E9FF" />
                                    <stop offset="50%" stopColor="#0D4B7A" />
                                </linearGradient>
                            </defs>
                        </svg>
                    </div>
                </div>

                {/* Dropdown de filtros */}
                {isFilterOpen && (
                    <div className={styles.filterDropdown} ref={dropdownRef}>
                        <div className={styles.filterSection}>
                            <label className={styles.filterLabel}>Fabricante</label>
                            <select
                                value={manufacturerValue}
                                onChange={(e) => onManufacturerChange(e.target.value)}
                                className={styles.filterSelect}
                            >
                                <option value="">Todos los fabricantes</option>
                                {manufacturers.map((m) => (
                                    <option key={m.id} value={m.id}>
                                        {m.name}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className={styles.filterSection}>
                            <label className={styles.filterLabel}>Ordenar por</label>
                            <select
                                value={sortValue}
                                onChange={(e) => onSortChange(e.target.value)}
                                className={styles.filterSelect}
                            >
                                {sortOptions.map((option) => (
                                    <option key={option.value} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};
