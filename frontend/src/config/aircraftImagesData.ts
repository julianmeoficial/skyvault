/**
 * @file aircraftImagesData.ts
 * @description Mapeo de las 36 aeronaves reales con sus imágenes
 * Basado en los archivos EXISTENTES en /assets/images/aircraft/
 */

export interface AircraftImagesData {
    name: string;
    family: string;
    manufacturer: 'Airbus' | 'Boeing';
    imagePath: string;
    year: number;
    description: string;
    passengers?: number;
    range?: number;
    engines?: number;
}

export const AIRCRAFT_IMAGES_DATA: AircraftImagesData[] = [
    // =============================================
    // AIRBUS (18 aeronaves)
    // =============================================
    {
        name: 'A220-100',
        family: 'A220',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A220-100.jpg',
        year: 2008,
        description: 'Regional jet moderno y eficiente',
        passengers: 130,
        range: 5900,
        engines: 2,
    },
    {
        name: 'A220-300',
        family: 'A220',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A220-300.jpg',
        year: 2013,
        description: 'Versión mejorada del A220',
        passengers: 160,
        range: 6850,
        engines: 2,
    },
    {
        name: 'A319neo',
        family: 'A320',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A319neo.jpg',
        year: 2015,
        description: 'Narrow-body eficiente',
        passengers: 160,
        range: 6850,
        engines: 2,
    },
    {
        name: 'A320neo',
        family: 'A320',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A320neo.jpg',
        year: 2015,
        description: 'Exitoso narrow-body moderno',
        passengers: 194,
        range: 6300,
        engines: 2,
    },
    {
        name: 'A321neo',
        family: 'A320',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A321neo.jpg',
        year: 2018,
        description: 'Versión extendida del A320neo',
        passengers: 244,
        range: 5550,
        engines: 2,
    },
    {
        name: 'A321LR',
        family: 'A320',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A321LR.jpg',
        year: 2018,
        description: 'Narrow-body de largo alcance',
        passengers: 194,
        range: 8000,
        engines: 2,
    },
    {
        name: 'A321XLR',
        family: 'A320',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A321XLR.jpg',
        year: 2019,
        description: 'Ultra-largo alcance narrow-body',
        passengers: 194,
        range: 8700,
        engines: 2,
    },
    {
        name: 'A330-200F',
        family: 'A330',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A330-200F.jpg',
        year: 2004,
        description: 'Wide-body carguero',
        passengers: 0,
        range: 7400,
        engines: 2,
    },
    {
        name: 'A330-800neo',
        family: 'A330',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A330-800neo.jpg',
        year: 2018,
        description: 'Wide-body moderno versión corta',
        passengers: 287,
        range: 8600,
        engines: 2,
    },
    {
        name: 'A330-900neo',
        family: 'A330',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A330-900neo.jpg',
        year: 2018,
        description: 'Wide-body versión extendida',
        passengers: 365,
        range: 8200,
        engines: 2,
    },
    {
        name: 'A350-900',
        family: 'A350',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A350-900.jpg',
        year: 2015,
        description: 'Wide-body de última generación',
        passengers: 325,
        range: 8100,
        engines: 2,
    },
    {
        name: 'A350-1000',
        family: 'A350',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A350-1000.jpg',
        year: 2018,
        description: 'Versión extendida del A350',
        passengers: 369,
        range: 8700,
        engines: 2,
    },
    {
        name: 'A350F',
        family: 'A350',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A350F.jpg',
        year: 2022,
        description: 'A350 versión carguero',
        passengers: 0,
        range: 8000,
        engines: 2,
    },
    {
        name: 'A380-800',
        family: 'A380',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/A380-800.jpg',
        year: 2007,
        description: 'Jumbo más grande del mundo',
        passengers: 853,
        range: 15000,
        engines: 4,
    },
    {
        name: 'ACJ320neo',
        family: 'A320',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/ACJ320neo.jpg',
        year: 2018,
        description: 'Business jet basado en A320',
        passengers: 50,
        range: 6300,
        engines: 2,
    },
    {
        name: 'ACJ330neo',
        family: 'A330',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/ACJ330neo.jpg',
        year: 2019,
        description: 'Ultra-lujo wide-body VIP',
        passengers: 30,
        range: 8600,
        engines: 2,
    },
    {
        name: 'ACJ350-XWB',
        family: 'A350',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/ACJ350 XWB.jpg',  // CORREGIDO: sin guión
        year: 2020,
        description: 'Business jet de largo alcance',
        passengers: 40,
        range: 8700,
        engines: 2,
    },
    {
        name: 'ACJ-TwoTwenty',
        family: 'A220',
        manufacturer: 'Airbus',
        imagePath: '/assets/images/aircraft/airbus/ACJ TwoTwenty.jpg',
        year: 2021,
        description: 'Business jet basado en A220',
        passengers: 20,
        range: 5900,
        engines: 2,
    },

    // =============================================
    // BOEING (18 aeronaves)
    // =============================================
    {
        name: '737 MAX 7',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/737 MAX 7.jpg',
        year: 2020,
        description: 'Narrow-body de nueva generación',
        passengers: 172,
        range: 3550,
        engines: 2,
    },
    {
        name: '737 MAX 8',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/737 MAX 8.jpg',
        year: 2017,
        description: 'Versión estándar del 737 MAX',
        passengers: 189,
        range: 3550,
        engines: 2,
    },
    {
        name: '737 MAX 9',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/737 MAX 9.jpg',
        year: 2018,
        description: 'Versión extendida del 737 MAX',
        passengers: 220,
        range: 3550,
        engines: 2,
    },
    {
        name: '737 MAX 10',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/737 MAX 10.jpg',
        year: 2023,
        description: 'Versión más grande del 737 MAX',
        passengers: 230,
        range: 3300,
        engines: 2,
    },
    {
        name: '737-800',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/737-800.jpg',
        year: 1998,
        description: 'Clásico narrow-body exitoso',
        passengers: 189,
        range: 5235,
        engines: 2,
    },
    {
        name: '737-900ER',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/737900ER (NG).jpg',  // CORREGIDO: nombre completo
        year: 2007,
        description: 'Versión mejorada del 737-900',
        passengers: 189,
        range: 5435,
        engines: 2,
    },
    {
        name: '747-8 Freighter',
        family: '747',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/747-8 Freighter.jpg',
        year: 2009,
        description: 'Jumbo carguero moderno',
        passengers: 0,
        range: 8130,
        engines: 4,
    },
    {
        name: '767-300F (carguero)',
        family: '767',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/767-300F (carguero).jpg',
        year: 1983,
        description: 'Wide-body carguero clásico',
        passengers: 0,
        range: 6385,
        engines: 2,
    },
    {
        name: '777-8',
        family: '777',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/777-8.jpg',
        year: 2020,
        description: 'Wide-body ultra-moderno',
        passengers: 350,
        range: 8715,
        engines: 2,
    },
    {
        name: '777-9 (777X)',
        family: '777X',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/777-9 (777X).jpg',
        year: 2020,
        description: 'Versión extendida del 777X',
        passengers: 420,
        range: 8715,
        engines: 2,
    },
    {
        name: '777-300ER',
        family: '777',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/777-300ER.jpg',
        year: 2004,
        description: 'Wide-body de largo alcance',
        passengers: 396,
        range: 7370,
        engines: 2,
    },
    {
        name: '777F (carguero)',
        family: '777',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/777F (carguero).jpg',
        year: 2009,
        description: 'Wide-body carguero',
        passengers: 0,
        range: 9065,
        engines: 2,
    },
    {
        name: '787-8 Dreamliner',
        family: '787',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/787-8 Dreamliner.jpg',
        year: 2011,
        description: 'Revolucionario wide-body',
        passengers: 287,
        range: 14685,
        engines: 2,
    },
    {
        name: '787-9 Dreamliner',
        family: '787',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/787-9 Dreamliner.jpg',
        year: 2013,
        description: 'Versión extendida Dreamliner',
        passengers: 296,
        range: 14010,
        engines: 2,
    },
    {
        name: '787-10 Dreamliner',
        family: '787',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/787-10 Dreamliner.jpg',
        year: 2018,
        description: 'Versión más grande Dreamliner',
        passengers: 330,
        range: 13620,
        engines: 2,
    },
    {
        name: 'BBJ 737-9 (MAX)',
        family: '737',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/BBJ 737-9 (MAX).jpg',
        year: 2019,
        description: 'Business jet basado en 737 MAX',
        passengers: 50,
        range: 3550,
        engines: 2,
    },
    {
        name: 'BBJ 777-9',
        family: '777',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/BBJ 777-9.jpg',
        year: 2020,
        description: 'Business jet ultra-lujo',
        passengers: 40,
        range: 8715,
        engines: 2,
    },
    {
        name: 'BBJ 787-9',
        family: '787',
        manufacturer: 'Boeing',
        imagePath: '/assets/images/aircraft/boeing/BBJ 787-9.jpg',
        year: 2019,
        description: 'Dreamliner VIP extremo',
        passengers: 30,
        range: 14010,
        engines: 2,
    },
];

/**
 * Obtiene imagen por nombre exacto
 */
export const getImageByAircraftName = (aircraftName: string): string | undefined => {
    const aircraft = AIRCRAFT_IMAGES_DATA.find((a) => a.name === aircraftName);
    return aircraft?.imagePath;
};

/**
 * Obtiene todas las aeronaves de una familia
 */
export const getAircraftByFamily = (familyName: string): AircraftImagesData[] => {
    return AIRCRAFT_IMAGES_DATA.filter((a) => a.family === familyName);
};

/**
 * Obtiene todas las aeronaves de un fabricante
 */
export const getAircraftByManufacturer = (
    manufacturer: 'Airbus' | 'Boeing'
): AircraftImagesData[] => {
    return AIRCRAFT_IMAGES_DATA.filter((a) => a.manufacturer === manufacturer);
};

export default AIRCRAFT_IMAGES_DATA;
