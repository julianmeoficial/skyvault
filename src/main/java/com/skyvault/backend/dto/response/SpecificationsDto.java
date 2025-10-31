package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Especificaciones técnicas detalladas")
public class SpecificationsDto {

    // Dimensiones físicas
    @Schema(description = "Longitud en metros", example = "39.5")
    private String lengthM;

    @Schema(description = "Envergadura en metros", example = "35.8")
    private String wingspanM;

    @Schema(description = "Altura en metros", example = "12.5")
    private String heightM;

    @Schema(description = "Área alar en m²", example = "124.6")
    private String wingAreaM2;

    // Pesos
    @Schema(description = "Peso vacío en kg", example = "41413")
    private String emptyWeightKg;

    @Schema(description = "Peso máximo al despegue en kg", example = "79016")
    private String maxTakeoffWeightKg;

    @Schema(description = "Peso máximo de aterrizaje en kg", example = "66361")
    private String maxLandingWeightKg;

    @Schema(description = "Carga útil máxima en kg", example = "23317")
    private String maxPayloadKg;

    // Rendimiento
    @Schema(description = "Velocidad máxima en km/h", example = "876")
    private String maxSpeedKmh;

    @Schema(description = "Techo de servicio en metros", example = "12497")
    private String serviceCeilingM;

    @Schema(description = "Distancia de despegue en metros", example = "2316")
    private String takeoffDistanceM;

    @Schema(description = "Distancia de aterrizaje en metros", example = "1524")
    private String landingDistanceM;

    // Combustible
    @Schema(description = "Capacidad de combustible en litros", example = "26020")
    private String fuelCapacityLiters;

    @Schema(description = "Consumo de combustible en L/h", example = "2640")
    private String fuelConsumptionLph;

    // Cabina
    @Schema(description = "Longitud de cabina en metros", example = "27.3")
    private String cabinLengthM;

    @Schema(description = "Anchura de cabina en metros", example = "3.53")
    private String cabinWidthM;

    @Schema(description = "Altura de cabina en metros", example = "2.19")
    private String cabinHeightM;

    @Schema(description = "Volumen de carga en m³", example = "44.5")
    private String cargoVolumeM3;

    // Motores
    @Schema(description = "Fabricante del motor", example = "CFM International")
    private String engineManufacturer;

    @Schema(description = "Modelo del motor", example = "CFM56-7B")
    private String engineModel;

    @Schema(description = "Número de motores", example = "2")
    private String engineCount;

    @Schema(description = "Empuje por motor en N", example = "117344")
    private String engineThrustN;

    @Schema(description = "Empuje total en N", example = "234688")
    private String totalThrustN;

    // Configuración de asientos
    @Schema(description = "Asientos de primera clase", example = "8")
    private String firstClassSeats;

    @Schema(description = "Asientos de clase ejecutiva", example = "20")
    private String businessClassSeats;

    @Schema(description = "Asientos de clase económica", example = "134")
    private String economyClassSeats;

    @Schema(description = "Espaciado entre asientos en clase económica (cm)", example = "79")
    private String seatPitchEconomyCm;

    @Schema(description = "Ancho de asiento en clase económica (cm)", example = "43")
    private String seatWidthEconomyCm;

    // Rangos operacionales
    @Schema(description = "Alcance con máximo de pasajeros en km", example = "5765")
    private String rangeWithMaxPaxKm;

    @Schema(description = "Alcance con máxima carga en km", example = "5926")
    private String rangeWithMaxPayloadKm;

    // Certificaciones
    @Schema(description = "Autoridades de certificación", example = "FAA, EASA")
    private String certificationAuthorities;

    @Schema(description = "Nivel de ruido en dB", example = "85.2")
    private String noiseLevelDb;

    // Constructors
    public SpecificationsDto() {}

    // Getters and Setters completos - todos los campos

    public String getLengthM() {
        return lengthM;
    }

    public void setLengthM(String lengthM) {
        this.lengthM = lengthM;
    }

    public String getWingspanM() {
        return wingspanM;
    }

    public void setWingspanM(String wingspanM) {
        this.wingspanM = wingspanM;
    }

    public String getHeightM() {
        return heightM;
    }

    public void setHeightM(String heightM) {
        this.heightM = heightM;
    }

    public String getWingAreaM2() {
        return wingAreaM2;
    }

    public void setWingAreaM2(String wingAreaM2) {
        this.wingAreaM2 = wingAreaM2;
    }

    public String getEmptyWeightKg() {
        return emptyWeightKg;
    }

    public void setEmptyWeightKg(String emptyWeightKg) {
        this.emptyWeightKg = emptyWeightKg;
    }

    public String getMaxTakeoffWeightKg() {
        return maxTakeoffWeightKg;
    }

