import { useState, useEffect, useCallback } from 'react';
import { getUserFriendlyError } from '../../../shared/utils/errorMessages';
import { aircraftService } from '../services/aircraftService';
import type {
    AircraftCardDto,
    AircraftFilters,
    SortOption,
    ManufacturerSummaryDto
} from '../types/aircraft.types';

export const useAircraftCatalog = () => {
    const [aircraft, setAircraft] = useState<AircraftCardDto[]>([]);
    const [manufacturers, setManufacturers] = useState<ManufacturerSummaryDto[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [hasMore, setHasMore] = useState(false);

    const [filters, setFilters] = useState<AircraftFilters>({
        onlyActive: true
    });

    const [sort, setSort] = useState<SortOption>({
        field: 'name',
        direction: 'asc'
    });

    // ========== CARGAR FABRICANTES ==========
    useEffect(() => {
        const loadManufacturers = async () => {
            try {
                const data = await aircraftService.getManufacturers();
                setManufacturers(data);
            } catch (err) {
                console.error('Error loading manufacturers:', err);
            }
        };

        loadManufacturers();
    }, []);

    // ========== CARGAR AERONAVES CON VALIDACIÓN ==========
    useEffect(() => {
        const loadAircraft = async () => {
            try {
                setIsLoading(true);
                setError(null);

                // ✅ VALIDACIÓN: Si hay searchTerm y tiene 1 carácter, NO buscar
                if (filters.searchTerm && filters.searchTerm.trim().length === 1) {
                    console.log('⏸️ Search term too short, waiting for more characters...');
                    setIsLoading(false);
                    return; // NO hacer petición al backend
                }

                // ✅ VALIDACIÓN: Si searchTerm está vacío, eliminarlo del filtro
                const validatedFilters = {
                    ...filters,
                    searchTerm: filters.searchTerm && filters.searchTerm.trim().length >= 2
                        ? filters.searchTerm.trim()
                        : undefined
                };

                console.log('🚀 Loading aircraft with filters:', validatedFilters, 'sort:', sort);

                const response = await aircraftService.getAircraft(
                    validatedFilters,  // ✅ Filtros validados
                    sort,
                    0,
                    20
                );

                console.log('✅ Aircraft loaded:', response.content.length, 'Total:', response.page.totalElements);

                setAircraft(response.content);
                setTotalElements(response.page.totalElements);
                setHasMore(response.page.hasNext);
                setCurrentPage(0);
            } catch (err) {
                console.error('❌ Error loading aircraft:', err);

                // ✅ Transformar error técnico a mensaje amigable
                const errorMessage = err instanceof Error ? err.message : 'Error al cargar aeronaves';
                const friendlyMessage = getUserFriendlyError(errorMessage);

                setError(friendlyMessage);
            } finally {
                setIsLoading(false);
            }
        }; // ⬅️ CIERRE DE loadAircraft

        // ✅ DEBOUNCE: Esperar 400ms después de que el usuario deje de escribir
        const debounceTimer = setTimeout(() => {
            loadAircraft();
        }, 400);

        // Limpiar timer si cambian los filtros antes de que se cumpla el tiempo
        return () => clearTimeout(debounceTimer);
    }, [
        filters.manufacturerId,
        filters.familyId,
        filters.searchTerm,
        filters.onlyActive,
        sort.field,
        sort.direction
    ]);

    // ========== LOAD MORE ==========
    const loadMore = useCallback(async () => {
        if (isLoading || !hasMore) return;

        try {
            setIsLoading(true);
            const nextPage = currentPage + 1;

            console.log('📄 Loading more, page:', nextPage);

            // ✅ Aplicar misma validación que en el efecto principal
            const validatedFilters = {
                ...filters,
                searchTerm: filters.searchTerm && filters.searchTerm.trim().length >= 2
                    ? filters.searchTerm.trim()
                    : undefined
            };

            const response = await aircraftService.getAircraft(
                validatedFilters,
                sort,
                nextPage,
                20
            );

            setAircraft(prev => [...prev, ...response.content]);
            setHasMore(response.page.hasNext);
            setCurrentPage(nextPage);
        } catch (err) {
            console.error('Error loading more aircraft:', err);
        } finally {
            setIsLoading(false);
        }
    }, [currentPage, hasMore, isLoading, filters, sort]);

    // ========== RESET ==========
    const reset = useCallback(() => {
        setFilters({ onlyActive: true });
        setSort({ field: 'name', direction: 'asc' });
        setCurrentPage(0);
        setAircraft([]);
    }, []);

    return {
        aircraft,
        manufacturers,
        isLoading,
        error,
        totalElements,
        hasMore,
        filters,
        sort,
        setFilters,
        setSort,
        loadMore,
        reset,
    };
};
