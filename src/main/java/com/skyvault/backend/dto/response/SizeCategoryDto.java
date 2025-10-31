package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoría de tamaño de aeronave")
public class SizeCategoryDto {

    @Schema(description = "ID único de la categoría", example = "4")
    private Long id;

    @Schema(description = "Nombre de la categoría", example = "Narrow-body")
    private String name;

    @Schema(description = "Descripción de la categoría")
    private String description;

    @Schema(description = "Número mínimo de pasajeros", example = "100")
    private Integer minPassengers;

    @Schema(description = "Número máximo de pasajeros", example = "300")
    private Integer maxPassengers;

    @Schema(description = "Rango de pasajeros como texto", example = "100-300")
    private String passengerRange;

    @Schema(description = "Número de aeronaves en esta categoría", example = "12")
    private Integer aircraftCount;

    // Constructors
    public SizeCategoryDto() {}

    public SizeCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinPassengers() {
        return minPassengers;
    }

    public void setMinPassengers(Integer minPassengers) {
        this.minPassengers = minPassengers;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public String getPassengerRange() {
        return passengerRange;
    }

    public void setPassengerRange(String passengerRange) {
        this.passengerRange = passengerRange;
    }

    public Integer getAircraftCount() {
        return aircraftCount;
    }

    public void setAircraftCount(Integer aircraftCount) {
        this.aircraftCount = aircraftCount;
    }

    private Boolean isActive;

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
