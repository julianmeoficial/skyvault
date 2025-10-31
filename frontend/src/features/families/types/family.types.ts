/**
 * @file family.types.ts
 * @description Tipos e interfaces para la feature de Familias de Aeronaves
 */

export interface ManufacturerSummaryDto {
    id: number;
    name: string;
    country: string;
    logoUrl?: string;
}

export interface FamilyDto {
    id: number;
    name: string;
    description: string;
    launchDate: string; // ISO date string (YYYY-MM-DD)
    category: string; // Narrow-body, Wide-body, Jumbo
    manufacturer: ManufacturerSummaryDto;
    activeModelCount: number;
    createdAt: string;
    updatedAt: string;
}

export interface AircraftCardDto {
    id: number;
    name: string;
    type: string;
    category: string;
    productionState: string;
    passengers: number;
    range: number;
    fuelCapacity: number;
    year: number;
    imageUrl?: string;
}

export interface PagedResponseDto<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    currentPage: number;
    pageSize: number;
    last: boolean;
    first: boolean;
}

// Filtros para familias
export interface FamilyFilterDto {
    manufacturerId?: number;
    name?: string;
    category?: string;
    page?: number;
    size?: number;
}

// Datos de evolución histórica (frontend)
export interface FamilyEvolutionEvent {
    id: string; // unique identifier (e.g., "airbus-a220-100")
    year: number;
    aircraftName: string; // A220-100, A320neo, etc.
    familyName: string; // A220, A320, 737, etc.
    manufacturer: 'Airbus' | 'Boeing';
    category: string; // Narrow-body, Wide-body, Jumbo
    event: string; // Título del evento (ej: "Primera aeronave de la familia")
    description: string; // Descripción detallada del evento/cambio
    imagePath: string; // Ruta local de la imagen (/assets/images/aircraft/...)
    keyHighlights: string[]; // Puntos clave del modelo
    passengers?: number;
    range?: number; // En km
    engines?: string;
}

// Estadísticas de familias
export interface FamilyStatisticsDto {
    totalFamilies: number;
    totalAircraft: number;
    byManufacturer: {
        airbus: number;
        boeing: number;
    };
    byCategory: {
        narrowBody: number;
        wideBody: number;
        jumbo: number;
    };
}

// Tipo para filtro de timeline
export interface TimelineFilter {
    manufacturer?: 'Airbus' | 'Boeing';
    familyName?: string;
    yearRange?: [number, number];
}

export type FamilyCategory = 'Narrow-body' | 'Wide-body' | 'Jumbo';
export type Manufacturer = 'Airbus' | 'Boeing';
