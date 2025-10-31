import { useState, useEffect } from 'react';
import { aircraftService } from '../services/aircraftService';
import type { AircraftCardDto, SimilarAircraftParams } from '../types/aircraft.types';

interface UseSimilarAircraftResult {
    similarAircraft: AircraftCardDto[];
    isLoading: boolean;
    error: Error | null;
}

/**
 * Hook para obtener aeronaves similares a una aeronave específica.
 *
 * @param aircraftId - ID de la aeronave de referencia
 * @param params - Parámetros opcionales de búsqueda (passengerTolerance, rangeTolerance, limit)
 * @returns Estado con aeronaves similares, loading y error
 *
 * @example
 * const { similarAircraft, isLoading, error } = useSimilarAircraft(51, { limit: 3 });
 */
export function useSimilarAircraft(
    aircraftId: number,
    params?: SimilarAircraftParams
): UseSimilarAircraftResult {
    const [similarAircraft, setSimilarAircraft] = useState<AircraftCardDto[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        let isMounted = true;

        const fetchSimilarAircraft = async () => {
            try {
                setIsLoading(true);
                setError(null);

                // Valores por defecto: máximo 3 aeronaves, solo activas
                const searchParams: SimilarAircraftParams = {
                    limit: 3,
                    onlyActive: true,
                    ...params
                };

                const data = await aircraftService.getSimilarAircraft(
                    aircraftId,
                    searchParams
                );

                if (isMounted) {
                    setSimilarAircraft(data);
                }
            } catch (err) {
                if (isMounted) {
                    setError(err instanceof Error ? err : new Error('Unknown error'));
                    setSimilarAircraft([]);
                }
            } finally {
                if (isMounted) {
                    setIsLoading(false);
                }
            }
        };

        if (aircraftId) {
            fetchSimilarAircraft();
        }

        return () => {
            isMounted = false;
        };
    }, [aircraftId, params?.limit, params?.passengerTolerance, params?.rangeTolerance, params?.onlyActive]);

    return { similarAircraft, isLoading, error };
}
