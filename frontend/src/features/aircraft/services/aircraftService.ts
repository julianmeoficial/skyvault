import type {
    AircraftCardDto,
    AircraftDetailDto,
    AircraftFilters,
    SortOption,
    ManufacturerSummaryDto,
    FamilyDto,
    SimilarAircraftParams,
} from '../types/aircraft.types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

// ========== INTERFAZ PARA RESPUESTA PAGINADA DEL BACKEND ==========

export interface PagedResponseDto<T> {
    content: T[];
    page: {
        number: number;
        size: number;
        totalElements: number;
        totalPages: number;
        numberOfElements?: number;
        first: boolean;
        last: boolean;
        hasPrevious: boolean;
        hasNext: boolean;
        empty?: boolean;
    };
    filters?: AircraftFilters | null;
    sort?: string | null;
}

// ========== SERVICIO PRINCIPAL ==========

export const aircraftService = {
    /**
     * Obtiene aeronaves paginadas con filtros
     */
    async getAircraft(
        filters?: AircraftFilters,     // ✅ 1er parámetro
        sort?: SortOption,             // ✅ 2do parámetro
        page: number = 0,              // ✅ 3er parámetro
        size: number = 20              // ✅ 4to parámetro
    ): Promise<PagedResponseDto<AircraftCardDto>> {
        try {
            const params = new URLSearchParams({
                page: page.toString(),
                size: size.toString(),
            });

            // ========== FILTROS ==========
            if (filters) {
                if (filters.manufacturerId) {
                    params.append('manufacturerId', filters.manufacturerId.toString());
                }
                if (filters.familyId) {
                    params.append('familyId', filters.familyId.toString());
                }
                if (filters.searchTerm && filters.searchTerm.trim()) {
                    params.append('searchTerm', filters.searchTerm.trim());
                }
                if (filters.onlyActive !== undefined) {
                    params.append('onlyActive', filters.onlyActive.toString());
                }
            }

            // ========== ORDENAMIENTO ==========
            // ✅ CORRECCIÓN: El backend usa sortField y sortDirection separados
            if (sort) {
                params.append('sortField', sort.field);
                params.append('sortDirection', sort.direction.toUpperCase());
            }

            console.log('🌐 Fetching aircraft:', `${API_BASE_URL}/aircraft?${params.toString()}`);

            const response = await fetch(`${API_BASE_URL}/aircraft?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            const data: PagedResponseDto<AircraftCardDto> = await response.json();

            console.log('✅ Aircraft fetched:', data.content.length, 'Total:', data.page.totalElements);

            return data;
        } catch (error) {
            console.error('❌ Error in aircraftService.getAircraft:', error);
            throw error;
        }
    },

    /**
     * Obtiene todas las aeronaves (sin paginación)
     */
    async getAllAircraft(): Promise<AircraftCardDto[]> {
        try {
            const response = await this.getAircraft({ onlyActive: true }, undefined, 0, 100);
            return response.content;
        } catch (error) {
            console.error('Error in aircraftService.getAllAircraft:', error);
            throw error;
        }
    },

    /**
     * Obtiene aeronaves de un fabricante específico
     */
    async getAircraftByManufacturer(manufacturerId: number): Promise<AircraftCardDto[]> {
        try {
            const response = await this.getAircraft({
                manufacturerId,
                onlyActive: true
            }, undefined, 0, 100);
            return response.content;
        } catch (error) {
            console.error('Error in aircraftService.getAircraftByManufacturer:', error);
            throw error;
        }
    },

    /**
     * Obtiene aeronaves de una familia específica
     */
    async getAircraftByFamily(familyId: number): Promise<AircraftCardDto[]> {
        try {
            const response = await this.getAircraft({
                familyId,
                onlyActive: true
            }, undefined, 0, 100);
            return response.content;
        } catch (error) {
            console.error('Error in aircraftService.getAircraftByFamily:', error);
            throw error;
        }
    },

    /**
     * Busca aeronaves por término
     */
    async searchAircraft(query: string, limit: number = 20): Promise<AircraftCardDto[]> {
        try {
            if (!query || query.trim().length < 2) {
                return [];
            }

            const params = new URLSearchParams({
                q: query.trim(),
                limit: limit.toString(),
            });

            const response = await fetch(`${API_BASE_URL}/aircraft/search?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error searching aircraft: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in aircraftService.searchAircraft:', error);
            throw error;
        }
    },

    /**
     * Obtiene aeronave por ID
     */
    async getAircraftById(id: number): Promise<AircraftCardDto> {
        try {
            const response = await fetch(`${API_BASE_URL}/aircraft/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Aircraft not found');
                }
                throw new Error(`Error fetching aircraft: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in aircraftService.getAircraftById:', error);
            throw error;
        }
    },

    /**
     * Obtiene todos los fabricantes
     */
    async getManufacturers(): Promise<ManufacturerSummaryDto[]> {
        try {
            const response = await fetch(`${API_BASE_URL}/manufacturers/summary?onlyActive=true`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                console.error('ManufacturersPage endpoint returned:', response.status);
                // Fallback con fabricantes básicos
                return [
                    { id: 1, name: 'Airbus', code: 'AIR', country: 'France/Germany', aircraftCount: 18 },
                    { id: 2, name: 'Boeing', code: 'BA', country: 'United States', aircraftCount: 18 }
                ];
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching manufacturers:', error);
            return [
                { id: 1, name: 'Airbus', code: 'AIR', country: 'France/Germany', aircraftCount: 18 },
                { id: 2, name: 'Boeing', code: 'BA', country: 'United States', aircraftCount: 18 }
            ];
        }
    },

    /**
     * Obtiene todas las familias
     */
    async getFamilies(manufacturerId?: number): Promise<FamilyDto[]> {
        try {
            const params = new URLSearchParams();
            if (manufacturerId) {
                params.append('manufacturerId', manufacturerId.toString());
            }

            const response = await fetch(`${API_BASE_URL}/families?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                console.error('Families endpoint returned:', response.status);
                return [];
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching families:', error);
            return [];
        }
    },

    /**
     * Obtiene aeronaves populares
     */
    async getPopularAircraft(limit: number = 10): Promise<AircraftCardDto[]> {
        try {
            const params = new URLSearchParams({
                limit: limit.toString()
            });

            const response = await fetch(`${API_BASE_URL}/aircraft/popular?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching popular aircraft: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in aircraftService.getPopularAircraft:', error);
            throw error;
        }
    },

    /**
     * Obtiene aeronaves destacadas
     */

    async getFeaturedAircraft(limit: number = 8): Promise<AircraftCardDto[]> {
        try {
            const params = new URLSearchParams({
                limit: limit.toString()
            });

            const response = await fetch(`${API_BASE_URL}/aircraft/featured?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching featured aircraft: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error in aircraftService.getFeaturedAircraft:', error);
            throw error;
        }
    },

    /**
     * Obtiene detalle completo de una aeronave por ID numérico o slug textual.
     * Ejemplos: getAircraftDetail('51') o getAircraftDetail('a350-1000')
     *
     * @param identifier - ID numérico (ej: '51') o slug (ej: 'a350-1000')
     * @returns AircraftDetailDto con toda la información técnica
     */
    async getAircraftDetail(identifier: string): Promise<AircraftDetailDto> {
        try {
            console.log('🌐 Fetching aircraft detail:', identifier);

            const response = await fetch(`${API_BASE_URL}/aircraft/${identifier}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error(`Aircraft with identifier "${identifier}" not found`);
                }
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            const data: AircraftDetailDto = await response.json();

            console.log('✅ Aircraft detail fetched:', data.name);

            return data;
        } catch (error) {
            console.error('❌ Error in aircraftService.getAircraftDetail:', error);
            throw error;
        }
    },

    /**
     * Obtiene aeronaves similares a una aeronave específica.
     * GET /api/v1/aircraft/{id}/similar
     *
     * @param aircraftId - ID de la aeronave de referencia
     * @param params - Parámetros opcionales (passengerTolerance, rangeTolerance, limit, onlyActive)
     * @returns Array de aeronaves similares (AircraftCardDto[])
     */
    async getSimilarAircraft(
        aircraftId: number,
        params?: SimilarAircraftParams
    ): Promise<AircraftCardDto[]> {
        try {
            const queryParams = new URLSearchParams();

            // Parámetros opcionales
            if (params?.passengerTolerance !== undefined) {
                queryParams.append('passengerTolerance', params.passengerTolerance.toString());
            }
            if (params?.rangeTolerance !== undefined) {
                queryParams.append('rangeTolerance', params.rangeTolerance.toString());
            }
            if (params?.limit !== undefined) {
                queryParams.append('limit', params.limit.toString());
            }
            if (params?.onlyActive !== undefined) {
                queryParams.append('onlyActive', params.onlyActive.toString());
            }

            const queryString = queryParams.toString();
            const url = queryString
                ? `${API_BASE_URL}/aircraft/${aircraftId}/similar?${queryString}`
                : `${API_BASE_URL}/aircraft/${aircraftId}/similar`;

            console.log('🌐 Fetching similar aircraft:', url);

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Failed to fetch similar aircraft: ${response.statusText}`);
            }

            const data: AircraftCardDto[] = await response.json();

            console.log('✅ Similar aircraft fetched:', data.length);

            return data;
        } catch (error) {
            console.error('❌ Error fetching similar aircraft:', error);
            throw error;
        }
    }

};

// ==================== FUNCIONES LEGACY (para compatibilidad) ====================

export const fetchManufacturers = aircraftService.getManufacturers;
export const fetchAircraftByManufacturer = aircraftService.getAircraftByManufacturer;
export const fetchAllAircraft = aircraftService.getAllAircraft;
