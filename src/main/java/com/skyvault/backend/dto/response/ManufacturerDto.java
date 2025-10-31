package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Información completa del fabricante")
public class ManufacturerDto {

    @Schema(description = "ID único del fabricante", example = "1")
    private Long id;

    @Schema(description = "Nombre del fabricante", example = "Boeing")
    private String name;

    @Schema(description = "País de origen", example = "United States")
    private String country;

    @Schema(description = "Fecha de fundación", example = "1916-07-15")
    private LocalDate foundedDate;

    @Schema(description = "Descripción del fabricante")
    private String description;

    @Schema(description = "URL del sitio web oficial")
    private String websiteUrl;

    @Schema(description = "Número de aeronaves activas", example = "18")
    private Integer activeAircraftCount;

    @Schema(description = "Número de familias", example = "5")
    private Integer familyCount;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updatedAt;

    // Constructors
    public ManufacturerDto() {}

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

    public LocalDate getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(LocalDate foundedDate) {
        this.foundedDate = foundedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getActiveAircraftCount() {
        return activeAircraftCount;
    }

    public void setActiveAircraftCount(Integer activeAircraftCount) {
        this.activeAircraftCount = activeAircraftCount;
    }

    public Integer getFamilyCount() {
        return familyCount;
    }

    public void setFamilyCount(Integer familyCount) {
        this.familyCount = familyCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
