// ========== DTOs del Backend ==========

export interface ManufacturerDto {
    id: number;
    name: string;
    country?: string;
    logoUrl?: string;
}

export interface ManufacturerSummaryDto {
    id: number;
    name: string;
    code?: string;
    country?: string;
    logoUrl?: string;
    aircraftCount?: number;
}

export interface FamilyDto {
    id: number;
    name: string;
    description?: string;
}

export interface TypeDto {
    id: number;
    name: string;
    code?: string;
}

export interface ProductionStateDto {
    id: number;
    name: string;
    code?: string;
}

export interface SizeCategoryDto {
    id: number;
    name: string;
    code?: string;
}

// ========== Aircraft Card DTO (para Grid/Catálogo) ==========

export interface AircraftCardDto {
    id: number;
    name: string;
    model: string;
    displayName: string;
    thumbnailUrl?: string;

    // Datos básicos
    typicalPassengers?: number;
    maxPassengers?: number;
    rangeKm?: number;
    cruiseSpeedKnots?: number;
    introductionYear?: number;

    // Relaciones
    manufacturer: ManufacturerDto;
    family: FamilyDto;
    type?: TypeDto;
    productionState?: ProductionStateDto;
    sizeCategory?: SizeCategoryDto;

    // Metadatos
    isActive?: boolean;
    createdAt?: string;
    updatedAt?: string;
}

// ========== Respuesta paginada ==========

export interface PageInfo {
    number: number;
    size: number;
    totalElements: number;
    totalPages: number;
    first: boolean;
    last: boolean;
    hasNext: boolean;
    hasPrevious: boolean;
}

export interface PagedAircraftResponse {
    content: AircraftCardDto[];
    page: PageInfo;
    filters?: AircraftFilters;
    sort?: string;
}

// ========== Filtros ==========

export interface AircraftFilters {
    manufacturerId?: number;
    familyId?: number;
    typeId?: number;
    productionStateId?: number;
    sizeCategoryId?: number;
    minPassengers?: number;
    maxPassengers?: number;
    minRange?: number;
    maxRange?: number;
    searchTerm?: string;
    onlyActive?: boolean;
}

export interface SortOption {
    field: 'name' | 'introductionYear' | 'maxPassengers' | 'rangeKm';
    direction: 'asc' | 'desc';
}

// ========== Hook useAircraftCatalog state ==========

export interface AircraftCatalogState {
    aircraft: AircraftCardDto[];
    isLoading: boolean;
    error: string | null;
    page: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    hasMore: boolean;
}

// ========== Grid props ==========

export interface AircraftGridProps {
    aircraft: AircraftCardDto[];
    isLoading?: boolean;
    onCompare?: (aircraftId: number) => void;
    onLoadMore?: () => void;
    hasMore?: boolean;
}

// ========== Respuesta del backend (Spring) ==========

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

// ========== DTOs para Aircraft Detail Page ==========

    /**
     * Especificaciones técnicas completas de una aeronave.
     * Espejo del SpecificationsDto del backend.
     */
    export interface SpecificationsDto {
    id: number;

    // === DIMENSIONES ===
    lengthM?: string | number;
    wingspanM?: string | number;
    heightM?: string | number;
    wingAreaM2?: string | number;
    cabinLengthM?: string | number;
    cabinWidthM?: string | number;
    cabinHeightM?: string | number;

    // === PESOS ===
    emptyWeightKg?: string | number;
    maxTakeoffWeightKg?: string | number;
    maxLandingWeightKg?: string | number;
    maxPayloadKg?: string | number;

    // === MOTORES ===
    engineCount?: string | number;
    engineManufacturer?: string;
    engineModel?: string;
    engineThrustN?: string | number;
    totalThrustN?: string | number;

    // === COMBUSTIBLE ===
    fuelCapacityLiters?: string | number;
    fuelConsumptionLph?: string | number;

    // === PERFORMANCE ===
    maxSpeedKmh?: string | number;
    serviceCeilingM?: string | number;
    takeoffDistanceM?: string | number;
    landingDistanceM?: string | number;
    rangeWithMaxPaxKm?: string | number;
    rangeWithMaxPayloadKm?: string | number;

    // === CABINA ===
    firstClassSeats?: string | number;
    businessClassSeats?: string | number;
    economyClassSeats?: string | number;
    seatPitchEconomyCm?: string | number;
    seatWidthEconomyCm?: string | number;
    cargoVolumeM3?: string | number;

    // === CERTIFICACIÓN ===
    certificationAuthorities?: string;
    noiseLevelDb?: string | number;

    // === METADATA ===
    createdAt?: string;
    updatedAt?: string;
}

/**
 * DTO completo de detalle de aeronave.
 * Espejo del AircraftDetailDto del backend.
 */
export interface AircraftDetailDto {
    id: number;
    name: string;
    model: string;
    displayName: string;
    description?: string;

    // === DATOS BÁSICOS ===
    introductionYear?: number;
    firstFlightDate?: string; // ISO date string
    typicalPassengers?: number;
    maxPassengers?: number;
    rangeKm?: number;
    cruiseSpeedKnots?: number;
    serviceCeilingFt?: number;
    minCrew?: number;
    isActive?: boolean;

    // === RELACIONES ===
    manufacturer: ManufacturerDto;
    family: FamilyDto;
    type?: TypeDto;
    productionState?: ProductionStateDto;
    sizeCategory?: SizeCategoryDto;

    // === ESPECIFICACIONES TÉCNICAS ===
    specifications?: SpecificationsDto;

    // === METADATA ===
    createdAt?: string;
    updatedAt?: string;
}

/**
 * Estado del hook useAircraftDetail
 */
export interface AircraftDetailState {
    aircraft: AircraftDetailDto | null;
    isLoading: boolean;
    error: string | null;
}

// ========== SIMILAR AIRCRAFT ==========

export interface SimilarAircraftRequest {
    aircraftId: number;
    passengerTolerance?: number;
    rangeTolerance?: number;
    limit?: number;
    onlyActive?: boolean;
}

export interface SimilarAircraftParams {
    passengerTolerance?: number;
    rangeTolerance?: number;
    limit?: number;
    onlyActive?: boolean;
}
