package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aircraft_models",
        indexes = {
                @Index(name = "idx_aircraft_manufacturer", columnList = "manufacturer_id"),
                @Index(name = "idx_aircraft_family", columnList = "family_id"),
                @Index(name = "idx_aircraft_type", columnList = "type_id"),
                @Index(name = "idx_aircraft_production", columnList = "production_state_id"),
                @Index(name = "idx_aircraft_size", columnList = "size_category_id"),
                @Index(name = "idx_aircraft_name", columnList = "name"),
                @Index(name = "idx_aircraft_model", columnList = "model"),
                @Index(name = "idx_aircraft_passengers", columnList = "max_passengers"),
                @Index(name = "idx_aircraft_range", columnList = "range_km")
        })
public class AircraftModel extends BaseEntity {

    @NotBlank(message = "Aircraft name is required")
    @Size(max = 150, message = "Aircraft name must not exceed 150 characters")
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NotBlank(message = "Aircraft model is required")
    @Size(max = 100, message = "Aircraft model must not exceed 100 characters")
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Size(max = 200, message = "Display name must not exceed 200 characters")
    @Column(name = "display_name", length = 200)
    private String displayName;

    @Column(name = "introduction_year")
    private Integer introductionYear;

    @Column(name = "first_flight_date")
    private LocalDate firstFlightDate;

    @Column(name = "max_passengers")
    private Integer maxPassengers;

    @Column(name = "typical_passengers")
    private Integer typicalPassengers;

    @Column(name = "range_km")
    private Integer rangeKm;

    @Column(name = "cruise_speed_knots")
    private Integer cruiseSpeedKnots;

    @Column(name = "max_fuel_tons", precision = 8, scale = 2)
    private BigDecimal maxFuelTons;

    @Column(name = "min_crew")
    private Integer minCrew;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "last_info_update")
    private LocalDate lastInfoUpdate;

    // Relaciones obligatorias
    @NotNull(message = "Manufacturer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_aircraft_manufacturer"))
    private Manufacturer manufacturer;

    @NotNull(message = "Family is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_aircraft_family"))
    private Family family;

    // Relaciones opcionales
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "fk_aircraft_type"))
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_state_id",
            foreignKey = @ForeignKey(name = "fk_aircraft_production_state"))
    private ProductionState productionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_category_id",
            foreignKey = @ForeignKey(name = "fk_aircraft_size_category"))
    private SizeCategory sizeCategory;

    // Relaciones one-to-one y one-to-many
    @OneToOne(mappedBy = "aircraftModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Specifications specifications;

    @OneToMany(mappedBy = "aircraftModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    // Constructors
    public AircraftModel() {}

    public AircraftModel(String name, String model, Manufacturer manufacturer, Family family) {
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.family = family;
    }

    public String getFullDisplayName() {
        if (displayName != null && !displayName.trim().isEmpty()) {
            return displayName;
        }
        return manufacturer.getName() + " " + name;
    }

    public boolean hasSpecifications() {
        return specifications != null;
    }

    public boolean hasImages() {
        return images != null && !images.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIntroductionYear() {
        return introductionYear;
    }

    public void setIntroductionYear(Integer introductionYear) {
        this.introductionYear = introductionYear;
    }

    public LocalDate getFirstFlightDate() {
        return firstFlightDate;
    }

    public void setFirstFlightDate(LocalDate firstFlightDate) {
        this.firstFlightDate = firstFlightDate;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Integer getTypicalPassengers() {
        return typicalPassengers;
    }

    public void setTypicalPassengers(Integer typicalPassengers) {
        this.typicalPassengers = typicalPassengers;
    }

    public Integer getRangeKm() {
        return rangeKm;
    }

    public void setRangeKm(Integer rangeKm) {
        this.rangeKm = rangeKm;
    }

    public Integer getCruiseSpeedKnots() {
        return cruiseSpeedKnots;
    }

    public void setCruiseSpeedKnots(Integer cruiseSpeedKnots) {
        this.cruiseSpeedKnots = cruiseSpeedKnots;
    }

    public BigDecimal getMaxFuelTons() {
        return maxFuelTons;
    }

    public void setMaxFuelTons(BigDecimal maxFuelTons) {
        this.maxFuelTons = maxFuelTons;
    }

    public Integer getMinCrew() {
        return minCrew;
    }

    public void setMinCrew(Integer minCrew) {
        this.minCrew = minCrew;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLastInfoUpdate() {
        return lastInfoUpdate;
    }

    public void setLastInfoUpdate(LocalDate lastInfoUpdate) {
        this.lastInfoUpdate = lastInfoUpdate;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ProductionState getProductionState() {
        return productionState;
    }

    public void setProductionState(ProductionState productionState) {
        this.productionState = productionState;
    }

    public SizeCategory getSizeCategory() {
        return sizeCategory;
    }

    public void setSizeCategory(SizeCategory sizeCategory) {
        this.sizeCategory = sizeCategory;
    }

    public Specifications getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}