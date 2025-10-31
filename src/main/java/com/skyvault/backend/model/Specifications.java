package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "specifications",
        indexes = {
                @Index(name = "idx_specs_aircraft", columnList = "aircraft_model_id")
        })
public class Specifications extends BaseEntity {

    // Dimensiones físicas
    @Column(name = "length_m", precision = 8, scale = 2)
    private BigDecimal lengthM;

    @Column(name = "wingspan_m", precision = 8, scale = 2)
    private BigDecimal wingspanM;

    @Column(name = "height_m", precision = 8, scale = 2)
    private BigDecimal heightM;

    @Column(name = "wing_area_m2", precision = 8, scale = 2)
    private BigDecimal wingAreaM2;

    // Pesos
    @Column(name = "empty_weight_kg", precision = 10, scale = 2)
    private BigDecimal emptyWeightKg;

    @Column(name = "max_takeoff_weight_kg", precision = 10, scale = 2)
    private BigDecimal maxTakeoffWeightKg;

    @Column(name = "max_landing_weight_kg", precision = 10, scale = 2)
    private BigDecimal maxLandingWeightKg;

    @Column(name = "max_payload_kg", precision = 10, scale = 2)
    private BigDecimal maxPayloadKg;

    // Rendimiento
    @Column(name = "max_speed_kmh")
    private Integer maxSpeedKmh;

    @Column(name = "service_ceiling_m")
    private Integer serviceCeilingM;

    @Column(name = "takeoff_distance_m")
    private Integer takeoffDistanceM;

    @Column(name = "landing_distance_m")
    private Integer landingDistanceM;

    // Combustible
    @Column(name = "fuel_capacity_liters")
    private Integer fuelCapacityLiters;

    @Column(name = "fuel_consumption_lph")
    private Integer fuelConsumptionLph;

    // Capacidades de cabina
    @Column(name = "cabin_length_m", precision = 6, scale = 2)
    private BigDecimal cabinLengthM;

    @Column(name = "cabin_width_m", precision = 6, scale = 2)
    private BigDecimal cabinWidthM;

    @Column(name = "cabin_height_m", precision = 6, scale = 2)
    private BigDecimal cabinHeightM;

    @Column(name = "cargo_volume_m3", precision = 8, scale = 2)
    private BigDecimal cargoVolumeM3;

    // Información de motores
    @Column(name = "engine_manufacturer", length = 100)
    private String engineManufacturer;

    @Column(name = "engine_model", length = 100)
    private String engineModel;

    @Column(name = "engine_count")
    private Integer engineCount;

    @Column(name = "engine_thrust_n", precision = 10, scale = 2)
    private BigDecimal engineThrustN;

    @Column(name = "total_thrust_n", precision = 10, scale = 2)
    private BigDecimal totalThrustN;

    // Configuraciones de asientos
    @Column(name = "first_class_seats")
    private Integer firstClassSeats;

    @Column(name = "business_class_seats")
    private Integer businessClassSeats;

    @Column(name = "economy_class_seats")
    private Integer economyClassSeats;

    @Column(name = "seat_pitch_economy_cm")
    private Integer seatPitchEconomyCm;

    @Column(name = "seat_width_economy_cm")
    private Integer seatWidthEconomyCm;

    // Rangos operacionales
    @Column(name = "range_with_max_pax_km")
    private Integer rangeWithMaxPaxKm;

    @Column(name = "range_with_max_payload_km")
    private Integer rangeWithMaxPayloadKm;

    // Certificaciones y regulaciones
    @Column(name = "certification_authorities", length = 200)
    private String certificationAuthorities;

    @Column(name = "noise_level_db", precision = 5, scale = 2)
    private BigDecimal noiseLevelDb;

    // Relación uno a uno con AircraftModel
    @NotNull(message = "Aircraft model is required")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_model_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_specs_aircraft_model"))
    private AircraftModel aircraftModel;

    // Constructors
    public Specifications() {}

