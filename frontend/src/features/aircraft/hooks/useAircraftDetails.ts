import { useState, useEffect } from 'react';
import { aircraftService } from '../services/aircraftService';
import type { AircraftDetailDto } from '../types/aircraft.types';

/**
 * Custom Hook para obtener el detalle completo de una aeronave.
 * Soporta búsqueda por ID numérico o slug textual.
 *
 * @param identifier - ID numérico (ej: '51') o slug (ej: 'a350-1000')
 * @returns { aircraft, isLoading, error, refetch }
 *
 * @example
 * // Por ID numérico
 * const { aircraft, isLoading, error } = useAircraftDetail('51');
 *
 * @example
 * // Por slug textual
 * const { aircraft, isLoading, error } = useAircraftDetail('a350-1000');
 */
export const useAircraftDetail = (identifier: string | undefined) => {
    const [aircraft, setAircraft] = useState<AircraftDetailDto | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const fetchAircraftDetail = async () => {
        if (!identifier) {
            setError('No identifier provided');
            setIsLoading(false);
            return;
        }

        setIsLoading(true);
        setError(null);

        try {
            console.log('🔍 useAircraftDetail: Fetching aircraft with identifier:', identifier);

            const data = await aircraftService.getAircraftDetail(identifier);

            setAircraft(data);
            setError(null);

            console.log('✅ useAircraftDetail: Aircraft loaded successfully:', data.name);
        } catch (err) {
            const errorMessage = err instanceof Error ? err.message : 'Failed to fetch aircraft details';

            console.error('❌ useAircraftDetail: Error fetching aircraft:', errorMessage);

            setError(errorMessage);
            setAircraft(null);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchAircraftDetail();
    }, [identifier]); // Re-fetch cuando cambia el identifier

    return {
        aircraft,
        isLoading,
        error,
        refetch: fetchAircraftDetail, // Permite refetch manual
    };
};
