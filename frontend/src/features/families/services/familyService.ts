/**
 * @file familyService.ts
 * @description Servicio completo para consumir endpoints de familias desde el backend
 * Usa fetch nativo para mantener consistencia con otros servicios
 */

import type {
    FamilyDto,
    AircraftCardDto,
    PagedResponseDto,
    FamilyFilterDto,
    FamilyStatisticsDto,
} from '../types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

interface FamilySummaryResponse {
    id: number;
    name: string;
    category: string;
}

export const familyService = {
    /**
     * Obtiene lista paginada de familias con filtros opcionales
     * @param filters - Filtros de búsqueda
     * @returns Promise con datos paginados
     */
    async getAllFamilies(
        filters?: FamilyFilterDto
    ): Promise<PagedResponseDto<FamilyDto>> {
        try {
            const params = new URLSearchParams();

            if (filters?.manufacturerId) {
                params.append('manufacturerId', filters.manufacturerId.toString());
            }
            if (filters?.name) {
                params.append('name', filters.name);
            }
            if (filters?.category) {
                params.append('category', filters.category);
            }
            if (filters?.page !== undefined) {
                params.append('page', filters.page.toString());
            }
            if (filters?.size !== undefined) {
                params.append('size', filters.size.toString());
            }

            const response = await fetch(
                `${API_BASE_URL}/families?${params.toString()}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            if (!response.ok) {
                throw new Error(`Error fetching families: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getAllFamilies:', error);
            throw error;
        }
    },

    /**
     * Obtiene detalles de una familia específica
     * @param id - ID de la familia
     * @returns Promise con datos de la familia
     */
    async getFamilyById(id: number): Promise<FamilyDto> {
        try {
            const response = await fetch(`${API_BASE_URL}/families/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching family: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getFamilyById:', error);
            throw error;
        }
    },

    /**
     * Obtiene todas las aeronaves de una familia específica
     * @param id - ID de la familia
     * @param page - Número de página (por defecto 0)
     * @param size - Tamaño de página (por defecto 20)
     * @returns Promise con lista de aeronaves
     */
    async getFamilyAircraft(
        id: number,
        page = 0,
        size = 20
    ): Promise<AircraftCardDto[]> {
        try {
            const params = new URLSearchParams({
                page: page.toString(),
                size: size.toString(),
            });

            const response = await fetch(
                `${API_BASE_URL}/families/${id}/aircraft?${params.toString()}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            if (!response.ok) {
                throw new Error(`Error fetching family aircraft: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getFamilyAircraft:', error);
            throw error;
        }
    },

    /**
     * Obtiene lista simplificada de familias (para dropdowns, etc.)
     * @param manufacturerId - ID de fabricante opcional
     * @returns Promise con lista de familias
     */
    async getFamiliesSummary(
        manufacturerId?: number
    ): Promise<FamilySummaryResponse[]> {
        try {
            const params = new URLSearchParams();
            if (manufacturerId) {
                params.append('manufacturerId', manufacturerId.toString());
            }

            const queryString = params.toString();
            const url = queryString
                ? `${API_BASE_URL}/families/summary?${queryString}`
                : `${API_BASE_URL}/families/summary`;

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching families summary: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getFamiliesSummary:', error);
            throw error;
        }
    },

    /**
     * Obtiene categorías disponibles de familias
     * @returns Promise con array de categorías
     */
    async getAvailableCategories(): Promise<string[]> {
        try {
            const response = await fetch(`${API_BASE_URL}/families/categories`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching categories: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getAvailableCategories:', error);
            throw error;
        }
    },

    /**
     * Obtiene familias populares (con más modelos)
     * @param limit - Cantidad máxima de resultados (por defecto 10)
     * @returns Promise con familias populares
     */
    async getPopularFamilies(limit = 10): Promise<FamilyDto[]> {
        try {
            const params = new URLSearchParams({
                limit: limit.toString(),
            });

            const response = await fetch(
                `${API_BASE_URL}/families/popular?${params.toString()}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            if (!response.ok) {
                throw new Error(`Error fetching popular families: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getPopularFamilies:', error);
            throw error;
        }
    },

    /**
     * Busca familias por texto
     * @param query - Término de búsqueda (mínimo 2 caracteres)
     * @param limit - Cantidad máxima de resultados (por defecto 10)
     * @returns Promise con resultados de búsqueda
     */
    async searchFamilies(
        query: string,
        limit = 10
    ): Promise<FamilySummaryResponse[]> {
        if (query.trim().length < 2) {
            return [];
        }

        try {
            const params = new URLSearchParams({
                q: encodeURIComponent(query.trim()),
                limit: limit.toString(),
            });

            const response = await fetch(
                `${API_BASE_URL}/families/search?${params.toString()}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            if (!response.ok) {
                throw new Error(`Error searching families: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.searchFamilies:', error);
            throw error;
        }
    },

    /**
     * Obtiene estadísticas globales de familias
     * @returns Promise con estadísticas
     */
    async getFamilyStatistics(): Promise<FamilyStatisticsDto> {
        try {
            const response = await fetch(`${API_BASE_URL}/families/statistics`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching statistics: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in familyService.getFamilyStatistics:', error);
            throw error;
        }
    },

    /**
     * Filtra familias por fabricante
     * @param manufacturer - Nombre del fabricante (Airbus o Boeing)
     * @returns Promise con familias del fabricante
     */
    async getFamiliesByManufacturer(
        manufacturer: 'Airbus' | 'Boeing'
    ): Promise<FamilyDto[]> {
        try {
            const response = await familyService.getAllFamilies({
                manufacturerId: manufacturer === 'Airbus' ? 1 : 2,
            });
            return response.content;
        } catch (error) {
            console.error('Error in familyService.getFamiliesByManufacturer:', error);
            throw error;
        }
    },
};

export default familyService;
