/**
 * @file aircraftImageMapping.ts
 * @description Mapeo correcto con nombres de archivos EXISTENTES
 */

export const FAMILY_TO_IMAGE_MAP: Record<string, string> = {
    // AIRBUS
    'A220': 'airbus/A220-300.jpg',
    'A320': 'airbus/A321XLR.jpg',
    'A330': 'airbus/A330-900neo.jpg',
    'A350': 'airbus/A350-1000.jpg',
    'A380': 'airbus/A380-800.jpg',

    // BOEING
    '737': 'boeing/737 MAX 10.jpg',
    '747': 'boeing/747-8 Freighter.jpg',
    '767': 'boeing/767-300F (carguero).jpg',
    '777': 'boeing/777-300ER.jpg',
    '777X': 'boeing/777-9 (777X).jpg',
    '787': 'boeing/787-10 Dreamliner.jpg',
};

export const getAircraftImage = (familyName: string): string => {
    if (!familyName) {
        console.warn('[getAircraftImage] familyName vacío');
        return '/assets/images/placeholder.jpg';
    }

    const imagePath = FAMILY_TO_IMAGE_MAP[familyName];

    if (!imagePath) {
        console.warn(`[getAircraftImage] "${familyName}" no en mapping`);
        return '/assets/images/placeholder.jpg';
    }

    return `/assets/images/aircraft/${imagePath}`;
};

export default { getAircraftImage, FAMILY_TO_IMAGE_MAP };
