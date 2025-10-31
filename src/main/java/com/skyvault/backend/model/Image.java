package com.skyvault.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "images",
        indexes = {
                @Index(name = "idx_image_aircraft", columnList = "aircraft_model_id"),
                @Index(name = "idx_image_type", columnList = "image_type"),
                @Index(name = "idx_image_order", columnList = "display_order")
        })
public class Image extends BaseEntity {

    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Size(max = 50, message = "Image type must not exceed 50 characters")
    @Column(name = "image_type", length = 50)
    private String imageType; // thumbnail, main, comparison, gallery, etc.

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "is_comparison")
    private Boolean isComparison = false;

    @Size(max = 300, message = "Description must not exceed 300 characters")
    @Column(name = "description", length = 300)
    private String description;

    @Size(max = 200, message = "Alt text must not exceed 200 characters")
    @Column(name = "alt_text", length = 200)
    private String altText;

    @Size(max = 20, message = "File format must not exceed 20 characters")
    @Column(name = "file_format", length = 20)
    private String fileFormat; // jpg, png, webp, etc.

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "width_px")
    private Integer widthPx;

    @Column(name = "height_px")
    private Integer heightPx;

    // Relación con AircraftModel
    @NotNull(message = "Aircraft model is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_model_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_image_aircraft_model"))
    private AircraftModel aircraftModel;

    // Constructors
    public Image() {}

    public Image(String url, String imageType, AircraftModel aircraftModel) {
        this.url = url;
        this.imageType = imageType;
        this.aircraftModel = aircraftModel;
    }

    public boolean isValidImageType() {
        if (imageType == null) return false;
        return imageType.matches("^(thumbnail|main|comparison|gallery|detail)$");
    }

    public boolean isWebOptimized() {
        return "webp".equalsIgnoreCase(fileFormat) || "avif".equalsIgnoreCase(fileFormat);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean getIsComparison() {
        return isComparison;
    }

    public void setIsComparison(Boolean isComparison) {
        this.isComparison = isComparison;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public Integer getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(Integer widthPx) {
        this.widthPx = widthPx;
    }

    public Integer getHeightPx() {
        return heightPx;
    }

    public void setHeightPx(Integer heightPx) {
        this.heightPx = heightPx;
    }

    public AircraftModel getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(AircraftModel aircraftModel) {
        this.aircraftModel = aircraftModel;
    }
}
