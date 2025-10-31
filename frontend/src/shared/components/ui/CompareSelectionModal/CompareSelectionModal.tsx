import { useEffect, useRef, useState, useCallback } from 'react';
import { gsap } from 'gsap';
import { aircraftService } from '../../../../features/aircraft/services/aircraftService';
import { LiquidGlassButton } from '../LiquidGlassButton';
import type { AircraftCardDto } from '../../../../features/aircraft/types/aircraft.types';
import styles from './CompareSelectionModal.module.css';

interface CompareSelectionModalProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (selectedAircraftId: number) => void;
    excludeAircraftId?: number;
}

export const CompareSelectionModal: React.FC<CompareSelectionModalProps> = ({
                                                                                isOpen,
                                                                                onClose,
                                                                                onConfirm,
                                                                                excludeAircraftId
                                                                            }) => {
    const [aircraft, setAircraft] = useState<AircraftCardDto[]>([]);
    const [filteredAircraft, setFilteredAircraft] = useState<AircraftCardDto[]>([]);
    const [selectedAircraft, setSelectedAircraft] = useState<number | null>(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const backdropRef = useRef<HTMLDivElement>(null);
    const modalRef = useRef<HTMLDivElement>(null);

    // ========== CARGAR AERONAVES ==========
    useEffect(() => {
        if (isOpen && aircraft.length === 0) {
            loadAircraft();
        }
    }, [isOpen]);

    const loadAircraft = async () => {
        setIsLoading(true);
        try {
            const response = await aircraftService.getAircraft(
                {},
                { field: 'name', direction: 'asc' },
                0,
                100
            );

            const filtered = response.content.filter((a: AircraftCardDto) => a.id !== excludeAircraftId);
            setAircraft(filtered);
            setFilteredAircraft(filtered);
        } catch (error) {
            console.error('Error loading aircraft:', error);
        } finally {
            setIsLoading(false);
        }
    };

    // ========== FILTRAR POR BÚSQUEDA ==========
    useEffect(() => {
        if (searchTerm.trim() === '') {
            setFilteredAircraft(aircraft);
        } else {
            const filtered = aircraft.filter((a: AircraftCardDto) =>
                a.displayName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                a.model.toLowerCase().includes(searchTerm.toLowerCase())
            );
            setFilteredAircraft(filtered);
        }
    }, [searchTerm, aircraft]);

    // ========== ANIMACIÓN DE ENTRADA/SALIDA (REACT 18 COMPATIBLE) ==========
    useEffect(() => {
        if (!backdropRef.current || !modalRef.current) return;

        const backdrop = backdropRef.current;
        const modal = modalRef.current;

        if (isOpen) {
            backdrop.style.display = 'flex';
            modal.style.display = 'flex';

            const tl = gsap.timeline();
            tl.fromTo(backdrop,
                { opacity: 0 },
                { opacity: 1, duration: 0.3, ease: 'power2.out' }
            )
                .fromTo(modal,
                    { opacity: 0, y: -30, scale: 0.95 },
                    { opacity: 1, y: 0, scale: 1, duration: 0.4, ease: 'back.out(1.5)' },
                    '-=0.2'
                );
        } else if (backdrop.style.display !== 'none') {
            const tl = gsap.timeline({
                onComplete: () => {
                    if (backdrop && modal) {
                        backdrop.style.display = 'none';
                        modal.style.display = 'none';
                    }
                }
            });
            tl.to(modal, {
                opacity: 0,
                y: -20,
                scale: 0.95,
                duration: 0.3,
                ease: 'power2.in'
            })
                .to(backdrop, {
                    opacity: 0,
                    duration: 0.2,
                    ease: 'power2.in'
                }, '-=0.1');
        }

        return () => {
            gsap.killTweensOf([backdrop, modal]);
        };
    }, [isOpen]);

    // ========== HANDLERS ==========
    const handleBackdropClick = useCallback(() => {
        onClose();
    }, [onClose]);

    const handleModalClick = useCallback((e: React.MouseEvent) => {
        e.stopPropagation();
    }, []);

    const handleConfirm = useCallback(() => {
        if (selectedAircraft) {
            onConfirm(selectedAircraft);
            setSelectedAircraft(null);
            setSearchTerm('');
        }
    }, [selectedAircraft, onConfirm]);

    const handleKeyDown = useCallback((e: KeyboardEvent) => {
        if (e.key === 'Escape' && isOpen) {
            onClose();
        }
    }, [isOpen, onClose]);

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);
        return () => document.removeEventListener('keydown', handleKeyDown);
    }, [handleKeyDown]);

    return (
        <div
            ref={backdropRef}
            className={styles.backdrop}
            onClick={handleBackdropClick}
            style={{ display: 'none', opacity: 0 }}
        >
            <div
                ref={modalRef}
                className={styles.modal}
                onClick={handleModalClick}
                style={{ display: 'none', opacity: 0 }}
            >
                {/* Header */}
                <div className={styles.header}>
                    <h2 className={styles.title}>Selecciona otra aeronave para comparar</h2>
                    <button
                        className={styles.closeButton}
                        onClick={onClose}
                        aria-label="Cerrar modal"
                    >
                        ✕
                    </button>
                </div>

                {/* Search */}
                <div className={styles.searchContainer}>
                    <input
                        type="text"
                        className={styles.searchInput}
                        placeholder="Buscar aeronave..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>

                {/* Aircraft List */}
                <div className={styles.listContainer}>
                    {isLoading ? (
                        <div className={styles.loading}>Cargando aeronaves...</div>
                    ) : filteredAircraft.length === 0 ? (
                        <div className={styles.empty}>No se encontraron aeronaves</div>
                    ) : (
                        filteredAircraft.map((item) => (
                            <div
                                key={item.id}
                                className={`${styles.item} ${selectedAircraft === item.id ? styles.itemSelected : ''}`}
                                onClick={() => setSelectedAircraft(item.id)}
                            >
                                <span className={styles.itemName}>{item.displayName}</span>
                                <span className={styles.itemModel}>{item.model}</span>
                            </div>
                        ))
                    )}
                </div>

                {/* Footer */}
                <div className={styles.footer}>
                    <LiquidGlassButton
                        onClick={handleConfirm}
                        variant="primary"
                        disabled={!selectedAircraft}
                    >
                        Comparar
                    </LiquidGlassButton>
                </div>
            </div>
        </div>
    );
};
