export interface ComparisonItem {
    id: number;
    name: string;
    displayName?: string;
    model?: string;
    thumbnailUrl?: string;

    manufacturer?: {
        id: number;
        name: string;
    };

    family?: {
        id: number;
        name: string;
    };

    // Rendimiento - AJUSTADO
    range_km?: number;
    rangeKm?: number;
    cruise_speed_knots?: number;
    cruiseSpeedKnots?: number;

    // Capacidad - AJUSTADO
    max_passengers?: number;
    maxPassengers?: number;
    typical_passengers?: number;
    typicalPassengers?: number;
    max_fuel_liters?: number;
    maxFuelLiters?: number;

    // Dimensiones - AJUSTADO (nombres que pueden venir del backend)
    length_meters?: number;
    lengthMeters?: number;
    wingspan_meters?: number;
    wingspanMeters?: number;
    height_meters?: number;
    heightMeters?: number;

    // Motor - AJUSTADO
    engine_manufacturer?: string;
    engineManufacturer?: string;
    engine_model?: string;
    engineModel?: string;

    // General - AJUSTADO
    min_crew?: number;
    minCrew?: number;
    introduction_year?: number;
    introductionYear?: number;
    production_state?: {
        id: number;
        name: string;
    };
    productionState?: {
        id: number;
        name: string;
    };

    // ⭐ AGREGAR SPECIFICATIONS - NUEVO
    specifications?: {
        lengthM?: string;
        wingspanM?: string;
        heightM?: string;
        wingAreaM2?: string;
        emptyWeightKg?: string;
        maxTakeoffWeightKg?: string;
        maxLandingWeightKg?: string;
        maxPayloadKg?: string;
        maxSpeedKmh?: string;
        serviceCeilingM?: string;
        takeoffDistanceM?: string;
        landingDistanceM?: string;
        fuelCapacityLiters?: string;
        fuelConsumptionLph?: string;
        cabinLengthM?: string;
        cabinWidthM?: string;
        cabinHeightM?: string;
        cargoVolumeM3?: string;
        engineManufacturer?: string;
        engineModel?: string;
        engineCount?: string;
        engineThrustN?: string;
        totalThrustN?: string;
        firstClassSeats?: string;
        businessClassSeats?: string;
        economyClassSeats?: string;
        seatPitchEconomyCm?: string;
        seatWidthEconomyCm?: string;
        rangeWithMaxPaxKm?: string;
        rangeWithMaxPayloadKm?: string;
        certificationAuthorities?: string;
        noiseLevelDb?: string;
    };

    // Imágenes
    images?: Array<{
        id: number;
        url: string;
        isPrimary: boolean;
    }>;
}

export interface ComparisonState {
    items: ComparisonItem[];
    maxItems: number;
    isLoading: boolean;
    error: string | null;
}

// ==================== SELECTOR TYPES ====================

export interface AircraftOption {
    id: number;
    name: string;
    model: string;
    displayName: string;
    manufacturer: {
        id: number;
        name: string;
    };
    family: {
        id: number;
        name: string;
    };
    thumbnailUrl?: string;
    typicalPassengers?: number;
    rangeKm?: number;
}

export interface AircraftSelector {
    id: string; // selector-1, selector-2, selector-3
    selectedAircraft: AircraftOption | null;
    isOpen: boolean;
}

export interface GroupedAircraft {
    Airbus: AircraftOption[];
    Boeing: AircraftOption[];
}

export interface ComparisonSelectorState {
    selectors: AircraftSelector[];
    allAircraft: AircraftOption[];
    groupedAircraft: GroupedAircraft;
    isLoading: boolean;
    error: string | null;
}

// ==================== SPECIFICATION ROW ====================

export interface SpecificationRowData {
    label: string;
    unit?: string;
    values: (number | string | null | undefined)[];
}

export interface ManufacturerDto {
    id: number;
    name: string;
}

export interface FamilyDto {
    id: number;
    name: string;
}
