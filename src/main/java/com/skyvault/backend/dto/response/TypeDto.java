package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de aeronave")
public class TypeDto {

    @Schema(description = "ID único del tipo", example = "2")
    private Long id;

    @Schema(description = "Nombre del tipo", example = "Commercial Airliner")
    private String name;

    @Schema(description = "Descripción del tipo")
    private String description;

    @Schema(description = "Número de aeronaves de este tipo", example = "25")
    private Integer aircraftCount;

    // Constructors
    public TypeDto() {}

    public TypeDto(Long id, String name) {
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
