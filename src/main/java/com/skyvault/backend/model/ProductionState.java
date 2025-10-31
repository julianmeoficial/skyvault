package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "production_states",
        indexes = {@Index(name = "idx_production_state_name", columnList = "name")})
public class ProductionState extends BaseEntity {

    @NotBlank(message = "Production state name is required")
    @Size(max = 50, message = "Production state name must not exceed 50 characters")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Size(max = 300, message = "Description must not exceed 300 characters")
    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Relaciones
    @OneToMany(mappedBy = "productionState", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AircraftModel> aircraftModels = new ArrayList<>();

    public ProductionState() {}

    public ProductionState(String name, String description, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
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

    public List<AircraftModel> getAircraftModels() {
        return aircraftModels;
    }

    public void setAircraftModels(List<AircraftModel> aircraftModels) {
        this.aircraftModels = aircraftModels;
    }
}
