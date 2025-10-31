/**
 * @file familyHistoryData.ts
 * @description Datos históricos y de evolución de familias de aeronaves
 * 36 aeronaves comerciales: 18 Airbus + 18 Boeing
 * RUTAS CORREGIDAS PARA COINCIDIR CON aircraftImageMapping.ts
 */

import type { FamilyEvolutionEvent } from '../types';

/**
 * Datos completos de evolución histórica de todas las familias
 * Incluye 36 aeronaves comerciales
 */
export const familyHistoryData: FamilyEvolutionEvent[] = [
    // ============================================
    // AIRBUS - 18 AERONAVES
    // ============================================

    // A220 Family (3 aeronaves)
    {
        id: 'airbus-a220-100',
        year: 2016,
        aircraftName: 'A220-100',
        familyName: 'A220',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Inicio de la familia A220',
        description:
            'Incorporación de la serie Bombardier C Series a Airbus como A220. Primer modelo de una nueva generación de aeronaves narrow-body más eficientes.',
        imagePath: '/images/aircraft/airbus/A220-100.jpg',
        keyHighlights: [
            'Motor Geared Turbofan (GTF)',
            '133 pasajeros',
            'Consumo 20% menor que competencia',
            'Sistemas aeronáuticos más modernos',
        ],
        passengers: 133,
        range: 5740,
        engines: 'Pratt & Whitney PW1500G',
    },
    {
        id: 'airbus-a220-300',
        year: 2016,
        aircraftName: 'A220-300',
        familyName: 'A220',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Variante extendida A220-300',
        description:
            'Versión alargada del A220 con mayor capacidad. Mantiene la eficiencia de combustible con capacidad para 160 pasajeros.',
        imagePath: '/images/aircraft/airbus/A220-300.jpg',
        keyHighlights: [
            '160 pasajeros',
            'Alcance extendido 6297 km',
            'Compatibilidad con A220-100',
            'Eficiencia operacional mejorada',
        ],
        passengers: 160,
        range: 6297,
        engines: 'Pratt & Whitney PW1500G',
    },
    {
        id: 'airbus-acj-twotwenty',
        year: 2021,
        aircraftName: 'ACJ TwoTwenty',
        familyName: 'A220',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Business Jet basado en A220',
        description:
            'Derivado business jet del A220 con cabina ultra-lujosa. Diseñado para ejecutivos con alcance intercontinental.',
        imagePath: '/images/aircraft/airbus/ACJ-TwoTwenty.jpg',
        keyHighlights: [
            '12 pasajeros (configuración ejecutiva)',
            'Alcance 10465 km',
            'Cabina completamente personalizable',
            'Certificado EASA/FAA',
        ],
        passengers: 12,
        range: 10465,
        engines: 'Pratt & Whitney PW1500G',
    },

    // A320 Family (6 aeronaves)
    {
        id: 'airbus-a319neo',
        year: 2016,
        aircraftName: 'A319neo',
        familyName: 'A320',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Neo: Nueva Generación A320',
        description:
            'Primera aeronave de la serie "neo" (New Engine Option) con motores nuevos LEAP-1A o PW1100G. Revolucionó la eficiencia del segmento narrow-body.',
        imagePath: '/images/aircraft/airbus/A319neo.jpg',
        keyHighlights: [
            'Consumo 20% menor de combustible',
            '124 pasajeros',
            'Motores de nueva generación',
            'Alcance 6900 km',
            'Aviónicos Thales TopDeck',
        ],
        passengers: 124,
        range: 6900,
        engines: 'CFM LEAP-1A / Pratt & Whitney PW1100G-JM',
    },
    {
        id: 'airbus-a320neo',
        year: 2016,
        aircraftName: 'A320neo',
        familyName: 'A320',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Caballo de batalla: A320neo',
        description:
            'Versión estándar neo del A320, la aeronave más producida del mundo. 150 pasajeros con tecnología de punta.',
        imagePath: '/images/aircraft/airbus/A320neo.jpg',
        keyHighlights: [
            'Aeronave más solicitada globalmente',
            '150 pasajeros',
            'Fuselaje composite en puertas y alas',
            'Alcance 6300 km',
            'Sistemas IFE de última generación',
        ],
        passengers: 150,
        range: 6300,
        engines: 'CFM LEAP-1A / Pratt & Whitney PW1100G-JM',
    },
    {
        id: 'airbus-a321lr',
        year: 2018,
        aircraftName: 'A321LR',
        familyName: 'A320',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Alcance extendido: A321LR',
        description:
            'Variante "Long Range" del A321 con tanques de combustible adicionales. Competidor directo del 787 en rutas punto-a-punto.',
        imagePath: '/images/aircraft/airbus/A321LR.jpg',
        keyHighlights: [
            '206 pasajeros',
            'Alcance intercontinental 7400 km',
            'Combustible adicional 4600 kg',
            'Sistemas CFRP avanzados',
            'Viable para rutas transatlánticas',
        ],
        passengers: 206,
        range: 7400,
        engines: 'CFM LEAP-1A / Pratt & Whitney PW1100G-JM',
    },
    {
        id: 'airbus-a321neo',
        year: 2017,
        aircraftName: 'A321neo',
        familyName: 'A320',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Versatilidad: A321neo',
        description:
            'Versión neo alargada del A320. Proporciona capacidad de fuselaje ancho en un airframe narrow-body con eficiencia de costos.',
        imagePath: '/images/aircraft/airbus/A321neo.jpg',
        keyHighlights: [
            '185 pasajeros en configuración típica',
            'Alcance 7400 km',
            'Eficiencia comparable a wide-body',
            'Mayor flexibilidad en rutas',
            'Compatibilidad con A320 family',
        ],
        passengers: 185,
        range: 7400,
        engines: 'CFM LEAP-1A / Pratt & Whitney PW1100G-JM',
    },
    {
        id: 'airbus-a321xlr',
        year: 2020,
        aircraftName: 'A321XLR',
        familyName: 'A320',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Ultra alcance: A321XLR',
        description:
            'Versión "Extra Long Range" del A321 con capacidad para rutas ultra-largas (8700 km). Introduce fuselaje composite avanzado.',
        imagePath: '/images/aircraft/airbus/A321XLR.jpg',
        keyHighlights: [
            '220 pasajeros',
            'Alcance 8700 km (América del Sur a Europa)',
            'Fuselaje 30% composite',
            'Cabina presurizada mejorada',
            'Revolución en segmento punto-a-punto',
        ],
        passengers: 220,
        range: 8700,
        engines: 'CFM LEAP-1A / Pratt & Whitney PW1100G-JM',
    },
    {
        id: 'airbus-acj320neo',
        year: 2018,
        aircraftName: 'ACJ320neo',
        familyName: 'A320',
        manufacturer: 'Airbus',
        category: 'Narrow-body',
        event: 'Business Jet: ACJ320neo',
        description:
            'Jet corporativo basado en A320neo para ejecutivos globales. Cabina completamente customizable con servicios de lujo.',
        imagePath: '/images/aircraft/airbus/ACJ320neo.jpg',
        keyHighlights: [
            '8 pasajeros (ultra-lujosa)',
            'Alcance 11100 km',
            'Suite ejecutiva personalizada',
            'Cocina gourmet integrada',
            'Compatibilidad con infraestructura A320',
        ],
        passengers: 8,
        range: 11100,
        engines: 'CFM LEAP-1A / Pratt & Whitney PW1100G',
    },

    // A330 Family (4 aeronaves)
    {
        id: 'airbus-a330-200f',
        year: 2010,
        aircraftName: 'A330-200F',
        familyName: 'A330',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Carguero moderno: A330-200F',
        description:
            'Versión carguero del A330-200 optimizada para transporte de carga. Fuselaje robusto con capacidad de 7400 km.',
        imagePath: '/images/aircraft/airbus/A330-200F.jpg',
        keyHighlights: [
            'Capacidad 65-70 toneladas de carga',
            'Alcance 7400 km',
            'Sistemas hidráulicos redundantes',
            'Mantenimiento simplificado',
            'Eficiente para rutas intercontinentales de carga',
        ],
        passengers: 0,
        range: 7400,
        engines: 'Pratt & Whitney PW4000 / Rolls-Royce Trent 700',
    },
    {
        id: 'airbus-a330-800neo',
        year: 2018,
        aircraftName: 'A330-800neo',
        familyName: 'A330',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Neo revolucionario: A330-800neo',
        description:
            'Primera versión neo de la familia A330. Motores Rolls-Royce Trent 7000 con 25% más eficiencia y alcance de 14334 km.',
        imagePath: '/images/aircraft/airbus/A330-800neo.jpg',
        keyHighlights: [
            '271 pasajeros',
            'Motores Trent 7000',
            'Consumo 30% inferior a generación anterior',
            'Alcance 14334 km (récord para A330)',
            'Nuevas alas optimizadas',
        ],
        passengers: 271,
        range: 14334,
        engines: 'Rolls-Royce Trent 7000',
    },
    {
        id: 'airbus-a330-900neo',
        year: 2018,
        aircraftName: 'A330-900neo',
        familyName: 'A330',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Capacidad máxima: A330-900neo',
        description:
            'Versión extendida neo del A330. Mayor capacidad (303 pasajeros) combinada con eficiencia moderna.',
        imagePath: '/images/aircraft/airbus/A330-900neo.jpg',
        keyHighlights: [
            '303 pasajeros',
            'Fuselaje 40% composite',
            'Alcance 13600 km',
            'Consumo reducido 25%',
            'Cabina presurizada a mayor altitud virtual',
        ],
        passengers: 303,
        range: 13600,
        engines: 'Rolls-Royce Trent 7000',
    },
    {
        id: 'airbus-acj330neo',
        year: 2019,
        aircraftName: 'ACJ330neo',
        familyName: 'A330',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Ultra lujoso: ACJ330neo',
        description:
            'Jet corporativo ultra-lujoso basado en A330neo. El business jet más grande del mundo con espacios ejecutivos ultra-premium.',
        imagePath: '/images/aircraft/airbus/ACJ330neo.jpg',
        keyHighlights: [
            '25 pasajeros (máxima privacía)',
            'Alcance 19260 km (vuelo 15+ horas)',
            'Ascensor ejecutivo integrado',
            'Spa y sauna opcionales',
            'La cabina más grande en su clase',
        ],
        passengers: 25,
        range: 19260,
        engines: 'Rolls-Royce Trent 7000',
    },

    // A350 Family (4 aeronaves)
    {
        id: 'airbus-a350-900',
        year: 2015,
        aircraftName: 'A350-900',
        familyName: 'A350',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Revolución composite: A350-900',
        description:
            'Primera aeronave comercial con fuselaje 50% composite. Transformó la industria con materiales avanzados y eficiencia sin precedentes.',
        imagePath: '/images/aircraft/airbus/A350-900.jpg',
        keyHighlights: [
            '352 pasajeros',
            'Fuselaje 50% composite (CFRP)',
            'Consumo 25% menor que generación anterior',
            'Alcance 15750 km',
            'Cabina de presión a 1800m virtual',
        ],
        passengers: 352,
        range: 15750,
        engines: 'Rolls-Royce Trent XWB-84',
    },
    {
        id: 'airbus-a350-1000',
        year: 2018,
        aircraftName: 'A350-1000',
        familyName: 'A350',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Mega-capacidad: A350-1000',
        description:
            'Versión extendida del A350 con 410 pasajeros. Mantiene la revolucionaria construcción composite con mayor capacidad.',
        imagePath: '/images/aircraft/airbus/A350-1000.jpg',
        keyHighlights: [
            '410 pasajeros',
            'Motores Trent XWB-97 mejorados',
            'Alcance 16700 km',
            'Mayor fuselaje, mismo consumo específico',
            'Ala optimizada para carga adicional',
        ],
        passengers: 410,
        range: 16700,
        engines: 'Rolls-Royce Trent XWB-97',
    },
    {
        id: 'airbus-a350f',
        year: 2021,
        aircraftName: 'A350F',
        familyName: 'A350',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Carguero futurista: A350F',
        description:
            'Primera versión carguero del A350 con fuselaje completamente composite. Revolucionó el transporte de carga de larga distancia.',
        imagePath: '/images/aircraft/airbus/A350F.jpg',
        keyHighlights: [
            'Capacidad 120 toneladas de carga',
            'Composite totalmente aprovechado para carga',
            'Alcance 17000 km',
            'Sistemas de carga automatizados',
            'Revolucionó cargo intercontinental',
        ],
        passengers: 0,
        range: 17000,
        engines: 'Rolls-Royce Trent XWB',
    },
    {
        id: 'airbus-acj350',
        year: 2020,
        aircraftName: 'ACJ350 XWB',
        familyName: 'A350',
        manufacturer: 'Airbus',
        category: 'Wide-body',
        event: 'Supremacía ejecutiva: ACJ350',
        description:
            'Business jet más exclusivo. Basado en A350 con tecnología composite de vanguardia y capacidades intercontinentales únicas.',
        imagePath: '/images/aircraft/airbus/ACJ350-XWB.jpg',
        keyHighlights: [
            '25 pasajeros (ultra-privado)',
            'Alcance 20550 km',
            'Materiales composite aeroespaciales',
            'Conectividad global integrada',
            'Experiencia de viaje sin precedentes',
        ],
        passengers: 25,
        range: 20550,
        engines: 'Rolls-Royce Trent XWB',
    },

    // A380 Family (1 aeronave)
    {
        id: 'airbus-a380-800',
        year: 2007,
        aircraftName: 'A380-800',
        familyName: 'A380',
        manufacturer: 'Airbus',
        category: 'Jumbo',
        event: 'El coloso: A380-800',
        description:
            'Aeronave comercial más grande del mundo. Doble cubierta completa con 850 pasajeros. Transformó los viajes de ultra-larga distancia.',
        imagePath: '/images/aircraft/airbus/A380-800.jpg',
        keyHighlights: [
            '850 pasajeros en una sola aeronave',
            'Doble cubierta completa',
            'Alcance 15400 km',
            'Motores más potentes jamás construidos',
            'Insignia de la aviación moderna',
        ],
        passengers: 850,
        range: 15400,
        engines: 'Engine Alliance GP7200 / Rolls-Royce Trent 900',
    },

    // ============================================
    // BOEING - 18 AERONAVES
    // ============================================

    // 737 Family (7 aeronaves)
    {
        id: 'boeing-737-800',
        year: 1998,
        aircraftName: '737-800',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Generación NG: 737-800',
        description:
            'Generación "Next Generation" del 737. El avión más producido de la historia con más de 5000 ejemplares vendidos.',
        imagePath: '/images/aircraft/boeing/737-800.jpg',
        keyHighlights: [
            '162 pasajeros',
            'Más de 5000 ejemplares vendidos',
            'Motor CFM56-7B mejorado',
            'Alcance 5765 km',
            'Columna vertebral de la aviación comercial',
        ],
        passengers: 162,
        range: 5765,
        engines: 'CFM International CFM56-7B',
    },
    {
        id: 'boeing-737-900er',
        year: 2001,
        aircraftName: '737-900ER',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Variante extendida NG: 737-900ER',
        description:
            'Versión alargada del 737 NG con alcance extendido. El "ER" (Extended Range) proporcionaba mayor flexibilidad operacional.',
        imagePath: '/images/aircraft/boeing/737-900ER.jpg',
        keyHighlights: [
            '178 pasajeros',
            'Alcance extendido 5600 km',
            'Tanques de combustible adicionales',
            'Compatibilidad total con 737-800',
            'Operación desde aeropuertos más remotos',
        ],
        passengers: 178,
        range: 5600,
        engines: 'CFM International CFM56-7B',
    },
    {
        id: 'boeing-737-max-7',
        year: 2018,
        aircraftName: '737 MAX 7',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Nueva era: 737 MAX 7',
        description:
            'Primera generación MAX del 737 con motores LEAP más eficientes. Marca un nuevo estándar de consumo de combustible (-20%).',
        imagePath: '/images/aircraft/boeing/737-MAX-7.jpg',
        keyHighlights: [
            '153 pasajeros',
            'Motor CFM LEAP-1B',
            'Consumo 20% menor vs NG',
            'Alcance 7040 km',
            'Aviónica digital completamente actualizada',
        ],
        passengers: 153,
        range: 7040,
        engines: 'CFM International LEAP-1B',
    },
    {
        id: 'boeing-737-max-8',
        year: 2017,
        aircraftName: '737 MAX 8',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Estándar MAX: 737 MAX 8',
        description:
            'Modelo principal de la serie MAX. Tras resolución de grounding (2020), se convirtió en el narrow-body más moderno de Boeing.',
        imagePath: '/images/aircraft/boeing/737-MAX-8.jpg',
        keyHighlights: [
            '178 pasajeros',
            'Certificación EASA/FAA completada 2020',
            'Alcance 6480 km',
            'Sistema MCAS optimizado',
            'Confianza restaurada tras 2 años',
        ],
        passengers: 178,
        range: 6480,
        engines: 'CFM International LEAP-1B',
    },
    {
        id: 'boeing-737-max-9',
        year: 2018,
        aircraftName: '737 MAX 9',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Capacidad MAX: 737 MAX 9',
        description:
            'Versión extendida del MAX 8 con fuselaje más largo. Proporciona 193 pasajeros en configuración de dos clases.',
        imagePath: '/images/aircraft/boeing/737-MAX-9.jpg',
        keyHighlights: [
            '193 pasajeros',
            'Fuselaje 45cm más largo',
            'Alcance 6110 km',
            'Mayor flexibilidad de ingresos',
            'Compatible con infraestructura MAX',
        ],
        passengers: 193,
        range: 6110,
        engines: 'CFM International LEAP-1B',
    },
    {
        id: 'boeing-737-max-10',
        year: 2020,
        aircraftName: '737 MAX 10',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Límite del narrow-body: 737 MAX 10',
        description:
            'Versión máxima del 737 MAX. Límite de alargamiento del fuselaje historic. Directamente competidor del A321XLR.',
        imagePath: '/images/aircraft/boeing/737-MAX-10.jpg',
        keyHighlights: [
            '204 pasajeros',
            'Máximo alargamiento posible (3.76m más)',
            'Alcance 5740 km',
            'Competencia directa a Airbus',
            'Punto final de la serie 737 clásica',
        ],
        passengers: 204,
        range: 5740,
        engines: 'CFM International LEAP-1B',
    },
    {
        id: 'boeing-bbj-737-9',
        year: 2017,
        aircraftName: 'BBJ 737-9',
        familyName: '737',
        manufacturer: 'Boeing',
        category: 'Narrow-body',
        event: 'Lujo compacto: BBJ 737-9',
        description:
            'Boeing Business Jet basado en 737 MAX. Cabina ultra-lujosa para ejecutivos con alcance intercontinental de 11110 km.',
        imagePath: '/images/aircraft/boeing/BBJ-737-9.jpg',
        keyHighlights: [
            '19 pasajeros (máximo confort)',
            'Alcance 11110 km',
            'Cabina completamente personalizable',
            'Suite privada de descanso',
            'Conectividad ejecutiva integrada',
        ],
        passengers: 19,
        range: 11110,
        engines: 'CFM International LEAP-1B',
    },

    // 747 Family (1 aeronave)
    {
        id: 'boeing-747-8-freighter',
        year: 2011,
        aircraftName: '747-8 Freighter',
        familyName: '747',
        manufacturer: 'Boeing',
        category: 'Jumbo',
        event: 'Último Jumbo: 747-8 Freighter',
        description:
            'Última generación del icónico 747. Versión carguero con capacidad de 140 toneladas. El "jumbo" original se retira progresivamente.',
        imagePath: '/images/aircraft/boeing/747-8-Freighter.jpg',
        keyHighlights: [
            'Capacidad 140 toneladas de carga',
            'Motores GE más potentes (GEnx)',
            'Alcance 13450 km (con carga total)',
            'Estructura completamente rediseñada',
            'Fin de una era de 50 años',
        ],
        passengers: 0,
        range: 13450,
        engines: 'General Electric GEnx-2B67',
    },

    // 767 Family (1 aeronave)
    {
        id: 'boeing-767-300f',
        year: 1995,
        aircraftName: '767-300F',
        familyName: '767',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Carguero de ruta: 767-300F',
        description:
            'Versión carguero del 767-300. Cabina de carga totalmente reconfigurable. Perfecta para rutas de mediano alcance con carga general.',
        imagePath: '/images/aircraft/boeing/767-300F-carguero.jpg',
        keyHighlights: [
            'Capacidad 62-79 toneladas',
            'Piso principal completamente abierto',
            'Alcance 6025 km con carga',
            'Máquinas de carga automatizadas',
            'Estándar en rutas regionales de carga',
        ],
        passengers: 0,
        range: 6025,
        engines:
            'Pratt & Whitney PW4000 / General Electric CF6 / Rolls-Royce RB211',
    },

    // 777 Family (2 aeronaves)
    {
        id: 'boeing-777-300er',
        year: 2003,
        aircraftName: '777-300ER',
        familyName: '777',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Primera clase: 777-300ER',
        description:
            'El "Triple Seven" extendido. Motor GE90-115B más potente jamás instalado en comercial. Alcance 13650 km con 365 pasajeros.',
        imagePath: '/images/aircraft/boeing/777-300ER.jpg',
        keyHighlights: [
            '365 pasajeros',
            'Motor GE90-115B (115,300 lbf empuje)',
            'Alcance 13650 km',
            'Cabina de aviónica digital completa',
            'Eficiencia incomparable en wide-body',
        ],
        passengers: 365,
        range: 13650,
        engines: 'General Electric GE90-115B',
    },
    {
        id: 'boeing-777f',
        year: 2009,
        aircraftName: '777F',
        familyName: '777',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Carguero premium: 777F',
        description:
            'Versión carguero del 777 con capacidad de 103-134 toneladas. Fuselaje robusto con piso reforzado. Estándar en carga intercontinental.',
        imagePath: '/images/aircraft/boeing/777F-carguero.jpg',
        keyHighlights: [
            'Capacidad 103-134 toneladas de carga',
            'Motor GE90-115B mantiene potencia',
            'Alcance 9200 km (con carga máxima)',
            'Piso de carga de titanio',
            'Estándar oro en cargo intercontinental',
        ],
        passengers: 0,
        range: 9200,
        engines: 'General Electric GE90-115B',
    },

    // 777X Family (3 aeronaves)
    {
        id: 'boeing-777-8',
        year: 2022,
        aircraftName: '777-8',
        familyName: '777X',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Alas plegables: 777-8',
        description:
            'Nueva generación X del 777 con alas plegables para encajar en gates estándar. Motores GE9X aún más potentes. Primera entrega 2022.',
        imagePath: '/images/aircraft/boeing/777-8.jpg',
        keyHighlights: [
            '395 pasajeros',
            'Alas plegables de 12 metros',
            'Motor GE9X (110,000+ lbf)',
            'Alcance 16190 km',
            'Nuevos materiales composite (11%)',
        ],
        passengers: 395,
        range: 16190,
        engines: 'General Electric GE9X',
    },
    {
        id: 'boeing-777-9',
        year: 2026,
        aircraftName: '777-9',
        familyName: '777X',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Futuro próximo: 777-9',
        description:
            'Versión extendida del 777X con fuselaje 6.1m más largo. Máxima capacidad (426 pasajeros) con alas plegables. Entrega estimada 2026.',
        imagePath: '/images/aircraft/boeing/777-9-777X.jpg',
        keyHighlights: [
            '426 pasajeros',
            'Máximo alargamiento del fuselaje',
            'Motores GE9X optimizados',
            'Alcance 13500 km',
            'Presurización a 1600m virtual',
            'Certificación completada 2024',
        ],
        passengers: 426,
        range: 13500,
        engines: 'General Electric GE9X',
    },
    {
        id: 'boeing-bbj-777-9',
        year: 2026,
        aircraftName: 'BBJ 777-9',
        familyName: '777X',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Megajuguete corporativo: BBJ 777-9',
        description:
            'Business jet basado en 777-9 para ultra-billonarios. Cabina más lujosa jamás creada. Alcance 20420 km para viajes globales sin escalas.',
        imagePath: '/images/aircraft/boeing/BBJ-777-9-777X.jpg',
        keyHighlights: [
            '75 pasajeros (ultra-privacidad)',
            'Alcance 20420 km (viaje 20+ horas)',
            'Ascensor de cubierta doble',
            'Cine IMAX integrado',
            'La cabina más grande jamás construida',
        ],
        passengers: 75,
        range: 20420,
        engines: 'General Electric GE9X',
    },

    // 787 Family (4 aeronaves)
    {
        id: 'boeing-787-8',
        year: 2011,
        aircraftName: '787-8 Dreamliner',
        familyName: '787',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Dreamliner original: 787-8',
        description:
            'Primera aeronave con fuselaje 100% composite. Revolucionó la aviación con 50% más eficiencia energética. Primer vuelo 2011.',
        imagePath: '/images/aircraft/boeing/787-8-Dreamliner.jpg',
        keyHighlights: [
            '248 pasajeros',
            'Fuselaje 100% composite',
            'Consumo 20% inferior a generación anterior',
            'Alcance 13530 km',
            'Cabina presurizada a 1800m virtual',
        ],
        passengers: 248,
        range: 13530,
        engines: 'General Electric GEnx-1B / Rolls-Royce Trent 1000',
    },
    {
        id: 'boeing-787-9',
        year: 2014,
        aircraftName: '787-9 Dreamliner',
        familyName: '787',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Dreamliner alargado: 787-9',
        description:
            'Versión extendida del 787-8 con 20% más capacidad. 296 pasajeros en configuración típica. Competidor directo del A350-900.',
        imagePath: '/images/aircraft/boeing/787-9-Dreamliner.jpg',
        keyHighlights: [
            '296 pasajeros',
            'Fuselaje 6m más largo que 787-8',
            'Alcance 14010 km',
            'Capacidad de carga aumentada',
            'Cabina "Dreamliner Experience"',
        ],
        passengers: 296,
        range: 14010,
        engines: 'General Electric GEnx-1B / Rolls-Royce Trent 1000',
    },
    {
        id: 'boeing-787-10',
        year: 2017,
        aircraftName: '787-10 Dreamliner',
        familyName: '787',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Máxima extensión: 787-10',
        description:
            'Versión más larga del Dreamliner (9.3m más que 787-8). 336 pasajeros. Límite máximo de alargamiento del fuselaje composite.',
        imagePath: '/images/aircraft/boeing/787-10-Dreamliner.jpg',
        keyHighlights: [
            '336 pasajeros',
            'Máximo alargamiento del fuselaje (3.1%)',
            'Alcance 11730 km',
            'Capacidad casi comparable a A350',
            'Estructura composite optimizada',
        ],
        passengers: 336,
        range: 11730,
        engines: 'General Electric GEnx-1B / Rolls-Royce Trent 1000',
    },
    {
        id: 'boeing-bbj-787-9',
        year: 2011,
        aircraftName: 'BBJ 787-9',
        familyName: '787',
        manufacturer: 'Boeing',
        category: 'Wide-body',
        event: 'Ejecutivo futurista: BBJ 787-9',
        description:
            'Business jet basado en 787-9 Dreamliner. Combina lujo corporativo con tecnología composite de vanguardia. Alcance intercontinental sin límites.',
        imagePath: '/images/aircraft/boeing/BBJ-787-9.jpg',
        keyHighlights: [
            '25 pasajeros (configuración ejecutiva)',
            'Alcance 17550 km (vuelo 18+ horas)',
            'Cabina 100% composite',
            'Presurización mejorada hasta 1600m',
            'Conectividad satelital integrada',
        ],
        passengers: 25,
        range: 17550,
        engines: 'General Electric GEnx-1B / Rolls-Royce Trent 1000',
    },
];

export const getEventsByFamily = (familyName: string): FamilyEvolutionEvent[] => {
    return familyHistoryData.filter(
        (event) => event.familyName.toUpperCase() === familyName.toUpperCase()
    );
};

export const getEventsByManufacturer = (
    manufacturer: 'Airbus' | 'Boeing'
): FamilyEvolutionEvent[] => {
    return familyHistoryData.filter((event) => event.manufacturer === manufacturer);
};

export const getEventsByYearRange = (
    startYear: number,
    endYear: number
): FamilyEvolutionEvent[] => {
    return familyHistoryData.filter(
        (event) => event.year >= startYear && event.year <= endYear
    );
};

export const getUniqueFamilies = (): string[] => {
    const families = new Set(familyHistoryData.map((event) => event.familyName));
    return Array.from(families).sort();
};

export const getUniqueYears = (): number[] => {
    const years = new Set(familyHistoryData.map((event) => event.year));
    return Array.from(years).sort((a, b) => a - b);
};

export default familyHistoryData;
