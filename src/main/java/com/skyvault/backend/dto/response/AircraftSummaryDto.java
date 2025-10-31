package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información resumida de aeronave para referencias")
public class AircraftSummaryDto {

    @Schema(description = "ID único de la aeronave", example = "15")
    private Long id;

    @Schema(description = "Nombre completo", example = "Boeing 737-800")
    private String name;

    @Schema(description = "Fabricante", example = "Boeing")
    private String manufacturerName;

    @Schema(description = "Valor destacado", example = "189 passengers")
    private String highlightValue;

    // Constructors
    public AircraftSummaryDto() {}

    public AircraftSummaryDto(Long id, String name, String manufacturerName) {
        this.id = id;
        this.name = name;
        this.manufacturerName = manufacturerName;
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

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getHighlightValue() {
        return highlightValue;
    }

    public void setHighlightValue(String highlightValue) {
        this.highlightValue = highlightValue;
    }
}
