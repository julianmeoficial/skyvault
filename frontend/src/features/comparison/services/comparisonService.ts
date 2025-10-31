import type { ComparisonItem, GroupedAircraft, AircraftOption } from '../types/comparison.types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

interface CompareResultDto {
    aircraft: ComparisonItem[];
    comparisonTable?: Record<string, unknown>;
}

interface SearchSuggestionDto {
    id?: number | string;
    aircraftId?: number | string;
    name?: string;
    label?: string;
    model?: string;
    subtitle?: string;
}

interface SearchResult {
    id: string;
    name: string;
    model: string;
}

export const comparisonService = {
    async fetchComparisonData(ids: string[]): Promise<ComparisonItem[]> {
        if (ids.length === 0) {
            return [];
        }

        if (ids.length < 2 || ids.length > 3) {
            throw new Error('Debe seleccionar entre 2 y 3 aeronaves para comparar');
        }

        try {
            const idsParam = ids.join(',');

            const params = new URLSearchParams({
                ids: idsParam,
                includeSpecifications: 'true',
                includeImages: 'true',
                normalizeUnits: 'true',
                unitFormat: 'metric',
            });

            const response = await fetch(`${API_BASE_URL}/aircraft/compare?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching comparison data: ${response.statusText}`);
            }

            const data: CompareResultDto | ComparisonItem[] = await response.json();

            if (typeof data === 'object' && data !== null && 'aircraft' in data && Array.isArray(data.aircraft)) {
                return data.aircraft;
            }

            if (Array.isArray(data)) {
                return data;
            }

            throw new Error('Invalid response format from backend');
        } catch (error) {
            console.error('Error in comparisonService.fetchComparisonData:', error);
            throw error;
        }
    },

    async searchAircraft(query: string): Promise<SearchResult[]> {
        if (!query || query.trim().length < 2) {
            return [];
        }

        try {
            const params = new URLSearchParams({
                q: query.trim(),
                limit: '10',
                type: 'aircraft',
                onlyActive: 'true',
            });

            const response = await fetch(`${API_BASE_URL}/search/suggest?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error searching aircraft: ${response.statusText}`);
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const results: SearchResult[] = data.map((item: SearchSuggestionDto) => ({
                    id: String(item.id || item.aircraftId || ''),
                    name: String(item.name || item.label || ''),
                    model: String(item.model || item.subtitle || ''),
                }));
                return results;
            }

            return [];
        } catch (error) {
            console.error('Error in comparisonService.searchAircraft:', error);
            throw error;
        }
    },

    async getGroupedAircraft(): Promise<GroupedAircraft> {
        try {
            // Agregar parámetro size=100 para obtener todas las aeronaves
            const params = new URLSearchParams({
                size: '100',
                page: '0',
                sort: 'name,asc'
            });

            const response = await fetch(`${API_BASE_URL}/aircraft?${params.toString()}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching aircraft: ${response.statusText}`);
            }

            const pagedResponse = await response.json();

            const data: ComparisonItem[] = Array.isArray(pagedResponse)
                ? pagedResponse
                : pagedResponse.content || [];

            console.log('✅ Aircraft loaded:', data.length);

            const grouped: GroupedAircraft = {
                Airbus: [],
                Boeing: []
            };

            if (Array.isArray(data)) {
                data.forEach((aircraft: ComparisonItem) => {
                    if (!aircraft.manufacturer || !aircraft.manufacturer.name) {
                        console.warn('⚠️ Aircraft missing manufacturer:', aircraft);
                        return;
                    }

                    const aircraftOption: AircraftOption = {
                        id: Number(aircraft.id),
                        name: aircraft.name,
                        model: aircraft.model || '',
                        displayName: aircraft.displayName || aircraft.name,
                        manufacturer: aircraft.manufacturer,
                        family: aircraft.family || { id: 0, name: 'Unknown' },
                        thumbnailUrl: aircraft.thumbnailUrl,
                        typicalPassengers: aircraft.typicalPassengers || aircraft.typical_passengers,
                        rangeKm: aircraft.rangeKm || aircraft.range_km
                    };

                    const manufacturerName = aircraft.manufacturer.name;

                    if (manufacturerName === 'Airbus') {
                        grouped.Airbus.push(aircraftOption);
                    } else if (manufacturerName === 'Boeing') {
                        grouped.Boeing.push(aircraftOption);
                    }
                });
            }

            console.log(' Grouped aircraft:', {
                Airbus: grouped.Airbus.length,
                Boeing: grouped.Boeing.length,
                Total: grouped.Airbus.length + grouped.Boeing.length
            });

            return grouped;
        } catch (error) {
            console.error(' Error in comparisonService.getGroupedAircraft:', error);
            throw error;
        }
    }
};