    public void setMaxTakeoffWeightKg(String maxTakeoffWeightKg) {
        this.maxTakeoffWeightKg = maxTakeoffWeightKg;
    }

    public String getMaxLandingWeightKg() {
        return maxLandingWeightKg;
    }

    public void setMaxLandingWeightKg(String maxLandingWeightKg) {
        this.maxLandingWeightKg = maxLandingWeightKg;
    }

    public String getMaxPayloadKg() {
        return maxPayloadKg;
    }

    public void setMaxPayloadKg(String maxPayloadKg) {
        this.maxPayloadKg = maxPayloadKg;
    }

    public String getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    public void setMaxSpeedKmh(String maxSpeedKmh) {
        this.maxSpeedKmh = maxSpeedKmh;
    }

    public String getServiceCeilingM() {
        return serviceCeilingM;
    }

    public void setServiceCeilingM(String serviceCeilingM) {
        this.serviceCeilingM = serviceCeilingM;
    }

    public String getTakeoffDistanceM() {
        return takeoffDistanceM;
    }

    public void setTakeoffDistanceM(String takeoffDistanceM) {
        this.takeoffDistanceM = takeoffDistanceM;
    }

    public String getLandingDistanceM() {
        return landingDistanceM;
    }

    public void setLandingDistanceM(String landingDistanceM) {
        this.landingDistanceM = landingDistanceM;
    }

    public String getFuelCapacityLiters() {
        return fuelCapacityLiters;
    }

    public void setFuelCapacityLiters(String fuelCapacityLiters) {
        this.fuelCapacityLiters = fuelCapacityLiters;
    }

    public String getFuelConsumptionLph() {
        return fuelConsumptionLph;
    }

    public void setFuelConsumptionLph(String fuelConsumptionLph) {
        this.fuelConsumptionLph = fuelConsumptionLph;
    }

    public String getCabinLengthM() {
        return cabinLengthM;
    }

    public void setCabinLengthM(String cabinLengthM) {
        this.cabinLengthM = cabinLengthM;
    }

    public String getCabinWidthM() {
        return cabinWidthM;
    }

    public void setCabinWidthM(String cabinWidthM) {
        this.cabinWidthM = cabinWidthM;
    }

    public String getCabinHeightM() {
        return cabinHeightM;
    }

    public void setCabinHeightM(String cabinHeightM) {
        this.cabinHeightM = cabinHeightM;
    }

    public String getCargoVolumeM3() {
        return cargoVolumeM3;
    }

    public void setCargoVolumeM3(String cargoVolumeM3) {
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

    public String getEngineCount() {
        return engineCount;
    }

    public void setEngineCount(String engineCount) {
        this.engineCount = engineCount;
    }

    public String getEngineThrustN() {
        return engineThrustN;
    }

    public void setEngineThrustN(String engineThrustN) {
        this.engineThrustN = engineThrustN;
    }

    public String getTotalThrustN() {
        return totalThrustN;
    }

    public void setTotalThrustN(String totalThrustN) {
        this.totalThrustN = totalThrustN;
    }

    public String getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(String firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public String getBusinessClassSeats() {
        return businessClassSeats;
    }

    public void setBusinessClassSeats(String businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }

    public String getEconomyClassSeats() {
        return economyClassSeats;
    }

    public void setEconomyClassSeats(String economyClassSeats) {
        this.economyClassSeats = economyClassSeats;
    }

    public String getSeatPitchEconomyCm() {
        return seatPitchEconomyCm;
    }

    public void setSeatPitchEconomyCm(String seatPitchEconomyCm) {
        this.seatPitchEconomyCm = seatPitchEconomyCm;
    }

    public String getSeatWidthEconomyCm() {
        return seatWidthEconomyCm;
    }

    public void setSeatWidthEconomyCm(String seatWidthEconomyCm) {
        this.seatWidthEconomyCm = seatWidthEconomyCm;
    }

    public String getRangeWithMaxPaxKm() {
        return rangeWithMaxPaxKm;
    }

    public void setRangeWithMaxPaxKm(String rangeWithMaxPaxKm) {
        this.rangeWithMaxPaxKm = rangeWithMaxPaxKm;
    }

    public String getRangeWithMaxPayloadKm() {
        return rangeWithMaxPayloadKm;
    }

    public void setRangeWithMaxPayloadKm(String rangeWithMaxPayloadKm) {
        this.rangeWithMaxPayloadKm = rangeWithMaxPayloadKm;
    }

    public String getCertificationAuthorities() {
        return certificationAuthorities;
    }

    public void setCertificationAuthorities(String certificationAuthorities) {
        this.certificationAuthorities = certificationAuthorities;
    }

    public String getNoiseLevelDb() {
        return noiseLevelDb;
    }

    public void setNoiseLevelDb(String noiseLevelDb) {
        this.noiseLevelDb = noiseLevelDb;
    }
}
