package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturers",
        indexes = {@Index(name = "idx_manufacturer_name", columnList = "name")})
public class Manufacturer extends BaseEntity {

    @NotBlank(message = "Manufacturer name is required")
    @Size(max = 100, message = "Manufacturer name must not exceed 100 characters")
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "founded_date")
    private LocalDate foundedDate;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 200, message = "Website URL must not exceed 200 characters")
    @Column(name = "website_url", length = 200)
    private String websiteUrl;

    // Relaciones - CORREGIDAS
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Family> families = new ArrayList<>();

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AircraftModel> aircraftModels = new ArrayList<>();

    // Constructors
    public Manufacturer() {}

    public Manufacturer(String name, String country, LocalDate foundedDate) {
        this.name = name;
        this.country = country;
        this.foundedDate = foundedDate;
    }

    // Getters and Setters
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

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    public List<AircraftModel> getAircraftModels() {
        return aircraftModels;
    }

    public void setAircraftModels(List<AircraftModel> aircraftModels) {
        this.aircraftModels = aircraftModels;
    }
}
