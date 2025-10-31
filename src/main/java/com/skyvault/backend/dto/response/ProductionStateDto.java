package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado de producción de aeronave")
public class ProductionStateDto {

    @Schema(description = "ID único del estado", example = "3")
    private Long id;

    @Schema(description = "Nombre del estado", example = "In Production")
    private String name;

    @Schema(description = "Descripción del estado")
    private String description;

    @Schema(description = "Estado activo", example = "true")
    private Boolean isActive;

    @Schema(description = "Número de aeronaves en este estado", example = "18")
    private Integer aircraftCount;

    // Constructors
    public ProductionStateDto() {}

    public ProductionStateDto(Long id, String name) {
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getAircraftCount() {
        return aircraftCount;
    }

    public void setAircraftCount(Integer aircraftCount) {
        this.aircraftCount = aircraftCount;
    }
}
