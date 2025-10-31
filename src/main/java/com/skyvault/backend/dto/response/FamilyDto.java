package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Información completa de familia de aeronaves")
public class FamilyDto {

    @Schema(description = "ID único de la familia", example = "5")
    private Long id;

    @Schema(description = "Nombre de la familia", example = "737")
    private String name;

    @Schema(description = "Descripción de la familia")
    private String description;

    @Schema(description = "Fecha de lanzamiento", example = "1967-02-19")
    private LocalDate launchDate;

    @Schema(description = "Categoría de la familia", example = "Narrow-body")
    private String category;

    @Schema(description = "Fabricante")
    private ManufacturerSummaryDto manufacturer;

    @Schema(description = "Número de modelos activos", example = "8")
    private Integer activeModelCount;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updatedAt;

    // Constructors
    public FamilyDto() {}

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

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ManufacturerSummaryDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerSummaryDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getActiveModelCount() {
        return activeModelCount;
    }

    public void setActiveModelCount(Integer activeModelCount) {
        this.activeModelCount = activeModelCount;
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
