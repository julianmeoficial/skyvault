/**
 * @file useFamilies.ts
 * @description Custom hooks para gestionar estado y lógica de familias
 */

import { useState, useEffect, useCallback } from 'react';
import { familyService } from '../services/familyService';
import type {
    FamilyDto,
    FamilyFilterDto,
    FamilyStatisticsDto,
} from '../types';

interface UseFamiliesReturn {
    families: FamilyDto[];
    loading: boolean;
    error: string | null;
    pagination: {
        currentPage: number;
        totalPages: number;
        totalElements: number;
        pageSize: number;
    };
    fetchFamilies: (filters?: FamilyFilterDto) => Promise<void>;
    refetch: () => Promise<void>;
}

/**
 * Hook para obtener y gestionar lista de familias
 * @param initialPage - Página inicial (por defecto 0)
 * @param pageSize - Tamaño de página (por defecto 20)
 * @returns Objeto con estado de familias y funciones de control
 */
export const useFamilies = (
    initialPage = 0,
    pageSize = 20
): UseFamiliesReturn => {
    const [families, setFamilies] = useState<FamilyDto[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [pagination, setPagination] = useState({
        currentPage: initialPage,
        totalPages: 0,
        totalElements: 0,
        pageSize,
    });

    const fetchFamilies = useCallback(
        async (filters?: FamilyFilterDto) => {
            try {
                setLoading(true);
                setError(null);

                const response = await familyService.getAllFamilies({
                    ...filters,
                    page: filters?.page ?? initialPage,
                    size: filters?.size ?? pageSize,
                });

                setFamilies(response.content);
                setPagination({
                    currentPage: response.currentPage,
                    totalPages: response.totalPages,
                    totalElements: response.totalElements,
                    pageSize: response.pageSize,
                });
            } catch (err) {
                const errorMessage =
                    err instanceof Error ? err.message : 'Error al cargar familias';
                setError(errorMessage);
                console.error('Error fetching families:', err);
            } finally {
                setLoading(false);
            }
        },
        [initialPage, pageSize]
    );

    const refetch = useCallback(() => {
        return fetchFamilies();
    }, [fetchFamilies]);

    // Carga inicial
    useEffect(() => {
        void fetchFamilies();
    }, [fetchFamilies]);

    return {
        families,
        loading,
        error,
        pagination,
        fetchFamilies,
        refetch,
    };
};

interface UseFamilyByIdReturn {
    family: FamilyDto | null;
    loading: boolean;
    error: string | null;
    refetch: () => Promise<void>;
}

/**
 * Hook para obtener detalles de una familia por ID
 * @param familyId - ID de la familia
 * @returns Objeto con datos de la familia
 */
export const useFamilyById = (familyId: number): UseFamilyByIdReturn => {
    const [family, setFamily] = useState<FamilyDto | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const refetch = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await familyService.getFamilyById(familyId);
            setFamily(data);
        } catch (err) {
            const errorMessage =
                err instanceof Error ? err.message : 'Error al cargar familia';
            setError(errorMessage);
            console.error('Error fetching family:', err);
        } finally {
            setLoading(false);
        }
    }, [familyId]);

    useEffect(() => {
        if (familyId) {
            void refetch();
        }
    }, [familyId, refetch]);

    return { family, loading, error, refetch };
};

interface UsePopularFamiliesReturn {
    families: FamilyDto[];
    loading: boolean;
    error: string | null;
    refetch: () => Promise<void>;
}

/**
 * Hook para obtener familias populares
 * @param limit - Cantidad de familias a obtener (por defecto 10)
 * @returns Objeto con familias populares
 */
export const usePopularFamilies = (limit = 10): UsePopularFamiliesReturn => {
    const [families, setFamilies] = useState<FamilyDto[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const refetch = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await familyService.getPopularFamilies(limit);
            setFamilies(data);
        } catch (err) {
            const errorMessage =
                err instanceof Error
                    ? err.message
                    : 'Error al cargar familias populares';
            setError(errorMessage);
            console.error('Error fetching popular families:', err);
        } finally {
            setLoading(false);
        }
    }, [limit]);

    useEffect(() => {
        void refetch();
    }, [refetch]);

    return { families, loading, error, refetch };
};

interface UseFamilyStatisticsReturn {
    stats: FamilyStatisticsDto | null;
    loading: boolean;
    error: string | null;
    refetch: () => Promise<void>;
}

/**
 * Hook para obtener estadísticas de familias
 * @returns Objeto con estadísticas
 */
export const useFamilyStatistics = (): UseFamilyStatisticsReturn => {
    const [stats, setStats] = useState<FamilyStatisticsDto | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const refetch = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await familyService.getFamilyStatistics();
            setStats(data);
        } catch (err) {
            const errorMessage =
                err instanceof Error ? err.message : 'Error al cargar estadísticas';
            setError(errorMessage);
            console.error('Error fetching family statistics:', err);
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        void refetch();
    }, [refetch]);

    return { stats, loading, error, refetch };
};
