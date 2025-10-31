package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información resumida del fabricante")
public class ManufacturerSummaryDto {

    @Schema(description = "ID único del fabricante", example = "1")
    private Long id;

    @Schema(description = "Nombre del fabricante", example = "Boeing")
    private String name;

    @Schema(description = "País de origen", example = "United States")
    private String country;

    // Constructors
    public ManufacturerSummaryDto() {}

    public ManufacturerSummaryDto(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
