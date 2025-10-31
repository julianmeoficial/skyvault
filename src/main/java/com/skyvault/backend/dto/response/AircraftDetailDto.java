package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Información detallada completa de una aeronave")
public class AircraftDetailDto {

    @Schema(description = "ID único de la aeronave", example = "15")
    private Long id;

    @Schema(description = "Nombre de la aeronave", example = "Boeing 737-800")
    private String name;

    @Schema(description = "Modelo específico", example = "737-800")
    private String model;

    @Schema(description = "Nombre para mostrar", example = "Boeing 737-800")
    private String displayName;

    @Schema(description = "Descripción detallada")
    private String description;

    @Schema(description = "Año de introducción", example = "1998")
    private Integer introductionYear;

    @Schema(description = "Fecha de primer vuelo", example = "1996-07-31")
    private String firstFlightDate;

    @Schema(description = "Capacidad típica de pasajeros", example = "162")
    private Integer typicalPassengers;

    @Schema(description = "Capacidad máxima de pasajeros", example = "189")
    private Integer maxPassengers;

    @Schema(description = "Alcance máximo en km", example = "5765")
    private Integer rangeKm;

    @Schema(description = "Velocidad de crucero en nudos", example = "447")
    private Integer cruiseSpeedKnots;

    @Schema(description = "Altitud máxima de servicio en pies", example = "41000")
    private Integer serviceCeilingFt;

    @Schema(description = "Tripulación mínima requerida", example = "2")
    private Integer minCrew;

    @Schema(description = "Estado activo", example = "true")
    private Boolean isActive;

    @Schema(description = "Fabricante completo")
    private ManufacturerDto manufacturer;

    @Schema(description = "Familia completa")
    private FamilyDto family;

    @Schema(description = "Tipo de aeronave")
    private TypeDto type;

    @Schema(description = "Estado de producción")
    private ProductionStateDto productionState;

    @Schema(description = "Categoría de tamaño")
    private SizeCategoryDto sizeCategory;

    @Schema(description = "Especificaciones técnicas detalladas")
    private SpecificationsDto specifications;

    @Schema(description = "Galería de imágenes")
    private List<ImageDto> images;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updatedAt;

    // Constructors
    public AircraftDetailDto() {}

    // Getters and Setters completos
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntroductionYear() {
        return introductionYear;
    }

    public void setIntroductionYear(Integer introductionYear) {
        this.introductionYear = introductionYear;
    }

    public String getFirstFlightDate() {
        return firstFlightDate;
    }

    public void setFirstFlightDate(String firstFlightDate) {
        this.firstFlightDate = firstFlightDate;
    }

    public Integer getTypicalPassengers() {
        return typicalPassengers;
    }

    public void setTypicalPassengers(Integer typicalPassengers) {
        this.typicalPassengers = typicalPassengers;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
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

    public Integer getServiceCeilingFt() {
        return serviceCeilingFt;
    }

    public void setServiceCeilingFt(Integer serviceCeilingFt) {
        this.serviceCeilingFt = serviceCeilingFt;
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

    public ManufacturerDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public FamilyDto getFamily() {
        return family;
    }

    public void setFamily(FamilyDto family) {
        this.family = family;
    }

    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }

    public ProductionStateDto getProductionState() {
        return productionState;
    }

    public void setProductionState(ProductionStateDto productionState) {
        this.productionState = productionState;
    }

    public SizeCategoryDto getSizeCategory() {
        return sizeCategory;
    }

    public void setSizeCategory(SizeCategoryDto sizeCategory) {
        this.sizeCategory = sizeCategory;
    }

    public SpecificationsDto getSpecifications() {
        return specifications;
    }

    public void setSpecifications(SpecificationsDto specifications) {
        this.specifications = specifications;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
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
