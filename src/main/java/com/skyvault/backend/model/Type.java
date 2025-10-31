package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "types",
        indexes = {@Index(name = "idx_type_name", columnList = "name")})
public class Type extends BaseEntity {

    @NotBlank(message = "Type name is required")
    @Size(max = 50, message = "Type name must not exceed 50 characters")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Size(max = 300, message = "Description must not exceed 300 characters")
    @Column(name = "description", length = 300)
    private String description;

    // Relaciones
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AircraftModel> aircraftModels = new ArrayList<>();

    public Type() {}

    public Type(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<AircraftModel> getAircraftModels() {
        return aircraftModels;
    }

    public void setAircraftModels(List<AircraftModel> aircraftModels) {
        this.aircraftModels = aircraftModels;
    }
}