    public Specifications(AircraftModel aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    // Helper methods
    public boolean hasBasicSpecs() {
        return lengthM != null && wingspanM != null && heightM != null;
    }

    public boolean hasEngineInfo() {
        return engineManufacturer != null && engineModel != null && engineCount != null;
    }

    public boolean hasWeightInfo() {
        return emptyWeightKg != null && maxTakeoffWeightKg != null;
    }

    // GETTERS AND SETTERS COMPLETOS - TODOS LOS CAMPOS

    public BigDecimal getLengthM() {
        return lengthM;
    }

    public void setLengthM(BigDecimal lengthM) {
        this.lengthM = lengthM;
    }

    public BigDecimal getWingspanM() {
        return wingspanM;
    }

    public void setWingspanM(BigDecimal wingspanM) {
        this.wingspanM = wingspanM;
    }

    public BigDecimal getHeightM() {
        return heightM;
    }

    public void setHeightM(BigDecimal heightM) {
        this.heightM = heightM;
    }

    public BigDecimal getWingAreaM2() {
        return wingAreaM2;
    }

    public void setWingAreaM2(BigDecimal wingAreaM2) {
        this.wingAreaM2 = wingAreaM2;
    }

    public BigDecimal getEmptyWeightKg() {
        return emptyWeightKg;
    }

    public void setEmptyWeightKg(BigDecimal emptyWeightKg) {
        this.emptyWeightKg = emptyWeightKg;
    }

    public BigDecimal getMaxTakeoffWeightKg() {
        return maxTakeoffWeightKg;
    }

    public void setMaxTakeoffWeightKg(BigDecimal maxTakeoffWeightKg) {
        this.maxTakeoffWeightKg = maxTakeoffWeightKg;
    }

    public BigDecimal getMaxLandingWeightKg() {
        return maxLandingWeightKg;
    }

    public void setMaxLandingWeightKg(BigDecimal maxLandingWeightKg) {
        this.maxLandingWeightKg = maxLandingWeightKg;
    }

    public BigDecimal getMaxPayloadKg() {
        return maxPayloadKg;
    }

    public void setMaxPayloadKg(BigDecimal maxPayloadKg) {
        this.maxPayloadKg = maxPayloadKg;
    }

    public Integer getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    public void setMaxSpeedKmh(Integer maxSpeedKmh) {
        this.maxSpeedKmh = maxSpeedKmh;
    }

    public Integer getServiceCeilingM() {
        return serviceCeilingM;
    }

    public void setServiceCeilingM(Integer serviceCeilingM) {
        this.serviceCeilingM = serviceCeilingM;
    }

    public Integer getTakeoffDistanceM() {
        return takeoffDistanceM;
    }

    public void setTakeoffDistanceM(Integer takeoffDistanceM) {
        this.takeoffDistanceM = takeoffDistanceM;
    }

    public Integer getLandingDistanceM() {
        return landingDistanceM;
    }

    public void setLandingDistanceM(Integer landingDistanceM) {
        this.landingDistanceM = landingDistanceM;
    }

    public Integer getFuelCapacityLiters() {
        return fuelCapacityLiters;
    }

    public void setFuelCapacityLiters(Integer fuelCapacityLiters) {
        this.fuelCapacityLiters = fuelCapacityLiters;
    }

    public Integer getFuelConsumptionLph() {
        return fuelConsumptionLph;
    }

    public void setFuelConsumptionLph(Integer fuelConsumptionLph) {
        this.fuelConsumptionLph = fuelConsumptionLph;
    }

    public BigDecimal getCabinLengthM() {
        return cabinLengthM;
    }

    public void setCabinLengthM(BigDecimal cabinLengthM) {
        this.cabinLengthM = cabinLengthM;
    }

    public BigDecimal getCabinWidthM() {
        return cabinWidthM;
    }

    public void setCabinWidthM(BigDecimal cabinWidthM) {
        this.cabinWidthM = cabinWidthM;
    }

    public BigDecimal getCabinHeightM() {
        return cabinHeightM;
    }

    public void setCabinHeightM(BigDecimal cabinHeightM) {
        this.cabinHeightM = cabinHeightM;
    }

    public BigDecimal getCargoVolumeM3() {
        return cargoVolumeM3;
    }

    public void setCargoVolumeM3(BigDecimal cargoVolumeM3) {
        this.cargoVolumeM3 = cargoVolumeM3;
    }

    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public Integer getEngineCount() {
        return engineCount;
    }

    public void setEngineCount(Integer engineCount) {
        this.engineCount = engineCount;
    }

    public BigDecimal getEngineThrustN() {
        return engineThrustN;
    }

    public void setEngineThrustN(BigDecimal engineThrustN) {
        this.engineThrustN = engineThrustN;
    }

    public BigDecimal getTotalThrustN() {
        return totalThrustN;
    }

    public void setTotalThrustN(BigDecimal totalThrustN) {
        this.totalThrustN = totalThrustN;
    }

    public Integer getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(Integer firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public Integer getBusinessClassSeats() {
        return businessClassSeats;
    }

    public void setBusinessClassSeats(Integer businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }

    public Integer getEconomyClassSeats() {
        return economyClassSeats;
    }

    public void setEconomyClassSeats(Integer economyClassSeats) {
        this.economyClassSeats = economyClassSeats;
    }

    public Integer getSeatPitchEconomyCm() {
        return seatPitchEconomyCm;
    }

    public void setSeatPitchEconomyCm(Integer seatPitchEconomyCm) {
        this.seatPitchEconomyCm = seatPitchEconomyCm;
    }

    public Integer getSeatWidthEconomyCm() {
        return seatWidthEconomyCm;
    }

    public void setSeatWidthEconomyCm(Integer seatWidthEconomyCm) {
        this.seatWidthEconomyCm = seatWidthEconomyCm;
    }

    public Integer getRangeWithMaxPaxKm() {
        return rangeWithMaxPaxKm;
    }

    public void setRangeWithMaxPaxKm(Integer rangeWithMaxPaxKm) {
        this.rangeWithMaxPaxKm = rangeWithMaxPaxKm;
    }

    public Integer getRangeWithMaxPayloadKm() {
        return rangeWithMaxPayloadKm;
    }

    public void setRangeWithMaxPayloadKm(Integer rangeWithMaxPayloadKm) {
        this.rangeWithMaxPayloadKm = rangeWithMaxPayloadKm;
    }

    public String getCertificationAuthorities() {
        return certificationAuthorities;
    }

    public void setCertificationAuthorities(String certificationAuthorities) {
        this.certificationAuthorities = certificationAuthorities;
    }

    public BigDecimal getNoiseLevelDb() {
        return noiseLevelDb;
    }

    public void setNoiseLevelDb(BigDecimal noiseLevelDb) {
        this.noiseLevelDb = noiseLevelDb;
    }

    public AircraftModel getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(AircraftModel aircraftModel) {
        this.aircraftModel = aircraftModel;
    }
}
