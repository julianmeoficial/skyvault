package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "size_categories",
        indexes = {@Index(name = "idx_size_category_name", columnList = "name")})
public class SizeCategory extends BaseEntity {

    @NotBlank(message = "Size category name is required")
    @Size(max = 50, message = "Size category name must not exceed 50 characters")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Size(max = 300, message = "Description must not exceed 300 characters")
    @Column(name = "description", length = 300)
    private String description;

    @Size(max = 50, message = "Passenger range must not exceed 50 characters")
    @Column(name = "passenger_range", length = 50)
    private String passengerRange;

    @Column(name = "min_passengers")
    private Integer minPassengers;

    @Column(name = "max_passengers")
    private Integer maxPassengers;

    // Relaciones
    @OneToMany(mappedBy = "sizeCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AircraftModel> aircraftModels = new ArrayList<>();

    // Constructors
    public SizeCategory() {}

    public SizeCategory(String name, String description, String passengerRange) {
        this.name = name;
        this.description = description;
        this.passengerRange = passengerRange;
    }

    // Getters and Setters
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

    public String getPassengerRange() {
        return passengerRange;
    }

    public void setPassengerRange(String passengerRange) {
        this.passengerRange = passengerRange;
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

    public List<AircraftModel> getAircraftModels() {
        return aircraftModels;
    }

    public void setAircraftModels(List<AircraftModel> aircraftModels) {
        this.aircraftModels = aircraftModels;
    }
}