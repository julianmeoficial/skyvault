/**
 * Datos curiosos de aeronaves para SkyVault
 * CLAVES NORMALIZADAS: lowercase, sin "Boeing/Airbus", espacios → guiones, sin "neo/dreamliner/ceo"
 *
 * ÉNFASIS ESPECIAL: Familia Airbus A350
 */

export interface AircraftFunFacts {
    fuelSaving: string;
    airQuality: string;
    components: string;
    operators: string[];
    curiosities: string;
}

export const funFactsData: Record<string, AircraftFunFacts> = {
    // ========================================
    // FAMILIA A350 — MÁXIMA PRIORIDAD
    // ========================================

    "a350-900": { // BD: "A350-900neo" → normaliza a "a350-900"
        fuelSaving: "25% menos consumo de combustible que aeronaves de generación anterior. Es 20 toneladas más ligero que el Boeing 777-300ER gracias a sus materiales compuestos avanzados.",
        airQuality: "Cabina presurizada a 1,800 metros (equivalente a 6,000 pies) en lugar de los 2,400m típicos, reduciendo la fatiga. Mayor humedad en cabina que aeronaves tradicionales y sistema de filtración grado hospitalario con 8 zonas de temperatura independientes.",
        components: "53% materiales compuestos de fibra de carbono, 19% aleaciones de titanio, 14% aleaciones de aluminio avanzadas. Es el primer Airbus con más del 50% de composites.",
        operators: ["Qatar Airways (cliente de lanzamiento 2015)", "Singapore Airlines", "Cathay Pacific", "Delta Air Lines", "Lufthansa", "Iberia", "Air France", "LATAM"],
        curiosities: "Las ventanas tienen un diseño distintivo tipo 'máscara de mapache' y son las más grandes de cualquier aeronave comercial, ofreciendo 30% más de superficie visual. Las alas tienen curvatura variable que se ajusta automáticamente durante cada fase del vuelo, optimizando la eficiencia aerodinámica."
    },

    "a350-1000": { // BD: "A350-1000neo" → normaliza a "a350-1000"
        fuelSaving: "25% menos consumo de combustible que aeronaves de generación anterior, con una eficiencia operacional superior gracias a su diseño aerodinámico y motores Trent XWB-97 de última generación.",
        airQuality: "Presurización a 1,800 metros para máximo confort. Sistema de filtración grado hospitalario con 8 zonas de temperatura independientes. Cabina más silenciosa de su categoría con mayor humedad ambiental para vuelos de largo alcance.",
        components: "53% materiales compuestos de fibra de carbono, 19% aleaciones de titanio, 14% aleaciones de aluminio avanzadas. Ancho de cabina de 5.61m con altura de 2.20-2.43m, ofreciendo un espacio interior superior.",
        operators: ["Qatar Airways", "Singapore Airlines", "Cathay Pacific", "British Airways", "Virgin Atlantic", "Etihad Airways"],
        curiosities: "Ventanas tipo 'máscara de mapache' con 30% más de superficie que otros widebodies. Las alas con perfil de curvatura variable se adaptan automáticamente en cada fase del vuelo, reduciendo resistencia y consumo. Es el avión de largo alcance más avanzado tecnológicamente en servicio."
    },

    "a350f": { // BD: "A350F" → normaliza a "a350f"
        fuelSaving: "25% menos consumo de combustible comparado con cargueros de generación anterior. Tecnología ecológica heredada del A350-900 comercial con optimización para carga.",
        airQuality: "Aunque es carguero, mantiene sistemas avanzados de ventilación y presurización para tripulación, con filtración HEPA y confort superior en cabina de mando.",
        components: "53% materiales compuestos de fibra de carbono, 19% aleaciones de titanio, 14% aleaciones de aluminio avanzadas. Estructura optimizada para cargas pesadas con máxima resistencia y mínimo peso.",
        operators: ["Singapore Airlines Cargo", "Air France-KLM Cargo", "CMA CGM Air Cargo", "Etihad Cargo"],
        curiosities: "Primer carguero widebody con más del 50% de materiales compuestos. Alcance de 18,000 km con carga máxima, permitiendo rutas transcontinentales sin escalas. Puertas de carga optimizadas para pallets y contenedores estándar."
    },

    "acj350-xwb": { // BD: "ACJ350 XWB" → normaliza a "acj350-xwb"
        fuelSaving: "25% menos consumo de combustible que jets ejecutivos de generación anterior. Eficiencia sin comprometer el lujo, con 22 horas de vuelo continuo.",
        airQuality: "Sistema de filtración grado hospitalario con 8+ zonas de temperatura independientes. Presurización a 1,800 metros y mayor humedad en cabina para máximo confort en vuelos ultra largos. La aeronave VIP widebody más silenciosa del mercado.",
        components: "53% materiales compuestos de fibra de carbono, 19% aleaciones de titanio. Hasta 308 m² de espacio en cabina (equivalente a una casa grande) totalmente personalizable.",
        operators: ["Gobiernos", "Corporaciones privadas", "VIPs de alto perfil"],
        curiosities: "Alcance de 20,550 km - puede volar 22 horas sin escalas. 12 años hasta primera revisión estructural (vs 6 años en competencia). Capacidad para 25-50 pasajeros en configuración VIP de lujo con dormitorios, oficinas, salas de reuniones y hasta spas privados."
    },

    // ========================================
    // FAMILIA A220
    // ========================================

    "a220-100": { // BD: "A220-100" → normaliza a "a220-100"
        fuelSaving: "25% más eficiente que aeronaves de generación anterior en su categoría gracias a los motores Pratt & Whitney PW1500G Geared Turbofan.",
        airQuality: "Ventanas más grandes de su clase en cada fila de asientos. Sistema de iluminación LED de ambiente con múltiples colores. Renovación de aire cada 2-3 minutos con filtración HEPA.",
        components: "Materiales compuestos en estructura de alas y empenaje. Motores GTF de última generación con tecnología de engranaje reductor que reduce ruido y consumo.",
        operators: ["airBaltic", "Air Canada", "Swiss Airlines", "Delta Air Lines", "JetBlue Airways", "Breeze Airways"],
        curiosities: "Diseñado originalmente por Bombardier como CSeries, fue adquirido por Airbus en 2018. Es el avión narrow-body más silencioso de su clase, con 50% menos ruido que competidores."
    },

    "a220-300": { // BD: "A220-300" → normaliza a "a220-300"
        fuelSaving: "25% más eficiente que aeronaves de generación anterior. Motor PW1500G GTF reduce consumo mediante tecnología de engranaje reductor.",
        airQuality: "Cabina Airspace (nuevo estándar desde 2026 con Air Canada). Compartimentos superiores XL con 20% más volumen para equipaje. Ventanas más grandes de su clase.",
        components: "Materiales compuestos en estructura. Ancho de cabina de 3.28m con altura de 2.11m. Iluminación LED dinámica con ambiente personalizable.",
        operators: ["Air Canada", "airBaltic (mayor operador)", "Delta Air Lines", "JetBlue Airways", "Swiss Airlines", "Breeze Airways"],
        curiosities: "Versión estirada del A220-100 con 27 asientos adicionales. Air Canada será el cliente de lanzamiento de la nueva cabina Airspace en 2026. Es considerado el avión de pasillo único más avanzado tecnológicamente."
    },

    "acj-twotwenty": { // BD: "ACJ TwoTwenty" → normaliza a "acj-twotwenty"
        fuelSaving: "25% más eficiente que jets ejecutivos de generación anterior en su categoría. Alcance de 10,465 km con consumo optimizado para operaciones VIP.",
        airQuality: "Cabina totalmente personalizable con las ventanas más grandes de su clase. Sistema de iluminación LED programable, filtración HEPA y zonas de temperatura independientes.",
        components: "Materiales compuestos avanzados. Hasta 73 m² de espacio en cabina con configuración flexible para 12 pasajeros VIP en máximo confort.",
        operators: ["Ejecutivos corporativos", "Gobiernos", "Clientes privados de ultra-alto patrimonio"],
        curiosities: "Versión business jet del A220. Único narrow-body VIP con tecnología GTF. Puede operar en aeropuertos con pistas cortas, ofreciendo acceso a destinos exclusivos inaccesibles para otros jets ejecutivos."
    },

    // ========================================
    // FAMILIA A320neo
    // ========================================

    "a319": { // BD: "A319neo" → normaliza a "a319" (quita "neo")
        fuelSaving: "15-20% menos consumo vs generación anterior A319ceo. 50% menos ruido gracias a motores LEAP-1A o PW1100G-JM de nueva generación.",
        airQuality: "Cabina más ancha de aviones de pasillo único (3.70m). Sistema de iluminación LED dinámica. Renovación de aire cada 2-3 minutos con filtración HEPA.",
        components: "15% de materiales compuestos en superficies de vuelo (pionero en su uso). Estructura de aluminio avanzado con winglets optimizados.",
        operators: ["Wizz Air", "Frontier Airlines", "easyJet", "Avianca", "Volaris"],
        curiosities: "Es el miembro más pequeño de la familia A320neo, ideal para rutas de menor demanda. Única opción de carga containerizada en aviones de pasillo único, facilitando operaciones logísticas."
    },

    "a320": { // BD: "A320neo" → normaliza a "a320"
        fuelSaving: "15-20% menos consumo vs generación anterior A320ceo. Motores LEAP-1A o PW1100G-JM de última generación con tecnología de alta eficiencia.",
        airQuality: "Cabina más ancha de aviones de pasillo único (3.70m). Asientos de 47cm de ancho en clase económica. Iluminación LED dinámica y filtración HEPA.",
        components: "15% de materiales compuestos en superficies de vuelo. Winglets Sharklet de fibra de carbono que reducen resistencia aerodinámica.",
        operators: ["IndiGo (mayor operador A320neo)", "American Airlines", "China Southern Airlines", "Wizz Air", "Spirit Airlines", "Avianca", "LATAM", "Volaris", "Viva Aerobus"],
        curiosities: "Familia A320 completa tiene más de 18,000 pedidos - el avión de fuselaje estrecho más vendido del mundo. Opción de carga containerizada única en single-aisle."
    },

    "a321lr": { // BD: "A321LR" → normaliza a "a321lr"
        fuelSaving: "15-20% menos consumo vs generación anterior. Tanques de combustible adicionales permiten alcance extendido de 7,400 km sin sacrificar eficiencia.",
        airQuality: "Cabina Airspace con compartimentos superiores XL. Cabina más ancha de su clase (3.70m). Iluminación LED personalizable y renovación de aire constante con HEPA.",
        components: "15% de materiales compuestos en superficies de vuelo. Estructura reforzada para tanques adicionales de combustible. Winglets Sharklet optimizados.",
        operators: ["TAP Air Portugal", "Aer Lingus", "Arkia Israeli Airlines", "La Compagnie (todo business class)"],
        curiosities: "Versión de alcance extendido del A321neo con tanque adicional central. Permite rutas transatlánticas en narrow-body, revolucionando la conectividad intercontinental de punto a punto."
    },

    "a321": { // BD: "A321neo" → normaliza a "a321"
        fuelSaving: "15-20% menos consumo vs A321ceo de generación anterior. 50% menos ruido. Motores LEAP-1A o PW1100G-JM de última generación.",
        airQuality: "Cabina más ancha de aviones de pasillo único (3.70m). Asientos de 47cm de ancho. Compartimentos superiores optimizados. Iluminación LED y HEPA.",
        components: "15% de materiales compuestos en superficies de vuelo. Estructura de aluminio avanzado. Winglets Sharklet de nueva generación.",
        operators: ["American Airlines", "China Southern Airlines", "IndiGo", "Wizz Air", "Spirit Airlines", "Frontier Airlines", "Avianca", "LATAM", "Viva Aerobus"],
        curiosities: "El miembro más grande de la familia A320, con 185 asientos en configuración típica. Es el narrow-body más popular para rutas de alta densidad en mercados emergentes."
    },

    "a321xlr": { // BD: "A321XLR" → normaliza a "a321xlr"
        fuelSaving: "15-20% menos consumo vs generación anterior. Optimización aerodinámica y tanques de largo alcance permiten eficiencia máxima en rutas de hasta 8,700 km.",
        airQuality: "Cabina Airspace con los compartimentos superiores más grandes de su clase. Iluminación LED dinámica. Filtración HEPA continua. Cabina de 3.70m de ancho.",
        components: "15% de materiales compuestos en superficies de vuelo. Tanque de combustible trasero adicional (RCT) de diseño exclusivo. Estructura reforzada para largo alcance.",
        operators: ["Iberia (cliente de lanzamiento, 2024)", "Aer Lingus", "United Airlines", "American Airlines", "Qantas", "IndiGo"],
        curiosities: "Alcance de 8,700 km - puede conectar cualquier par de ciudades del mundo en single-aisle. Primer narrow-body verdaderamente transatlántico y transpacífico. Revoluciona la aviación al eliminar necesidad de widebodies en muchas rutas."
    },

    "acj320": { // BD: "ACJ320neo" → normaliza a "acj320"
        fuelSaving: "15-20% menos consumo vs jets ejecutivos de generación anterior en su categoría. Alcance de 11,100 km con eficiencia operacional superior.",
        airQuality: "Cabina totalmente personalizable con iluminación LED dinámica. Filtración HEPA y zonas de temperatura independientes. Cabina más ancha de su clase (3.70m).",
        components: "15% de materiales compuestos en superficies de vuelo. Estructura reforzada para configuración VIP. Winglets Sharklet optimizados.",
        operators: ["Gobiernos", "Corporaciones", "Ejecutivos de ultra-alto patrimonio", "Clientes privados"],
        curiosities: "Versión business jet del A320neo. Configuración típica para 8 pasajeros VIP con dormitorio privado, oficina, sala de conferencias y baño completo. Puede operar en aeropuertos con restricciones de ruido."
    },

    // ========================================
    // FAMILIA A330neo
    // ========================================

    "a330-200f": { // BD: "A330-200F" → normaliza a "a330-200f"
        fuelSaving: "10-15% más eficiente que cargueros de generación anterior en su categoría. Motores PW4000 o Trent 700 optimizados para operaciones de carga.",
        airQuality: "Sistemas de ventilación y presurización avanzados para tripulación. Cabina de mando con ergonomía mejorada y filtración de aire.",
        components: "Estructura de aluminio reforzada para cargas pesadas. Puertas de carga optimizadas para contenedores y pallets estándar.",
        operators: ["FedEx Express", "DHL Aviation", "Turkish Cargo", "MasAir", "Etihad Cargo"],
        curiosities: "Versión carguero puro del A330-200. Capacidad de carga de hasta 70 toneladas con alcance de 7,400 km. Puertas de carga lateral y frontal para carga rápida y eficiente."
    },

    "a330-800": { // BD: "A330-800neo" → normaliza a "a330-800"
        fuelSaving: "25% menos consumo por asiento vs generación anterior A330ceo. Nuevos winglets con extensión de fibra de carbono optimizan eficiencia aerodinámica.",
        airQuality: "Cabina Airspace con compartimentos superiores 66% más grandes. Iluminación LED de ambiente. Ventanas electro-oscurecibles en business class. Filtración HEPA.",
        components: "Extensión de ala exterior en CFRP (fibra de carbono reforzada). Envergadura de 64m. Diseño optimizado con CFD 3D. Paneles laterales 100kg más ligeros.",
        operators: ["Kuwait Airways", "Uganda Airlines", "Air Greenland", "Garuda Indonesia"],
        curiosities: "Versión más pequeña del A330neo con alcance excepcional de 14,334 km. Es el widebody más eficiente para rutas de largo alcance con menor densidad de pasajeros."
    },

    "a330-900": { // BD: "A330-900neo" → normaliza a "a330-900"
        fuelSaving: "25% menos consumo por asiento vs generación anterior A330ceo. Motores Trent 7000 de Rolls-Royce con máxima eficiencia operacional.",
        airQuality: "Cabina Airspace con compartimentos superiores 66% más grandes. Iluminación LED dinámica. Ventanas electro-oscurecibles en business class. Renovación de aire constante con HEPA.",
        components: "Extensión de ala exterior en CFRP (fibra de carbono). Winglets optimizados con diseño CFD 3D. Envergadura de 64m. Paneles laterales 100kg más ligeros.",
        operators: ["TAP Air Portugal (mayor operador)", "Delta Air Lines", "Condor", "Azul Brazilian Airlines", "Air Mauritius", "Garuda Indonesia"],
        curiosities: "Versión más popular del A330neo. TAP Air Portugal es el mayor operador con 41 unidades. Combina eficiencia de nueva generación con costos operacionales más bajos que el A350 para rutas de mediano alcance."
    },

    "acj330": { // BD: "ACJ330neo" → normaliza a "acj330"
        fuelSaving: "25% menos consumo vs jets ejecutivos widebody de generación anterior. Alcance de 19,260 km con eficiencia operacional excepcional.",
        airQuality: "Cabina VIP totalmente personalizable con filtración HEPA. Ventanas electro-oscurecibles. Iluminación LED programable. Zonas de temperatura independientes. Máximo confort en vuelos ultra largos.",
        components: "Extensión de ala exterior en CFRP. Estructura optimizada para configuración VIP. Hasta 218 m² de espacio en cabina con diseño a medida.",
        operators: ["Gobiernos", "Corporaciones internacionales", "Realeza", "VIPs de ultra-alto patrimonio"],
        curiosities: "Versión business jet del A330neo. Alcance de 19,260 km permite vuelos sin escalas entre cualquier par de ciudades del mundo. Configuración típica para 25 pasajeros VIP con múltiples dormitorios, oficinas, salas de cine y spas."
    },

    // ========================================
    // FAMILIA A380
    // ========================================

    "a380-800": { // BD: "A380-800" → normaliza a "a380-800"
        fuelSaving: "15% más eficiente por asiento que el Boeing 747-400. Sin embargo, requiere alta ocupación para ser rentable, lo que limitó su éxito comercial.",
        airQuality: "Dos pisos completos con 550 m² de espacio utilizable. Sistemas de ventilación independientes por piso. Filtración HEPA en toda la cabina. Menos turbulencias gracias a su masa.",
        components: "40% materiales compuestos y metales modernos. 575 toneladas de peso máximo al despegue. Más de 4 millones de componentes de 1,500 compañías en 30 países.",
        operators: ["Emirates (116 unidades - mayor operador)", "Singapore Airlines (cliente de lanzamiento 2007)", "Qantas", "British Airways", "Lufthansa", "Korean Air"],
        curiosities: "Envergadura de 79.8m (tamaño de piscina olímpica). Muchos aeropuertos tuvieron que reconstruir pistas para soportar su peso. Capacidad de hasta 853 pasajeros en configuración máxima (típicamente 525-575 en 3 clases). Producción finalizada en 2021. Emirates opera casi la mitad de todos los A380 construidos."
    },

    // ========================================
    // FAMILIA BOEING 737
    // ========================================

    "737-max-7": { // BD: "Boeing 737 MAX 7" → normaliza a "737-max-7"
        fuelSaving: "14-20% menos consumo vs 737NG de generación anterior. Motor CFM LEAP-1B con tecnología de alta eficiencia. 40% menos ruido.",
        airQuality: "Cabina Boeing Sky Interior con iluminación LED dinámica. Compartimentos superiores más grandes con espacio para más equipaje. Filtración HEPA y renovación de aire constante.",
        components: "Estructura de aluminio avanzado con winglets cónicos. Motor LEAP-1B de última generación con álabes de fibra de carbono.",
        operators: ["Southwest Airlines", "United Airlines", "WestJet"],
        curiosities: "Versión más pequeña del 737 MAX, con menor capacidad pero mayor alcance (7,040 km). Ideal para rutas de baja densidad y largo alcance. Compite directamente con el Airbus A220-300."
    },

    "737-max-8": { // BD: "Boeing 737 MAX 8" → normaliza a "737-max-8"
        fuelSaving: "14-20% menos consumo vs 737-800 NG. Motor CFM LEAP-1B optimizado. 40% menos ruido que generación anterior.",
        airQuality: "Cabina Boeing Sky Interior con iluminación LED programable. Compartimentos superiores optimizados. Ventanas más grandes. Filtración HEPA.",
        components: "Estructura de aluminio avanzado. Winglets cónicos que reducen resistencia. Motor LEAP-1B con álabes de fibra de carbono.",
        operators: ["Southwest Airlines (mayor operador 737)", "Ryanair (mayor pedido MAX en Europa)", "United Airlines", "American Airlines", "Alaska Airlines", "Copa Airlines", "Gol", "Aeromexico"],
        curiosities: "El miembro más popular de la familia 737 MAX. Ryanair tiene un pedido masivo de más de 200 unidades. Reemplaza al exitoso 737-800, el narrow-body más producido de Boeing."
    },

    "737-max-9": { // BD: "Boeing 737 MAX 9" → normaliza a "737-max-9"
        fuelSaving: "14-20% menos consumo vs 737-900ER NG. Motor CFM LEAP-1B de última generación. 40% menos ruido.",
        airQuality: "Cabina Boeing Sky Interior con iluminación LED dinámica y compartimentos superiores optimizados. Ventanas más grandes. Filtración HEPA y renovación de aire constante.",
        components: "Estructura de aluminio reforzada para mayor capacidad. Winglets cónicos optimizados. Motor LEAP-1B con tecnología de álabes de fibra de carbono.",
        operators: ["United Airlines", "Alaska Airlines", "American Airlines", "Copa Airlines"],
        curiosities: "Versión estirada del 737 MAX 8 con capacidad para 193 pasajeros. Alcance de 6,110 km. Compite con el Airbus A321neo en rutas de alta densidad."
    },

    "737-max-10": { // BD: "Boeing 737 MAX 10" → normaliza a "737-max-10"
        fuelSaving: "14-20% menos consumo vs 737-900ER. Motor CFM LEAP-1B optimizado para mayor capacidad. 40% menos ruido.",
        airQuality: "Cabina Boeing Sky Interior con iluminación LED y compartimentos superiores XL. Ventanas más grandes. Filtración HEPA continua.",
        components: "Estructura de aluminio reforzada. Tren de aterrizaje mejorado con sistema de levante para despegue y aterrizaje. Winglets cónicos.",
        operators: ["United Airlines", "Southwest Airlines (futuro operador)", "Ryanair (futuro)"],
        curiosities: "El miembro más grande de la familia 737 MAX con 204 asientos. Tren de aterrizaje innovador con sistema de levante para evitar contacto de cola en despegue/aterrizaje. Compite directamente con el Airbus A321neo."
    },

    "737-800": { // BD: "Boeing 737-800" → normaliza a "737-800"
        fuelSaving: "8-10% más eficiente que el 737-700 de generación anterior. Motor CFM56-7B optimizado para operaciones de corto y mediano alcance.",
        airQuality: "Cabina con iluminación mejorada y compartimentos superiores optimizados. Filtración de aire estándar con renovación cada 3-4 minutos.",
        components: "Estructura de aluminio tradicional con winglets blended. Motor CFM56-7B, uno de los motores más confiables de la aviación comercial.",
        operators: ["Southwest Airlines", "Ryanair", "American Airlines", "United Airlines", "Alaska Airlines", "Copa Airlines", "Gol", "Aeromexico"],
        curiosities: "El avión narrow-body más producido de Boeing con más de 5,000 unidades entregadas. Southwest Airlines opera la mayor flota de 737-800 del mundo. Es extremadamente confiable y popular entre aerolíneas de bajo costo."
    },

    "737-900er": { // BD: "Boeing 737-900ER" → normaliza a "737-900er"
        fuelSaving: "5-8% más eficiente que el 737-800 por asiento gracias a mayor capacidad. Motor CFM56-7B optimizado.",
        airQuality: "Cabina con iluminación mejorada y compartimentos superiores. Ventanas más grandes que generaciones anteriores. Filtración de aire estándar.",
        components: "Estructura de aluminio reforzada para mayor longitud. Winglets blended. Motor CFM56-7B con tecnología probada.",
        operators: ["United Airlines", "Alaska Airlines", "Lion Air", "Garuda Indonesia"],
        curiosities: "Versión estirada del 737-800 con capacidad para 178 pasajeros. Fue el precursor del 737 MAX 9. United Airlines es el mayor operador con más de 100 unidades."
    },

    "bbj-737-9-max": { // BD: "BBJ 737-9 MAX" → normaliza a "bbj-737-9-max"
        fuelSaving: "14-20% menos consumo vs BBJ de generación anterior. Motor CFM LEAP-1B optimizado para operaciones VIP de largo alcance.",
        airQuality: "Cabina VIP totalmente personalizable con iluminación LED programable. Filtración HEPA. Zonas de temperatura independientes. Máximo confort.",
        components: "Estructura reforzada para configuración ejecutiva. Motor LEAP-1B de última generación. Tanques de combustible adicionales para alcance extendido.",
        operators: ["Gobiernos", "Corporaciones", "Ejecutivos de ultra-alto patrimonio", "Clientes privados VIP"],
        curiosities: "Versión business jet del 737 MAX 9. Alcance de 11,110 km con configuración típica para 19 pasajeros VIP. Incluye dormitorio privado, oficina, sala de conferencias y entretenimiento de lujo."
    },

    // ========================================
    // FAMILIA BOEING 747
    // ========================================

    "747-8-freighter": { // BD: "Boeing 747-8 Freighter" → normaliza a "747-8-freighter"
        fuelSaving: "16% menos consumo que el 747-400F. Motores GEnx de nueva generación más silenciosos y eficientes que los anteriores CF6.",
        airQuality: "Sistemas de ventilación y presurización mejorados para tripulación. Cabina de mando modernizada con ergonomía superior.",
        components: "Estructura de aluminio reforzada. Motores GEnx con tecnología de álabes de fibra de carbono. Último jumbo de 4 motores en producción (hasta 2023).",
        operators: ["UPS (mayor operador)", "Cargolux", "Cathay Pacific Cargo", "Korean Air Cargo", "Atlas Air"],
        curiosities: "858 m³ de volumen de carga - el carguero más grande de Boeing. Capacidad de 140 toneladas de carga máxima. Último 747 producido fue entregado en 2023, marcando el fin de una era icónica. Nariz basculante para carga frontal directa."
    },

    // ========================================
    // FAMILIA BOEING 767
    // ========================================

    "767-300f": { // BD: "Boeing 767-300F" → normaliza a "767-300f"
        fuelSaving: "10-12% más eficiente que cargueros de generación similar. Motores PW4000, GE CF6 o RB211 optimizados para operaciones de carga.",
        airQuality: "Sistemas de ventilación estándar para tripulación. Cabina de mando ergonómica con sistemas modernizados.",
        components: "Estructura de aluminio reforzada para cargas pesadas. Puertas de carga lateral optimizadas para contenedores estándar.",
        operators: ["FedEx Express (mayor operador)", "UPS", "DHL Aviation", "Amazon Air", "Atlas Air"],
        curiosities: "Versión carguero del 767-300. Capacidad de hasta 52 toneladas con alcance de 6,025 km. Muy popular entre operadores de carga exprés como FedEx y UPS. Producción continúa debido a alta demanda de e-commerce."
    },

    // ========================================
    // FAMILIA BOEING 777
    // ========================================

    "777-300er": { // BD: "Boeing 777-300ER" → normaliza a "777-300er"
        fuelSaving: "20% menos consumo que el 747-400 en rutas similares. Motores GE90-115B, los más potentes del mundo con 513 kN de empuje.",
        airQuality: "Cabina amplia con iluminación LED dinámica. Compartimentos superiores optimizados. Filtración HEPA y renovación de aire constante. Presurización mejorada.",
        components: "Estructura de aluminio avanzado con materiales compuestos en superficies de control. Motores GE90-115B con diámetro de fan de 3.43m (más grande que un fuselaje de 737).",
        operators: ["Emirates (mayor operador con 120+ unidades)", "United Airlines", "Air France", "British Airways", "Cathay Pacific", "Singapore Airlines", "ANA"],
        curiosities: "El widebody bimotor de largo alcance más exitoso de la historia. Alcance de 13,650 km permite vuelos directos entre casi cualquier par de ciudades. Emirates opera la mayor flota con más de 120 unidades. Los motores GE90-115B son los más potentes jamás montados en un avión comercial."
    },

    "777f": { // BD: "Boeing 777F" → normaliza a "777f"
        fuelSaving: "18% menos consumo que el 747-400F en rutas de carga similares. Motores GE90-115B optimizados para operaciones de carga pesada.",
        airQuality: "Sistemas de ventilación avanzados para tripulación. Cabina de mando modernizada con tecnología fly-by-wire.",
        components: "Estructura reforzada para cargas pesadas. Motores GE90-115B con 511 kN de empuje. Puertas de carga lateral optimizadas.",
        operators: ["FedEx Express", "Emirates SkyCargo", "Qatar Airways Cargo", "Cathay Pacific Cargo", "LATAM Cargo"],
        curiosities: "Versión carguero del 777-300ER. Capacidad de carga de 103 toneladas con alcance de 9,200 km. El bimotor carguero más grande del mundo. Puertas de carga de apertura lateral para carga rápida."
    },

    // ========================================
    // FAMILIA BOEING 777X
    // ========================================

    "777-8": { // BD: "Boeing 777-8" → normaliza a "777-8"
        fuelSaving: "12% menos consumo que el 777-300ER gracias a motores GE9X y diseño aerodinámico optimizado. Alas plegables reducen resistencia en tierra.",
        airQuality: "Cabina con ventanas más grandes que generación anterior. Iluminación LED dinámica. Filtración HEPA continua. Presurización mejorada con mayor humedad.",
        components: "Alas plegables de fibra de carbono (primera vez en aviación comercial) - se pliegan 3.5m en cada punta para caber en gates estándar. Motores GE9X, los más grandes y potentes del mundo.",
        operators: ["Emirates (cliente de lanzamiento)", "Qatar Airways", "Lufthansa", "Singapore Airlines"],
        curiosities: "Alas plegables revolucionarias que permiten envergadura de 72m (reducida a 65m en tierra). Alcance de 16,190 km - el bimotor de mayor alcance. Motores GE9X con diámetro de fan de 3.4m. Entró en servicio en 2022."
    },

    "777-9": { // BD: "Boeing 777-9" → normaliza a "777-9"
        fuelSaving: "12% menos consumo que el 777-300ER. Motores GE9X de última generación con máxima eficiencia. Diseño aerodinámico revolucionario.",
        airQuality: "Cabina con ventanas más grandes. Iluminación LED personalizable. Filtración HEPA continua. Presurización mejorada con mayor humedad para vuelos ultra largos.",
        components: "Alas plegables de fibra de carbono que se pliegan 3.5m en cada punta. Motores GE9X (récord de potencia con 467 kN). Estructura optimizada para máxima capacidad.",
        operators: ["Emirates (mayor cliente con 150+ pedidos)", "Lufthansa", "Qatar Airways", "ANA", "Singapore Airlines", "Cathay Pacific"],
        curiosities: "El avión bimotor comercial más grande jamás construido con capacidad para 426 pasajeros. Alas plegables permiten usar gates estándar a pesar de su envergadura récord. Motores GE9X tienen el diámetro de fan más grande de la historia (3.4m). Entrada en servicio prevista para 2026."
    },

    "bbj-777-9": { // BD: "BBJ 777-9" → normaliza a "bbj-777-9"
        fuelSaving: "12% menos consumo que jets ejecutivos widebody de generación anterior. Motores GE9X optimizados para operaciones VIP de ultra largo alcance.",
        airQuality: "Cabina VIP totalmente personalizable con las ventanas más grandes de su clase. Iluminación LED programable. Filtración HEPA. Zonas de temperatura independientes. Máximo lujo y confort.",
        components: "Alas plegables de fibra de carbono. Motores GE9X (los más potentes del mundo). Hasta 353 m² de espacio en cabina - equivalente a una mansión voladora.",
        operators: ["Gobiernos", "Realeza", "Corporaciones internacionales", "VIPs de ultra-alto patrimonio"],
        curiosities: "Alcance de 20,420 km - puede volar sin escalas entre cualquier par de ciudades del mundo. Configuración típica para 75 pasajeros VIP con múltiples dormitorios, oficinas, salas de cine, gimnasio y spa. El jet ejecutivo más grande y lujoso del mercado. Entrada en servicio prevista para 2026."
    },

    // ========================================
    // FAMILIA BOEING 787 DREAMLINER
    // ========================================

    "787-8": { // BD: "Boeing 787-8 Dreamliner" → normaliza a "787-8"
        fuelSaving: "20-25% más eficiente que aeronaves que reemplaza gracias a 50% de materiales compuestos. Motores GEnx-1B o Trent 1000 de última generación.",
        airQuality: "Presurización a 1,800m (como el A350) - menor fatiga. Humedad de cabina de 16% (vs 4% típico). Ventanas electrocrómicas 30% más grandes sin persianas mecánicas. Cambio completo de aire cada 2-3 minutos con filtración HEPA.",
        components: "50% materiales compuestos (fibra de carbono), 20% aluminio, 15% titanio, 10% acero, 5% otros. Primer Boeing con mayoría de composites.",
        operators: ["United Airlines (mayor operador)", "ANA (cliente de lanzamiento 2011)", "Japan Airlines", "American Airlines", "British Airways", "LATAM", "Avianca"],
        curiosities: "Ventanas electrocrómicas con oscurecimiento electrónico en 5 niveles sin persianas mecánicas. Iluminación LED dinámica con 16.7 millones de colores que simula amanecer y atardecer. Cabina más silenciosa gracias a materiales compuestos que amortiguan ruido."
    },

    "787-9": { // BD: "Boeing 787-9 Dreamliner" → normaliza a "787-9"
        fuelSaving: "20-25% más eficiente que aeronaves de generación anterior. 50% de materiales compuestos reduce peso y consumo. Motores GEnx-1B o Trent 1000 optimizados.",
        airQuality: "Presurización a 1,800m para máximo confort. Humedad de 16% en cabina (4x más que aviones tradicionales). Ventanas electrocrómicas 30% más grandes. Filtración HEPA con cambio de aire cada 2-3 minutos.",
        components: "50% materiales compuestos, 20% aluminio, 15% titanio, 10% acero. Estructura optimizada para mayor longitud que el 787-8.",
        operators: ["United Airlines", "ANA", "American Airlines", "British Airways", "Air Canada", "KLM", "Air New Zealand"],
        curiosities: "Versión estirada del 787-8 con 52 pasajeros adicionales. Alcance de 14,010 km permite rutas ultra largas como Perth-Londres sin escalas. Techos arqueados crean sensación de amplitud. Iluminación LED dinámica con 16.7 millones de colores."
    },

    "787-10": { // BD: "Boeing 787-10 Dreamliner" → normaliza a "787-10"
        fuelSaving: "20-25% más eficiente que generación anterior. 50% de materiales compuestos maximiza eficiencia. Motores GEnx-1B o Trent 1000 de última generación.",
        airQuality: "Presurización a 1,800m (equivalente a 6,000 pies). Humedad de cabina de 16% - 4x más que aviones tradicionales. Ventanas electrocrómicas 30% más grandes. Cambio de aire cada 2-3 minutos con HEPA.",
        components: "50% materiales compuestos (fibra de carbono), 20% aluminio, 15% titanio. El 787 más grande con estructura optimizada para alta capacidad.",
        operators: ["Singapore Airlines", "United Airlines", "British Airways", "Etihad Airways", "Air France"],
        curiosities: "El miembro más grande de la familia 787 con 336 pasajeros. Menor alcance (11,730 km) pero mayor eficiencia por asiento. Ideal para rutas de alta demanda y mediano alcance. Iluminación LED con 16.7 millones de colores simula ciclos naturales de luz."
    },

    "bbj-787-9": { // BD: "BBJ 787-9" → normaliza a "bbj-787-9"
        fuelSaving: "20-25% más eficiente que jets ejecutivos widebody de generación anterior. 50% de materiales compuestos reduce peso y consumo significativamente.",
        airQuality: "Presurización a 1,800m para mínima fatiga en vuelos largos. Humedad de cabina de 16% (vs 4% típico) - la más alta en aviación ejecutiva. Ventanas electrocrómicas 30% más grandes. Filtración HEPA continua.",
        components: "50% materiales compuestos, 20% aluminio, 15% titanio. Estructura optimizada para configuración VIP con máximo espacio personalizable.",
        operators: ["Gobiernos", "Corporaciones internacionales", "Ejecutivos de ultra-alto patrimonio", "VIPs"],
        curiosities: "Alcance de 17,550 km permite vuelos sin escalas entre casi cualquier par de ciudades. Configuración típica para 25-40 pasajeros VIP con dormitorios, oficinas, salas de cine y spas. Ventanas electrocrómicas con 5 niveles de oscurecimiento sin persianas. Iluminación LED con 16.7 millones de colores programable según preferencias."
    }
};
