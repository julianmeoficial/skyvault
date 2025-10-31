import { useState, useEffect, useCallback, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import type {
    AircraftOption,
    AircraftSelector,
    GroupedAircraft
} from '../types/comparison.types';
// @ts-ignore
import { fetchAllAircraft, type AircraftCardDto } from '@/features/aircraft/services/aircraftService';

const MIN_SELECTORS = 2;
const MAX_SELECTORS = 3;

export const useComparisonSelector = () => {
    const navigate = useNavigate();

    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [allAircraft, setAllAircraft] = useState<AircraftOption[]>([]);
    const [selectors, setSelectors] = useState<AircraftSelector[]>([
        { id: 'selector-1', selectedAircraft: null, isOpen: false },
        { id: 'selector-2', selectedAircraft: null, isOpen: false }
    ]);

    // Agrupar aeronaves por fabricante
    const groupedAircraft = useMemo<GroupedAircraft>(() => {
        return allAircraft.reduce((acc, aircraft) => {
            const manufacturerName = aircraft.manufacturer.name;

            if (manufacturerName === 'Airbus') {
                acc.Airbus.push(aircraft);
            } else if (manufacturerName === 'Boeing') {
                acc.Boeing.push(aircraft);
            }

            return acc;
        }, { Airbus: [], Boeing: [] } as GroupedAircraft);
    }, [allAircraft]);

    // Cargar aeronaves al montar
    useEffect(() => {
        const loadAircraft = async () => {
            try {
                setIsLoading(true);
                setError(null);

                // Obtener todas las aeronaves
                const aircraftData: AircraftCardDto[] = await fetchAllAircraft();

                // Mapear a AircraftOption
                const mapped: AircraftOption[] = aircraftData.map(aircraft => ({
                    id: aircraft.id,
                    name: aircraft.name,
                    model: aircraft.model,
                    displayName: aircraft.displayName || aircraft.name,
                    manufacturer: aircraft.manufacturer,
                    family: aircraft.family,
                    thumbnailUrl: aircraft.thumbnailUrl,
                    typicalPassengers: aircraft.typicalPassengers,
                    rangeKm: aircraft.rangeKm
                }));

                setAllAircraft(mapped);
            } catch (err) {
                setError('Error al cargar las aeronaves. Por favor, intenta de nuevo.');
                console.error('Error loading aircraft:', err);
            } finally {
                setIsLoading(false);
            }
        };

        loadAircraft();
    }, []);

    // Verificar si se puede comparar
    const canCompare = useCallback(() => {
        const selectedCount = selectors.filter(s => s.selectedAircraft !== null).length;
        return selectedCount >= MIN_SELECTORS;
    }, [selectors]);

    // Verificar si se puede agregar otro selector
    const canAddSelector = useCallback(() => {
        return selectors.length < MAX_SELECTORS;
    }, [selectors.length]);

    // Seleccionar aeronave en un selector específico
    const selectAircraft = useCallback((selectorId: string, aircraft: AircraftOption) => {
        setSelectors(prev => prev.map(selector =>
            selector.id === selectorId
                ? { ...selector, selectedAircraft: aircraft, isOpen: false }
                : selector
        ));
    }, []);

    // Limpiar selección de un selector
    const clearSelection = useCallback((selectorId: string) => {
        setSelectors(prev => prev.map(selector =>
            selector.id === selectorId
                ? { ...selector, selectedAircraft: null }
                : selector
        ));
    }, []);

    // Toggle dropdown de un selector
    const toggleDropdown = useCallback((selectorId: string) => {
        setSelectors(prev => prev.map(selector =>
            selector.id === selectorId
                ? { ...selector, isOpen: !selector.isOpen }
                : { ...selector, isOpen: false } // Cerrar los demás
        ));
    }, []);

    // Agregar un nuevo selector
    const addSelector = useCallback(() => {
        if (canAddSelector()) {
            const newId = `selector-${selectors.length + 1}`;
            setSelectors(prev => [...prev, { id: newId, selectedAircraft: null, isOpen: false }]);
        }
    }, [selectors.length, canAddSelector]);

    // Eliminar un selector
    const removeSelector = useCallback((selectorId: string) => {
        if (selectors.length > MIN_SELECTORS) {
            setSelectors(prev => prev.filter(s => s.id !== selectorId));
        }
    }, [selectors.length]);

    // Navegar a la página de comparación
    const handleCompare = useCallback(() => {
        if (canCompare()) {
            const selectedIds = selectors
                .filter(s => s.selectedAircraft !== null)
                .map(s => s.selectedAircraft!.id);

            navigate(`/compare?ids=${selectedIds.join(',')}`);
        }
    }, [selectors, canCompare, navigate]);

    return {
        selectors,
        allAircraft,
        groupedAircraft,
        isLoading,
        error,
        canCompare: canCompare(),
        canAddSelector: canAddSelector(),
        selectAircraft,
        clearSelection,
        toggleDropdown,
        addSelector,
        removeSelector,
        handleCompare
    };
};
