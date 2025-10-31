package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información resumida de aeronave para listas y tarjetas")
public class AircraftCardDto {

    @Schema(description = "ID único de la aeronave", example = "15")
    private Long id;

    @Schema(description = "Nombre de la aeronave", example = "Boeing 737-800")
    private String name;

    @Schema(description = "Modelo específico", example = "737-800")
    private String model;

    @Schema(description = "Nombre para mostrar", example = "Boeing 737-800")
    private String displayName;

    @Schema(description = "Año de introducción", example = "1998")
    private Integer introductionYear;

    @Schema(description = "Capacidad típica de pasajeros", example = "162")
    private Integer typicalPassengers;

    @Schema(description = "Capacidad máxima de pasajeros", example = "189")
    private Integer maxPassengers;

    @Schema(description = "Alcance máximo en km", example = "5765")
    private Integer rangeKm;

    @Schema(description = "Velocidad de crucero en nudos", example = "447")
    private Integer cruiseSpeedKnots;

    @Schema(description = "Fabricante")
    private ManufacturerSummaryDto manufacturer;

    @Schema(description = "Familia de aeronaves")
    private FamilySummaryDto family;

    @Schema(description = "URL de imagen en miniatura")
    private String thumbnailUrl;

    @Schema(description = "Estado activo", example = "true")
    private Boolean isActive;

    // Constructors
    public AircraftCardDto() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIntroductionYear() {
        return introductionYear;
    }

    public void setIntroductionYear(Integer introductionYear) {
        this.introductionYear = introductionYear;
    }

    public Integer getTypicalPassengers() {
        return typicalPassengers;
    }

    public void setTypicalPassengers(Integer typicalPassengers) {
        this.typicalPassengers = typicalPassengers;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Integer getRangeKm() {
        return rangeKm;
    }

    public void setRangeKm(Integer rangeKm) {
        this.rangeKm = rangeKm;
    }

    public Integer getCruiseSpeedKnots() {
        return cruiseSpeedKnots;
    }

    public void setCruiseSpeedKnots(Integer cruiseSpeedKnots) {
        this.cruiseSpeedKnots = cruiseSpeedKnots;
    }

    public ManufacturerSummaryDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerSummaryDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public FamilySummaryDto getFamily() {
        return family;
    }

    public void setFamily(FamilySummaryDto family) {
        this.family = family;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
