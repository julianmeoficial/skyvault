package com.skyvault.backend.service;

import com.skyvault.backend.model.*;
import com.skyvault.backend.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;

@Service
@Transactional
public class DataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);
    private static final int CURRENT_DATA_VERSION = 1;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ProductionStateRepository productionStateRepository;

    @Autowired
    private SizeCategoryRepository sizeCategoryRepository;

    @Autowired
    private AircraftModelRepository aircraftModelRepository;

    @Autowired
    private SpecificationsRepository specificationsRepository;

    @Value("${skyvault.data.read-only-mode:true}")
    private boolean readOnlyMode;

    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {
        if (readOnlyMode) {
            logger.info("🔒 Running in READ-ONLY MODE - database will NOT be modified");
            logger.info("✅ Database contains {} aircraft", aircraftModelRepository.count());
            return;  // No hacer nada más
        }

        logger.info("🚀 Loading/Updating SkyVault aircraft database (Safe Mode)...");

        // Siempre cargar catálogos base
        loadCatalogs();

        // Cargar aircraft data (solo nuevos)
        loadAircraftData();

        // NO actualizar datos existentes
        // updateEmptyFieldsOnly();  // COMENTADO

        logger.info("✅ Database contains {} aircraft successfully!", aircraftModelRepository.count());
    }

    /**
     * MÉTODO PÚBLICO PARA FORZAR ACTUALIZACIONES DESDE ENDPOINTS (MODO SEGURO)
     */
    public void forceDataUpdate() {
        logger.info("🔄 Forcing SAFE data update (only empty fields)...");
        updateEmptyFieldsOnly();
        logger.info("✅ Safe data update completed");
    }

    private void loadCatalogs() {
        logger.info("📊 Loading catalog data...");

        // ManufacturersPage
        createManufacturer("Airbus", "European Union", LocalDate.of(1970, 12, 18));
        createManufacturer("Boeing", "United States", LocalDate.of(1916, 7, 15));

        // Types
        createType("Comercial", "Commercial passenger aircraft");
        createType("Business Jet", "Business and private jets");
        createType("Carguero", "Cargo and freight aircraft");

        // Production States
        createProductionState("En Producción", "Currently being manufactured", true);
        createProductionState("Finalizado", "No longer in production", false);
        createProductionState("En Desarrollo", "In development phase", true);

        // Size Categories
        createSizeCategory("Narrow-body", "Single-aisle aircraft", "100-250 passengers", 100, 250);
        createSizeCategory("Wide-body", "Twin-aisle aircraft", "250-600 passengers", 250, 600);
        createSizeCategory("Jumbo", "Very large aircraft", "400+ passengers", 400, 900);

        logger.info("✅ Catalog data loaded successfully");
    }

    private void loadAircraftData() {
        logger.info("✈️ Loading aircraft families and models...");

        var airbus = manufacturerRepository.findByName("Airbus").orElse(null);
        var boeing = manufacturerRepository.findByName("Boeing").orElse(null);

        if (airbus == null || boeing == null) {
            logger.error("❌ ManufacturersPage not found! Cannot load aircraft data.");
            return;
        }

        // Create families
        createFamily("A220 Family", airbus, "Short to medium-range narrow-body aircraft family");
        createFamily("A320 Family", airbus, "Short to medium-range narrow-body aircraft family");
        createFamily("A330 Family", airbus, "Wide-body aircraft family");
        createFamily("A350 Family", airbus, "Long-range wide-body aircraft family");
        createFamily("A380 Family", airbus, "Double-deck, wide-body aircraft family");

        createFamily("737 Family", boeing, "Short to medium-range narrow-body aircraft family");
        createFamily("747 Family", boeing, "Large wide-body commercial aircraft family");
        createFamily("767 Family", boeing, "Wide-body aircraft family");
        createFamily("777 Family", boeing, "Long-range wide-body aircraft family");
        createFamily("777X Family", boeing, "Next-generation long-range wide-body aircraft family");
        createFamily("787 Family", boeing, "Long-range, mid-size wide-body aircraft family");

        // Load ALL 36 aircraft (SOLO CREAR NUEVOS - NO ACTUALIZAR EXISTENTES)
        loadAllAircraft(airbus, boeing);

        logger.info("✅ Aircraft families and models loaded successfully");
    }

    private void loadAllAircraft(Manufacturer airbus, Manufacturer boeing) {
        logger.info("🔵 Loading all 36 aircraft (create only mode)...");

        // AIRBUS - 18 aeronaves
        loadAirbusAircraft(airbus);

        // BOEING - 18 aeronaves
        loadBoeingAircraft(boeing);

        logger.info("✅ All aircraft loaded");
    }

    private void loadAirbusAircraft(Manufacturer airbus) {
        // A220 Family (3 aeronaves)
        var a220Family = familyRepository.findByNameAndManufacturerId("A220 Family", airbus.getId()).orElse(null);
        if (a220Family != null) {
            createAircraftSafe("A220-100", airbus, a220Family, "Comercial", "En Producción", "Narrow-body", 133, 21.8, 5740, 2016, "Pratt & Whitney PW1500G", "Geared Turbofan (GTF)", 108.5);
            createAircraftSafe("A220-300", airbus, a220Family, "Comercial", "En Producción", "Narrow-body", 160, 17.2, 6297, 2016, "Pratt & Whitney PW1500G", "Geared Turbofan (GTF)", 108.5);
            createAircraftSafe("ACJ TwoTwenty", airbus, a220Family, "Business Jet", "En Producción", "Narrow-body", 12, 22.9, 10465, 2021, "Pratt & Whitney PW1500G", "Geared Turbofan (GTF)", 108.5);
        }

        // A320 Family (6 aeronaves)
        var a320Family = familyRepository.findByNameAndManufacturerId("A320 Family", airbus.getId()).orElse(null);
        if (a320Family != null) {
            createAircraftSafe("A319neo", airbus, a320Family, "Comercial", "En Producción", "Narrow-body", 124, 21.4, 6900, 2016, "CFM International LEAP-1A", "Turbofan", 155.7);
            createAircraftSafe("A320neo", airbus, a320Family, "Comercial", "En Producción", "Narrow-body", 150, 21.4, 6300, 2016, "CFM International LEAP-1A", "Turbofan", 155.7);
            createAircraftSafe("A321LR", airbus, a320Family, "Comercial", "En Producción", "Narrow-body", 206, 26.3, 7400, 2018, "CFM International LEAP-1A", "Turbofan", 147.3);
            createAircraftSafe("A321neo", airbus, a320Family, "Comercial", "En Producción", "Narrow-body", 185, 26.4, 7400, 2017, "CFM International LEAP-1A", "Turbofan", 147.3);
            createAircraftSafe("A321XLR", airbus, a320Family, "Comercial", "En Producción", "Narrow-body", 220, 26.5, 8700, 2020, "CFM International LEAP-1A", "Turbofan", 147.3);
            createAircraftSafe("ACJ320neo", airbus, a320Family, "Business Jet", "En Producción", "Narrow-body", 8, 27.5, 11100, 2018, "CFM LEAP-1A", "Turbofan", 155.7);
        }

        // A330 Family (4 aeronaves)
        var a330Family = familyRepository.findByNameAndManufacturerId("A330 Family", airbus.getId()).orElse(null);
        if (a330Family != null) {
            createAircraftSafe("A330-200F", airbus, a330Family, "Carguero", "En Producción", "Wide-body", 0, 111.3, 7400, 2010, "Pratt & Whitney PW4000", "Turbofan", 311.0);
            createAircraftSafe("A330-800neo", airbus, a330Family, "Comercial", "En Producción", "Wide-body", 271, 111.3, 14334, 2018, "Rolls-Royce Trent 7000", "High-bypass Turbofan", 320.0);
            createAircraftSafe("A330-900neo", airbus, a330Family, "Comercial", "En Producción", "Wide-body", 303, 111.3, 13600, 2018, "Rolls-Royce Trent 7000", "High-bypass Turbofan", 320.0);
            createAircraftSafe("ACJ330neo", airbus, a330Family, "Business Jet", "En Producción", "Wide-body", 25, 111.3, 19260, 2019, "Rolls-Royce Trent 7000", "High-bypass Turbofan", 320.0);
        }

        // A350 Family (4 aeronaves)
        var a350Family = familyRepository.findByNameAndManufacturerId("A350 Family", airbus.getId()).orElse(null);
        if (a350Family != null) {
            createAircraftSafe("A350-1000", airbus, a350Family, "Comercial", "En Producción", "Wide-body", 410, 134.6, 16700, 2018, "Rolls-Royce Trent XWB-97", "High-bypass Turbofan", 431.0);
            createAircraftSafe("A350-900", airbus, a350Family, "Comercial", "En Producción", "Wide-body", 352, 133.2, 15750, 2015, "Rolls-Royce Trent XWB-84", "High-bypass Turbofan", 374.0);
            createAircraftSafe("A350F", airbus, a350Family, "Carguero", "En Producción", "Wide-body", 0, 130.0, 17000, 2021, "Rolls-Royce Trent XWB", "High-bypass Turbofan", 431.0);
            createAircraftSafe("ACJ350 XWB", airbus, a350Family, "Business Jet", "En Producción", "Wide-body", 25, 134.0, 20550, 2020, "Rolls-Royce Trent XWB", "High-bypass Turbofan", 431.0);
        }

        // A380 Family (1 aeronave)
        var a380Family = familyRepository.findByNameAndManufacturerId("A380 Family", airbus.getId()).orElse(null);
        if (a380Family != null) {
            createAircraftSafe("A380-800", airbus, a380Family, "Comercial", "Finalizado", "Jumbo", 850, 250.0, 15400, 2007, "Engine Alliance GP7200", "High-bypass Turbofan", 374.0);
        }
    }

    private void loadBoeingAircraft(Manufacturer boeing) {
        // 737 Family (7 aeronaves)
        var b737Family = familyRepository.findByNameAndManufacturerId("737 Family", boeing.getId()).orElse(null);
        if (b737Family != null) {
            createAircraftSafe("737 MAX 7", boeing, b737Family, "Comercial", "En Producción", "Narrow-body", 153, 20.7, 7040, 2018, "CFM International LEAP-1B", "High-bypass Turbofan", 124.5);
            createAircraftSafe("737 MAX 8", boeing, b737Family, "Comercial", "En Producción", "Narrow-body", 178, 20.7, 6480, 2017, "CFM International LEAP-1B", "High-bypass Turbofan", 124.5);
            createAircraftSafe("737 MAX 9", boeing, b737Family, "Comercial", "En Producción", "Narrow-body", 193, 20.7, 6110, 2018, "CFM International LEAP-1B", "High-bypass Turbofan", 124.5);
            createAircraftSafe("737 MAX 10", boeing, b737Family, "Comercial", "En Producción", "Narrow-body", 204, 20.7, 5740, 2020, "CFM International LEAP-1B", "High-bypass Turbofan", 124.5);
            createAircraftSafe("737-800", boeing, b737Family, "Comercial", "En Producción", "Narrow-body", 162, 20.8, 5765, 1998, "CFM International LEAP-1B", "High-bypass Turbofan", 121.4);
            createAircraftSafe("737900ER", boeing, b737Family, "Comercial", "En Producción", "Narrow-body", 178, 20.8, 5600, 2001, "CFM International LEAP-1B", "High-bypass Turbofan", 121.4);
            createAircraftSafe("BBJ 737-9", boeing, b737Family, "Business Jet", "En Producción", "Narrow-body", 19, 20.7, 11110, 2017, "CFM International LEAP-1B", "High-bypass Turbofan", 124.5);
        }

        // 747 Family (1 aeronave)
        var b747Family = familyRepository.findByNameAndManufacturerId("747 Family", boeing.getId()).orElse(null);
        if (b747Family != null) {
            createAircraftSafe("747-8 Freighter", boeing, b747Family, "Carguero", "Finalizado", "Jumbo", 0, 181.0, 13450, 2011, "General Electric GEnx-2B67", "High-bypass Turbofan", 296.0);
        }

        // 767 Family (1 aeronave)
        var b767Family = familyRepository.findByNameAndManufacturerId("767 Family", boeing.getId()).orElse(null);
        if (b767Family != null) {
            createAircraftSafe("767-300F", boeing, b767Family, "Carguero", "En Producción", "Wide-body", 0, 72.6, 6025, 1995, "Pratt & Whitney PW4000", "Turbofan", 276.0);
        }

        // 777 Family (2 aeronaves)
        var b777Family = familyRepository.findByNameAndManufacturerId("777 Family", boeing.getId()).orElse(null);
        if (b777Family != null) {
            createAircraftSafe("777-300ER", boeing, b777Family, "Comercial", "En Producción", "Wide-body", 365, 136.9, 13650, 2003, "General Electric GE90-115B", "High-bypass Turbofan", 513.0);
            createAircraftSafe("777F", boeing, b777Family, "Carguero", "En Producción", "Wide-body", 0, 145.0, 9200, 2009, "General Electric GE90-115B", "High-bypass Turbofan", 511.0);
        }

        // 777X Family (3 aeronaves)
        var b777xFamily = familyRepository.findByNameAndManufacturerId("777X Family", boeing.getId()).orElse(null);
        if (b777xFamily != null) {
            createAircraftSafe("777-8", boeing, b777xFamily, "Comercial", "En Producción", "Wide-body", 395, 158.0, 16190, 2022, "General Electric GE9X", "High-bypass Turbofan", 467.0);
            createAircraftSafe("777-9", boeing, b777xFamily, "Comercial", "En Desarrollo", "Wide-body", 426, 158.0, 13500, 2026, "General Electric GE9X", "High-bypass Turbofan", 467.0);
            createAircraftSafe("BBJ 777-9", boeing, b777xFamily, "Business Jet", "En Desarrollo", "Wide-body", 75, 158.0, 20420, 2026, "General Electric GE9X", "High-bypass Turbofan", 467.0);
        }

        // 787 Family (4 aeronaves)
        var b787Family = familyRepository.findByNameAndManufacturerId("787 Family", boeing.getId()).orElse(null);
        if (b787Family != null) {
            createAircraftSafe("787-8", boeing, b787Family, "Comercial", "En Producción", "Wide-body", 248, 101.3, 13530, 2011, "General Electric GEnx-1B", "High-bypass Turbofan", 338.4);
            createAircraftSafe("787-9", boeing, b787Family, "Comercial", "En Producción", "Wide-body", 296, 101.3, 14010, 2014, "General Electric GEnx-1B", "High-bypass Turbofan", 338.4);
            createAircraftSafe("787-10", boeing, b787Family, "Comercial", "En Producción", "Wide-body", 336, 101.3, 11730, 2017, "General Electric GEnx-1B", "High-bypass Turbofan", 338.4);
            createAircraftSafe("BBJ 787-9", boeing, b787Family, "Business Jet", "En Producción", "Wide-body", 25, 101.3, 17550, 2011, "General Electric GEnx-1B", "High-bypass Turbofan", 338.4);
        }
    }

    /**
     * ✅ MÉTODO SEGURO: ACTUALIZAR SOLO CAMPOS VACÍOS/NULL (NO SOBREESCRIBE EDICIONES MANUALES)
     */
    private void updateEmptyFieldsOnly() {
        logger.info("🔄 Updating ONLY empty/null fields (SAFE MODE - preserves manual edits)...");

        // Actualizar aircraft con campos vacíos
        var allAircraft = aircraftModelRepository.findAll();
        for (var aircraft : allAircraft) {
            updateAircraftEmptyFieldsOnly(aircraft);
        }

        // Actualizar specifications con campos vacíos
        var allSpecifications = specificationsRepository.findAll();
        for (var spec : allSpecifications) {
            updateSpecificationsEmptyFieldsOnly(spec);
        }

        logger.info("✅ Empty fields updated safely - manual edits preserved");
    }

    // ==================== HELPER METHODS ====================

    private void createManufacturer(String name, String country, LocalDate foundedDate) {
        if (!manufacturerRepository.existsByName(name)) {
            var manufacturer = new Manufacturer();
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            manufacturer.setFoundedDate(foundedDate);
            manufacturerRepository.save(manufacturer);
            logger.debug("✅ Created manufacturer: {}", name);
        }
    }

    private void createType(String name, String description) {
        if (!typeRepository.existsByName(name)) {
            var type = new Type();
            type.setName(name);
            type.setDescription(description);
            typeRepository.save(type);
            logger.debug("✅ Created type: {}", name);
        }
    }

    private void createProductionState(String name, String description, Boolean isActive) {
        if (!productionStateRepository.existsByName(name)) {
            var productionState = new ProductionState();
            productionState.setName(name);
            productionState.setDescription(description);
            productionState.setIsActive(isActive);
            productionStateRepository.save(productionState);
            logger.debug("✅ Created production state: {}", name);
        }
    }

    private void createSizeCategory(String name, String description, String passengerRange, Integer minPassengers, Integer maxPassengers) {
        if (!sizeCategoryRepository.existsByName(name)) {
            var sizeCategory = new SizeCategory();
            sizeCategory.setName(name);
            sizeCategory.setDescription(description);
            sizeCategory.setPassengerRange(passengerRange);
            sizeCategory.setMinPassengers(minPassengers);
            sizeCategory.setMaxPassengers(maxPassengers);
            sizeCategoryRepository.save(sizeCategory);
            logger.debug("✅ Created size category: {}", name);
        }
    }

    private void createFamily(String name, Manufacturer manufacturer, String description) {
        var existing = familyRepository.findByNameAndManufacturerId(name, manufacturer.getId());
        if (existing.isEmpty()) {
            var family = new Family();
            family.setName(name);
            family.setManufacturer(manufacturer);
            family.setDescription(description);
            familyRepository.save(family);
            logger.debug("✅ Created family: {}", name);
        }
    }

    /**
     * ✅ CREAR AIRCRAFT SAFE - SOLO SI NO EXISTE (NO ACTUALIZA EXISTENTES)
     */
    private void createAircraftSafe(String name, Manufacturer manufacturer, Family family, String typeName,
                                    String productionStateName, String sizeCategoryName, int maxPassengers,
                                    double fuelCapacity, int rangeKm, int introductionYear, String engineName,
                                    String engineType, double thrustKn) {

        // Solo crear si NO existe - RESPETA DATOS EXISTENTES
        var existingAircraft = aircraftModelRepository.findByName(name);
        if (existingAircraft.isPresent()) {
            logger.debug("⚠️ Aircraft {} already exists, skipping creation (preserving existing data)", name);
            return;
        }

        // Find references
        var type = typeRepository.findByName(typeName).orElse(null);
        var productionState = productionStateRepository.findByName(productionStateName).orElse(null);
        var sizeCategory = sizeCategoryRepository.findByName(sizeCategoryName).orElse(null);

        // Create new aircraft
        var aircraft = new AircraftModel();
        aircraft.setName(name);
        aircraft.setModel(name);
        aircraft.setDisplayName(name);
        aircraft.setIntroductionYear(introductionYear);
        aircraft.setMaxPassengers(maxPassengers);
        aircraft.setTypicalPassengers(maxPassengers > 0 ? (int)(maxPassengers * 0.85) : 0);
        aircraft.setRangeKm(rangeKm);
        aircraft.setCruiseSpeedKnots(450); // Default - se llenará con datos reales después
        aircraft.setMaxFuelTons(BigDecimal.valueOf(fuelCapacity));
        aircraft.setMinCrew(2);
        aircraft.setIsActive(true);
        aircraft.setLastInfoUpdate(LocalDate.now());

        // Set relationships
        aircraft.setManufacturer(manufacturer);
        aircraft.setFamily(family);
        if (type != null) aircraft.setType(type);
        if (productionState != null) aircraft.setProductionState(productionState);
        if (sizeCategory != null) aircraft.setSizeCategory(sizeCategory);

        aircraft = aircraftModelRepository.save(aircraft);

        // Create specifications (solo si no existe)
        createSpecificationsSafe(aircraft, fuelCapacity, engineName, engineType, thrustKn);

        logger.debug("✅ Created new aircraft: {}", name);
    }

    /**
     * ✅ ACTUALIZAR SOLO CAMPOS VACÍOS EN AIRCRAFT (PRESERVA EDICIONES MANUALES)
     */
    private void updateAircraftEmptyFieldsOnly(AircraftModel aircraft) {
        boolean updated = false;

        // DESCRIPCIÓN: Solo actualizar si está completamente vacía
        if (aircraft.getDescription() == null || aircraft.getDescription().trim().isEmpty()) {
            // NO actualizar description - dejarlo para que tu equipo lo edite manualmente
            // Mantener como null para que puedan editarlo
        }

        // FIRST_FLIGHT_DATE: Solo actualizar si está vacío
        if (aircraft.getFirstFlightDate() == null) {
            // NO actualizar first_flight_date - dejarlo para edición manual
            // Tu equipo lo editará manualmente
        }

        // CRUISE_SPEED: Solo actualizar si tiene el valor por defecto (450)
        if (aircraft.getCruiseSpeedKnots() == null || aircraft.getCruiseSpeedKnots() == 450) {
            switch (aircraft.getName()) {
                case "A220-100", "A220-300" -> {
                    aircraft.setCruiseSpeedKnots(834);
                    updated = true;
                }
                case "A320neo", "A321neo" -> {
                    aircraft.setCruiseSpeedKnots(828);
                    updated = true;
                }
                case "737 MAX 8", "737 MAX 9" -> {
                    aircraft.setCruiseSpeedKnots(842);
                    updated = true;
                }
                case "787-8", "787-9" -> {
                    aircraft.setCruiseSpeedKnots(903);
                    updated = true;
                }
            }
        }

        // MIN_CREW: Solo actualizar si está vacío o es el default
        if (aircraft.getMinCrew() == null || aircraft.getMinCrew() == 2) {
            // Mantener 2 como default, tu equipo puede editarlo después
        }

        if (updated) {
            aircraft.setLastInfoUpdate(LocalDate.now());
            aircraftModelRepository.save(aircraft);
            logger.debug("🔄 Updated ONLY empty fields for aircraft: {}", aircraft.getName());
        }
    }

    /**
     * ✅ ACTUALIZAR SOLO CAMPOS VACÍOS EN SPECIFICATIONS (PRESERVA EDICIONES MANUALES)
     */
    private void updateSpecificationsEmptyFieldsOnly(Specifications spec) {
        boolean updated = false;
        var aircraft = spec.getAircraftModel();

        // DIMENSIONES: Solo agregar si están completamente vacías
        if (spec.getLengthM() == null) {
            switch (aircraft.getName()) {
                case "A220-100" -> {
                    spec.setLengthM(BigDecimal.valueOf(35.0));
                    spec.setWingspanM(BigDecimal.valueOf(35.1));
                    spec.setHeightM(BigDecimal.valueOf(11.5));
                    updated = true;
                }
                case "A320neo" -> {
                    spec.setLengthM(BigDecimal.valueOf(37.6));
                    spec.setWingspanM(BigDecimal.valueOf(35.8));
                    spec.setHeightM(BigDecimal.valueOf(11.8));
                    updated = true;
                }
                case "737 MAX 8" -> {
                    spec.setLengthM(BigDecimal.valueOf(39.5));
                    spec.setWingspanM(BigDecimal.valueOf(35.9));
                    spec.setHeightM(BigDecimal.valueOf(12.3));
                    updated = true;
                }
                case "787-8" -> {
                    spec.setLengthM(BigDecimal.valueOf(56.7));
                    spec.setWingspanM(BigDecimal.valueOf(60.1));
                    spec.setHeightM(BigDecimal.valueOf(17.0));
                    updated = true;
                }
                // Más casos según necesites
            }
        }

        // PESOS: Solo agregar si están vacíos
        if (spec.getEmptyWeightKg() == null) {
            switch (aircraft.getName()) {
                case "A220-100" -> {
                    spec.setEmptyWeightKg(BigDecimal.valueOf(38100));
                    spec.setMaxTakeoffWeightKg(BigDecimal.valueOf(63100));
                    updated = true;
                }
                case "A320neo" -> {
                    spec.setEmptyWeightKg(BigDecimal.valueOf(42600));
                    spec.setMaxTakeoffWeightKg(BigDecimal.valueOf(79000));
                    updated = true;
                }
                case "737 MAX 8" -> {
                    spec.setEmptyWeightKg(BigDecimal.valueOf(45070));
                    spec.setMaxTakeoffWeightKg(BigDecimal.valueOf(82191));
                    updated = true;
                }
                case "787-8" -> {
                    spec.setEmptyWeightKg(BigDecimal.valueOf(119950));
                    spec.setMaxTakeoffWeightKg(BigDecimal.valueOf(227930));
                    updated = true;
                }
            }
        }

        // CABINA: Solo agregar si está completamente vacía
        if (spec.getCabinLengthM() == null && aircraft.getMaxPassengers() > 0) {
            if (aircraft.getName().startsWith("A22")) {
                spec.setCabinLengthM(BigDecimal.valueOf(23.0));
                spec.setCabinWidthM(BigDecimal.valueOf(3.28));
                spec.setCabinHeightM(BigDecimal.valueOf(2.06));
                updated = true;
            } else if (aircraft.getName().startsWith("737")) {
                spec.setCabinLengthM(BigDecimal.valueOf(28.9));
                spec.setCabinWidthM(BigDecimal.valueOf(3.54));
                spec.setCabinHeightM(BigDecimal.valueOf(2.01));
                updated = true;
            }
        }

        // Todos los demás campos los dejamos para edición manual:
        // business_class_seats, economy_class_seats, first_class_seats,
        // cargo_volume_m3, certification_authorities, fuel_consumption_lph,
        // landing_distance_m, max_landing_weight_kg, max_payload_kg,
        // max_speed_kmh, noise_level_db, range_with_max_pax_km,
        // range_with_max_payload_km, seat_pitch_economy_cm, seat_width_economy_cm,
        // service_ceiling_m, takeoff_distance_m, wing_area_m2

        if (updated) {
            specificationsRepository.save(spec);
            logger.debug("🔄 Updated ONLY empty fields for specifications: {}", aircraft.getName());
        }
    }

    /**
     * ✅ CREAR SPECIFICATIONS SAFE - SOLO SI NO EXISTE
     */
    private void createSpecificationsSafe(AircraftModel aircraft, double fuelCapacity, String engineName, String engineType, double thrustKn) {
        // Solo crear si no existe - RESPETA SPECIFICATIONS EXISTENTES
        var existingSpec = specificationsRepository.findByAircraftModelId(aircraft.getId());
        if (existingSpec.isPresent()) {
            logger.debug("⚠️ Specifications for {} already exist, skipping creation (preserving existing data)", aircraft.getName());
            return;
        }

        try {
            var specifications = new Specifications();
            specifications.setAircraftModel(aircraft);
            specifications.setEngineCount(2);
            specifications.setEngineManufacturer(engineName.split(" ")[0]);
            specifications.setEngineModel(engineName);
            specifications.setFuelCapacityLiters((int)(fuelCapacity * 1000));
            specifications.setEngineThrustN(BigDecimal.valueOf(thrustKn * 1000));
            specifications.setTotalThrustN(BigDecimal.valueOf(thrustKn * 1000 * 2));

            specificationsRepository.save(specifications);
            logger.debug("✅ Created specifications for: {}", aircraft.getName());
        } catch (Exception e) {
            logger.warn("⚠️ Could not create specifications for {}: {}", aircraft.getName(), e.getMessage());
        }
    }
}
